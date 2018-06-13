/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.ItemDTO;
import com.pdms.dto.UnitDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author ramakrishna
 */
public class PoReleaseItemsDTO implements Serializable{
   
    private long poReleaseItemsId;
    private long poNumBer;
    private String code;
    private String groupCode;
    private String storeCardNo;
    private String itemDes;
    private String qtyOrder;
    private String unitQty;
    private String lumpsum;
    private BigDecimal pAndf;
    private String excise;
    private String cst;
    private BigDecimal surcharge;
    private String turnOver;
    private String service;
    private String itemYetReq;
    private String poValue;
    private BigDecimal discount;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getPoReleaseItemsId() {
        return poReleaseItemsId;
    }

    public long getPoNumBer() {
        return poNumBer;
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

    public String getItemDes() {
        return itemDes;
    }

    public String getQtyOrder() {
        return qtyOrder;
    }

    public String getUnitQty() {
        return unitQty;
    }

    public String getLumpsum() {
        return lumpsum;
    }

    public BigDecimal getpAndf() {
        return pAndf;
    }

    public String getExcise() {
        return excise;
    }

    public String getCst() {
        return cst;
    }

    public BigDecimal getSurcharge() {
        return surcharge;
    }

    public String getTurnOver() {
        return turnOver;
    }

    public String getService() {
        return service;
    }

    public String getItemYetReq() {
        return itemYetReq;
    }

    public String getPoValue() {
        return poValue;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setPoReleaseItemsId(long poReleaseItemsId) {
        this.poReleaseItemsId = poReleaseItemsId;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
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

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public void setQtyOrder(String qtyOrder) {
        this.qtyOrder = qtyOrder;
    }

    public void setUnitQty(String unitQty) {
        this.unitQty = unitQty;
    }

    public void setLumpsum(String lumpsum) {
        this.lumpsum = lumpsum;
    }

    public void setpAndf(BigDecimal pAndf) {
        this.pAndf = pAndf;
    }

    public void setExcise(String excise) {
        this.excise = excise;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public void setSurcharge(BigDecimal surcharge) {
        this.surcharge = surcharge;
    }

    public void setTurnOver(String turnOver) {
        this.turnOver = turnOver;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setItemYetReq(String itemYetReq) {
        this.itemYetReq = itemYetReq;
    }

    public void setPoValue(String poValue) {
        this.poValue = poValue;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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
