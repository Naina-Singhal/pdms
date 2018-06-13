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
public class TaxDTO implements Serializable{
    private long taxId;
    private String taxCode;
    private String taxname;
    private String description;

    public long getTaxId() {
        return taxId;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public String getTaxname() {
        return taxname;
    }

    public String getDescription() {
        return description;
    }

    public void setTaxId(long taxId) {
        this.taxId = taxId;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public void setTaxname(String taxname) {
        this.taxname = taxname;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
