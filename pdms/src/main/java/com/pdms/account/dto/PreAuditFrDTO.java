/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class PreAuditFrDTO implements Serializable{
    private long preAuditId;
    private long fileNo;
    private String fileType;
    private String receivedOn;
    private String purpose;

    public long getPreAuditId() {
        return preAuditId;
    }

    public long getFileNo() {
        return fileNo;
    }

    public String getFileType() {
        return fileType;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPreAuditId(long preAuditId) {
        this.preAuditId = preAuditId;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    
}
