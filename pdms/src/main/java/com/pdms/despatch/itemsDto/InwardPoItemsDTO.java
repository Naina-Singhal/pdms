/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.itemsDto;

import java.io.Serializable;

/**
 *
 * @author Phanivaranasi
 */
public class InwardPoItemsDTO implements Serializable{
    
    private long poItemsId;
    private long filenumber;
    private long ponumber;
    private String posrno;
    private String vendorcode;

    public long getPoItemsId() {
        return poItemsId;
    }

    public long getFilenumber() {
        return filenumber;
    }

    public long getPonumber() {
        return ponumber;
    }

    public String getPosrno() {
        return posrno;
    }

    public String getVendorcode() {
        return vendorcode;
    }

    public void setPoItemsId(long poItemsId) {
        this.poItemsId = poItemsId;
    }

    public void setFilenumber(long filenumber) {
        this.filenumber = filenumber;
    }

    public void setPonumber(long ponumber) {
        this.ponumber = ponumber;
    }

    public void setPosrno(String posrno) {
        this.posrno = posrno;
    }

    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }
    
    
    
}
