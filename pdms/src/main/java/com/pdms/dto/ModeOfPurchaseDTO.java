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
public class ModeOfPurchaseDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -285581154914123394L;

    private long modeOfPurchaseID;
    private String modeOfPurchase;
    private String description;

    /**
     * @return the modeOfPurchaseID
     */
    public long getModeOfPurchaseID() {
        return modeOfPurchaseID;
    }

    /**
     * @param modeOfPurchaseID the modeOfPurchaseID to set
     */
    public void setModeOfPurchaseID(long modeOfPurchaseID) {
        this.modeOfPurchaseID = modeOfPurchaseID;
    }

    /**
     * @return the modeOfPurchase
     */
    public String getModeOfPurchase() {
        return modeOfPurchase;
    }

    /**
     * @param modeOfPurchase the modeOfPurchase to set
     */
    public void setModeOfPurchase(String modeOfPurchase) {
        this.modeOfPurchase = modeOfPurchase;
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
