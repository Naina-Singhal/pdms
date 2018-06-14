/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.ItemsDto.BillTempDTO;
import com.pdms.account.dto.BillEntryDTO;
import com.pdms.account.dto.BillEntryItemsDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.service.impl.BillEntryServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.controllers.IndentManagementController;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.PaymentDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.UserPermissionServiceImpl;
import static java.lang.Integer.parseInt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.cellprocessor.ParseInt;

/**
 *
 * @author myassessment
 */
@Controller
public class BillEntryScreenController {
    
     private static final Logger log = Logger.getLogger(BillEntryScreenController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private BillEntryServiceImpl serviceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public BillEntryScreenController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/billEntryScreen")
    public ModelAndView billEntryScreenView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));            
            modelView.setViewName("/accounts/BillEntryScreen");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveBillEntry", consumes="application/json")
    @ResponseBody
    public String saveBillEntryScreen(HttpServletRequest request, @RequestBody BillTempDTO billObj, ModelMap map,
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
            
            b = serviceImpl.insertBillDaEntry(billObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/BillEntryRe")
    public ModelAndView BillEntryRecord(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));             
            modelView.setViewName("/accounts/BillEnRecord");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/getBillEnJsonRe")
    public @ResponseBody String getBillEnJsonRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<BillEntryDTO>  billRe = serviceImpl.getBillEntryRecord();
            return gson.toJson(billRe);
    }
    
    @RequestMapping(value = "/getVendorNaFrPoEn")
    public @ResponseBody String getVendorNaFrPoEn(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PoEntryDTO>  a = serviceImpl.getVenderNaFrPoEn();            
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/getPaymentTerms")
    public @ResponseBody String getPaymentTerms(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PaymentDTO>  a = serviceImpl.getPaymentTerms();
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/getGstinNumber")
    public @ResponseBody String getGstinNumber(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<VendorDTO>  a = serviceImpl.getGstinNumberFrVen();
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/getBillEntryDa")
    public @ResponseBody String getBillEntryData(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<BillEntryDTO>  a = serviceImpl.getBillEntryNumRe();
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/editBillEntry")
    public ModelAndView editBillEntry(HttpServletRequest request, HttpServletResponse response, 
            @RequestParam("editId") String encBill_ID) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));            
            modelView.addObject("encBill_ID", encBill_ID);
            modelView.setViewName("/accounts/BillEntryEdit");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getBillEnDeFrEdit")
    public @ResponseBody String getBillEnDeFrEdit(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Bill_ID") long Billid) throws AppException{
            Gson gson = new Gson();            
            List<BillEntryDTO>  bl_id_list = serviceImpl.getBillEnEditLi(Billid);
            return gson.toJson(bl_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateBillEntry")
    @ResponseBody
    public String updateBillEntry(HttpServletRequest request, @RequestBody List<BillEntryDTO> billObj, ModelMap map,
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
            b = serviceImpl.updateBillEntry(billObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/BillEntryReSuc")
    public ModelAndView BillEntryReSuc(HttpServletRequest request,
            HttpServletResponse response, @RequestParam("message") String message,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try {
            log.info("------message--------" + message);
            if (parseInt(message) > 0) {
                redirectAttributes.addFlashAttribute("msg", "Bill Entry data Updated Successfully");
                modelView.setViewName("redirect:/BillEntryRe.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
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
    
    @RequestMapping(value = "/getBillEnReByPoNum")
    public @ResponseBody String getBillEnReByPoNum(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PoNum") long PONum) throws AppException{
            Gson gson = new Gson();            
            List<BillEntryDTO>  b_list = serviceImpl.getBillEntryReByPoNo(PONum);
            return gson.toJson(b_list);
    }
    
    @RequestMapping(value = "/getPoDeItemDeByPoNo")
    public @ResponseBody String getPoDeItemDeByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poNumb") long PONum) throws AppException{
            Gson gson = new Gson();            
            List<PoDeItemsDTO>  items = serviceImpl.getPoDeItemsByPoNumber(PONum);
            return gson.toJson(items);
    }
    
    @RequestMapping(value = "/getItemDeByItemCode")
    public @ResponseBody String getItemDetailsByItemCode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("itemCode") long itemCode) throws AppException{
            Gson gson = new Gson();            
            List<ItemDTO>  item = serviceImpl.getItemMaDeByItemCode(itemCode);
            return gson.toJson(item);
    }
    
    @RequestMapping(value = "/getUnitDeByUnitCode")
    public @ResponseBody String getUnitDeByUnitCode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("UnitCode") long unitCode) throws AppException{
            Gson gson = new Gson();            
            List<UnitDTO>  unit = serviceImpl.getUnitDeByUnitCode(unitCode);
            return gson.toJson(unit);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveBillEnItemList")
    @ResponseBody
    public String saveBillEnItemList(HttpServletRequest request, @RequestBody List<BillEntryItemsDTO> itemsObj, ModelMap map,
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
            
            b = serviceImpl.insertBillEntryItemsData(itemsObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/lrForwardPdfDown")
    public ModelAndView lrForwardingPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<BillEntryDTO> lrObj = serviceImpl.getBillEnEditLi(encIndId);

            return new ModelAndView("lrForwardingPDFView", "LrObj", lrObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
