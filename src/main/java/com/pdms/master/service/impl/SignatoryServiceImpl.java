/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.CurrencyDAOImpl;
import com.pdms.master.dao.impl.SignatoryDAOImpl;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.master.dto.SignatoryDTO;
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
public class SignatoryServiceImpl {
    
    private static final Logger logger = Logger.getLogger(SignatoryServiceImpl.class);
    
    @Autowired
    private SignatoryDAOImpl signatoryDAOImpl;
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertSignatoryDetail(final List<SignatoryDTO> sinObj, final long sessUserID) throws AppException {
        long dupCont = 0;
        for (SignatoryDTO cdObj : sinObj) {
            dupCont = this.checkDuplicateSignatory("0", cdObj.getSignatoryName());            
            if (dupCont == 0) {
                dupCont = this.checkDuplicateSignatory(cdObj.getSignatoryCode(), "0");                
            }
            if (dupCont == 0) {
                dupCont = this.checkDuplicateSignatory(cdObj.getSignatoryCode(), cdObj.getSignatoryName());                
            }
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return signatoryDAOImpl.insertSignatoryDetail(sinObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateSignatory(final String gCode, final String gName) throws AppException {
        return signatoryDAOImpl.checkDuplicateSignatory(gCode, gName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
     public List<SignatoryDTO> getSignatoryDetails() throws AppException {

        return signatoryDAOImpl.getSignatoryDetails();

    }
     
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
     public List<SignatoryDTO> getSignatoryReById(final long id) throws AppException {

        return signatoryDAOImpl.getSignatoryReById(id);

    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public SignatoryDTO getSelectedSignatory(final long signatoryID) throws AppException {

        return signatoryDAOImpl.getSelectedSignatory(signatoryID);

    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateSignatoryDetail(final List<SignatoryDTO> singObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(SignatoryDTO Obj: singObj){            
            SignatoryDTO existObject = this.getSelectedSignatory(Obj.getSignatoryId());
            if(!(existObject.getSignatoryCode().equalsIgnoreCase(Obj.getSignatoryCode())) &&
                    !(existObject.getSignatoryName().equalsIgnoreCase(Obj.getSignatoryName()))){
                dupCount = this.checkDuplicateSignatory(Obj.getSignatoryCode(), Obj.getSignatoryName());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicateSignatory(Obj.getSignatoryCode(), "0");                    
                }
                if(dupCount == 0){
                    dupCount = this.checkDuplicateSignatory("0", Obj.getSignatoryName());                   
                }
                
                
            }else if(!(existObject.getSignatoryCode().equalsIgnoreCase(Obj.getSignatoryCode()))){
                
                dupCount = this.checkDuplicateSignatory(Obj.getSignatoryCode(), "0");
                
            }else if(!(existObject.getSignatoryName().equalsIgnoreCase(Obj.getSignatoryName()))){
                
                dupCount = this.checkDuplicateSignatory("0", Obj.getSignatoryName());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return signatoryDAOImpl.updateSignatoryDetail(singObj, sessUserID);
        } 
    }
}
