/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import com.pdms.account.dto.VoucherDTO;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author myassessment
 */
public class VouTempDTO implements Serializable{
    private List<VoucherDTO> voucherDTO;
    private List<ItemsInVouDaEnDTO> itemsDTO;
    private List<HoaItemsInVouDaEnDTO>  hoaDTO;
    private List<VouDaEnBillsDTO> billsDTO;
    private List<RvVouDaEnItemsDTO> rvDTO;

    public List<VoucherDTO> getVoucherDTO() {
        return voucherDTO;
    }

    public List<ItemsInVouDaEnDTO> getItemsDTO() {
        return itemsDTO;
    }

    public List<HoaItemsInVouDaEnDTO> getHoaDTO() {
        return hoaDTO;
    }

    public void setVoucherDTO(List<VoucherDTO> voucherDTO) {
        this.voucherDTO = voucherDTO;
    }

    public void setItemsDTO(List<ItemsInVouDaEnDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public void setHoaDTO(List<HoaItemsInVouDaEnDTO> hoaDTO) {
        this.hoaDTO = hoaDTO;
    }

    public List<VouDaEnBillsDTO> getBillsDTO() {
        return billsDTO;
    }

    public void setBillsDTO(List<VouDaEnBillsDTO> billsDTO) {
        this.billsDTO = billsDTO;
    }

    public List<RvVouDaEnItemsDTO> getRvDTO() {
        return rvDTO;
    }

    public void setRvDTO(List<RvVouDaEnItemsDTO> rvDTO) {
        this.rvDTO = rvDTO;
    }

    
    
}
