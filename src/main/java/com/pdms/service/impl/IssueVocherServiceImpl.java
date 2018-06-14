/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.IssueVocherDAOImpl;
import com.pdms.dao.impl.MaterialReceiptDAOImpl;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.IssueVoucherTempDTO;
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
public class IssueVocherServiceImpl {
    
    private static final Logger logger = Logger.getLogger(IssueVocherServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private IssueVocherDAOImpl issueVocherDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public IssueVocherServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertIssueVoucher(final IssueVoucherTempDTO issueVoucher, final long sessUserID) throws AppException
    {
        
            return issueVocherDAOImpl.insertIssueVoucher(issueVoucher, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IssueVoucherDTO> getIssueVoucherRecord() throws AppException
    {
        
            return issueVocherDAOImpl.getIssueVoucherRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IssueVoucherDTO> getIssueVoucherReById(final long id) throws AppException
    {
        
            return issueVocherDAOImpl.getIssueVoucherReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateIssueVoucherDetail(final List<IssueVoucherDTO> issueVoucher, final long sessUserID) throws AppException
    {
        
            return issueVocherDAOImpl.updateIssueVoucherDetail(issueVoucher, sessUserID);
        
    }
}
