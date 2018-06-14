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
public class RcivAuthorisationItemsDTO implements Serializable{
    private long rcivAuthoItemId;
    private long poNumBer;
    private long requisitionNo;
    private String store;
    private String groupCode;
    private String storeCardNo;
    private String item;
    private String itemDes;
    private String unit;
    private String requiredQty;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getRcivAuthoItemId() {
        return rcivAuthoItemId;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public long getRequisitionNo() {
        return requisitionNo;
    }

    public String getStore() {
        return store;
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

    public String getItemDes() {
        return itemDes;
    }

    public String getUnit() {
        return unit;
    }

    public String getRequiredQty() {
        return requiredQty;
    }

    public void setRcivAuthoItemId(long rcivAuthoItemId) {
        this.rcivAuthoItemId = rcivAuthoItemId;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setRequisitionNo(long requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public void setStore(String store) {
        this.store = store;
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

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRequiredQty(String requiredQty) {
        this.requiredQty = requiredQty;
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
