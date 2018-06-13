/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.BankMasterDTO;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.BankMasterServiceImpl;
import com.pdms.master.service.impl.RtgsServiceImpl;
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
public class BankMasterController {
     private static final Logger log = Logger.getLogger(BankMasterController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private BankMasterServiceImpl bankMasterServiceImpl;
    
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public BankMasterController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/bankMaster")
    public ModelAndView rtgsPageView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/Bank");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveBank")
    @ResponseBody
    public String saveBank(HttpServletRequest request, @RequestBody List<BankMasterDTO> bankObj, ModelMap map,
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
            
            b = bankMasterServiceImpl.insertBankDetail(bankObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getBankMaRecord")
    public @ResponseBody String getBankMaRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<BankMasterDTO>  bank_list = bankMasterServiceImpl.getBankMasterDetails();
            return gson.toJson(bank_list);
    }
    
    @RequestMapping(value = "/getBankReById")
    public @ResponseBody String getBankReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("bank_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<BankMasterDTO>  editList = bankMasterServiceImpl.getBankReById(id);
            return gson.toJson(editList);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateBankMaster")
    @ResponseBody
    public String updateBankMaster(HttpServletRequest request, @RequestBody List<BankMasterDTO> bankObj, ModelMap map,
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
            b = bankMasterServiceImpl.updateBankDetail(bankObj, sessUserID);
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
