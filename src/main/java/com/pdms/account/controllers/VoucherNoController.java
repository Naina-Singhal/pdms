/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.ItemsDto.HoaItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import com.pdms.account.ItemsDto.VouNoTempDTO;
import com.pdms.account.dto.LcTableDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.account.service.impl.VoucherDaEnServiceImpl;
import com.pdms.account.service.impl.VoucherNoServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.RtgsServiceImpl;
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
public class VoucherNoController {
    
     private static final Logger logger = Logger.getLogger(VoucherNoController.class);
    
    
    //Defult constructer
    public VoucherNoController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private VoucherNoServiceImpl voucherNoServiceImpl;
    
    @Autowired
    private VoucherDaEnServiceImpl vouDaEnSerImpl;
    
    @Autowired
    private RtgsServiceImpl rtgsServiceImpl;
    
    @RequestMapping(value = "/supplierVouNoEn")
    public ModelAndView supplierVouNoEnView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/VoucherNoEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveVoucherNoEn", consumes="application/json")
    @ResponseBody
    public String saveVoucherNoEn(HttpServletRequest request, @RequestBody VouNoTempDTO vounoObj, ModelMap map,
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
            
            b = voucherNoServiceImpl.insertVoucherNoEntry(vounoObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getVoucherNumberRecord")
    public @ResponseBody String getVoucherNumberRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<VoucherNoDTO>  list = voucherNoServiceImpl.getVoucherNoDaRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getVoucherNoReById")
    public @ResponseBody String getVoucherNoReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("VoucherNo_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<VoucherNoDTO>  id_list = voucherNoServiceImpl.getVoucherNumberReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateVoucherNumberDe")
    @ResponseBody
    public String updateVoucherNumberDe(HttpServletRequest request, @RequestBody List<VoucherNoDTO> vounoObj, ModelMap map,
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
            b = voucherNoServiceImpl.updateVoucherNumberDetail(vounoObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getVoucherEnDa")
    public @ResponseBody String getVoucherEnData(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<VoucherDTO> voNo = vouDaEnSerImpl.getVoucherDaRecord();
            String json_a = gson.toJson(voNo);            
            return json_a;
    }
    
    @RequestMapping(value = "/getVoucherNosOnly")
    public @ResponseBody String getVoucherNosOnly(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<VoucherNoDTO> voNo = voucherNoServiceImpl.getVoucherNos();                    
            return gson.toJson(voNo); 
    }
    
    @RequestMapping(value = "/getRequisitionNoInce")
    public @ResponseBody String getRequisitionNoInce(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            long reqNo = voucherNoServiceImpl.getRequisitionNoFrInc();
            return gson.toJson(reqNo); 
    }
    
    @RequestMapping(value = "/getRtgsNoFrRtgsMaByVen")
    public @ResponseBody String getRtgsNoFrRtgsMaByVen(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("vendorName") String venCode) throws AppException{
            Gson gson = new Gson();            
            List<RtgsDTO>  rtgslist = rtgsServiceImpl.getRtgsNoByVenCode(venCode);
            return gson.toJson(rtgslist);
    }
    
    @RequestMapping(value = "/getCompDaFrVouDaEn")
    public @ResponseBody String getCompDaFrVouDaEntry(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("CompCode") long CompCode) throws AppException{
            Gson gson = new Gson();            
            List<VoucherDTO>  Voulist = voucherNoServiceImpl.getVoucherDaEnDeByCompCode(CompCode);
            return gson.toJson(Voulist);
    }
    
    @RequestMapping(value = "/getVoucDaEnHoaReByPo")
    public @ResponseBody String getVoucDaEnHoaReByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poNo_id") long PoNumber) throws AppException{
            Gson gson = new Gson();            
            List<HoaItemsInVouDaEnDTO>  hoa_list = voucherNoServiceImpl.getVoucherDaEnHoaByPo(PoNumber);
            return gson.toJson(hoa_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveLcTable")
    @ResponseBody
    public String saveLcTable(HttpServletRequest request, @RequestBody List<LcTableDTO> lcObj, ModelMap map,
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
            
            b = voucherNoServiceImpl.saveLcTableDe(lcObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getOpenBalanceRe")
    public @ResponseBody String getOpenBalanceRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();
            List<LcTableDTO> lc = voucherNoServiceImpl.getOpenBalanceRecord();
            return gson.toJson(lc); 
    }
    
    @RequestMapping(value = "/voucherNoIbcPdf")
    public ModelAndView voucherNoIbcPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<VoucherNoDTO> ibcObj = voucherNoServiceImpl.getVoucherNumberReById(encIndId);

            return new ModelAndView("vouNoIbcPDFView", "vouNoIbcObj", ibcObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/rtgsLetterPdf")
    public ModelAndView rtgsLetterPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            List<VoucherNoDTO> rtgsObj = voucherNoServiceImpl.getVoucherNumberReById(encIndId);

            return new ModelAndView("rtgsLetterPDFView", "rtgsObj", rtgsObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
     @RequestMapping(value = "/rtgsOfficePdf")
    public ModelAndView rtgsOfficePdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("rtgsdate") String rtgs_date) {
        try {
            List<VoucherNoDTO> rtgObj = voucherNoServiceImpl.getVoucherNoDeOnDate(rtgs_date);

            return new ModelAndView("rtgsOfficePDFView", "rtgObj", rtgObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "rtgsOfficePdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
     @RequestMapping(value = "/securityDeFrMoPdf")
    public ModelAndView securityDeFrMoPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("headOfAccount") String headOfAccount, @RequestParam("dateAc") String dateAc) {
        try {
            List<VouNoHoaItemsDTO> secuObj = voucherNoServiceImpl.getVoucherNoItemsByHoaPdf(headOfAccount, dateAc);

            return new ModelAndView("securityFrMoPDFView", "secObj", secuObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "securityDeFrMoPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/emdDetailsForMoPdf")
    public ModelAndView emdDetailsFrMoPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("headOfAccount") String headOfAccount, @RequestParam("dateAc") String dateAc) {
        try {
            List<VouNoHoaItemsDTO> secuObj = voucherNoServiceImpl.getVoucherNoItemsByHoaPdf(headOfAccount, dateAc);

            return new ModelAndView("emdForMonthPDFView", "secObj", secuObj);
        } catch (Exception e) {
            logger.debug("Exception Occured while Executing the "
                    + "emdDetailsFrMoPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
}
