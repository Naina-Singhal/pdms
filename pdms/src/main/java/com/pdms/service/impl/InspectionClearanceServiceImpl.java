/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.InspectionClearanceDAOImpl;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.InspectionCleaItemsDTO;
import com.pdms.itemsDto.receipt.InspectionCleaTempDTO;
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
public class InspectionClearanceServiceImpl {
    
     private static final Logger logger = Logger.getLogger(InspectionClearanceServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private InspectionClearanceDAOImpl inspectionClearanceDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public InspectionClearanceServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertInspeClearance(final InspectionCleaTempDTO inspectObj, final long sessUserID) throws AppException
    {
        
            return inspectionClearanceDAOImpl.insertInspeClearance(inspectObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<InspectionClearanceDTO> getInspectionClearanceRecord() throws AppException
    {
        return inspectionClearanceDAOImpl.getInspectionClearanceRecord();
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public long getICNoForIncrement() throws AppException
    {
        return inspectionClearanceDAOImpl.getICNoForIncrement();
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<InspectionClearanceDTO> getInspecCleareReById(final long id) throws AppException
    {
        return inspectionClearanceDAOImpl.getInspecCleareReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateInspectCleareDetail(final List<InspectionClearanceDTO> inspectObj, final long sessUserID) throws AppException
    {
        return inspectionClearanceDAOImpl.updateInspectCleareDetail(inspectObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<InspectionCleaItemsDTO> getInspectionCleaItemsByDbNo(final long DbNuMber) throws AppException
    {
        return inspectionClearanceDAOImpl.getInspectionCleaItemsByDbNo(DbNuMber);
    }
}
