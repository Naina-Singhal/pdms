/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class EnquiryDTO implements Serializable{
    private long enquiryId;
    private long fileNo;
    private String vendorName;
    private String enquireDate;
    private String dueDate;
    private String prepareDate;
    private String openDate;
    private String ifscCode;

    public void setEnquiryId(long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setEnquireDate(String enquireDate) {
        this.enquireDate = enquireDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setPrepareDate(String prepareDate) {
        this.prepareDate = prepareDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public long getEnquiryId() {
        return enquiryId;
    }

    public long getFileNo() {
        return fileNo;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getEnquireDate() {
        return enquireDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPrepareDate() {
        return prepareDate;
    }

    public String getOpenDate() {
        return openDate;
    }

    public String getIfscCode() {
        return ifscCode;
    }
    
    
}
