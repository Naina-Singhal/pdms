/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class TypeDTO implements Serializable{
    private long typeId;
    private String code;
    private String type;

    public long getTypeId() {
        return typeId;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
