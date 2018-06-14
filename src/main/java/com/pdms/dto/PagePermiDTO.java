/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author myassessment
 */
public class PagePermiDTO extends BaseDTO implements Serializable{
    
    private long id;
    private String page_id;
    private String page_name;
    private String page_group;

    public long getId() {
        return id;
    }

    public String getPage_id() {
        return page_id;
    }

    public String getPage_name() {
        return page_name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_group() {
        return page_group;
    }

    public void setPage_group(String page_group) {
        this.page_group = page_group;
    }
    
    
    
}
