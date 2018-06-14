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
import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.master.service.impl.CurrencyServiceImpl;
import com.pdms.master.service.impl.PaymentServiceImpl;
import com.pdms.masters.controllers.PaymentController;
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
public class CurrencyController {
    
     private static final Logger log = Logger.getLogger(PaymentController.class);
    
    public CurrencyController(){
        
    }
    
    @Autowired
    private CurrencyServiceImpl currencyServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/currencyType")
    public ModelAndView currencyType(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/CurrencyType");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveCurrency")
    @ResponseBody
    public String saveCurrency(HttpServletRequest request, @RequestBody List<CurrencyDTO> curObj, ModelMap map,
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
            
            b = currencyServiceImpl.insertCurrencyDetail(curObj, sessUserID);
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
    @RequestMapping(value = "/getCurrencyDetails")
    public @ResponseBody String getCurrencyDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<CurrencyDTO>  cur_list = currencyServiceImpl.getCurrencyDetails();
            String json_list = gson.toJson(cur_list);   
            log.info("--currency obj---"+json_list);
            return json_list;
    }
    
    @RequestMapping(value = "/getCurrencyReById")
    public @ResponseBody String getCurrencyReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("cur_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<CurrencyDTO>  cur_id_list = currencyServiceImpl.getCurrencyReById(id);
            String json_id_list = gson.toJson(cur_id_list);   
            log.info("--currency obj---"+json_id_list);
            return json_id_list;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateCurrency")
    @ResponseBody
    public String updateCurrency(HttpServletRequest request, @RequestBody List<CurrencyDTO> curObj, ModelMap map,
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
            b = currencyServiceImpl.updateCurrencyDetail(curObj, sessUserID);
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
