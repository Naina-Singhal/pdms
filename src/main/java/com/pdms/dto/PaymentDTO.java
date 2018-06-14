/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class PaymentDTO extends BaseDTO implements Serializable{
    
    private long paymentID;
    private String paymentCode;
    private String paymentName;
    private String paymentDes;

    public long getPaymentID() {
        return paymentID;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public String getPaymentDes() {
        return paymentDes;
    }

    public void setPaymentID(long paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public void setPaymentDes(String paymentDes) {
        this.paymentDes = paymentDes;
    }
    
    
    
    
}
