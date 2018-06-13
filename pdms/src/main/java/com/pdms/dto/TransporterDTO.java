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
public class TransporterDTO extends BaseDTO implements Serializable{
    
    private long transpId;
    private String transpName;
    private String transCode;
    private String transDes;

    public long getTranspId() {
        return transpId;
    }

    public String getTranspName() {
        return transpName;
    }

    public String getTransCode() {
        return transCode;
    }

    public String getTransDes() {
        return transDes;
    }

    public void setTranspId(long transpId) {
        this.transpId = transpId;
    }

    public void setTranspName(String transpName) {
        this.transpName = transpName;
    }

    public void setTransCode(String transCode) {
        this.transCode = transCode;
    }

    public void setTransDes(String transDes) {
        this.transDes = transDes;
    }
    
    
    
}
