/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.ItemsDto.HoaItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import com.pdms.account.ItemsDto.VouNoTempDTO;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dao.impl.VoucherNoDAOImpl;
import com.pdms.account.dto.LcTableDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.dto.VoucherNoDTO;
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
public class VoucherNoServiceImpl {
    private static final Logger logger = Logger.getLogger(VoucherNoServiceImpl.class);
    
    @Autowired
    private VoucherNoDAOImpl voucherNoDAOImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertVoucherNoEntry(final VouNoTempDTO vounoObj, final long sessUserID) throws AppException {
        return voucherNoDAOImpl.insertVoucherNoEntry(vounoObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getVoucherNoDaRecord()  throws AppException {
        return voucherNoDAOImpl.getVoucherNoDaRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getVoucherNos()  throws AppException {
        return voucherNoDAOImpl.getVoucherNos();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getRequisitionNoFrInc()  throws AppException {
        return voucherNoDAOImpl.getRequisitionNoFrInc();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherDTO> getVoucherDaEnDeByCompCode(final long compCode)  throws AppException {
        return voucherNoDAOImpl.getVoucherDaEnDeByCompCode(compCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getVoucherNoDeByReqNo(final long reqNumber) throws AppException {
        return voucherNoDAOImpl.getVoucherNoDeByReqNo(reqNumber);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getVoucherNumberReById(final long id) throws AppException {
        return voucherNoDAOImpl.getVoucherNumberReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateVoucherNumberDetail(final List<VoucherNoDTO> vounoObj, final long sessUserID) throws AppException {
        return voucherNoDAOImpl.updateVoucherNumberDetail(vounoObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HoaItemsInVouDaEnDTO> getVoucherDaEnHoaByPo(final long Ponumber) throws AppException {
        return voucherNoDAOImpl.getVoucherDaEnHoaByPo(Ponumber);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int saveLcTableDe(final List<LcTableDTO> lcObj, final long sessUserID) throws AppException {
        return voucherNoDAOImpl.saveLcTableDe(lcObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LcTableDTO> getOpenBalanceRecord() throws AppException {
        return voucherNoDAOImpl.getOpenBalanceRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getVoucherNoDeOnDate(final String onDate) throws AppException {
        return voucherNoDAOImpl.getVoucherNoDeOnDate(onDate);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VouNoHoaItemsDTO> getVoucherNoItemsByHoaPdf(final String hoAccount, final String hoaDate) throws AppException {
        return voucherNoDAOImpl.getVoucherNoItemsByHoaPdf(hoAccount, hoaDate);
    }
}
