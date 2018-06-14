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
public class PlaceOfDeliveryDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -3241940753727889240L;
    
    private long placeOfDeliveryID;
    private String placeOfDelivery;
    private String description;

    /**
     * @return the placeOfDeliveryID
     */
    public long getPlaceOfDeliveryID() {
        return placeOfDeliveryID;
    }

    /**
     * @param placeOfDeliveryID the placeOfDeliveryID to set
     */
    public void setPlaceOfDeliveryID(long placeOfDeliveryID) {
        this.placeOfDeliveryID = placeOfDeliveryID;
    }

    /**
     * @return the placeOfDelivery
     */
    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    /**
     * @param placeOfDelivery the placeOfDelivery to set
     */
    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
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
