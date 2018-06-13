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
public class IssueVouItemsDTO implements Serializable{
    
    private long issueVouItemId;
    private long ConNumber;
    private String group;
    private String cardNo;
    private String item;
    private String unit;
    private String qtyRqud;
    private String available;
    private String qtyIssue;
    private String remarks;
    private String upd;
    private String qtyBalance;
    private String qtyShort;
    private String waRate;
    private String lifoRate;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();

    public long getIssueVouItemId() {
        return issueVouItemId;
    }

    public long getConNumber() {
        return ConNumber;
    }

    public String getGroup() {
        return group;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getItem() {
        return item;
    }

    public String getUnit() {
        return unit;
    }

    public String getQtyRqud() {
        return qtyRqud;
    }

    public String getAvailable() {
        return available;
    }

    public String getQtyIssue() {
        return qtyIssue;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getUpd() {
        return upd;
    }

    public String getQtyBalance() {
        return qtyBalance;
    }

    public String getQtyShort() {
        return qtyShort;
    }

    public String getWaRate() {
        return waRate;
    }

    public String getLifoRate() {
        return lifoRate;
    }

    public ItemDTO getItemObj() {
        return itemObj;
    }

    public UnitDTO getUnitObj() {
        return unitObj;
    }

    public void setIssueVouItemId(long issueVouItemId) {
        this.issueVouItemId = issueVouItemId;
    }

    public void setConNumber(long ConNumber) {
        this.ConNumber = ConNumber;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setQtyRqud(String qtyRqud) {
        this.qtyRqud = qtyRqud;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setQtyIssue(String qtyIssue) {
        this.qtyIssue = qtyIssue;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setUpd(String upd) {
        this.upd = upd;
    }

    public void setQtyBalance(String qtyBalance) {
        this.qtyBalance = qtyBalance;
    }

    public void setQtyShort(String qtyShort) {
        this.qtyShort = qtyShort;
    }

    public void setWaRate(String waRate) {
        this.waRate = waRate;
    }

    public void setLifoRate(String lifoRate) {
        this.lifoRate = lifoRate;
    }

    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    public void setUnitObj(UnitDTO unitObj) {
        this.unitObj = unitObj;
    }
    
    
    
    
    
}
