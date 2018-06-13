/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import java.io.Serializable;

/**
 *
 * @author ramakrishna
 */
public class MaterialRequisitionItemsDTO implements Serializable{
    
    private long mateRequiItemsId;
    private long requisitionNo;
    private long categoryId;
    private String store;
    private String groupCode;
    private String cardNo;
    private String item;
    private String itemDes;
    private String unit;
    private String qtyRged;
    private String qtyIssued;

    public long getMateRequiItemsId() {
        return mateRequiItemsId;
    }

    public long getRequisitionNo() {
        return requisitionNo;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getStore() {
        return store;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getItem() {
        return item;
    }

    public String getItemDes() {
        return itemDes;
    }

    public String getUnit() {
        return unit;
    }

    public String getQtyRged() {
        return qtyRged;
    }

    public String getQtyIssued() {
        return qtyIssued;
    }

    public void setMateRequiItemsId(long mateRequiItemsId) {
        this.mateRequiItemsId = mateRequiItemsId;
    }

    public void setRequisitionNo(long requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQtyRged(String qtyRged) {
        this.qtyRged = qtyRged;
    }

    public void setQtyIssued(String qtyIssued) {
        this.qtyIssued = qtyIssued;
    }
    
    
    
}
