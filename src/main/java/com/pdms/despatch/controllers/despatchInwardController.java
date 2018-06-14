/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.despatch.dto.InwardDTO;
import com.pdms.despatch.itemsDto.InwardTempDTO;
import com.pdms.despatch.service.serviceImpl.InwardServiceImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorIndentServiceImpl;
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
public class despatchInwardController {
     private static final Logger logger = Logger.getLogger(despatchInwardController.class);
    
    
    //Defult constructer
    public despatchInwardController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private InwardServiceImpl inwardServiceImpl;
    
    @Autowired
    private VendorIndentServiceImpl vendorIndentServiceImpl;
    
    @RequestMapping(value = "/dispatchInwardEn")
    public ModelAndView dispatchInwardEn(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/despatch/DespatchInwardEn");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savedInward",consumes="application/json")
    @ResponseBody
    public String savedInward(HttpServletRequest request, @RequestBody InwardTempDTO inObj, ModelMap map,
     HttpServletResponse response) {
            int Ret = 0;           
            HttpSession session = request.getSession();
            Gson gson = new Gson();
        try
        {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            
            Ret = inwardServiceImpl.insertInwardDetails(inObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(Ret);
    }
    
    @RequestMapping(value = "/getInwardRecord")
    public @ResponseBody String getInwardRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<InwardDTO>  list = inwardServiceImpl.getInwardRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getInwardReById")
    public @ResponseBody String getInwardReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("inward_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<InwardDTO>  id_list = inwardServiceImpl.getInwardReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateInwardDetails")
    @ResponseBody
    public String updateInwardDetails(HttpServletRequest request, @RequestBody List<InwardDTO> inObj, ModelMap map,
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
            b = inwardServiceImpl.updateInwardDetail(inObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getSlNoInwaIncreament")
    public @ResponseBody String getSlNoInwardIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  SlNoIncre = inwardServiceImpl.getSlNoInwardForIncrem();
            return gson.toJson(SlNoIncre); 
    }
    
    @RequestMapping(value = "/getVenIndMappDeByFile")
    public @ResponseBody String getVenIndMappDeByFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file_no") long FileNumber) throws AppException{
            Gson gson = new Gson();            
            List<VendorItemsDTO>  mapp_list = vendorIndentServiceImpl.getVenItemMappingByfileNo(FileNumber);
            return gson.toJson(mapp_list);
    }
}
