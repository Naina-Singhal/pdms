/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class IssueVoucherDTO extends BaseDTO implements Serializable{
    
    private long id;
    private String ivControlNo;
    private String ivControlDate;
    private String rcivNumber;
    private String rcivDate;
    private String issueType;
    private String section;
    private String disposal;
    private String deliveryAt;
    private String itemCovered;
    private String joAllocation;
    private String storeGroupCode;
    private String cardNumber;
    private String cardCode;
    private String upToDateBal;
    private String describe;
    private String partNumber;
    private String minLevel;
    private String mlReachedDat;
    private String date;
    private String qtyLaIssue;
    private String basicRate;
    private String rateDate;
    private String waRate;
    private String rate;
    private String currencyCode;
    private String receivedBy;
    private String surplusQty;
    private String lifoRate;    
    private String indentorName;    
    private String issuedByName;    
    private String authorByName;
    private String authorByDate;

    public long getId() {
        return id;
    }

    public String getIvControlNo() {
        return ivControlNo;
    }

    public String getIvControlDate() {
        return ivControlDate;
    }

    public String getRcivNumber() {
        return rcivNumber;
    }

    public String getRcivDate() {
        return rcivDate;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getSection() {
        return section;
    }

    public String getDisposal() {
        return disposal;
    }

    public String getDeliveryAt() {
        return deliveryAt;
    }

    public String getItemCovered() {
        return itemCovered;
    }

    public String getJoAllocation() {
        return joAllocation;
    }

    public String getStoreGroupCode() {
        return storeGroupCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardCode() {
        return cardCode;
    }

    public String getUpToDateBal() {
        return upToDateBal;
    }

    public String getDescribe() {
        return describe;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getMinLevel() {
        return minLevel;
    }

    public String getMlReachedDat() {
        return mlReachedDat;
    }

    public String getDate() {
        return date;
    }

    public String getQtyLaIssue() {
        return qtyLaIssue;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public String getRateDate() {
        return rateDate;
    }

    public String getWaRate() {
        return waRate;
    }

    public String getRate() {
        return rate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public String getSurplusQty() {
        return surplusQty;
    }

    public String getLifoRate() {
        return lifoRate;
    }


    public String getIndentorName() {
        return indentorName;
    }


    public String getIssuedByName() {
        return issuedByName;
    }


    public String getAuthorByName() {
        return authorByName;
    }

    public String getAuthorByDate() {
        return authorByDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIvControlNo(String ivControlNo) {
        this.ivControlNo = ivControlNo;
    }

    public void setIvControlDate(String ivControlDate) {
        this.ivControlDate = ivControlDate;
    }

    public void setRcivNumber(String rcivNumber) {
        this.rcivNumber = rcivNumber;
    }

    public void setRcivDate(String rcivDate) {
        this.rcivDate = rcivDate;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setDisposal(String disposal) {
        this.disposal = disposal;
    }

    public void setDeliveryAt(String deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public void setItemCovered(String itemCovered) {
        this.itemCovered = itemCovered;
    }

    public void setJoAllocation(String joAllocation) {
        this.joAllocation = joAllocation;
    }

    public void setStoreGroupCode(String storeGroupCode) {
        this.storeGroupCode = storeGroupCode;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public void setUpToDateBal(String upToDateBal) {
        this.upToDateBal = upToDateBal;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void setMinLevel(String minLevel) {
        this.minLevel = minLevel;
    }

    public void setMlReachedDat(String mlReachedDat) {
        this.mlReachedDat = mlReachedDat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQtyLaIssue(String qtyLaIssue) {
        this.qtyLaIssue = qtyLaIssue;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public void setWaRate(String waRate) {
        this.waRate = waRate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    public void setSurplusQty(String surplusQty) {
        this.surplusQty = surplusQty;
    }

    public void setLifoRate(String lifoRate) {
        this.lifoRate = lifoRate;
    }


    public void setIndentorName(String indentorName) {
        this.indentorName = indentorName;
    }


    public void setIssuedByName(String issuedByName) {
        this.issuedByName = issuedByName;
    }


    public void setAuthorByName(String authorByName) {
        this.authorByName = authorByName;
    }

    public void setAuthorByDate(String authorByDate) {
        this.authorByDate = authorByDate;
    }
    
    
}
