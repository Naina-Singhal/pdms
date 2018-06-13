/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.CategoryServiceImpl;
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
public class CategoryController {
    
    private static final Logger log = Logger.getLogger(CategoryController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private CategoryServiceImpl categoryService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public CategoryController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/categoryli")
    public ModelAndView CategoryListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<CategoryDTO> categoryLi = categoryService.getAllCategories();
            modelView.addObject("categoryLi", categoryLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/CategoryList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savecategory")
    public ModelAndView SaveCategoryAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("categoryObj") CategoryDTO categoryObj,
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
            int retVal = categoryService.insertCategoryDetail(categoryObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Category  ("+categoryObj.getCategoryName()+ ") added Successfully.");
                modelView.setViewName("redirect:/categoryli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Entered Category Data with name '<strong>("
                        +categoryObj.getCategoryName()+ ")</strong>' or code '<strong>("
                        +categoryObj.getCategoryCode()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/categoryli.htm");
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
    
    
    @RequestMapping(value = "/editcategory", method = RequestMethod.GET)
    public @ResponseBody String EditItemAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("ctid") String encCategoryId)
    {
        CategoryDTO catObj = new CategoryDTO();
        try
        {
            catObj = categoryService.getSelectedCategoryByID(encCategoryId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(catObj);
    }
    
    
    @RequestMapping(value = "/updatecategory")
    public ModelAndView UpdateCategoryAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("categoryObj") CategoryDTO categoryObj,
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
            int retVal = categoryService.updateCategoryDetail(categoryObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Category  ("+categoryObj.getCategoryName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/categoryli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Entered Category Data with name '<strong>("
                        +categoryObj.getCategoryName()+ ")</strong>' or code '<strong>("
                        +categoryObj.getCategoryCode()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/categoryli.htm");
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
    
    
    
    @RequestMapping(value = "/chkcategory", method = RequestMethod.GET)
    public @ResponseBody String CheckDuplicateCategoryAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("cname") String catName,
            @RequestParam("ccode") String catCode)
    {
        int res = 0;
        try
        {
            res = categoryService.checkDuplicateCategory(catName, catCode);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(res+"");
    }
    
}
