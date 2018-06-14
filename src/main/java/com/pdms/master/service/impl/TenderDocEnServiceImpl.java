/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.TenderDocMaDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.dao.impl.TenderDocEnDAOImpl;
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
public class TenderDocEnServiceImpl {
    
    private static final Logger logger = Logger.getLogger(TenderDocEnServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private TenderDocEnDAOImpl docEnDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public TenderDocEnServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertTenderDocDetail(final List<TenderDocMaDTO> tenObj, final long sessUserID) throws AppException
    {
        long dupCont = 0;
        for (TenderDocMaDTO cdObj : tenObj) {
            dupCont = this.checkDuplicateTenderDoc(cdObj.getTenderNo());     
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return docEnDAOImpl.insertTenderDocDetail(tenObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TenderDocMaDTO> getTenderDocEnDetails() throws AppException
    {
        
            return docEnDAOImpl.getTenderDocEnDetails();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TenderDocMaDTO> getTenderDocReById(final long id) throws AppException
    {
        
            return docEnDAOImpl.getTenderDocReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateTenderDoc(final String tenderNo) throws AppException
    {
        
            return docEnDAOImpl.checkDuplicateTenderDoc(tenderNo);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public TenderDocMaDTO getSelectedTenderDoc(final long tenDocID) throws AppException
    {
        
            return docEnDAOImpl.getSelectedTenderDoc(tenDocID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateTenderDocDetail(final List<TenderDocMaDTO> tenObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(TenderDocMaDTO Obj: tenObj){            
            TenderDocMaDTO existObject = this.getSelectedTenderDoc(Obj.getTenderDocID());
            if(!(existObject.getTenderNo().equalsIgnoreCase(Obj.getTenderNo()))){
                
                dupCount = this.checkDuplicateTenderDoc(Obj.getTenderNo());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return docEnDAOImpl.updateTenderDocDetail(tenObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TenderDocMaDTO> getTenderDomentsByFile(final long fileNo) throws AppException
    {
        
            return docEnDAOImpl.getTenderDomentsByFile(fileNo);
    }
}
