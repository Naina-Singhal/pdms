/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author naagu
 */
@Repository
public class EnquiryMaDAOImpl {
    
     private static final Logger logger = Logger.getLogger(EnquiryMaDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DateUtil dateUtil;
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public EnquiryMaDAOImpl(){
        
    }
    
    
    public int insertEnquiryDetail(final List<EnquiryDTO> enqObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO enquiry_master_details  ");
            sb.append("(enquiryId,fileNo,vendor_name,enquireDate,dueDate,prepareDate, "); 
            sb.append("openDate,ifscCode,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?)");
            
            for(EnquiryDTO Obj : enqObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getEnquiryId(),
                        Obj.getFileNo(),
                        Obj.getVendorName().trim(),
                        Obj.getEnquireDate().trim(),
                        Obj.getDueDate().trim(),
                        Obj.getPrepareDate(),
                        Obj.getOpenDate(),
                        Obj.getIfscCode().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertEnquiryDetail:",e);
        }
        return retVal;
    }
    
    public List<EnquiryDTO> getEnquiryRecord() throws AppException {
        List<EnquiryDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM enquiry_master_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    EnquiryDTO indto = new EnquiryDTO();
                    indto.setEnquiryId((Long) (retMap.get("enquiryId")));
                    indto.setFileNo((Long) (retMap.get("fileNo")));
                    indto.setVendorName((String) (retMap.get("vendor_name")));
                    indto.setEnquireDate((String) (retMap.get("enquireDate")));
                    indto.setDueDate((String) (retMap.get("dueDate")));
                    indto.setPrepareDate((String) (retMap.get("prepareDate")));
                    indto.setOpenDate((String) (retMap.get("openDate")));
                    indto.setIfscCode((String) (retMap.get("ifscCode")));                    
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getEnquiryRecord:", e);
        }
        return enqDTO;
    }
    
    public List<EnquiryDTO> getEnquiryRecordById(final long id) throws AppException {
        List<EnquiryDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM enquiry_master_details WHERE enquiryId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    EnquiryDTO indto = new EnquiryDTO();
                    indto.setEnquiryId((Long) (retMap.get("enquiryId")));
                    indto.setFileNo((Long) (retMap.get("fileNo")));
                    indto.setVendorName((String) (retMap.get("vendor_name")));
                    indto.setEnquireDate((String) (retMap.get("enquireDate")));
                    indto.setDueDate((String) (retMap.get("dueDate")));
                    indto.setPrepareDate((String) (retMap.get("prepareDate")));
                    indto.setOpenDate((String) (retMap.get("openDate")));
                    indto.setIfscCode((String) (retMap.get("ifscCode")));                    
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getEnquiryRecordById:", e);
        }
        return enqDTO;
    }
    
    public int updateEnquiryDetail(final List<EnquiryDTO> enqObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE enquiry_master_details  ");
            sb.append(" SET fileNo=?,vendor_name=?,enquireDate=?,dueDate=?,prepareDate=?,openDate=?,ifscCode=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE enquiryId=? ");
            
            for(EnquiryDTO obj : enqObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getFileNo(),                        
                        obj.getVendorName().trim(),
                        obj.getEnquireDate().trim(),
                        obj.getDueDate().trim(),
                        obj.getPrepareDate(),
                        obj.getOpenDate(),
                        obj.getIfscCode().trim(),  
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getEnquiryId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateEnquiryDetail:",e);
        }
        return retVal;
    }
    
}
