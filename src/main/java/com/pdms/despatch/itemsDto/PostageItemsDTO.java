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
public class PostageItemsDTO implements Serializable{
    
    private long postageItemId;
    private long dispatchno;
    private String nature;
    private long vendorid;
    private String tods;
    private BigDecimal amount;
    
    private VendorDTO venObj = new VendorDTO();
    private PostEntryDTO postObj = new PostEntryDTO();

    public long getPostageItemId() {
        return postageItemId;
    }

    public long getDispatchno() {
        return dispatchno;
    }

    public String getNature() {
        return nature;
    }

    public long getVendorid() {
        return vendorid;
    }

    public String getTods() {
        return tods;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setPostageItemId(long postageItemId) {
        this.postageItemId = postageItemId;
    }

    public void setDispatchno(long dispatchno) {
        this.dispatchno = dispatchno;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setVendorid(long vendorid) {
        this.vendorid = vendorid;
    }

    public void setTods(String tods) {
        this.tods = tods;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

    public PostEntryDTO getPostObj() {
        return postObj;
    }

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }

    public void setPostObj(PostEntryDTO postObj) {
        this.postObj = postObj;
    }
    
    
    
}
