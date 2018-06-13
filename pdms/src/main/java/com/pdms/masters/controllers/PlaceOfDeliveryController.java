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
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.PlaceOfDeliveryServiceImpl;
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
public class PlaceOfDeliveryController {
    
    private static final Logger log = Logger.getLogger(PlaceOfDeliveryController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private PlaceOfDeliveryServiceImpl podService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public PlaceOfDeliveryController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/podli")
    public ModelAndView PODListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<PlaceOfDeliveryDTO> podLi = podService.getAllPlaceOfDeliveryDetails();
            modelView.addObject("podLi", podLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/PODList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savepod")
    public ModelAndView SavePODAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("podObj") PlaceOfDeliveryDTO podObj,
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
            int retVal = podService.insertPlaceOfDeliveryDetail(podObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Place Of Delivery  ("+podObj.getPlaceOfDelivery()+ ") added Successfully.");
                modelView.setViewName("redirect:/podli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +podObj.getPlaceOfDelivery()+ ")</strong>' data already exists.");
                modelView.setViewName("redirect:/podli.htm");
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
    
    
    @RequestMapping(value = "/editpod", method = RequestMethod.GET)
    public @ResponseBody String EditMOPAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("pid") String encPodId)
    {
        PlaceOfDeliveryDTO podObj = new PlaceOfDeliveryDTO();
        try
        {
            podObj = podService.getSelectedPlaceOfDeliveryDetail(encPodId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(podObj);
    }
    
    
    @RequestMapping(value = "/updatepod")
    public ModelAndView UpdatePODAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("podObj") PlaceOfDeliveryDTO podObj,
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
            int retVal = podService.updatePlaceOfDeliveryDetail(podObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Place Of Delivery ("+podObj.getPlaceOfDelivery()+ ") updated Successfully.");
                modelView.setViewName("redirect:/podli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +podObj.getPlaceOfDelivery()+ ")</strong>' data already exists.");
                modelView.setViewName("redirect:/podli.htm");
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

    
}
