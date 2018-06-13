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
import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.UnitServiceImpl;
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
public class UnitsController {
    
    private static final Logger log = Logger.getLogger(UnitsController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UnitServiceImpl unitService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public UnitsController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/unitli")
    public ModelAndView UnitListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<UnitDTO> unitLi = unitService.getAllUnits();
            modelView.addObject("unitLi", unitLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/UnitsList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/saveunit")
    public ModelAndView SaveUnitAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("unitObj") UnitDTO unitObj,
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
            int retVal = unitService.insertUnitDetail(unitObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Unit  ("+unitObj.getUnitName()+ ") added Successfully.");
                modelView.setViewName("redirect:/unitli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Unit detail with code '<strong>("
                        +unitObj.getUnitCode()+ ")</strong>' and name '<strong>("
                        +unitObj.getUnitName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/unitli.htm");
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
    
    
    @RequestMapping(value = "/editunit", method = RequestMethod.GET)
    public @ResponseBody String EditUnitAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("uid") String encUnitId)
    {
        UnitDTO unitObj = new UnitDTO();
        try
        {
            unitObj = unitService.getSelectedUnit(encUnitId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(unitObj);
    }
    
    
    @RequestMapping(value = "/updunit")
    public ModelAndView UpdateCategoryAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("unitObj") UnitDTO unitObj,
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
            int retVal = unitService.updateUnitDetail(unitObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Unit  ("+unitObj.getUnitName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/unitli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Unit detail with code '<strong>("
                        +unitObj.getUnitCode()+ ")</strong>' and name '<strong>("
                        +unitObj.getUnitName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/unitli.htm");
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
