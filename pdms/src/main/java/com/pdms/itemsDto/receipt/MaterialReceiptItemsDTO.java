/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.ItemDTO;
import com.pdms.dto.UnitDTO;
import java.io.Serializable;

/**
 *
 * @author ramakrishna
 */
public class MaterialReceiptItemsDTO implements Serializable{
    
    private long mateReceiptItemsId;
    private long dbNumBer;
    private String itemCode;
    private String group;
    private String cardNo;
    private String unit;
    private String order;
    private String dispatchQty;
    private String accepted;
    private String updataed;

    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();
    public long getMateReceiptItemsId() {
        return mateReceiptItemsId;
    }


    public String getItemCode() {
        return itemCode;
    }

    public String getGroup() {
        return group;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getUnit() {
        return unit;
    }

    public String getOrder() {
        return order;
    }

    public String getDispatchQty() {
        return dispatchQty;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getUpdataed() {
        return updataed;
    }

    public void setMateReceiptItemsId(long mateReceiptItemsId) {
        this.mateReceiptItemsId = mateReceiptItemsId;
    }


    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setDispatchQty(String dispatchQty) {
        this.dispatchQty = dispatchQty;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setUpdataed(String updataed) {
        this.updataed = updataed;
    }

    public long getDbNumBer() {
        return dbNumBer;
    }

    public void setDbNumBer(long dbNumBer) {
        this.dbNumBer = dbNumBer;
    }

    public ItemDTO getItemObj() {
        return itemObj;
    }

    public UnitDTO getUnitObj() {
        return unitObj;
    }

    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    public void setUnitObj(UnitDTO unitObj) {
        this.unitObj = unitObj;
    }
    
    
    
}
