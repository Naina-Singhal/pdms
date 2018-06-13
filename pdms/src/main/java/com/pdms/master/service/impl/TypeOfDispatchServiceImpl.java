/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.TypeOfDispatchDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.TypeOfDispatchDTO;
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
public class TypeOfDispatchServiceImpl {
    
    private static final Logger logger = Logger.getLogger(TypeOfDispatchServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private TypeOfDispatchDAOImpl dispatchDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public TypeOfDispatchServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertDispatchMaster(final List<TypeOfDispatchDTO> disObj, final long sessUserID) throws AppException
    {
        
            return dispatchDAOImpl.insertDispatchMaster(disObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TypeOfDispatchDTO> getDispatchMaRecord() throws AppException
    {
        
            return dispatchDAOImpl.getDispatchMaRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TypeOfDispatchDTO> getDispatchMaReById(final long id) throws AppException
    {
        
            return dispatchDAOImpl.getDispatchMaReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateDispatchMaDetail(final List<TypeOfDispatchDTO> disObj, final long sessUserID) throws AppException
    {
        
            return dispatchDAOImpl.updateDispatchMaDetail(disObj, sessUserID);
                
    }
}
