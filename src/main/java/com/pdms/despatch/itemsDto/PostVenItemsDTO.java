/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.itemsDto;

import com.pdms.despatch.dto.PostEntryDTO;
import com.pdms.dto.VendorDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Phanivaranasi
 */
public class PostVenItemsDTO implements Serializable{
    
    private long postVenItemId;
    private long dispatchno;
    private long filenumber;
    private long itemid;
    private long vendorid;
    private BigDecimal amount;
    private PostEntryDTO postObj = new PostEntryDTO();
    private VendorDTO vendorObj = new VendorDTO();

    public long getPostVenItemId() {
        return postVenItemId;
    }

    public long getFilenumber() {
        return filenumber;
    }

    public long getItemid() {
        return itemid;
    }

    public long getVendorid() {
        return vendorid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPostVenItemId(long postVenItemId) {
        this.postVenItemId = postVenItemId;
    }

    public void setFilenumber(long filenumber) {
        this.filenumber = filenumber;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public void setVendorid(long vendorid) {
        this.vendorid = vendorid;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getDispatchno() {
        return dispatchno;
    }

    public void setDispatchno(long dispatchno) {
        this.dispatchno = dispatchno;
    }

    public PostEntryDTO getPostObj() {
        return postObj;
    }

    public VendorDTO getVendorObj() {
        return vendorObj;
    }

    public void setPostObj(PostEntryDTO postObj) {
        this.postObj = postObj;
    }

    public void setVendorObj(VendorDTO vendorObj) {
        this.vendorObj = vendorObj;
    }
    
    
    
}
