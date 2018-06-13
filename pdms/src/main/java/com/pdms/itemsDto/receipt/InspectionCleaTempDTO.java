/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.InspectionClearanceDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class InspectionCleaTempDTO implements Serializable{
    
    private List<InspectionClearanceDTO> inspecDTO;
    private List<InspectionCleaItemsDTO> inspecItemsDTO;

    public List<InspectionClearanceDTO> getInspecDTO() {
        return inspecDTO;
    }

    public List<InspectionCleaItemsDTO> getInspecItemsDTO() {
        return inspecItemsDTO;
    }

    public void setInspecDTO(List<InspectionClearanceDTO> inspecDTO) {
        this.inspecDTO = inspecDTO;
    }

    public void setInspecItemsDTO(List<InspectionCleaItemsDTO> inspecItemsDTO) {
        this.inspecItemsDTO = inspecItemsDTO;
    }
    
    
    
}
