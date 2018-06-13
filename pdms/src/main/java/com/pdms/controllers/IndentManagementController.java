/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.ModeOfPurchaseDTO;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.CategoryServiceImpl;
import com.pdms.master.service.impl.DesignationServiceImpl;
import com.pdms.master.service.impl.EmployeeServiceImpl;
import com.pdms.master.service.impl.HeadOfAccountServiceImpl;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.master.service.impl.ModeOfPurchaseServiceImpl;
import com.pdms.master.service.impl.PlaceOfDeliveryServiceImpl;
import com.pdms.master.service.impl.SectionServiceImpl;
import com.pdms.service.impl.IndentServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class IndentManagementController {

    private static final Logger log = Logger.getLogger(IndentManagementController.class);

    /*
    Start : Autowiring of Fields
     */
    @Autowired
    private SectionServiceImpl sectionService;

    @Autowired
    private ModeOfPurchaseServiceImpl mopService;

    @Autowired
    private DesignationServiceImpl desigService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private HeadOfAccountServiceImpl hoaService;

    @Autowired
    private PlaceOfDeliveryServiceImpl podService;

    @Autowired
    private EmployeeServiceImpl empService;

    @Autowired
    private CategoryServiceImpl catService;

    @Autowired
    private IndentServiceImpl indentService;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    /*
    End : Autowiring of Fields
     */
    @RequestMapping(value = "/indentli")
    public ModelAndView IndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = indentService.getAllIndentForms(request);

            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/indsubmitredirect")
    public ModelAndView IndentSubmitRedirectAction(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            HttpSession session = request.getSession(false);
            EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
            String empType = "";
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
                empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            }
//            if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
//                modelView.setViewName("redirect:/rpuadmapprindli.htm");
//            } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
//                modelView.setViewName("redirect:/admappindli.htm");
//            } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECUSER)) {
//                DesignationDTO desigObj = empLoginObj.getDesignationDTO();
//                long isApprover = desigObj.getIsApprovingAuthority();
//                if (isApprover > 0) {
//                    modelView.setViewName("redirect:/admappindli.htm");
//                } else {
//                    modelView.setViewName("redirect:/appuserindli.htm");
//                }
//            } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUUSER)) {
//                DesignationDTO desigObj = empLoginObj.getDesignationDTO();
//                long isApprover = desigObj.getIsApprovingAuthority();
//                if (isApprover > 0) {
//                    modelView.setViewName("redirect:/rpuadmapprindli.htm");
//                } else {
//                    modelView.setViewName("redirect:/appuserindli.htm");
//                }
//            }
            modelView.setViewName("redirect:/appuserindli.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ApplicationUserIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/appuserindli")
    public ModelAndView ApplicationUserIndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = indentService.getUserIndentForms(request);

            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "ApplicationUserIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/admappindli")
    public ModelAndView AdminApprovalIndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = indentService.getApprovalIndentForms(request);            
            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "AdminApprovalIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }

    @RequestMapping(value = "/rpuadmapprindli")
    public ModelAndView RPUAdminApprovalIndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = indentService.getRPUApprovalIndentForms(request);

            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "RPUAdminApprovalIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/rpuadmsignindli")
    public ModelAndView RPUAdminSigningIndentListView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<IndentFormDTO> indentLi = indentService.getSigningIndentForms(request);

            modelView.addObject("indentLi", indentLi);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentList");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "RPUAdminSigningIndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/indenthome")
    public ModelAndView IndentFormHomeView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentFormHome");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/indentformredirect")
    public ModelAndView IndentFormRedirectAction(HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            String indType = request.getParameter("indType");
            String encString = "";
            if (indType.equalsIgnoreCase("behalf")) {
                encString = encryptDecrypt.encrypt("behalf");
                redirectAttributes.addAttribute("tp", encString);
                modelView.setViewName("redirect:/indentform.htm");
            } else if (indType.equalsIgnoreCase("self")) {
                encString = encryptDecrypt.encrypt("self");
                redirectAttributes.addAttribute("tp", encString);
                modelView.setViewName("redirect:/indentform.htm");
            } else {
                modelView.setViewName("indent/IndentFormHome");
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "IndentListView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/indentform")
    public ModelAndView IndentFormView(HttpServletRequest request,
            HttpServletResponse response,
            final Model model) {
        ModelAndView modelView = new ModelAndView();
        try {
            String indType = "";
            String encIndentType = request.getParameter("tp");
            if (!StringUtils.isEmpty(encIndentType)) {
                indType = encryptDecrypt.decrypt(encIndentType);
            }

            // GET ALL PREDEFINED DATA
            List<SectionDTO> sectionLi = new ArrayList<>();
            if (indType.equalsIgnoreCase("self")) {

            } else if (indType.equalsIgnoreCase("behalf")) {
                sectionLi = sectionService.getAllSectionDetails(request);
            }
            List<ModeOfPurchaseDTO> mopLi = mopService.getAllModeOfPurchaseDetails();
            //List<DesignationDTO> apprAuthLi = desigService.getAllApprovingAuthorityDesignations();
            List<DesignationDTO> apprAuthLi = desigService.getApprovingAuthorityDesignations(request);
            List<DesignationDTO> signAuthLi = desigService.getAllSigningAuthorityDesignations();
            //List<ItemDTO> itemLi = itemService.getAllItemDetails(ApplicationConstants.ACTIVE_FLAG_VALUE);
            List<HeadOfAccountDTO> hoaLi = hoaService.getAllHeadOfAccountDetails();
            List<PlaceOfDeliveryDTO> podLi = podService.getAllPlaceOfDeliveryDetails();
            List<EmployeeLoginDTO> empLi = empService.getAllActiveEmployeeDetails();
            List<CategoryDTO> catLi = catService.getAllCategories();
            List<ItemDTO> itemLi = new ArrayList<>();
            IndentFormDTO indentObj = new IndentFormDTO();
            if (model.containsAttribute("itemLi")) {
                itemLi = (List<ItemDTO>) model.asMap().get("itemLi");
            }
            if (model.containsAttribute("indentStat")) {
                modelView.addObject("indentStat", "indentStat");
            }
            if (model.containsAttribute("indentObj")) {
                indentObj = (IndentFormDTO) model.asMap().get("indentObj");
                if (indentObj.getSectionId() > 0) {
                    sectionLi = sectionService.getAllSectionDetails(request);
                }
            }

            modelView.addObject("sectionLi", sectionLi);
            modelView.addObject("mopLi", mopLi);
            modelView.addObject("apprAuthLi", apprAuthLi);
            modelView.addObject("signAuthLi", signAuthLi);
            modelView.addObject("hoaLi", hoaLi);
            modelView.addObject("podLi", podLi);
            modelView.addObject("empLi", empLi);
            modelView.addObject("catLi", catLi);

            modelView.addObject("itemLi", itemLi);
            modelView.addObject("indentSObj", indentObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/IndentForm");

        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "IndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/saveindent")
    public ModelAndView SaveIndentFormAction(HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("indentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {
            int resVal = indentService.insertIndentFormDetail(indentObj, request);

            if (resVal > 0) {
                redirectAttributes.addFlashAttribute("msg", "Indent (" + indentObj.getIndentNumber() + ") added Successfully.");
                //modelView.setViewName("redirect:/appuserindli.htm");
                redirectAttributes.addAttribute("indid", encryptDecrypt.encrypt(resVal + ""));
                modelView.setViewName("redirect:/updfile.htm");
            } else if (resVal == -1) {
                redirectAttributes.addFlashAttribute("ermsg", "Indent with number '<strong>("
                        + indentObj.getIndentNumber() + ")</strong>' details alreay exists.");
                redirectAttributes.addFlashAttribute("indentObj", indentObj);
                modelView.setViewName("redirect:/indenthome.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveIndentFormAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/editindent")
    public ModelAndView EditIndentFormView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") String encIndId,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndId);

            List<ItemDTO> itemLi = itemService.getCategoryWiseItemDetails(indentObj.getCategoryObj().getCategoryID());

            redirectAttributes.addFlashAttribute("itemLi", itemLi);

            //modelView.addObject("indentSObj", indentObj);
            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            redirectAttributes.addFlashAttribute("indentStat", "update");
            modelView.setViewName("redirect:/indentform.htm");

            //modelView.setViewName("indent/EditIndentForm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/appeditindent")
    public ModelAndView EditIndentFormByApproverView(HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView modelView = new ModelAndView();
        try {
            // GET ALL PREDEFINED DATA

            String encIndId = request.getParameter("eindi");

            List<SectionDTO> sectionLi = sectionService.getAllSectionDetails(request);
            List<ModeOfPurchaseDTO> mopLi = mopService.getAllModeOfPurchaseDetails();
            List<DesignationDTO> apprAuthLi = desigService.getAllApprovingAuthorityDesignations();
            List<DesignationDTO> signAuthLi = desigService.getAllSigningAuthorityDesignations();
            List<HeadOfAccountDTO> hoaLi = hoaService.getAllHeadOfAccountDetails();
            List<PlaceOfDeliveryDTO> podLi = podService.getAllPlaceOfDeliveryDetails();
            List<EmployeeLoginDTO> empLi = empService.getAllActiveEmployeeDetails();
            List<CategoryDTO> catLi = catService.getAllCategories();
            List<ItemDTO> itemLi = new ArrayList<>();

            modelView.addObject("sectionLi", sectionLi);
            modelView.addObject("mopLi", mopLi);
            modelView.addObject("apprAuthLi", apprAuthLi);
            modelView.addObject("signAuthLi", signAuthLi);
            modelView.addObject("itemLi", itemLi);
            modelView.addObject("hoaLi", hoaLi);
            modelView.addObject("podLi", podLi);
            modelView.addObject("empLi", empLi);

            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndId);

            itemLi = itemService.getCategoryWiseItemDetails(indentObj.getCategoryObj().getCategoryID());

            modelView.addObject("itemLi", itemLi);
            modelView.addObject("indentSObj", indentObj);

            modelView.addObject("appStat", encryptDecrypt.encrypt(ApplicationConstants.INDENT_STATUS_APPROVED));
            modelView.addObject("revStat", encryptDecrypt.encrypt(ApplicationConstants.INDENT_STATUS_REVERTED));
            modelView.addObject("rejStat", encryptDecrypt.encrypt(ApplicationConstants.INDENT_STATUS_REJECTED));
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/AppEditIndentForm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/viewindent")
    public ModelAndView ViewIndentFormView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") String encIndId) {
        ModelAndView modelView = new ModelAndView();
        try {
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndId);

            modelView.addObject("indentSObj", indentObj);
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("indent/ViewIndentForm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/stkindentform")
    public ModelAndView IndentFormFromStockListView(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("itemObj") ItemDTO itemObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<ItemDTO> itemLi = itemService.getCategoryWiseItemDetails(itemObj.getCategoryDTO().getCategoryID());

            redirectAttributes.addFlashAttribute("itemLi", itemLi);
            IndentFormDTO indentObj = new IndentFormDTO();
            indentObj.setItemDTOLi(itemLi);

            CategoryDTO catObj = new CategoryDTO();
            catObj.setCategoryID(itemObj.getCategoryDTO().getCategoryID());
            indentObj.setCategoryObj(catObj);

            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            modelView.setViewName("redirect:/indenthome.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/updateindent")
    public ModelAndView UpdateIndentFormAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("updIndentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        HttpSession session = request.getSession();
        try {

            int resVal = indentService.updateIndentFormDetail(indentObj, request);
            
            if (resVal > 0) {
                redirectAttributes.addFlashAttribute("msg", "Indent (" + indentObj.getIndentNumber() + ") Successfully Updated");
                //modelView.setViewName("redirect:/appuserindli.htm");
                redirectAttributes.addAttribute("indid", indentObj.getEncFieldValue());
                modelView.setViewName("redirect:/updfile.htm");
            } else if (resVal == -1) {
                redirectAttributes.addFlashAttribute("ermsg", "Indent with number '<strong>("
                        + indentObj.getIndentNumber() + ")</strong>' details alreay exists.");
                redirectAttributes.addFlashAttribute("indentObj", indentObj);
                modelView.setViewName("redirect:/indenthome.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveIndentFormAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/getselcatitems")
    public ModelAndView GetSelectedCategoryItems(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentObj") IndentFormDTO indentObj,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            List<ItemDTO> itemLi = itemService.getCategoryWiseItemDetails(indentObj.getCategoryObj().getCategoryID());

            redirectAttributes.addFlashAttribute("itemLi", itemLi);
            redirectAttributes.addFlashAttribute("indentObj", indentObj);
            if (!StringUtils.isEmpty(indentObj.getEncFieldValue())) {
                redirectAttributes.addFlashAttribute("indentStat", "update");
            }
            modelView.setViewName("redirect:/indentform.htm");
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "GetSelectedCategoryItems :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/approveindent")
    public ModelAndView ApproveIndentFormAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("indentObj") IndentFormDTO indentObj,
            @RequestParam("apdi") String indStatus,
            RedirectAttributes redirectAttributes) {
        ModelAndView modelView = new ModelAndView();
        try {
            int resVal = indentService.approveSelectedIndentForm(request, indentObj, indStatus);

            if (resVal > 0) {
                //redirectAttributes.addFlashAttribute("msg", "Indent ("+indentObj.getIndentNumber()+ ") updated Successfully.");
                redirectAttributes.addFlashAttribute("msg", "Indent updated Successfully.");
                modelView.setViewName("redirect:/indentli.htm");
            } else {
                ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "SaveIndentFormAction :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

        return modelView;
    }

    @RequestMapping(value = "/ifpdfdownload")
    public ModelAndView IndentFormPDFDownloadView(HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("eindi") String encIndId) {
        try {
            IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encIndId);

            return new ModelAndView("indentFormPDFView", "indentObj", indentObj);
        } catch (Exception e) {
            log.debug("Exception Occured while Executing the "
                    + "EditIndentFormView :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }

    }
    
    @RequestMapping(value = "/getIndentFormData")
    public @ResponseBody String getFileNumber(HttpServletRequest request, HttpServletResponse response,
    @RequestParam("IndentFormId") int indentId) throws AppException{
            Gson gson = new Gson(); 
            log.info("---indentform----"+indentId);
            List<IndentFormDTO> indentObj = indentService.getIndentFormDeById(indentId);
            String json_fileNo = gson.toJson(indentObj);   
            log.info("--file no---"+json_fileNo);
            return json_fileNo;
    }

}
