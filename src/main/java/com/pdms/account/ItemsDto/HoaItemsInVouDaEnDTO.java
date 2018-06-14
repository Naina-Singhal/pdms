/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author myassessment
 */
public class HoaItemsInVouDaEnDTO implements Serializable{
    private long hoaItemsInVouDaEnId;
    private long fileNumber;
    private long poNumBer;
    private String hoaName;
    private String shortCode;
    private BigDecimal cbcbcb;
    private BigDecimal debitamt;
    private BigDecimal creditAmt;

    public long getHoaItemsInVouDaEnId() {
        return hoaItemsInVouDaEnId;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public String getHoaName() {
        return hoaName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public BigDecimal getCbcbcb() {
        return cbcbcb;
    }

    public BigDecimal getDebitamt() {
        return debitamt;
    }

    public BigDecimal getCreditAmt() {
        return creditAmt;
    }

    public void setHoaItemsInVouDaEnId(long hoaItemsInVouDaEnId) {
        this.hoaItemsInVouDaEnId = hoaItemsInVouDaEnId;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setHoaName(String hoaName) {
        this.hoaName = hoaName;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setCbcbcb(BigDecimal cbcbcb) {
        this.cbcbcb = cbcbcb;
    }

    public void setDebitamt(BigDecimal debitamt) {
        this.debitamt = debitamt;
    }

    public void setCreditAmt(BigDecimal creditAmt) {
        this.creditAmt = creditAmt;
    }
    
    
    
    
}
