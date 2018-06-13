/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.MaterialRequisitionDAOImpl;
import com.pdms.dao.impl.RcivAuthorisationDAOImpl;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivAuthorisationItemsDTO;
import com.pdms.itemsDto.receipt.RcivAuthorisationTempDTO;
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
public class RcivAuthorisationServiceImpl {
    
     private static final Logger logger = Logger.getLogger(PoEntryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private RcivAuthorisationDAOImpl rcivAuthorisationDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public RcivAuthorisationServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertRcivAuthorisation(final RcivAuthorisationTempDTO rcivAuthObj, final long sessUserID) throws AppException
    {
        
            return rcivAuthorisationDAOImpl.insertRcivAuthorisation(rcivAuthObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivAuthorisationDTO> getRcivAuthoriRecord() throws AppException
    {
        
            return rcivAuthorisationDAOImpl.getRcivAuthoriRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivAuthorisationDTO> getRcivAuthorisationReById(final long id) throws AppException
    {
        
            return rcivAuthorisationDAOImpl.getRcivAuthorisationReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateRcivAuthorisationDetail(final List<RcivAuthorisationDTO> rcivAuthObj, final long sessUserID) throws AppException
    {
        
            return rcivAuthorisationDAOImpl.updateRcivAuthorisationDetail(rcivAuthObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RcivAuthorisationItemsDTO> getRcivAuthorisaItemsByPoNo(final long PoNumber) throws AppException
    {
        
            return rcivAuthorisationDAOImpl.getRcivAuthorisaItemsByPoNo(PoNumber);
        
    }
    
}
