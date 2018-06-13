/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.POrderEntryDAOImpl;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.PoReleaseItemsDTO;
import com.pdms.itemsDto.receipt.PoReleaseTempDTO;
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
public class POrderEntryServiceImpl {
    
     private static final Logger logger = Logger.getLogger(PoEntryServiceImpl.class);
     
     @Autowired
    private EncryptDecrypt encryptDecrypt;
     
     @Autowired
     private POrderEntryDAOImpl pOrderEntryDAOImpl;
     
     public POrderEntryServiceImpl(){
         
     }
     
     
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPOrderEnDetail(final PoReleaseTempDTO poedObj, final long sessUserID) throws AppException
    {
        
            return pOrderEntryDAOImpl.insertPOrderEnDetail(poedObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<POrderEntryDTO> getPOrderEnRecord() throws AppException
    {
        return pOrderEntryDAOImpl.getPOrderEnRecord();
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<POrderEntryDTO> getPoNoFromPoEn(String fromDate, String toDate) throws AppException
    {
        
            return pOrderEntryDAOImpl.getPoNoFromPoEn(fromDate, toDate);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PlaceOfDeliveryDTO> getPlaceOfDelivery() throws AppException{
            return pOrderEntryDAOImpl.getPlaceOfDelivery();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<SectionDTO> getSectionName() throws AppException{
            return pOrderEntryDAOImpl.getSectionName();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DesignationDTO> getDesignationName() throws AppException{
            return pOrderEntryDAOImpl.getDesignationName();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HeadOfAccountDTO> getHeadOfAccount() throws AppException{
            return pOrderEntryDAOImpl.getHeadOfAccount();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VendorDTO> getVendorNames() throws AppException{
            return pOrderEntryDAOImpl.getVendorNames();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<POrderEntryDTO> getPoNoOnlyFromPoEn() throws AppException{
            return pOrderEntryDAOImpl.getPoNoOnlyFromPoEn();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<POrderEntryDTO> getPOrderEnDeByPoNo(final long poNumBer) throws AppException{
            return pOrderEntryDAOImpl.getPOrderEnDeByPoNo(poNumBer);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<POrderEntryDTO> getPoReleaseReById(final long id) throws AppException{
            return pOrderEntryDAOImpl.getPoReleaseReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePoReleaseDetail(final List<POrderEntryDTO> poedObj, final long sessUserID) throws AppException{
            return pOrderEntryDAOImpl.updatePoReleaseDetail(poedObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IndentFormDTO> getAllIndentFormsByIndentId(final long id) throws AppException{
            return pOrderEntryDAOImpl.getAllIndentFormsByIndentId(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoReleaseItemsDTO> getPoReleaseItemsReByPo(final long pono) throws AppException{
            return pOrderEntryDAOImpl.getPoReleaseItemsReByPo(pono);
    }
}
