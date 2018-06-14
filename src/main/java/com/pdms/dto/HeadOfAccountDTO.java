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
public class HeadOfAccountDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 7869580028716066716L;
    
    private long headOfAccountID;
    private String accountType;
    private String accountCode;
    private String description;

    /**
     * @return the headOfAccountID
     */
    public long getHeadOfAccountID() {
        return headOfAccountID;
    }

    /**
     * @param headOfAccountID the headOfAccountID to set
     */
    public void setHeadOfAccountID(long headOfAccountID) {
        this.headOfAccountID = headOfAccountID;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
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
     * @return the accountCode
     */
    public String getAccountCode() {
        return accountCode;
    }

    /**
     * @param accountCode the accountCode to set
     */
    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
    
    
}
