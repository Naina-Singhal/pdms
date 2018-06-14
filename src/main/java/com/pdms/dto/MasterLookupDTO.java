/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */

public class MasterLookupDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -7217877669597692540L;
    
    private long lookUpID;
    private String encLookUpID;
    private String lookUpName;
    private String lookUpValue;
    private String description;

    /**
     * @return the lookUpID
     */
    public long getLookUpID() {
        return lookUpID;
    }

    /**
     * @param lookUpID the lookUpID to set
     */
    public void setLookUpID(long lookUpID) {
        this.lookUpID = lookUpID;
    }

    /**
     * @return the encLookUpID
     */
    public String getEncLookUpID() {
        return encLookUpID;
    }

    /**
     * @param encLookUpID the encLookUpID to set
     */
    public void setEncLookUpID(String encLookUpID) {
        this.encLookUpID = encLookUpID;
    }

    /**
     * @return the lookUpName
     */
    public String getLookUpName() {
        return lookUpName;
    }

    /**
     * @param lookUpName the lookUpName to set
     */
    public void setLookUpName(String lookUpName) {
        this.lookUpName = lookUpName;
    }

    /**
     * @return the lookUpValue
     */
    public String getLookUpValue() {
        return lookUpValue;
    }

    /**
     * @param lookUpValue the lookUpValue to set
     */
    public void setLookUpValue(String lookUpValue) {
        this.lookUpValue = lookUpValue;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
