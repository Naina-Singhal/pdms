/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.CsrvPreparationDAOImpl;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.CsrvPreparationTempDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
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
 * @author myassessment
 */
@Service
public class CsrvPreparationServiceImpl {
    
   
     private static final Logger logger = Logger.getLogger(CsrvPreparationServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private CsrvPreparationDAOImpl csrvPreparationDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
     public CsrvPreparationServiceImpl(){
        
    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertCsrvPreparationDetails(final CsrvPreparationTempDTO csrvObj, final long sessUserID) throws AppException
    {
        
            return csrvPreparationDAOImpl.insertCsrvPreparationDetails(csrvObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CSRVpreparationDTO> getCsrvPreparationRecord() throws AppException
    {
        
            return csrvPreparationDAOImpl.getCsrvPreparationRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CSRVpreparationDTO> getCsrvPreparationReById(final long id) throws AppException
    {
        
            return csrvPreparationDAOImpl.getCsrvPreparationReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateCsrvPreparationDetail(final List<CSRVpreparationDTO> csrvObj, final long sessUserID) throws AppException
    {
        
            return csrvPreparationDAOImpl.updateCsrvPreparationDetail(csrvObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getRvNumForIncrement() throws AppException
    {
        
            return csrvPreparationDAOImpl.getRvNumForIncrement();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CSRVpreparationDTO> getCsrvPreparationReByPo(final long PoNumber) throws AppException
    {
        
            return csrvPreparationDAOImpl.getCsrvPreparationReByPo(PoNumber);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CsrvPreparationItemsDTO> getCsrvPreparaItemsReByPo(final long PoNumber) throws AppException
    {
        
            return csrvPreparationDAOImpl.getCsrvPreparaItemsReByPo(PoNumber);
        
    }
    
}
