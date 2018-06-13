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
public class PostPoItemsDTO implements Serializable{
    
    private long postPoitemId;
    private long dispatchno;
    private long filenumber;
    private long ponumber;
    private String posrno;
    private String vendorcode;
    private String deliverydate;

    public long getPostPoitemId() {
        return postPoitemId;
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

    public String getDeliverydate() {
        return deliverydate;
    }

    public void setPostPoitemId(long postPoitemId) {
        this.postPoitemId = postPoitemId;
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

    public void setDeliverydate(String deliverydate) {
        this.deliverydate = deliverydate;
    }

    public long getDispatchno() {
        return dispatchno;
    }

    public void setDispatchno(long dispatchno) {
        this.dispatchno = dispatchno;
    }
    
    
    
    
}
