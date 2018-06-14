/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.GroupDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ModeOfPurchaseDTO;
import com.pdms.master.service.impl.GroupServiceImpl;
import com.pdms.master.service.impl.ModeOfPurchaseServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class IndentFileMappingController {

    private static final Logger log = Logger.getLogger(IndentFileMappingController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private IndentServiceImpl indentService;
    
    @Autowired
    private GroupServiceImpl groupService;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private ModeOfPurchaseServiceImpl mopService;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    /*
    End : Autowiring of Fields
     */
    /*
        Default Constructor
    */
    public IndentFileMappingController()
    {
        
    }
    /*
        Default Constructor
    */
    
    @RequestMapping(value = "/signeditindent")
    public ModelAndView SignIndentFormBySigningAuthorityView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") String encIndId )
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<GroupDTO> groupLi = groupService.getAllGroupDetails();
            long fileNo = indentService.getMaxFileNumberForIndentMapping();
            List<ModeOfPurchaseDTO> mopLi = mopService.getAllModeOfPurchaseDetails();
            
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndId);
            
            modelView.addObject("indentSObj", indentObj);
            modelView.addObject("groupLi",groupLi);
            modelView.addObject("mopLi",mopLi);
            modelView.addObject("fileNo",fileNo);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.addObject("appStat",encryptDecrypt.encrypt(ApplicationConstants.INDENT_STATUS_APPROVED));
            modelView.addObject("rejStat",encryptDecrypt.encrypt(ApplicationConstants.INDENT_STATUS_REJECTED));
            
            modelView.setViewName("indent/SignEditIndentForm");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/updsignindent")
    public ModelAndView UpdateSignIndentFormBySigningAuthorityAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
           int retVal = indentService.signAndMappingIndentToFile(indentObj, request);
           if(retVal >0)
            {
                redirectAttributes.addFlashAttribute("msg", "Indent ("+indentObj.getIndentNumber()+ ") updated Successfully.");
                modelView.setViewName("redirect:/indentli.htm");
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
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/indentfmap")
    public ModelAndView IndentFileMappingView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {
            List<GroupDTO> groupLi = groupService.getAllGroupDetails();
            long fileNo = indentService.getMaxFileNumberForIndentMapping();
            List<IndentFormDTO> unMapedIndentLi = indentService.getAllUnMappedIndentForms(request);
            
            modelView.addObject("groupLi",groupLi);
            modelView.addObject("fileNo",fileNo);
            modelView.addObject("indentLi",unMapedIndentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentFileMapping");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
    @RequestMapping(value = "/mapindents")
    public ModelAndView MapSelectedIndentsAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentFileMapObj") IndentFileMappingDTO fileMapObj,
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
            int retVal = indentService.mapIndentsToFile(fileMapObj, sessUserID);
            if(retVal > 0)
            {
                redirectAttributes.addFlashAttribute("msg", "Selected Indent(s) mapped successfully.");
                modelView.setViewName("redirect:/indentli.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "MapSelectedIndentsAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }
}
