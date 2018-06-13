/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.service.serviceImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.service.impl.DdNumberServiceImpl;
import com.pdms.despatch.dao.daoImpl.InwardDAOImpl;
import com.pdms.despatch.dto.InwardDTO;
import com.pdms.despatch.itemsDto.InwardTempDTO;
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
public class InwardServiceImpl {
    
    private static final Logger logger = Logger.getLogger(InwardServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private InwardDAOImpl inwardDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public InwardServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertInwardDetails(final InwardTempDTO inObj, final long sessUserID) throws AppException
    {
        
            return inwardDAOImpl.insertInwardDetails(inObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<InwardDTO> getInwardRecord() throws AppException
    {
        
            return inwardDAOImpl.getInwardRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<InwardDTO> getInwardReById(final long id) throws AppException
    {
        
            return inwardDAOImpl.getInwardReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateInwardDetail(final List<InwardDTO> inObj, final long sessUserID) throws AppException
    {
        
            return inwardDAOImpl.updateInwardDetail(inObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getSlNoInwardForIncrem() throws AppException
    {
        
            return inwardDAOImpl.getSlNoInwardForIncrem();
                
    }
    
}
