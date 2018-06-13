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
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.dto.SignatoryDTO;
import com.pdms.master.service.impl.RtgsServiceImpl;
import com.pdms.master.service.impl.SignatoryServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.simple.JSONObject;
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
public class RtgsController {
    private static final Logger log = Logger.getLogger(RtgsController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private RtgsServiceImpl rtgsServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public RtgsController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/rtgsPage")
    public ModelAndView rtgsPageView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/RtgsView");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveRtgs")
    @ResponseBody
    public String saveRtgs(HttpServletRequest request, @RequestBody List<RtgsDTO> rtgsObj, ModelMap map,
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
            
            b = rtgsServiceImpl.insertRtgsDetail(rtgsObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRtgsDetails")
    public @ResponseBody String getRtgsDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<RtgsDTO>  rtgs_list = rtgsServiceImpl.getRtgsDetails();
            return gson.toJson(rtgs_list);
    }
    
    @RequestMapping(value = "/getRtgsReById")
    public @ResponseBody String getRtgsReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("rtgs_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<RtgsDTO>  rt_id_list = rtgsServiceImpl.getRtgsReById(id);
            return gson.toJson(rt_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateRtgs")
    @ResponseBody
    public String updateRtgs(HttpServletRequest request, @RequestBody List<RtgsDTO> rtgsObj, ModelMap map,
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
            b = rtgsServiceImpl.updateRtgsDetail(rtgsObj, sessUserID);            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRtgsNoIncre")
    public @ResponseBody String getRtgsNoAutoIncre(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  rtgs_no = rtgsServiceImpl.getRtgsNoFrIncre();
            return gson.toJson(rtgs_no);
    }
}
