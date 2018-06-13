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
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.SignatoryDTO;
import com.pdms.master.service.impl.PaymentServiceImpl;
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
public class PaymentController {
    
    private static final Logger log = Logger.getLogger(PaymentController.class);
    
    public PaymentController(){
        
    }
    
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/paymentMaster")
    public ModelAndView paymentMasterView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/PaymentMaster");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePayment")
    @ResponseBody
    public String savePOEntryDetails(HttpServletRequest request, @RequestBody List<PaymentDTO> payObj, ModelMap map,
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
            
            b = paymentServiceImpl.insertPaymentDetail(payObj, sessUserID);
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
    
    
    @RequestMapping(value = "/getPaymentDetails")
    public @ResponseBody String getPaymentDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            log.info("----payment record----");
            Gson gson = new Gson();            
            List<PaymentDTO>  payment_list = paymentServiceImpl.getPaymentDetails();
            String json_list = gson.toJson(payment_list);   
            log.info("--po no---"+json_list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPaymentReById")
    public @ResponseBody String getPaymentReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("payment_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<PaymentDTO>  pay_id_list = paymentServiceImpl.getPaymentReById(id);
            return gson.toJson(pay_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updatePayment")
    @ResponseBody
    public String updatePayment(HttpServletRequest request, @RequestBody List<PaymentDTO> payObj, ModelMap map,
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
            b = paymentServiceImpl.updatePaymentDetail(payObj, sessUserID);           
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
