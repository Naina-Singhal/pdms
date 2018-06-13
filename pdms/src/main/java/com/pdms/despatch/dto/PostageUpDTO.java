/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class PostageUpDTO implements Serializable{
    
    private long postageId;
    private String toDayDate;
    private BigDecimal totalAmt;
    private BigDecimal otBalance;
    private BigDecimal ctBalance;
    private String postage;

    public long getPostageId() {
        return postageId;
    }

    public String getToDayDate() {
        return toDayDate;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public BigDecimal getOtBalance() {
        return otBalance;
    }

    public BigDecimal getCtBalance() {
        return ctBalance;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostageId(long postageId) {
        this.postageId = postageId;
    }

    public void setToDayDate(String toDayDate) {
        this.toDayDate = toDayDate;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public void setOtBalance(BigDecimal otBalance) {
        this.otBalance = otBalance;
    }

    public void setCtBalance(BigDecimal ctBalance) {
        this.ctBalance = ctBalance;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }
    
    
    
    
}
