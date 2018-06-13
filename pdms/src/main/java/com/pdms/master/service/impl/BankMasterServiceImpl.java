/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.BankMasterDAOImpl;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dto.BankMasterDTO;
import com.pdms.master.dto.RtgsDTO;
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
public class BankMasterServiceImpl {
    private static final Logger logger = Logger.getLogger(BankMasterServiceImpl.class);
    
    @Autowired
    private BankMasterDAOImpl bankMasterDAOImpl;
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertBankDetail(final List<BankMasterDTO> bankObj, final long sessUserID) throws AppException {
        long dupCont = 0;
        for (BankMasterDTO cdObj : bankObj) {
            
            dupCont = this.checkDuplicateBank(cdObj.getBankCode());
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return bankMasterDAOImpl.insertBankDetail(bankObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateBank(final String gCode) throws AppException {
        return bankMasterDAOImpl.checkDuplicateBank(gCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankMasterDTO> getBankMasterDetails() throws AppException {
        return bankMasterDAOImpl.getBankMasterDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<BankMasterDTO> getBankReById(final long id) throws AppException {
        return bankMasterDAOImpl.getBankReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateBankDetail(final List<BankMasterDTO> bankObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(BankMasterDTO Obj: bankObj){            
            BankMasterDTO existObject = this.getSelectedBank(Obj.getBankId());
            if(!(existObject.getBankCode().equalsIgnoreCase(Obj.getBankCode())) ){
                dupCount = this.checkDuplicateBank(Obj.getBankCode());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicateBank(Obj.getBankCode());                    
                }                
            }else if(!(existObject.getBankCode().equalsIgnoreCase(Obj.getBankCode()))){
                
                dupCount = this.checkDuplicateBank(Obj.getBankCode());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return bankMasterDAOImpl.updateBankDetail(bankObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public BankMasterDTO getSelectedBank(final long bankId) throws AppException {
        return bankMasterDAOImpl.getSelectedBank(bankId);
    }
    
}
