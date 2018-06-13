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
public class InspectionCleaItemsDTO implements Serializable{
    
    private long inspeCleItemsId;
    private long dbNumBer;
    private String code;
    private String groupCode;
    private String storeCardNo;
    private String item;
    private String unit;
    private String orderQty;
    private String dispatchQty;
    private String update;
    private String acceptedQty;
    private String rejectedQty;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getInspeCleItemsId() {
        return inspeCleItemsId;
    }

    public long getDbNumBer() {
        return dbNumBer;
    }

    public String getCode() {
        return code;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public String getStoreCardNo() {
        return storeCardNo;
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

    public String getDispatchQty() {
        return dispatchQty;
    }

    public String getUpdate() {
        return update;
    }

    public String getAcceptedQty() {
        return acceptedQty;
    }

    public String getRejectedQty() {
        return rejectedQty;
    }

    public void setInspeCleItemsId(long inspeCleItemsId) {
        this.inspeCleItemsId = inspeCleItemsId;
    }

    public void setDbNumBer(long dbNumBer) {
        this.dbNumBer = dbNumBer;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public void setStoreCardNo(String storeCardNo) {
        this.storeCardNo = storeCardNo;
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

    public void setDispatchQty(String dispatchQty) {
        this.dispatchQty = dispatchQty;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public void setAcceptedQty(String acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public void setRejectedQty(String rejectedQty) {
        this.rejectedQty = rejectedQty;
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
