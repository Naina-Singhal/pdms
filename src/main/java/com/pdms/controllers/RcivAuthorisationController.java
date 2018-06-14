/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.RcivAuthorisationTempDTO;
import com.pdms.masters.controllers.UserManagementController;
import com.pdms.service.impl.CsrvPreparationServiceImpl;
import com.pdms.service.impl.RcivAuthorisationServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author STEINMETZ
 */
@Controller
public class RcivAuthorisationController {
    private static final Logger log = Logger.getLogger(UserManagementController.class);

    /*
    Start : Autowiring of Fields
     */
    
    @Autowired
    private RcivAuthorisationServiceImpl rcivAuthorisationServiceImpl;
    
    @Autowired
    private CsrvPreparationServiceImpl csrvSerImpl;
    /*
    End : Autowiring of Fields
     */
 /*
        Default Constructor
     */
    public RcivAuthorisationController() {

    }

    /*
        Default Constructor
     */
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
      
    @RequestMapping(value = "/rcivAuthorisation")
    public ModelAndView rcivAuthorisationView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/RCIVauthorisation");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
         
    @RequestMapping(method = RequestMethod.POST, value = "/saveRcivAuthorisition",consumes="application/json")
    @ResponseBody
    public String saveRcivAuthorisition(HttpServletRequest request, @RequestBody RcivAuthorisationTempDTO rcivAuthObj, ModelMap map,
     HttpServletResponse response) {
            Gson gson = new Gson();            
            int b = 0;           
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
            
            b = rcivAuthorisationServiceImpl.insertRcivAuthorisation(rcivAuthObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);            
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRcivAuthorizationRecord")
    public @ResponseBody String getRcivAuthorizationRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<RcivAuthorisationDTO>  list = rcivAuthorisationServiceImpl.getRcivAuthoriRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getRcivAuthorisationReById")
    public @ResponseBody String getRcivAuthorisationReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Authori_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<RcivAuthorisationDTO>  id_list = rcivAuthorisationServiceImpl.getRcivAuthorisationReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateRcivAuthorisation")
    @ResponseBody
    public String updateRcivAuthorisation(HttpServletRequest request, @RequestBody List<RcivAuthorisationDTO> rcivAuthObj, ModelMap map,
     HttpServletResponse response) {
            Gson gson = new Gson();            
            int b = 0;           
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
            b = rcivAuthorisationServiceImpl.updateRcivAuthorisationDetail(rcivAuthObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getCsrvPreparItemsByPoNo")
    public @ResponseBody String getCsrvPreparItemsByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poNumber_Id") long poNumber) throws AppException{
            Gson gson = new Gson();            
            List<CsrvPreparationItemsDTO>  list = csrvSerImpl.getCsrvPreparaItemsReByPo(poNumber);
            return gson.toJson(list);
    }
    
}
