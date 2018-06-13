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
 * @author myassessment
 */
public class OilDataDTO implements Serializable{
    
    private long oilDataId;
    private String posr;
    private long poNumber;
    private String supplierName;
    private String description;
    private long invoiceNo;
    private String invoiceDate;
    private BigDecimal invoiceAmt;
    private String invoiceQty;
    private BigDecimal rate;
    private String state;
    private BigDecimal scDiscount;
    private BigDecimal ed_rate;
    private BigDecimal edAmount;
    private BigDecimal edCess;
    private BigDecimal addDiscount;
    private BigDecimal freight;
    private BigDecimal fcAmount;
    private String cst;
    private BigDecimal cstAmt;
    private BigDecimal unitRate;
    private String qtyRecord;
    private BigDecimal totalAmt;
    private long pprNo;
    private BigDecimal amountPaid;
    private BigDecimal qtyReceivedRate;

    public long getOilDataId() {
        return oilDataId;
    }

    public String getPosr() {
        return posr;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getDescription() {
        return description;
    }

    public long getInvoiceNo() {
        return invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public BigDecimal getInvoiceAmt() {
        return invoiceAmt;
    }

    public String getInvoiceQty() {
        return invoiceQty;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public String getState() {
        return state;
    }

    public BigDecimal getScDiscount() {
        return scDiscount;
    }

    public BigDecimal getEd_rate() {
        return ed_rate;
    }

    public BigDecimal getEdAmount() {
        return edAmount;
    }

    public BigDecimal getEdCess() {
        return edCess;
    }

    public BigDecimal getAddDiscount() {
        return addDiscount;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public BigDecimal getFcAmount() {
        return fcAmount;
    }

    public String getCst() {
        return cst;
    }

    public BigDecimal getCstAmt() {
        return cstAmt;
    }

    public BigDecimal getUnitRate() {
        return unitRate;
    }

    public String getQtyRecord() {
        return qtyRecord;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public long getPprNo() {
        return pprNo;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setOilDataId(long oilDataId) {
        this.oilDataId = oilDataId;
    }

    public void setPosr(String posr) {
        this.posr = posr;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInvoiceNo(long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setInvoiceAmt(BigDecimal invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public void setInvoiceQty(String invoiceQty) {
        this.invoiceQty = invoiceQty;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setScDiscount(BigDecimal scDiscount) {
        this.scDiscount = scDiscount;
    }

    public void setEd_rate(BigDecimal ed_rate) {
        this.ed_rate = ed_rate;
    }

    public void setEdAmount(BigDecimal edAmount) {
        this.edAmount = edAmount;
    }

    public void setEdCess(BigDecimal edCess) {
        this.edCess = edCess;
    }

    public void setAddDiscount(BigDecimal addDiscount) {
        this.addDiscount = addDiscount;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public void setFcAmount(BigDecimal fcAmount) {
        this.fcAmount = fcAmount;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public void setCstAmt(BigDecimal cstAmt) {
        this.cstAmt = cstAmt;
    }

    public void setUnitRate(BigDecimal unitRate) {
        this.unitRate = unitRate;
    }

    public void setQtyRecord(String qtyRecord) {
        this.qtyRecord = qtyRecord;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public void setPprNo(long pprNo) {
        this.pprNo = pprNo;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public BigDecimal getQtyReceivedRate() {
        return qtyReceivedRate;
    }

    public void setQtyReceivedRate(BigDecimal qtyReceivedRate) {
        this.qtyReceivedRate = qtyReceivedRate;
    }
    
    
}
