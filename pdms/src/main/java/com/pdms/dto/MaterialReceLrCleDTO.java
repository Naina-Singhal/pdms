/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class MaterialReceLrCleDTO implements Serializable{
    
    private long clearanceMaReId;
    private long poNumBer;
    private long lrNumberr;
    private String lrDatee;
    private String lrClearanceDate;

    public long getClearanceMaReId() {
        return clearanceMaReId;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public long getLrNumberr() {
        return lrNumberr;
    }

    public String getLrDatee() {
        return lrDatee;
    }

    public String getLrClearanceDate() {
        return lrClearanceDate;
    }

    public void setClearanceMaReId(long clearanceMaReId) {
        this.clearanceMaReId = clearanceMaReId;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setLrNumberr(long lrNumberr) {
        this.lrNumberr = lrNumberr;
    }

    public void setLrDatee(String lrDatee) {
        this.lrDatee = lrDatee;
    }

    public void setLrClearanceDate(String lrClearanceDate) {
        this.lrClearanceDate = lrClearanceDate;
    }
    
    
    
}
