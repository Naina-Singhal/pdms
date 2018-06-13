/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.ItemsDto.BillTempDTO;
import com.pdms.account.dao.impl.BillEntryDAOImpl;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.BillEntryDTO;
import com.pdms.account.dto.BillEntryItemsDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.PaymentDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
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
public class BillEntryServiceImpl {
     private static final Logger logger = Logger.getLogger(BillEntryServiceImpl.class);
    
    @Autowired
    private BillEntryDAOImpl daoImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertBillDaEntry(final BillTempDTO billObj, final long sessUserID) throws AppException {
        return daoImpl.insertBillDaEntry(billObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BillEntryDTO> getBillEntryRecord() throws AppException {
        return daoImpl.getBillEntryRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoEntryDTO> getVenderNaFrPoEn() throws AppException {
        return daoImpl.getVenderNaFrPoEn();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PaymentDTO> getPaymentTerms() throws AppException  {
        return daoImpl.getPaymentTerms();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VendorDTO> getGstinNumberFrVen() throws AppException  {
        return daoImpl.getGstinNumberFrVen();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BillEntryDTO> getBillEntryNumRe() throws AppException  {
        return daoImpl.getBillEntryNumRe();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BillEntryDTO> getBillEnEditLi(final long billID) throws AppException  {
        return daoImpl.getBillEnEditLi(billID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateBillEntry(final List<BillEntryDTO> billObj, final long sessUserID) throws AppException  {
        return daoImpl.updateBillEntry(billObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BillEntryDTO> getBillEntryReByPoNo(final long poNum) throws AppException  {
        return daoImpl.getBillEntryReByPoNo(poNum);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PoDeItemsDTO> getPoDeItemsByPoNumber(final long poNum) throws AppException  {
        return daoImpl.getPoDeItemsByPoNumber(poNum);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemMaDeByItemCode(final long itemCode) throws AppException  {
        return daoImpl.getItemMaDeByItemCode(itemCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<UnitDTO> getUnitDeByUnitCode(final long unitCode) throws AppException  {
        return daoImpl.getUnitDeByUnitCode(unitCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertBillEntryItemsData(final List<BillEntryItemsDTO> itemsObj, final long sessUserID) throws AppException  {
        return daoImpl.insertBillEntryItemsData(itemsObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BillEntryItemsDTO> getBillItemsListByPoNo(final long poNumBer) throws AppException  {
        return daoImpl.getBillItemsListByPoNo(poNumBer);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemMaDeByCategotyId(final long category_id) throws AppException  {
        return daoImpl.getItemMaDeByCategotyId(category_id);
    }
}
