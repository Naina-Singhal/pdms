/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.account.controllers.BillEntryScreenController;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.master.dto.SignatoryDTO;
import com.pdms.master.service.impl.SignatoryServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author myassessment
 */
@Controller
public class SignatoryController {
    private static final Logger log = Logger.getLogger(SignatoryController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private SignatoryServiceImpl serviceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public SignatoryController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/signatory")
    public ModelAndView signatoryView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/Singnatory");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveSignatory")
    @ResponseBody
    public String saveSignatory(HttpServletRequest request, @RequestBody List<SignatoryDTO> sinObj, ModelMap map,
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
            
            b = serviceImpl.insertSignatoryDetail(sinObj, sessUserID);            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getSignatoryDetails")
    public @ResponseBody String getSignatoryDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<SignatoryDTO>  cur_list = serviceImpl.getSignatoryDetails(); 
            return gson.toJson(cur_list);
    }
    
    @RequestMapping(value = "/getSignatoryReById")
    public @ResponseBody String getSignatoryReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("sign_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<SignatoryDTO>  cur_id_list = serviceImpl.getSignatoryReById(id);            
            return gson.toJson(cur_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateSignatory")
    @ResponseBody
    public String updateSignatory(HttpServletRequest request, @RequestBody List<SignatoryDTO> signObj, ModelMap map,
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
            b = serviceImpl.updateSignatoryDetail(signObj, sessUserID);
            log.info("---info---"+b);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
}
