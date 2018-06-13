/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.uploads.dto;

import com.pdms.dto.BaseDTO;
import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class ExcelUploadDTO extends BaseDTO implements Serializable{
    
    private long id;
    private String fileNumber;
    private String vendorCode;
    private String srNo;
    private String itemCategory;
    private String itemDescription;
    private String qty;
    private String unit;
    private String customExciseDuty ;
    private String exciseDuty;
    private String octroiEntryTax;
    private String currencySelected;
    private String natureOfPrice;
    private String partNoModelNo;
    private String basicCost;
    private String discount;
    private String subTotalBPD;
    private String packForwaCharges;
    private String gst;
    private String freightCharges ;
    private String instalCommiCharges;
    private String trainingCharges;
    private String inspecTestCharges;
    private String gstOnITITCharges;
    private String anyOtherCharges;
    private String evaluatedCost;
    private String totEvaluatedCost;
    private String totEvaluaCostWords;
    private String hsnCode;
    private String mrp;

    public long getId() {
        return id;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public String getSrNo() {
        return srNo;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getQty() {
        return qty;
    }

    public String getUnit() {
        return unit;
    }

    public String getCustomExciseDuty() {
        return customExciseDuty;
    }

    public String getExciseDuty() {
        return exciseDuty;
    }

    public String getOctroiEntryTax() {
        return octroiEntryTax;
    }

    public String getCurrencySelected() {
        return currencySelected;
    }

    public String getNatureOfPrice() {
        return natureOfPrice;
    }

    public String getPartNoModelNo() {
        return partNoModelNo;
    }

    public String getBasicCost() {
        return basicCost;
    }

    public String getDiscount() {
        return discount;
    }

    public String getSubTotalBPD() {
        return subTotalBPD;
    }

    public String getPackForwaCharges() {
        return packForwaCharges;
    }

    public String getGst() {
        return gst;
    }

    public String getFreightCharges() {
        return freightCharges;
    }

    public String getInstalCommiCharges() {
        return instalCommiCharges;
    }

    public String getTrainingCharges() {
        return trainingCharges;
    }

    public String getInspecTestCharges() {
        return inspecTestCharges;
    }

    public String getGstOnITITCharges() {
        return gstOnITITCharges;
    }

    public String getAnyOtherCharges() {
        return anyOtherCharges;
    }

    public String getEvaluatedCost() {
        return evaluatedCost;
    }

    public String getTotEvaluatedCost() {
        return totEvaluatedCost;
    }

    public String getTotEvaluaCostWords() {
        return totEvaluaCostWords;
    }

   

    public void setId(long id) {
        this.id = id;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public void setSrNo(String srNo) {
        this.srNo = srNo;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCustomExciseDuty(String customExciseDuty) {
        this.customExciseDuty = customExciseDuty;
    }

    public void setExciseDuty(String exciseDuty) {
        this.exciseDuty = exciseDuty;
    }

    public void setOctroiEntryTax(String octroiEntryTax) {
        this.octroiEntryTax = octroiEntryTax;
    }

    public void setCurrencySelected(String currencySelected) {
        this.currencySelected = currencySelected;
    }

    public void setNatureOfPrice(String natureOfPrice) {
        this.natureOfPrice = natureOfPrice;
    }

    public void setPartNoModelNo(String partNoModelNo) {
        this.partNoModelNo = partNoModelNo;
    }

    public void setBasicCost(String basicCost) {
        this.basicCost = basicCost;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setSubTotalBPD(String subTotalBPD) {
        this.subTotalBPD = subTotalBPD;
    }

    public void setPackForwaCharges(String packForwaCharges) {
        this.packForwaCharges = packForwaCharges;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public void setFreightCharges(String freightCharges) {
        this.freightCharges = freightCharges;
    }

    public void setInstalCommiCharges(String instalCommiCharges) {
        this.instalCommiCharges = instalCommiCharges;
    }

    public void setTrainingCharges(String trainingCharges) {
        this.trainingCharges = trainingCharges;
    }

    public void setInspecTestCharges(String inspecTestCharges) {
        this.inspecTestCharges = inspecTestCharges;
    }

    public void setGstOnITITCharges(String gstOnITITCharges) {
        this.gstOnITITCharges = gstOnITITCharges;
    }

    public void setAnyOtherCharges(String anyOtherCharges) {
        this.anyOtherCharges = anyOtherCharges;
    }

    public void setEvaluatedCost(String evaluatedCost) {
        this.evaluatedCost = evaluatedCost;
    }

    public void setTotEvaluatedCost(String totEvaluatedCost) {
        this.totEvaluatedCost = totEvaluatedCost;
    }

    public void setTotEvaluaCostWords(String totEvaluaCostWords) {
        this.totEvaluaCostWords = totEvaluaCostWords;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public String getMrp() {
        return mrp;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

  
   

    
    
}
