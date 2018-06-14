/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dto;

import com.pdms.master.dto.TypeOfDispatchDTO;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author naagu
 */
public class PostEntryDTO implements Serializable{
    
    private long postEntryId;
    private long dispatchNo;
    private long fileNo;
    private String group;
    private String nature;
    private BigDecimal amount;
    private String typeOfDispatch;
    private BigDecimal serviceTax;
    private BigDecimal total;
    private BigDecimal balance;
    private TypeOfDispatchDTO todsObj = new TypeOfDispatchDTO();

    public long getPostEntryId() {
        return postEntryId;
    }

    public long getDispatchNo() {
        return dispatchNo;
    }

    public long getFileNo() {
        return fileNo;
    }

    public String getGroup() {
        return group;
    }

    public String getNature() {
        return nature;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getTypeOfDispatch() {
        return typeOfDispatch;
    }

    public BigDecimal getServiceTax() {
        return serviceTax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setPostEntryId(long postEntryId) {
        this.postEntryId = postEntryId;
    }

    public void setDispatchNo(long dispatchNo) {
        this.dispatchNo = dispatchNo;
    }

    public void setFileNo(long fileNo) {
        this.fileNo = fileNo;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTypeOfDispatch(String typeOfDispatch) {
        this.typeOfDispatch = typeOfDispatch;
    }

    public void setServiceTax(BigDecimal serviceTax) {
        this.serviceTax = serviceTax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public TypeOfDispatchDTO getTodsObj() {
        return todsObj;
    }

    public void setTodsObj(TypeOfDispatchDTO todsObj) {
        this.todsObj = todsObj;
    }
    
    
    
}
