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
public class NilVoucherDTO implements Serializable{
    
    private long nilVouId;
    private long pprNumber;
    private long posr;
    private long poNumber;
    private BigDecimal amountPaid;
    private String dateOfPmt;
    private long billNumber;
    private String billDate;
    private long rvNumber;
    private String rvDate;
    private BigDecimal amtAdmitted;
    private BigDecimal balanceDue;

    public long getNilVouId() {
        return nilVouId;
    }

    public long getPprNumber() {
        return pprNumber;
    }

    public long getPosr() {
        return posr;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public String getDateOfPmt() {
        return dateOfPmt;
    }

    public long getBillNumber() {
        return billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public long getRvNumber() {
        return rvNumber;
    }

    public String getRvDate() {
        return rvDate;
    }

    public void setNilVouId(long nilVouId) {
        this.nilVouId = nilVouId;
    }

    public void setPprNumber(long pprNumber) {
        this.pprNumber = pprNumber;
    }

    public void setPosr(long posr) {
        this.posr = posr;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setDateOfPmt(String dateOfPmt) {
        this.dateOfPmt = dateOfPmt;
    }

    public void setBillNumber(long billNumber) {
        this.billNumber = billNumber;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setRvNumber(long rvNumber) {
        this.rvNumber = rvNumber;
    }

    public void setRvDate(String rvDate) {
        this.rvDate = rvDate;
    }

    public BigDecimal getAmtAdmitted() {
        return amtAdmitted;
    }

    public BigDecimal getBalanceDue() {
        return balanceDue;
    }

    public void setAmtAdmitted(BigDecimal amtAdmitted) {
        this.amtAdmitted = amtAdmitted;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }
    
    
}
