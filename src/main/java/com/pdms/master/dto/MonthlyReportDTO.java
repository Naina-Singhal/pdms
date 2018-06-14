/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dto;

import java.io.Serializable;

/**
 *
 * @author naagu
 */
public class MonthlyReportDTO implements Serializable{
    private long monthlyReId;
    private String code;
    private String name;

    public long getMonthlyReId() {
        return monthlyReId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setMonthlyReId(long monthlyReId) {
        this.monthlyReId = monthlyReId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
