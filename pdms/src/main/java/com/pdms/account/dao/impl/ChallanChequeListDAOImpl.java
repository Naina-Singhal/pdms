/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.ChallanListDTO;
import com.pdms.account.dto.ChequeListDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author myassessment
 */
@Repository
public class ChallanChequeListDAOImpl {
    
    private static final Logger logger = Logger.getLogger(ChallanChequeListDAOImpl.class);

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
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public ChallanChequeListDAOImpl(){
        
    }
    
     public List<ChallanEnFrCashDTO> getOnlyChallnRecord(String fromDate, String toDate) throws AppException
    {
        List<ChallanEnFrCashDTO> chaList = new LinkedList<>();
        try
        {
            fromDate = dateUtil.dateConvertionFromJSPToDB(fromDate);
            toDate = dateUtil.dateConvertionFromJSPToDB(toDate);            
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT challanNo,challanDate,challanAmt ");
            sb.append(" FROM challan_cash_details WHERE (challanDate BETWEEN '"+fromDate+"' AND '"+toDate+"')");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {   
                    
                    ChallanEnFrCashDTO chaDTO = new ChallanEnFrCashDTO();
                    chaDTO.setChallanNo((long)(resultMap.get("challanNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("challanDate")));
                    chaDTO.setChallanDate((dateUtil.dateConvertionFromDBToJSP(indDate)));                    
                    chaDTO.setChallanAmt((BigDecimal)(resultMap.get("challanAmt")));
                    chaList.add(chaDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getOnlyChallnRecord:",e);
        }
        return chaList;
    }
     
    public List<VoucherNoDTO> getOnlyChequeReFrVouNo(String fromDate, String toDate) throws AppException
    {
        List<VoucherNoDTO> CheList = new LinkedList<>();
        try
        {
            fromDate = dateUtil.dateConvertionFromJSPToDB(fromDate);
            toDate = dateUtil.dateConvertionFromJSPToDB(toDate);            
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT chequeNo,chequeDate,chequeAmount ");
            sb.append(" FROM voucher_no_entry WHERE (chequeDate BETWEEN '"+fromDate+"' AND '"+toDate+"')");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {   
                    
                    VoucherNoDTO cheDTO = new VoucherNoDTO();
                    cheDTO.setChequeNo((String)(resultMap.get("chequeNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("chequeDate")));
                    cheDTO.setChequeDate((dateUtil.dateConvertionFromDBToJSP(indDate)));                    
                    cheDTO.setChequeAmount((BigDecimal)(resultMap.get("chequeAmount")));
                    CheList.add(cheDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getOnlyChequeReFrVouNo:",e);
        }
        return CheList;
    }
    
    
    public int insertChallanList(final List<ChallanListDTO> chaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO challan_list_details  ");
            sb.append("(challanListId,challanNum,challanDate,challanAmt,chaRecevDate,");                      
            sb.append("session_id,challan_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?)");            
            
            
            for (ChallanListDTO VDTO : chaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                        new Object[]{
                            VDTO.getChallanListId(),
                            VDTO.getChallanNum(),
                            dateUtil.dateConvertionFromJSPToDB(VDTO.getChallanDate().trim()),
                            VDTO.getChallanAmt(),
                            VDTO.getChaRecevDate().trim(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime()
                        });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertChallanList:",e);
        }
        return retVal;
    }
    
    
    public int insertChequeList(final List<ChequeListDTO> cheObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO cheque_list_details  ");
            sb.append("(chequeListId,chequeNo,chequeDate,chequeAmount,chequeRecevDate,");                      
            sb.append("session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?)");            
            
            
            for (ChequeListDTO cObj : cheObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                        new Object[]{
                            cObj.getChequeListId(),
                            cObj.getChequeNo(),
                            dateUtil.dateConvertionFromJSPToDB(cObj.getChequeDate().trim()),
                            cObj.getChequeAmount(),
                            cObj.getChequeRecevDate().trim(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime()
                        });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertChequeList:",e);
        }
        return retVal;
    }
}
