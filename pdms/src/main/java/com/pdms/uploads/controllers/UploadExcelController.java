/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.uploads.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.uploads.dto.ExcelUploadDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.uploads.service.Impl.UploadExcelServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author myassessment
 */
@Controller
public class UploadExcelController {
    
     private static final Logger logger = Logger.getLogger(UploadExcelController.class);
    
    
    //Defult constructer
    public UploadExcelController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private UploadExcelServiceImpl uploadExcelServiceImpl;
    
    @RequestMapping(value = "/transferEntry")
    public ModelAndView transferEntryView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/TransferEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/uploadaaa", method = RequestMethod.POST)
    @ResponseBody
    public String uploadaaa(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("excelfile") MultipartFile excelfile, Model model) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        int retValue = 0;
        String a = null;
        try {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            retValue = uploadExcelServiceImpl.getUploadExcelList(excelfile, sessUserID);
            logger.info("-----retValue------"+retValue);
            //modelView.addObject("SuccessRetVal", retValue);            
            //modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            //modelView.setViewName("/accounts/TransferEntry");
            Gson gson = new Gson();
            a = gson.toJson(retValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }
    
    @RequestMapping(value = "/uploadXlsx", method = RequestMethod.POST)
    @ResponseBody
    public String uploadXlsx(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("xlsxfile") MultipartFile xlsxfile, Model model) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        int retValue = 0;
        String a = null;
        try {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            retValue = uploadExcelServiceImpl.getUploadExcelXlsxList(xlsxfile, sessUserID);
            logger.info("-----retValue2------"+retValue);
            //modelView.addObject("SuccessRetVal", retValue);            
            //modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            //modelView.setViewName("/accounts/TransferEntry");
            Gson gson = new Gson();
            a = gson.toJson(retValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }
}
