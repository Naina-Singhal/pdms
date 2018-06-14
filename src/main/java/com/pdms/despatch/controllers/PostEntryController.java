/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.controllers;

import com.google.gson.Gson;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.despatch.dto.PostEntryDTO;
import com.pdms.despatch.itemsDto.PostEntryTempDTO;
import com.pdms.despatch.itemsDto.PostVenItemsDTO;
import com.pdms.despatch.service.serviceImpl.PostEntryServiceImpl;
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
public class PostEntryController {
    private static final Logger logger = Logger.getLogger(PostEntryController.class);
    
    
    //Defult constructer
    public PostEntryController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private PostEntryServiceImpl postEntryServiceImpl;
    
    @RequestMapping(value = "/postEntry")
    public ModelAndView postEntry(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/despatch/PostEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePostEntry",consumes="application/json")
    @ResponseBody
    public String savePostEntry(HttpServletRequest request, @RequestBody PostEntryTempDTO poObj, ModelMap map,
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
            
            ibcRet = postEntryServiceImpl.insertPostEntryDetails(poObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/getPostEntryRecord")
    public @ResponseBody String getPostEntryRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PostEntryDTO>  list = postEntryServiceImpl.getPostEntryRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPostEnReById")
    public @ResponseBody String getPostEnReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("postEn_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<PostEntryDTO>  id_list = postEntryServiceImpl.getPostEntryReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updatePostEntry")
    @ResponseBody
    public String updatePostEntry(HttpServletRequest request, @RequestBody List<PostEntryDTO> poObj, ModelMap map,
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
            b = postEntryServiceImpl.updatePostEntryDetail(poObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getDispatchNoIncreament")
    public @ResponseBody String getDispatchNoIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  dbIncre = postEntryServiceImpl.getDispatchNoNumForIncr();
            return gson.toJson(dbIncre); 
    }
    
    @RequestMapping(value = "/getPostVenReByDisNo")
    public @ResponseBody String getPostVenReByDisNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("dispatch_no") long dispatchNo) throws AppException{
            Gson gson = new Gson();            
            List<PostVenItemsDTO>  id_list = postEntryServiceImpl.getPostEntryVenReByDisNo(dispatchNo);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(value = "/postEntryStaPdf")
    public ModelAndView postEntryStaPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("fromdate") String fromDate, @RequestParam("todate") String toDate) {
        try {
            //List<ItemsInVouDaEnDTO> vouItemsObj = vouDaEnSeImpl.getVouDaEnItemWithBillEnBeDates(encIndId);          
            return new ModelAndView("postEntryPDFView", "postObj", null);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "postEntryStaPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
