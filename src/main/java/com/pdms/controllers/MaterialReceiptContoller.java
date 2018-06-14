/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.IssueVocherDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.MaterialReceItemDTO;
import com.pdms.dto.MaterialReceLrCleDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.testDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptTempDTO;
import com.pdms.itemsDto.receipt.PoReleaseItemsDTO;
import com.pdms.service.impl.MaterialReceiptServiceImpl;
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
import sun.net.www.MeteredStream;

/**
 *
 * @author myassessment
 */
@Controller
public class MaterialReceiptContoller {
    
    private static final Logger logger = Logger.getLogger(IssueVocherDAOImpl.class);
    
    @Autowired
    private MaterialReceiptServiceImpl materialReceiptServiceImpl;
    
    public MaterialReceiptContoller(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private  POrderEntryServiceImpl poReleSerImpl;
    
    @RequestMapping(value = "/materialReceipt")
    public ModelAndView materialReceiptView(HttpServletRequest request, HttpServletResponse response) {
            ModelAndView modelView = new ModelAndView();             
            try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/MaterialReceipt");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
            return modelView;
    }
    
    @RequestMapping(value = "/testTest")
    public ModelAndView testTest(HttpServletRequest request, HttpServletResponse response) {
            ModelAndView modelView = new ModelAndView();
            modelView.setViewName("/receipt/test");   
            return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/dataList")
    @ResponseBody
    public void dataList(HttpServletRequest request, @RequestBody List<testDTO> test, ModelMap map) {
            ModelAndView modelView = new ModelAndView();
           
    }
    
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveMaterialRecipet",consumes="application/json")
    @ResponseBody
    public String saveMaterialRecipet(HttpServletRequest request, @RequestBody MaterialReceiptTempDTO mareObj, ModelMap map,
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
            
            b = materialReceiptServiceImpl.insertMaterialReceipt(mareObj, sessUserID);
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
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveMeteReceItemData")
    @ResponseBody
    public String saveMeteReceItemData(HttpServletRequest request, @RequestBody List<MaterialReceItemDTO> mrit, ModelMap map,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        String MsG = "null";
        try {
            long sessUserID = 0;
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

                sessUserID = empDTO.getEmployeeID();
            }

            int msg = materialReceiptServiceImpl.insertIssueVoucherItemData(mrit, sessUserID);
            Gson gson = new Gson();
            MsG = gson.toJson(msg);

        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return MsG;
    }
    
    
    @RequestMapping(value = "/materialReceiptRecord")
    public @ResponseBody String materialReceiptRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<MaterialReceiptDTO>  list = materialReceiptServiceImpl.getMaterialReceRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getMaterialRecptReById")
    public @ResponseBody String getMaterialRecptReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("recpt_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<MaterialReceiptDTO>  id_list = materialReceiptServiceImpl.getMaterialReceiptReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateMaterialReceiptDe")
    @ResponseBody
    public String updateMaterialReceiptDe(HttpServletRequest request, @RequestBody List<MaterialReceiptDTO> mareObj, ModelMap map,
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
            b = materialReceiptServiceImpl.updateMaterialReceiptDetail(mareObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getDbNoReFromMeteRec")
    public @ResponseBody String getDbNoReFromMeteRec(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "DbNumber") int DbNumber) throws AppException{                    
            Gson gson = new Gson();            
            List<MaterialReceiptDTO> a = materialReceiptServiceImpl.getMaterialRecReByDbNo(DbNumber);
            String json_a = gson.toJson(a);            
            return json_a;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveMaterialReceCleDa")
    @ResponseBody
    public String saveMaterialReceCleDa(HttpServletRequest request, @RequestBody List<MaterialReceLrCleDTO> cleObj, ModelMap map,
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
            
            b = materialReceiptServiceImpl.insertMaterialReceCleData(cleObj, sessUserID);
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
    
    @RequestMapping(value = "/getDbNumIncreament")
    public @ResponseBody String getDbNumIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  dbIncre = materialReceiptServiceImpl.getDbNumForIncrement();
            return gson.toJson(dbIncre); 
    }
    
    @RequestMapping(value = "/getMeterialReceByPoNo")
    public @ResponseBody String getMeterialReceByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "PONuMbeR") int P0NuMber) throws AppException{                    
            Gson gson = new Gson();            
            List<MaterialReceiptDTO> aa = materialReceiptServiceImpl.getMaterialRecReByPoNo(P0NuMber);                       
            return gson.toJson(aa);
    }
    
    @RequestMapping(value = "/getPoReleaseItemsDeByPoNo")
    public @ResponseBody String getPoReleaseDeByPoNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PONumber") long pono) throws AppException{
            Gson gson = new Gson();            
            List<PoReleaseItemsDTO>  poRelease_list = poReleSerImpl.getPoReleaseItemsReByPo(pono);
            return gson.toJson(poRelease_list);
    }
}
