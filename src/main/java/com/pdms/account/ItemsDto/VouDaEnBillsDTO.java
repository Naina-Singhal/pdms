/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class VouDaEnBillsDTO implements Serializable{
    
    private long vouDaEnBillsId;
    private long fileNumber;
    private long poNumBer;
    private long billNUM;
    private String billDATE;

    public long getVouDaEnBillsId() {
        return vouDaEnBillsId;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public long getBillNUM() {
        return billNUM;
    }

    public String getBillDATE() {
        return billDATE;
    }

    public void setVouDaEnBillsId(long vouDaEnBillsId) {
        this.vouDaEnBillsId = vouDaEnBillsId;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setBillNUM(long billNUM) {
        this.billNUM = billNUM;
    }

    public void setBillDATE(String billDATE) {
        this.billDATE = billDATE;
    }

    
    
}
