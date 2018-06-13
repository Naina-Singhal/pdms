/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class LrEnDeFrStoresDTO implements Serializable{
    private long lrEnDeStoresId;
    private long poNumber;
    private String vendorName;
    private long challanNo;
    private long rrAndLrNumber;
    private String rrAndLrDate;
    private String numOfPackage;
    private String freight;
    private String fromPlace;
    private String toPlace;
    private String nameTransporter;

    public long getLrEnDeStoresId() {
        return lrEnDeStoresId;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public String getVendorName() {
        return vendorName;
    }

    public long getChallanNo() {
        return challanNo;
    }

    public long getRrAndLrNumber() {
        return rrAndLrNumber;
    }

    public String getNumOfPackage() {
        return numOfPackage;
    }

    public String getFreight() {
        return freight;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public String getNameTransporter() {
        return nameTransporter;
    }

    public void setLrEnDeStoresId(long lrEnDeStoresId) {
        this.lrEnDeStoresId = lrEnDeStoresId;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public void setChallanNo(long challanNo) {
        this.challanNo = challanNo;
    }

    public void setRrAndLrNumber(long rrAndLrNumber) {
        this.rrAndLrNumber = rrAndLrNumber;
    }

    public void setNumOfPackage(String numOfPackage) {
        this.numOfPackage = numOfPackage;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public void setNameTransporter(String nameTransporter) {
        this.nameTransporter = nameTransporter;
    }

    public String getRrAndLrDate() {
        return rrAndLrDate;
    }

    public void setRrAndLrDate(String rrAndLrDate) {
        this.rrAndLrDate = rrAndLrDate;
    }
    
    
    
}
