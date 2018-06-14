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
public class EmployeeTypeDTO extends  BaseDTO implements Serializable{

    private static final long serialVersionUID = -1800196901387610780L;
    
    private long employeeTypeID;
    private String employeeTypeName;
    private String employeeTypeCode;
    private String description;

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

    /**
     * @return the employeeTypeName
     */
    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    /**
     * @param employeeTypeName the employeeTypeName to set
     */
    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
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
     * @return the employeeTypeCode
     */
    public String getEmployeeTypeCode() {
        return employeeTypeCode;
    }

    /**
     * @param employeeTypeCode the employeeTypeCode to set
     */
    public void setEmployeeTypeCode(String employeeTypeCode) {
        this.employeeTypeCode = employeeTypeCode;
    }
    
    
    
}
