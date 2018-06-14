/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public class MenuDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 4906142322305083315L;
    
    private long menuID;
    private String encMenuID;
    private String menuName;
    private String submenuflag;
    private String menuLink;
    private String insertFlag;
    private List<SubMenuDTO> subMenuList = new ArrayList<>();

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
     * @return the encMenuID
     */
    public String getEncMenuID() {
        return encMenuID;
    }

    /**
     * @param encMenuID the encMenuID to set
     */
    public void setEncMenuID(String encMenuID) {
        this.encMenuID = encMenuID;
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

    /**
     * @return the submenuflag
     */
    public String getSubmenuflag() {
        return submenuflag;
    }

    /**
     * @param submenuflag the submenuflag to set
     */
    public void setSubmenuflag(String submenuflag) {
        this.submenuflag = submenuflag;
    }

    /**
     * @return the menuLink
     */
    public String getMenuLink() {
        return menuLink;
    }

    /**
     * @param menuLink the menuLink to set
     */
    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
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
     * @return the subMenuList
     */
    public List<SubMenuDTO> getSubMenuList() {
        return subMenuList;
    }

    /**
     * @param subMenuList the subMenuList to set
     */
    public void setSubMenuList(List<SubMenuDTO> subMenuList) {
        this.subMenuList = subMenuList;
    }
    
}
