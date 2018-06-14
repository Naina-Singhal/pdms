/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.MaterialReceiptDAOImpl;
import com.pdms.dto.MaterialReceItemDTO;
import com.pdms.dto.MaterialReceLrCleDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
import com.pdms.itemsDto.receipt.MaterialReceiptTempDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author myassessment
 */
@Service
public class MaterialReceiptServiceImpl {
    
     private static final Logger logger = Logger.getLogger(MaterialReceiptServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private MaterialReceiptDAOImpl materialReceiptDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public MaterialReceiptServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertMaterialReceipt(final MaterialReceiptTempDTO mareObj, final long sessUserID) throws AppException
    {
        
            return materialReceiptDAOImpl.insertMaterialReceipt(mareObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertIssueVoucherItemData(final List<MaterialReceItemDTO> mrit, final long sessUserID) throws AppException
    {
        
            return materialReceiptDAOImpl.insertIssueVoucherItemData(mrit, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<MaterialReceiptDTO> getMaterialReceRecord() throws AppException
    {
        return materialReceiptDAOImpl.getMaterialReceRecord();
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<MaterialReceiptDTO> getMaterialRecReByDbNo(final int DbNumber) throws AppException
    {
        return materialReceiptDAOImpl.getMaterialRecReByDbNo(DbNumber);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialReceiptDTO> getMaterialReceiptReById(final long id) throws AppException
    {
        return materialReceiptDAOImpl.getMaterialReceiptReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateMaterialReceiptDetail(final List<MaterialReceiptDTO> mareObj, final long sessUserID) throws AppException
    {
        return materialReceiptDAOImpl.updateMaterialReceiptDetail(mareObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertMaterialReceCleData(final List<MaterialReceLrCleDTO> cleObj, final long sessUserID) throws AppException
    {
        return materialReceiptDAOImpl.insertMaterialReceCleData(cleObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialReceiptDTO> getMateReceDbNosByUser(final long userID) throws AppException
    {
        return materialReceiptDAOImpl.getMateReceDbNosByUser(userID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getDbNumForIncrement() throws AppException
    {
        return materialReceiptDAOImpl.getDbNumForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialReceiptDTO> getMaterialRecReByPoNo(final int PoNuMber) throws AppException
    {
        return materialReceiptDAOImpl.getMaterialRecReByPoNo(PoNuMber);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MaterialReceiptItemsDTO> getMaterialRecItemsReByDbNo(final long DbNuMber) throws AppException
    {
        return materialReceiptDAOImpl.getMaterialRecItemsReByDbNo(DbNuMber);
    }
}
