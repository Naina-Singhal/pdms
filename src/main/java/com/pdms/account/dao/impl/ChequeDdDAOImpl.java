/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.ChequeDdEntryDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.TenderDocMaDTO;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author myassessment
 */
@Repository
public class ChequeDdDAOImpl {
     private static final Logger logger = Logger.getLogger(ChequeDdDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private TransactionTemplate transactionTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
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
    public ChequeDdDAOImpl(){
        
    }
    
    
    public int insertChequeDdEntry(final List<ChequeDdEntryDTO> ddObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    long fileDeduct = 0;
                    int retVal = 0;
                    int retValSb = 0;
                    int retValSbUp = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO cheque_dd_entry  ");
                        sb.append("(chequeDdId,receiptNo,date,supplierName,challanNo,challanDate,");
                        sb.append("month,challanYear,headOfAc,scode,");
                        sb.append("head,tag,ddDate,subHead,fileNo,");
                        sb.append("balDocument,balance,ddOrChequeNo,amount,letterDt,details,slNo,");
                        sb.append("session_id,check_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,");
                        sb.append("?,?,?,?,?,?,?,?,?,");
                        sb.append("?,?,?,?,?)");

                        StringBuilder sbUp = new StringBuilder();
                        sbUp.append(" UPDATE tender_document_details  ");
                        sbUp.append(" SET noOfCopies=? ");
                        sbUp.append(" WHERE fileNo=? ");

                        for (ChequeDdEntryDTO VDTO : ddObj) {
                            retValSb = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{VDTO.getChequeDdId(),
                                        VDTO.getReceiptNo(),
                                        VDTO.getDate().trim(),
                                        VDTO.getSupplierName().trim(),
                                        VDTO.getChallanNo(),
                                        VDTO.getChallanDate().trim(),
                                        VDTO.getMonth().trim(),
                                        VDTO.getChallanYear().trim(),
                                        VDTO.getHeadOfAc().trim(),
                                        VDTO.getScode().trim(),
                                        VDTO.getHead().trim(),
                                        VDTO.getTag().trim(),
                                        VDTO.getDdDate().trim(),
                                        VDTO.getSubHead().trim(),
                                        VDTO.getFileNo(),
                                        VDTO.getBalDocument().trim(),
                                        VDTO.getBalance(),
                                        VDTO.getDdOrChequeNo().trim(),
                                        VDTO.getAmount(),
                                        VDTO.getLetterDt().trim(),
                                        VDTO.getDetails().trim(),
                                        VDTO.getSlNo(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        for (ChequeDdEntryDTO SDTO : ddObj) {
                            fileDeduct = Long.parseLong(SDTO.getBalDocument()) - 1;                            
                            retValSbUp = getJdbcTemplate().update(sbUp.toString(),
                                    new Object[]{
                                        fileDeduct,
                                        SDTO.getFileNo()
                                    });
                        }

                        if ((retValSb > 0) && (retValSbUp > 0)) {
                            retVal = retValSb;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -5;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertEmployeeDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
            } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertEmployeeDetails:: " + e.getMessage());
        }


    }
    
     public List<ChequeDdEntryDTO> getChequeDdDaRecord() throws AppException {
        List<ChequeDdEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM cheque_dd_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ChequeDdEntryDTO dTO = new ChequeDdEntryDTO();
                    dTO.setChequeDdId((Long) (retMap.get("chequeDdId")));
                    dTO.setReceiptNo((Long) (retMap.get("receiptNo")));
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setChallanNo((Long) (retMap.get("challanNo")));  
                    dTO.setChallanDate((String) (retMap.get("challanDate")));
                    dTO.setMonth((String) (retMap.get("month")));
                    dTO.setChallanYear((String) (retMap.get("challanYear")));
                    dTO.setHeadOfAc((String) (retMap.get("headOfAc")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setHead((String) (retMap.get("head")));
                    dTO.setTag((String) (retMap.get("tag")));
                    dTO.setDdDate((String) (retMap.get("ddDate")));
                    dTO.setSubHead((String) (retMap.get("subHead")));
                    dTO.setFileNo((Long) (retMap.get("fileNo")));
                    dTO.setBalDocument((String) (retMap.get("balDocument")));                    
                    dTO.setBalance((BigDecimal) (retMap.get("balance")));
                    dTO.setDdOrChequeNo((String) (retMap.get("ddOrChequeNo")));
                    dTO.setAmount((BigDecimal) (retMap.get("amount")));
                    dTO.setLetterDt((String) (retMap.get("letterDt")));
                    dTO.setDetails((String) (retMap.get("details")));  
                    dTO.setSlNo((Long) (retMap.get("slNo"))); 
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChequeDdDaRecord:", e);
        }
        return list;
    }
   
    public long getReceiptNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM cheque_dd_entry WHERE receiptNo=(SELECT MAX(receiptNo) FROM cheque_dd_entry) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   InspectionClearanceDTO icdto = new InspectionClearanceDTO();
                    icdto.setGrOrrNumber((long)(resultMap.get("receiptNo")));
                    maxNo = (long)(resultMap.get("receiptNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getReceiptNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public long getChallanNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM cheque_dd_entry WHERE challanNo=(SELECT MAX(challanNo) FROM cheque_dd_entry) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   InspectionClearanceDTO icdto = new InspectionClearanceDTO();
                    icdto.setGrOrrNumber((long)(resultMap.get("challanNo")));
                    maxNo = (long)(resultMap.get("challanNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChallanNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public long getSlNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM cheque_dd_entry WHERE slNo=(SELECT MAX(slNo) FROM cheque_dd_entry) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   ChequeDdEntryDTO dto = new ChequeDdEntryDTO();
                    dto.setSlNo((long)(resultMap.get("slNo")));
                    maxNo = (long)(resultMap.get("slNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSlNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<ChequeDdEntryDTO> getChequeAndDdReById(final long id) throws AppException {
        List<ChequeDdEntryDTO> Lis = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM cheque_dd_entry WHERE chequeDdId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ChequeDdEntryDTO dTO = new ChequeDdEntryDTO();
                    dTO.setChequeDdId((Long) (retMap.get("chequeDdId")));
                    dTO.setReceiptNo((Long) (retMap.get("receiptNo")));
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setChallanNo((Long) (retMap.get("challanNo")));  
                    dTO.setChallanDate((String) (retMap.get("challanDate")));
                    dTO.setMonth((String) (retMap.get("month")));
                    dTO.setChallanYear((String) (retMap.get("challanYear")));
                    dTO.setHeadOfAc((String) (retMap.get("headOfAc")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setHead((String) (retMap.get("head")));
                    dTO.setTag((String) (retMap.get("tag")));
                    dTO.setDdDate((String) (retMap.get("ddDate")));
                    dTO.setSubHead((String) (retMap.get("subHead")));
                    dTO.setFileNo((Long) (retMap.get("fileNo")));
                    dTO.setBalDocument((String) (retMap.get("balDocument")));                    
                    dTO.setBalance((BigDecimal) (retMap.get("balance")));
                    dTO.setDdOrChequeNo((String) (retMap.get("ddOrChequeNo")));
                    dTO.setAmount((BigDecimal) (retMap.get("amount")));
                    dTO.setLetterDt((String) (retMap.get("letterDt")));
                    dTO.setDetails((String) (retMap.get("details")));  
                    dTO.setSlNo((Long) (retMap.get("slNo"))); 
                    Lis.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChequeAndDdReById:", e);
        }
        return Lis;
    }
    
    public int updateChequeAndDdDetail(final List<ChequeDdEntryDTO> ddObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE cheque_dd_entry  ");
            sb.append(" SET receiptNo=?,date=?,supplierName=?,challanNo=?,challanDate=?,month=?,");  
            sb.append("challanYear=?,headOfAc=?,scode=?,head=?,tag=?,ddDate=?, ");
            sb.append("subHead=?,fileNo=?,balDocument=?,balance=?,ddOrChequeNo=?,amount=?, ");
            sb.append("letterDt=?,details=?,slNo=?,session_id=?,check_date=?  ");
            sb.append(" WHERE chequeDdId=? ");
            
            for(ChequeDdEntryDTO VDTO : ddObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                        new Object[]{
                            VDTO.getReceiptNo(),
                            VDTO.getDate().trim(),
                            VDTO.getSupplierName().trim(),
                            VDTO.getChallanNo(),
                            VDTO.getChallanDate().trim(),
                            VDTO.getMonth().trim(),
                            VDTO.getChallanYear().trim(),
                            VDTO.getHeadOfAc().trim(),
                            VDTO.getScode().trim(),
                            VDTO.getHead().trim(),
                            VDTO.getTag().trim(),
                            VDTO.getDdDate().trim(),
                            VDTO.getSubHead().trim(),
                            VDTO.getFileNo(),
                            VDTO.getBalDocument().trim(),
                            VDTO.getBalance(),
                            VDTO.getDdOrChequeNo().trim(),
                            VDTO.getAmount(),
                            VDTO.getLetterDt().trim(),
                            VDTO.getDetails().trim(),
                            VDTO.getSlNo(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            VDTO.getChequeDdId()
                        });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateChequeAndDdDetail:",e);
        }
        return retVal;
    }
   
}
