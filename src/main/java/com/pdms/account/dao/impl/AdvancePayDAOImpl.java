/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.AdvancePayDTO;
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
public class AdvancePayDAOImpl {
    private static final Logger logger = Logger.getLogger(AdvancePayDAOImpl.class);

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
    public AdvancePayDAOImpl(){
        
    }
    
    
    public int insertAdvancePayDaEntry(final List<AdvancePayDTO> advaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO advance_pay_details  ");
            sb.append("(avancePayId,pprNo,poNumber,vname,dop,amountPaid,advanceAdj,");            
            sb.append("balancePaid,balanceDue,totalDue,clDate,qtyPaid,recovery,");
            sb.append("remarks,posr,session_id,advance_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?)");            
            
            for(AdvancePayDTO advObj : advaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{advObj.getAvancePayId(),
                            advObj.getPprNo(),
                            advObj.getPoNumber(),
                            advObj.getVname().trim(),
                            advObj.getDop().trim(),
                            advObj.getAmountPaid(),
                            advObj.getAdvanceAdj().trim(),
                            advObj.getBalancePaid(),
                            advObj.getBalanceDue(),
                            advObj.getTotalDue(),
                            advObj.getClDate().trim(),                                                      
                            advObj.getQtyPaid(),
                            advObj.getRecovery().trim(),
                            advObj.getRemarks().trim(),
                            advObj.getPosr().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertAdvancePayDaEntry:",e);
        }
        return retVal;
    }
    
    public List<AdvancePayDTO> getAdvancePayDaRecord() throws AppException {
        List<AdvancePayDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM advance_pay_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    AdvancePayDTO dTO = new AdvancePayDTO();
                    dTO.setAvancePayId((Long) (retMap.get("avancePayId")));
                    dTO.setPprNo((Long) (retMap.get("pprNo")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setVname((String) (retMap.get("vname")));
                    dTO.setDop((String) (retMap.get("dop")));  
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid")));
                    dTO.setAdvanceAdj((String) (retMap.get("advanceAdj")));
                    dTO.setBalancePaid((BigDecimal) (retMap.get("balancePaid")));
                    dTO.setBalanceDue((BigDecimal) (retMap.get("balanceDue")));
                    dTO.setTotalDue((BigDecimal) (retMap.get("totalDue")));
                    dTO.setClDate((String) (retMap.get("clDate")));                                   
                    dTO.setQtyPaid((String) (retMap.get("qtyPaid")));
                    dTO.setRecovery((String) (retMap.get("recovery")));
                    dTO.setRemarks((String) (retMap.get("remarks")));  
                    dTO.setPosr((String) (retMap.get("posr"))); 
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAdvancePaymentDataRecord:", e);
        }
        return list;
    }
   
    public List<AdvancePayDTO> getAdvancePayReById(final long id) throws AppException {
        List<AdvancePayDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM advance_pay_details WHERE avancePayId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    AdvancePayDTO dTO = new AdvancePayDTO();
                    dTO.setAvancePayId((Long) (retMap.get("avancePayId")));
                    dTO.setPprNo((Long) (retMap.get("pprNo")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setVname((String) (retMap.get("vname")));
                    dTO.setDop((String) (retMap.get("dop")));  
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid")));
                    dTO.setAdvanceAdj((String) (retMap.get("advanceAdj")));
                    dTO.setBalancePaid((BigDecimal) (retMap.get("balancePaid")));
                    dTO.setBalanceDue((BigDecimal) (retMap.get("balanceDue")));
                    dTO.setTotalDue((BigDecimal) (retMap.get("totalDue")));
                    dTO.setClDate((String) (retMap.get("clDate")));                                   
                    dTO.setQtyPaid((String) (retMap.get("qtyPaid")));
                    dTO.setRecovery((String) (retMap.get("recovery")));
                    dTO.setRemarks((String) (retMap.get("remarks")));
                    dTO.setPosr((String) (retMap.get("posr")));                     
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAdvancePayReById:", e);
        }
        return list;
    }
    
    public int updateAdvancePayDetail(final List<AdvancePayDTO> advaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE advance_pay_details  ");
            sb.append(" SET pprNo=?,poNumber=?,vname=?,dop=?,amountPaid=?,advanceAdj=?,");  
            sb.append(" balancePaid=?,balanceDue=?,totalDue=?,clDate=?,qtyPaid=?,recovery=?, ");
            sb.append(" remarks=?,posr=?,session_id=?,advance_date=? ");
            sb.append(" WHERE avancePayId=? ");
            
            for(AdvancePayDTO advObj : advaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                            advObj.getPprNo(),
                            advObj.getPoNumber(),
                            advObj.getVname().trim(),
                            advObj.getDop().trim(),
                            advObj.getAmountPaid(),
                            advObj.getAdvanceAdj().trim(),
                            advObj.getBalancePaid(),
                            advObj.getBalanceDue(),
                            advObj.getTotalDue(),
                            advObj.getClDate().trim(),                                                      
                            advObj.getQtyPaid(),
                            advObj.getRecovery().trim(),
                            advObj.getRemarks().trim(),
                            advObj.getPosr().trim(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            advObj.getAvancePayId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateAdvancePayDetail:",e);
        }
        return retVal;
    }
   
}
