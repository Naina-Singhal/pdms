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
public class SubMenuDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 5563501392047113642L;
    
    private long subMenuID;
    private String encSubMenuID;
    private long menuID;
    private String subMenuName;
    private String subMenuLink;
    private String description;
    private String insertFlag;
    private String menuName;

    /**
     * @return the subMenuID
     */
    public long getSubMenuID() {
        return subMenuID;
    }

    /**
     * @param subMenuID the subMenuID to set
     */
    public void setSubMenuID(long subMenuID) {
        this.subMenuID = subMenuID;
    }

    /**
     * @return the encSubMenuID
     */
    public String getEncSubMenuID() {
        return encSubMenuID;
    }

    /**
     * @param encSubMenuID the encSubMenuID to set
     */
    public void setEncSubMenuID(String encSubMenuID) {
        this.encSubMenuID = encSubMenuID;
    }

    /**
     * @return the menuID
     */
    public long getMenuID() {
        return menuID;
    }

    /**
     * @param menuID the menuID to set
     */
    public void setMenuID(long menuID) {
        this.menuID = menuID;
    }

    /**
     * @return the subMenuName
     */
    public String getSubMenuName() {
        return subMenuName;
    }

    /**
     * @param subMenuName the subMenuName to set
     */
    public void setSubMenuName(String subMenuName) {
        this.subMenuName = subMenuName;
    }

    /**
     * @return the subMenuLink
     */
    public String getSubMenuLink() {
        return subMenuLink;
    }

    /**
     * @param subMenuLink the subMenuLink to set
     */
    public void setSubMenuLink(String subMenuLink) {
        this.subMenuLink = subMenuLink;
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
     * @return the insertFlag
     */
    public String getInsertFlag() {
        return insertFlag;
    }

    /**
     * @param insertFlag the insertFlag to set
     */
    public void setInsertFlag(String insertFlag) {
        this.insertFlag = insertFlag;
    }

    /**
     * @return the menuName
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * @param menuName the menuName to set
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    
}
