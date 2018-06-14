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
import com.pdms.dto.ModeOfPurchaseDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.ModeOfPurchaseServiceImpl;
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
public class ModeOfPurchaseController {
    
    private static final Logger log = Logger.getLogger(ModeOfPurchaseController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private ModeOfPurchaseServiceImpl mopService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public ModeOfPurchaseController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/mopli")
    public ModelAndView MOPListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<ModeOfPurchaseDTO> mopLi = mopService.getAllModeOfPurchaseDetails();
            modelView.addObject("mopLi", mopLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/MOPList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savemop")
    public ModelAndView SaveMOPAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("mopObj") ModeOfPurchaseDTO mopObj,
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
            int retVal = mopService.insertModeOfPurchaseDetail(mopObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Mode Of Purchase  ("+mopObj.getModeOfPurchase()+ ") added Successfully.");
                modelView.setViewName("redirect:/mopli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +mopObj.getModeOfPurchase()+ ")</strong>' data already exists.");
                modelView.setViewName("redirect:/mopli.htm");
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
    
    
    @RequestMapping(value = "/editmop", method = RequestMethod.GET)
    public @ResponseBody String EditMOPAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("mpid") String encMopId)
    {
        ModeOfPurchaseDTO mopObj = new ModeOfPurchaseDTO();
        try
        {
            mopObj = mopService.getSelectedModeOfPurchaseDetail(encMopId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(mopObj);
    }
    
    
    @RequestMapping(value = "/updatemop")
    public ModelAndView UpdateMOPAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("mopObj") ModeOfPurchaseDTO mopObj,
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
            int retVal = mopService.updateModeOfPurchaseDetail(mopObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Mode Of Purchase  ("+mopObj.getModeOfPurchase()+ ") updated Successfully.");
                modelView.setViewName("redirect:/mopli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +mopObj.getModeOfPurchase()+ ")</strong>' data already exists.");
                modelView.setViewName("redirect:/mopli.htm");
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
