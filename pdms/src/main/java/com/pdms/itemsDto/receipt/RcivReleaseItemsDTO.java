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
public class RcivReleaseItemsDTO implements Serializable{
        
    private long rcivReleaseItemId;
    private long poNumBerR;
    private long requisitionNoR;
    private String storeR;
    private String groupCodeR;
    private String storeCardNoR;
    private String itemR;
    private String itemDesR;
    private String unitR;
    private String requiredQtyR;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getRcivReleaseItemId() {
        return rcivReleaseItemId;
    }

   

    public long getRequisitionNoR() {
        return requisitionNoR;
    }

    public String getStoreR() {
        return storeR;
    }

    public String getGroupCodeR() {
        return groupCodeR;
    }

    public String getStoreCardNoR() {
        return storeCardNoR;
    }

    public String getItemR() {
        return itemR;
    }

    public String getItemDesR() {
        return itemDesR;
    }

    public String getUnitR() {
        return unitR;
    }

    public String getRequiredQtyR() {
        return requiredQtyR;
    }

    public ItemDTO getItemObj() {
        return itemObj;
    }

    public UnitDTO getUnitObj() {
        return unitObj;
    }

    public void setRcivReleaseItemId(long rcivReleaseItemId) {
        this.rcivReleaseItemId = rcivReleaseItemId;
    }


    public void setRequisitionNoR(long requisitionNoR) {
        this.requisitionNoR = requisitionNoR;
    }

    public void setStoreR(String storeR) {
        this.storeR = storeR;
    }

    public void setGroupCodeR(String groupCodeR) {
        this.groupCodeR = groupCodeR;
    }

    public void setStoreCardNoR(String storeCardNoR) {
        this.storeCardNoR = storeCardNoR;
    }

    public void setItemR(String itemR) {
        this.itemR = itemR;
    }

    public void setItemDesR(String itemDesR) {
        this.itemDesR = itemDesR;
    }

    public void setUnitR(String unitR) {
        this.unitR = unitR;
    }

    public void setRequiredQtyR(String requiredQtyR) {
        this.requiredQtyR = requiredQtyR;
    }

    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    public void setUnitObj(UnitDTO unitObj) {
        this.unitObj = unitObj;
    }

    public long getPoNumBerR() {
        return poNumBerR;
    }

    public void setPoNumBerR(long poNumBerR) {
        this.poNumBerR = poNumBerR;
    }
    
    
    
    
    
}
