/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class BillEntryItemsDTO implements Serializable{
    private long billEnItemId;
    private long poNumBer;
    private String items;
    private BigDecimal rate;
    private long rcdQty;
    private String gbys;
    private long gstPer;
    private BigDecimal igst;
    private BigDecimal sgst;
    private BigDecimal cgst;
    private BigDecimal gst;
    private String hsnCode;

    public long getBillEnItemId() {
        return billEnItemId;
    }

    public String getItems() {
        return items;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public long getRcdQty() {
        return rcdQty;
    }

    public String getGbys() {
        return gbys;
    }

    public long getGstPer() {
        return gstPer;
    }

    public BigDecimal getIgst() {
        return igst;
    }

    public BigDecimal getSgst() {
        return sgst;
    }

    public BigDecimal getCgst() {
        return cgst;
    }

    public BigDecimal getGst() {
        return gst;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setBillEnItemId(long billEnItemId) {
        this.billEnItemId = billEnItemId;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setRcdQty(long rcdQty) {
        this.rcdQty = rcdQty;
    }

    public void setGbys(String gbys) {
        this.gbys = gbys;
    }

    public void setGstPer(long gstPer) {
        this.gstPer = gstPer;
    }

    public void setIgst(BigDecimal igst) {
        this.igst = igst;
    }

    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }

    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }

    public void setGst(BigDecimal gst) {
        this.gst = gst;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }
    
    
    
}
