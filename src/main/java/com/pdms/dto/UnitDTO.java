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
public class UnitDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private long unitID;
    private String unitCode;
    private String unitName;
    private String unitDescription;

    /**
     * @return the unitID
     */
    public long getUnitID() {
        return unitID;
    }

    /**
     * @param unitID the unitID to set
     */
    public void setUnitID(long unitID) {
        this.unitID = unitID;
    }

    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }

    /**
     * @param unitCode the unitCode to set
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    /**
     * @return the unitDescription
     */
    public String getUnitDescription() {
        return unitDescription;
    }

    /**
     * @param unitDescription the unitDescription to set
     */
    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    
}
