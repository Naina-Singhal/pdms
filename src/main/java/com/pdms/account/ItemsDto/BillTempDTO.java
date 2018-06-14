/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import com.pdms.account.dto.BillEntryDTO;
import com.pdms.account.dto.BillEntryItemsDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Phanivaranasi
 */
public class BillTempDTO implements Serializable{
    
    private List<BillEntryDTO> billEnDTO;
    private List<BillEntryItemsDTO> billItemsDTO;

    public List<BillEntryDTO> getBillEnDTO() {
        return billEnDTO;
    }

    public List<BillEntryItemsDTO> getBillItemsDTO() {
        return billItemsDTO;
    }

    public void setBillEnDTO(List<BillEntryDTO> billEnDTO) {
        this.billEnDTO = billEnDTO;
    }

    public void setBillItemsDTO(List<BillEntryItemsDTO> billItemsDTO) {
        this.billItemsDTO = billItemsDTO;
    }
    
    
    
}
