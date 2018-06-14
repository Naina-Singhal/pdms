/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoDetailsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.purchase.dto.PoTempDTO;
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
public class PoEntryServiceImpl {
     private static final Logger logger = Logger.getLogger(PoEntryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private PoEntryDAOImpl poEntryDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public PoEntryServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPoEntryDetail(final List<PoEntryDTO> unitObj, final long sessUserID) throws AppException
    {
        
            return poEntryDAOImpl.insertPoEntryDetail(unitObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getPoEntryDetails() throws AppException
    {
        return poEntryDAOImpl.getPoEntryDetails();
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getPoNoForIncrement() throws AppException
    {
        return poEntryDAOImpl.getPoNoForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<VendorDTO> getVendorDetails(final String vendor_name) throws AppException
    {
        return poEntryDAOImpl.getVendorDetails(vendor_name);
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<PoEntryDTO> getPoEntryDeByFileNo(final int PoNumber) throws AppException
    {
        return poEntryDAOImpl.getPoEntryDeByFileNo(PoNumber);
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PublicTenderItemsDTO> getPublicTenderDeFrPo(final long fiNum) throws AppException
    {
        return poEntryDAOImpl.getPublicTenderDeFrPo(fiNum);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getFileNoFrTenByPoNo(final long poNum) throws AppException
    {
        return poEntryDAOImpl.getFileNoFrTenByPoNo(poNum);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemMaDeByItemNo(final long itemNO) throws AppException
    {
        return poEntryDAOImpl.getItemMaDeByItemNo(itemNO);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<UnitDTO> getUnitDeByUnitNumber(final long unitNO) throws AppException
    {
        return poEntryDAOImpl.getUnitDeByUnitNumber(unitNO);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPoEntryDetailOne(final List<PoDetailsDTO> deObj, final long sessUserID) throws AppException
    {
        return poEntryDAOImpl.insertPoEntryDetailOne(deObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPoEntryItemsDe(final PoTempDTO itObj, final long sessUserID) throws AppException
    {
        return poEntryDAOImpl.insertPoEntryItemsDe(itObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getTenderIdByFileNumber(final long fiNO) throws AppException
    {
        return poEntryDAOImpl.getTenderIdByFileNumber(fiNO);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PublicTenderItemsDTO> getBreifDesFrPubTender() throws AppException
    {
        return poEntryDAOImpl.getBreifDesFrPubTender();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getPoEntryDeByFileNumber(final int fileNumber) throws AppException
    {
        return poEntryDAOImpl.getPoEntryDeByFileNumber(fileNumber);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getPoEntryReById(final long id) throws AppException
    {
        return poEntryDAOImpl.getPoEntryReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePoEntryDetail(final List<PoEntryDTO> unitObj, final long sessUserID) throws AppException
    {
        return poEntryDAOImpl.updatePoEntryDetail(unitObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public List<IndentFormDTO> getBreifDesFrIndentForm() throws AppException
    {
        return poEntryDAOImpl.getBreifDesFrIndentForm();
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoDetailsDTO> getPoNoteDesByPoNo(final long pono) throws AppException
    {
        return poEntryDAOImpl.getPoNoteDesByPoNo(pono);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getPoEntryReByIdFrPdf(final long id) throws AppException
    {
        return poEntryDAOImpl.getPoEntryReByIdFrPdf(id);
    }
}
