/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.ItemsDto;

import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.dto.VendorDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Phanivaranasi
 */
public class VouNoHoaItemsDTO implements Serializable{
    
    private long vouHoaItemId;
    private long compCode;
    private long poNumBer;
    private String hoaname;
    private String shortcode;
    private BigDecimal cbamt;
    private BigDecimal creamt;
    private BigDecimal debamt;
    
    private VoucherNoDTO vouObj = new VoucherNoDTO();
    private VendorDTO venObj = new VendorDTO();

    public long getVouHoaItemId() {
        return vouHoaItemId;
    }

    public long getCompCode() {
        return compCode;
    }

    public long getPoNumBer() {
        return poNumBer;
    }

    public String getHoaname() {
        return hoaname;
    }

    public String getShortcode() {
        return shortcode;
    }

    public BigDecimal getCbamt() {
        return cbamt;
    }

    public BigDecimal getCreamt() {
        return creamt;
    }

    public BigDecimal getDebamt() {
        return debamt;
    }

    public void setVouHoaItemId(long vouHoaItemId) {
        this.vouHoaItemId = vouHoaItemId;
    }

    public void setCompCode(long compCode) {
        this.compCode = compCode;
    }

    public void setPoNumBer(long poNumBer) {
        this.poNumBer = poNumBer;
    }

    public void setHoaname(String hoaname) {
        this.hoaname = hoaname;
    }

    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    public void setCbamt(BigDecimal cbamt) {
        this.cbamt = cbamt;
    }

    public void setCreamt(BigDecimal creamt) {
        this.creamt = creamt;
    }

    public void setDebamt(BigDecimal debamt) {
        this.debamt = debamt;
    }

    public VoucherNoDTO getVouObj() {
        return vouObj;
    }

    public VendorDTO getVenObj() {
        return venObj;
    }

    public void setVouObj(VoucherNoDTO vouObj) {
        this.vouObj = vouObj;
    }

    public void setVenObj(VendorDTO venObj) {
        this.venObj = venObj;
    }
    
    
    
    
    
}
