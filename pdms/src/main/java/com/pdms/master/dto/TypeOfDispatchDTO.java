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
public class TypeOfDispatchDTO implements Serializable{
    private long typeDispatchId;
    private String code;
    private String name;

    public long getTypeDispatchId() {
        return typeDispatchId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setTypeDispatchId(long typeDispatchId) {
        this.typeDispatchId = typeDispatchId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
