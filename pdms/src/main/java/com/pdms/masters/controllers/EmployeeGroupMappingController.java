/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.GroupDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.EmployeeGroupMappingServiceImpl;
import com.pdms.master.service.impl.GroupServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class EmployeeGroupMappingController {
    
    private static final Logger log = Logger.getLogger(EmployeeGroupMappingController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private EmployeeGroupMappingServiceImpl empGrpMapService;
    
    @Autowired
    private GroupServiceImpl groupService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public EmployeeGroupMappingController()
    {
        
    }
    /*
        Default Constructor
    */
     @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/empgrpli")
    public ModelAndView EmployeeGroupMappingListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<GroupDTO> groupLi = groupService.getAllGroupDetails();
            List<EmployeeLoginDTO> empGrpLi = empGrpMapService.getAllPurchaseUnitEmployeeDetails();
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.addObject("groupLi", groupLi);
            modelView.addObject("empGrpLi", empGrpLi);
            
            modelView.setViewName("/masters/EmployeeGroupMappingList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/saveempgrpmap")
    public ModelAndView SaveEmployeeGroupMappingAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("grpObj") GroupDTO groupObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int resVal = empGrpMapService.mapEmployeesToGroup(groupObj, request);
            
            if(resVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Employees mapped to slected group Successfully.");
                modelView.setViewName("redirect:/empgrpli.htm");
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
    
    @RequestMapping(value = "/updempgrpmap")
    public ModelAndView UpdateEmployeeGroupMappingAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("grpObj") GroupDTO groupObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int resVal = empGrpMapService.updateEmployeesToGroup(groupObj, request);
            
            if(resVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Employees mapped to slected group Successfully.");
                modelView.setViewName("redirect:/empgrpli.htm");
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
    
    @RequestMapping(value = "/editempgrp", method = RequestMethod.GET)
    public @ResponseBody String EditEmployeeGroupMapAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("gpid") String encGroupId)
    {
        List<GroupDTO> groupLi = new ArrayList<>();
        JsonArray jsonArray=new JsonArray();
        try
        {
            groupLi = empGrpMapService.getAllMappedEmployeeDetails(encGroupId);
            
            Gson gson = new Gson();
            JsonElement element = gson.toJsonTree(groupLi, new TypeToken<List<GroupDTO>>() {}.getType());

            if (!element.isJsonArray()) {
                throw new AppException();
            }

             jsonArray = element.getAsJsonArray();
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(jsonArray);
    }
    
    
}
