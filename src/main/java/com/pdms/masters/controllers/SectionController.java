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
import com.pdms.dto.SectionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.SectionServiceImpl;
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
public class SectionController {
    
    
    private static final Logger log = Logger.getLogger(SectionController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private SectionServiceImpl sectionService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public SectionController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/sectionli")
    public ModelAndView SectionListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<SectionDTO> sectionLi = sectionService.getAllSectionDetails(request);
            modelView.addObject("sectionLi", sectionLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/SectionList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savesection")
    public ModelAndView SaveSectionAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("sectionObj") SectionDTO sectionObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            long sessUserID=0;
            long empTypeID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
                empTypeID = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID();
            }
            sectionObj.setEmployeeTypeID(empTypeID);
            int retVal = sectionService.insertSectionDetail(sectionObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Section  ("+sectionObj.getSectionName()+ ") added Successfully.");
                modelView.setViewName("redirect:/sectionli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +sectionObj.getSectionCode()+ ")</strong>' or name '<strong>("
                        +sectionObj.getSectionName()+")</strong>' data already exists.");
                modelView.setViewName("redirect:/sectionli.htm");
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
    
    
    @RequestMapping(value = "/editsection", method = RequestMethod.GET)
    public @ResponseBody String EditSectionAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("seid") String encSectionId)
    {
        SectionDTO sectionObj = new SectionDTO();
        try
        {
            sectionObj = sectionService.getSeclectedSectionByID(encSectionId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(sectionObj);
    }
    
    
    @RequestMapping(value = "/updatesection")
    public ModelAndView UpdateSectionAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("sectionObj") SectionDTO sectionObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            long sessUserID=0;
            long empTypeID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
                empTypeID = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID();
            }
            sectionObj.setEmployeeTypeID(empTypeID);
            int retVal = sectionService.updateSectionDetail(sectionObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Section ("+sectionObj.getSectionName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/sectionli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Details with entered '<strong>("
                        +sectionObj.getSectionCode()+ ")</strong>' or name '<strong>("
                        +sectionObj.getSectionName()+")</strong>' data already exists.");
                modelView.setViewName("redirect:/sectionli.htm");
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
    
    @RequestMapping(value = "/getSecDeBySectionCode")
    public @ResponseBody String getSecDeBySectionCode(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "SecCode") String sectionCode) throws AppException{
            Gson gson = new Gson();             
            return gson.toJson(sectionService.getSectionDeBySecCode(sectionCode));
    }

    
}
