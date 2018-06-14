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
public class VendorItemsDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = -9014372299496369548L;
    
    private long vendorItemID;
    private String encVendorItemID;
    private long vendorID;
    private long itemID;
    private long categoryID;
    private ItemDTO itemObj = new ItemDTO();
    private CategoryDTO categoryObj = new CategoryDTO();
    private String encVendorID;
    private String encIndentID;
    private List<Long> selItemList = new LinkedList<>();
    private List<String> selVendorList = new LinkedList<>();
    private long indentID;
    private List<Long> selOptionArr = new LinkedList<>();;
    private List<Long> indentIDArr = new LinkedList<>();;
    private List<Long> vendorIDArr = new LinkedList<>();;
    private long fileNo;
    private VendorDTO vendorObj = new VendorDTO();

    /**
     * @return the vendorItemID
     */
    public long getVendorItemID() {
        return vendorItemID;
    }

    /**
     * @param vendorItemID the vendorItemID to set
     */
    public void setVendorItemID(long vendorItemID) {
        this.vendorItemID = vendorItemID;
    }

    /**
     * @return the encVendorItemID
     */
    public String getEncVendorItemID() {
        return encVendorItemID;
    }

    /**
     * @param encVendorItemID the encVendorItemID to set
     */
    public void setEncVendorItemID(String encVendorItemID) {
        this.encVendorItemID = encVendorItemID;
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
     * @return the itemID
     */
    public long getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the categoryID
     */
    public long getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * @return the itemObj
     */
    public ItemDTO getItemObj() {
        return itemObj;
    }

    /**
     * @param itemObj the itemObj to set
     */
    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    /**
     * @return the categoryObj
     */
    public CategoryDTO getCategoryObj() {
        return categoryObj;
    }

    /**
     * @param categoryObj the categoryObj to set
     */
    public void setCategoryObj(CategoryDTO categoryObj) {
        this.categoryObj = categoryObj;
    }

    /**
     * @return the encVendorID
     */
    public String getEncVendorID() {
        return encVendorID;
    }

    /**
     * @param encVendorID the encVendorID to set
     */
    public void setEncVendorID(String encVendorID) {
        this.encVendorID = encVendorID;
    }

    /**
     * @return the selItemList
     */
    public List<Long> getSelItemList() {
        return selItemList;
    }

    /**
     * @param selItemList the selItemList to set
     */
    public void setSelItemList(List<Long> selItemList) {
        this.selItemList = selItemList;
    }

    /**
     * @return the selVendorList
     */
    public List<String> getSelVendorList() {
        return selVendorList;
    }

    /**
     * @param selVendorList the selVendorList to set
     */
    public void setSelVendorList(List<String> selVendorList) {
        this.selVendorList = selVendorList;
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
     * @return the selOptionArr
     */
    public List<Long> getSelOptionArr() {
        return selOptionArr;
    }

    /**
     * @param selOptionArr the selOptionArr to set
     */
    public void setSelOptionArr(List<Long> selOptionArr) {
        this.selOptionArr = selOptionArr;
    }

    /**
     * @return the indentIDArr
     */
    public List<Long> getIndentIDArr() {
        return indentIDArr;
    }

    /**
     * @param indentIDArr the indentIDArr to set
     */
    public void setIndentIDArr(List<Long> indentIDArr) {
        this.indentIDArr = indentIDArr;
    }

    /**
     * @return the vendorIDArr
     */
    public List<Long> getVendorIDArr() {
        return vendorIDArr;
    }

    /**
     * @param vendorIDArr the vendorIDArr to set
     */
    public void setVendorIDArr(List<Long> vendorIDArr) {
        this.vendorIDArr = vendorIDArr;
    }

    public long getFileNo() {
        return fileNo;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public VendorDTO getVendorObj() {
        return vendorObj;
    }

    public void setVendorObj(VendorDTO vendorObj) {
        this.vendorObj = vendorObj;
    }

    
    
}
