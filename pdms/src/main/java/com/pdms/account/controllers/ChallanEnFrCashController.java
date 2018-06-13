/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.service.impl.ChallanEnFrCashServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
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
public class ChallanEnFrCashController {
    
    
    private static final Logger logger = Logger.getLogger(ChallanEnFrCashController.class);
    
    
    //Defult constructer
    public ChallanEnFrCashController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private ChallanEnFrCashServiceImpl cashServiceImpl;
    
    @RequestMapping(value = "/challanEnForCash")
    public ModelAndView challanEnForCashView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/ChallanEnFrCash");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveChallanEntry")
    @ResponseBody
    public String saveChallanEntryForCash(HttpServletRequest request, @RequestBody List<ChallanEnFrCashDTO> chaObj, ModelMap map,
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
            
            b = cashServiceImpl.insertChallanEntryCash(chaObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getChallanEnFrCashRecord")
    public @ResponseBody String getChallanEnFrCashRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<ChallanEnFrCashDTO>  list = cashServiceImpl.getChallanEnFrCashDaRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getChallanEnFrCashReById")
    public @ResponseBody String getChallanEnFrCashReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("challanEn_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<ChallanEnFrCashDTO>  id_list = cashServiceImpl.getChallanEnCashReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateChallanEnFrDe")
    @ResponseBody
    public String updateChallanEnFrDe(HttpServletRequest request, @RequestBody List<ChallanEnFrCashDTO> chaObj, ModelMap map,
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
            b = cashServiceImpl.updateChallanEnFrCashDetail(chaObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getChalCashNoIncr")
    public @ResponseBody String getChalCashNoIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  cha = cashServiceImpl.getChalNoEnFrCashForIncrement();
            return gson.toJson(cha);
    }
    
    @RequestMapping(value = "/challanCashPdfDown")
    public ModelAndView challanCashPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<ChallanEnFrCashDTO> indentObj = cashServiceImpl.getChallanEnCashReById(encIndId);

            return new ModelAndView("challanCashPDFView", "challanObj", indentObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
