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
 * @author myassessment
 */
public class TenderDocMaDTO extends BaseDTO implements Serializable{
    
    private long tenderDocID;
    private String tenderNo;
    private String tenderDate;
    private BigDecimal tenderAmt;
    private String noOfCopies;
    private String dateOfIssue;
    private String issueClose;
    private String dateOfOpen;
    private String fileNo;
    private String tenderreceFr;
    private String bDescription;

    public long getTenderDocID() {
        return tenderDocID;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public String getTenderDate() {
        return tenderDate;
    }

    public BigDecimal getTenderAmt() {
        return tenderAmt;
    }

    public String getNoOfCopies() {
        return noOfCopies;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public String getIssueClose() {
        return issueClose;
    }

    public String getDateOfOpen() {
        return dateOfOpen;
    }

    public String getFileNo() {
        return fileNo;
    }

    public String getTenderreceFr() {
        return tenderreceFr;
    }

    public String getbDescription() {
        return bDescription;
    }

    public void setTenderDocID(long tenderDocID) {
        this.tenderDocID = tenderDocID;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public void setTenderDate(String tenderDate) {
        this.tenderDate = tenderDate;
    }

    public void setTenderAmt(BigDecimal tenderAmt) {
        this.tenderAmt = tenderAmt;
    }

    public void setNoOfCopies(String noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public void setIssueClose(String issueClose) {
        this.issueClose = issueClose;
    }

    public void setDateOfOpen(String dateOfOpen) {
        this.dateOfOpen = dateOfOpen;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public void setTenderreceFr(String tenderreceFr) {
        this.tenderreceFr = tenderreceFr;
    }

    public void setbDescription(String bDescription) {
        this.bDescription = bDescription;
    }
    
    
    
}
