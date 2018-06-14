/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dao.impl.TransferEnScDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.dto.TransferEntryDTO;
import com.pdms.exception.AppException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naagu
 */
@Service
public class TransferEnScServiceImpl {
    private static final Logger logger = Logger.getLogger(TransferEnScServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private TransferEnScDAOImpl trEnScDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public TransferEnScServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertTransferEnScDetails(final List<TransferEntryDTO> traObj, final long sessUserID) throws AppException
    {
        
            return trEnScDAOImpl.insertTransferEnScDetails(traObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TransferEntryDTO> getTransferEnScRecord() throws AppException
    {
        
            return trEnScDAOImpl.getTransferEnScRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TransferEntryDTO> getTransferEnScReById(final long id) throws AppException
    {
        
            return trEnScDAOImpl.getTransferEnScReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateTransferEnScDetail(final List<TransferEntryDTO> traObj, final long sessUserID) throws AppException
    {
        
            return trEnScDAOImpl.updateTransferEnScDetail(traObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getTENumForIncrement() throws AppException
    {
        
            return trEnScDAOImpl.getTENumForIncrement();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TransferEntryDTO> getTransferEnPdfReById(final long id) throws AppException
    {
        
            return trEnScDAOImpl.getTransferEnPdfReById(id);
                
    }
}
