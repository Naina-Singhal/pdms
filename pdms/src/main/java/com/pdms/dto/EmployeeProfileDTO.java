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
public class EmployeeProfileDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 3963695112350882669L;

    private long employeeProfileID;
    private String encEmployeeProfileID;
    private String icNumber;
    private String employeeName;
    private String employeeEmail;
    private EmployeeTypeDTO employeeTypeDTO = new EmployeeTypeDTO();
    private MasterLookupDTO genderObj = new MasterLookupDTO();
    private String description;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String phone;
    private long sectionId;
    /**
     * @return the employeeProfileID
     */
    public long getEmployeeProfileID() {
        return employeeProfileID;
    }

    /**
     * @param employeeProfileID the employeeProfileID to set
     */
    public void setEmployeeProfileID(long employeeProfileID) {
        this.employeeProfileID = employeeProfileID;
    }

    /**
     * @return the encEmployeeProfileID
     */
    public String getEncEmployeeProfileID() {
        return encEmployeeProfileID;
    }

    /**
     * @param encEmployeeProfileID the encEmployeeProfileID to set
     */
    public void setEncEmployeeProfileID(String encEmployeeProfileID) {
        this.encEmployeeProfileID = encEmployeeProfileID;
    }

    /**
     * @return the icNumber
     */
    public String getIcNumber() {
        return icNumber;
    }

    /**
     * @param icNumber the icNumber to set
     */
    public void setIcNumber(String icNumber) {
        this.icNumber = icNumber;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * @return the employeeEmail
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * @param employeeEmail the employeeEmail to set
     */
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * @return the employeeTypeDTO
     */
    public EmployeeTypeDTO getEmployeeTypeDTO() {
        return employeeTypeDTO;
    }

    /**
     * @param employeeTypeDTO the employeeTypeDTO to set
     */
    public void setEmployeeTypeDTO(EmployeeTypeDTO employeeTypeDTO) {
        this.employeeTypeDTO = employeeTypeDTO;
    }

    /**
     * @return the genderObj
     */
    public MasterLookupDTO getGenderObj() {
        return genderObj;
    }

    /**
     * @param genderObj the genderObj to set
     */
    public void setGenderObj(MasterLookupDTO genderObj) {
        this.genderObj = genderObj;
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
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }
            
    
}
