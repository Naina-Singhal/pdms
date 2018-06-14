/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.HeadOfAccountServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class HeadOfAccountController {
    
    private static final Logger log = Logger.getLogger(HeadOfAccountController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private HeadOfAccountServiceImpl hoaService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public HeadOfAccountController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/hoali")
    public ModelAndView HOAListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<HeadOfAccountDTO> hoaLi = hoaService.getAllHeadOfAccountDetails();
            modelView.addObject("hoaLi", hoaLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/HOAList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savehoa")
    public ModelAndView SaveHOAAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("hoaObj") HeadOfAccountDTO hoaObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
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
            int retVal = hoaService.insertHeadOfAccountDetail(hoaObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Head Of Account  ("+hoaObj.getAccountType()+ ") added Successfully.");
                modelView.setViewName("redirect:/hoali.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +hoaObj.getAccountType()+ ")</strong>' or description '<strong>("
                        +hoaObj.getDescription()+")</strong>' already exists.");
                modelView.setViewName("redirect:/hoali.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/edithoa", method = RequestMethod.GET)
    public @ResponseBody String EditHOAAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("hid") String encHoaId)
    {
        HeadOfAccountDTO hoaObj = new HeadOfAccountDTO();
        try
        {
            hoaObj = hoaService.getSelectedHeadOfAccountDetail(encHoaId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(hoaObj);
    }
    
    
    @RequestMapping(value = "/updatehoa")
    public ModelAndView UpdateHOAAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("hoaObj") HeadOfAccountDTO hoaObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
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
            int retVal = hoaService.updateHeadOfAccountDetail(hoaObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Head Of Account  ("+hoaObj.getAccountType()+ ") updated Successfully.");
                modelView.setViewName("redirect:/hoali.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +hoaObj.getAccountType()+ ")</strong>' or description '<strong>("
                        +hoaObj.getDescription()+")</strong>' already exists.");
                modelView.setViewName("redirect:/hoali.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/getHoaNamesDetails")
    public @ResponseBody String getHoaNamesDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<HeadOfAccountDTO>  hoac_list = hoaService.getAllHeadOfAccountDetails();
            return gson.toJson(hoac_list);
    }
}
