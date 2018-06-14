/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.InspectionClearanceDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.InspectionCleaTempDTO;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.service.impl.InspectionClearanceServiceImpl;
import com.pdms.service.impl.MaterialReceiptServiceImpl;
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
public class InspectionClearanceController {
    
    private static final Logger logger = Logger.getLogger(InspectionClearanceController.class);
    
    public InspectionClearanceController(){
        
    }
    
    @Autowired
    private InspectionClearanceServiceImpl clearanceServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private MaterialReceiptServiceImpl mateReceServiceImpl;
    
    
    @RequestMapping(value = "/inspectionClearance")
    public ModelAndView inspectionClearanceView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/InspectionClearance");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
        
    @RequestMapping(method = RequestMethod.POST, value = "/saveInspeClearance",consumes="application/json")
    @ResponseBody
    public String saveInspeClearance(HttpServletRequest request, @RequestBody InspectionCleaTempDTO inspectObj, ModelMap map,
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
            
            b = clearanceServiceImpl.insertInspeClearance(inspectObj, sessUserID);
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
    
    @RequestMapping(value = "/inspectionCleaRecord")
    public @ResponseBody String inspectionCleaRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<InspectionClearanceDTO>  list = clearanceServiceImpl.getInspectionClearanceRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getInspeClearReById")
    public @ResponseBody String getInspeClearReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("inspeClear_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<InspectionClearanceDTO>  id_list = clearanceServiceImpl.getInspecCleareReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateInspecClearRe")
    @ResponseBody
    public String updateInspecClearRe(HttpServletRequest request, @RequestBody List<InspectionClearanceDTO> inspectObj, ModelMap map,
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
            b = clearanceServiceImpl.updateInspectCleareDetail(inspectObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getICNoIncrement")
    public @ResponseBody String getICNoIncrement(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  a = clearanceServiceImpl.getICNoForIncrement();
            String json_ICNo = gson.toJson(a);   
            logger.info("--json increment---"+json_ICNo);
            return json_ICNo;
    }
    
    @RequestMapping(value = "/getDbNoFrMeteReceDeByUser")
    public @ResponseBody String getDbNoFrMeteReceDeByUser(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("User_Id") long userId) throws AppException{
            Gson gson = new Gson();            
            List<MaterialReceiptDTO>  list = mateReceServiceImpl.getMateReceDbNosByUser(userId);
            return gson.toJson(list);
    }
    
    @RequestMapping(value = "/getMateReceiptItemsByDbNo")
    public @ResponseBody String getMateReceiptItemsByDbNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("dbnumber_Id") long DbNuMber) throws AppException{
            Gson gson = new Gson();            
            List<MaterialReceiptItemsDTO>  list = mateReceServiceImpl.getMaterialRecItemsReByDbNo(DbNuMber);
            return gson.toJson(list);
    }
    
}
