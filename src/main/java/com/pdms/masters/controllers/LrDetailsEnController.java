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
import com.pdms.dto.LrEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
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
 * @author myassessment
 */
@Controller
public class LrDetailsEnController {
    
    private static final Logger log = Logger.getLogger(LrDetailsEnController.class);
    
    //Default constructor 
    public LrDetailsEnController(){
        
    }
    
    @Autowired
    private LrDetailsEnServiceImpl lrDetailsEnServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
            
    @RequestMapping(value = "/lrDetailsEn")
    public ModelAndView lrDetailsEntry(HttpServletRequest request, HttpServletResponse response) {            
            ModelAndView modelView = new ModelAndView();            
            try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/LrDetailsEnView"); 
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
            return modelView;
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveLRDataNO")
    @ResponseBody
    public String saveLRDataNO(HttpServletRequest request, @RequestBody List<LrEntryDTO> lrObj, ModelMap map,
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
            
            lrRet = lrDetailsEnServiceImpl.insertLRNoDetail(lrObj, sessUserID);            
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
    
    @RequestMapping(value = "/getLrDetailsRe")
    public @ResponseBody String getLrDetailsRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<LrEntryDTO>  des_list = lrDetailsEnServiceImpl.getLrEntryDetails();
            String json_list = gson.toJson(des_list);               
            return json_list;
    }
    
    @RequestMapping(value = "/getLrDetailsReById")
    public @ResponseBody String getLrDetailsReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("LrEntry_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<LrEntryDTO>  lr_id_list = lrDetailsEnServiceImpl.getLrDetailsReById(id);
            return gson.toJson(lr_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateLrDetails")
    @ResponseBody
    public String updateLrDetails(HttpServletRequest request, @RequestBody List<LrEntryDTO> LrObj, ModelMap map,
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
            b = lrDetailsEnServiceImpl.updateLeDetailsDetail(LrObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getLrDetailsByPoNo")
    public @ResponseBody String getLrDetailsByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PONOOmber") long poNo) throws AppException{
            Gson gson = new Gson();            
            List<LrEntryDTO>  lr_id_list = lrDetailsEnServiceImpl.getLrDetailsReByPONO(poNo);
            return gson.toJson(lr_id_list);
    }
    
    @RequestMapping(value = "/LcForwardingMasPdf")
    public ModelAndView LcForwardingMasPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("lcforid") long lcforid) {
        try {
            List<LrEntryDTO> secuObj = lrDetailsEnServiceImpl.getLrDeByPoFrPdf(lcforid);

            return new ModelAndView("lcForwardingPDFView", "lrObj", secuObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "LcForwardingMasPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
