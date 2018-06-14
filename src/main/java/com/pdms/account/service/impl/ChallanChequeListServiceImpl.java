/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.ChallanChequeListDAOImpl;
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.ChallanListDTO;
import com.pdms.account.dto.ChequeListDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
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
public class ChallanChequeListServiceImpl {
    
    private static final Logger logger = Logger.getLogger(ChallanChequeListServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private ChallanChequeListDAOImpl chequeListDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public ChallanChequeListServiceImpl(){
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<ChallanEnFrCashDTO> getOnlyChallnRecord(String fromDate, String toDate) throws AppException {
        
        return chequeListDAOImpl.getOnlyChallnRecord(fromDate, toDate);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<VoucherNoDTO> getOnlyChequeReFrVouNo(String fromDate, String toDate) throws AppException {
        
        return chequeListDAOImpl.getOnlyChequeReFrVouNo(fromDate, toDate);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertChallanList(final List<ChallanListDTO> chaObj, final long sessUserID) throws AppException {
        
        return chequeListDAOImpl.insertChallanList(chaObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertChequeList(final List<ChequeListDTO> cheObj, final long sessUserID) throws AppException {
        
        return chequeListDAOImpl.insertChequeList(cheObj, sessUserID);
                
    }
    
    
}
