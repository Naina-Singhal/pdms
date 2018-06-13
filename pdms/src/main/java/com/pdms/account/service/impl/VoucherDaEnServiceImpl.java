/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.VouTempDTO;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.RtgsServiceImpl;
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
public class VoucherDaEnServiceImpl {
    private static final Logger logger = Logger.getLogger(VoucherDaEnServiceImpl.class);
    
    @Autowired
    private VoucherDaEnDAOImpl voucherDaEnDAOImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertVoucherDaEntry(final VouTempDTO vouObj, final long sessUserID) throws AppException {
        return voucherDaEnDAOImpl.insertVoucherDaEntry(vouObj, sessUserID);
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherDTO> getVoucherDaRecord() throws AppException {
        return voucherDaEnDAOImpl.getVoucherDaRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getPprNoForIncrement() throws AppException {
        return voucherDaEnDAOImpl.getPprNoForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getCompCodeForIncrement() throws AppException {
        return voucherDaEnDAOImpl.getCompCodeForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherDTO> getVoucherPoDetails(final long poNum) throws AppException {
        return voucherDaEnDAOImpl.getVoucherPoDetails(poNum);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherDTO> getVoucherDaEnReById(final long id) throws AppException {
        return voucherDaEnDAOImpl.getVoucherDaEnReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateVoucherDaEnDetail(final List<VoucherDTO> vouObj, final long sessUserID) throws AppException {
        return voucherDaEnDAOImpl.updateVoucherDaEnDetail(vouObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ItemsInVouDaEnDTO> getVouDaEnItemWithBillEnBeDates(final long poNum) throws AppException {
        return voucherDaEnDAOImpl.getVouDaEnItemWithBillEnBeDates(poNum);
    }
}
