/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.NilVoucherDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
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
 * @author myassessment
 */
@Repository
public class NilVoucherDAOImpl {
     private static final Logger logger = Logger.getLogger(NilVoucherDAOImpl.class);

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
    public NilVoucherDAOImpl(){
        
    }
    
    
    public int insertNilVoucherDaEntry(final List<NilVoucherDTO> nilObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO nil_voucher_entry  ");
            sb.append("(nilVouId,pprNumber,posr,poNumber,amountPaid,dateOfPmt,billNumber,");            
            sb.append("billDate,rvNumber,rvDate,amtAdmitted,balanceDue,session_id,nil_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");            
            
            for(NilVoucherDTO ndto : nilObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ndto.getNilVouId(),
                            ndto.getPprNumber(),
                            ndto.getPosr(),
                            ndto.getPoNumber(),
                            ndto.getAmountPaid(),
                            ndto.getDateOfPmt().trim(),
                            ndto.getBillNumber(),
                            ndto.getBillDate().trim(),
                            ndto.getRvNumber(),
                            ndto.getRvDate().trim(), 
                            ndto.getAmtAdmitted(),
                            ndto.getBalanceDue(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertNilVoucherDaEntry:",e);
        }
        return retVal;
    }
   
    public List<NilVoucherDTO> getNilVoucherEnRecord() throws AppException {
        List<NilVoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM nil_voucher_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    NilVoucherDTO dTO = new NilVoucherDTO();
                    dTO.setNilVouId((Long) (retMap.get("nilVouId")));
                    dTO.setPprNumber((Long) (retMap.get("pprNumber")));
                    dTO.setPosr((Long) (retMap.get("posr")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid")));  
                    dTO.setDateOfPmt((String) (retMap.get("dateOfPmt")));
                    dTO.setBillNumber((Long) (retMap.get("billNumber")));
                    dTO.setBillDate((String) (retMap.get("billDate")));
                    dTO.setRvNumber((Long) (retMap.get("rvNumber")));
                    dTO.setRvDate((String) (retMap.get("rvDate")));   
                    dTO.setAmtAdmitted((BigDecimal) (retMap.get("amtAdmitted"))); 
                    dTO.setBalanceDue((BigDecimal) (retMap.get("balanceDue"))); 
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getNilVoucherEnRecord:", e);
        }
        return list;
    }
    
    public List<NilVoucherDTO> getNilVoucherReById(final long id) throws AppException {
        List<NilVoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM nil_voucher_entry WHERE nilVouId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    NilVoucherDTO dTO = new NilVoucherDTO();
                    dTO.setNilVouId((Long) (retMap.get("nilVouId")));
                    dTO.setPprNumber((Long) (retMap.get("pprNumber")));
                    dTO.setPosr((Long) (retMap.get("posr")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid")));  
                    dTO.setDateOfPmt((String) (retMap.get("dateOfPmt")));
                    dTO.setBillNumber((Long) (retMap.get("billNumber")));
                    dTO.setBillDate((String) (retMap.get("billDate")));
                    dTO.setRvNumber((Long) (retMap.get("rvNumber")));
                    dTO.setRvDate((String) (retMap.get("rvDate")));   
                    dTO.setAmtAdmitted((BigDecimal) (retMap.get("amtAdmitted"))); 
                    dTO.setBalanceDue((BigDecimal) (retMap.get("balanceDue"))); 
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getNilVoucherReById:", e);
        }
        return list;
    }
    
    public int updateNilVoucherDetail(final List<NilVoucherDTO> nilObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE nil_voucher_entry  ");
            sb.append(" SET pprNumber=?,posr=?,poNumber=?,amountPaid=?,");  
            sb.append("dateOfPmt=?,billNumber=?,billDate=?,rvNumber=?, ");
            sb.append("rvDate=?,amtAdmitted=?,balanceDue=?,session_id=?,nil_date=? ");
            sb.append(" WHERE nilVouId=? ");
            
            for(NilVoucherDTO ndto : nilObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        ndto.getPprNumber(),
                            ndto.getPosr(),
                            ndto.getPoNumber(),
                            ndto.getAmountPaid(),
                            ndto.getDateOfPmt().trim(),
                            ndto.getBillNumber(),
                            ndto.getBillDate().trim(),
                            ndto.getRvNumber(),
                            ndto.getRvDate().trim(), 
                            ndto.getAmtAdmitted(),
                            ndto.getBalanceDue(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            ndto.getNilVouId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateNilVoucherDetail:",e);
        }
        return retVal;
    }
   
}
