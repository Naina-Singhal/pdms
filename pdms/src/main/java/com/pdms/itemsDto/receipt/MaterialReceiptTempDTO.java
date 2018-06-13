/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.itemsDto.receipt;

import com.pdms.dto.MaterialReceiptDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author ramakrishna
 */
public class MaterialReceiptTempDTO implements Serializable{
    
    private List<MaterialReceiptDTO> mateReceiptDTO;
    private List<MaterialReceiptItemsDTO> meteReceItemsDTO;

    public List<MaterialReceiptDTO> getMateReceiptDTO() {
        return mateReceiptDTO;
    }

    public List<MaterialReceiptItemsDTO> getMeteReceItemsDTO() {
        return meteReceItemsDTO;
    }

    public void setMateReceiptDTO(List<MaterialReceiptDTO> mateReceiptDTO) {
        this.mateReceiptDTO = mateReceiptDTO;
    }

    public void setMeteReceItemsDTO(List<MaterialReceiptItemsDTO> meteReceItemsDTO) {
        this.meteReceItemsDTO = meteReceItemsDTO;
    }
    
    
    
}
