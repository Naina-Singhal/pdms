/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.AdvancePayDAOImpl;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.AdvancePayDTO;
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
public class AdvancePayServiceImpl {
     private static final Logger logger = Logger.getLogger(VoucherDaEnServiceImpl.class);
    
    @Autowired
    private AdvancePayDAOImpl daoImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertAdvancePayDaEntry(final List<AdvancePayDTO> advaObj, final long sessUserID) throws AppException {
        return daoImpl.insertAdvancePayDaEntry(advaObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<AdvancePayDTO> getAdvancePayDaRecord() throws AppException {
        return daoImpl.getAdvancePayDaRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<AdvancePayDTO> getAdvancePayReById(final long id) throws AppException {
        return daoImpl.getAdvancePayReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateAdvancePayDetail(final List<AdvancePayDTO> advaObj, final long sessUserID) throws AppException {
        return daoImpl.updateAdvancePayDetail(advaObj, sessUserID);
    }
}
