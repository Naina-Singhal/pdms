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
public class ChequeListDTO implements Serializable{
    
    private long chequeListId;
    private String chequeNo;
    private String chequeDate;
    private BigDecimal chequeAmount;
    private String chequeRecevDate;

    public long getChequeListId() {
        return chequeListId;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public BigDecimal getChequeAmount() {
        return chequeAmount;
    }

    public String getChequeRecevDate() {
        return chequeRecevDate;
    }

    public void setChequeListId(long chequeListId) {
        this.chequeListId = chequeListId;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public void setChequeAmount(BigDecimal chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public void setChequeRecevDate(String chequeRecevDate) {
        this.chequeRecevDate = chequeRecevDate;
    }
    
    
}
