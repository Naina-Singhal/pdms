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
public class CurrencyDTO extends BaseDTO implements Serializable{
    private long currencyId;
    private String currencyName;
    private String currencyCode;
    private String currencyDes;

    public long getCurrencyId() {
        return currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyDes() {
        return currencyDes;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setCurrencyDes(String currencyDes) {
        this.currencyDes = currencyDes;
    }
    
    
    
}
