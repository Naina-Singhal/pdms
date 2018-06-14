/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import java.io.Serializable;

/**
 *
 * @author Phanivaranasi
 */
public class RvVouDaEnItemsDTO implements Serializable{
    
    private long rvVouDaEnItems;
    private long fileNumber;
    private long poNumBer;
    private long rvnumber;
    private String rvdate;

    public long getRvVouDaEnItems() {
        return rvVouDaEnItems;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public long getRvnumber() {
        return rvnumber;
    }

    public String getRvdate() {
        return rvdate;
    }

    public void setRvVouDaEnItems(long rvVouDaEnItems) {
        this.rvVouDaEnItems = rvVouDaEnItems;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setRvnumber(long rvnumber) {
        this.rvnumber = rvnumber;
    }

    public void setRvdate(String rvdate) {
        this.rvdate = rvdate;
    }
    
    
    
}
