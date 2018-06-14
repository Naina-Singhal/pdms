/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.controllers;

import com.pdms.dto.ExceptionDTO;
import com.pdms.purchase.services.Impl.AmendmentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author naagu
 */
@Controller
public class VendorPerformanceController {
    private static final Logger log = Logger.getLogger(VendorPerformanceController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    
    
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public VendorPerformanceController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/vendorPerformance")
    public ModelAndView vendorPerformance(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/purchase/VendorPerformance");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
}
