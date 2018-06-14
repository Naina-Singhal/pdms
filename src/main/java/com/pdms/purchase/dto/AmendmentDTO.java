/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class AmendmentDTO implements Serializable{
    
    private long amendmentId;
    private long fileNumber;
    private long poNumber;
    private String vendorName;
    private String vendorAddress;
    private String BreifDescription;
    private String BgClause;
    private String reference;
    private String date;
    private String annexure;
    private String code;
    private String temp;
    private String bgNumber;
    private String bgDate;
    private BigDecimal amount;
    private String expireDate;
    private String forOne;
    private String deliveryPeriod;
    private String otherAmendments;

    public long getAmendmentId() {
        return amendmentId;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public String getBreifDescription() {
        return BreifDescription;
    }

    public String getBgClause() {
        return BgClause;
    }

    public String getReference() {
        return reference;
    }

    public String getDate() {
        return date;
    }

    public String getAnnexure() {
        return annexure;
    }

    public String getCode() {
        return code;
    }

    public String getTemp() {
        return temp;
    }

    public String getBgNumber() {
        return bgNumber;
    }

    public String getBgDate() {
        return bgDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getForOne() {
        return forOne;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public String getOtherAmendments() {
        return otherAmendments;
    }

    public void setAmendmentId(long amendmentId) {
        this.amendmentId = amendmentId;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public void setBreifDescription(String BreifDescription) {
        this.BreifDescription = BreifDescription;
    }

    public void setBgClause(String BgClause) {
        this.BgClause = BgClause;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAnnexure(String annexure) {
        this.annexure = annexure;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setBgNumber(String bgNumber) {
        this.bgNumber = bgNumber;
    }

    public void setBgDate(String bgDate) {
        this.bgDate = bgDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setForOne(String forOne) {
        this.forOne = forOne;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public void setOtherAmendments(String otherAmendments) {
        this.otherAmendments = otherAmendments;
    }
    
    
    
    
    
}
