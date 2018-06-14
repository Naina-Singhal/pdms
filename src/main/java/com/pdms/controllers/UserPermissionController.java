/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.LrEntryDTO;
import com.pdms.dto.PagePermiDTO;
import com.pdms.dto.UserPermissionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import com.pdms.masters.controllers.IbcMasterController;
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
public class UserPermissionController {
    private static final Logger log = Logger.getLogger(UserPermissionController.class);
    
    //Default constructor 
    public UserPermissionController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
            
    @RequestMapping(value = "/userPermissionEn")
    public ModelAndView userPermissionEn(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/UserPermission");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getUserProfileDa")
    public @ResponseBody String getIBCnumberRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<EmployeeProfileDTO>  des_list = permissionServiceImpl.getUserProfileReco();
            String json_list = gson.toJson(des_list);   
            log.info("--permission record---"+json_list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPagePermiData")
    public @ResponseBody String getPagePermiData(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PagePermiDTO>  list = permissionServiceImpl.getPagepermiData();
            String json_list = gson.toJson(list);   
            log.info("--page record---"+json_list);
            return json_list;
    }
    
    @RequestMapping(value = "/getUserPermiActive")
    public @ResponseBody String getUserPermiActive(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("icno") String icno, @RequestParam("page_ID") String page_id) throws AppException{
            Gson gson = new Gson(); 
            log.info(page_id+"--------icnoat--------"+icno);
            int  pr_list = permissionServiceImpl.getUserPermiActive(icno, page_id);
            String json_list = gson.toJson(pr_list);   
            log.info("--permission record---"+json_list);
            return json_list;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePermissions")
    @ResponseBody
    public String savePermissions(HttpServletRequest request, @RequestBody List<UserPermissionDTO> perObj, ModelMap map,
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
            log.info("---info---"+perObj);
            for(UserPermissionDTO a: perObj){
                    log.info("---info---"+a.getD1());
            }
            lrRet = permissionServiceImpl.insertUserPermission(perObj, sessUserID);
            log.info("---info---"+lrRet);
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
    
    @RequestMapping(value = "/getUserPermForPage")
    public @ResponseBody String getUserPermForPage(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("page_ID") String page_id) throws AppException{
            Gson gson = new Gson(); 
            log.info(page_id+"--------icnoat--------");
            HttpSession session = request.getSession();            
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION)); 
            String empIcNo = empDTO.getEmployeeProfileDTO().getIcNumber();
            int  pr_list = permissionServiceImpl.getUserPermiActive(empIcNo, page_id);
            String json_list = gson.toJson(pr_list);   
            log.info("--permission page record---"+json_list);
            return json_list;
    }
}
