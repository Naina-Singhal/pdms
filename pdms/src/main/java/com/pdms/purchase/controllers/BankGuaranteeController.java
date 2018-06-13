/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.purchase.dto.BankGuaranteeDTO;
import com.pdms.purchase.services.Impl.BankGuaranteeServiceImpl;
import com.pdms.service.impl.ComparativeStatementServiceImpl;
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
import org.supercsv.cellprocessor.ParseBigDecimal;

/**
 *
 * @author myassessment
 */
@Controller
public class BankGuaranteeController {
    private static final Logger log = Logger.getLogger(BankGuaranteeController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl; 
    
    @Autowired
    private BankGuaranteeServiceImpl guaranteeServiceImpl; 
    
    @Autowired
    private PoEntryServiceImpl poEntryImpl;
    
    @Autowired
    private ComparativeStatementServiceImpl cstService;
     /*
    End : Autowiring of Fields
     */
     /*
        Default Constructor
    */
    public BankGuaranteeController()
    {
        
    }
    /*
        Default Constructor
    */ 
    @RequestMapping(value = "/bankGarantee")
    public ModelAndView bankGarantee(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/purchase/BankGuarantee");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveBankGuarantee")
    @ResponseBody
    public String saveBankGuarantee(HttpServletRequest request, @RequestBody List<BankGuaranteeDTO> guaObj, ModelMap map,
     HttpServletResponse response) {
            
            String guaJSON = null;
            int bankGua = 0;           
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
            
            bankGua = guaranteeServiceImpl.insertBankGuaranteeDetail(guaObj, sessUserID);            
            Gson gson = new Gson();
            guaJSON = gson.toJson(bankGua);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return guaJSON;
    }
    
    @RequestMapping(value = "/getBankGuaranteeDeByPo")
    public @ResponseBody String getBankGuaranteeDeByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PONumber") long poNum) throws AppException{
            Gson gson = new Gson();            
            List<BankGuaranteeDTO>  Gua_list = guaranteeServiceImpl.getBankGuaranteeDeByPo(poNum);
            return gson.toJson(Gua_list);
    }
    
    @RequestMapping(value = "/getBankGuaranteeRecord")
    public @ResponseBody String getBankGuaranteeRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<BankGuaranteeDTO>  list = guaranteeServiceImpl.getBankGuaranteeRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getBankGuaranteeReById")
    public @ResponseBody String getBankGuaranteeReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Guarantee_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<BankGuaranteeDTO>  id_list = guaranteeServiceImpl.getBankGuaranteeReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateBankGuarantee")
    @ResponseBody
    public String updateBankGuarantee(HttpServletRequest request, @RequestBody List<BankGuaranteeDTO> guaObj, ModelMap map,
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
            b = guaranteeServiceImpl.updateBankGuaranteeDetail(guaObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/generateSlNoIncre")
    public @ResponseBody String generateSlNoIncre(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  nor = guaranteeServiceImpl.geneSlNoIncreFrBankGua();
            return gson.toJson(nor);
    }
    
     @RequestMapping(value = "/bankGuaranteePdf")
    public ModelAndView bankGuaranteePdfDownload(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") long bankid) {
        ModelAndView modelView = new ModelAndView();
        try {   
            String poNo = "";
            String fileNo = "";
            List<BankGuaranteeDTO> secuObj = guaranteeServiceImpl.getBankGuaranteeReByIdPdf(bankid);
            
            for(BankGuaranteeDTO b: secuObj){
                
                poNo = String.valueOf(b.getPoNumber());
                log.info("-----aaaaa-------"+b.getBgNumber());
            }
            List<PoEntryDTO> po = poEntryImpl.getPoEntryDeByFileNo(Integer.parseInt(poNo));
            for(PoEntryDTO p: po){
                fileNo = p.getTenderFN();
                log.info("-----bbbbb-------"+fileNo);
            }
            List<IndentFormDTO> indentfrm = cstService.getIndentFormIdByFileNo(Long.parseLong(fileNo));
            List<IndentFileMappingDTO> group = cstService.getIndentdFormIdByFileNo(Long.parseLong(fileNo));

            modelView.addObject("baObj", secuObj);
            modelView.addObject("inObj", indentfrm);
            modelView.addObject("gropObj", group);
            modelView.addObject("poObj", po);
            modelView.setViewName("bankGuaranteePDFView");            
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "bankGuaranteePdfDownload :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(value = "/bgRemainder")
    public ModelAndView bgRemainder(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));           
            modelView.setViewName("/purchase/BgRemainder");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
}
