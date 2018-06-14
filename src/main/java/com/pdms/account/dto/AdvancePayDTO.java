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
public class AdvancePayDTO implements Serializable{
    
    private long avancePayId;
    private long pprNo;
    private long poNumber;
    private String vname;
    private String dop;
    private BigDecimal amountPaid;
    private String advanceAdj;
    private BigDecimal balancePaid;
    private BigDecimal balanceDue;
    private BigDecimal totalDue;
    private String clDate;    
    private String qtyPaid;
    private String recovery;
    private String remarks;
    private String posr;

    public long getAvancePayId() {
        return avancePayId;
    }

    public long getPprNo() {
        return pprNo;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getVname() {
        return vname;
    }

    public String getDop() {
        return dop;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public String getAdvanceAdj() {
        return advanceAdj;
    }

    public BigDecimal getBalancePaid() {
        return balancePaid;
    }

    public BigDecimal getBalanceDue() {
        return balanceDue;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public String getClDate() {
        return clDate;
    }

    public String getQtyPaid() {
        return qtyPaid;
    }

    public String getRecovery() {
        return recovery;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setAvancePayId(long avancePayId) {
        this.avancePayId = avancePayId;
    }

    public void setPprNo(long pprNo) {
        this.pprNo = pprNo;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setDop(String dop) {
        this.dop = dop;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setAdvanceAdj(String advanceAdj) {
        this.advanceAdj = advanceAdj;
    }

    public void setBalancePaid(BigDecimal balancePaid) {
        this.balancePaid = balancePaid;
    }

    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public void setClDate(String clDate) {
        this.clDate = clDate;
    }


    public void setQtyPaid(String qtyPaid) {
        this.qtyPaid = qtyPaid;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPosr() {
        return posr;
    }

    public void setPosr(String posr) {
        this.posr = posr;
    }
    
    
}
