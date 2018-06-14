/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.PoReleaseTempDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.service.impl.POrderEntryServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @author myassessment
 */
@Controller
public class POrderEntryController {
    
     private static final Logger logger = Logger.getLogger(POrderEntryController.class);
    
    @Autowired
    private POrderEntryServiceImpl pOrderEntryServiceImpl;
    
    public POrderEntryController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/purOrdLi")
    public ModelAndView purchaseOrderView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/PoReleaseEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/savePorderDetEn",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public String savePorderDetEn(HttpServletRequest request, @RequestBody PoReleaseTempDTO poedObj, ModelMap map,
     HttpServletResponse response) {
            String a = null;
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
            List<POrderEntryDTO> poe = poedObj.getPoReleaseDTO();
            b =  pOrderEntryServiceImpl.insertPOrderEnDetail(poedObj, sessUserID);
            for(POrderEntryDTO aa: poe){
                logger.info("---infooo---"+aa.getPoValue());
            }
            
            Gson gson = new Gson();
            a = gson.toJson(b);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return a;
    }
    
    
    @RequestMapping(value = "/poEntryRecord")
    public @ResponseBody String getDdNumberRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<POrderEntryDTO>  list = pOrderEntryServiceImpl.getPOrderEnRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPoReleaseReById")
    public @ResponseBody String getDdNumberReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poRelease_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<POrderEntryDTO>  id_list = pOrderEntryServiceImpl.getPoReleaseReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updatePoReleaseDe")
    @ResponseBody
    public String updatePoReleaseDe(HttpServletRequest request, @RequestBody List<POrderEntryDTO> poedObj, ModelMap map,
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
            b = pOrderEntryServiceImpl.updatePoReleaseDetail(poedObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    
    @RequestMapping(value = "/getPoNoFromPOEn")
    public @ResponseBody String getPoNoFromPOEn(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "FromDate") String fromDate, @RequestParam(value = "ToDate") String toDate) throws AppException{
            Gson gson = new Gson();            
            List<POrderEntryDTO> a = pOrderEntryServiceImpl.getPoNoFromPoEn(fromDate, toDate);
            String json_a = gson.toJson(a);             
            return json_a;
    }
    
    @RequestMapping(value = "/getPlaceOfDeli")
    public @ResponseBody String getPlaceOfDeli(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<PlaceOfDeliveryDTO> a = pOrderEntryServiceImpl.getPlaceOfDelivery();            
            String json_a = gson.toJson(a);            
            return json_a;
    }
    
    @RequestMapping(value = "/getSection")
    public @ResponseBody String getSection(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<SectionDTO> sn = pOrderEntryServiceImpl.getSectionName();           
            String json_a = gson.toJson(sn);            
            return json_a;
    }
    
    @RequestMapping(value = "/getDesignation")
    public @ResponseBody String getDesignation(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<DesignationDTO> sn = pOrderEntryServiceImpl.getDesignationName();        
            String json_a = gson.toJson(sn);            
            return json_a;
    }
    
    @RequestMapping(value = "/getHeadOfAc")
    public @ResponseBody String getHeadOfAc(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<HeadOfAccountDTO> HAT = pOrderEntryServiceImpl.getHeadOfAccount();            
            String json_HAT = gson.toJson(HAT);            
            return json_HAT;
    }
    
    @RequestMapping(value = "/getVendorName")
    public @ResponseBody String getVendorName(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<VendorDTO> VDN = pOrderEntryServiceImpl.getVendorNames();            
            String json_VDN = gson.toJson(VDN);               
            logger.info("----json info-----"+json_VDN);
            return json_VDN;
    }
    
    @RequestMapping(value = "/getPoNoFrPOEntry")
    public @ResponseBody String getPoNoFrPOEntry(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<POrderEntryDTO> PONo = pOrderEntryServiceImpl.getPoNoOnlyFromPoEn();
            String json_PONo = gson.toJson(PONo);               
            logger.info("----json info-----"+json_PONo);
            return json_PONo;
    }
   
    @RequestMapping(value = "/getPoReleaseDeByPoNo")
    public @ResponseBody String getPoReleaseDeByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PONumber") long poNumBer) throws AppException{
            Gson gson = new Gson();            
            List<POrderEntryDTO>  poRelease_list = pOrderEntryServiceImpl.getPOrderEnDeByPoNo(poNumBer);
            return gson.toJson(poRelease_list);
    }
    
}
