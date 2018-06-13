/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.ReceiverMaDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.ReceiverMaDTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author myassessment
 */
@Service
public class ReceiverMaServiceImpl {
    
    private static final Logger logger = Logger.getLogger(ReceiverMaServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private ReceiverMaDAOImpl receiverMaDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public ReceiverMaServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertReceiverDetail(final List<ReceiverMaDTO> recObj, final long sessUserID) throws AppException
    {
        
            return receiverMaDAOImpl.insertReceiverDetail(recObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ReceiverMaDTO> getReceiverRecord() throws AppException
    {
        
            return receiverMaDAOImpl.getReceiverRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ReceiverMaDTO> getReceiverRecordById(final long id) throws AppException
    {
        
            return receiverMaDAOImpl.getReceiverRecordById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateReceiverDetail(final List<ReceiverMaDTO> recObj, final long sessUserID) throws AppException
    {
        
            return receiverMaDAOImpl.updateReceiverDetail(recObj, sessUserID);
                
    }
}
