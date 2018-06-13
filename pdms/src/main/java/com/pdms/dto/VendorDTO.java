/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public class VendorDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 6651249759253262433L;
    
    private long vendorID;
    private String encVendorID;
    private String vendorCode;
    private String vendorName;
    private String contactPerson;
    private String vendorAddress;
    private String vendorCity;
    private String vendorPin;
    private String vendorPhone;
    private String vendorFax;
    private String vendorEmail;
    private String registrationType;
    private String expiraryDate;
    private String vendorPan;
    private String vendorRating;
    private String source;
    private String comments;
    private String gstinNumber;
    private List<VendorItemsDTO> vendorItemsLi = new ArrayList<>();
    private long sentCount;
    private long recordedCount;
    private long orderedCount;
    private long indentID;
    private long itemID;
    private long cstID;
    private String encCstID;
    private long fileNo;

    private IndentFormDTO indentFormObj = new IndentFormDTO();
    
    /**
     * @return the vendorID
     */
    public long getVendorID() {
        return vendorID;
    }

    /**
     * @param vendorID the vendorID to set
     */
    public void setVendorID(long vendorID) {
        this.vendorID = vendorID;
    }

    /**
     * @return the encVendorID
     */
    public String getEncVendorID() {
        return encVendorID;
    }

    /**
     * @param encVendorID the encVendorID to set
     */
    public void setEncVendorID(String encVendorID) {
        this.encVendorID = encVendorID;
    }

    /**
     * @return the vendorCode
     */
    public String getVendorCode() {
        return vendorCode;
    }

    /**
     * @param vendorCode the vendorCode to set
     */
    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    /**
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the vendorAddress
     */
    public String getVendorAddress() {
        return vendorAddress;
    }

    /**
     * @param vendorAddress the vendorAddress to set
     */
    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    /**
     * @return the vendorCity
     */
    public String getVendorCity() {
        return vendorCity;
    }

    /**
     * @param vendorCity the vendorCity to set
     */
    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    /**
     * @return the vendorPin
     */
    public String getVendorPin() {
        return vendorPin;
    }

    /**
     * @param vendorPin the vendorPin to set
     */
    public void setVendorPin(String vendorPin) {
        this.vendorPin = vendorPin;
    }

    /**
     * @return the vendorPhone
     */
    public String getVendorPhone() {
        return vendorPhone;
    }

    /**
     * @param vendorPhone the vendorPhone to set
     */
    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    /**
     * @return the vendorFax
     */
    public String getVendorFax() {
        return vendorFax;
    }

    /**
     * @param vendorFax the vendorFax to set
     */
    public void setVendorFax(String vendorFax) {
        this.vendorFax = vendorFax;
    }

    /**
     * @return the vendorEmail
     */
    public String getVendorEmail() {
        return vendorEmail;
    }

    /**
     * @param vendorEmail the vendorEmail to set
     */
    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    /**
     * @return the registrationType
     */
    public String getRegistrationType() {
        return registrationType;
    }

    /**
     * @param registrationType the registrationType to set
     */
    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    /**
     * @return the expiraryDate
     */
    public String getExpiraryDate() {
        return expiraryDate;
    }

    /**
     * @param expiraryDate the expiraryDate to set
     */
    public void setExpiraryDate(String expiraryDate) {
        this.expiraryDate = expiraryDate;
    }

    /**
     * @return the vendorPan
     */
    public String getVendorPan() {
        return vendorPan;
    }

    /**
     * @param vendorPan the vendorPan to set
     */
    public void setVendorPan(String vendorPan) {
        this.vendorPan = vendorPan;
    }

    /**
     * @return the vendorRating
     */
    public String getVendorRating() {
        return vendorRating;
    }

    /**
     * @param vendorRating the vendorRating to set
     */
    public void setVendorRating(String vendorRating) {
        this.vendorRating = vendorRating;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the vendorItemsLi
     */
    public List<VendorItemsDTO> getVendorItemsLi() {
        return vendorItemsLi;
    }

    /**
     * @param vendorItemsLi the vendorItemsLi to set
     */
    public void setVendorItemsLi(List<VendorItemsDTO> vendorItemsLi) {
        this.vendorItemsLi = vendorItemsLi;
    }

    /**
     * @return the sentCount
     */
    public long getSentCount() {
        return sentCount;
    }

    /**
     * @param sentCount the sentCount to set
     */
    public void setSentCount(long sentCount) {
        this.sentCount = sentCount;
    }

    /**
     * @return the recordedCount
     */
    public long getRecordedCount() {
        return recordedCount;
    }

    /**
     * @param recordedCount the recordedCount to set
     */
    public void setRecordedCount(long recordedCount) {
        this.recordedCount = recordedCount;
    }

    /**
     * @return the orderedCount
     */
    public long getOrderedCount() {
        return orderedCount;
    }

    /**
     * @param orderedCount the orderedCount to set
     */
    public void setOrderedCount(long orderedCount) {
        this.orderedCount = orderedCount;
    }

    /**
     * @return the indentID
     */
    public long getIndentID() {
        return indentID;
    }

    /**
     * @param indentID the indentID to set
     */
    public void setIndentID(long indentID) {
        this.indentID = indentID;
    }

    /**
     * @return the itemID
     */
    public long getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the indentFormObj
     */
    public IndentFormDTO getIndentFormObj() {
        return indentFormObj;
    }

    /**
     * @param indentFormObj the indentFormObj to set
     */
    public void setIndentFormObj(IndentFormDTO indentFormObj) {
        this.indentFormObj = indentFormObj;
    }

    /**
     * @return the cstID
     */
    public long getCstID() {
        return cstID;
    }

    /**
     * @param cstID the cstID to set
     */
    public void setCstID(long cstID) {
        this.cstID = cstID;
    }

    /**
     * @return the encCstID
     */
    public String getEncCstID() {
        return encCstID;
    }

    /**
     * @param encCstID the encCstID to set
     */
    public void setEncCstID(String encCstID) {
        this.encCstID = encCstID;
    }

    public String getGstinNumber() {
        return gstinNumber;
    }

    public void setGstinNumber(String gstinNumber) {
        this.gstinNumber = gstinNumber;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public long getFileNo() {
        return fileNo;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }
    
    
}
