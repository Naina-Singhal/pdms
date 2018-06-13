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
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.dto.RcivControlDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivControlTempDTO;
import com.pdms.itemsDto.receipt.RcivReleaseItemsDTO;
import com.pdms.masters.controllers.UserManagementController;
import com.pdms.service.impl.RcivAuthorisationServiceImpl;
import com.pdms.service.impl.RcivControlServiceImpl;
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
public class RcivControlController {
    private static final Logger log = Logger.getLogger(RcivControlController.class);

    /*
    Start : Autowiring of Fields
     */
    
    @Autowired
    private RcivControlServiceImpl rcivControlServiceImpl;
    
    @Autowired
    private RcivReleaseServiceImpl rcivReleSerImpl;
    /*
    End : Autowiring of Fields
     */
 /*
        Default Constructor
     */
    
    
    public RcivControlController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
           
    @RequestMapping(method = RequestMethod.POST, value = "/saveRcivControlData",consumes="application/json")
    @ResponseBody
    public String saveRcivControlData(HttpServletRequest request, @RequestBody RcivControlTempDTO rcivCtlObj, ModelMap map,
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
            
            b = rcivControlServiceImpl.insertRcivControlData(rcivCtlObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);            
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRcivControlRecord")
    public @ResponseBody String getRcivControlRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<RcivControlDTO>  list = rcivControlServiceImpl.getRcivControlRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getRcivControlReById")
    public @ResponseBody String getRcivControlReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Control_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<RcivControlDTO>  id_list = rcivControlServiceImpl.getRcivControlReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateRcivControlRe")
    @ResponseBody
    public String updateRcivControlRe(HttpServletRequest request, @RequestBody List<RcivControlDTO> rcivCtlObj, ModelMap map,
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
            b = rcivControlServiceImpl.updateRcivControlDetail(rcivCtlObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/generateControlNoIncrea")
    public @ResponseBody String generateControlNoIncrea(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  increament = rcivControlServiceImpl.getControlForIncrement();
            return gson.toJson(increament); 
    }
    
    @RequestMapping(value = "/getRcivControlReByConNo")
    public @ResponseBody String getRcivControlReByConNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Control_no") long conNo) throws AppException{
            Gson gson = new Gson();            
            List<RcivControlDTO>  id_list = rcivControlServiceImpl.getRcivControlReByControlNo(conNo);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(value = "/getRcivControlItemsReByPo")
    public @ResponseBody String getRcivControlItemsReByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poNumber_IdC") long poNumber) throws AppException{
            Gson gson = new Gson();            
            List<RcivReleaseItemsDTO>  id_list = rcivReleSerImpl.getRcivReleaseItemsByPoNo(poNumber);
            return gson.toJson(id_list);
    }
}
