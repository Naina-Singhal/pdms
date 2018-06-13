/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author myassessment
 */
public class IbcNumberDTO extends BaseDTO implements Serializable{
    
    private long ibcID;
    private long poNumber;
    private String nameOfSupplr;
    private String billNumber;
    private String billDate;
    private BigDecimal billAmount;
    private long ibcNumber;
    private String ibcDate;
    private BigDecimal ibcAmount;
    private String ibcBank;

    public long getIbcID() {
        return ibcID;
    }

    public Long getPoNumber() {
        return poNumber;
    }

    public String getNameOfSupplr() {
        return nameOfSupplr;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public Long getIbcNumber() {
        return ibcNumber;
    }

    public String getIbcDate() {
        return ibcDate;
    }

    public BigDecimal getIbcAmount() {
        return ibcAmount;
    }

    public String getIbcBank() {
        return ibcBank;
    }

    public void setIbcID(long ibcID) {
        this.ibcID = ibcID;
    }

    public void setPoNumber(Long poNumber) {
        this.poNumber = poNumber;
    }

    public void setNameOfSupplr(String nameOfSupplr) {
        this.nameOfSupplr = nameOfSupplr;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public void setBillAmount(BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public void setIbcNumber(long ibcNumber) {
        this.ibcNumber = ibcNumber;
    }

    public void setIbcDate(String ibcDate) {
        this.ibcDate = ibcDate;
    }

    public void setIbcAmount(BigDecimal ibcAmount) {
        this.ibcAmount = ibcAmount;
    }

    public void setIbcBank(String ibcBank) {
        this.ibcBank = ibcBank;
    }

    
}
