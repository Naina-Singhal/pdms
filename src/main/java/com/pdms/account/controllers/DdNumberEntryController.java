/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dao.impl.VoucherNoDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.account.service.impl.DdNumberServiceImpl;
import com.pdms.account.service.impl.VoucherNoServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.VendorDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.dto.TypeOfDispatchDTO;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
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
public class DdNumberEntryController {
    private static final Logger log = Logger.getLogger(DdNumberEntryController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private VoucherNoServiceImpl voucherNoServiceImpl;
    
    @Autowired
    private VendorServiceImpl vendorServiceImpl;
    
    @Autowired
    private DdNumberServiceImpl ddNumberServiceImpl;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public DdNumberEntryController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/ddNumberEn")
    public ModelAndView ddNumberEntry(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/accounts/DdNumberEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getVouNoDeByReqNo")
    public @ResponseBody String getVouNoDeByReqNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("requiNo") long reqNumber) throws AppException{
            Gson gson = new Gson();            
            List<VoucherNoDTO>  list = voucherNoServiceImpl.getVoucherNoDeByReqNo(reqNumber);
            return gson.toJson(list);
    }
    
    @RequestMapping(value = "/getVendorNameByCode")
    public @ResponseBody String getVendorNameByCode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("venCode") String venCode) throws AppException{
            Gson gson = new Gson();            
            List<VendorDTO>  listV = vendorServiceImpl.getVendorNameByCode(venCode);
            return gson.toJson(listV);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/savedDdNumberAc")
    @ResponseBody
    public String savedDdNumberAc(HttpServletRequest request, @RequestBody List<DdNumberDTO> ddObj, ModelMap map,
     HttpServletResponse response) {
            int ibcRet = 0;           
            HttpSession session = request.getSession();
            Gson gson = new Gson();
        try
        {
            long sessUserID=0;
            if(session.getAttribute(SessionConstants.USER_SESSION) != null)
            {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO)
                                (session.getAttribute(SessionConstants.USER_SESSION));
                
                sessUserID = empDTO.getEmployeeID();
            }
            
            ibcRet = ddNumberServiceImpl.insertDdNumberDetails(ddObj, sessUserID);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return gson.toJson(ibcRet);
    }
    
    @RequestMapping(value = "/getDdNumberRecord")
    public @ResponseBody String getDdNumberRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<DdNumberDTO>  list = ddNumberServiceImpl.getDdNumberRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getDdNumberReById")
    public @ResponseBody String getDdNumberReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("ddNo_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<DdNumberDTO>  id_list = ddNumberServiceImpl.getDdNumberReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateDdNumberAc")
    @ResponseBody
    public String updateDdNumberAc(HttpServletRequest request, @RequestBody List<DdNumberDTO> ddObj, ModelMap map,
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
            b = ddNumberServiceImpl.updateDdNumberDetail(ddObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/ddForwardingPdf")
    public ModelAndView ddForwardingPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
        try {
            //List<ItemsInVouDaEnDTO> vouItemsObj = vouDaEnSeImpl.getVouDaEnItemWithBillEnBeDates(encIndId);          
            return new ModelAndView("ddForwardingPDFView", "ddObj", null);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ddForwardingPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
}
