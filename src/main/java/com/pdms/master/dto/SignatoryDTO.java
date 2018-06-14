/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import com.pdms.dto.BaseDTO;
import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class SignatoryDTO extends BaseDTO implements Serializable{
    
    private long signatoryId;
    private String signatoryCode;
    private String signatoryName;
    private String signatoryDes;

    public long getSignatoryId() {
        return signatoryId;
    }

    public String getSignatoryCode() {
        return signatoryCode;
    }

    public String getSignatoryName() {
        return signatoryName;
    }

    public String getSignatoryDes() {
        return signatoryDes;
    }

    public void setSignatoryId(long signatoryId) {
        this.signatoryId = signatoryId;
    }

    public void setSignatoryCode(String signatoryCode) {
        this.signatoryCode = signatoryCode;
    }

    public void setSignatoryName(String signatoryName) {
        this.signatoryName = signatoryName;
    }

    public void setSignatoryDes(String signatoryDes) {
        this.signatoryDes = signatoryDes;
    }
    
    
}
