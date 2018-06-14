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
public class SectionDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1425833954480757097L;

    
    private long sectionID;
    private String sectionName;
    private String sectionCode;
    private String description;
    private long employeeTypeID;
    

    /**
     * @return the sectionID
     */
    public long getSectionID() {
        return sectionID;
    }

    /**
     * @param sectionID the sectionID to set
     */
    public void setSectionID(long sectionID) {
        this.sectionID = sectionID;
    }

    /**
     * @return the sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName the sectionName to set
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return the sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * @param sectionCode the sectionCode to set
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
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

    /**
     * @return the employeeTypeID
     */
    public long getEmployeeTypeID() {
        return employeeTypeID;
    }

    /**
     * @param employeeTypeID the employeeTypeID to set
     */
    public void setEmployeeTypeID(long employeeTypeID) {
        this.employeeTypeID = employeeTypeID;
    }

    
    
}
