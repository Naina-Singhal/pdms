/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import com.pdms.dto.BaseDTO;
import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class RtgsDTO extends BaseDTO implements Serializable{
    
    private long rtgsId;
    private String vendorCode;
    private long rtgsNo;
    private String accountNo;
    private String ifscCode;
    private String branch;
    private String city;

    public long getRtgsId() {
        return rtgsId;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public Long getRtgsNo() {
        return rtgsNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public String getCity() {
        return city;
    }

    public void setRtgsId(long rtgsId) {
        this.rtgsId = rtgsId;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setRtgsNo(Long rtgsNo) {
        this.rtgsNo = rtgsNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
    
}
