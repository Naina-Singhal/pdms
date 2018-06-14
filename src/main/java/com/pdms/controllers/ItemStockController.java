/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class ItemStockController {
    
    private static final Logger log = Logger.getLogger(ItemStockController.class);
    
    /*
    Start : Autowiring of Fields
    */
    @Autowired
    private CategoryServiceImpl catService;
    
    @Autowired
    private ItemServiceImpl itemService;
    /*
    End : Autowiring of Fields
    */
    /*
        Default Constructor
    */
    public ItemStockController()
    {
        
    }
    /*
        Default Constructor
    */
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/itemstockli")
    public ModelAndView ItemStockListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            //GET ALL ACTIVE ITEMS
            List<ItemDTO> itemLi = itemService.getAllItemDetails
                                        (ApplicationConstants.ACTIVE_FLAG_VALUE);
            List<CategoryDTO> categoryLi = catService.getAllCategories();
            
            modelView.addObject("categoryLi", categoryLi);
            modelView.addObject("itemLi", itemLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/ItemStockList");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/fetchitembycat")
    public ModelAndView FetchItemByCategory(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("itemObj") ItemDTO itemObj)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            if(itemObj.getCategoryDTO().getCategoryID()>0)
            {
                long catID= itemObj.getCategoryDTO().getCategoryID();
                
                List <ItemDTO> itemLi = itemService.getCategoryWiseItemDetails(catID);
                List<CategoryDTO> categoryLi = catService.getAllCategories();

                modelView.addObject("selCatID",catID);
                modelView.addObject("categoryLi", categoryLi);
                modelView.addObject("itemLi", itemLi);
                modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
                modelView.setViewName("indent/ItemStockList");
            }
            
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/generatepdf.htm", method = RequestMethod.GET)
    public ModelAndView generatePdf(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Calling generatePdf()...");

        IndentFormDTO indentForm = new IndentFormDTO();

        ModelAndView modelAndView = new ModelAndView("indentFormPDFView", "indentObj", indentForm);

        return modelAndView;
    }
    
}
