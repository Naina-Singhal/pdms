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
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialRequisitionTempDTO;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.MaterialRequisitionServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
public class MaterialRequisitionController {
    
    @Autowired
    private MaterialRequisitionServiceImpl materialRequisitionServiceImpl;
    
    @Autowired
    private CategoryServiceImpl categoryService;
    
    @Autowired
    private ItemServiceImpl itemServiceImpl;
    
    public MaterialRequisitionController(){
        
    }
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @Autowired
    private IndentServiceImpl indentServiceImpl;
    
    @RequestMapping(value = "/materialRequisition")
    public ModelAndView materialRequisitionView(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/receipt/MaterialRequisition");
        } catch (Exception e) {
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
        
    @RequestMapping(method = RequestMethod.POST, value = "/saveMaterialRequiSi",consumes="application/json")
    @ResponseBody
    public String saveMaterialRequiSitionData(HttpServletRequest request, @RequestBody MaterialRequisitionTempDTO matrsObj, ModelMap map,
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
            
            b = materialRequisitionServiceImpl.insertMaterialRequisition(matrsObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }        
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getMaterialRequiRecord")
    public @ResponseBody String getMaterialRequiRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<MaterialRequisitionDTO>  list = materialRequisitionServiceImpl.getMaterialRequiRecord();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getMaterialRquiReById")
    public @ResponseBody String getMaterialRquiReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Requisition_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<MaterialRequisitionDTO>  id_list = materialRequisitionServiceImpl.getMaterialRequisitionReById(id);
            return gson.toJson(id_list);
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateMaterialRquisition")
    @ResponseBody
    public String updateMaterialRquisition(HttpServletRequest request, @RequestBody List<MaterialRequisitionDTO> matrsObj, ModelMap map,
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
            b = materialRequisitionServiceImpl.updateMaterialRquisitionDetail(matrsObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
    @RequestMapping(value = "/getRequiSitionNoIncrea")
    public @ResponseBody String getRequiSitionNoIncrea(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            long  Incre = materialRequisitionServiceImpl.getRequisitionForIncrement();
            return gson.toJson(Incre); 
    }
    
    @RequestMapping(value = "/getMaterialRquiReByRequiNo")
    public @ResponseBody String getMaterialRquiReByRequiNo(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Requisition_no") long requiNo) throws AppException{
            Gson gson = new Gson();            
            List<MaterialRequisitionDTO>  list = materialRequisitionServiceImpl.getMaterialRequiReByRequiNo(requiNo);
            return gson.toJson(list);
    }
    
    @RequestMapping(value = "/getEmpDeByEmpId")
    public @ResponseBody String getEmpDeByEmpId(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("employee_id") long empid) throws AppException{
            Gson gson = new Gson();            
            List<EmployeeProfileDTO>  list = indentServiceImpl.getEmpProfileDeByEmpID(empid);
            return gson.toJson(list);
    }
    
    @RequestMapping(value = "/getCategoryAllDetails")
    public @ResponseBody String getCategoryAllDetails(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<CategoryDTO> cate = categoryService.getAllCategories();
            return gson.toJson(cate); 
    }
    
    @RequestMapping(value = "/getItemsDeByCategoryId")
    public @ResponseBody String getItemsDeByCategoryId(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("Category_Id") long categoryID) throws AppException{
            Gson gson = new Gson();            
            List<ItemDTO>  list = itemServiceImpl.getCategoryWiseItemDeByCate(categoryID);
            return gson.toJson(list);
    }
    
}
