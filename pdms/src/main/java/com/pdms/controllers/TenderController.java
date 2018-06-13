/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.MasterLookupDTO;
import com.pdms.dto.PublicTenderDTO;
import com.pdms.dto.PublicTenderVendorDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.IndentTenderServiceImpl;
import com.pdms.service.impl.MasterLookUpServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class TenderController {

    private static final Logger log = Logger.getLogger(TenderController.class);
    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private IndentTenderServiceImpl ITenderService;

    @Autowired
    private IndentServiceImpl indentService;

    @Autowired
    private VendorServiceImpl vendorService;
    
    @Autowired
    private MasterLookUpServiceImpl masterService;

    /*
    End : Autowiring of Fields
     */
 /*
        Default Constructor
     */
    public TenderController() {

    }

    /*
        Default Constructor
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/pudtenderindli")
    public ModelAndView PublicTenderIndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = ITenderService.getAllTendorIndentForms(request);

            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/ptIndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "PublicTenderIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/raisepudtender")
    public ModelAndView RaisePublicTenderView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") String encIndentID) {
        ModelAndView modelView = new ModelAndView();
        try {
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndentID);

            List<MasterLookupDTO> tenderTypes = masterService.getMasterEntriesByLookUpType
                            (ApplicationConstants.CONSTANT_TENDER_TYPE);
            
            modelView.addObject("indentSObj", indentObj);
            modelView.addObject("tenderTypes", tenderTypes);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/RaisePubTender");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "RaisePublicTenderView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/savepubtender")
    public ModelAndView SavePublicTenderAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("tenderObj") PublicTenderDTO tenderObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            int resVal = ITenderService.saveIndentPublicTenderDetails(tenderObj, request);

            if (resVal > 0) {
                redirectAttributes.addFlashAttribute("msg",
                        "Public Tender Raised and Saved Successfully.");
                modelView.setViewName("redirect:/pudtenderindli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SavePublicTenderAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/pudtenderli")
    public ModelAndView PublicTenderListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<PublicTenderDTO> tenderLi = ITenderService.getAllPublicTenderDetails(request);

            modelView.addObject("tenderLi", tenderLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/PublicTenderLi");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "PublicTenderIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/viewpudtender")
    public ModelAndView ViewPublicTenderByTenderIDView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.fetchSelectedPublicTenders(request, encTenderID);

            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/ViewPublicTender");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ViewPublicTenderByTenderIDView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
    
    
    @RequestMapping(value = "/vdwdpudtender")
    public ModelAndView ViewDownloadPublicTenderByTenderIDView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.fetchSelectedPublicTenders(request, encTenderID);

            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/ViewDownloadPublicTender");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ViewPublicTenderByTenderIDView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/viewpudtenderfi")
    public ModelAndView ViewPublicTenderByIndentIDView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encIndentID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.
                    fetchSelectedPublicTendersByIndent(request, encIndentID);

            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/ViewPublicTenderIndent");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ViewPublicTenderByTenderIDView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/edtpudtender")
    public ModelAndView EditPublicTenderByTenderIDView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.fetchSelectedPublicTenders(request, encTenderID);
            List<MasterLookupDTO> tenderTypes = masterService.getMasterEntriesByLookUpType
                            (ApplicationConstants.CONSTANT_TENDER_TYPE);
            
            modelView.addObject("tenderTypes", tenderTypes);
            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/EditPublicTender");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ViewPublicTenderByTenderIDView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/updpubtender")
    public ModelAndView UpdatePublicTenderAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("tenderObj") PublicTenderDTO tenderObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            int resVal = ITenderService.updateIndentPublicTenderDetails(tenderObj, request);

            if (resVal > 0) {
                redirectAttributes.addFlashAttribute("msg",
                        "Public Tender Updated Successfully.");
                modelView.setViewName("redirect:/pudtenderli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "UpdatePublicTenderAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/maptendertov")
    public ModelAndView MapTenderTenderToVendorView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.fetchSelectedPublicTenders(request, encTenderID);

            List<VendorDTO> vendorLi = vendorService.getAllActiveVendorDetails();

            modelView.addObject("vendorLi", vendorLi);
            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/MapPublicTender");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "MapTenderTenderToVendorView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/maptendertova")
    public ModelAndView MapTenderTenderToVendorAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("ptVendorObj") PublicTenderVendorDTO ptVendorObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {

            int retVal = ITenderService.mapVendorToPublicTenderDetails(ptVendorObj, request);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("msg",
                        "Public Tender Details Updated Successfully.");
                modelView.setViewName("redirect:/pudtenderli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "MapTenderTenderToVendorAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
    
    

    @RequestMapping(value = "/gpoview")
    public ModelAndView GeneratePOView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.
                    fetchSelectedPublicTenders(request, encTenderID);

            modelView.addObject("tenderObj", tenderObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("tender/POGeneration");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "GeneratePOView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/savepoact")
    public ModelAndView SaveTenderPOAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("ptVendorObj") PublicTenderVendorDTO ptVendorObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {

            int retVal = ITenderService.generatePOForPublicTenderDetails(ptVendorObj, request);
            if (retVal > 0) {
                redirectAttributes.addFlashAttribute("msg",
                        "Public Tender Details Updated Successfully.");
                modelView.setViewName("redirect:/pudtenderli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveTenderPOAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/popdf")
    public ModelAndView GeneratePOPDFView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        ModelAndView modelView = new ModelAndView();
        try {

            PublicTenderDTO tenderObj = ITenderService.
                    fetchSelectedPublicTenders(request, encTenderID);

            return new ModelAndView("poFormPDFView", "tenderObj", tenderObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "GeneratePOPDFView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
    }
    
    
    @RequestMapping(value = "/tenderpdf")
    public ModelAndView TenderFormPDFDownloadView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        try {
            PublicTenderDTO tenderObj = ITenderService.
                    fetchSelectedPublicTenders(request, encTenderID);

            return new ModelAndView("tenderFormPDFView", "tenderObj", tenderObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "TenderFormPDFDownloadView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/tenderxls")
    public ModelAndView TenderFormXLSDownloadView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("etid") String encTenderID) {
        try {
            PublicTenderDTO tenderObj = ITenderService.
                    fetchSelectedPublicTenders(request, encTenderID);

            return new ModelAndView("tenderFormExcelView", "tenderObj", tenderObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "TenderFormPDFDownloadView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
