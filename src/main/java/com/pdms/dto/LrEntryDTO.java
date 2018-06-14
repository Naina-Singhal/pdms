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
public class LrEntryDTO extends BaseDTO implements Serializable{
    
    private long lrEntryID;
    private String poNumber;
    private String nameOfSupplr;
    private String billNumber;
    private String billDate;
    private BigDecimal billAmount;
    private String ibcNumber;
    private String lrNumber;
    private String lrDate;
    private String nameTransporter;
    
    private VendorDTO venObj  = new VendorDTO();

    public long getLrEntryID() {
        return lrEntryID;
    }

    public String getPoNumber() {
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

    public String getIbcNumber() {
        return ibcNumber;
    }

    public String getLrNumber() {
        return lrNumber;
    }

    public String getLrDate() {
        return lrDate;
    }

    public String getNameTransporter() {
        return nameTransporter;
    }

    public void setLrEntryID(long lrEntryID) {
        this.lrEntryID = lrEntryID;
    }

    public void setPoNumber(String poNumber) {
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

    public void setIbcNumber(String ibcNumber) {
        this.ibcNumber = ibcNumber;
    }

    public void setLrNumber(String lrNumber) {
        this.lrNumber = lrNumber;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

    public void setNameTransporter(String nameTransporter) {
        this.nameTransporter = nameTransporter;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }
    
    
}
