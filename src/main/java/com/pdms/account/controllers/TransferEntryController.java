/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.TransferEntryDTO;
import com.pdms.account.service.impl.TransferEnScServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.RtgsServiceImpl;
import com.pdms.masters.controllers.RtgsController;
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
public class TransferEntryController {
    private static final Logger log = Logger.getLogger(TransferEntryController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private TransferEnScServiceImpl trEnScServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public TransferEntryController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/transferEnScreen")
    public ModelAndView transferEnScreen(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/accounts/TransferEnScreen");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savedTransferEnDeAc")
    @ResponseBody
    public String savedTransferEnDeAc(HttpServletRequest request, @RequestBody List<TransferEntryDTO> traObj, ModelMap map,
     HttpServletResponse response) {
            int RetVal = 0;           
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
            
            RetVal = trEnScServiceImpl.insertTransferEnScDetails(traObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(RetVal);
    }
    
    @RequestMapping(value = "/getTransferEnRecord")
    public @ResponseBody String getTransferEnRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<TransferEntryDTO>  list = trEnScServiceImpl.getTransferEnScRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getTransferEnReById")
    public @ResponseBody String getTransferEnReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("transferEn_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<TransferEntryDTO>  id_list = trEnScServiceImpl.getTransferEnScReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateTransferEnScAc")
    @ResponseBody
    public String updateTransferEnScAc(HttpServletRequest request, @RequestBody List<TransferEntryDTO> traObj, ModelMap map,
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
            b = trEnScServiceImpl.updateTransferEnScDetail(traObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getTeNumIncreament")
    public @ResponseBody String getTeNumIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  teIncre = trEnScServiceImpl.getTENumForIncrement();
            return gson.toJson(teIncre); 
    }
    
    @RequestMapping(value = "/transferEntryPdf")
    public ModelAndView transferEntryPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<TransferEntryDTO> transferObj = trEnScServiceImpl.getTransferEnPdfReById(encIndId);
            return new ModelAndView("transferEnPDFView", "traObj", transferObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "transferEntryPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
