/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.HeadOfAccountDAOImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class HeadOfAccountServiceImpl {
    
    
    private static final Logger logger = Logger.getLogger(HeadOfAccountServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private HeadOfAccountDAOImpl hodDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public HeadOfAccountServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HeadOfAccountDTO> getAllHeadOfAccountDetails() throws AppException
    {
        return hodDAO.getAllHeadOfAccountDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public HeadOfAccountDTO getSelectedHeadOfAccountDetail(final String encHodID) throws AppException {
        long hodID = 0;
        if(!StringUtils.isEmpty(encHodID))
        {
            String decHodID = encryptDecrypt.decrypt(encHodID);
            if(NumberUtils.isNumber(decHodID))
            {
                hodID = Long.parseLong(decHodID);
            }
        }
        
        return hodDAO.getSelectedHeadOfAccountDetail(hodID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertHeadOfAccountDetail(final HeadOfAccountDTO hodObj, final long sessUserID)
            throws AppException {
        
        long dupCnt = this.checkDuplicateHOA(hodObj.getAccountType(),hodObj.getAccountCode());
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return hodDAO.insertHeadOfAccountDetail(hodObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateHeadOfAccountDetail(final HeadOfAccountDTO hodObj, final long sessUserID) 
            throws AppException {
        
        long hodID = 0;
        if(!StringUtils.isEmpty(hodObj.getEncFieldValue()))
        {
            String decHodID = encryptDecrypt.decrypt(hodObj.getEncFieldValue());
            if(NumberUtils.isNumber(decHodID))
            {
                hodID = Long.parseLong(decHodID);
            }
        }
        hodObj.setHeadOfAccountID(hodID);
        
        HeadOfAccountDTO exisHoaObj = this.getSelectedHeadOfAccountDetail(hodObj.getEncFieldValue());
        
        long dupCnt =0;
        if((!exisHoaObj.getAccountType().equalsIgnoreCase(hodObj.getAccountType()))
            &&(!exisHoaObj.getAccountCode().equalsIgnoreCase(hodObj.getAccountCode())))
        {
            dupCnt = this.checkDuplicateHOA(hodObj.getAccountType(),hodObj.getAccountCode());
        }
        else if((!exisHoaObj.getAccountCode().equalsIgnoreCase(hodObj.getAccountCode())))
        {
            dupCnt = this.checkDuplicateHOA("0",hodObj.getDescription());
        }
        else if((!exisHoaObj.getAccountType().equalsIgnoreCase(hodObj.getAccountType())))
        {
            dupCnt = this.checkDuplicateHOA(hodObj.getAccountType(),"0");
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return hodDAO.updateHeadOfAccountDetail(hodObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateHOA(final String accountType, final String acctDesc) throws AppException
    {
        return hodDAO.checkDuplicateHOA(accountType,acctDesc);
    }
    
}
