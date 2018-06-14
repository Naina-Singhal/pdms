/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class InwardDTO implements Serializable{
    private long inwardId;
    private long slno;
    private long fileNumber;
    private String type;
    private String receiveDate;

    public long getInwardId() {
        return inwardId;
    }

    public long getSlno() {
        return slno;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public String getType() {
        return type;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setInwardId(long inwardId) {
        this.inwardId = inwardId;
    }

    public void setSlno(long slno) {
        this.slno = slno;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }
    
    
    
    
}
