/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.TaxDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author myassessment
 */
@Repository
public class TaxDAOImpl {
     private static final Logger logger = Logger.getLogger(TaxDAOImpl.class);

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
    public TaxDAOImpl(){
        
    }
    
    
    public int insertTaxDetail(final List<TaxDTO> taxObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO tax_master_details  ");
            sb.append("(taxId,taxCode,taxname,description, "); 
            sb.append("session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(TaxDTO Obj : taxObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getTaxId(),
                        Obj.getTaxCode().trim(),
                        Obj.getTaxname().trim(),
                        Obj.getDescription().trim(),                                         
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertTaxDetail:",e);
        }
        return retVal;
    }
    
    public List<TaxDTO> getTaxRecord() throws AppException {
        List<TaxDTO> taxDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM tax_master_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TaxDTO dto = new TaxDTO();
                    dto.setTaxId((Long) (retMap.get("taxId")));
                    dto.setTaxCode((String) (retMap.get("taxCode")));
                    dto.setTaxname((String) (retMap.get("taxname")));
                    dto.setDescription((String) (retMap.get("description")));                                        
                    taxDTO.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTaxRecord:", e);
        }
        return taxDTO;
    }
    
    public List<TaxDTO> getTaxRecordById(final long id) throws AppException {
        List<TaxDTO> DTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM tax_master_details WHERE taxId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TaxDTO indto = new TaxDTO();
                    indto.setTaxId((Long) (retMap.get("taxId")));
                    indto.setTaxCode((String) (retMap.get("taxCode")));
                    indto.setTaxname((String) (retMap.get("taxname")));
                    indto.setDescription((String) (retMap.get("description")));                    
                    DTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTaxRecordById:", e);
        }
        return DTO;
    }
    
    public int updateTaxDetail(final List<TaxDTO> taxObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE tax_master_details  ");
            sb.append(" SET taxCode=?,taxname=?,description=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE taxId=? ");
            
            for(TaxDTO obj : taxObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getTaxCode().trim(),                        
                        obj.getTaxname().trim(),
                        obj.getDescription().trim(),                         
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getTaxId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateTaxDetail:",e);
        }
        return retVal;
    }
    
    
}
