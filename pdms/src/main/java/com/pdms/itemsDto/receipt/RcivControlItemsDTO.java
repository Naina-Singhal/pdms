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
public class RcivControlItemsDTO implements Serializable{
    
    private long rcivContrItemId;
    private long conNumberC;
    private long requisitionNoC;
    private String storeC;
    private String groupCodeC;
    private String storeCardNoC;
    private String itemC;
    private String itemDesC;
    private String unitC;
    private String requiredQtyC;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getRcivContrItemId() {
        return rcivContrItemId;
    }


    public long getRequisitionNoC() {
        return requisitionNoC;
    }

    public String getStoreC() {
        return storeC;
    }

    public String getGroupCodeC() {
        return groupCodeC;
    }

    public String getStoreCardNoC() {
        return storeCardNoC;
    }

    public String getItemC() {
        return itemC;
    }

    public String getItemDesC() {
        return itemDesC;
    }

    public String getUnitC() {
        return unitC;
    }

    public String getRequiredQtyC() {
        return requiredQtyC;
    }

    public ItemDTO getItemObj() {
        return itemObj;
    }

    public UnitDTO getUnitObj() {
        return unitObj;
    }

    public void setRcivContrItemId(long rcivContrItemId) {
        this.rcivContrItemId = rcivContrItemId;
    }


    public void setRequisitionNoC(long requisitionNoC) {
        this.requisitionNoC = requisitionNoC;
    }

    public void setStoreC(String storeC) {
        this.storeC = storeC;
    }

    public void setGroupCodeC(String groupCodeC) {
        this.groupCodeC = groupCodeC;
    }

    public void setStoreCardNoC(String storeCardNoC) {
        this.storeCardNoC = storeCardNoC;
    }

    public void setItemC(String itemC) {
        this.itemC = itemC;
    }

    public void setItemDesC(String itemDesC) {
        this.itemDesC = itemDesC;
    }

    public void setUnitC(String unitC) {
        this.unitC = unitC;
    }

    public void setRequiredQtyC(String requiredQtyC) {
        this.requiredQtyC = requiredQtyC;
    }

    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    public void setUnitObj(UnitDTO unitObj) {
        this.unitObj = unitObj;
    }

    public long getConNumberC() {
        return conNumberC;
    }

    public void setConNumberC(long conNumberC) {
        this.conNumberC = conNumberC;
    }

   
    
    
    
    
    
}
