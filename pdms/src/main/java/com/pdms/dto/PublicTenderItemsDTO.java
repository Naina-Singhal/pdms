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
public class PublicTenderItemsDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    
    private long pTenderItemID;
    private ItemDTO itemObj = new ItemDTO();
    private String breifDesc;
    private long itemQty;
    private String itemUnits;
    private String itemName;
    private String itemCode;
    private long indSlNo;
    private ItemDTO itemDto = new ItemDTO();

    /**
     * @return the pTenderItemID
     */
    public long getpTenderItemID() {
        return pTenderItemID;
    }

    /**
     * @param pTenderItemID the pTenderItemID to set
     */
    public void setpTenderItemID(long pTenderItemID) {
        this.pTenderItemID = pTenderItemID;
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
     * @return the breifDesc
     */
    public String getBreifDesc() {
        return breifDesc;
    }

    /**
     * @param breifDesc the breifDesc to set
     */
    public void setBreifDesc(String breifDesc) {
        this.breifDesc = breifDesc;
    }

    /**
     * @return the itemQty
     */
    public long getItemQty() {
        return itemQty;
    }

    /**
     * @param itemQty the itemQty to set
     */
    public void setItemQty(long itemQty) {
        this.itemQty = itemQty;
    }

    /**
     * @return the itemUnits
     */
    public String getItemUnits() {
        return itemUnits;
    }

    /**
     * @param itemUnits the itemUnits to set
     */
    public void setItemUnits(String itemUnits) {
        this.itemUnits = itemUnits;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the itemCode to set
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public long getIndSlNo() {
        return indSlNo;
    }

    public void setIndSlNo(long indSlNo) {
        this.indSlNo = indSlNo;
    }

    public ItemDTO getItemDto() {
        return itemDto;
    }

    public void setItemDto(ItemDTO itemDto) {
        this.itemDto = itemDto;
    }


    
    
}
