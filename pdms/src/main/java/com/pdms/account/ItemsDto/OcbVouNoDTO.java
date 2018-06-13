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
 * @author Phanivaranasi
 */
public class OcbVouNoDTO implements Serializable{
    
    private long ocbId;
    private long compCode;
    private long poNumBer;
    private String month;
    private BigDecimal openbalance;
    private BigDecimal closebalance;
    private String dateofentry;

    public long getOcbId() {
        return ocbId;
    }

    public long getCompCode() {
        return compCode;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public String getMonth() {
        return month;
    }

    public BigDecimal getOpenbalance() {
        return openbalance;
    }

    public BigDecimal getClosebalance() {
        return closebalance;
    }

    public String getDateofentry() {
        return dateofentry;
    }

    public void setOcbId(long ocbId) {
        this.ocbId = ocbId;
    }

    public void setCompCode(long compCode) {
        this.compCode = compCode;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setOpenbalance(BigDecimal openbalance) {
        this.openbalance = openbalance;
    }

    public void setClosebalance(BigDecimal closebalance) {
        this.closebalance = closebalance;
    }

    public void setDateofentry(String dateofentry) {
        this.dateofentry = dateofentry;
    }
    
    
    
}
