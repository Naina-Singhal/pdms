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
import com.pdms.dto.PaymentDTO;
import com.pdms.dto.TransporterDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.master.service.impl.PaymentServiceImpl;
import com.pdms.master.service.impl.TransporterServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
public class TransporterController {
    
    private static final Logger log = Logger.getLogger(PaymentController.class);
    
    public TransporterController(){
        
    }
    
    @Autowired
    private TransporterServiceImpl transporterServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/nameOfTransporter")
    public ModelAndView paymentMasterView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/TransporterView");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveTransporter")
    @ResponseBody
    public String savePOEntryDetails(HttpServletRequest request, @RequestBody List<TransporterDTO> transObj, ModelMap map,
     HttpServletResponse response) {
            
            String a = null;
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
            
            b = transporterServiceImpl.insertTransporterData(transObj, sessUserID);
            log.info("---info---"+b);
            Gson gson = new Gson();
            a = gson.toJson(b);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return a;
    }
    
    @RequestMapping(value = "/getTransportDetails")
    public @ResponseBody String getTransportDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            log.info("----payment record----");
            Gson gson = new Gson();            
            List<TransporterDTO>  tra_list = transporterServiceImpl.getTransportRecord();
            String tr_list = gson.toJson(tra_list);   
            log.info("--po no---"+tr_list);
            return tr_list;
    }
    
    @RequestMapping(value = "/getTranspoReById")
    public @ResponseBody String getCurrencyReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("transpo_id") long trans_id) throws AppException{
            Gson gson = new Gson();            
            List<TransporterDTO>  tra_id_list = transporterServiceImpl.getTransporterReById(trans_id);
            log.info("--currency obj---"+gson.toJson(tra_id_list));
            return gson.toJson(tra_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateTransporter")
    @ResponseBody
    public String updateTransporter(HttpServletRequest request, @RequestBody List<TransporterDTO> tranObj, ModelMap map,
     HttpServletResponse response) {
            Gson gson = new Gson();            
            long b = 0;           
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
            b = transporterServiceImpl.updateTransporterDetail(tranObj, sessUserID);            
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
