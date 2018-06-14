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
import com.pdms.master.dto.HoaCodesDTO;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.HoaCodesServiceImpl;
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
public class HoaCodesController {
    private static final Logger log = Logger.getLogger(HoaCodesController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private HoaCodesServiceImpl hoaCodesServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public HoaCodesController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/headOfAcCodes")
    public ModelAndView headOfAcCodes(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/HeadOfAcCodes");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveHoaCodes")
    @ResponseBody
    public String saveHoaCodes(HttpServletRequest request, @RequestBody List<HoaCodesDTO> hoaObj, ModelMap map,
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
            
            b = hoaCodesServiceImpl.insertHoaCodesDetail(hoaObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getHoaCodesDetails")
    public @ResponseBody String getHoaCodesDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<HoaCodesDTO>  hoac_list = hoaCodesServiceImpl.getHoaShortCodesDetails();
            return gson.toJson(hoac_list);
    }
    
    @RequestMapping(value = "/getHoacCodesReById")
    public @ResponseBody String getHoacCodesReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("HoaCode_id") long hoaid) throws AppException{
            Gson gson = new Gson();            
            List<HoaCodesDTO>  hoa_id_list = hoaCodesServiceImpl.getHoaCodesReById(hoaid);
            return gson.toJson(hoa_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateHoaShortCodes")
    @ResponseBody
    public String updateHoaShortCodes(HttpServletRequest request, @RequestBody List<HoaCodesDTO> hoaObj, ModelMap map,
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
            b = hoaCodesServiceImpl.updateHoaCodesDetails(hoaObj, sessUserID);
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
