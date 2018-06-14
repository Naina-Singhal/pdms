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
public class DesignationDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -5469794693562870324L;
    
    private long designationID;
    private String designationName;
    private String designationCode;
    private String description;
    private long isSigningAuthority;
    private long isApprovingAuthority;
    private long employeeTypeID;

    /**
     * @return the designationID
     */
    public long getDesignationID() {
        return designationID;
    }

    /**
     * @param designationID the designationID to set
     */
    public void setDesignationID(long designationID) {
        this.designationID = designationID;
    }

    /**
     * @return the designationName
     */
    public String getDesignationName() {
        return designationName;
    }

    /**
     * @param designationName the designationName to set
     */
    public void setDesignationName(String designationName) {
        this.designationName = designationName;
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
     * @return the designationCode
     */
    public String getDesignationCode() {
        return designationCode;
    }

    /**
     * @param designationCode the designationCode to set
     */
    public void setDesignationCode(String designationCode) {
        this.designationCode = designationCode;
    }

    /**
     * @return the isSigningAuthority
     */
    public long getIsSigningAuthority() {
        return isSigningAuthority;
    }

    /**
     * @param isSigningAuthority the isSigningAuthority to set
     */
    public void setIsSigningAuthority(long isSigningAuthority) {
        this.isSigningAuthority = isSigningAuthority;
    }

    /**
     * @return the isApprovingAuthority
     */
    public long getIsApprovingAuthority() {
        return isApprovingAuthority;
    }

    /**
     * @param isApprovingAuthority the isApprovingAuthority to set
     */
    public void setIsApprovingAuthority(long isApprovingAuthority) {
        this.isApprovingAuthority = isApprovingAuthority;
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
