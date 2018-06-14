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
public class InspectionClearanceDTO extends BaseDTO implements Serializable{
    
    private long inspeClearID;
    private String dbNumber;
    private String dbDate;    
    private String section;
    private String plant;
    private String purchaseUnit;
    private String poLastNo;
    private String poDate;
    private long grOrrNumber;
    private String grOrrDate;
    private String itemsCovered;    
    private String inspectedBy;
    private String inspeCleaRemarks;

    public long getInspeClearID() {
        return inspeClearID;
    }

    public String getDbNumber() {
        return dbNumber;
    }

    public String getDbDate() {
        return dbDate;
    }

    public String getSection() {
        return section;
    }

    public String getPlant() {
        return plant;
    }

    public String getPurchaseUnit() {
        return purchaseUnit;
    }

    public String getPoLastNo() {
        return poLastNo;
    }

    public String getPoDate() {
        return poDate;
    }

    public long getGrOrrNumber() {
        return grOrrNumber;
    }

    public String getGrOrrDate() {
        return grOrrDate;
    }

    public String getItemsCovered() {
        return itemsCovered;
    }

    public String getInspectedBy() {
        return inspectedBy;
    }

    public String getInspeCleaRemarks() {
        return inspeCleaRemarks;
    }

    public void setInspeClearID(long inspeClearID) {
        this.inspeClearID = inspeClearID;
    }

    public void setDbNumber(String dbNumber) {
        this.dbNumber = dbNumber;
    }

    public void setDbDate(String dbDate) {
        this.dbDate = dbDate;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setPurchaseUnit(String purchaseUnit) {
        this.purchaseUnit = purchaseUnit;
    }

    public void setPoLastNo(String poLastNo) {
        this.poLastNo = poLastNo;
    }

    public void setPoDate(String poDate) {
        this.poDate = poDate;
    }

    public void setGrOrrNumber(long grOrrNumber) {
        this.grOrrNumber = grOrrNumber;
    }

    public void setGrOrrDate(String grOrrDate) {
        this.grOrrDate = grOrrDate;
    }

    public void setItemsCovered(String itemsCovered) {
        this.itemsCovered = itemsCovered;
    }

    public void setInspectedBy(String inspectedBy) {
        this.inspectedBy = inspectedBy;
    }

    public void setInspeCleaRemarks(String inspeCleaRemarks) {
        this.inspeCleaRemarks = inspeCleaRemarks;
    }
    
    
    
}
