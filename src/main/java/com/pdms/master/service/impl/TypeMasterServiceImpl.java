/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.TypeMasterDAOImpl;
import com.pdms.master.dao.impl.TypeOfDispatchDAOImpl;
import com.pdms.master.dto.TypeDTO;
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
public class TypeMasterServiceImpl {
    private static final Logger logger = Logger.getLogger(TypeMasterServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private TypeMasterDAOImpl typeDAOImpl;    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public TypeMasterServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertTypeMaster(final List<TypeDTO> typeObj, final long sessUserID) throws AppException
    {
        
            return typeDAOImpl.insertTypeMaster(typeObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TypeDTO> getTypeMaRecord() throws AppException
    {
        
            return typeDAOImpl.getTypeMaRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TypeDTO> getTypeMaReById(final long id) throws AppException
    {
        
            return typeDAOImpl.getTypeMaReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateTypeMaDetail(final List<TypeDTO> typeObj, final long sessUserID) throws AppException
    {
        
            return typeDAOImpl.updateTypeMaDetail(typeObj, sessUserID);
                
    }
    
}
