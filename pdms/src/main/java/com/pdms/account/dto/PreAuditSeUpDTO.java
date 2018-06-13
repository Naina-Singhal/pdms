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
public class PreAuditSeUpDTO implements Serializable{
    private long preAudSeUpId;
    private String receivedOn;
    private String sendDate;
    private String purpose;
    private String nod;
    private long fileNo;

    public long getPreAudSeUpId() {
        return preAudSeUpId;
    }

    public String getReceivedOn() {
        return receivedOn;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getNod() {
        return nod;
    }

    public long getFileNo() {
        return fileNo;
    }

    public void setPreAudSeUpId(long preAudSeUpId) {
        this.preAudSeUpId = preAudSeUpId;
    }

    public void setReceivedOn(String receivedOn) {
        this.receivedOn = receivedOn;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setNod(String nod) {
        this.nod = nod;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }
    
    

}
