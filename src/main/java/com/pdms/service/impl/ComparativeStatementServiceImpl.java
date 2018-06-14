/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.ComparativeStatementDAOImpl;
import com.pdms.dao.impl.IndentDAOImpl;
import com.pdms.dao.impl.POrderEntryDAOImpl;
import com.pdms.dto.ComparativeStatementDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentFormFileDTO;
import com.pdms.dto.IndentLogDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.NumberUtils;
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
public class ComparativeStatementServiceImpl {

    private static final Logger logger = Logger.getLogger(ComparativeStatementServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private ComparativeStatementDAOImpl cstDAOImpl;

    @Autowired
    private IndentServiceImpl indentService;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private VendorServiceImpl vendorService;

    @Autowired
    private ItemServiceImpl itemService;

     @Autowired
    private IndentDAOImpl indentDAO;
     
     @Autowired
     private POrderEntryServiceImpl pOrderEntryServiceImpl;
    /*
    Default Constructor 
     */
    public ComparativeStatementServiceImpl() {

    }

    /*
    Default Constructor 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllCSTIndentForms() throws AppException {
        return cstDAOImpl.getAllCSTIndentForms();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public IndentFormDTO getSelectedIndentFormDetail(final String encIndentID) throws AppException {
        return indentService.getSelectedIndentFormDetail(encIndentID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<VendorDTO> fetchItemRelatedVendorCSTDetails(final String encItemID)
            throws AppException {
        long itemID = 0;

        if (NumberUtils.isNumber(encItemID)) {
            itemID = Long.valueOf(encItemID);
        }

        return cstDAOImpl.fetchItemRelatedVendorCSTDetails(itemID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ComparativeStatementDTO> fetchCSTDetailsForSelectedVendor(final long fiNo, final int vid)
            throws AppException {
        long cstID = 0;

        //String encItemID = encryptDecrypt.encrypt(itemID);

        //VendorDTO vendorObj = vendorService.getSelectedVendorDetails(encVendorID);

        //ItemDTO itemObj = itemService.getSelectedItemDetail(encItemID);

        List<ComparativeStatementDTO> csDTO = cstDAOImpl.fetchCSTDetailsForSelectedVendor(fiNo, vid);

        //csDTO.setVendorID(vendorObj.getVendorID());
        //csDTO.setItemID(itemObj.getItemID());
        //csDTO.setVendorName(vendorObj.getVendorName());
        //csDTO.setItemName(itemObj.getItemName() + " [" + itemObj.getItemCode() + "]");

        return csDTO;
    }

    

    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getIndentdIdByFileNo(final long file_no) throws AppException  {
        long indentID = cstDAOImpl.getIndentdIdByFileNo(file_no);
        String e = encryptDecrypt.encrypt(Long.toString(indentID));
        logger.info("-----enc--"+e);
        IndentFormDTO intt = new IndentFormDTO();
        //IndentFormDTO ins = indentService.getSelectedIndentFormDetail(e);
        //ArrayList<IndentFormDTO> listObj = new ArrayList<>();
        
        List<IndentFormDTO> indentDTO = indentService.getIndentFormDeByIndentId(indentID);
        return indentDTO;
    }
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getFileNumbersList() throws AppException  {
        return cstDAOImpl.getFileNumbersList();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertCSTDetails(final List<ComparativeStatementDTO> cstDTO, final long sessUserID) throws AppException  {
        return cstDAOImpl.insertCSTDetails(cstDTO, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFileMappingDTO> getIndentdFormIdByFileNo(final long file_no) throws AppException  {
        return cstDAOImpl.getIndentdFormIdByFileNo(file_no);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getIndentFormIdByFileNo(final long file_no) throws AppException  {
        long indentID = cstDAOImpl.getIndentFormIdByFileNo(file_no);
        String e = encryptDecrypt.encrypt(Long.toString(indentID));
        logger.info("-----enc--"+indentID);
        
        
        List<IndentFormDTO> indentDTO = pOrderEntryServiceImpl.getAllIndentFormsByIndentId(indentID);
        return indentDTO;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ComparativeStatementDTO> fetchCSTDetailsForSelectedItem(final long fiNo, final long itemId) throws AppException  {
        return cstDAOImpl.fetchCSTDetailsForSelectedItem(fiNo, itemId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ComparativeStatementDTO> cstDetailsForVendorBased(final long fileNo, final long vendorID) throws AppException  {
        return cstDAOImpl.cstDetailsForVendorBased(fileNo, vendorID);
    }

}
