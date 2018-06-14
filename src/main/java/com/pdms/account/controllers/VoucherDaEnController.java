/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.VouTempDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.service.impl.BillEntryServiceImpl;
import com.pdms.account.service.impl.VoucherDaEnServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.HoaCodesServiceImpl;
import com.pdms.master.service.impl.RtgsServiceImpl;
import com.pdms.masters.controllers.RtgsController;
import com.pdms.service.impl.UserPermissionServiceImpl;
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

/**
 *
 * @author myassessment
 */
@Controller
public class VoucherDaEnController {
    
    private static final Logger log = Logger.getLogger(VoucherDaEnController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private VoucherDaEnServiceImpl vouDaEnSeImpl;
    
    @Autowired
    private BillEntryServiceImpl billEntrySerImpl;
    
    @Autowired
    private HoaCodesServiceImpl codesServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public VoucherDaEnController()
    {
        
    }
    /*
        Default Constructor
    */ 
    
    
    @RequestMapping(value = "/voucherDataEnView")
    public ModelAndView voucherDataEnView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/VoucherDataEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping( value = "/saveVoucherDataEn",method = RequestMethod.POST,consumes="application/json")
    @ResponseBody
    public String saveVoucherDataEn(HttpServletRequest request, @RequestBody VouTempDTO vouObj,
     HttpServletResponse response, ModelMap map) {
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
            
            List<VoucherDTO> vou = vouObj.getVoucherDTO();                     
            b = vouDaEnSeImpl.insertVoucherDaEntry(vouObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getVoucherDaEnRecord")
    public @ResponseBody String getVoucherDaEnRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<VoucherDTO>  list = vouDaEnSeImpl.getVoucherDaRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getVoucherDaEnReById")
    public @ResponseBody String getVoucherDaEnReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("VouCher_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<VoucherDTO>  id_list = vouDaEnSeImpl.getVoucherDaEnReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateVoucherDataEnDe")
    @ResponseBody
    public String updateVoucherDataEnDe(HttpServletRequest request, @RequestBody List<VoucherDTO> vouObj, ModelMap map,
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
            b = vouDaEnSeImpl.updateVoucherDaEnDetail(vouObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getPprNoFrInc")
    public @ResponseBody String getPprNoFrIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  a = vouDaEnSeImpl.getPprNoForIncrement();            
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/getCompCodeInc")
    public @ResponseBody String getCompCodeInc(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  a = vouDaEnSeImpl.getCompCodeForIncrement();
            return gson.toJson(a);
    }
    
    @RequestMapping(value = "/getBillEnItemsByPoNo")
    public @ResponseBody String getBillEnItemsByPoNo(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "PONumBer") long poNumBer) throws AppException{
            Gson gson = new Gson();             
            return gson.toJson(billEntrySerImpl.getBillItemsListByPoNo(poNumBer));
    }
    
    @RequestMapping(value = "/getHoaSsortByAcName")
    public @ResponseBody String getHoaSsortByAcName(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "acName") String acName) throws AppException{
            Gson gson = new Gson();             
            return gson.toJson(codesServiceImpl.getHoaCodesReByAcName(acName));
    }
    
    @RequestMapping(value = "/voucherDataPdf")
    public ModelAndView voucherDataPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<ItemsInVouDaEnDTO> vouItemsObj = vouDaEnSeImpl.getVouDaEnItemWithBillEnBeDates(encIndId);          
            return new ModelAndView("vouDataEnPDFView", "vouitObj", vouItemsObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "voucherDataPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/acPdfButtonList")
    public ModelAndView acPdfButtonList(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/PdfAcButtons");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/voucherDaHrauRpuPdf")
    public ModelAndView voucherDaHrauRpuPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            //List<ItemsInVouDaEnDTO> vouItemsObj = vouDaEnSeImpl.getVouDaEnItemWithBillEnBeDates(encIndId);          
            return new ModelAndView("vouDaHrauRpuPDFView", "rpuObj", null);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "voucherDaHrauRpuPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
