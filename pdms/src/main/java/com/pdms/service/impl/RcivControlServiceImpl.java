/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.RcivAuthorisationDAOImpl;
import com.pdms.dao.impl.RcivControlDAOImpl;
import com.pdms.dao.impl.RcivReleaseDAOImpl;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.dto.RcivControlDTO;
import com.pdms.dto.RcivReleaseDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivControlItemsDTO;
import com.pdms.itemsDto.receipt.RcivControlTempDTO;
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
public class RcivControlServiceImpl {
    private static final Logger logger = Logger.getLogger(RcivControlServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private RcivControlDAOImpl rcivControlDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public RcivControlServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertRcivControlData(final RcivControlTempDTO rcivCtlObj, final long sessUserID) throws AppException
    {
        
            return rcivControlDAOImpl.insertRcivControlData(rcivCtlObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivControlDTO> getRcivControlRecord() throws AppException
    {
        
            return rcivControlDAOImpl.getRcivControlRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<RcivControlDTO> getRcivControlReById(final long id) throws AppException
    {
        
            return rcivControlDAOImpl.getRcivControlReById(id);
        
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateRcivControlDetail(final List<RcivControlDTO> rcivCtlObj, final long sessUserID) throws AppException
    {
        
            return rcivControlDAOImpl.updateRcivControlDetail(rcivCtlObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getControlForIncrement() throws AppException
    {
        
            return rcivControlDAOImpl.getControlForIncrement();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivControlDTO> getRcivControlReByControlNo(final long conNo) throws AppException
    {
        
            return rcivControlDAOImpl.getRcivControlReByControlNo(conNo);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivControlItemsDTO> getRcivControlItemsByPoNo(final long DbNumber) throws AppException
    {
        
            return rcivControlDAOImpl.getRcivControlItemsByPoNo(DbNumber);
        
    }

}
