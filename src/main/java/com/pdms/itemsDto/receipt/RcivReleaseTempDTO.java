/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.RcivReleaseDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class RcivReleaseTempDTO implements Serializable{
    
    private List<RcivReleaseItemsDTO> releaseItemsDTO;
    private List<RcivReleaseDTO> releaseDTO;

    public List<RcivReleaseItemsDTO> getReleaseItemsDTO() {
        return releaseItemsDTO;
    }

    public List<RcivReleaseDTO> getReleaseDTO() {
        return releaseDTO;
    }

    public void setReleaseItemsDTO(List<RcivReleaseItemsDTO> releaseItemsDTO) {
        this.releaseItemsDTO = releaseItemsDTO;
    }

    public void setReleaseDTO(List<RcivReleaseDTO> releaseDTO) {
        this.releaseDTO = releaseDTO;
    }

    
    
   
    
}
