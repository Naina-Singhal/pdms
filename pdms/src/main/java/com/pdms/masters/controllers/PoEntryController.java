/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.account.service.impl.BillEntryServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoDetailsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.purchase.dto.PoTempDTO;
import com.pdms.service.impl.ComparativeStatementServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.vfs2.FileName;
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
public class PoEntryController {
    
    private static final Logger log = Logger.getLogger(PoEntryController.class);
    
    @Autowired
    private PoEntryServiceImpl poEntryServiceImpl;
    
    @Autowired
    private IndentServiceImpl indentServiceImpl;
    
    @Autowired
    private BillEntryServiceImpl billServiceImpl;
    
    @Autowired
    private ComparativeStatementServiceImpl cstService;
    
    public PoEntryController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/POEntryView")
    public ModelAndView POEntryView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/tender/POEntryGen");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
   
    
    @RequestMapping(method = RequestMethod.POST, value = "/savePOEntryDetails")
    @ResponseBody
    public String savePOEntryDetails(HttpServletRequest request, @RequestBody List<PoEntryDTO> unitObj, ModelMap map,
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
            
            b = poEntryServiceImpl.insertPoEntryDetail(unitObj, sessUserID);
            log.info("---info---"+b);
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
    
    @RequestMapping(value = "/getPoEntryRecord")
    public @ResponseBody String getPoEntryRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<PoEntryDTO>  list = poEntryServiceImpl.getPoEntryDetails();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getPoEntryReById")
    public @ResponseBody String getPoEntryReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("poEntry_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<PoEntryDTO>  id_list = poEntryServiceImpl.getPoEntryReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updatePoEntryDetails")
    @ResponseBody
    public String updatePoEntryDetails(HttpServletRequest request, @RequestBody List<PoEntryDTO> unitObj, ModelMap map,
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
            b = poEntryServiceImpl.updatePoEntryDetail(unitObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getFileNumber")
    public @ResponseBody String getFileNumber(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long fileNo = indentServiceImpl.getMaxFileNumberForIndentMapping();          
            String json_fileNo = gson.toJson(fileNo);   
            log.info("--file no---"+json_fileNo);
            return json_fileNo;
    }
    
    @RequestMapping(value = "/getPONoIncrement")
    public @ResponseBody String getPONoIncrement(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  a = poEntryServiceImpl.getPoNoForIncrement();            
            String json_PoNo = gson.toJson(a);   
            log.info("--po no---"+json_PoNo);
            return json_PoNo;
    }
    
    @RequestMapping(value = "/getVendorDetails")
    public @ResponseBody String getVendorDetails(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "vendorName") String vendorName) throws AppException{            
            Gson gson = new Gson();            
            List<VendorDTO>  vendor_list = poEntryServiceImpl.getVendorDetails(vendorName);
            String json_list = gson.toJson(vendor_list);         
            return json_list;
    }
    
    @RequestMapping(value = "/getPoDetailsByFileNo")
    public @ResponseBody String getPoDetailsByFileNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "PONumber") int PoNumber) throws AppException{            
            Gson gson = new Gson();            
            List<PoEntryDTO>  Po_list = poEntryServiceImpl.getPoEntryDeByFileNo(PoNumber);            
            return gson.toJson(Po_list);
    }
    
    @RequestMapping(value = "/PODetailsEntry")
    public ModelAndView PODetailsEntry(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/tender/PODetailsView");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/getPublicTenDetails")
    public @ResponseBody String getPublicTenderDetails(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "fileNum") int fiNum) throws AppException{
            Gson gson = new Gson();            
            List<PublicTenderItemsDTO>  pub_list = poEntryServiceImpl.getPublicTenderDeFrPo(fiNum);
            return gson.toJson(pub_list);
    }
    
    @RequestMapping(value = "/getFileNoFrTenByPo")
    public @ResponseBody String getFileNoFrTenByPo(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "PoNum") int PoNum) throws AppException{
            Gson gson = new Gson();            
            List<PoEntryDTO>  po_list = poEntryServiceImpl.getFileNoFrTenByPoNo(PoNum);
            return gson.toJson(po_list);
    }
    
    @RequestMapping(value = "/getItemDeByItemNo")
    public @ResponseBody String getItemDeByItemNumber(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "itemNo") int itemNo) throws AppException{
            Gson gson = new Gson();            
            List<ItemDTO>  It_list = poEntryServiceImpl.getItemMaDeByItemNo(itemNo);
            return gson.toJson(It_list);
    }
    
    @RequestMapping(value = "/getUnitDeByUnitNo")
    public @ResponseBody String getUnitDeByUnitNo(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "UnitNo") int unitNo) throws AppException{
            Gson gson = new Gson();            
            List<UnitDTO>  unLisst = poEntryServiceImpl.getUnitDeByUnitNumber(unitNo);
            return gson.toJson(unLisst);
    }
    
        
    @RequestMapping(method = RequestMethod.POST, value = "/savePoOrderItemsDe",consumes="application/json")
    @ResponseBody
    public String savePoOrderItemsDetails(HttpServletRequest request, @RequestBody PoTempDTO itObj, ModelMap map,
     HttpServletResponse response) {
            int b = 0;   
            Gson gson = new Gson();
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
            
            b = poEntryServiceImpl.insertPoEntryItemsDe(itObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);           
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getTenderIdByFileNo")
    public @ResponseBody String getTenderIdByFileNumber(HttpServletRequest request, HttpServletResponse response,
           @RequestParam(value = "fileNum") int fiNO) throws AppException{
            Gson gson = new Gson(); 
            return gson.toJson(poEntryServiceImpl.getTenderIdByFileNumber(fiNO));
    }
    
    @RequestMapping(value = "/getBreifDesFrTender")
    public @ResponseBody String getBreifDesFrTender(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson(); 
            return gson.toJson(poEntryServiceImpl.getBreifDesFrPubTender());
    }
    
    @RequestMapping(value = "/getBreifDescriFrIndentForm")
    public @ResponseBody String getBreifDesFrIndentForm(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson(); 
            return gson.toJson(poEntryServiceImpl.getBreifDesFrIndentForm());
    }
    
    @RequestMapping(value = "/getPoEntryDeByFileNo")
    public @ResponseBody
    String getPoEntryDeByFileNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "filNumber") int filNumber) throws AppException {
        Gson gson = new Gson();
        return gson.toJson(poEntryServiceImpl.getPoEntryDeByFileNumber(filNumber));
    }
    
    @RequestMapping(value = "/poPdfDownload")
    public ModelAndView purchaseOrPdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long encIndId) {
            ModelAndView modelView = new ModelAndView();
        try {            
            String fileNo = "0";
            long poNo = 0;
            List<PoEntryDTO> poObj = poEntryServiceImpl.getPoEntryReByIdFrPdf(encIndId);
            for(PoEntryDTO a: poObj){
                fileNo = a.getTenderFN();   
                poNo = a.getPoNumber();
            }           
            List<IndentFormDTO> indentfrm = cstService.getIndentFormIdByFileNo(Long.parseLong(fileNo));
            List<PoDetailsDTO> note = poEntryServiceImpl.getPoNoteDesByPoNo(poNo);
            List<IndentFileMappingDTO> group = cstService.getIndentdFormIdByFileNo(Long.parseLong(fileNo));
            
            modelView.addObject("poObj", poObj);
            modelView.addObject("indentForm", indentfrm);
            modelView.addObject("note", note);
            modelView.addObject("group", group);
            modelView.setViewName("purchaseOrPDFView");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "purchaseOrPdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
         return modelView;
    }
    
    @RequestMapping(value = "/poItemsPdfDownload")
    public ModelAndView poItemsPdfDownloadDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("ponum") long ponum) {
        try {
            List<PoDeItemsDTO> iteObj = billServiceImpl.getPoDeItemsByPoNumber(ponum);

            return new ModelAndView("purchaseOrItemPDFView", "itemsObj", iteObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "poItemsPdfDownloadDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/poRemainder")
    public ModelAndView PoRemainder(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/purchase/PoRemainder");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
}
