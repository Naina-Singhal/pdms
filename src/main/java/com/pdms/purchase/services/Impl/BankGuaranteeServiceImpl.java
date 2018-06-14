/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.services.Impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import com.pdms.purchase.dao.daoImpl.BankGuaranteeDAOImpl;
import com.pdms.purchase.dto.BankGuaranteeDTO;
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
public class BankGuaranteeServiceImpl {
    private static final Logger logger = Logger.getLogger(BankGuaranteeServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private BankGuaranteeDAOImpl guaranteeDAOimpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public BankGuaranteeServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertBankGuaranteeDetail(final List<BankGuaranteeDTO> guaObj, final long sessUserID) throws AppException
    {
       
            return guaranteeDAOimpl.insertBankGuaranteeDetail(guaObj, sessUserID);
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankGuaranteeDTO> getBankGuaranteeDeByPo(final long poNum) throws AppException
    {
       
            return guaranteeDAOimpl.getBankGuaranteeDeByPo(poNum);
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankGuaranteeDTO> getBankGuaranteeRecord() throws AppException
    {
       
            return guaranteeDAOimpl.getBankGuaranteeRecord();
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankGuaranteeDTO> getBankGuaranteeReById(final long id) throws AppException
    {
       
            return guaranteeDAOimpl.getBankGuaranteeReById(id);
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateBankGuaranteeDetail(final List<BankGuaranteeDTO> guaObj, final long sessUserID) throws AppException
    {
       
            return guaranteeDAOimpl.updateBankGuaranteeDetail(guaObj, sessUserID);
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long geneSlNoIncreFrBankGua() throws AppException
    {
       
            return guaranteeDAOimpl.geneSlNoIncreFrBankGua();
               
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankGuaranteeDTO> getBankGuaranteeReByIdPdf(final long id) throws AppException
    {
       
            return guaranteeDAOimpl.getBankGuaranteeReByIdPdf(id);
               
    }
}
