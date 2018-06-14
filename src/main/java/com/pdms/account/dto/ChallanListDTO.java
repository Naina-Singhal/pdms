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
public class ChallanListDTO implements Serializable{
    
    private long challanListId;
    private long challanNum;
    private String challanDate;
    private BigDecimal challanAmt;
    private String chaRecevDate;

    public long getChallanListId() {
        return challanListId;
    }

    public long getChallanNum() {
        return challanNum;
    }

    public String getChallanDate() {
        return challanDate;
    }

    public BigDecimal getChallanAmt() {
        return challanAmt;
    }

    public String getChaRecevDate() {
        return chaRecevDate;
    }

    public void setChallanListId(long challanListId) {
        this.challanListId = challanListId;
    }

    public void setChallanNum(long challanNum) {
        this.challanNum = challanNum;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public void setChallanAmt(BigDecimal challanAmt) {
        this.challanAmt = challanAmt;
    }

    public void setChaRecevDate(String chaRecevDate) {
        this.chaRecevDate = chaRecevDate;
    }
    
    
    
}
