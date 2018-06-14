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
 * @author naagu
 */
public class DdNumberDTO implements Serializable{
    private long ddNumberId;
    private long reqNumber;
    private String vendorname;
    private long vouNumber;
    private String voucherDate;
    private BigDecimal amount;
    private long ddNumber;
    private String ddDate;

    public long getDdNumberId() {
        return ddNumberId;
    }

    public long getReqNumber() {
        return reqNumber;
    }

    public String getVendorname() {
        return vendorname;
    }

    public long getVouNumber() {
        return vouNumber;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getDdNumber() {
        return ddNumber;
    }

    public String getDdDate() {
        return ddDate;
    }

    public void setDdNumberId(long ddNumberId) {
        this.ddNumberId = ddNumberId;
    }

    public void setReqNumber(long reqNumber) {
        this.reqNumber = reqNumber;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public void setVouNumber(long vouNumber) {
        this.vouNumber = vouNumber;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDdNumber(long ddNumber) {
        this.ddNumber = ddNumber;
    }

    public void setDdDate(String ddDate) {
        this.ddDate = ddDate;
    }
    
    
    
}
