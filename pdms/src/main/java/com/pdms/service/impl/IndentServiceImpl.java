/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.IndentDAOImpl;
import com.pdms.dao.impl.IndentTransactDAOImpl;
import com.pdms.dao.impl.IndentWorkListDAOImpl;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentFormFileDTO;
import com.pdms.dto.IndentLogDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.MasterLookupDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.SectionServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class IndentServiceImpl {

    private static final Logger logger = Logger.getLogger(IndentServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private IndentDAOImpl indentDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private IndentWorkListDAOImpl indentWLDAO;

    @Autowired
    private IndentTransactDAOImpl indentTransactDAO;

    @Autowired
    private MasterLookUpServiceImpl masterService;

    @Autowired
    private SectionServiceImpl sectionService;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public IndentServiceImpl() {

    }

    /*
    Default Constructor 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
        }

        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
            return indentDAO.getAllIndentForms(sessionKey);
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
            String qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_SECADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_SECUSER + "'";
            return indentWLDAO.getAllPlantAccountStoresAdminIndentForms(qryCondition, "");
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
            String qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_STORES_USER + "'";
            return indentWLDAO.getAllPlantAccountStoresAdminIndentForms(qryCondition, "");
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
            String qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER + "'";
            return indentWLDAO.getAllPlantAccountStoresAdminIndentForms(qryCondition, "");
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
            return indentWLDAO.getAllPUAdminIndentForms();
        } else {
            DesignationDTO desigObj = empLoginObj.getDesignationDTO();
            long isApprover = desigObj.getIsApprovingAuthority();
            long isSignIn = desigObj.getIsSigningAuthority();
            long signInUser = 0;
            long apprUser = 0;
            if (isApprover >= 1) {
                //apprUser = sessUserID;
                apprUser = desigObj.getDesignationID();
                return indentWLDAO.getAllApprovingAuthorityIndentForms(sessUserID, apprUser);
            } else if (isSignIn >= 1) {
                signInUser = desigObj.getDesignationID();
                return indentWLDAO.getAllSigningAuthorityIndentForms(sessUserID, signInUser);
            } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUUSER)) {
                return indentWLDAO.getAllPurchaseUnitUserIndentForms(sessUserID);
            } else {
                return indentWLDAO.getAllCreatedUserIndentForms(sessUserID);
            }

            //return indentDAO.getUserBasedAllIndentForms(sectionID, signInUser, apprUser, sessUserID, sessionKey);
        }

    }

    public List<IndentFormDTO> getApprovalIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
        }
        List<IndentFormDTO> indentLi = new ArrayList<>();
        String qryCondition = "";
        StringBuilder subQuery = new StringBuilder();

//        if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECUSER))) {
//            qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_SECADMIN + "','"
//                    + ApplicationConstants.EMPLOYEE_TYPE_SECUSER + "'";
//        } else if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER))) {
//            qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN + "','"
//                    + ApplicationConstants.EMPLOYEE_TYPE_STORES_USER + "'";
//        } else if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER))) {
//            qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN + "','"
//                    + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER + "'";
//        } else {
//
//        }
//
//        subQuery.append(" WHERE etm.employee_type_name IN  (");
//        subQuery.append(qryCondition);
//        subQuery.append(")");
//        subQuery.append(" AND (k.lookup_name = '");
//        subQuery.append(ApplicationConstants.INDENT_STATUS_CREATED);
//        subQuery.append("' OR k.lookup_name = '");
//        subQuery.append(ApplicationConstants.INDENT_STATUS_MODIFIED);
//        subQuery.append("' OR k.lookup_name = '");
//        subQuery.append(ApplicationConstants.INDENT_STATUS_REVERTED);
//        subQuery.append("') ");

        indentLi = indentWLDAO.getAllPlantAccountStoresAdminIndentForms(qryCondition, subQuery.toString());

        return indentLi;
    }

    public List<IndentFormDTO> getSigningIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
        }
        List<IndentFormDTO> indentLi = new ArrayList<>();

//        // IF APPROVING AUTHORITY IS RPU ADMIN
//        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
//            indentLi = indentWLDAO.getAllPUAdminIndentForms();
//        } else {
//            DesignationDTO desigObj = empLoginObj.getDesignationDTO();
//            long isSignIn = desigObj.getIsSigningAuthority();
//            long signDesigID = 0;
//            // IF SIGNING AUTHORITY IS RPU DESIGNATED USER
//            if (isSignIn >= 1) {
//                signDesigID = desigObj.getDesignationID();
//                indentLi = indentWLDAO.getAllSigningAuthorityIndentForms(sessUserID, signDesigID);
//            } // IF LOGGED IN USER IS RPU USER
//            else {
//                indentLi = indentWLDAO.getAllCreatedUserIndentForms(sessUserID);
//            }
//        }
        indentLi = indentWLDAO.getAllPUAdminIndentForms();
        return indentLi;
    }

    public List<IndentFormDTO> getRPUApprovalIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String empType = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
        }
        List<IndentFormDTO> indentLi = new ArrayList<>();

        String qryCondition = "";
        StringBuilder subQuery = new StringBuilder();

//        if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUUSER))) {
//            //if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUUSER))
//            //{
//            qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_PUADMIN + "','"
//                    + ApplicationConstants.EMPLOYEE_TYPE_PUUSER + "'";
//
//            subQuery.append(" WHERE etm.employee_type_name IN  (");
//            subQuery.append(qryCondition);
//            subQuery.append(")");
//            subQuery.append(" AND (k.lookup_name = '");
//            subQuery.append(ApplicationConstants.INDENT_STATUS_CREATED);
//            subQuery.append("' OR k.lookup_name = '");
//            subQuery.append(ApplicationConstants.INDENT_STATUS_MODIFIED);
//            subQuery.append("' OR k.lookup_name = ' ");
//            subQuery.append(ApplicationConstants.INDENT_STATUS_REVERTED);
//            subQuery.append("' OR k.lookup_name <> ' ");
//            subQuery.append(ApplicationConstants.INDENT_STATUS_APPROVED);
//            subQuery.append("') ");
//
//            
//        }
        indentLi = indentWLDAO.getAllPlantAccountStoresAdminIndentForms(qryCondition, subQuery.toString());
        /*
        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN))
        {
            
            qryCondition = "'" +ApplicationConstants.EMPLOYEE_TYPE_SECADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_SECUSER + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_STORES_USER + "'";
            
            subQuery.setLength(0);

            subQuery.append(" WHERE etm.employee_type_name IN  (");
            subQuery.append(qryCondition);
            subQuery.append(")");
            subQuery.append(" AND (k.lookup_name <> '");
            subQuery.append(ApplicationConstants.INDENT_STATUS_CREATED);
            subQuery.append("' OR k.lookup_name <> '");
            subQuery.append(ApplicationConstants.INDENT_STATUS_MODIFIED);
            subQuery.append("' OR k.lookup_name <> ' ");
            subQuery.append(ApplicationConstants.INDENT_STATUS_REVERTED);
            subQuery.append("' OR k.lookup_name <> ' ");
            subQuery.append(ApplicationConstants.INDENT_STATUS_APPROVED);
            subQuery.append("') ");
            
            List<IndentFormDTO> apprOtherIndentLi = indentWLDAO.getAllPlantAccountStoresAdminIndentForms
                                                (qryCondition,subQuery.toString());
            
            
            qryCondition ="";
            subQuery.setLength(0);
           
            qryCondition = "'" + ApplicationConstants.EMPLOYEE_TYPE_PUADMIN + "','"
                    + ApplicationConstants.EMPLOYEE_TYPE_PUUSER + "'";

            subQuery.append(" WHERE etm.employee_type_name IN  (");
            subQuery.append(qryCondition);
            subQuery.append(")");
            subQuery.append(" AND (k.lookup_name = '");
            subQuery.append(ApplicationConstants.INDENT_STATUS_CREATED);
            subQuery.append("' OR k.lookup_name = '");
            subQuery.append(ApplicationConstants.INDENT_STATUS_MODIFIED);
            subQuery.append("' OR k.lookup_name = ' ");
            subQuery.append(ApplicationConstants.INDENT_STATUS_REVERTED);
            subQuery.append("') ");
            
            List<IndentFormDTO> apprRPUIndentLi = indentWLDAO.getAllPlantAccountStoresAdminIndentForms
                                                (qryCondition,subQuery.toString());
            
//            if(!apprOtherIndentLi.isEmpty())
//            {
//                indentLi.addAll(apprOtherIndentLi);
//            }
            if(!apprRPUIndentLi.isEmpty())
            {
                indentLi.addAll(apprRPUIndentLi);
            }
            
        }
         */

        return indentLi;
    }

    public List<IndentFormDTO> getUserIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
            sessUserID = empLoginObj.getEmployeeID();
        }
        List<IndentFormDTO> indentLi = new ArrayList<>();
        logger.info("-------------------ssssid------------------------"+sessUserID);
        indentLi = indentWLDAO.getAllCreatedUserIndentForms(sessUserID);

        return indentLi;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public IndentFormDTO getSelectedIndentFormDetail(final String encIndentID) throws AppException {
        long indentID = 0;
        if (!StringUtils.isEmpty(encIndentID)) {
            String decIndentID = encryptDecrypt.decrypt(encIndentID);
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }

        IndentFormDTO indentDTO = indentDAO.getSelectedIndentFormDetail(indentID);
        List<ItemDTO> indentItems = indentDAO.getAllIndentItemDetails(indentID);
        List<IndentFormFileDTO> indentFileLi = indentDAO.getAllIndentFiles(indentID);
        List<IndentLogDTO> indentLogLi = indentDAO.getAllIndentLog(indentID);

        indentDTO.setItemDTOLi(indentItems);
        indentDTO.setIndentLogLi(indentLogLi);
        indentDTO.setIndentFormFileLi(indentFileLi);

        return indentDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertIndentFormDetail(final IndentFormDTO indentForm, final HttpServletRequest request)
            throws AppException {

        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
            logger.info(sessUserID+"---------------empTypeempType------------------"+empType);
            if ((indentForm.getSectionId() == 0)) {

                SectionDTO sectionObj = new SectionDTO();
//                if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
//                    sectionObj = sectionService.getSeclectedSectionDetailByName(ApplicationConstants.DEFAULT_PUADMIN_SECTION);
//                    sectionID = sectionObj.getSectionID();
//                } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
//                    sectionObj = sectionService.getSeclectedSectionDetailByName(ApplicationConstants.DEFAULT_PLANT_ADMIN_SECTION);
//                    sectionID = sectionObj.getSectionID();
//                } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
//                    sectionObj = sectionService.getSeclectedSectionDetailByName(ApplicationConstants.DEFAULT_ACCOUNT_ADMIN_SECTION);
//                    sectionID = sectionObj.getSectionID();
//                } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
//                    sectionObj = sectionService.getSeclectedSectionDetailByName(ApplicationConstants.DEFAULT_STORE_ADMIN_SECTION);
//                    sectionID = sectionObj.getSectionID();
//                } else {
//                    sectionID = empLoginObj.getSectionDTO().getSectionID();
//                }
                 List<EmployeeProfileDTO>  empObj = indentDAO.getEmpProfileDeByEmpID(sessUserID);
                 for(EmployeeProfileDTO obj: empObj){
                        sectionID = obj.getSectionId();
                 }
                 
                 logger.info("--------------sectionIDIDID---------------"+sectionID);
            } else {
                sectionID = indentForm.getSectionId();
            }
        }

        long dupCnt = this.checkDuplicateIndent(indentForm.getIndentNumber());
        if (dupCnt > 0) {
            return -1;
        } else {
            return indentTransactDAO.insertIndentFormDetail(indentForm, sessUserID, sectionID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateIndentFormDetail(final IndentFormDTO indentForm, final HttpServletRequest request)
            throws AppException {

        long indentID = 0;
        long statusID = 0;
        if (!StringUtils.isEmpty(indentForm.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(indentForm.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        if (!StringUtils.isEmpty(indentForm.getEncRowStatusValue())) {
            String decStatusID = encryptDecrypt.decrypt(indentForm.getEncRowStatusValue());
            if (NumberUtils.isNumber(decStatusID)) {
                statusID = Long.parseLong(decStatusID);
            }
            MasterLookupDTO masterObj = masterService.getMasterLookUpByLookUpID(statusID);
            if (masterObj.getLookUpName().equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_REVERTED)) {
                MasterLookupDTO lookUpObj = masterService.getMasterLookUpByLookUpValueAndType(ApplicationConstants.INDENT_STATUS_MODIFIED,
                        ApplicationConstants.CONSTANT_INDENT_STATUS);

                statusID = lookUpObj.getLookUpID();
            }
        }

        HttpSession session = request.getSession(false);
        long sectionID = 0;
        String empType = "";
        String sessionKey = "";
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
            sectionID = empLoginObj.getSectionDTO().getSectionID();
        }

        indentForm.setIndentFormID(indentID);
        indentForm.setRowStatusKey(statusID);
        indentForm.setSectionId(sectionID);

        long dupCnt = 0;
        IndentFormDTO exisIndentObj = this.getSelectedIndentFormDetail(indentForm.getEncFieldValue());

        if (!exisIndentObj.getIndentNumber().equalsIgnoreCase(indentForm.getIndentNumber())) {
            dupCnt = this.checkDuplicateIndent(indentForm.getIndentNumber());
        }

        if (dupCnt > 0) {
            return -1;
        } else {
            return indentTransactDAO.updateIndentFormDetail(indentForm, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getUserBasedAllIndentForms(final long sectionID, final long signUserID,
            final long approveUserID, final long sessUserID, final String sessionKey)
            throws AppException {
        return indentDAO.getUserBasedAllIndentForms(sectionID, signUserID, approveUserID, sessUserID, sessionKey);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getAllIndentItemDetails(final String encIndentID) throws AppException {
        long indentID = 0;
        if (!StringUtils.isEmpty(encIndentID)) {
            String decIndentID = encryptDecrypt.decrypt(encIndentID);
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        return indentDAO.getAllIndentItemDetails(indentID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int approveSelectedIndentForm(final HttpServletRequest request, final IndentFormDTO indentForm,
            final String encSelStatus)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
            sessUserID = empLoginObj.getEmployeeID();
        }
        String indStatus = "";
        if (!StringUtils.isEmpty(encSelStatus)) {
            indStatus = encryptDecrypt.decrypt(encSelStatus);
        }
        long indentID = 0;

        if (!StringUtils.isEmpty(indentForm.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(indentForm.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        indentForm.setIndentFormID(indentID);

        return indentTransactDAO.approveIndentFormDetail(indentForm, sessUserID, indStatus);

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getMaxFileNumberForIndentMapping() throws AppException {
        return indentDAO.getMaxFileNumberForIndentMapping();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllUnMappedIndentForms(final HttpServletRequest request) throws AppException {
        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
        }

        List<IndentFormDTO> indentLi = indentDAO.getAllIndentForms(sessionKey);
        List<IndentFormDTO> unMapIndentLi = new LinkedList<>();

        if (!indentLi.isEmpty()) {
            for (IndentFormDTO indentObj : indentLi) {
                if (indentObj.getIndentStatus().equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_UN_MAPPED)) {
                    unMapIndentLi.add(indentObj);
                }
            }
        }

        return unMapIndentLi;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int signAndMappingIndentToFile(final IndentFormDTO indentObj, final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        long indentID = 0;
        long statusID = 0;
        if (!StringUtils.isEmpty(indentObj.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(indentObj.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        if (!StringUtils.isEmpty(indentObj.getEncRowStatusValue())) {
            String decStatusID = encryptDecrypt.decrypt(indentObj.getEncRowStatusValue());
            if (NumberUtils.isNumber(decStatusID)) {
                statusID = Long.parseLong(decStatusID);
            }
            MasterLookupDTO masterObj = masterService.getMasterLookUpByLookUpID(statusID);
            if (masterObj.getLookUpName().equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_REVERTED)) {
                MasterLookupDTO lookUpObj = masterService.getMasterLookUpByLookUpValueAndType(ApplicationConstants.INDENT_STATUS_MODIFIED,
                        ApplicationConstants.CONSTANT_INDENT_STATUS);

                statusID = lookUpObj.getLookUpID();
            }
        }
        indentObj.setIndentFormID(indentID);

        return indentTransactDAO.signAndMappingIndentToFile(indentObj, sessUserID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int mapIndentsToFile(final IndentFileMappingDTO fileMapDTO, final long sessUserID)
            throws AppException {

        List<IndentFileMappingDTO> fileMapDTOLi = new LinkedList<>();
        if (!fileMapDTO.getMappedIndents().isEmpty()) {
            for (String encIndentID : fileMapDTO.getMappedIndents()) {
                IndentFileMappingDTO fileMapObj = new IndentFileMappingDTO();
                long indentId = 0;
                if (!StringUtils.isEmpty(encIndentID)) {
                    String decIndentID = encryptDecrypt.decrypt(encIndentID);
                    if (NumberUtils.isNumber(decIndentID)) {
                        indentId = Long.parseLong(decIndentID);
                    }
                }

                fileMapObj.setIndentFormID(indentId);
                fileMapObj.setFileGroupID(fileMapDTO.getFileGroupID());
                fileMapObj.setFileNo(fileMapDTO.getFileNo());
                fileMapObj.setCreatedByUserID(sessUserID);

                fileMapDTOLi.add(fileMapObj);
            }
        }

        return indentTransactDAO.mappingIndentsToFile(fileMapDTOLi);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentLogDTO> getAllIndentLog(final long indentID) throws AppException {
        return indentDAO.getAllIndentLog(indentID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateIndent(final String indentNo) throws AppException {
        return indentDAO.checkDuplicateIndent(indentNo);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormFileDTO> getAllIndentFiles(final IndentFormDTO indentFormDTO) throws AppException
    {
        long indentID = 0;
        if (!StringUtils.isEmpty(indentFormDTO.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(indentFormDTO.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        return indentDAO.getAllIndentFiles(indentID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public IndentFormFileDTO getSelectedIndentFileUploaded(final long indentID, final long fileUploadID) 
             throws AppException 
    {
        return indentDAO.getSelectedIndentFileUploaded(indentID, fileUploadID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertIndentFormUploadedFileDetail(final IndentFormDTO indentFormDTO, final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
            sessUserID = empLoginObj.getEmployeeID();
        }
        long indentID = 0;
        if (!StringUtils.isEmpty(indentFormDTO.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(indentFormDTO.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }

        IndentFormFileDTO fileDTO = new IndentFormFileDTO();
        if (indentFormDTO.getIndentFile() != null) {
            String fileName = indentFormDTO.getIndentFile().getOriginalFilename();

            String appUploadFolder = ApplicationConstants.APPLICATION_FILE_UPLOAD_LOCATION;
            String destinationFilePath ="";
            try {
                String indentUploadFolderPath = appUploadFolder + File.separator
                        + indentID;
                File destFolderPath = new File(indentUploadFolderPath);
                if (!destFolderPath.exists()) {
                    destFolderPath.mkdirs();
                }
                destinationFilePath = destFolderPath + File.separator + fileName;
                File destinationFile = new File(destinationFilePath);
                indentFormDTO.getIndentFile().transferTo(destinationFile);
            } catch (IOException ie) {
                ie.printStackTrace();
                throw new AppException("Exception Occured while Executing the "
                        + "insertIndentFormUploadedFileDetail() :: " + ie.getMessage());
            }

            fileDTO.setFileName(fileName);
            fileDTO.setFileSize(indentFormDTO.getIndentFile().getSize() + "");
            fileDTO.setFileType(indentFormDTO.getIndentFile().getContentType());
            fileDTO.setIndentFormID(indentID);
            fileDTO.setFilePath(destinationFilePath);

        }

        return indentTransactDAO.insertIndentFormUploadedFileDetail(fileDTO, sessUserID);
    }

    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int deleteIndentFormUploadedFileDetail(final String encIndentID, final String encUpdFileID)
            throws AppException {
        
        long indentID = 0;
        if (!StringUtils.isEmpty(encIndentID)) {
            String decIndentID = encryptDecrypt.decrypt(encIndentID);
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        long fileUploadID = 0;
        if (!StringUtils.isEmpty(encUpdFileID)) {
            String decFileUploadID = encryptDecrypt.decrypt(encUpdFileID);
            if (NumberUtils.isNumber(decFileUploadID)) {
                fileUploadID = Long.parseLong(decFileUploadID);
            }
        }
        
        IndentFormFileDTO fileDTO = this.getSelectedIndentFileUploaded(indentID, fileUploadID);
        
        File f = new File(fileDTO.getFilePath());
        boolean bool = false;
        if(f.exists())
        {
            bool = f.delete();
        }
        
        return indentTransactDAO.deleteIndentFormUploadedFileDetail(fileUploadID);
        
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getIndentFormDeById(final int indentForm_no) throws AppException
    {
        
            return indentDAO.getIndentFormDeById(indentForm_no);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<IndentFormDTO> getIndentFormDeByIndentId(final long indentID) throws AppException
    {
        
            return indentDAO.getIndentFormDeByIndentId(indentID);
        
    }
     
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EmployeeProfileDTO> getEmpProfileDeByEmpID(final long empID) throws AppException
    {
        
            return indentDAO.getEmpProfileDeByEmpID(empID);
        
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public IndentFormDTO getSelectedIndentFormDetail(final long indentID) throws AppException
    {
        
            return indentDAO.getSelectedIndentFormDetail(indentID);
        
    }
    
}
