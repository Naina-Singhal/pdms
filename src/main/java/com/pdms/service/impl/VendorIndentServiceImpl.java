/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.VendorIndentDAOImpl;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
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
public class VendorIndentServiceImpl {

    private static final Logger logger = Logger.getLogger(VendorIndentServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private VendorIndentDAOImpl vIndentDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private MasterLookUpServiceImpl masterService;

    @Autowired
    private ItemServiceImpl itemService;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public VendorIndentServiceImpl() {

    }

    /*
    Default Constructor 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllVendorIndentForms(final HttpServletRequest request)
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

        return vIndentDAO.getAllVendorIndentForms(sessUserID, signInUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getCountOfVendorItemStatus(final String vendorItemStatus, final long vendorId,
            final long itemID) throws AppException {
        return vIndentDAO.getCountOfVendorItemStatus(vendorItemStatus, vendorId, itemID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> fetchItemRelatedVendorDetails(final String encItemID, final long indent_id) throws AppException {

        long itemID = 0;
//        if(!StringUtils.isEmpty(encItemID))
//        {
//            String decItemID = encryptDecrypt.decrypt(encItemID);
//            if(NumberUtils.isNumber(decItemID))
//            {
//                itemID = Long.valueOf(decItemID);
//            }
//        }
        if (NumberUtils.isNumber(encItemID)) {
            itemID = Long.valueOf(encItemID);
        }
        return vIndentDAO.fetchItemRelatedVendorDetails(itemID, indent_id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public VendorItemsDTO insertVendorIndentItemMappingDetails(final VendorItemsDTO vItemObj,
            final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
            sessUserID = empLoginObj.getEmployeeID();
        }

        long indentID = 0;
        
        ItemDTO itemObj = itemService.getSelectedItemDetail(encryptDecrypt.encrypt(vItemObj.getItemID() + ""));        
        vItemObj.setCategoryID(itemObj.getCategoryDTO().getCategoryID());
        vItemObj.setIndentID(Long.parseLong(vItemObj.getEncIndentID()));
        vItemObj.setFileNo(vItemObj.getFileNo());        

        long insRet = vIndentDAO.insertVendorIndentItemMappingDetails(vItemObj, sessUserID);

        return vItemObj;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public VendorItemsDTO updateVendorIndentMappedItems(final VendorItemsDTO vItemObj,
            final String encVendorID) throws AppException {

        long indentID = 0;
        
        indentID = Long.parseLong(vItemObj.getEncIndentID());
        long vendorID = 0;
        if (!StringUtils.isEmpty(encVendorID)) {
            String decVendorID = encryptDecrypt.decrypt(encVendorID);
            if (NumberUtils.isNumber(decVendorID)) {
                vendorID = Long.valueOf(decVendorID);
            }
        }
        ItemDTO itemObj = itemService.getSelectedItemDetail(encryptDecrypt.encrypt(vItemObj.getItemID() + ""));

        vItemObj.setCategoryID(itemObj.getCategoryDTO().getCategoryID());
        vItemObj.setIndentID(Long.parseLong(vItemObj.getEncIndentID()));
        vItemObj.setFileNo(vItemObj.getFileNo());        
        long upRetVal = vIndentDAO.updateVendorIndentMappedItems(vendorID, indentID, vItemObj.getItemID());

        return vItemObj;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> fetchAllVendorsForSelectedItem(final long itemID) throws AppException {
        return vIndentDAO.fetchAllVendorsForSelectedItem(itemID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long updateVendorIndentItemMappingDetails(final VendorItemsDTO vItemObj,
            final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);
            sessUserID = empLoginObj.getEmployeeID();
        }
        return vIndentDAO.updateVendorIndentItemMappingRecordedDetails(vItemObj, sessUserID);

    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getCountOfVendorOrder(final String Code) throws AppException {
        return vIndentDAO.getCountOfVendorOrder(Code);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorItemsDTO> getFileNoInVendorItems(final long itemId, final long indentId) throws AppException {
        return vIndentDAO.getFileNoInVendorItems(itemId, indentId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorItemsDTO> getVenItemMappingByfileNo(final long fileNumber) throws AppException {
        return vIndentDAO.getVenItemMappingByfileNo(fileNumber);
    }
}
