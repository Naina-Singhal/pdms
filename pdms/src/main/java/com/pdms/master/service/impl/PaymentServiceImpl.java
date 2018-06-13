/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PaymentDAOImpl;
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
public class PaymentServiceImpl {
    
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class);
    
    @Autowired
    private PaymentDAOImpl paymentDAOImpl;
    
    public PaymentServiceImpl(){
        
    }
    
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPaymentDetail(final List<PaymentDTO> payObj, final long sessUserID) throws AppException
    {
         long dupCont = 0;
        for (PaymentDTO pyObj : payObj) {
            dupCont = this.checkDuplicatePayment("0", pyObj.getPaymentName());            
            if (dupCont == 0) {
                dupCont = this.checkDuplicatePayment(pyObj.getPaymentCode(), "0");                
            }
            if (dupCont == 0) {
                dupCont = this.checkDuplicatePayment(pyObj.getPaymentCode(), pyObj.getPaymentName());                
            }
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return paymentDAOImpl.insertPaymentDetail(payObj, sessUserID);
        }
            
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PaymentDTO> getPaymentDetails() throws AppException
    {
        
            return paymentDAOImpl.getPaymentDetails();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PaymentDTO> getPaymentReById(final long id) throws AppException
    {
        
            return paymentDAOImpl.getPaymentReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicatePayment(final String gCode, final String gName) throws AppException
    {
        
            return paymentDAOImpl.checkDuplicatePayment(gCode, gName);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public PaymentDTO getSelectedPayment(final long paymentId) throws AppException
    {
        
            return paymentDAOImpl.getSelectedPayment(paymentId);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePaymentDetail(final List<PaymentDTO> payObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(PaymentDTO Obj: payObj){            
            PaymentDTO existObject = this.getSelectedPayment(Obj.getPaymentID());
            if(!(existObject.getPaymentCode().equalsIgnoreCase(Obj.getPaymentCode())) &&
                    !(existObject.getPaymentName().equalsIgnoreCase(Obj.getPaymentName()))){
                dupCount = this.checkDuplicatePayment(Obj.getPaymentCode(), Obj.getPaymentName());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicatePayment(Obj.getPaymentCode(), "0");                    
                }
                if(dupCount == 0){
                    dupCount = this.checkDuplicatePayment("0", Obj.getPaymentName());                   
                }
                
                
            }else if(!(existObject.getPaymentCode().equalsIgnoreCase(Obj.getPaymentCode()))){
                
                dupCount = this.checkDuplicatePayment(Obj.getPaymentCode(), "0");
                
            }else if(!(existObject.getPaymentName().equalsIgnoreCase(Obj.getPaymentName()))){
                
                dupCount = this.checkDuplicatePayment("0", Obj.getPaymentName());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return paymentDAOImpl.updatePaymentDetail(payObj, sessUserID);
        } 
    }
}
