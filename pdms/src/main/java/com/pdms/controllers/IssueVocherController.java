/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.IssueVocherDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.testDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.IssueVoucherTempDTO;
import com.pdms.itemsDto.receipt.RcivControlItemsDTO;
import com.pdms.service.impl.IssueVocherServiceImpl;
import com.pdms.service.impl.RcivControlServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
 * @author STEINMETZ
 */
@Controller
public class IssueVocherController {
    
    private static final Logger logger = Logger.getLogger(IssueVocherDAOImpl.class);
    
    public IssueVocherController(){
        
    }
    
    @Autowired
    private IssueVocherServiceImpl issueVoServiceImpl;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private RcivControlServiceImpl rcivConSerImpl;
    
    @RequestMapping(value = "/issueVocherEntry")
    public ModelAndView rcivReleaseView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/IssueVocherEntry");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/saveIssueVoucher",consumes="application/json")
    @ResponseBody
    public String saveIssueVoucher(HttpServletRequest request, @RequestBody IssueVoucherTempDTO issueVoucher, ModelMap map,
     HttpServletResponse response) {           
            int b = 0;           
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
            
            b = issueVoServiceImpl.insertIssueVoucher(issueVoucher, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);            
        }
        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getIssueVoucherRecord")
    public @ResponseBody String getIssueVoucherRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<IssueVoucherDTO>  list = issueVoServiceImpl.getIssueVoucherRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getIssueVoucherReById")
    public @ResponseBody String getIssueVoucherReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("IssueVou_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<IssueVoucherDTO>  id_list = issueVoServiceImpl.getIssueVoucherReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateIssueVoucherDe")
    @ResponseBody
    public String updateIssueVoucherDe(HttpServletRequest request, @RequestBody List<IssueVoucherDTO> issueVoucher, ModelMap map,
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
            b = issueVoServiceImpl.updateIssueVoucherDetail(issueVoucher, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRcivControlItemReByPo")
    public @ResponseBody String getRcivControlItemReByPo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("ConNumber_Id") long DbNumber) throws AppException{
            Gson gson = new Gson();            
            List<RcivControlItemsDTO>  item_list = rcivConSerImpl.getRcivControlItemsByPoNo(DbNumber);
            return gson.toJson(item_list);
    }
}
