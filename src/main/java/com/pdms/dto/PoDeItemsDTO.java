/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author myassessment
 */
public class PoDeItemsDTO implements Serializable{
    private long poDeItemsId;
    private long poNum;
    private String itemCode;
    private String quantity;
    private String unit;
    private BigDecimal rate;
    private BigDecimal frieght;
    private BigDecimal pfCharges;
    private String salesTax;
    private String gst;
    private BigDecimal discount;
    private BigDecimal total;
    private ItemDTO itemObj = new ItemDTO();
    private UnitDTO unitObj = new UnitDTO();
    private long indentslno;

    public long getPoDeItemsId() {
        return poDeItemsId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getFrieght() {
        return frieght;
    }

    public BigDecimal getPfCharges() {
        return pfCharges;
    }

    public String getSalesTax() {
        return salesTax;
    }

    public String getGst() {
        return gst;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setPoDeItemsId(long poDeItemsId) {
        this.poDeItemsId = poDeItemsId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setFrieght(BigDecimal frieght) {
        this.frieght = frieght;
    }

    public void setPfCharges(BigDecimal pfCharges) {
        this.pfCharges = pfCharges;
    }

    public void setSalesTax(String salesTax) {
        this.salesTax = salesTax;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public long getPoNum() {
        return poNum;
    }

    public void setPoNum(long poNum) {
        this.poNum = poNum;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public long getIndentslno() {
        return indentslno;
    }

    public void setIndentslno(long indentslno) {
        this.indentslno = indentslno;
    }
    
    
}
