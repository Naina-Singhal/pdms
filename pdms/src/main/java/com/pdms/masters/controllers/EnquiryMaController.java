/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.account.controllers.TransferEntryController;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.service.impl.EnquiryMaServiceImpl;
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
public class EnquiryMaController {
    private static final Logger log = Logger.getLogger(EnquiryMaController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private EnquiryMaServiceImpl enqServiceImpl;
    
    
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public EnquiryMaController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/enquiryForm")
    public ModelAndView enquiryForm(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/EnquiryForm");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveEnquiryForm")
    @ResponseBody
    public String saveEnquiryForm(HttpServletRequest request, @RequestBody List<EnquiryDTO> enqObj, ModelMap map,
     HttpServletResponse response) {
            int ibcRet = 0;           
            HttpSession session = request.getSession();
            Gson gson = new Gson();
        try
        {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            
            ibcRet = enqServiceImpl.insertEnquiryDetail(enqObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/getEnquiryRecord")
    public @ResponseBody String getEnquiryRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<EnquiryDTO>  list = enqServiceImpl.getEnquiryRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getEnquiryReById")
    public @ResponseBody String getEnquiryReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("enquiry_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<EnquiryDTO>  id_list = enqServiceImpl.getEnquiryRecordById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateEnquiryMaster")
    @ResponseBody
    public String updateEnquiryMaster(HttpServletRequest request, @RequestBody List<EnquiryDTO> enqObj, ModelMap map,
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
            b = enqServiceImpl.updateEnquiryDetail(enqObj, sessUserID);
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
