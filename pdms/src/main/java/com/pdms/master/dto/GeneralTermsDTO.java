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
public class GeneralTermsDTO implements Serializable{
    private long descriptionId;
    private String description;

    public long getDescriptionId() {
        return descriptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptionId(long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
