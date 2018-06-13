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
public class POrderEntryDTO extends BaseDTO implements Serializable{
    
    private long pOrderEntryID;
    private String poReleaseType;
    private long poNumber;    
    private String date;
    private String poDate;
    private String scheDelDate;    
    private String indentRefNo;    
    private String indentor;
    private String section;
    private String supplier;
    private String itemsCovered;
    private String remarks;
    private String poValue;
    private String poValPaid;    
    private String currencyType;
    private String currencyCode;    
    private String headOfAccount;
    private String placeOfDel;
    private String paymentTerms;
    private String payingAuthority;

    public long getpOrderEntryID() {
        return pOrderEntryID;
    }

    public long getPoNumber() {
        return poNumber;
    }

    
    public String getDate() {
        return date;
    }

    public String getPoDate() {
        return poDate;
    }

    public String getScheDelDate() {
        return scheDelDate;
    }

   

    public String getIndentRefNo() {
        return indentRefNo;
    }

    

    
    public String getIndentor() {
        return indentor;
    }

    public String getSection() {
        return section;
    }

    
    public String getItemsCovered() {
        return itemsCovered;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getPoValue() {
        return poValue;
    }

    public String getPoValPaid() {
        return poValPaid;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }


    public String getHeadOfAccount() {
        return headOfAccount;
    }

    public String getPlaceOfDel() {
        return placeOfDel;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public String getPayingAuthority() {
        return payingAuthority;
    }

    public void setpOrderEntryID(long pOrderEntryID) {
        this.pOrderEntryID = pOrderEntryID;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

   

    public void setDate(String date) {
        this.date = date;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public void setScheDelDate(String scheDelDate) {
        this.scheDelDate = scheDelDate;
    }

    

    public void setIndentRefNo(String indentRefNo) {
        this.indentRefNo = indentRefNo;
    }

    

    public void setIndentor(String indentor) {
        this.indentor = indentor;
    }

    public void setSection(String section) {
        this.section = section;
    }

    
    public void setItemsCovered(String itemsCovered) {
        this.itemsCovered = itemsCovered;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setPoValue(String poValue) {
        this.poValue = poValue;
    }

    public void setPoValPaid(String poValPaid) {
        this.poValPaid = poValPaid;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }


    public void setHeadOfAccount(String headOfAccount) {
        this.headOfAccount = headOfAccount;
    }

    public void setPlaceOfDel(String placeOfDel) {
        this.placeOfDel = placeOfDel;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public void setPayingAuthority(String payingAuthority) {
        this.payingAuthority = payingAuthority;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPoReleaseType() {
        return poReleaseType;
    }

    public void setPoReleaseType(String poReleaseType) {
        this.poReleaseType = poReleaseType;
    }
          
    
    
    
}
