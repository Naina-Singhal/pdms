/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
public class PublicTenderVendorDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private long publicTenderVendorID;
    private long vendorID;
    private VendorDTO vendorObj = new VendorDTO();
    private String closingDate;
    private long tenderID;
    private String fileNo;
    private String poNumber;
    private String poGenDate;
    private String poOffer;

    /**
     * @return the publicTenderVendorID
     */
    public long getPublicTenderVendorID() {
        return publicTenderVendorID;
    }

    /**
     * @param publicTenderVendorID the publicTenderVendorID to set
     */
    public void setPublicTenderVendorID(long publicTenderVendorID) {
        this.publicTenderVendorID = publicTenderVendorID;
    }

    /**
     * @return the vendorID
     */
    public long getVendorID() {
        return vendorID;
    }

    /**
     * @param vendorID the vendorID to set
     */
    public void setVendorID(long vendorID) {
        this.vendorID = vendorID;
    }

    /**
     * @return the vendorObj
     */
    public VendorDTO getVendorObj() {
        return vendorObj;
    }

    /**
     * @param vendorObj the vendorObj to set
     */
    public void setVendorObj(VendorDTO vendorObj) {
        this.vendorObj = vendorObj;
    }

    /**
     * @return the closingDate
     */
    public String getClosingDate() {
        return closingDate;
    }

    /**
     * @param closingDate the closingDate to set
     */
    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * @return the tenderID
     */
    public long getTenderID() {
        return tenderID;
    }

    /**
     * @param tenderID the tenderID to set
     */
    public void setTenderID(long tenderID) {
        this.tenderID = tenderID;
    }

    /**
     * @return the fileNo
     */
    public String getFileNo() {
        return fileNo;
    }

    /**
     * @param fileNo the fileNo to set
     */
    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    /**
     * @return the poNumber
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * @param poNumber the poNumber to set
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    /**
     * @return the poGenDate
     */
    public String getPoGenDate() {
        return poGenDate;
    }

    /**
     * @param poGenDate the poGenDate to set
     */
    public void setPoGenDate(String poGenDate) {
        this.poGenDate = poGenDate;
    }

    /**
     * @return the poOffer
     */
    public String getPoOffer() {
        return poOffer;
    }

    /**
     * @param poOffer the poOffer to set
     */
    public void setPoOffer(String poOffer) {
        this.poOffer = poOffer;
    }
    
    
}
