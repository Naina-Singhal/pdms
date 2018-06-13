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
import com.pdms.dto.LrEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.LrEnDeFrStoresDTO;
import com.pdms.master.service.impl.LrDeEnStoresServiceImpl;
import com.pdms.master.service.impl.LrDetailsEnServiceImpl;
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
 * @author naagu
 */
@Controller
public class LrEnDeForStoresController {
    
    private static final Logger log = Logger.getLogger(LrEnDeForStoresController.class);
    
    //Default constructor 
    public LrEnDeForStoresController(){
        
    }
    
    @Autowired
    private LrDeEnStoresServiceImpl lrSerServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
            
    @RequestMapping(value = "/lrDeEnForStores")
    public ModelAndView lrDeEnForStores(HttpServletRequest request, HttpServletResponse response) {            
            ModelAndView modelView = new ModelAndView();            
            try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/LrDeEnFrStores"); 
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
            return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveLRStoresData")
    @ResponseBody
    public String saveLRStoresData(HttpServletRequest request, @RequestBody List<LrEnDeFrStoresDTO> lrObj, ModelMap map,
     HttpServletResponse response) {            
            String ibcJSOn = null;
            int lrRet = 0;           
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
            
            lrRet = lrSerServiceImpl.insertLRNoStoresDetail(lrObj, sessUserID);
            Gson gson = new Gson();
            ibcJSOn = gson.toJson(lrRet);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return ibcJSOn;
    }
    
    @RequestMapping(value = "/getLrStoresDetailsRe")
    public @ResponseBody String getLrStoresDetailsRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<LrEnDeFrStoresDTO>  des_list = lrSerServiceImpl.getLrEntryStoresDetails();
            String json_list = gson.toJson(des_list);               
            return json_list;
    }
    
    @RequestMapping(value = "/getLrStoresDetailsReById")
    public @ResponseBody String getLrStoresDetailsReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("LrStores_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<LrEnDeFrStoresDTO>  lr_list = lrSerServiceImpl.getLrStoresDetailsReById(id);
            return gson.toJson(lr_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateLrstoresDetails")
    @ResponseBody
    public String updateLrstoresDetails(HttpServletRequest request, @RequestBody List<LrEnDeFrStoresDTO> LrObj, ModelMap map,
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
            b = lrSerServiceImpl.updateLrStoresDetailsDetail(LrObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getLrStoresDeByPoNum")
    public @ResponseBody String getLrStoresDeByPoNum(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("LrStores_po") long ponumber) throws AppException{
            Gson gson = new Gson();            
            List<LrEnDeFrStoresDTO>  lr_list = lrSerServiceImpl.getLrStoresReByPoNo(ponumber);
            return gson.toJson(lr_list);
    }
    
}
