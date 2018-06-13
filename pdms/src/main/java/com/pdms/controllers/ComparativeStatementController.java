/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.BillEntryItemsDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.ComparativeStatementDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.PublicTenderDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.ComparativeStatementServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.IndentTenderServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorIndentServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ComparativeStatementController {

    private static final Logger log = Logger.getLogger(ComparativeStatementController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private ComparativeStatementServiceImpl cstServiceImpl;

    @Autowired
    private VendorIndentServiceImpl vendorIndentService;

    @Autowired
    private IndentServiceImpl indentService;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private IndentTenderServiceImpl indTenSecImpl;
    
    @Autowired
    private VendorServiceImpl venServiImpl;

    /*
    End : Autowiring of Fields
     */
    @RequestMapping(value = "/csthome")
    public ModelAndView CSTIndentListView(HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes,
            Model model) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = cstServiceImpl.getAllCSTIndentForms();

            modelView.addObject("indentLi", indentLi);

            if (model.asMap().containsKey("indentObj")) {
                modelView.addObject("indentSObj", model.asMap().get("indentObj"));
            }
            if (model.asMap().containsKey("vendorLi")) {
                modelView.addObject("vendorLi", model.asMap().get("vendorLi"));
            }
            if (model.asMap().containsKey("selItemID")) {
                modelView.addObject("selItemID", model.asMap().get("selItemID"));
            }
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("cst/CSTHome");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "CSTIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/fetchcstind")
    public ModelAndView FetchCSTSelectedIndentView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("endi") String encIndentID,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            IndentFormDTO indentObj = cstServiceImpl.getSelectedIndentFormDetail(encIndentID);
            //List<IndentFormDTO> indentLi = cstServiceImpl.getAllCSTIndentForms();

            //modelView.addObject("indentLi", indentLi);
            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            //modelView.setViewName("cst/CSTHome");
            modelView.setViewName("redirect:/csthome.htm");

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "FetchCSTSelectedIndentView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/cstvendtlselitem")
    public ModelAndView GetVendorDetailForSelectedItemInCSTAction(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("enit") String encItemID,
            @RequestParam("endi") String encIndentID,
            //@ModelAttribute("vIndentObj") IndentFormDTO indentForm,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<VendorDTO> vendorLi = cstServiceImpl.fetchItemRelatedVendorCSTDetails(encItemID);

            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID.trim());
            //(indentForm.getIndentNumber());

            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            redirectAttributes.addFlashAttribute("vendorLi", vendorLi);
            redirectAttributes.addFlashAttribute("selItemID", encItemID);

            modelView.setViewName("redirect:/csthome.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "GetVendorDetailForSelectedItemInCSTAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    
    @RequestMapping(value = "/getCstDeByFileNoVenNo")
    public @ResponseBody String getCstDeByFileNoVenNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("fileNo") long fiNo,
            @RequestParam("vid") int VendorID) throws AppException{
            Gson gson = new Gson();  
             List<ComparativeStatementDTO> cstObj = null;
            try {
            cstObj = cstServiceImpl.fetchCSTDetailsForSelectedVendor(fiNo, VendorID);

            } catch (AppException e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            }

            return gson.toJson(cstObj);
    }

    
     @RequestMapping(method = RequestMethod.POST, value = "/savecstdtls")
    @ResponseBody
    public String savecstdtls(HttpServletRequest request, @RequestBody List<ComparativeStatementDTO> cstObj, ModelMap map,
     HttpServletResponse response) {
            Gson gson = new Gson();            
            int b = 0;           
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
            
            b = cstServiceImpl.insertCSTDetails(cstObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getFileNumbers")
    public @ResponseBody String getFileNumbers(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<IndentFormDTO>  item = cstServiceImpl.getFileNumbersList();
            return gson.toJson(item);
    }
    
    @RequestMapping(value = "/getIndentDeByFiNo")
    public @ResponseBody String getIndentDeByFiNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("fileNo") long fileNo) throws AppException{
            Gson gson = new Gson();            
            List<IndentFormDTO> item = cstServiceImpl.getIndentdIdByFileNo(fileNo);
            return gson.toJson(item);
    }
    
    @RequestMapping(value = "/getPubTenDeByTenId")
    public @ResponseBody String getPubTenDeByTenId(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("FkPubTenderId") long FkPubTenderId) throws AppException{
            Gson gson = new Gson();            
            List<PublicTenderItemsDTO> titem = indTenSecImpl.fetAllPublicTenderItemDeById(FkPubTenderId);
            return gson.toJson(titem);
    }
    
     @RequestMapping(value = "/getVendorDeByVenId")
    public @ResponseBody String getVendorDeByVenId(HttpServletRequest request, HttpServletResponse response,            
            @RequestParam("Vendor_ID") long VendorID) throws AppException{
            Gson gson = new Gson();  
             List<VendorDTO> venObj = null;
            try {
            venObj = venServiImpl.getVendorDeByVendorID(VendorID);

            } catch (AppException e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            }

            return gson.toJson(venObj);
    }
    
     @RequestMapping(value = "/getPublicTenderDeByFileNo")
    public @ResponseBody String getPublicTenderDeByFileNo(HttpServletRequest request, HttpServletResponse response,            
            @RequestParam("file_no") long fileNO) throws AppException{
            Gson gson = new Gson();  
             List<PublicTenderDTO> pubObj = null;
            try {
            pubObj = indTenSecImpl.getPublicTenderDeByFileNo(fileNO);

            } catch (AppException e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            }

            return gson.toJson(pubObj);
    }
    
    @RequestMapping(value = "/getIndentFormIdByFiNo")
    public @ResponseBody String getIndentFormIdByFiNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("filNumber") long fileNum) throws AppException{
            Gson gson = new Gson();            
            List<IndentFileMappingDTO> indentFormList= cstServiceImpl.getIndentdFormIdByFileNo(fileNum);
            return gson.toJson(indentFormList);
    }
    
    @RequestMapping(value = "/getIndentDetailsByFiNo")
    public @ResponseBody String getIndentDetailsByFiNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("fileNo") long fileNo) throws AppException{
            Gson gson = new Gson();            
            List<IndentFormDTO> item = cstServiceImpl.getIndentFormIdByFileNo(fileNo);
            return gson.toJson(item);
    }
    
    @RequestMapping(value = "/getCstReByItemFile")
    public @ResponseBody String getCstReByItemFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("fileNo") long fileNo, @RequestParam("item_id") long itemId) throws AppException{
            Gson gson = new Gson();            
            List<ComparativeStatementDTO> item = cstServiceImpl.fetchCSTDetailsForSelectedItem(fileNo, itemId);
            return gson.toJson(item);
    }
    
    @RequestMapping(value = "/getCstReByVendorBased")
    public @ResponseBody String getCstReByVendorBased(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("fileNo") long fileNo, @RequestParam("vendor_id") long vendorID) throws AppException{
            Gson gson = new Gson();            
            List<ComparativeStatementDTO> vender = cstServiceImpl.cstDetailsForVendorBased(fileNo, vendorID);
            return gson.toJson(vender);
    }
    
    @RequestMapping(value = "/venBasedCstPdf")
    public ModelAndView venBasedCstPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<ComparativeStatementDTO> rtgsObj = cstServiceImpl.cstDetailsForVendorBased(encIndId, encIndId);

            return new ModelAndView("cstVenBasedPDFView", "cstObj", rtgsObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
