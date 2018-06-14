/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.CurrencyDAOImpl;
import com.pdms.master.dao.impl.PaymentDAOImpl;
import com.pdms.master.dto.CurrencyDTO;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
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
public class CurrencyServiceImpl {
    
    private static final Logger logger = Logger.getLogger(CurrencyServiceImpl.class);
    
    @Autowired
    private CurrencyDAOImpl currencyDAOImpl;
    
    public CurrencyServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertCurrencyDetail(final List<CurrencyDTO> curObj, final long sessUserID) throws AppException {
        long dupCont = 0;
        for (CurrencyDTO cdObj : curObj) {
            dupCont = this.checkDuplicateCurrency("0", cdObj.getCurrencyName());
            if (dupCont == 0) {
                dupCont = this.checkDuplicateCurrency(cdObj.getCurrencyCode(), "0");
            }
            if (dupCont == 0) {
                dupCont = this.checkDuplicateCurrency(cdObj.getCurrencyCode(), cdObj.getCurrencyName());
            }
        }
        if (dupCont > 0) {
            return -1;
        } else {
            return currencyDAOImpl.insertCurrencyDetail(curObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<CurrencyDTO> getCurrencyDetails() throws AppException {

        return currencyDAOImpl.getCurrencyDetails();

    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CurrencyDTO> getCurrencyReById(final long id) throws AppException
    {
        
            return currencyDAOImpl.getCurrencyReById(id);
        
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateCurrencyDetail(final List<CurrencyDTO> curObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(CurrencyDTO Obj: curObj){
            
            CurrencyDTO existObject = this.getSelectedCurrency(Obj.getCurrencyId());
            if(!(existObject.getCurrencyCode().equalsIgnoreCase(Obj.getCurrencyCode())) &&
                    !(existObject.getCurrencyName().equalsIgnoreCase(Obj.getCurrencyName()))){
                dupCount = this.checkDuplicateCurrency(Obj.getCurrencyCode(), Obj.getCurrencyName());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicateCurrency(Obj.getCurrencyCode(), "0");                    
                }
                if(dupCount == 0){
                    dupCount = this.checkDuplicateCurrency("0", Obj.getCurrencyName());                   
                }
                
                
            }else if(!(existObject.getCurrencyCode().equalsIgnoreCase(Obj.getCurrencyCode()))){
                
                dupCount = this.checkDuplicateCurrency(Obj.getCurrencyCode(), "0");
                
            }else if(!(existObject.getCurrencyName().equalsIgnoreCase(Obj.getCurrencyName()))){
                
                dupCount = this.checkDuplicateCurrency("0", Obj.getCurrencyName());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return currencyDAOImpl.updateCurrencyDetail(curObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateCurrency(final String gCode, final String gName) throws AppException {
        return currencyDAOImpl.checkDuplicateCurrency(gCode, gName);
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public CurrencyDTO getSelectedCurrency(final long currencyID) throws AppException {
        return currencyDAOImpl.getSelectedCurrency(currencyID);
    }
}
