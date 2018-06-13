/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.PreAuditFrDTO;
import com.pdms.account.dto.PreAuditSeUpDTO;
import com.pdms.account.service.impl.PreAuditServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.despatch.dto.DispatchEntryDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author myassessment
 */
@Controller
public class PreAuditController {
    private static final Logger logger = Logger.getLogger(PreAuditController.class);
    
    
    //Defult constructer
    public PreAuditController(){
        
    }
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private PreAuditServiceImpl preAuditServiceImpl;
    
    @RequestMapping(value = "/preAuditFiReEn")
    public ModelAndView preAuditFiReEnView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/PreAuditFiReEn");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }

    @RequestMapping(value = "/preAuditSendUpd")
    public ModelAndView preAuditSendUpdView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/PreAuditUpSenUpd");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getDispatchEnDe")
    public @ResponseBody String getDispatchEnDe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<DispatchEntryDTO>  dis_list = preAuditServiceImpl.getDispatchEnDetails();
            return gson.toJson(dis_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePreAuditFr")
    @ResponseBody
    public String savePreAuditFr(HttpServletRequest request, @RequestBody List<PreAuditFrDTO> preObj, ModelMap map,
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
            b = preAuditServiceImpl.insertPreAuditFrDetail(preObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getPreAuditFrRe")
    public @ResponseBody String getPreAuditFrRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PreAuditFrDTO>  pre_list = preAuditServiceImpl.getPreAuditFrDetails();
            return gson.toJson(pre_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePreAuditSeUp")
    @ResponseBody
    public String savePreAuditSeUp(HttpServletRequest request, @RequestBody List<PreAuditSeUpDTO> preseObj, ModelMap map,
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
            b = preAuditServiceImpl.insertPreAuditSeUpDetail(preseObj, sessUserID);
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
