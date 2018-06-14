/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.NilVoucherDAOImpl;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.NilVoucherDTO;
import com.pdms.account.dto.VoucherDTO;
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
public class NilVoucherServiceImpl {
     private static final Logger logger = Logger.getLogger(NilVoucherServiceImpl.class);
    
    @Autowired
    private NilVoucherDAOImpl daoImpl;
    
    /*
    Default Constructor 
     */
    public NilVoucherServiceImpl(){
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertNilVoucherDaEntry(final List<NilVoucherDTO> nilObj, final long sessUserID) throws AppException {
        return daoImpl.insertNilVoucherDaEntry(nilObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<NilVoucherDTO> getNilVoucherEnRecord() throws AppException {
        return daoImpl.getNilVoucherEnRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<NilVoucherDTO> getNilVoucherReById(final long id) throws AppException {
        return daoImpl.getNilVoucherReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateNilVoucherDetail(final List<NilVoucherDTO> nilObj, final long sessUserID) throws AppException {
        return daoImpl.updateNilVoucherDetail(nilObj, sessUserID);
    }
}
