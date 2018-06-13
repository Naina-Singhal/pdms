/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dto;

import com.pdms.account.ItemsDto.HoaItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.dto.BaseDTO;
import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class VoucherDTO extends BaseDTO implements Serializable{
    
    private long voucherID;
    private long fileNumber;
    private long poNumber;
    private String posrFileNo;
    private String bgClause;
    private String nameOfSupplr;
    private String gcode;
    private String fileClosed;
    private String modeOfPaymt;
    private String abc;
    private String bbys;
    private String scode;
    private String rpumOrmngr;   
    private String cqeDdR;   
    private long pprNo;
    private String lrNo;
    private String bankLRC;
    private String lrDT;
    private String quantity;
    private String ibcNo;
    private String pprNo2;
    private String amountPaid;
    private String dateOfpmt;    
    private String recoveries;
    private String defference;
    private String rsInWords;
    private long compCode;    
    

    public long getVoucherID() {
        return voucherID;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getPosrFileNo() {
        return posrFileNo;
    }

    public String getBgClause() {
        return bgClause;
    }

    public String getNameOfSupplr() {
        return nameOfSupplr;
    }

    public String getGcode() {
        return gcode;
    }

    public String getFileClosed() {
        return fileClosed;
    }

    public String getModeOfPaymt() {
        return modeOfPaymt;
    }

    public String getAbc() {
        return abc;
    }

    public String getBbys() {
        return bbys;
    }

    public String getScode() {
        return scode;
    }

    public String getRpumOrmngr() {
        return rpumOrmngr;
    }

    public String getCqeDdR() {
        return cqeDdR;
    }

    public long getPprNo() {
        return pprNo;
    }

    public String getLrNo() {
        return lrNo;
    }

    public String getBankLRC() {
        return bankLRC;
    }

    public String getLrDT() {
        return lrDT;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getIbcNo() {
        return ibcNo;
    }

    public String getPprNo2() {
        return pprNo2;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getDateOfpmt() {
        return dateOfpmt;
    }

    public String getRecoveries() {
        return recoveries;
    }

    public String getDefference() {
        return defference;
    }

    public String getRsInWords() {
        return rsInWords;
    }

    public long getCompCode() {
        return compCode;
    }

    public void setVoucherID(long voucherID) {
        this.voucherID = voucherID;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setPosrFileNo(String posrFileNo) {
        this.posrFileNo = posrFileNo;
    }

    public void setBgClause(String bgClause) {
        this.bgClause = bgClause;
    }

    public void setNameOfSupplr(String nameOfSupplr) {
        this.nameOfSupplr = nameOfSupplr;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public void setFileClosed(String fileClosed) {
        this.fileClosed = fileClosed;
    }

    public void setModeOfPaymt(String modeOfPaymt) {
        this.modeOfPaymt = modeOfPaymt;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }

    public void setBbys(String bbys) {
        this.bbys = bbys;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public void setRpumOrmngr(String rpumOrmngr) {
        this.rpumOrmngr = rpumOrmngr;
    }

    public void setCqeDdR(String cqeDdR) {
        this.cqeDdR = cqeDdR;
    }

    public void setPprNo(long pprNo) {
        this.pprNo = pprNo;
    }

    public void setLrNo(String lrNo) {
        this.lrNo = lrNo;
    }

    public void setBankLRC(String bankLRC) {
        this.bankLRC = bankLRC;
    }

    public void setLrDT(String lrDT) {
        this.lrDT = lrDT;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setIbcNo(String ibcNo) {
        this.ibcNo = ibcNo;
    }

    public void setPprNo2(String pprNo2) {
        this.pprNo2 = pprNo2;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setDateOfpmt(String dateOfpmt) {
        this.dateOfpmt = dateOfpmt;
    }

    public void setRecoveries(String recoveries) {
        this.recoveries = recoveries;
    }

    public void setDefference(String defference) {
        this.defference = defference;
    }

    public void setRsInWords(String rsInWords) {
        this.rsInWords = rsInWords;
    }

    public void setCompCode(long compCode) {
        this.compCode = compCode;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    
    
    
}
