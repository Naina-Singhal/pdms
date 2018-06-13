/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.VendorDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
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
public class VendorServiceImpl {
    
    private static final Logger logger = Logger.getLogger(VendorServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private VendorDAOImpl vendorDAO;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public VendorServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> getAllActiveVendorDetails() throws AppException
    {
        return vendorDAO.getAllVendorDetails(ApplicationConstants.ACTIVE_FLAG_VALUE);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorItemsDTO> getAllVendorItemDetails(final String encVendorID) throws AppException
    {
        long vendorID = 0;
        if (!StringUtils.isEmpty(encVendorID)) {
            String decvendorID = encryptDecrypt.decrypt(encVendorID);
            if (NumberUtils.isNumber(decvendorID)) {
                vendorID = Long.parseLong(decvendorID);
            }
        }
        
        return vendorDAO.getAllVendorItemDetails(vendorID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public VendorDTO getSelectedVendorDetails(final String encVendorID) throws AppException
    {
        long vendorID = 0;
        if (!StringUtils.isEmpty(encVendorID)) {
            String decvendorID = encryptDecrypt.decrypt(encVendorID);
            if (NumberUtils.isNumber(decvendorID)) {
                vendorID = Long.parseLong(decvendorID);
            }
        }
        
        return vendorDAO.getSelectedVendorDetails(vendorID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int checkVendorExistence(final String vendorName) throws AppException{
        return vendorDAO.checkVendorExistence(vendorName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertVendorDetails(final VendorDTO vendorObj, final HttpServletRequest request) throws AppException
    {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        
        long dupCnt = this.checkDuplicateVendor(vendorObj.getVendorCode(), vendorObj.getVendorName());
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return vendorDAO.insertVendorDetails(vendorObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateVendorDetails(final VendorDTO vendorObj, final HttpServletRequest request) throws AppException
    {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        long vendorID = 0;
        if (!StringUtils.isEmpty(vendorObj.getEncVendorID())) {
            String decvendorID = encryptDecrypt.decrypt(vendorObj.getEncVendorID());
            if (NumberUtils.isNumber(decvendorID)) {
                vendorID = Long.parseLong(decvendorID);
            }
        }
        
        vendorObj.setVendorID(vendorID);
        
        long dupCnt = 0;
        VendorDTO exisVObj = this.getSelectedVendorDetails(vendorObj.getEncVendorID());
        
        if((!vendorObj.getVendorCode().equalsIgnoreCase(exisVObj.getVendorCode()))
                &&(!vendorObj.getVendorName().equalsIgnoreCase(exisVObj.getVendorName())))
        {
            dupCnt = this.checkDuplicateVendor(vendorObj.getVendorCode(), vendorObj.getVendorName());
        }
        else if((!vendorObj.getVendorName().equalsIgnoreCase(exisVObj.getVendorName())))
        {
            dupCnt = this.checkDuplicateVendor("0", vendorObj.getVendorName());
        }
        else if((!vendorObj.getVendorCode().equalsIgnoreCase(exisVObj.getVendorCode())))
        {
            dupCnt = this.checkDuplicateVendor(vendorObj.getVendorCode(),"0");
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return vendorDAO.updateVendorDetails(vendorObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertVendorItemDetails(final List<VendorItemsDTO> vendorItemLi,final String encVendorID, 
            final HttpServletRequest request) throws AppException
    {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        
        long vendorID = 0;
        if (!StringUtils.isEmpty(encVendorID)) {
            String decvendorID = encryptDecrypt.decrypt(encVendorID);
            if (NumberUtils.isNumber(decvendorID)) {
                vendorID = Long.parseLong(decvendorID);
            }
        }
        
        return vendorDAO.insertVendorItemDetails(vendorItemLi, vendorID, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int deleteSelectedVendorItemDetails(final String encVendorItemID) throws AppException
    {
        long vendorItemID=0;
        if (!StringUtils.isEmpty(encVendorItemID)) {
            String decvendorItemID = encryptDecrypt.decrypt(encVendorItemID);
            if (NumberUtils.isNumber(decvendorItemID)) {
                vendorItemID = Long.parseLong(decvendorItemID);
            }
        }
        
        return vendorDAO.deleteSelectedVendorItemDetails(vendorItemID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int mapSelectedVendorItemDetails(final String encVendorItemID) throws AppException {
        long vendorItemID=0;
        if (!StringUtils.isEmpty(encVendorItemID)) {
            String decvendorItemID = encryptDecrypt.decrypt(encVendorItemID);
            if (NumberUtils.isNumber(decvendorItemID)) {
                vendorItemID = Long.parseLong(decvendorItemID);
            }
        }
        
        return vendorDAO.mapSelectedVendorItemDetails(vendorItemID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertSelectedVendorItemDetails(final VendorItemsDTO vendorItem, 
            final HttpServletRequest request) throws AppException
    {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            sessUserID = empLoginObj.getEmployeeID();
        }
        
        long vendorID = 0;
        if (!StringUtils.isEmpty(vendorItem.getEncVendorID())) {
            String decvendorID = encryptDecrypt.decrypt(vendorItem.getEncVendorID());
            if (NumberUtils.isNumber(decvendorID)) {
                vendorID = Long.parseLong(decvendorID);
            }
        }
        
        vendorItem.setVendorID(vendorID);
        
        return vendorDAO.insertSelectedVendorItemDetails(vendorItem, sessUserID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateVendor(final String vCode,final String vName) throws AppException
    {
        return vendorDAO.checkDuplicateVendor(vCode, vName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public String generateVendorCode() throws AppException
    {
        String vendorCode = "";
        long nVendorID = vendorDAO.getNextVendorIDToGenerateVendorCode();        
        if(nVendorID > 0)
        {
            vendorCode = "VEN-"+(nVendorID + 1);            
        }
        else
        {
            vendorCode = "VEN-1";
        }
        return vendorCode;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> getGstinNoByVendorName(final String venCode) throws AppException{
        return vendorDAO.getGstinNoByVendorName(venCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> getVendorNameByCode(final String venCode) throws AppException{
        return vendorDAO.getVendorNameByCode(venCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> getVendorDeByVendorID(final long vendorID) throws AppException
    {
        return vendorDAO.getVendorDeByVendorID(vendorID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public String getVendorCodeById(final long venId) throws AppException
    {
        return vendorDAO.getVendorCodeById(venId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public ArrayList<String> getVendorOnlyCodesSearch(final String keywords) throws AppException
    {
        return vendorDAO.getVendorOnlyCodesSearch(keywords);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public String getVendorDetailsByName(final String vendor_name) throws AppException
    {
        return vendorDAO.getVendorDetailsByName(vendor_name);
    }
}
