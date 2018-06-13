/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public class PublicTenderDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private long pTenderID;
    private long tenderTypeID;
    private String fileNo;
    private String tenderCost;
    private long setsNo;
    private String saleLastDate;
    private String dueDate;
    private String openingDate;
    private String ewd;
    private long indentID;
    private String encIndentID;
    private String encTenderID;
    private String tenderType;
    
    private List<PublicTenderItemsDTO> tenderItemsLi = new LinkedList<>();
    
    private IndentFormDTO indentObj = new IndentFormDTO();
    
    private PublicTenderVendorDTO ptVendorObj = new PublicTenderVendorDTO();
    
    

    /**
     * @return the pTenderID
     */
    public long getpTenderID() {
        return pTenderID;
    }

    /**
     * @param pTenderID the pTenderID to set
     */
    public void setpTenderID(long pTenderID) {
        this.pTenderID = pTenderID;
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
     * @return the tenderCost
     */
    public String getTenderCost() {
        return tenderCost;
    }

    /**
     * @param tenderCost the tenderCost to set
     */
    public void setTenderCost(String tenderCost) {
        this.tenderCost = tenderCost;
    }

    /**
     * @return the setsNo
     */
    public long getSetsNo() {
        return setsNo;
    }

    /**
     * @param setsNo the setsNo to set
     */
    public void setSetsNo(long setsNo) {
        this.setsNo = setsNo;
    }

    /**
     * @return the saleLastDate
     */
    public String getSaleLastDate() {
        return saleLastDate;
    }

    /**
     * @param saleLastDate the saleLastDate to set
     */
    public void setSaleLastDate(String saleLastDate) {
        this.saleLastDate = saleLastDate;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    /**
     * @return the ewd
     */
    public String getEwd() {
        return ewd;
    }

    /**
     * @param ewd the ewd to set
     */
    public void setEwd(String ewd) {
        this.ewd = ewd;
    }

    /**
     * @return the tenderItemsLi
     */
    public List<PublicTenderItemsDTO> getTenderItemsLi() {
        return tenderItemsLi;
    }

    /**
     * @param tenderItemsLi the tenderItemsLi to set
     */
    public void setTenderItemsLi(List<PublicTenderItemsDTO> tenderItemsLi) {
        this.tenderItemsLi = tenderItemsLi;
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
     * @return the indentObj
     */
    public IndentFormDTO getIndentObj() {
        return indentObj;
    }

    /**
     * @param indentObj the indentObj to set
     */
    public void setIndentObj(IndentFormDTO indentObj) {
        this.indentObj = indentObj;
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
     * @return the ptVendorObj
     */
    public PublicTenderVendorDTO getPtVendorObj() {
        return ptVendorObj;
    }

    /**
     * @param ptVendorObj the ptVendorObj to set
     */
    public void setPtVendorObj(PublicTenderVendorDTO ptVendorObj) {
        this.ptVendorObj = ptVendorObj;
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
   

    /**
     * @param tenderDescription the tenderDescription to set
     */
    
    
    
}
