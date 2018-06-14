/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.RcivControlDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class RcivControlTempDTO implements Serializable{
    
    private List<RcivControlDTO> controlDTO;
    private List<RcivControlItemsDTO> controlItemsDTO;

    public List<RcivControlDTO> getControlDTO() {
        return controlDTO;
    }

    public List<RcivControlItemsDTO> getControlItemsDTO() {
        return controlItemsDTO;
    }

    public void setControlDTO(List<RcivControlDTO> controlDTO) {
        this.controlDTO = controlDTO;
    }

    public void setControlItemsDTO(List<RcivControlItemsDTO> controlItemsDTO) {
        this.controlItemsDTO = controlItemsDTO;
    }
    
    
    
}
