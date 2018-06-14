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
public class DescriptionDTO extends BaseDTO implements Serializable{
    
    private long descriptionID;
    private String description;

    public long getDescriptionID() {
        return descriptionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescriptionID(long descriptionID) {
        this.descriptionID = descriptionID;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
