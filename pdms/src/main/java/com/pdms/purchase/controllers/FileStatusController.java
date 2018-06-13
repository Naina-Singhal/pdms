/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.controllers;

import com.google.gson.Gson;
import com.pdms.account.controllers.TransferEntryController;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author myassessment
 */
@Controller
public class FileStatusController {
    private static final Logger log = Logger.getLogger(FileStatusController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private IndentServiceImpl indentServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public FileStatusController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/statusScreen")
    public ModelAndView statusScreen(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/purchase/FileStatus");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getIndentFormDeByIndId")
    public @ResponseBody String getIndentFormDeByIndId(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("IndentFormId") long indentFormId) throws AppException{
            Gson gson = new Gson();            
            List<IndentFormDTO> list = indentServiceImpl.getIndentFormDeByIndentId(indentFormId);
            return gson.toJson(list);
    }
}
