/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.MasterLookupDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.service.impl.MasterLookUpServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class VendorController {
    
    private static final Logger log = Logger.getLogger(VendorController.class);
    /*
    Start : Autowiring of Fields
    */
    @Autowired
    private VendorServiceImpl vendorService;
    
    @Autowired
    private CategoryServiceImpl catService;
    
    @Autowired
    private EncryptDecrypt encDecrypt;
    
    @Autowired
    private ItemServiceImpl itemService;
    
    @Autowired
    private MasterLookUpServiceImpl masterService;
    /*
    End : Autowiring of Fields
    */
    /*
        Default Constructor
    */
    public VendorController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/vendorli")
    public ModelAndView VendorListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            //GET ALL ACTIVE ITEMS
            List<VendorDTO> vendorLi = vendorService.getAllActiveVendorDetails();
            
            modelView.addObject("vendorLi", vendorLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/VendorList");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "VendorListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/addvendor")
    public ModelAndView AddVendorView(HttpServletRequest request,
            HttpServletResponse response,@ModelAttribute("vendorObj") VendorDTO vendorObj)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            String venCode = vendorService.generateVendorCode();
            
            List<MasterLookupDTO> vendroRegTypeLi = masterService.
                        getMasterEntriesByLookUpType(ApplicationConstants.CONSTANT_VENDOR_REGISTRATION_TYPE);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.addObject("vendorCode", venCode);
            modelView.addObject("vendroRegType", vendroRegTypeLi);
            modelView.setViewName("vendor/AddVendor");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/vendoritems")
    public ModelAndView AddVendorItemsView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("vdi") String encVendorID,
            Model model)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            
            List<VendorItemsDTO> vendorItemLi = vendorService.getAllVendorItemDetails(encVendorID);
            List<CategoryDTO> catLi = catService.getAllCategories();
            
            modelView.addObject("vendorItemLi", vendorItemLi);
            modelView.addObject("catLi", catLi);
            modelView.addObject("encVendorID", encVendorID);
            if(model.asMap().containsKey("itemLi"))
            {
                modelView.addObject("itemLi",model.asMap().get("itemLi"));
            }
            if(model.asMap().containsKey("sVendorItemsObj"))
            {
                modelView.addObject("sVendorItemsObj",model.asMap().get("sVendorItemsObj"));
            }
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/VendorItems");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/savevendor")
    public ModelAndView SaveVendorAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vendorObj") VendorDTO vendorObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int vID = vendorService.insertVendorDetails(vendorObj, request);
            if(vID > 0)
            {
                modelView.setViewName("redirect:/vendoritems.htm");
                modelView.addObject("vdi",encDecrypt.encrypt(vID+""));
            }
            else if(vID == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Vendor with code '<strong>("
                        +vendorObj.getVendorCode()+ ")</strong>' or name '<strong>("
                        +vendorObj.getVendorName()+")</strong>' details alreay exists.");
                modelView.setViewName("redirect:/vendorli.htm");
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
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/chkvendor", method = RequestMethod.GET)
    public @ResponseBody String CheckVendorNameAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("vendorName") String vendorName)
    {
        int res = 0;
        try
        {
            res = vendorService.checkVendorExistence(vendorName);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(res);
    }
    
    
    @RequestMapping(value = "/editvendor")
    public ModelAndView EditVendorView(HttpServletRequest request,
            HttpServletResponse response,@RequestParam("vdi") String encVendorID)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            
            VendorDTO vendorObj = vendorService.getSelectedVendorDetails(encVendorID);
            modelView.addObject("sVendorObj", vendorObj);
            List<MasterLookupDTO> vendroRegTypeLi = masterService.
                        getMasterEntriesByLookUpType(ApplicationConstants.CONSTANT_VENDOR_REGISTRATION_TYPE);
            
            modelView.addObject("vendroRegType", vendroRegTypeLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/EditVendor");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/updvendor")
    public ModelAndView UpdateVendorAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vendorObj") VendorDTO vendorObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int vID = vendorService.updateVendorDetails(vendorObj, request);
            if(vID > 0)
            {
                modelView.setViewName("redirect:/vendoritems.htm");
                modelView.addObject("vdi",vendorObj.getEncVendorID());
            }
            else if(vID == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Vendor with code '<strong>("
                        +vendorObj.getVendorCode()+ ")</strong>' or name '<strong>("
                        +vendorObj.getVendorName()+")</strong>' details alreay exists.");
                modelView.setViewName("redirect:/vendorli.htm");
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
            log.debug("Exception Occured while Executing the "
                    + "UpdateVendorAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/vendorselcatitems")
    public ModelAndView GetSelectedCategoryItems(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vendorItemsObj") VendorItemsDTO vendorItemsObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<ItemDTO> itemLi = itemService.getCategoryWiseItemDetails
                        (vendorItemsObj.getCategoryID());
            
            redirectAttributes.addFlashAttribute("itemLi",itemLi);
            redirectAttributes.addFlashAttribute("sVendorItemsObj",vendorItemsObj);
            redirectAttributes.addAttribute("vdi",vendorItemsObj.getEncVendorID());
            
            modelView.setViewName("redirect:/vendoritems.htm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "GetSelectedCategoryItems :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/savevendoritems")
    public ModelAndView SaveVendorItemsAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vendorItemsObj") VendorItemsDTO vendorItemsObj)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int vID = vendorService.insertSelectedVendorItemDetails(vendorItemsObj,request);
            if(vID > 0)
            {
                modelView.setViewName("redirect:/vendoritems.htm");
                modelView.addObject("vdi",vendorItemsObj.getEncVendorID());
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
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/viewvendor")
    public ModelAndView ViewVendorView(HttpServletRequest request,
            HttpServletResponse response,@RequestParam("vdi") String encVendorID)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            
            VendorDTO vendorObj = vendorService.getSelectedVendorDetails(encVendorID);
            modelView.addObject("sVendorObj", vendorObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/ViewVendor");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "AddVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/delvendoritem")
    public ModelAndView DeleteVendorItemAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("vidi") String encVendorItemID,
            @RequestParam("vdi") String encVendorID,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int res = vendorService.deleteSelectedVendorItemDetails(encVendorItemID);
            if(res > 0)
            {
                modelView.addObject("vdi",encVendorID);
                modelView.setViewName("redirect:/vendoritems.htm");
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
            log.debug("Exception Occured while Executing the "
                    + "DeleteVendorItemAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/mapvendoritem")
    public ModelAndView MapVendorItemAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("vidi") String encVendorItemID,
            @RequestParam("vdi") String encVendorID,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            int res = vendorService.mapSelectedVendorItemDetails(encVendorItemID);
            if(res > 0)
            {
                modelView.addObject("vdi",encVendorID);
                modelView.setViewName("redirect:/vendoritems.htm");
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
            log.debug("Exception Occured while Executing the "
                    + "mapSelectedVendorItemDetails :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/getGstinNoByVendorNa")
    public @ResponseBody String getGstinNoByVendorNamae(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("ven_code") String venCode) throws AppException{
            Gson gson = new Gson();            
            List<VendorDTO>  ven = vendorService.getGstinNoByVendorName(venCode);
            return gson.toJson(ven);
    }
    
    @RequestMapping(value = "/getVendorOnlyCodeSer")
    public @ResponseBody String getVendorOnlyCodeSer(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("query") String keywords) throws AppException{
            Gson gson = new Gson();            
            ArrayList<String> ven = vendorService.getVendorOnlyCodesSearch(keywords);
            return gson.toJson(ven);
    }
    
    @RequestMapping(value = "/getVendorCodeByName")
    public @ResponseBody String getVendorCodeByName(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("vendor_name") String vendor_name) throws AppException{
            Gson gson = new Gson();            
            String ven = vendorService.getVendorDetailsByName(vendor_name);
            return gson.toJson(ven);
    }
}
