/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.controllers;

import com.google.gson.Gson;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.OilDataDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.service.impl.OilDataServiceImpl;
import com.pdms.account.service.impl.VoucherDaEnServiceImpl;
import com.pdms.constants.SessionConstants;
import com.pdms.controllers.PreAuditController;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
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
public class OilsDataEnController {
    
    private static final Logger logger = Logger.getLogger(PreAuditController.class);
    
    
    //Defult constructer
    public OilsDataEnController(){
        
    }
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private OilDataServiceImpl serviceImpl;
    
    @Autowired
    private VoucherDaEnServiceImpl vouServiceImpl;
    
    
    @RequestMapping(value = "/oilsDataEntry")
    public ModelAndView oilsDataEntryView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/accounts/OilsDataEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveOilDataEn")
    @ResponseBody
    public String saveOilDataEn(HttpServletRequest request, @RequestBody List<OilDataDTO> oilObj, ModelMap map,
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
            
            b = serviceImpl.insertOilDaEntry(oilObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);           
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getOilDataRecord")
    public @ResponseBody String getOilDataRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<OilDataDTO>  list = serviceImpl.getOilDataRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getOilDataReById")
    public @ResponseBody String getOilDataReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("oilData_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<OilDataDTO>  id_list = serviceImpl.getOilDataReByID(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateOilDataDetails")
    @ResponseBody
    public String updateOilDataDetails(HttpServletRequest request, @RequestBody List<OilDataDTO> oilObj, ModelMap map,
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
            b = serviceImpl.updateOilDataDetail(oilObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    
    @RequestMapping(value = "/getVouDaEnPoDeById")
    public @ResponseBody String getVouDaEnPoDeById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PoVouDaEn_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<VoucherDTO>  vou_list = vouServiceImpl.getVoucherPoDetails(id);
            return gson.toJson(vou_list);
    }
}
