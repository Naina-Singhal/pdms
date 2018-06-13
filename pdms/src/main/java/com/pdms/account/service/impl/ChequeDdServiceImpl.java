/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.ChequeDdDAOImpl;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.ChequeDdEntryDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
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
public class ChequeDdServiceImpl {
    private static final Logger logger = Logger.getLogger(ChequeDdServiceImpl.class);
    
    @Autowired
    private ChequeDdDAOImpl ddDAOImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertChequeDdEntry(final List<ChequeDdEntryDTO> ddObj, final long sessUserID) throws AppException {
        int retval = 0;
        for(ChequeDdEntryDTO que: ddObj){
            long a = Long.parseLong(que.getBalDocument());            
            if(a <= 0){
                retval =  -5;
            }else{
                retval =  ddDAOImpl.insertChequeDdEntry(ddObj, sessUserID);
            }
        }
        return retval;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<ChequeDdEntryDTO> getChequeDdDaRecord() throws AppException {
        return ddDAOImpl.getChequeDdDaRecord();
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getReceiptNoForIncrement() throws AppException {
        return ddDAOImpl.getReceiptNoForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getChallanNoForIncrement() throws AppException {
        return ddDAOImpl.getChallanNoForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getSlNoForIncrement() throws AppException {
        return ddDAOImpl.getSlNoForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ChequeDdEntryDTO> getChequeAndDdReById(final long id) throws AppException {
        return ddDAOImpl.getChequeAndDdReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateChequeAndDdDetail(final List<ChequeDdEntryDTO> ddObj, final long sessUserID) throws AppException {
        return ddDAOImpl.updateChequeAndDdDetail(ddObj, sessUserID);
    }
}
