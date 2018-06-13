/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class MaterialReceiptDTO extends BaseDTO implements Serializable{
    
    private long materialRecID;  
    private String mateReceiptType;
    private String excessQuaAllow;
    private long dbNumber;
    private String date;
    private String purchaseUnit;
    private String section;
    private String plant;
    private String poLastNo;
    private String poLNoDate;
    private String delayRchaNo;
    private String delayDate;    
    private String lrORrrNo;
    private String lrDate;
    private String lrClearance; 
    private String transportCharges;
    private String tranChaType;
    private String packingCharges;    
    private String otherDemCha;
    private String itemsCovered;
    private String remarks;
    private String locInRecSec;  
    private String userDbId;

    public long getMaterialRecID() {
        return materialRecID;
    }

    

    public String getExcessQuaAllow() {
        return excessQuaAllow;
    }

    public long getDbNumber() {
        return dbNumber;
    }

    public String getDate() {
        return date;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public String getSection() {
        return section;
    }

    public String getPlant() {
        return plant;
    }

    public String getPoLastNo() {
        return poLastNo;
    }

    public String getPoLNoDate() {
        return poLNoDate;
    }

    public String getDelayRchaNo() {
        return delayRchaNo;
    }

    public String getDelayDate() {
        return delayDate;
    }

    

    public String getLrORrrNo() {
        return lrORrrNo;
    }

    public String getLrDate() {
        return lrDate;
    }

  

    public String getTransportCharges() {
        return transportCharges;
    }

    public String getTranChaType() {
        return tranChaType;
    }

    public String getPackingCharges() {
        return packingCharges;
    }

    

    public String getOtherDemCha() {
        return otherDemCha;
    }

    public String getItemsCovered() {
        return itemsCovered;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getLocInRecSec() {
        return locInRecSec;
    }

    

    

    public void setMaterialRecID(long materialRecID) {
        this.materialRecID = materialRecID;
    }

    

    public void setExcessQuaAllow(String excessQuaAllow) {
        this.excessQuaAllow = excessQuaAllow;
    }

    public void setDbNumber(long dbNumber) {
        this.dbNumber = dbNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setPoLastNo(String poLastNo) {
        this.poLastNo = poLastNo;
    }

    public void setPoLNoDate(String poLNoDate) {
        this.poLNoDate = poLNoDate;
    }

    public void setDelayRchaNo(String delayRchaNo) {
        this.delayRchaNo = delayRchaNo;
    }

    public void setDelayDate(String delayDate) {
        this.delayDate = delayDate;
    }

    
    public void setLrORrrNo(String lrORrrNo) {
        this.lrORrrNo = lrORrrNo;
    }

    public void setLrDate(String lrDate) {
        this.lrDate = lrDate;
    }

   

    public void setTransportCharges(String transportCharges) {
        this.transportCharges = transportCharges;
    }

    public void setTranChaType(String tranChaType) {
        this.tranChaType = tranChaType;
    }

    public void setPackingCharges(String packingCharges) {
        this.packingCharges = packingCharges;
    }

 

    public void setOtherDemCha(String otherDemCha) {
        this.otherDemCha = otherDemCha;
    }

    public void setItemsCovered(String itemsCovered) {
        this.itemsCovered = itemsCovered;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setLocInRecSec(String locInRecSec) {
        this.locInRecSec = locInRecSec;
    }

    public String getLrClearance() {
        return lrClearance;
    }

    public void setLrClearance(String lrClearance) {
        this.lrClearance = lrClearance;
    }

    public String getUserDbId() {
        return userDbId;
    }

    public void setUserDbId(String userDbId) {
        this.userDbId = userDbId;
    }

    public String getMateReceiptType() {
        return mateReceiptType;
    }

    public void setMateReceiptType(String mateReceiptType) {
        this.mateReceiptType = mateReceiptType;
    }

    
    
    
    
}
