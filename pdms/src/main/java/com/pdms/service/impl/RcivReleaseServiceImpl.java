/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.RcivAuthorisationDAOImpl;
import com.pdms.dao.impl.RcivReleaseDAOImpl;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.dto.RcivReleaseDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivReleaseItemsDTO;
import com.pdms.itemsDto.receipt.RcivReleaseTempDTO;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author STEINMETZ
 */
@Service
public class RcivReleaseServiceImpl {
    private static final Logger logger = Logger.getLogger(PoEntryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private RcivReleaseDAOImpl rcivReleaseDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public RcivReleaseServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertRcivRelease(final RcivReleaseTempDTO rcivReleObj, final long sessUserID) throws AppException
    {
        
            return rcivReleaseDAOImpl.insertRcivRelease(rcivReleObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivReleaseDTO> getRcivReleaseRecord() throws AppException
    {
        
            return rcivReleaseDAOImpl.getRcivReleaseRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivReleaseDTO> getRcivReleaseReById(final long id) throws AppException
    {
        
            return rcivReleaseDAOImpl.getRcivReleaseReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateRcivReleaseDetail(final List<RcivReleaseDTO> rcivReleObj, final long sessUserID) throws AppException
    {
        
            return rcivReleaseDAOImpl.updateRcivReleaseDetail(rcivReleObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivReleaseItemsDTO> getRcivReleaseItemsByPoNo(final long PoNumber) throws AppException
    {
        
            return rcivReleaseDAOImpl.getRcivReleaseItemsByPoNo(PoNumber);
        
    }
}
