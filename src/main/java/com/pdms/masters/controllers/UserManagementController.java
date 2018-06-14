/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeTypeDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.DesignationServiceImpl;
import com.pdms.master.service.impl.EmployeeServiceImpl;
import com.pdms.master.service.impl.EmployeeTypeServiceImpl;
import com.pdms.master.service.impl.SectionServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class UserManagementController {

    private static final Logger log = Logger.getLogger(UserManagementController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private EmployeeServiceImpl emplService;

    @Autowired
    private EmployeeTypeServiceImpl empTypeService;

    @Autowired
    private DesignationServiceImpl desigService;

    @Autowired
    private SectionServiceImpl sectionService;

    /*
    End : Autowiring of Fields
     */
 /*
        Default Constructor
     */
    public UserManagementController() {

    }

    /*
        Default Constructor
     */
    
   @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/searchMasRec")
    public ModelAndView searchMasRecView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/SearchMasRec");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/userli")
    public ModelAndView UserListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            long sessUserID = 0;
            String employeeType = "";
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

                sessUserID = empDTO.getEmployeeID();
                employeeType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();

            }

            List<EmployeeLoginDTO> userLi = emplService.getAllEmployeeDetails(request);
            modelView.addObject("userLi", userLi);

            //List<EmployeeTypeDTO> empTypeLi = empTypeService.getAllEmployeeTypeDetails();
            List<EmployeeTypeDTO> empTypeLi = getEmployeeTypeList(employeeType);
            modelView.addObject("empTypeLi", empTypeLi);

            List<SectionDTO> sectionLi = sectionService.getAllSectionDetails(request);
            modelView.addObject("sectionLi", sectionLi);

            List<DesignationDTO> desigLi = new ArrayList<>();
            if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
                desigLi = desigService.getAllDesignationsbyEmployeeType(request);
            } else if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
                desigLi = desigService.getAllDesignationsbyEmployeeType(request);
            } else if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
                desigLi = desigService.getAllDesignationsbyEmployeeType(request);
            } else if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
                desigLi = desigService.getAllDesignationsbyEmployeeType(request);
            } else if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
                desigLi = desigService.getAllDesignations();
            }
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.addObject("desigLi", desigLi);

            modelView.setViewName("/masters/UserList");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/saveuser")
    public ModelAndView SaveUserAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("userObj") EmployeeLoginDTO userObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            int retVal = emplService.insertEmployeeDetails(userObj, request);

            if (retVal > 0) {
                String user = userObj.getEmployeeProfileDTO().getFirstName() + " "
                        + userObj.getEmployeeProfileDTO().getLastName();
                redirectAttributes.addFlashAttribute("msg", "User  (" + user + ") added Successfully.");
                modelView.setViewName("redirect:/userli.htm");
            } else if (retVal == -1) {
                redirectAttributes.addFlashAttribute("ermsg", "User  with IC Number <strong>("
                        + userObj.getEmployeeProfileDTO().getIcNumber() + ")</strong> already exists.");
                modelView.setViewName("redirect:/userli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/editemp", method = RequestMethod.GET)
    public @ResponseBody
    String EditEmployeeAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eid") String encEmpId) {
        EmployeeLoginDTO empObj = new EmployeeLoginDTO();
        try {
            empObj = emplService.getSelectedEmployeeDetails(encEmpId);

        } catch (AppException e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }

        return new Gson().toJson(empObj);
    }

    @RequestMapping(value = "/upduser")
    public ModelAndView UpdateUserAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("userObj") EmployeeLoginDTO userObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            int retVal = emplService.updateEmployeeDetails(userObj, request);

            if (retVal > 0) {
                String user = userObj.getEmployeeProfileDTO().getFirstName() + " "
                        + userObj.getEmployeeProfileDTO().getLastName();
                redirectAttributes.addFlashAttribute("msg", "User  (" + user + ") updated Successfully.");
                modelView.setViewName("redirect:/userli.htm");
            } else if (retVal == -1) {
                redirectAttributes.addFlashAttribute("ermsg", "User  with  IC Number <strong>("
                        + userObj.getEmployeeProfileDTO().getIcNumber() + ")</strong> already exists.");
                modelView.setViewName("redirect:/userli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    private List<EmployeeTypeDTO> getEmployeeTypeList(final String empTypeName) throws AppException {
        List<EmployeeTypeDTO> empTypeLi = new ArrayList<>();

        if (empTypeName.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN));
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN));
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN));
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN));
        } else if (empTypeName.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER));
        } else if (empTypeName.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER));
        } else if (empTypeName.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER));
        } else if (empTypeName.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
            empTypeLi.add(empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER));
        }

        return empTypeLi;
    }

    @RequestMapping(value = "/chkdupemp", method = RequestMethod.GET)
    public @ResponseBody
    String CheckDuplicateEmployeeAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("icno") String icNumber) {
        long res = 0;
        try {
            res = emplService.checkDuplicateEmployees(icNumber);

        } catch (AppException e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }

        return new Gson().toJson(res + "");
    }

}
