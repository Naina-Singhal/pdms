/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.pdms.account.service.impl.BillEntryServiceImpl;
import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.service.impl.ComparativeStatementServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorIndentServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class VendorIndentController {
    
     private static final Logger log = Logger.getLogger(VendorIndentController.class);
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
    private VendorIndentServiceImpl vendorIndentService;
    
    @Autowired
    private IndentServiceImpl indentService;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private ComparativeStatementServiceImpl cstServiceImpl;
    
    @Autowired
    private BillEntryServiceImpl billServiceImpl;
    /*
    End : Autowiring of Fields
    */
    /*
        Default Constructor
    */
    public VendorIndentController()
    {
        
    }
    /*
        Default Constructor
    */
    
    @RequestMapping(value = "/vendorindentli")
    public ModelAndView VendorIndentListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            //GET ALL ACTIVE ITEMS
            List<IndentFormDTO> indentLi = vendorIndentService.getAllVendorIndentForms(request);
            
            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/VendorIndentList");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "VendorIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/mapindentv")
    public ModelAndView MapIndentToVendorView(HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            //GET ALL ACTIVE ITEMS
            List<IndentFormDTO> indentLi = vendorIndentService.getAllVendorIndentForms(request);
            List<IndentFormDTO> fileList = cstServiceImpl.getFileNumbersList();
            
            modelView.addObject("fileLi", fileList);
            modelView.addObject("indentLi", indentLi);            
            
            if(model.asMap().containsKey("indentObj"))
            {
                modelView.addObject("indentSObj", model.asMap().get("indentObj"));
            }
            if(model.asMap().containsKey("itemOb"))
            {
                modelView.addObject("itemSObj", model.asMap().get("itemOb"));
            }
            if(model.asMap().containsKey("vendorLi"))
            {
                modelView.addObject("vendorLi", model.asMap().get("vendorLi"));
            }
            if(model.asMap().containsKey("selItemID"))
            {
                modelView.addObject("selItemID", model.asMap().get("selItemID"));
            }
            if(model.asMap().containsKey("itemFi"))
            {
                modelView.addObject("itemFi", model.asMap().get("itemFi"));
            }
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("vendor/MapIndentToVendor");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "MapIndentToVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
//    @InitBinder("indentObj")
//    protected void saveRoleDetailsBinder(WebDataBinder binder)
//            throws Exception {
//        binder.setAllowedFields(new String[]{"indentNumber"});
//
//    }
    @RequestMapping(value = "/getvindentdtl")
    public ModelAndView GetSelectedIndentDataAction(HttpServletRequest request,
            HttpServletResponse response,
            //@ModelAttribute ("indentObj") IndentFormDTO indentForm,
            @RequestParam("endi") long encIndentID,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        long categoryId = 0;
        try
        {
            //IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID);
            List<IndentFormDTO> indentObj = cstServiceImpl.getIndentFormIdByFileNo(encIndentID);
            for (IndentFormDTO a : indentObj) {
                categoryId = a.getCategoryObj().getCategoryID();
            }
            List<ItemDTO> itemlist = billServiceImpl.getItemMaDeByCategotyId(categoryId);
            redirectAttributes.addFlashAttribute("itemOb", itemlist);
            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            modelView.setViewName("redirect:/mapindentv.htm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "GetSelectedIndentDataAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/getvendtlselitem")
    public ModelAndView GetVendorDetailForSelectedItemAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("enit") String encItemID,
            @RequestParam("endi") long encIndentID,
            //@ModelAttribute("vIndentObj") IndentFormDTO indentForm,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        long categoryId = 0;
        long indent_id = 0;
        try
        {
            
            List<IndentFormDTO> indentObj = cstServiceImpl.getIndentFormIdByFileNo(encIndentID);
            for (IndentFormDTO a : indentObj) {
                
                indent_id = a.getIndentFormID();                
                categoryId = a.getCategoryObj().getCategoryID();
            }
            List<VendorDTO> vendorLi = vendorIndentService.fetchItemRelatedVendorDetails(encItemID, indent_id);            
            
            List<VendorItemsDTO> venItems = vendorIndentService.getFileNoInVendorItems(Long.parseLong(encItemID), indent_id);
            
            //IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID.trim());
            //(indentForm.getIndentNumber());

            List<ItemDTO> itemlist = billServiceImpl.getItemMaDeByCategotyId(categoryId);            
            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            redirectAttributes.addFlashAttribute("vendorLi", vendorLi);
            redirectAttributes.addFlashAttribute("selItemID", encItemID);
            redirectAttributes.addFlashAttribute("itemOb", itemlist);
            redirectAttributes.addFlashAttribute("itemFi", venItems);
            modelView.setViewName("redirect:/mapindentv.htm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "GetSelectedIndentDataAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
 
    
    @RequestMapping(value = "/saveselvd")
    public ModelAndView SaveSelectedVendorsAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vItemObj") VendorItemsDTO vItemsObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            VendorItemsDTO insVItemObj = vendorIndentService.insertVendorIndentItemMappingDetails(vItemsObj, request);
            
            if(insVItemObj.getItemID() > 0)
            {
                String enit = insVItemObj.getItemID()+"";
                long endi = insVItemObj.getFileNo();                
                
                redirectAttributes.addAttribute("enit",enit);
                redirectAttributes.addAttribute("endi",endi);
                
                modelView.setViewName("redirect:/getvendtlselitem.htm");
                        
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
                    + "SaveSelectedVendorsAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/unmapvendorind")
    public ModelAndView UnMapSelectedVendorFromIndentAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vItemObj") VendorItemsDTO vItemsObj,
            @RequestParam("vid") String encVendorID,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {            
            VendorItemsDTO insVItemObj = vendorIndentService.updateVendorIndentMappedItems
                                                    (vItemsObj, encVendorID);
            
            if(insVItemObj.getItemID() > 0)
            {
                String enit = insVItemObj.getItemID()+"";
                long endi = insVItemObj.getFileNo();
                
                redirectAttributes.addAttribute("enit",enit);
                redirectAttributes.addAttribute("endi",endi);
                
                modelView.setViewName("redirect:/getvendtlselitem.htm");
                        
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
                    + "SaveSelectedVendorsAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    
    @RequestMapping(value = "/srcupdateview")
    public ModelAndView VendorSourceUpdateView(HttpServletRequest request,
            HttpServletResponse response,
            Model model)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<ItemDTO> itemLi = itemService.getAllItemDetails(ApplicationConstants.ACTIVE_FLAG_VALUE);
            
            modelView.addObject("itemLi", itemLi);
            
            if(model.asMap().containsKey("vList"))
            {
                modelView.addObject("vendorLi", model.asMap().get("vList"));
            }
            if(model.asMap().containsKey("itemObj"))
            {
                ItemDTO itemObj = (ItemDTO)model.asMap().get("itemObj");
                
                modelView.addObject("selItemId",itemObj.getItemID());
            }
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/vendor/SourceViewUpdate");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "VendorSourceUpdateView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
    @RequestMapping(value = "/srcupdatefetvends")
    public ModelAndView VendorSourceUpdateFetchVendorsView(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("itemObj") ItemDTO itemObj,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            if(model.asMap().containsKey("itemSObj"))
            {
                itemObj =(ItemDTO) model.asMap().get("itemSObj");
            }
            
            List<VendorDTO> vList = vendorIndentService.fetchAllVendorsForSelectedItem(itemObj.getItemID());
            
            redirectAttributes.addFlashAttribute("vList", vList);
            redirectAttributes.addFlashAttribute("itemObj", itemObj);
            
            modelView.setViewName("redirect:/srcupdateview.htm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "VendorSourceUpdateFetchVendorsView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/updvendorsrc")
    public ModelAndView VendorSourceUpdateAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("vItemObj") VendorItemsDTO vItemsObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            long retVal = vendorIndentService.updateVendorIndentItemMappingDetails(vItemsObj, request);
            
            ItemDTO itemObj = new ItemDTO();
            itemObj.setItemID(vItemsObj.getItemID());
            
            redirectAttributes.addFlashAttribute("itemSObj", itemObj);
            
            modelView.setViewName("redirect:/srcupdatefetvends.htm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "VendorSourceUpdateAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
}
