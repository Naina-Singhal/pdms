/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.PreAuditDAOImpl;
import com.pdms.account.dto.PreAuditFrDTO;
import com.pdms.account.dto.PreAuditSeUpDTO;
import com.pdms.despatch.dto.DispatchEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.service.impl.RtgsServiceImpl;
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
public class PreAuditServiceImpl {
    
    private static final Logger logger = Logger.getLogger(PreAuditServiceImpl.class);
    
    @Autowired
    private PreAuditDAOImpl preAuditDAOImpl;
    
    public PreAuditServiceImpl(){
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DispatchEntryDTO> getDispatchEnDetails() throws AppException {
        return preAuditDAOImpl.getDispatchEnDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPreAuditFrDetail(final List<PreAuditFrDTO> preObj, final long sessUserID) throws AppException {
        return preAuditDAOImpl.insertPreAuditFrDetail(preObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PreAuditFrDTO> getPreAuditFrDetails() throws AppException {
        return preAuditDAOImpl.getPreAuditFrDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPreAuditSeUpDetail(final List<PreAuditSeUpDTO> preseObj, final long sessUserID) throws AppException {
        return preAuditDAOImpl.insertPreAuditSeUpDetail(preseObj, sessUserID);
    }
}
