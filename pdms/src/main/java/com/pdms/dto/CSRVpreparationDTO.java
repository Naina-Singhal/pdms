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
public class CSRVpreparationDTO extends BaseDTO implements Serializable{
    
    private long csrvPreparationID;    
    private long rvControlNo;
    private String date;
    private String poNumber;
    private String poNodate;
    private String dbNumber;
    private String dbDate;
    private String indentNumber;
    private String indentDate;
    private String updateDate;
    private String updatedBy;
    
    private String deliveryChallanNo;
    private String deliveryChallenDate;
    private String inspectedBy;
    private String section;
    private String itemsCovered;
    private String deliveryAt;
    private String pfCharges;
    private String tptCharges;
    private String otherCharges;
    private String remarks;
    
    private String amountPaid;
    private String paymentTerms;
    private String indentor;
    private String payingAuthority;
    private String headOfAccount;    
    private String inspectRemarks;        
    private String group;
    private String cardNumber;
    
    private String code;
    private String itemDescription;
    private String partNumber;
    private String unit;
    private String SuppLastReceived;
    private String balance;
    private String basicRate;
    private String lifoRate;
    private String waRate;
    private String rateDate;
    
    private String mrDate;
    private String qtyAccepted;
    private String mrUnit;
    private String mrPoNumber;
    private String rvNumber;
    private String mrRemarks;
    private String mrLocation;
    private String minLevelDate;

    public long getCsrvPreparationID() {
        return csrvPreparationID;
    }


    public long getRvControlNo() {
        return rvControlNo;
    }

    public String getDate() {
        return date;
    }

    public String getPoNumber() {
        return poNumber;
    }

    

    public String getPoNodate() {
        return poNodate;
    }

    public String getDbNumber() {
        return dbNumber;
    }

    public String getDbDate() {
        return dbDate;
    }

    public String getIndentNumber() {
        return indentNumber;
    }

    public String getIndentDate() {
        return indentDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public String getDeliveryChallanNo() {
        return deliveryChallanNo;
    }

    public String getDeliveryChallenDate() {
        return deliveryChallenDate;
    }

    public String getInspectedBy() {
        return inspectedBy;
    }

    public String getSection() {
        return section;
    }

    public String getItemsCovered() {
        return itemsCovered;
    }

    public String getDeliveryAt() {
        return deliveryAt;
    }

    public String getPfCharges() {
        return pfCharges;
    }

    public String getTptCharges() {
        return tptCharges;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public String getIndentor() {
        return indentor;
    }

    public String getPayingAuthority() {
        return payingAuthority;
    }

    public String getHeadOfAccount() {
        return headOfAccount;
    }

    
    public String getInspectRemarks() {
        return inspectRemarks;
    }

    

    public String getGroup() {
        return group;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCode() {
        return code;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public String getUnit() {
        return unit;
    }

    public String getSuppLastReceived() {
        return SuppLastReceived;
    }

    public String getBalance() {
        return balance;
    }

    public String getBasicRate() {
        return basicRate;
    }

    public String getLifoRate() {
        return lifoRate;
    }

    public String getWaRate() {
        return waRate;
    }

    public String getRateDate() {
        return rateDate;
    }

    public String getMrDate() {
        return mrDate;
    }

    public String getQtyAccepted() {
        return qtyAccepted;
    }

    public String getMrUnit() {
        return mrUnit;
    }

    public String getMrPoNumber() {
        return mrPoNumber;
    }

    public String getRvNumber() {
        return rvNumber;
    }

    public String getMrRemarks() {
        return mrRemarks;
    }

    public String getMrLocation() {
        return mrLocation;
    }

    public String getMinLevelDate() {
        return minLevelDate;
    }

    public void setCsrvPreparationID(long csrvPreparationID) {
        this.csrvPreparationID = csrvPreparationID;
    }


    public void setRvControlNo(long rvControlNo) {
        this.rvControlNo = rvControlNo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    

    public void setPoNodate(String poNodate) {
        this.poNodate = poNodate;
    }

    public void setDbNumber(String dbNumber) {
        this.dbNumber = dbNumber;
    }

    public void setDbDate(String dbDate) {
        this.dbDate = dbDate;
    }

    public void setIndentNumber(String indentNumber) {
        this.indentNumber = indentNumber;
    }

    public void setIndentDate(String indentDate) {
        this.indentDate = indentDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setDeliveryChallanNo(String deliveryChallanNo) {
        this.deliveryChallanNo = deliveryChallanNo;
    }

    public void setDeliveryChallenDate(String deliveryChallenDate) {
        this.deliveryChallenDate = deliveryChallenDate;
    }

    public void setInspectedBy(String inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setItemsCovered(String itemsCovered) {
        this.itemsCovered = itemsCovered;
    }

    public void setDeliveryAt(String deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public void setPfCharges(String pfCharges) {
        this.pfCharges = pfCharges;
    }

    public void setTptCharges(String tptCharges) {
        this.tptCharges = tptCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public void setIndentor(String indentor) {
        this.indentor = indentor;
    }

    public void setPayingAuthority(String payingAuthority) {
        this.payingAuthority = payingAuthority;
    }

    public void setHeadOfAccount(String headOfAccount) {
        this.headOfAccount = headOfAccount;
    }

    

    public void setInspectRemarks(String inspectRemarks) {
        this.inspectRemarks = inspectRemarks;
    }

   

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setSuppLastReceived(String SuppLastReceived) {
        this.SuppLastReceived = SuppLastReceived;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBasicRate(String basicRate) {
        this.basicRate = basicRate;
    }

    public void setLifoRate(String lifoRate) {
        this.lifoRate = lifoRate;
    }

    public void setWaRate(String waRate) {
        this.waRate = waRate;
    }

    public void setRateDate(String rateDate) {
        this.rateDate = rateDate;
    }

    public void setMrDate(String mrDate) {
        this.mrDate = mrDate;
    }

    public void setQtyAccepted(String qtyAccepted) {
        this.qtyAccepted = qtyAccepted;
    }

    public void setMrUnit(String mrUnit) {
        this.mrUnit = mrUnit;
    }

    public void setMrPoNumber(String mrPoNumber) {
        this.mrPoNumber = mrPoNumber;
    }

    public void setRvNumber(String rvNumber) {
        this.rvNumber = rvNumber;
    }

    public void setMrRemarks(String mrRemarks) {
        this.mrRemarks = mrRemarks;
    }

    public void setMrLocation(String mrLocation) {
        this.mrLocation = mrLocation;
    }

    public void setMinLevelDate(String minLevelDate) {
        this.minLevelDate = minLevelDate;
    }
    
    
    
    
}
