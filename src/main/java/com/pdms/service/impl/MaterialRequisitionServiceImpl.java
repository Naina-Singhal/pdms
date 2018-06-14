/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.MaterialRequisitionDAOImpl;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialRequisitionTempDTO;
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
public class MaterialRequisitionServiceImpl {
    
     private static final Logger logger = Logger.getLogger(PoEntryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private MaterialRequisitionDAOImpl materialRequisitionDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public MaterialRequisitionServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertMaterialRequisition(final MaterialRequisitionTempDTO matrsObj, final long sessUserID) throws AppException
    {
        
            return materialRequisitionDAOImpl.insertMaterialRequisition(matrsObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialRequisitionDTO> getMaterialRequiRecord() throws AppException
    {
        
            return materialRequisitionDAOImpl.getMaterialRequiRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialRequisitionDTO> getMaterialRequisitionReById(final long id) throws AppException
    {
        
            return materialRequisitionDAOImpl.getMaterialRequisitionReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateMaterialRquisitionDetail(final List<MaterialRequisitionDTO> matrsObj, final long sessUserID) throws AppException
    {
        
            return materialRequisitionDAOImpl.updateMaterialRquisitionDetail(matrsObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getRequisitionForIncrement() throws AppException
    {
        
            return materialRequisitionDAOImpl.getRequisitionForIncrement();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialRequisitionDTO> getMaterialRequiReByRequiNo(final long requisiNo) throws AppException
    {
        
            return materialRequisitionDAOImpl.getMaterialRequiReByRequiNo(requisiNo);
        
    }
    
}
