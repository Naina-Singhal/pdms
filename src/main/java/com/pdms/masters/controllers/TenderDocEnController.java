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
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.TenderDocMaDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import com.pdms.master.service.impl.TenderDocEnServiceImpl;
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
public class TenderDocEnController {
    
    private static final Logger log = Logger.getLogger(TenderDocEnController.class);
    
    //Default constructor 
    public TenderDocEnController(){
        
    }
    
    @Autowired
    private TenderDocEnServiceImpl tenderDocEnServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
   
    @RequestMapping(value = "/tenderDocEn")
    public ModelAndView ibcNumberMaView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/TenderDocEn");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveTenderDocEn")
    @ResponseBody
    public String saveTenderDocEn(HttpServletRequest request, @RequestBody List<TenderDocMaDTO> tenObj, ModelMap map,
     HttpServletResponse response) {
            Gson gson = new Gson();            
            int tenRet = 0;           
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
            tenRet = tenderDocEnServiceImpl.insertTenderDocDetail(tenObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }        
        return gson.toJson(tenRet);
    }
    
     @RequestMapping(value = "/getTenderDocRe")
    public @ResponseBody String getTenderDocRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<TenderDocMaDTO>  ten_list = tenderDocEnServiceImpl.getTenderDocEnDetails();                      
            return gson.toJson(ten_list);
    }
    
    @RequestMapping(value = "/getTenderDocReById")
    public @ResponseBody String getTenderDocReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("tenderDoc_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<TenderDocMaDTO>  doc_id_list = tenderDocEnServiceImpl.getTenderDocReById(id);
            return gson.toJson(doc_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateTenderDocEn")
    @ResponseBody
    public String updateTenderDocEn(HttpServletRequest request, @RequestBody List<TenderDocMaDTO> tenObj, ModelMap map,
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
            b = tenderDocEnServiceImpl.updateTenderDocDetail(tenObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);            
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getTenderDocuDeByFile")
    public @ResponseBody String getTenderDocuDeByFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("FileNuM") long fileNo) throws AppException{
            Gson gson = new Gson();            
            List<TenderDocMaDTO>  doc_list = tenderDocEnServiceImpl.getTenderDomentsByFile(fileNo);
            return gson.toJson(doc_list);
    }
}
