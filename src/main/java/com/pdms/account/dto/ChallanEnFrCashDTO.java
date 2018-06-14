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
public class ChallanEnFrCashDTO implements Serializable{
    private long challanId;
    private long challanNo;
    private String challanDate;
    private String month;
    private String challanYear;
    private BigDecimal challanAmt;
    private String headOfAc;
    private String scode;
    private String detailedHead;
    private String subHead;
    private String desOfHead;
    private String details;
    private BigDecimal total;

    public long getChallanId() {
        return challanId;
    }

    public long getChallanNo() {
        return challanNo;
    }

    public String getChallanDate() {
        return challanDate;
    }

    public String getMonth() {
        return month;
    }

    public String getChallanYear() {
        return challanYear;
    }

    public BigDecimal getChallanAmt() {
        return challanAmt;
    }

    public String getScode() {
        return scode;
    }

    public String getDetailedHead() {
        return detailedHead;
    }

    public String getSubHead() {
        return subHead;
    }

    public String getDesOfHead() {
        return desOfHead;
    }

    public String getDetails() {
        return details;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setChallanId(long challanId) {
        this.challanId = challanId;
    }

    public void setChallanNo(long challanNo) {
        this.challanNo = challanNo;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setChallanYear(String challanYear) {
        this.challanYear = challanYear;
    }

    public void setChallanAmt(BigDecimal challanAmt) {
        this.challanAmt = challanAmt;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public void setDetailedHead(String detailedHead) {
        this.detailedHead = detailedHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public void setDesOfHead(String desOfHead) {
        this.desOfHead = desOfHead;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getHeadOfAc() {
        return headOfAc;
    }

    public void setHeadOfAc(String headOfAc) {
        this.headOfAc = headOfAc;
    }
    
    
}
