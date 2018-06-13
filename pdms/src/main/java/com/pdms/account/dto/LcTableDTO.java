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
 * @author Phanivaranasi
 */
public class LcTableDTO implements Serializable{
    private long lcTableId;
    private String monthlc;
    private BigDecimal opbalance;
    private BigDecimal aamount;
    private BigDecimal total;
    private BigDecimal cmamount;
    private BigDecimal cbamount;
    private String dateoe;

    public long getLcTableId() {
        return lcTableId;
    }

    public String getMonthlc() {
        return monthlc;
    }

    public BigDecimal getOpbalance() {
        return opbalance;
    }

    public BigDecimal getAamount() {
        return aamount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getCmamount() {
        return cmamount;
    }

    public BigDecimal getCbamount() {
        return cbamount;
    }

    public String getDateoe() {
        return dateoe;
    }

    public void setLcTableId(long lcTableId) {
        this.lcTableId = lcTableId;
    }

    public void setMonthlc(String monthlc) {
        this.monthlc = monthlc;
    }

    public void setOpbalance(BigDecimal opbalance) {
        this.opbalance = opbalance;
    }

    public void setAamount(BigDecimal aamount) {
        this.aamount = aamount;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setCmamount(BigDecimal cmamount) {
        this.cmamount = cmamount;
    }

    public void setCbamount(BigDecimal cbamount) {
        this.cbamount = cbamount;
    }

    public void setDateoe(String dateoe) {
        this.dateoe = dateoe;
    }
    
    
}
