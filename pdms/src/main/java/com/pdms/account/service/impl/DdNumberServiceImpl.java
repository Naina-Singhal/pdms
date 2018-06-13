/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.TypeOfDispatchDAOImpl;
import com.pdms.master.dto.TypeOfDispatchDTO;
import com.pdms.master.service.impl.TypeOfDispatchServiceImpl;
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
public class DdNumberServiceImpl {
    
    private static final Logger logger = Logger.getLogger(DdNumberServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private DdNumberDAOImpl ddNumberDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public DdNumberServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertDdNumberDetails(final List<DdNumberDTO> ddObj, final long sessUserID) throws AppException
    {
        
            return ddNumberDAOImpl.insertDdNumberDetails(ddObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DdNumberDTO> getDdNumberRecord() throws AppException
    {
        
            return ddNumberDAOImpl.getDdNumberRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<DdNumberDTO> getDdNumberReById(final long id)  throws AppException
    {
        
            return ddNumberDAOImpl.getDdNumberReById(id);
                
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateDdNumberDetail(final List<DdNumberDTO> ddObj, final long sessUserID)  throws AppException
    {
        
            return ddNumberDAOImpl.updateDdNumberDetail(ddObj, sessUserID);
                
    }
    
}
