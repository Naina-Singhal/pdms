/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.ChequeDdEntryDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.service.impl.ChequeDdServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.UserPermissionDTO;
import com.pdms.exception.AppException;
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
public class ChequeAndDdController {
    
    private static final Logger logger = Logger.getLogger(ChequeAndDdController.class);
    
    
    //Defult constructer
    public ChequeAndDdController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private ChequeDdServiceImpl serviceImpl;
    
    @RequestMapping(value = "/chequeAndDdEn")
    public ModelAndView chequeAndDdEnView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/ChequeAndDdEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveChequeDdEntry")
    @ResponseBody
    public String saveChequeDdEntry(HttpServletRequest request, @RequestBody List<ChequeDdEntryDTO> ddObj, ModelMap map,
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
            
            b = serviceImpl.insertChequeDdEntry(ddObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getChequeAndDdRecord")
    public @ResponseBody String getChequeAndDdRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<ChequeDdEntryDTO>  list = serviceImpl.getChequeDdDaRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getChequeAndDdrReById")
    public @ResponseBody String getChequeAndDdrReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("chedd_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<ChequeDdEntryDTO>  id_list = serviceImpl.getChequeAndDdReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateChequeDdDetails")
    @ResponseBody
    public String updateChequeDdDetails(HttpServletRequest request, @RequestBody List<ChequeDdEntryDTO> ddObj, ModelMap map,
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
            b = serviceImpl.updateChequeAndDdDetail(ddObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getReceiptNoIncr")
    public @ResponseBody String getReceiptNoIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  incre = serviceImpl.getReceiptNoForIncrement();            
            return gson.toJson(incre);
    }
    
    @RequestMapping(value = "/getChallanNoIncr")
    public @ResponseBody String getChallanNoIncr(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  cha = serviceImpl.getChallanNoForIncrement();
            return gson.toJson(cha);
    }
    
    @RequestMapping(value = "/getSerialNoIncr")
    public @ResponseBody String getSerialNoIncr(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  slno = serviceImpl.getSlNoForIncrement();
            return gson.toJson(slno);
    }
    
    @RequestMapping(value = "/challanEmdPdfDown")
    public ModelAndView challanEmdPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<ChequeDdEntryDTO> emdObj = serviceImpl.getChequeAndDdReById(encIndId);

            return new ModelAndView("challanEmdPDFView", "challEmdObj", emdObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/chequeForwdPdfDown")
    public ModelAndView chequeForwdPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            //List<ChequeDdEntryDTO> emdObj = serviceImpl.getChequeAndDdReById(encIndId);

            return new ModelAndView("chequeForwdPDFView", "chequeObj", null);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "chequeForwdPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
