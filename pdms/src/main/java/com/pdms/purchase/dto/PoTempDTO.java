/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.dto;

import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoDetailsDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author myassessment
 */
public class PoTempDTO implements Serializable{
    
    List<PoDetailsDTO> poObj;
    List<PoDeItemsDTO> poItemsObj;

    public List<PoDetailsDTO> getPoObj() {
        return poObj;
    }

    public List<PoDeItemsDTO> getPoItemsObj() {
        return poItemsObj;
    }

    public void setPoObj(List<PoDetailsDTO> poObj) {
        this.poObj = poObj;
    }

    public void setPoItemsObj(List<PoDeItemsDTO> poItemsObj) {
        this.poItemsObj = poItemsObj;
    }
    
    
    
}
