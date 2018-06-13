/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.DescriptionDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import com.pdms.service.impl.DescriptionServiceImpl;
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
public class IbcMasterController {
    
    private static final Logger log = Logger.getLogger(IbcMasterController.class);
    
    //Default constructor 
    public IbcMasterController(){
        
    }
    
    @Autowired
    private IbcNumbeServiceImpl ibcNumbeServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
            
    @RequestMapping(value = "/ibcNumberMa")
    public ModelAndView ibcNumberMaView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/IbcNumberView");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveIbcNumber")
    @ResponseBody
    public String saveIbcNumber(HttpServletRequest request, @RequestBody List<IbcNumberDTO> ibcObj, ModelMap map,
     HttpServletResponse response) {
            
            String ibcJSOn = null;
            int ibcRet = 0;           
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
            
            ibcRet = ibcNumbeServiceImpl.insertIbcNoDetail(ibcObj, sessUserID);
            log.info("---info---"+ibcRet);
            Gson gson = new Gson();
            ibcJSOn = gson.toJson(ibcRet);
            
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return ibcJSOn;
    }
    
    @RequestMapping(value = "/getIBCnumberRe")
    public @ResponseBody String getIBCnumberRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<IbcNumberDTO>  des_list = ibcNumbeServiceImpl.getIbcNoDetails();
            String json_list = gson.toJson(des_list);
            return json_list;
    }
    
    @RequestMapping(value = "/getIbcNumberReById")
    public @ResponseBody String getIbcNumberReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("ibcNo_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<IbcNumberDTO>  rt_id_list = ibcNumbeServiceImpl.getIbcNoReById(id);
            return gson.toJson(rt_id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateIbcNumber")
    @ResponseBody
    public String updateIbcNumber(HttpServletRequest request, @RequestBody List<IbcNumberDTO> ibcObj, ModelMap map,
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
            b = ibcNumbeServiceImpl.updateIbcNoDetail(ibcObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getIbcNumInc")
    public @ResponseBody String getIbcNumInc(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  des_list = ibcNumbeServiceImpl.getIbcNoForIncrement();            
            return gson.toJson(des_list);
    }
    
    @RequestMapping(value = "/getIbcNoFrIbcMaByPo")
    public @ResponseBody String getIbcNoFrIbcMaByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("PoNumb") long pono) throws AppException{
            Gson gson = new Gson();            
            List<IbcNumberDTO>  po_list = ibcNumbeServiceImpl.getIbcNoDeByPoNo(pono);
            return gson.toJson(po_list);
    }
}
