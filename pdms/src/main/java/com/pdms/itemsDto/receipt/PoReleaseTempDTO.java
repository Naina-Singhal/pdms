/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.POrderEntryDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class PoReleaseTempDTO implements Serializable{
    
    private List<POrderEntryDTO> poReleaseDTO;
    private List<PoReleaseItemsDTO> poItemsDTO;

    public List<POrderEntryDTO> getPoReleaseDTO() {
        return poReleaseDTO;
    }

    public List<PoReleaseItemsDTO> getPoItemsDTO() {
        return poItemsDTO;
    }

    public void setPoReleaseDTO(List<POrderEntryDTO> poReleaseDTO) {
        this.poReleaseDTO = poReleaseDTO;
    }

    public void setPoItemsDTO(List<PoReleaseItemsDTO> poItemsDTO) {
        this.poItemsDTO = poItemsDTO;
    }

    
    
}
