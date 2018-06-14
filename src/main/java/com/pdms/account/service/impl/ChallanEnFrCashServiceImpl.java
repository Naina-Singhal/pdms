/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.ChallanEnFrCashDAOImpl;
import com.pdms.account.dto.ChallanEnFrCashDTO;
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
public class ChallanEnFrCashServiceImpl {
    private static final Logger logger = Logger.getLogger(ChallanEnFrCashServiceImpl.class);
    
    @Autowired
    private ChallanEnFrCashDAOImpl cashDAOImpl;
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertChallanEntryCash(final List<ChallanEnFrCashDTO> chaObj, final long sessUserID) throws AppException {
        return cashDAOImpl.insertChallanEntryCash(chaObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ChallanEnFrCashDTO> getChallanEnFrCashDaRecord() throws AppException {
        return cashDAOImpl.getChallanEnFrCashDaRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getChalNoEnFrCashForIncrement() throws AppException {
        return cashDAOImpl.getChalNoEnFrCashForIncrement();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ChallanEnFrCashDTO> getChallanEnCashReById(final long id) throws AppException {
        return cashDAOImpl.getChallanEnCashReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateChallanEnFrCashDetail(final List<ChallanEnFrCashDTO> chaObj, final long sessUserID) throws AppException {
        return cashDAOImpl.updateChallanEnFrCashDetail(chaObj, sessUserID);
    }
    
}
