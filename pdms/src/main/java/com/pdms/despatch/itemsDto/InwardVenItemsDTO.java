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
public class InwardVenItemsDTO implements Serializable{
    
    private long venItemId;
    private long filenumber;
    private long itemid;
    private long vendorid;

    public long getVenItemId() {
        return venItemId;
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

    public void setVenItemId(long venItemId) {
        this.venItemId = venItemId;
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
    
    
    
}
