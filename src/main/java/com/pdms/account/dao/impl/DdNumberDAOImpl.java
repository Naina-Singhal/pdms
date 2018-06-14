/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.TypeOfDispatchDAOImpl;
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
public class DdNumberDAOImpl {
    
    private static final Logger logger = Logger.getLogger(DdNumberDAOImpl.class);

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
    public DdNumberDAOImpl(){
        
    }
    
    
    public int insertDdNumberDetails(final List<DdNumberDTO> ddObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO dd_number_details  ");
            sb.append("(ddNumberId,reqNumber,vendorname,vouNumber,voucherDate,");
            sb.append("amount,ddNumber,ddDate,session_id,cur_date)");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?)");
            
            for(DdNumberDTO Obj : ddObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getDdNumberId(),
                        Obj.getReqNumber(),
                        Obj.getVendorname().trim(),    
                        Obj.getVouNumber(), 
                        Obj.getVoucherDate().trim(), 
                        Obj.getAmount(), 
                        Obj.getDdNumber(), 
                        Obj.getDdDate().trim(), 
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertDdNumberDetails:",e);
        }
        return retVal;
    }
    
    public List<DdNumberDTO> getDdNumberRecord() throws AppException {
        List<DdNumberDTO> ddDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dd_number_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DdNumberDTO indto = new DdNumberDTO();
                    indto.setDdNumberId((Long) (retMap.get("ddNumberId")));
                    indto.setReqNumber((Long) (retMap.get("reqNumber")));
                    indto.setVendorname((String) (retMap.get("vendorname"))); 
                    indto.setVouNumber((Long) (retMap.get("vouNumber")));
                    indto.setVoucherDate((String) (retMap.get("voucherDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setDdNumber((Long) (retMap.get("ddNumber")));
                    indto.setDdDate((String) (retMap.get("ddDate")));
                    ddDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDdNumberRecord:", e);
        }
        return ddDTO;
    }
    
    public List<DdNumberDTO> getDdNumberReById(final long id) throws AppException {
        List<DdNumberDTO> ddDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dd_number_details WHERE ddNumberId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DdNumberDTO indto = new DdNumberDTO();
                    indto.setDdNumberId((Long) (retMap.get("ddNumberId")));
                    indto.setReqNumber((Long) (retMap.get("reqNumber")));
                    indto.setVendorname((String) (retMap.get("vendorname"))); 
                    indto.setVouNumber((Long) (retMap.get("vouNumber")));
                    indto.setVoucherDate((String) (retMap.get("voucherDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setDdNumber((Long) (retMap.get("ddNumber")));
                    indto.setDdDate((String) (retMap.get("ddDate")));
                    ddDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDdNumberReById:", e);
        }
        return ddDTO;
    }
    
    public int updateDdNumberDetail(final List<DdNumberDTO> ddObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE dd_number_details  ");
            sb.append(" SET reqNumber=?,vendorname=?,vouNumber=?,voucherDate=?,");  
            sb.append("amount=?,ddNumber=?,ddDate=?,session_id=?,cur_date=? ");
            sb.append(" WHERE ddNumberId=? ");
            
            for(DdNumberDTO Obj : ddObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        Obj.getReqNumber(),
                        Obj.getVendorname().trim(),    
                        Obj.getVouNumber(), 
                        Obj.getVoucherDate().trim(), 
                        Obj.getAmount(), 
                        Obj.getDdNumber(), 
                        Obj.getDdDate().trim(),                          
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getDdNumberId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateDdNumberDetail:",e);
        }
        return retVal;
    }
    
    
}
