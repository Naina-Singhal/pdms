/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class TransferEntryDTO implements Serializable{
    private long transferEntryId;
    private String options;
    private long vouChallanNo;
    private String voucherDate;
    private BigDecimal amount;
    private String teYear; 
    private long teNumber;
    
    private String hcodeNew;
    private String hoaNew;
    private String desNew;
    private String scodeNew;
    private BigDecimal debitNew;
    private BigDecimal creditNew;
    private String remarks;
    
    private VoucherNoDTO vouNoObj = new VoucherNoDTO();
    private VouNoHoaItemsDTO vouNoHoaItems = new VouNoHoaItemsDTO();

    public long getTransferEntryId() {
        return transferEntryId;
    }

    public String getHcodeNew() {
        return hcodeNew;
    }

    public String getHoaNew() {
        return hoaNew;
    }

    public String getDesNew() {
        return desNew;
    }

    public String getScodeNew() {
        return scodeNew;
    }

    public BigDecimal getDebitNew() {
        return debitNew;
    }

    public BigDecimal getCreditNew() {
        return creditNew;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setTransferEntryId(long transferEntryId) {
        this.transferEntryId = transferEntryId;
    }

    public void setHcodeNew(String hcodeNew) {
        this.hcodeNew = hcodeNew;
    }

    public void setHoaNew(String hoaNew) {
        this.hoaNew = hoaNew;
    }

    public void setDesNew(String desNew) {
        this.desNew = desNew;
    }

    public void setScodeNew(String scodeNew) {
        this.scodeNew = scodeNew;
    }

    public void setDebitNew(BigDecimal debitNew) {
        this.debitNew = debitNew;
    }

    public void setCreditNew(BigDecimal creditNew) {
        this.creditNew = creditNew;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOptions() {
        return options;
    }

    public long getVouChallanNo() {
        return vouChallanNo;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTeYear() {
        return teYear;
    }

    public long getTeNumber() {
        return teNumber;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setVouChallanNo(long vouChallanNo) {
        this.vouChallanNo = vouChallanNo;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTeYear(String teYear) {
        this.teYear = teYear;
    }

    public void setTeNumber(long teNumber) {
        this.teNumber = teNumber;
    }

    public VoucherNoDTO getVouNoObj() {
        return vouNoObj;
    }

    public VouNoHoaItemsDTO getVouNoHoaItems() {
        return vouNoHoaItems;
    }

    public void setVouNoObj(VoucherNoDTO vouNoObj) {
        this.vouNoObj = vouNoObj;
    }

    public void setVouNoHoaItems(VouNoHoaItemsDTO vouNoHoaItems) {
        this.vouNoHoaItems = vouNoHoaItems;
    }
    
    
    
}
