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
public class ChequeDdEntryDTO implements Serializable{
    private long chequeDdId;
    private long receiptNo;
    private String date;
    private String supplierName;
    private long challanNo;
    private String challanDate;    
    private String month;
    private String challanYear;    
    private String headOfAc;
    private String scode;
    private String head;
    private String tag;
    private String ddDate;
    private String subHead;
    private long fileNo;
    private String balDocument;   
    private BigDecimal balance;
    private String ddOrChequeNo;    
    private BigDecimal amount;
    private String letterDt;
    private String details; 
    private long slNo;

    public long getChequeDdId() {
        return chequeDdId;
    }

    public long getReceiptNo() {
        return receiptNo;
    }

    public String getDate() {
        return date;
    }

    public String getSupplierName() {
        return supplierName;
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

    

    public String getScode() {
        return scode;
    }

    public String getHead() {
        return head;
    }

    public String getTag() {
        return tag;
    }

    public String getDdDate() {
        return ddDate;
    }

    public String getSubHead() {
        return subHead;
    }

    public long getFileNo() {
        return fileNo;
    }

    public String getBalDocument() {
        return balDocument;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getDdOrChequeNo() {
        return ddOrChequeNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getLetterDt() {
        return letterDt;
    }

    public String getDetails() {
        return details;
    }

    public void setChequeDdId(long chequeDdId) {
        this.chequeDdId = chequeDdId;
    }

    public void setReceiptNo(long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

   

    public void setScode(String scode) {
        this.scode = scode;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDdDate(String ddDate) {
        this.ddDate = ddDate;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public void setBalDocument(String balDocument) {
        this.balDocument = balDocument;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setDdOrChequeNo(String ddOrChequeNo) {
        this.ddOrChequeNo = ddOrChequeNo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setLetterDt(String letterDt) {
        this.letterDt = letterDt;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public long getSlNo() {
        return slNo;
    }

    public void setSlNo(long slNo) {
        this.slNo = slNo;
    }

    public String getHeadOfAc() {
        return headOfAc;
    }

    public void setHeadOfAc(String headOfAc) {
        this.headOfAc = headOfAc;
    }

    
    
}
