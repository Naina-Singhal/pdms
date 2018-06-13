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
public class TenderDTO  extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -1512758241583069238L;
    
    private long tenderID;
    private String encTenderID;
    private long indentID;
    private String encIndentID;
    private long tenderTypeID;
    private String tenderType;
    private String tenderDescription;
    private String fileNo;
    private String tenderDetails;
    private long tenderEnvelopeTypeID;
    private String tenderEnvelopeType;
    private long contractTypeID;
    private String contractType;
    private long biddingTypeID;
    private String biddingType;
    private long submissionModeID;
    private String submissionMode;
    private double tenderCost;
    private String bidValidityPeriod;
    private long contractPeriod;
    private String paymentMode;
    private long paymentModeId;
    private double tenderFees;
    private String feesPayableToAt;
    private long emdFeesPaymentModeID;
    private String emdFeesPaymentMode;
    private double tenderEmd;
    private String placeOfDelivery;
    private String qualityRequirements;
    private String termsConditions;
    private long prebidMeetingModeID;
    private String prebidMeetingMode;
    private String prebidMeetingVenue;
    private String prebidStartDate;
    private String prebidEndDate;
    private String downloadStartDate;
    private String downloadEndDate;
    private String submissionLastDate;
    private String openingDate;

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
     * @return the encTenderID
     */
    public String getEncTenderID() {
        return encTenderID;
    }

    /**
     * @param encTenderID the encTenderID to set
     */
    public void setEncTenderID(String encTenderID) {
        this.encTenderID = encTenderID;
    }

    /**
     * @return the indentID
     */
    public long getIndentID() {
        return indentID;
    }

    /**
     * @param indentID the indentID to set
     */
    public void setIndentID(long indentID) {
        this.indentID = indentID;
    }

    /**
     * @return the encIndentID
     */
    public String getEncIndentID() {
        return encIndentID;
    }

    /**
     * @param encIndentID the encIndentID to set
     */
    public void setEncIndentID(String encIndentID) {
        this.encIndentID = encIndentID;
    }

    /**
     * @return the tenderTypeID
     */
    public long getTenderTypeID() {
        return tenderTypeID;
    }

    /**
     * @param tenderTypeID the tenderTypeID to set
     */
    public void setTenderTypeID(long tenderTypeID) {
        this.tenderTypeID = tenderTypeID;
    }

    /**
     * @return the tenderType
     */
    public String getTenderType() {
        return tenderType;
    }

    /**
     * @param tenderType the tenderType to set
     */
    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }

    /**
     * @return the tenderDescription
     */
    public String getTenderDescription() {
        return tenderDescription;
    }

    /**
     * @param tenderDescription the tenderDescription to set
     */
    public void setTenderDescription(String tenderDescription) {
        this.tenderDescription = tenderDescription;
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
     * @return the tenderDetails
     */
    public String getTenderDetails() {
        return tenderDetails;
    }

    /**
     * @param tenderDetails the tenderDetails to set
     */
    public void setTenderDetails(String tenderDetails) {
        this.tenderDetails = tenderDetails;
    }

    /**
     * @return the tenderEnvelopeTypeID
     */
    public long getTenderEnvelopeTypeID() {
        return tenderEnvelopeTypeID;
    }

    /**
     * @param tenderEnvelopeTypeID the tenderEnvelopeTypeID to set
     */
    public void setTenderEnvelopeTypeID(long tenderEnvelopeTypeID) {
        this.tenderEnvelopeTypeID = tenderEnvelopeTypeID;
    }

    /**
     * @return the tenderEnvelopeType
     */
    public String getTenderEnvelopeType() {
        return tenderEnvelopeType;
    }

    /**
     * @param tenderEnvelopeType the tenderEnvelopeType to set
     */
    public void setTenderEnvelopeType(String tenderEnvelopeType) {
        this.tenderEnvelopeType = tenderEnvelopeType;
    }

    /**
     * @return the contractTypeID
     */
    public long getContractTypeID() {
        return contractTypeID;
    }

    /**
     * @param contractTypeID the contractTypeID to set
     */
    public void setContractTypeID(long contractTypeID) {
        this.contractTypeID = contractTypeID;
    }

    /**
     * @return the contractType
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * @param contractType the contractType to set
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * @return the biddingTypeID
     */
    public long getBiddingTypeID() {
        return biddingTypeID;
    }

    /**
     * @param biddingTypeID the biddingTypeID to set
     */
    public void setBiddingTypeID(long biddingTypeID) {
        this.biddingTypeID = biddingTypeID;
    }

    /**
     * @return the biddingType
     */
    public String getBiddingType() {
        return biddingType;
    }

    /**
     * @param biddingType the biddingType to set
     */
    public void setBiddingType(String biddingType) {
        this.biddingType = biddingType;
    }

    /**
     * @return the submissionModeID
     */
    public long getSubmissionModeID() {
        return submissionModeID;
    }

    /**
     * @param submissionModeID the submissionModeID to set
     */
    public void setSubmissionModeID(long submissionModeID) {
        this.submissionModeID = submissionModeID;
    }

    /**
     * @return the submissionMode
     */
    public String getSubmissionMode() {
        return submissionMode;
    }

    /**
     * @param submissionMode the submissionMode to set
     */
    public void setSubmissionMode(String submissionMode) {
        this.submissionMode = submissionMode;
    }

    /**
     * @return the tenderCost
     */
    public double getTenderCost() {
        return tenderCost;
    }

    /**
     * @param tenderCost the tenderCost to set
     */
    public void setTenderCost(double tenderCost) {
        this.tenderCost = tenderCost;
    }

    /**
     * @return the bidValidityPeriod
     */
    public String getBidValidityPeriod() {
        return bidValidityPeriod;
    }

    /**
     * @param bidValidityPeriod the bidValidityPeriod to set
     */
    public void setBidValidityPeriod(String bidValidityPeriod) {
        this.bidValidityPeriod = bidValidityPeriod;
    }

    /**
     * @return the contractPeriod
     */
    public long getContractPeriod() {
        return contractPeriod;
    }

    /**
     * @param contractPeriod the contractPeriod to set
     */
    public void setContractPeriod(long contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    /**
     * @return the paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     * @param paymentMode the paymentMode to set
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * @return the paymentModeId
     */
    public long getPaymentModeId() {
        return paymentModeId;
    }

    /**
     * @param paymentModeId the paymentModeId to set
     */
    public void setPaymentModeId(long paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    /**
     * @return the tenderFees
     */
    public double getTenderFees() {
        return tenderFees;
    }

    /**
     * @param tenderFees the tenderFees to set
     */
    public void setTenderFees(double tenderFees) {
        this.tenderFees = tenderFees;
    }

    /**
     * @return the feesPayableToAt
     */
    public String getFeesPayableToAt() {
        return feesPayableToAt;
    }

    /**
     * @param feesPayableToAt the feesPayableToAt to set
     */
    public void setFeesPayableToAt(String feesPayableToAt) {
        this.feesPayableToAt = feesPayableToAt;
    }

    /**
     * @return the emdFeesPaymentModeID
     */
    public long getEmdFeesPaymentModeID() {
        return emdFeesPaymentModeID;
    }

    /**
     * @param emdFeesPaymentModeID the emdFeesPaymentModeID to set
     */
    public void setEmdFeesPaymentModeID(long emdFeesPaymentModeID) {
        this.emdFeesPaymentModeID = emdFeesPaymentModeID;
    }

    /**
     * @return the emdFeesPaymentMode
     */
    public String getEmdFeesPaymentMode() {
        return emdFeesPaymentMode;
    }

    /**
     * @param emdFeesPaymentMode the emdFeesPaymentMode to set
     */
    public void setEmdFeesPaymentMode(String emdFeesPaymentMode) {
        this.emdFeesPaymentMode = emdFeesPaymentMode;
    }

    /**
     * @return the tenderEmd
     */
    public double getTenderEmd() {
        return tenderEmd;
    }

    /**
     * @param tenderEmd the tenderEmd to set
     */
    public void setTenderEmd(double tenderEmd) {
        this.tenderEmd = tenderEmd;
    }

    /**
     * @return the placeOfDelivery
     */
    public String getPlaceOfDelivery() {
        return placeOfDelivery;
    }

    /**
     * @param placeOfDelivery the placeOfDelivery to set
     */
    public void setPlaceOfDelivery(String placeOfDelivery) {
        this.placeOfDelivery = placeOfDelivery;
    }

    /**
     * @return the qualityRequirements
     */
    public String getQualityRequirements() {
        return qualityRequirements;
    }

    /**
     * @param qualityRequirements the qualityRequirements to set
     */
    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    /**
     * @return the termsConditions
     */
    public String getTermsConditions() {
        return termsConditions;
    }

    /**
     * @param termsConditions the termsConditions to set
     */
    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    /**
     * @return the prebidMeetingModeID
     */
    public long getPrebidMeetingModeID() {
        return prebidMeetingModeID;
    }

    /**
     * @param prebidMeetingModeID the prebidMeetingModeID to set
     */
    public void setPrebidMeetingModeID(long prebidMeetingModeID) {
        this.prebidMeetingModeID = prebidMeetingModeID;
    }

    /**
     * @return the prebidMeetingMode
     */
    public String getPrebidMeetingMode() {
        return prebidMeetingMode;
    }

    /**
     * @param prebidMeetingMode the prebidMeetingMode to set
     */
    public void setPrebidMeetingMode(String prebidMeetingMode) {
        this.prebidMeetingMode = prebidMeetingMode;
    }

    /**
     * @return the prebidMeetingVenue
     */
    public String getPrebidMeetingVenue() {
        return prebidMeetingVenue;
    }

    /**
     * @param prebidMeetingVenue the prebidMeetingVenue to set
     */
    public void setPrebidMeetingVenue(String prebidMeetingVenue) {
        this.prebidMeetingVenue = prebidMeetingVenue;
    }

    /**
     * @return the prebidStartDate
     */
    public String getPrebidStartDate() {
        return prebidStartDate;
    }

    /**
     * @param prebidStartDate the prebidStartDate to set
     */
    public void setPrebidStartDate(String prebidStartDate) {
        this.prebidStartDate = prebidStartDate;
    }

    /**
     * @return the prebidEndDate
     */
    public String getPrebidEndDate() {
        return prebidEndDate;
    }

    /**
     * @param prebidEndDate the prebidEndDate to set
     */
    public void setPrebidEndDate(String prebidEndDate) {
        this.prebidEndDate = prebidEndDate;
    }

    /**
     * @return the downloadStartDate
     */
    public String getDownloadStartDate() {
        return downloadStartDate;
    }

    /**
     * @param downloadStartDate the downloadStartDate to set
     */
    public void setDownloadStartDate(String downloadStartDate) {
        this.downloadStartDate = downloadStartDate;
    }

    /**
     * @return the downloadEndDate
     */
    public String getDownloadEndDate() {
        return downloadEndDate;
    }

    /**
     * @param downloadEndDate the downloadEndDate to set
     */
    public void setDownloadEndDate(String downloadEndDate) {
        this.downloadEndDate = downloadEndDate;
    }

    /**
     * @return the submissionLastDate
     */
    public String getSubmissionLastDate() {
        return submissionLastDate;
    }

    /**
     * @param submissionLastDate the submissionLastDate to set
     */
    public void setSubmissionLastDate(String submissionLastDate) {
        this.submissionLastDate = submissionLastDate;
    }

    /**
     * @return the openingDate
     */
    public String getOpeningDate() {
        return openingDate;
    }

    /**
     * @param openingDate the openingDate to set
     */
    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }
    
    
    
    
    
}
