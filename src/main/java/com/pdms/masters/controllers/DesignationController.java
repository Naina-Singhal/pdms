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
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.DesignationServiceImpl;
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
public class DesignationController {
    
    private static final Logger log = Logger.getLogger(DesignationController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private DesignationServiceImpl desigService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public DesignationController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
 
    @RequestMapping(value = "/designationli")
    public ModelAndView DesignationListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            long sessUserID=0;
            String empType="";
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
                empType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            }
            
            
            List<DesignationDTO> desigLi = new ArrayList<>();
            
            if(empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN))
            {
                desigLi = desigService.getAllDesignations();
            }
            else
            {
                desigLi = desigService.getAllDesignationsbyEmployeeType(request);
            }
            
            modelView.addObject("desigLi", desigLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/DesignationList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/savedesig")
    public ModelAndView SaveDesignationAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("desigObj") DesignationDTO desigObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            int retVal = desigService.insertDesignationDetail(desigObj, request);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Designation  ("+desigObj.getDesignationName()+ ") added Successfully.");
                modelView.setViewName("redirect:/designationli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Designation with code '<strong>("
                        +desigObj.getDesignationCode()+ ")</strong>' or name '<strong>("
                        +desigObj.getDesignationName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/designationli.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/updatedesig")
    public ModelAndView UpdateDesignationAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("desigObj") DesignationDTO desigObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            int retVal = desigService.updateDesignationDetail(desigObj, request);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Designation  ("+desigObj.getDesignationName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/designationli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Designation with code '<strong>("
                        +desigObj.getDesignationCode()+ ")</strong>' or name '<strong>("
                        +desigObj.getDesignationName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/designationli.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
 
    
    @RequestMapping(value = "/editdesig", method = RequestMethod.GET)
    public @ResponseBody String EditDesignationAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("dsid") String encDesigId)
    {
        DesignationDTO desigObj=new DesignationDTO();
        try
        {
            desigObj = desigService.getSelectedDesignationByID(encDesigId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(desigObj);
    }
    
}
