/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.IndentTenderDAOImpl;
import com.pdms.dao.impl.VendorIndentDAOImpl;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.PublicTenderDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.PublicTenderVendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.utils.EncryptDecrypt;
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
public class IndentTenderServiceImpl {

    private static final Logger logger = Logger.getLogger(IndentTenderServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private IndentTenderDAOImpl iTenderDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private MasterLookUpServiceImpl masterService;

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private IndentServiceImpl indentService;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public IndentTenderServiceImpl() {

    }

    /*
    Default Constructor 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllTendorIndentForms(final HttpServletRequest request)
            throws AppException {

        HttpSession session = request.getSession(false);
        long sectionID = 0;
        long sessUserID = 0;
        String empType = "";
        String sessionKey = "";
        long signInUser = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
            sessionKey = empLoginObj.getSessionKey();
        }
        DesignationDTO desigObj = empLoginObj.getDesignationDTO();
        long isApprover = desigObj.getIsApprovingAuthority();
        long isSignIn = desigObj.getIsSigningAuthority();
        if (isSignIn >= 1) {
            signInUser = desigObj.getDesignationID();
        }
        logger.info("------looooger--------"+sessUserID+"--"+signInUser);
        return iTenderDAO.getAllTendorIndentForms(sessUserID, signInUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int saveIndentPublicTenderDetails(final PublicTenderDTO tenderObj,
            final HttpServletRequest request) throws AppException {
        long sessUserID = 0;
        HttpSession session = request.getSession(false);
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }

        long indentID = 0;
        if (!StringUtils.isEmpty(tenderObj.getEncFieldValue())) {
            String decIndentID = encryptDecrypt.decrypt(tenderObj.getEncFieldValue());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }

        tenderObj.setIndentID(indentID);

        return iTenderDAO.insertPublicTenderDetails(tenderObj, sessUserID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<PublicTenderDTO> getAllPublicTenderDetails(final HttpServletRequest request)
            throws AppException {
        return iTenderDAO.fetchAllPublicTenders();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public PublicTenderDTO fetchSelectedPublicTenders(final HttpServletRequest request,
            final String encTenderID) throws AppException {
        PublicTenderDTO tenderObj = new PublicTenderDTO();

        long tenderID = 0;
        if (!StringUtils.isEmpty(encTenderID)) {
            String decTendID = encryptDecrypt.decrypt(encTenderID);
            if (NumberUtils.isNumber(decTendID)) {
                tenderID = Long.parseLong(decTendID);
            }
        }

        tenderObj = iTenderDAO.fetchSelectedPublicTenders(tenderID);

        List<PublicTenderItemsDTO> tenderItemsLi = iTenderDAO.fetchAllPublicTenderItemDetails(tenderID,tenderObj.getIndentID());
        tenderObj.setTenderItemsLi(tenderItemsLi);

        PublicTenderVendorDTO ptVendorObj = this.fetchSelectedPublicTenderVendorsByTender
                    (tenderID);
        if(ptVendorObj.getTenderID()>0)
        {
            tenderObj.setPtVendorObj(ptVendorObj);
        }
        
        IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encryptDecrypt.encrypt(tenderObj.getIndentID() + ""));
        tenderObj.setIndentObj(indentObj);

        return tenderObj;
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public PublicTenderDTO fetchSelectedPublicTendersByIndent(final HttpServletRequest request,
            final String encIndentID) throws AppException {
        PublicTenderDTO tenderObj = new PublicTenderDTO();

        long indentID = 0;
        if (!StringUtils.isEmpty(encIndentID)) {
            String decIndentID = encryptDecrypt.decrypt(encIndentID);
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }

        tenderObj = iTenderDAO.fetchSelectedPublicTendersByIndent(indentID);

        List<PublicTenderItemsDTO> tenderItemsLi = iTenderDAO.fetchAllPublicTenderItemDetails
                                                (tenderObj.getpTenderID(),tenderObj.getIndentID());
        tenderObj.setTenderItemsLi(tenderItemsLi);

        PublicTenderVendorDTO ptVendorObj = this.fetchSelectedPublicTenderVendorsByTender
                    (tenderObj.getpTenderID());
        if(ptVendorObj.getTenderID()>0)
        {
            tenderObj.setPtVendorObj(ptVendorObj);
        }
        
        IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail(encryptDecrypt.encrypt(tenderObj.getIndentID() + ""));
        tenderObj.setIndentObj(indentObj);

        return tenderObj;
    }

    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateIndentPublicTenderDetails(final PublicTenderDTO tenderObj,
            final HttpServletRequest request) throws AppException {
        long sessUserID = 0;
        HttpSession session = request.getSession(false);
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }

        long indentID = 0;
        if (!StringUtils.isEmpty(tenderObj.getEncIndentID())) {
            String decIndentID = encryptDecrypt.decrypt(tenderObj.getEncIndentID());
            if (NumberUtils.isNumber(decIndentID)) {
                indentID = Long.parseLong(decIndentID);
            }
        }
        long tenderID = 0;
        if (!StringUtils.isEmpty(tenderObj.getEncTenderID())) {
            String decTenderID = encryptDecrypt.decrypt(tenderObj.getEncTenderID());
            if (NumberUtils.isNumber(decTenderID)) {
                tenderID = Long.parseLong(decTenderID);
            }
        }

        tenderObj.setIndentID(indentID);
        tenderObj.setpTenderID(tenderID);

        return iTenderDAO.updatePublicTenderDetails(tenderObj, sessUserID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int mapVendorToPublicTenderDetails(final PublicTenderVendorDTO  ptVendorObj,
            final HttpServletRequest request) throws AppException {
        long sessUserID = 0;
        HttpSession session = request.getSession(false);
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        
        return iTenderDAO.updatePublicTenderMapVendorDetails(ptVendorObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public PublicTenderVendorDTO fetchSelectedPublicTenderVendorsByTender(final long tender) 
            throws AppException
    {
        return iTenderDAO.fetchSelectedPublicTenderVendorsByTender(tender);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int generatePOForPublicTenderDetails(final PublicTenderVendorDTO  ptVendorObj,
            final HttpServletRequest request) throws AppException {
        long sessUserID = 0;
        HttpSession session = request.getSession(false);
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        
        return iTenderDAO.updatePublicTenderUpdatePODetails(ptVendorObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<PublicTenderItemsDTO> fetAllPublicTenderItemDeById(final long tenderID) 
            throws AppException
    {
        return iTenderDAO.fetAllPublicTenderItemDeById(tenderID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<PublicTenderDTO> getPublicTenderDeByFileNo(final long fileNO)  throws AppException
    {
        return iTenderDAO.getPublicTenderDeByFileNo(fileNO);
    }
    
}
