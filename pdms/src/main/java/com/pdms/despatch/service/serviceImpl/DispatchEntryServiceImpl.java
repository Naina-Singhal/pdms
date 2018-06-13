/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.service.serviceImpl;

import com.pdms.despatch.dao.daoImpl.DispatchEntryDAOImpl;
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
public class DispatchEntryServiceImpl {
    
    private static final Logger logger = Logger.getLogger(DispatchEntryServiceImpl.class);
    
    @Autowired
    private DispatchEntryDAOImpl disEnDaoImpl;
    
    /*
    Default Constructor 
     */
    public DispatchEntryServiceImpl(){

    }
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertDispatchEnDetail(final List<DispatchEntryDTO> disObj, final long sessUserID) throws AppException {
       return disEnDaoImpl.insertDispatchEnDetail(disObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DispatchEntryDTO> getFileMovementRecord() throws AppException {
       return disEnDaoImpl.getFileMovementRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DispatchEntryDTO> getFileMovementReById(final long id) throws AppException {
       return disEnDaoImpl.getFileMovementReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateFileMovementDetail(final List<DispatchEntryDTO> disObj, final long sessUserID) throws AppException {
       return disEnDaoImpl.updateFileMovementDetail(disObj, sessUserID);
    }
}
