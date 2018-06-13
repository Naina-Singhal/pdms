/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author myassessment
 */
public class BillEntryDTO implements Serializable{
    
    private long billId;
    private String poNumber;
    private String supplierName;
    private String paymentTerm;
    private long billNo;
    private String billDate;
    private String dcNo;
    private BigDecimal billAmt;
    private String lrEnclosed;
    private String gstinNo;
    private String billReceDate;

    public long getBillId() {
        return billId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public long getBillNo() {
        return billNo;
    }

    public String getBillDate() {
        return billDate;
    }

    public String getDcNo() {
        return dcNo;
    }

    public BigDecimal getBillAmt() {
        return billAmt;
    }

    public String getLrEnclosed() {
        return lrEnclosed;
    }

    public String getGstinNo() {
        return gstinNo;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public void setBillNo(long billNo) {
        this.billNo = billNo;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public void setBillAmt(BigDecimal billAmt) {
        this.billAmt = billAmt;
    }

    public void setLrEnclosed(String lrEnclosed) {
        this.lrEnclosed = lrEnclosed;
    }

    public void setGstinNo(String gstinNo) {
        this.gstinNo = gstinNo;
    }

    public String getBillReceDate() {
        return billReceDate;
    }

    public void setBillReceDate(String billReceDate) {
        this.billReceDate = billReceDate;
    }
    
    
}
