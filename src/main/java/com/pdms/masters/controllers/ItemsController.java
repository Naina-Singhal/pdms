/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.masters.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.Person;
import com.pdms.master.dto.PersonJsonObject;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.master.service.impl.UnitServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author hpasupuleti
 */
@Controller
public class ItemsController {
    
    private static final Logger log = Logger.getLogger(ItemsController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private ItemServiceImpl itemService;
    
    @Autowired
    private CategoryServiceImpl categoryService;
    
    @Autowired
    private UnitServiceImpl unitService;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public ItemsController()
    {
        
    }
    /*
        Default Constructor
    */
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/itemsli")
    public ModelAndView ItemListView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
//            List<ItemDTO> itemLi = itemService.getAllItemDetails(0);
//            List<CategoryDTO> categoryLi = categoryService.getAllCategories();
//            List<UnitDTO> unitLi = unitService.getAllUnits();
//            
//            modelView.addObject("categoryLi", categoryLi);
//            modelView.addObject("itemLi", itemLi);
//            modelView.addObject("unitLi", unitLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("/masters/ItemsList");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/additem")
    public ModelAndView AddItemView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<CategoryDTO> categoryLi = categoryService.getAllCategories();
            
            modelView.addObject("categoryLi", categoryLi);
            
            modelView.setViewName("/masters/AddItem");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/saveitem")
    public ModelAndView SaveItemAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("itemObj") ItemDTO itemObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
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
            int retVal = itemService.insertItemDetail(itemObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Item ("+itemObj.getItemName()+ ") added Successfully.");
                modelView.setViewName("redirect:/itemsli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Item with code '<strong>("
                        +itemObj.getItemCode()+ ")</strong>' and name '<strong>("
                        +itemObj.getItemName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/itemsli.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/edititem")
    public ModelAndView EditItemView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eitd") String encItemId)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<CategoryDTO> categoryLi = categoryService.getAllCategories();
            ItemDTO itemObj = itemService.getSelectedItemDetail(encItemId);
            
            modelView.addObject("categoryLi", categoryLi);
            modelView.addObject("eItemObj", itemObj);
            
            modelView.setViewName("/masters/EditItem");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/edititemaj", method = RequestMethod.GET)
    public @ResponseBody String EditItemAjaxView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eitd") String encItemId)
    {
        ItemDTO itemObj = new ItemDTO();
        try
        {
            itemObj = itemService.getSelectedItemDetail(encItemId);
           
        }
        catch(AppException e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
        }
        
        return new Gson().toJson(itemObj);
    }
    
    
    @RequestMapping(value = "/updateitem")
    public ModelAndView UpdateItemAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute ("itemObj") ItemDTO itemObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
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
            int retVal = 0;//itemService.updateItemDetail(itemObj, sessUserID);
            
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Item ("+itemObj.getItemName()+ ") updated Successfully.");
                modelView.setViewName("redirect:/itemsli.htm");
            }
            else if(retVal == -1)
            {
                redirectAttributes.addFlashAttribute("ermsg", "Item with code '<strong>("
                        +itemObj.getItemCode()+ ")</strong>' and name '<strong>("
                        +itemObj.getItemName()+")</strong>' details already exists.");
                modelView.setViewName("redirect:/itemsli.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/getItemMasterRe")
    public @ResponseBody String getItemMasterRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<ItemDTO>  des_list = itemService.getAllItemDetails(0);
            String json_list = gson.toJson(des_list);
            return json_list;
    }
    
    @RequestMapping(value = "/itemMasterReById")
    public @ResponseBody String itemMasterReById(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("itemid_id") long id) throws AppException{
            Gson gson = new Gson();            
            List<ItemDTO>  rt_id_list = itemService.getItemMasterDeById(id);
            return gson.toJson(rt_id_list);
    }
    
    @RequestMapping(value = "/getAllCategoryRe")
    public @ResponseBody String getAllCategoryRecord(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<CategoryDTO>  list = categoryService.getAllCategories();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(value = "/getAllunitRe")
    public @ResponseBody String getAllunitRe(HttpServletRequest request, HttpServletResponse response) throws AppException{
            Gson gson = new Gson();            
            List<UnitDTO>  list = unitService.getAllUnits();
            String json_list = gson.toJson(list);
            return json_list;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/updateItemRecord")
    @ResponseBody
    public String updateItemRecord(HttpServletRequest request, @RequestBody List<ItemDTO> itemObj, ModelMap map,
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
            b = itemService.updateItemDetail(itemObj, sessUserID);
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            //return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return gson.toJson(b);
    }
    
     @RequestMapping(value = "/springPaginationDataTables", method = RequestMethod.GET)
    public @ResponseBody String springPaginationDataTables(HttpServletRequest  request, HttpServletResponse response) throws IOException, AppException {
	
    	//Fetch the page number from client
    	Integer pageNumber = 0;
        long totalCount = 0;
        String pageNO = request.getParameter("iDisplayStart");
        Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	if (null != request.getParameter("iDisplayStart"))
    		pageNumber = (Integer.valueOf(request.getParameter("iDisplayStart"))/pageDisplayLength)+1;		
    	String ored = request.getParameter("order");
    	//Fetch search parameter
    	String searchParameter = request.getParameter("sSearch");
    	log.info(ored+"------searchParameter-----"+searchParameter);
    	//Fetch Page display length
    	
        log.info(pageDisplayLength+"------pageDisplayLength-----"+pageNumber);	
    	//Geting count  total record 
        totalCount  = itemService.getTotalRowCount();
        pageNumber = pageNumber - 1;
        long endIndex = pageNumber * pageDisplayLength;
        log.info(endIndex+"-----totalCount------"+totalCount);
    	//Create page list data
    	//List<Person> personsList = createPaginationData(pageDisplayLength);
	List<ItemDTO> itemList = itemService.getItemDetailsPagination(endIndex, pageDisplayLength);
        
		//Here is server side pagination logic. Based on the page number you could make call 
		//to the data base create new list and send back to the client. For demo I am shuffling 
		//the same list to show data randomly
//		if (pageNumber == 1) {
//			Collections.shuffle(itemList);
//		}else if (pageNumber == 2) {
//			Collections.shuffle(itemList);
//		}else {
//			Collections.shuffle(itemList);
//		}
		
		//Search functionality: Returns filtered list based on search parameter
		itemList = getListBasedOnSearchParameter(searchParameter,itemList);
		
		
		PersonJsonObject personJsonObject = new PersonJsonObject();
		//Set Total display record
		personJsonObject.setiTotalDisplayRecords(totalCount);
		//Set Total record
		personJsonObject.setiTotalRecords(totalCount);
		personJsonObject.setAaData(itemList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(personJsonObject);
	
		return json2;
    }

	private List<ItemDTO> getListBasedOnSearchParameter(String searchParameter,List<ItemDTO> itemList) {
		
		if (null != searchParameter && !searchParameter.equals("")) {
			List<ItemDTO> personsListForSearch = new ArrayList<ItemDTO>();
			searchParameter = searchParameter.toUpperCase();
			for (ItemDTO item : itemList) {
				if (item.getItemCode().toUpperCase().indexOf(searchParameter)!= -1 || item.getItemName().toUpperCase().indexOf(searchParameter)!= -1
						|| item.getDescription().toUpperCase().indexOf(searchParameter)!= -1 || item.getCategoryDTO().getCategoryName().toUpperCase().indexOf(searchParameter)!= -1
						|| item.getUnitDTO().getUnitName().toUpperCase().indexOf(searchParameter)!= -1) {
					personsListForSearch.add(item);					
				}
				
			}
			itemList = personsListForSearch;
			personsListForSearch = null;
		}
		return itemList;
	}
    
    
    @RequestMapping(value = "/getItemOnlyCode")
    public @ResponseBody String getItemOnlyCode(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("query") String keywords) throws AppException{            
            Gson gson = new Gson();            
            ArrayList<String> rt_id_list = itemService.getItemOnlyCodes(keywords);
            return gson.toJson(rt_id_list);
    }
    
    @RequestMapping(value = "/getItemDeByItemName")
    public @ResponseBody String getItemDeByItemName(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("item_name") String itemName) throws AppException{            
            Gson gson = new Gson();            
            List<ItemDTO> rt_id_list = itemService.getItemMaDeByItemName(itemName);
            return gson.toJson(rt_id_list);
    }
  
}
