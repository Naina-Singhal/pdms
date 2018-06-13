/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import com.pdms.dto.VendorDTO;
import com.pdms.master.dto.BankMasterDTO;
import com.pdms.master.dto.RtgsDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class VoucherNoDTO implements Serializable{
    
    private long voucherNoId;
    private long compCode;
    private String supplierName;
    private String posr;
    private String poNumber;
    private String pprNumber;
    private String voucherNo;
    private String voucherDate;
    private String chequeNo;
    private String chequeDate;
    private BigDecimal chequeAmount;
    private String cqe_dd_rtgs;
    private String bankCode;
    private String bank;
    private long req_number;
    private BigDecimal lcBalanceAmt;
    private String rtgsNumber;
    private long ibcNumber;
    private VendorDTO venObj = new VendorDTO();
    private RtgsDTO rtgsObj = new RtgsDTO();
    private BankMasterDTO bankObj = new BankMasterDTO();

    public void setRtgsObj(RtgsDTO rtgsObj) {
        this.rtgsObj = rtgsObj;
    }
    
    

    public RtgsDTO getRtgsObj() {
        return rtgsObj;
    }

    public long getVoucherNoId() {
        return voucherNoId;
    }

    public long getCompCode() {
        return compCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPosr() {
        return posr;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public String getPprNumber() {
        return pprNumber;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public BigDecimal getChequeAmount() {
        return chequeAmount;
    }

    public String getCqe_dd_rtgs() {
        return cqe_dd_rtgs;
    }

    public String getBankCode() {
        return bankCode;
    }

    public String getBank() {
        return bank;
    }

    public long getReq_number() {
        return req_number;
    }

    public BigDecimal getLcBalanceAmt() {
        return lcBalanceAmt;
    }

    public String getRtgsNumber() {
        return rtgsNumber;
    }

    public void setVoucherNoId(long voucherNoId) {
        this.voucherNoId = voucherNoId;
    }

    public void setCompCode(long compCode) {
        this.compCode = compCode;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setPosr(String posr) {
        this.posr = posr;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public void setPprNumber(String pprNumber) {
        this.pprNumber = pprNumber;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public void setChequeAmount(BigDecimal chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public void setCqe_dd_rtgs(String cqe_dd_rtgs) {
        this.cqe_dd_rtgs = cqe_dd_rtgs;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setReq_number(long req_number) {
        this.req_number = req_number;
    }

    public void setLcBalanceAmt(BigDecimal lcBalanceAmt) {
        this.lcBalanceAmt = lcBalanceAmt;
    }

    public void setRtgsNumber(String rtgsNumber) {
        this.rtgsNumber = rtgsNumber;
    }

    public long getIbcNumber() {
        return ibcNumber;
    }

    public void setIbcNumber(long ibcNumber) {
        this.ibcNumber = ibcNumber;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

   

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }

    public BankMasterDTO getBankObj() {
        return bankObj;
    }

    public void setBankObj(BankMasterDTO bankObj) {
        this.bankObj = bankObj;
    }

    
    
    
}
