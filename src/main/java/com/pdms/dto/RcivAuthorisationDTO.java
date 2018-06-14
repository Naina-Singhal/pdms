/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author STEINMETZ
 */
public class RcivAuthorisationDTO extends BaseDTO implements Serializable{
    
    private long rcivAuthoriID;  
    private long poNumber;
    private String authoriStatus;
    private String storeRequisition;
    private String plant;
    private String date;
    private String issueType;
    private String issueTo;
    private String disposal;
    private String issueFromOSecNa;
    private String requisitionNo;
    private String requisitionDate;    
    private String controlDate;    
    private String indentorName;
    private String section;
    private String jobAllocation;
    private String deliveryAt;
    private String itemCovered;
    private String AuthorisedYesNo;
    private String authorisedBy;    
    private String authorisedByDate;
    private String remarks;

    public long getRcivAuthoriID() {
        return rcivAuthoriID;
    }


    public String getAuthoriStatus() {
        return authoriStatus;
    }

    public String getStoreRequisition() {
        return storeRequisition;
    }

    public String getPlant() {
        return plant;
    }

    public String getDate() {
        return date;
    }

    public String getIssueType() {
        return issueType;
    }

    public String getIssueTo() {
        return issueTo;
    }

    public String getDisposal() {
        return disposal;
    }

    public String getIssueFromOSecNa() {
        return issueFromOSecNa;
    }

    public String getRequisitionNo() {
        return requisitionNo;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

  

    public String getControlDate() {
        return controlDate;
    }

   

    public String getIndentorName() {
        return indentorName;
    }

    public String getSection() {
        return section;
    }

    public String getJobAllocation() {
        return jobAllocation;
    }

    public String getDeliveryAt() {
        return deliveryAt;
    }

    public String getItemCovered() {
        return itemCovered;
    }

    

    public String getAuthorisedYesNo() {
        return AuthorisedYesNo;
    }

    public String getAuthorisedBy() {
        return authorisedBy;
    }

    

    public String getAuthorisedByDate() {
        return authorisedByDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRcivAuthoriID(long rcivAuthoriID) {
        this.rcivAuthoriID = rcivAuthoriID;
    }

 

    public void setAuthoriStatus(String authoriStatus) {
        this.authoriStatus = authoriStatus;
    }

    public void setStoreRequisition(String storeRequisition) {
        this.storeRequisition = storeRequisition;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public void setIssueTo(String issueTo) {
        this.issueTo = issueTo;
    }

    public void setDisposal(String disposal) {
        this.disposal = disposal;
    }

    public void setIssueFromOSecNa(String issueFromOSecNa) {
        this.issueFromOSecNa = issueFromOSecNa;
    }

    public void setRequisitionNo(String requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

 

    public void setControlDate(String controlDate) {
        this.controlDate = controlDate;
    }



    public void setIndentorName(String indentorName) {
        this.indentorName = indentorName;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setJobAllocation(String jobAllocation) {
        this.jobAllocation = jobAllocation;
    }

    public void setDeliveryAt(String deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public void setItemCovered(String itemCovered) {
        this.itemCovered = itemCovered;
    }

    

    public void setAuthorisedYesNo(String AuthorisedYesNo) {
        this.AuthorisedYesNo = AuthorisedYesNo;
    }

    public void setAuthorisedBy(String authorisedBy) {
        this.authorisedBy = authorisedBy;
    }

  
    public void setAuthorisedByDate(String authorisedByDate) {
        this.authorisedByDate = authorisedByDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(long poNumber) {
        this.poNumber = poNumber;
    }
   
    
    
}
