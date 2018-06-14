/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import com.pdms.master.dto.SignatoryDTO;
import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class PoEntryDTO extends BaseDTO implements Serializable {

    private long poEntryID;
    private String tenderFN;
    private String venderName;
    private String quotationNo;
    private String referenceNo;
    private String venDescription;
    private long poNumber;
    private String poDate;
    private String price;
    private String payment;
    private String inspection;
    private String poValue;
    private String placeOfDelivery;
    private String deliveryPeriod;
    private String signatory;
    private String preparedDate;
    private String preparedBy;
    private String securityDeposit;
    private String pBG;
    private String offer;    
    private String specED;   
    private String addST;    
    private String freight;
    private String others;
    private VendorDTO vendorObj = new VendorDTO();
    private SignatoryDTO signObj = new SignatoryDTO();

    public long getPoEntryID() {
        return poEntryID;
    }

    public String getTenderFN() {
        return tenderFN;
    }

    public String getVenderName() {
        return venderName;
    }

    public String getQuotationNo() {
        return quotationNo;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public String getVenDescription() {
        return venDescription;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getPoDate() {
        return poDate;
    }

    public String getPrice() {
        return price;
    }

    public String getPayment() {
        return payment;
    }

    public String getInspection() {
        return inspection;
    }

    public String getPoValue() {
        return poValue;
    }

    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    public String getDeliveryPeriod() {
        return deliveryPeriod;
    }

    public String getSignatory() {
        return signatory;
    }

    public String getPreparedDate() {
        return preparedDate;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public String getSecurityDeposit() {
        return securityDeposit;
    }

    public String getpBG() {
        return pBG;
    }

    public String getOffer() {
        return offer;
    }

    public String getSpecED() {
        return specED;
    }

    public String getAddST() {
        return addST;
    }

    public String getFreight() {
        return freight;
    }

    public String getOthers() {
        return others;
    }

    public void setPoEntryID(long poEntryID) {
        this.poEntryID = poEntryID;
    }

    public void setTenderFN(String tenderFN) {
        this.tenderFN = tenderFN;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public void setVenDescription(String venDescription) {
        this.venDescription = venDescription;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public void setPoValue(String poValue) {
        this.poValue = poValue;
    }

    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    public void setDeliveryPeriod(String deliveryPeriod) {
        this.deliveryPeriod = deliveryPeriod;
    }

    public void setSignatory(String signatory) {
        this.signatory = signatory;
    }

    public void setPreparedDate(String preparedDate) {
        this.preparedDate = preparedDate;
    }

    public void setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
    }

    public void setSecurityDeposit(String securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public void setpBG(String pBG) {
        this.pBG = pBG;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public void setSpecED(String specED) {
        this.specED = specED;
    }

    public void setAddST(String addST) {
        this.addST = addST;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public VendorDTO getVendorObj() {
        return vendorObj;
    }

    public void setVendorObj(VendorDTO vendorObj) {
        this.vendorObj = vendorObj;
    }

    public SignatoryDTO getSignObj() {
        return signObj;
    }

    public void setSignObj(SignatoryDTO signObj) {
        this.signObj = signObj;
    }

    
    
}
