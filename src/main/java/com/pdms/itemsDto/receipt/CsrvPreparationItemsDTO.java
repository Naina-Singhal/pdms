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
public class CsrvPreparationItemsDTO implements Serializable{
    
    private long csrvPrepaItemId;
    private long dbNumBer;
    private long poNumberI;
    private String groupCode;
    private String storeCode;
    private String item;
    private String unit;
    private String orderQty;
    private String acceptedQty;
    private String balanceQty;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getCsrvPrepaItemId() {
        return csrvPrepaItemId;
    }

    public long getDbNumBer() {
        return dbNumBer;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getItem() {
        return item;
    }

    public String getUnit() {
        return unit;
    }

    public String getOrderQty() {
        return orderQty;
    }

    public String getAcceptedQty() {
        return acceptedQty;
    }

    public String getBalanceQty() {
        return balanceQty;
    }

    public void setCsrvPrepaItemId(long csrvPrepaItemId) {
        this.csrvPrepaItemId = csrvPrepaItemId;
    }

    public void setDbNumBer(long dbNumBer) {
        this.dbNumBer = dbNumBer;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setOrderQty(String orderQty) {
        this.orderQty = orderQty;
    }

    public void setAcceptedQty(String acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public void setBalanceQty(String balanceQty) {
        this.balanceQty = balanceQty;
    }

    public long getPoNumberI() {
        return poNumberI;
    }

    public void setPoNumberI(long poNumberI) {
        this.poNumberI = poNumberI;
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
