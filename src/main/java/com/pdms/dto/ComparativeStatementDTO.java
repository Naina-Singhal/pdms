/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author hpasupuleti
 */
public class ComparativeStatementDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 2608116654354420601L;
    
    
    
    
    private long cstID;
    private String encCstID;
    
    private long vendorID;
    private long fileNumber;
    private long categoryId;
    
    
    
    private String cstType;
    private long itemID;
    private long itemqty;
    private long units;
    private BigDecimal basicRate;
    private String price;
    private BigDecimal discount;
    private BigDecimal subtotal;
    private String packetForwardCharges;
    private String exciseDuty;
    private String salesTax;
    private String insurance;
    private String customDuty;
    private BigDecimal cleaningCharges;
    private String inlandFreight;
    private String freight;
    private BigDecimal installation;
    private String serviceTaxPercent;
    private BigDecimal serviceTax;
    private BigDecimal trainingcha;
    private BigDecimal inspection;
    private BigDecimal testingAmount;
    private String gstonitit;
    private BigDecimal anyother;
    private String octroitax;
    private String sample;
    private String intrest;
    private String deliveryPeriod;
    private String payment;
    private String validityDays;
    private String validityDate;
    private BigDecimal landingCost;
    private BigDecimal totalAmount; 
    private BigDecimal totalLandingCost;
    private String hsncode;
    private BigDecimal mrprice;
    private String remarks;    
    
    private String vendorName;
    private String itemName;        
    private String modalObjStr;
    
       
    
    
    private ItemDTO itemObj = new ItemDTO();
    private VendorDTO venObj = new VendorDTO();
    private CategoryDTO catObj = new CategoryDTO();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getCstID() {
        return cstID;
    }

    public String getEncCstID() {
        return encCstID;
    }

    public long getVendorID() {
        return vendorID;
    }

    public long getFileNumber() {
        return fileNumber;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getCstType() {
        return cstType;
    }

    public long getItemID() {
        return itemID;
    }

    public long getItemqty() {
        return itemqty;
    }

    public long getUnits() {
        return units;
    }

    public BigDecimal getBasicRate() {
        return basicRate;
    }

    public String getPrice() {
        return price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public String getPacketForwardCharges() {
        return packetForwardCharges;
    }

    public String getExciseDuty() {
        return exciseDuty;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getCustomDuty() {
        return customDuty;
    }

    public BigDecimal getCleaningCharges() {
        return cleaningCharges;
    }

    public String getInlandFreight() {
        return inlandFreight;
    }

    public String getFreight() {
        return freight;
    }

    public BigDecimal getInstallation() {
        return installation;
    }

    public String getServiceTaxPercent() {
        return serviceTaxPercent;
    }

    public BigDecimal getServiceTax() {
        return serviceTax;
    }

    public BigDecimal getTrainingcha() {
        return trainingcha;
    }

    public BigDecimal getInspection() {
        return inspection;
    }

    public BigDecimal getTestingAmount() {
        return testingAmount;
    }

    public String getGstonitit() {
        return gstonitit;
    }

    public BigDecimal getAnyother() {
        return anyother;
    }

    public String getOctroitax() {
        return octroitax;
    }

    public String getSample() {
        return sample;
    }

    public String getIntrest() {
        return intrest;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public String getPayment() {
        return payment;
    }

    public String getValidityDays() {
        return validityDays;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public BigDecimal getLandingCost() {
        return landingCost;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public BigDecimal getTotalLandingCost() {
        return totalLandingCost;
    }

    public String getHsncode() {
        return hsncode;
    }

    public BigDecimal getMrprice() {
        return mrprice;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getVendorName() {
        return vendorName;
    }

    public String getItemName() {
        return itemName;
    }

    public String getModalObjStr() {
        return modalObjStr;
    }

    public ItemDTO getItemObj() {
        return itemObj;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

    public CategoryDTO getCatObj() {
        return catObj;
    }

    public void setCstID(long cstID) {
        this.cstID = cstID;
    }

    public void setEncCstID(String encCstID) {
        this.encCstID = encCstID;
    }

    public void setVendorID(long vendorID) {
        this.vendorID = vendorID;
    }

    public void setFileNumber(long fileNumber) {
        this.fileNumber = fileNumber;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setCstType(String cstType) {
        this.cstType = cstType;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public void setItemqty(long itemqty) {
        this.itemqty = itemqty;
    }

    public void setUnits(long units) {
        this.units = units;
    }

    public void setBasicRate(BigDecimal basicRate) {
        this.basicRate = basicRate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setPacketForwardCharges(String packetForwardCharges) {
        this.packetForwardCharges = packetForwardCharges;
    }

    public void setExciseDuty(String exciseDuty) {
        this.exciseDuty = exciseDuty;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public void setCustomDuty(String customDuty) {
        this.customDuty = customDuty;
    }

    public void setCleaningCharges(BigDecimal cleaningCharges) {
        this.cleaningCharges = cleaningCharges;
    }

    public void setInlandFreight(String inlandFreight) {
        this.inlandFreight = inlandFreight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public void setInstallation(BigDecimal installation) {
        this.installation = installation;
    }

    public void setServiceTaxPercent(String serviceTaxPercent) {
        this.serviceTaxPercent = serviceTaxPercent;
    }

    public void setServiceTax(BigDecimal serviceTax) {
        this.serviceTax = serviceTax;
    }

    public void setTrainingcha(BigDecimal trainingcha) {
        this.trainingcha = trainingcha;
    }

    public void setInspection(BigDecimal inspection) {
        this.inspection = inspection;
    }

    public void setTestingAmount(BigDecimal testingAmount) {
        this.testingAmount = testingAmount;
    }

    public void setGstonitit(String gstonitit) {
        this.gstonitit = gstonitit;
    }

    public void setAnyother(BigDecimal anyother) {
        this.anyother = anyother;
    }

    public void setOctroitax(String octroitax) {
        this.octroitax = octroitax;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public void setIntrest(String intrest) {
        this.intrest = intrest;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setValidityDays(String validityDays) {
        this.validityDays = validityDays;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public void setLandingCost(BigDecimal landingCost) {
        this.landingCost = landingCost;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setTotalLandingCost(BigDecimal totalLandingCost) {
        this.totalLandingCost = totalLandingCost;
    }

    public void setHsncode(String hsncode) {
        this.hsncode = hsncode;
    }

    public void setMrprice(BigDecimal mrprice) {
        this.mrprice = mrprice;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setModalObjStr(String modalObjStr) {
        this.modalObjStr = modalObjStr;
    }

    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }

    public void setCatObj(CategoryDTO catObj) {
        this.catObj = catObj;
    }
    
    
    
    
}
