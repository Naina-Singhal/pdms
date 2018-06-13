/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import com.pdms.account.dto.BillEntryDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author myassessment
 */
public class ItemsInVouDaEnDTO implements Serializable{
    private long itemsInVouDaEnId;
    private long fileNumber;
    private long poNumBer;
    private BigDecimal rate;
    private BigDecimal igst;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private String hsnCode;
    private String gbys;
    private String qtyReceived;
    private BillEntryDTO billObj = new BillEntryDTO();

    public long getItemsInVouDaEnId() {
        return itemsInVouDaEnId;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public String getGbys() {
        return gbys;
    }

    public String getQtyReceived() {
        return qtyReceived;
    }

    public void setItemsInVouDaEnId(long itemsInVouDaEnId) {
        this.itemsInVouDaEnId = itemsInVouDaEnId;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public void setGbys(String gbys) {
        this.gbys = gbys;
    }

    public void setQtyReceived(String qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public BillEntryDTO getBillObj() {
        return billObj;
    }

    public void setBillObj(BillEntryDTO billObj) {
        this.billObj = billObj;
    }

    
    
}
