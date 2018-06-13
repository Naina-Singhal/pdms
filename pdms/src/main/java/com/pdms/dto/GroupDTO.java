/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public class GroupDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 4988555746752514982L;
    
    private long groupID;
    private String groupCode;
    private String groupName;
    private String description;
    private List<Long> empList;
    private long employeeID;
    private String firstName;
    private String lastName;

    /**
     * @return the groupID
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * @param groupID the groupID to set
     */
    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    /**
     * @return the groupCode
     */
    public String getGroupCode() {
        return groupCode;
    }

    /**
     * @param groupCode the groupCode to set
     */
    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
     * @return the empList
     */
    public List<Long> getEmpList() {
        return empList;
    }

    /**
     * @param empList the empList to set
     */
    public void setEmpList(List<Long> empList) {
        this.empList = empList;
    }

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
    
    
    
}
