/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.CSRVpreparationDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class CsrvPreparationTempDTO implements Serializable{
    
    private List<CSRVpreparationDTO> csrvDTO;
    private List<CsrvPreparationItemsDTO> csrvItemsDTO;

    public void setCsrvDTO(List<CSRVpreparationDTO> csrvDTO) {
        this.csrvDTO = csrvDTO;
    }

    public void setCsrvItemsDTO(List<CsrvPreparationItemsDTO> csrvItemsDTO) {
        this.csrvItemsDTO = csrvItemsDTO;
    }

    public List<CSRVpreparationDTO> getCsrvDTO() {
        return csrvDTO;
    }

    public List<CsrvPreparationItemsDTO> getCsrvItemsDTO() {
        return csrvItemsDTO;
    }
    
    
    
}
