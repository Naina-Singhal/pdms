/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class MaterialReceItemDTO extends BaseDTO implements Serializable{
    
    private long mateReceItemId;
    private String itemCode;
    private String group;
    private String cardNo;
    private String unit;
    private String order;
    private String dispatched;
    private String accepted;
    private String updatedQty;

    public long getMateReceItemId() {
        return mateReceItemId;
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

    public String getDispatched() {
        return dispatched;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getUpdatedQty() {
        return updatedQty;
    }

    public void setMateReceItemId(long mateReceItemId) {
        this.mateReceItemId = mateReceItemId;
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

    public void setDispatched(String dispatched) {
        this.dispatched = dispatched;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setUpdatedQty(String updatedQty) {
        this.updatedQty = updatedQty;
    }
    
    
    
   
    
}
