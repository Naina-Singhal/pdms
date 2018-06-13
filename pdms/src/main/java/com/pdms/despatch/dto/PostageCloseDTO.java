/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dto;

import java.io.Serializable;

/**
 *
 * @author Phanivaranasi
 */
public class PostageCloseDTO implements Serializable{
    
    private long closeId;
    private String toDayDate;
    private int status;

    public long getCloseId() {
        return closeId;
    }

   

    public int getStatus() {
        return status;
    }

    public void setCloseId(long closeId) {
        this.closeId = closeId;
    }

  
    public void setStatus(int status) {
        this.status = status;
    }

    public String getToDayDate() {
        return toDayDate;
    }

    public void setToDayDate(String toDayDate) {
        this.toDayDate = toDayDate;
    }
    
    
    
}
