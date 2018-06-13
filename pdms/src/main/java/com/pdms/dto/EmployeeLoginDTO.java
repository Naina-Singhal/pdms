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
public class EmployeeLoginDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -5659877687806090207L;
    
    private long employeeID;
    private String encryptedEmployeeID;
    private String insertFlag;
    private String username;
    private String password;
    private String currentPassword;
    private long lockedFlag;
    private long passwordFlag;
    private long loginAttempts;
    private String newPassword;
    private String confPassword;
    private String sessionKey;
    private String sessionToken;
    private EmployeeProfileDTO employeeProfileDTO = new EmployeeProfileDTO();
    private String validationMessage;
    private SectionDTO sectionDTO = new SectionDTO();
    private DesignationDTO designationDTO = new DesignationDTO();
    private GroupDTO groupDTO= new GroupDTO(); 

    /**
     * @return the employeeID
     */
    public long getEmployeeID() {
        return employeeID;
    }

    /**
     * @param employeeID the employeeID to set
     */
    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    /**
     * @return the encryptedEmployeeID
     */
    public String getEncryptedEmployeeID() {
        return encryptedEmployeeID;
    }

    /**
     * @param encryptedEmployeeID the encryptedEmployeeID to set
     */
    public void setEncryptedEmployeeID(String encryptedEmployeeID) {
        this.encryptedEmployeeID = encryptedEmployeeID;
    }

    /**
     * @return the insertFlag
     */
    public String getInsertFlag() {
        return insertFlag;
    }

    /**
     * @param insertFlag the insertFlag to set
     */
    public void setInsertFlag(String insertFlag) {
        this.insertFlag = insertFlag;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the currentPassword
     */
    public String getCurrentPassword() {
        return currentPassword;
    }

    /**
     * @param currentPassword the currentPassword to set
     */
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    /**
     * @return the lockedFlag
     */
    public long getLockedFlag() {
        return lockedFlag;
    }

    /**
     * @param lockedFlag the lockedFlag to set
     */
    public void setLockedFlag(long lockedFlag) {
        this.lockedFlag = lockedFlag;
    }

    /**
     * @return the passwordFlag
     */
    public long getPasswordFlag() {
        return passwordFlag;
    }

    /**
     * @param passwordFlag the passwordFlag to set
     */
    public void setPasswordFlag(long passwordFlag) {
        this.passwordFlag = passwordFlag;
    }

    /**
     * @return the loginAttempts
     */
    public long getLoginAttempts() {
        return loginAttempts;
    }

    /**
     * @param loginAttempts the loginAttempts to set
     */
    public void setLoginAttempts(long loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the confPassword
     */
    public String getConfPassword() {
        return confPassword;
    }

    /**
     * @param confPassword the confPassword to set
     */
    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    /**
     * @return the sessionKey
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * @param sessionKey the sessionKey to set
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * @return the employeeProfileDTO
     */
    public EmployeeProfileDTO getEmployeeProfileDTO() {
        return employeeProfileDTO;
    }

    /**
     * @param employeeProfileDTO the employeeProfileDTO to set
     */
    public void setEmployeeProfileDTO(EmployeeProfileDTO employeeProfileDTO) {
        this.employeeProfileDTO = employeeProfileDTO;
    }

    /**
     * @return the validationMessage
     */
    public String getValidationMessage() {
        return validationMessage;
    }

    /**
     * @param validationMessage the validationMessage to set
     */
    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    /**
     * @return the sessionToken
     */
    public String getSessionToken() {
        return sessionToken;
    }

    /**
     * @param sessionToken the sessionToken to set
     */
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    /**
     * @return the sectionDTO
     */
    public SectionDTO getSectionDTO() {
        return sectionDTO;
    }

    /**
     * @param sectionDTO the sectionDTO to set
     */
    public void setSectionDTO(SectionDTO sectionDTO) {
        this.sectionDTO = sectionDTO;
    }

    /**
     * @return the designationDTO
     */
    public DesignationDTO getDesignationDTO() {
        return designationDTO;
    }

    /**
     * @param designationDTO the designationDTO to set
     */
    public void setDesignationDTO(DesignationDTO designationDTO) {
        this.designationDTO = designationDTO;
    }

    /**
     * @return the groupDTO
     */
    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    /**
     * @param groupDTO the groupDTO to set
     */
    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    
        
}
