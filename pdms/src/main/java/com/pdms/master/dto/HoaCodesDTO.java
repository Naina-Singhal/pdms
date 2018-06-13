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
public class HoaCodesDTO extends BaseDTO implements Serializable{
    
    private long hoaCodeId;
    private String accountName;
    private String shortCode;
    private String description;

    public long getHoaCodeId() {
        return hoaCodeId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getDescription() {
        return description;
    }

    public void setHoaCodeId(long hoaCodeId) {
        this.hoaCodeId = hoaCodeId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
