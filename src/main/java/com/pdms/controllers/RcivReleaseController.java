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
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.RcivReleaseDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivAuthorisationItemsDTO;
import com.pdms.itemsDto.receipt.RcivReleaseTempDTO;
import com.pdms.masters.controllers.UserManagementController;
import com.pdms.service.impl.RcivAuthorisationServiceImpl;
import com.pdms.service.impl.RcivReleaseServiceImpl;
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
public class RcivReleaseController {
     private static final Logger log = Logger.getLogger(UserManagementController.class);

    /*
    Start : Autowiring of Fields
     */
    
    @Autowired
    private RcivReleaseServiceImpl rcivReleaseServiceImpl;
    
    @Autowired
    private RcivAuthorisationServiceImpl rcivAuthSerImpl;
    /*
    End : Autowiring of Fields
     */
 /*
        Default Constructor
     */
    public RcivReleaseController() {

    }

    /*
        Default Constructor
     */
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;  
     
    
   @RequestMapping(method = RequestMethod.POST, value = "/saveRcivReleaseData",consumes="application/json")
    @ResponseBody
    public String saveRcivReleaseData(HttpServletRequest request, @RequestBody RcivReleaseTempDTO rcivReleObj, ModelMap map,
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
            
            b = rcivReleaseServiceImpl.insertRcivRelease(rcivReleObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
                    
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRcivReleaseRecord")
    public @ResponseBody String getRcivReleaseRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<RcivReleaseDTO>  list = rcivReleaseServiceImpl.getRcivReleaseRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getRcivReleaseReById")
    public @ResponseBody String getRcivReleaseReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("ReleaseR_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<RcivReleaseDTO>  id_list = rcivReleaseServiceImpl.getRcivReleaseReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateRcivReleaseDe")
    @ResponseBody
    public String updateRcivReleaseDe(HttpServletRequest request, @RequestBody List<RcivReleaseDTO> rcivReleObj, ModelMap map,
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
            b = rcivReleaseServiceImpl.updateRcivReleaseDetail(rcivReleObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRcivReleaseItemsReByPo")
    public @ResponseBody String getRcivReleaseItemsReByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poNumber_IdR") long ponumber) throws AppException{
            Gson gson = new Gson();            
            List<RcivAuthorisationItemsDTO>  item_list = rcivAuthSerImpl.getRcivAuthorisaItemsByPoNo(ponumber);
            return gson.toJson(item_list);
    }
    
}
