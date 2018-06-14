/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import com.pdms.account.dto.VoucherNoDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Phanivaranasi
 */
public class VouNoTempDTO implements Serializable{
    
    private List<VoucherNoDTO> voucherNoDTO;
    private List<VouNoHoaItemsDTO> hoaDTO;
    private List<OcbVouNoDTO> ocbDTO;

    public List<VoucherNoDTO> getVoucherNoDTO() {
        return voucherNoDTO;
    }

    public List<VouNoHoaItemsDTO> getHoaDTO() {
        return hoaDTO;
    }

    public void setVoucherNoDTO(List<VoucherNoDTO> voucherNoDTO) {
        this.voucherNoDTO = voucherNoDTO;
    }

    public void setHoaDTO(List<VouNoHoaItemsDTO> hoaDTO) {
        this.hoaDTO = hoaDTO;
    }

    public List<OcbVouNoDTO> getOcbDTO() {
        return ocbDTO;
    }

    public void setOcbDTO(List<OcbVouNoDTO> ocbDTO) {
        this.ocbDTO = ocbDTO;
    }
    
    
    
}
