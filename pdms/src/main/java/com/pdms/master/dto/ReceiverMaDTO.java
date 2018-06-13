/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class ReceiverMaDTO implements Serializable{
    private long receiverId;
    private String receiverCode;
    private String receiverName;

    public long getReceiverId() {
        return receiverId;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    
    
    
    
    
}
