/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.RcivAuthorisationDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class RcivAuthorisationTempDTO implements Serializable{
    
    private List<RcivAuthorisationDTO> authoDTO;
    private List<RcivAuthorisationItemsDTO> authoItemsDTO;

    public List<RcivAuthorisationDTO> getAuthoDTO() {
        return authoDTO;
    }

    public List<RcivAuthorisationItemsDTO> getAuthoItemsDTO() {
        return authoItemsDTO;
    }

    public void setAuthoDTO(List<RcivAuthorisationDTO> authoDTO) {
        this.authoDTO = authoDTO;
    }

    public void setAuthoItemsDTO(List<RcivAuthorisationItemsDTO> authoItemsDTO) {
        this.authoItemsDTO = authoItemsDTO;
    }
    
    
    
}
