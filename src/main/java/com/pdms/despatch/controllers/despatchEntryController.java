/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.controllers;

import com.google.gson.Gson;
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import com.pdms.account.controllers.VoucherNoController;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.service.impl.VoucherNoServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.despatch.dto.DispatchEntryDTO;
import com.pdms.despatch.service.serviceImpl.DispatchEntryServiceImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
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
public class despatchEntryController {
    private static final Logger logger = Logger.getLogger(despatchEntryController.class);
    
    
    //Defult constructer
    public despatchEntryController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private DispatchEntryServiceImpl disEnServImpl;
    
    
    
    @RequestMapping(value = "/dispatchEntry")
    public ModelAndView dispatchEntry(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/despatch/FileMovement");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveDispatchEn")
    @ResponseBody
    public String saveDispatchEn(HttpServletRequest request, @RequestBody List<DispatchEntryDTO> disObj, ModelMap map,
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
            
            b = disEnServImpl.insertDispatchEnDetail(disObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);           
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getFileMovementRecord")
    public @ResponseBody String getFileMovementRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<DispatchEntryDTO>  list = disEnServImpl.getFileMovementRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getFileMovementReById")
    public @ResponseBody String getFileMovementReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("movenment_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<DispatchEntryDTO>  id_list = disEnServImpl.getFileMovementReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateFileMovement")
    @ResponseBody
    public String updateFileMovement(HttpServletRequest request, @RequestBody List<DispatchEntryDTO> disObj, ModelMap map,
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
            b = disEnServImpl.updateFileMovementDetail(disObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/fileMoveMentPdf")
    public ModelAndView fileMoveMentPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("receiver") String receiver, @RequestParam("dateAc") String dateAc) {
        try {
            //List<VouNoHoaItemsDTO> secuObj = voucherNoServiceImpl.getVoucherNoItemsByHoaPdf(headOfAccount, dateAc);

            return new ModelAndView("fileMoveMentPDFView", "fileObj", null);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "fileMoveMentPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
