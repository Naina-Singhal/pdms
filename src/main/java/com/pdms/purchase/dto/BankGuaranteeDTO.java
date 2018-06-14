/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.dto;

import com.pdms.dto.VendorDTO;
import com.pdms.master.dto.BankMasterDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class BankGuaranteeDTO implements Serializable{
    
    private long bankGuaranteeId;
    private long Slno;
    private String tempone;
    private long poNumber;
    private String posr;
    private String modeBgDd;
    private String vendorName;
    private String bank;
    private String tempTwo;
    private String bgNumber;
    private String bgDate;
    private BigDecimal amount;
    private String expireDate;
    private String gracePeriod;
    private String retiredDate;
    private String entryDate;
    private String remarks;
    private String status;
    
    private VendorDTO venObj = new VendorDTO();
    private BankMasterDTO bankObj = new BankMasterDTO();

    public long getBankGuaranteeId() {
        return bankGuaranteeId;
    }

    public long getSlno() {
        return Slno;
    }

    public String getTempone() {
        return tempone;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getPosr() {
        return posr;
    }

    public String getModeBgDd() {
        return modeBgDd;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getBank() {
        return bank;
    }

    public String getTempTwo() {
        return tempTwo;
    }

    public String getBgNumber() {
        return bgNumber;
    }

    public String getBgDate() {
        return bgDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getGracePeriod() {
        return gracePeriod;
    }

    public String getRetiredDate() {
        return retiredDate;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setBankGuaranteeId(long bankGuaranteeId) {
        this.bankGuaranteeId = bankGuaranteeId;
    }

    public void setSlno(long Slno) {
        this.Slno = Slno;
    }

    public void setTempone(String tempone) {
        this.tempone = tempone;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setPosr(String posr) {
        this.posr = posr;
    }

    public void setModeBgDd(String modeBgDd) {
        this.modeBgDd = modeBgDd;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setTempTwo(String tempTwo) {
        this.tempTwo = tempTwo;
    }

    public void setBgNumber(String bgNumber) {
        this.bgNumber = bgNumber;
    }

    public void setBgDate(String bgDate) {
        this.bgDate = bgDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setGracePeriod(String gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    public void setRetiredDate(String retiredDate) {
        this.retiredDate = retiredDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

    public BankMasterDTO getBankObj() {
        return bankObj;
    }

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }

    public void setBankObj(BankMasterDTO bankObj) {
        this.bankObj = bankObj;
    }
    
    
    
    
}
