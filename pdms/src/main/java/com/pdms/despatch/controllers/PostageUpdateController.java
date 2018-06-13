/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.despatch.dto.PostageCloseDTO;
import com.pdms.despatch.dto.PostageUpDTO;
import com.pdms.despatch.itemsDto.PostageItemsDTO;
import com.pdms.despatch.itemsDto.PostageTempDTO;
import com.pdms.despatch.service.serviceImpl.PostageUpServiceImpl;
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
public class PostageUpdateController {
    private static final Logger logger = Logger.getLogger(PostageUpdateController.class);
    
    
    //Defult constructer
    public PostageUpdateController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private PostageUpServiceImpl postServiceImpl;
    
    
    @RequestMapping(value = "/postageUpdate")
    public ModelAndView postageUpdate(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/despatch/PostageUpdate");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePostageData",consumes="application/json")
    @ResponseBody
    public String savePostageData(HttpServletRequest request, @RequestBody PostageTempDTO upObj, ModelMap map,
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
            
            ibcRet = postServiceImpl.insertPostageUpDetails(upObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/getPostageUpRecord")
    public @ResponseBody String getPostageUpRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PostageUpDTO>  list = postServiceImpl.getPostageUpRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPostageReById")
    public @ResponseBody String getPostageReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("postage_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<PostageUpDTO>  id_list = postServiceImpl.getPostageReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updatePostageRe")
    @ResponseBody
    public String updatePostageRe(HttpServletRequest request, @RequestBody List<PostageUpDTO> upObj, ModelMap map,
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
            b = postServiceImpl.updatePostageDetail(upObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveCloseButton")
    @ResponseBody
    public String saveCloseButton(HttpServletRequest request, @RequestBody List<PostageCloseDTO> closeObj, ModelMap map,
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
            
            ibcRet = postServiceImpl.saveCloseButton(closeObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/postageDetailsPdf")
    public ModelAndView postageDetailsPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("postagedate") String postageDate) {
        try {
            ModelAndView modelView = new ModelAndView();
            List<PostageItemsDTO> postaggeObj = postServiceImpl.getPostagePdfByDate(postageDate);
            
            //return new ModelAndView("postageEntryPDFView", "postageObj", postaggeObj);
            
            modelView.addObject("selectDate", postageDate);
            modelView.addObject("postageObj", postaggeObj);
            modelView.setViewName("postageEntryPDFView");
            return modelView;
            
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "postageDetailsPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
