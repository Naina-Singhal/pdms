/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationTempDTO;
import com.pdms.itemsDto.receipt.InspectionCleaItemsDTO;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.service.impl.CsrvPreparationServiceImpl;
import com.pdms.service.impl.InspectionClearanceServiceImpl;
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
public class CsrvPreparationController {
    
    private static final Logger logger = Logger.getLogger(CsrvPreparationController.class);
    
    @Autowired
    private CsrvPreparationServiceImpl csrvPreparationServiceImpl;
    
    public CsrvPreparationController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private InspectionClearanceServiceImpl inspeCleaServImpl;
    
    @RequestMapping(value = "/csrvPreparation")
    public ModelAndView csrvPreparationView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/csrvPrepaView");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveCsrvPreparation",consumes="application/json")
    @ResponseBody
    public String saveCsrvPreparation(HttpServletRequest request, @RequestBody CsrvPreparationTempDTO csrvObj, ModelMap map,
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
            
            b = csrvPreparationServiceImpl.insertCsrvPreparationDetails(csrvObj, sessUserID);
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
    
    @RequestMapping(value = "/getCsrvPrepaRecord")
    public @ResponseBody String getCsrvPrepaRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<CSRVpreparationDTO>  list = csrvPreparationServiceImpl.getCsrvPreparationRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getCsrvPrepaReById")
    public @ResponseBody String getCsrvPrepaReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("csrv_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<CSRVpreparationDTO>  id_list = csrvPreparationServiceImpl.getCsrvPreparationReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateCsrvPreparation")
    @ResponseBody
    public String updateCsrvPreparation(HttpServletRequest request, @RequestBody List<CSRVpreparationDTO> csrvObj, ModelMap map,
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
            b = csrvPreparationServiceImpl.updateCsrvPreparationDetail(csrvObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRvNumIncreament")
    public @ResponseBody String getRvNumIncreament(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  dbIncre = csrvPreparationServiceImpl.getRvNumForIncrement();
            return gson.toJson(dbIncre); 
    }
    
    @RequestMapping(value = "/getCsrvDeByPoNomber")
    public @ResponseBody String getCsrvDeByPoNomber(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PoNo_no") long PoNumber) throws AppException{
            Gson gson = new Gson();            
            List<CSRVpreparationDTO>  id_list = csrvPreparationServiceImpl.getCsrvPreparationReByPo(PoNumber);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(value = "/getInspecCleaItemsReByDbNo")
    public @ResponseBody String getInspecCleaItemsReByDbNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("dbnumber_Id") long DbNumber) throws AppException{
            Gson gson = new Gson();            
            List<InspectionCleaItemsDTO>  list = inspeCleaServImpl.getInspectionCleaItemsByDbNo(DbNumber);
            return gson.toJson(list);
    }
}
