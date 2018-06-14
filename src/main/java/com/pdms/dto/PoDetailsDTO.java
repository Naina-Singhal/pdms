/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class PoDetailsDTO implements Serializable{
    private long poDetailsId;
    private long poNumInPo;
    private long fileNo;
    private String desCode;
    private String noteDes;

    public long getPoDetailsId() {
        return poDetailsId;
    }

    public long getPoNumInPo() {
        return poNumInPo;
    }

    public long getFileNo() {
        return fileNo;
    }

    public String getDesCode() {
        return desCode;
    }

    public String getNoteDes() {
        return noteDes;
    }

    public void setPoDetailsId(long poDetailsId) {
        this.poDetailsId = poDetailsId;
    }

    public void setPoNumInPo(long poNumInPo) {
        this.poNumInPo = poNumInPo;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public void setDesCode(String desCode) {
        this.desCode = desCode;
    }

    public void setNoteDes(String noteDes) {
        this.noteDes = noteDes;
    }
    
    
    
    
}
