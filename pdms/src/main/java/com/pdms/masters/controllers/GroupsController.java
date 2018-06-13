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
import com.pdms.dto.GroupDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.GroupServiceImpl;
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
public class GroupsController {
 
    private static final Logger log = Logger.getLogger(GroupsController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private GroupServiceImpl grpService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public GroupsController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/groupli")
    public ModelAndView GroupListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<GroupDTO> groupLi = grpService.getAllGroupDetails();
            modelView.addObject("groupLi", groupLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/GroupList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/savegroup")
    public ModelAndView SaveGroupAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("groupObj") GroupDTO groupObj,
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
            int retVal = grpService.insertGroupDetails(groupObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Group  ("+groupObj.getGroupName()+ ") added Successfully.");
                modelView.setViewName("redirect:/groupli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Group with code '<strong>("
                        +groupObj.getGroupCode()+ ")</strong>' Or name '<strong>("
                        +groupObj.getGroupName()+")</strong>' already exists.");
                modelView.setViewName("redirect:/groupli.htm");
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
    
    
    @RequestMapping(value = "/updategroup")
    public ModelAndView UpdateGroupAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("groupObj") GroupDTO groupObj,
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
            int retVal = grpService.updateGroupDetails(groupObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Group  ("+groupObj.getGroupName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/groupli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Group with code '<strong>("
                        +groupObj.getGroupCode()+ ")</strong>' Or name '<strong>("
                        +groupObj.getGroupName()+")</strong>' already exists.");
                modelView.setViewName("redirect:/groupli.htm");
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
    
    
    @RequestMapping(value = "/editgroup", method = RequestMethod.GET)
    public @ResponseBody String EditGroupAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("cgid") String encGroupId)
    {
        GroupDTO grpObj = new GroupDTO();
        try
        {
            grpObj = grpService.getSelectedGroupDetailByID(encGroupId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(grpObj);
    }
    
}
