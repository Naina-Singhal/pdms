/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.pdms.dto.ExceptionDTO;
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
 * @author myassessment
 */
@Controller
public class ChemicalDaEnController {
    
    private static final Logger logger = Logger.getLogger(ChemicalDaEnController.class);
    
    
    //Defult constructer
    public ChemicalDaEnController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/chemicalDaEn")
    public ModelAndView chemicalDaEnView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/ChemicalDaEn");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
}
