/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class DispatchEntryDTO implements Serializable {

    private long dispatchEnId;
    private long fileNumber;
    private String purpose;
    private String reference;
    private String type;
    private String receiver;
    private String remarks;    
    private String reasonFrSend;
    private String sendDate;

    public long getDispatchEnId() {
        return dispatchEnId;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getReference() {
        return reference;
    }

    public String getType() {
        return type;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getRemarks() {
        return remarks;
    }


    public String getReasonFrSend() {
        return reasonFrSend;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setDispatchEnId(long dispatchEnId) {
        this.dispatchEnId = dispatchEnId;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

  

    public void setReasonFrSend(String reasonFrSend) {
        this.reasonFrSend = reasonFrSend;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

}
