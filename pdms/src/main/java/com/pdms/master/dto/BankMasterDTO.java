/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class BankMasterDTO implements Serializable{
    
    private long bankId;
    private String bankCode;
    private String bankName;
    private String branch;
    private String ifscCode;
    private String city;

    public long getBankId() {
        return bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBranch() {
        return branch;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getCity() {
        return city;
    }

    public void setBankId(long bankId) {
        this.bankId = bankId;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
    
}
