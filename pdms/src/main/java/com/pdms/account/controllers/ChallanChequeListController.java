/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.ChallanListDTO;
import com.pdms.account.dto.ChequeListDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.account.service.impl.ChallanChequeListServiceImpl;
import com.pdms.account.service.impl.TransferEnScServiceImpl;
import com.pdms.account.service.impl.VoucherNoServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.POrderEntryDTO;
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
public class ChallanChequeListController {
    private static final Logger log = Logger.getLogger(ChallanChequeListController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private ChallanChequeListServiceImpl challanSerImpl;    
   
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public ChallanChequeListController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/challanChequeList")
    public ModelAndView challanChequeList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/accounts/ChallanChequeList");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getChallanNoDetails")
    public @ResponseBody String getChallanNoDetails(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "FromDate") String fromDate, @RequestParam(value = "ToDate") String toDate) throws AppException{
            Gson gson = new Gson();            
            List<ChallanEnFrCashDTO> cha = challanSerImpl.getOnlyChallnRecord(fromDate, toDate);                       
            return gson.toJson(cha);
    }
    
    @RequestMapping(value = "/getChequeNoDeList")
    public @ResponseBody String getChequeNoDeList(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "FromDate") String fromDate, @RequestParam(value = "ToDate") String toDate) throws AppException{
            Gson gson = new Gson();            
            List<VoucherNoDTO> cha = challanSerImpl.getOnlyChequeReFrVouNo(fromDate, toDate);
            return gson.toJson(cha);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveChallanOnlyList")
    @ResponseBody
    public String saveChallanOnlyList(HttpServletRequest request, @RequestBody List<ChallanListDTO> chaObj, ModelMap map,
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
            
            b = challanSerImpl.insertChallanList(chaObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveChequeOnlyList")
    @ResponseBody
    public String saveChequeOnlyList(HttpServletRequest request, @RequestBody List<ChequeListDTO> cheObj, ModelMap map,
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
            
            b = challanSerImpl.insertChequeList(cheObj, sessUserID);
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
