/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.MaterialRequisitionDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class MaterialRequisitionTempDTO implements Serializable{
    
    private List<MaterialRequisitionDTO> requiDTO;
    private List<MaterialRequisitionItemsDTO> requiItemsDTO;

    public List<MaterialRequisitionDTO> getRequiDTO() {
        return requiDTO;
    }

    public List<MaterialRequisitionItemsDTO> getRequiItemsDTO() {
        return requiItemsDTO;
    }

    public void setRequiDTO(List<MaterialRequisitionDTO> requiDTO) {
        this.requiDTO = requiDTO;
    }

    public void setRequiItemsDTO(List<MaterialRequisitionItemsDTO> requiItemsDTO) {
        this.requiItemsDTO = requiItemsDTO;
    }

    
    
    
    
}
