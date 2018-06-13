/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
public class ItemDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 5282552820637701302L;
    
    
    private long itemID;
    private String encItemID;
    private String itemName;
    private String itemCode;
    private String description;
    private CategoryDTO categoryDTO = new CategoryDTO();
    private long currentStock;
    private String requiredStock;
    private String exisItemCode;
    private UnitDTO unitDTO = new UnitDTO();
    private long categoryID;
    private long unitID;
    private long indentSlNo;

    /**
     * @return the itemID
     */
    public long getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the encItemID
     */
    public String getEncItemID() {
        return encItemID;
    }

    /**
     * @param encItemID the encItemID to set
     */
    public void setEncItemID(String encItemID) {
        this.encItemID = encItemID;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * @param itemCode the itemCode to set
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the categoryDTO
     */
    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    /**
     * @param categoryDTO the categoryDTO to set
     */
    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    /**
     * @return the currentStock
     */
    public long getCurrentStock() {
        return currentStock;
    }

    /**
     * @param currentStock the currentStock to set
     */
    public void setCurrentStock(long currentStock) {
        this.currentStock = currentStock;
    }

    /**
     * @return the requiredStock
     */
    public String getRequiredStock() {
        return requiredStock;
    }

    /**
     * @param requiredStock the requiredStock to set
     */
    public void setRequiredStock(String requiredStock) {
        this.requiredStock = requiredStock;
    }

    /**
     * @return the exisItemCode
     */
    public String getExisItemCode() {
        return exisItemCode;
    }

    /**
     * @param exisItemCode the exisItemCode to set
     */
    public void setExisItemCode(String exisItemCode) {
        this.exisItemCode = exisItemCode;
    }

    /**
     * @return the unitDTO
     */
    public UnitDTO getUnitDTO() {
        return unitDTO;
    }

    /**
     * @param unitDTO the unitDTO to set
     */
    public void setUnitDTO(UnitDTO unitDTO) {
        this.unitDTO = unitDTO;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public long getUnitID() {
        return unitID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    public void setUnitID(long unitID) {
        this.unitID = unitID;
    }

    public long getIndentSlNo() {
        return indentSlNo;
    }

    public void setIndentSlNo(long indentSlNo) {
        this.indentSlNo = indentSlNo;
    }
    
    
    
}
