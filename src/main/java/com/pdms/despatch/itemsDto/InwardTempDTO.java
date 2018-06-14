/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.itemsDto;

import com.pdms.despatch.dto.InwardDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Phanivaranasi
 */
public class InwardTempDTO implements Serializable{
    
    private List<InwardDTO> inwardDTO;
    private List<InwardPoItemsDTO> inwardPoDTO;
    private List<InwardVenItemsDTO> inwardVenDTO;

    public void setInwardDTO(List<InwardDTO> inwardDTO) {
        this.inwardDTO = inwardDTO;
    }

    public void setInwardPoDTO(List<InwardPoItemsDTO> inwardPoDTO) {
        this.inwardPoDTO = inwardPoDTO;
    }

    public List<InwardDTO> getInwardDTO() {
        return inwardDTO;
    }

    public List<InwardPoItemsDTO> getInwardPoDTO() {
        return inwardPoDTO;
    }

    public List<InwardVenItemsDTO> getInwardVenDTO() {
        return inwardVenDTO;
    }

    public void setInwardVenDTO(List<InwardVenItemsDTO> inwardVenDTO) {
        this.inwardVenDTO = inwardVenDTO;
    }
    
    
    
}
