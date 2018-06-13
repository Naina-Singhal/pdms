/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.account.controllers.TransferEntryController;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.MonthlyReportDTO;
import com.pdms.master.service.impl.MonthlyReportServiceImpl;
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
public class MonthlyReportController {
    private static final Logger log = Logger.getLogger(MonthlyReportController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private MonthlyReportServiceImpl monthlyServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public MonthlyReportController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/monthlyReportSc")
    public ModelAndView monthlyReportScreen(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/masters/MonthlyReportSc");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveMonthlyReport")
    @ResponseBody
    public String saveMonthlyReport(HttpServletRequest request, @RequestBody List<MonthlyReportDTO> monObj, ModelMap map,
     HttpServletResponse response) {
            int ibcRet = 0;           
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
            
            ibcRet = monthlyServiceImpl.insertMonthlyReportDetail(monObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/getMonthlyReport")
    public @ResponseBody String getMonthlyReport(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<MonthlyReportDTO>  list = monthlyServiceImpl.getMonthlyReportRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getMonthlyReportById")
    public @ResponseBody String getMonthlyReportById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("monthlyRe_Id") long id) throws AppException{
            Gson gson = new Gson();            
            List<MonthlyReportDTO>  id_list = monthlyServiceImpl.getMonthlyReportRecordById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateMonthlyReport")
    @ResponseBody
    public String updateMonthlyReport(HttpServletRequest request, @RequestBody List<MonthlyReportDTO> monObj, ModelMap map,
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
            b = monthlyServiceImpl.updateMonthlyReportDetail(monObj, sessUserID);
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
