/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;


import com.pdms.account.dto.TransferEntryDTO;
import com.pdms.exception.AppException;
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

/**
 *
 * @author naagu
 */
@Repository
public class TransferEnScDAOImpl {
    private static final Logger logger = Logger.getLogger(TransferEnScDAOImpl.class);

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
    public TransferEnScDAOImpl(){
        
    }
    
    
    public int insertTransferEnScDetails(final List<TransferEntryDTO> traObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO transfer_entry_details  ");
            sb.append("(transferEntryId,options,vouChallanNo,voucherDate,amount,teYear,");
            sb.append("teNumber,hcodeNew,hoaNew,desNew,scodeNew,");
            sb.append("debitNew,creditNew,remarks,session_id,cur_date)");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(TransferEntryDTO Obj : traObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getTransferEntryId(),
                        Obj.getOptions().trim(),
                        Obj.getVouChallanNo(),
                        Obj.getVoucherDate().trim(),
                        Obj.getAmount(),
                        Obj.getTeYear().trim(),
                        Obj.getTeNumber(),
                        
                        Obj.getHcodeNew().trim(),
                        Obj.getHoaNew().trim(),    
                        Obj.getDesNew().trim(), 
                        Obj.getScodeNew().trim(), 
                        Obj.getDebitNew(), 
                        Obj.getCreditNew(), 
                        Obj.getRemarks().trim(), 
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertTransferEnScDetails:",e);
        }
        return retVal;
    }
    
    public List<TransferEntryDTO> getTransferEnScRecord() throws AppException {
        List<TransferEntryDTO> ddDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM transfer_entry_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TransferEntryDTO indto = new TransferEntryDTO();
                    indto.setTransferEntryId((Long) (retMap.get("transferEntryId")));
                    indto.setOptions((String) (retMap.get("options")));
                    indto.setVouChallanNo((Long) (retMap.get("vouChallanNo")));
                    indto.setVoucherDate((String) (retMap.get("voucherDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setTeYear((String) (retMap.get("teYear")));
                    indto.setTeNumber((Long) (retMap.get("teNumber")));
                    
                    indto.setHcodeNew((String) (retMap.get("hcodeNew")));
                    indto.setHoaNew((String) (retMap.get("hoaNew"))); 
                    indto.setDesNew((String) (retMap.get("desNew")));
                    indto.setScodeNew((String) (retMap.get("scodeNew")));
                    indto.setDebitNew((BigDecimal) (retMap.get("debitNew")));
                    indto.setCreditNew((BigDecimal) (retMap.get("creditNew")));
                    indto.setRemarks((String) (retMap.get("remarks")));
                    ddDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTransferEnScRecord:", e);
        }
        return ddDTO;
    }
    
    public List<TransferEntryDTO> getTransferEnScReById(final long id) throws AppException {
        List<TransferEntryDTO> traDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM transfer_entry_details WHERE transferEntryId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TransferEntryDTO indto = new TransferEntryDTO();
                    indto.setTransferEntryId((Long) (retMap.get("transferEntryId")));
                    indto.setOptions((String) (retMap.get("options")));
                    indto.setVouChallanNo((Long) (retMap.get("vouChallanNo")));
                    indto.setVoucherDate((String) (retMap.get("voucherDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setTeYear((String) (retMap.get("teYear")));
                    indto.setTeNumber((Long) (retMap.get("teNumber")));
                    
                    indto.setHcodeNew((String) (retMap.get("hcodeNew")));
                    indto.setHoaNew((String) (retMap.get("hoaNew"))); 
                    indto.setDesNew((String) (retMap.get("desNew")));
                    indto.setScodeNew((String) (retMap.get("scodeNew")));
                    indto.setDebitNew((BigDecimal) (retMap.get("debitNew")));
                    indto.setCreditNew((BigDecimal) (retMap.get("creditNew")));
                    indto.setRemarks((String) (retMap.get("remarks")));
                    traDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTransferEnScReById:", e);
        }
        return traDTO;
    }
    
    public int updateTransferEnScDetail(final List<TransferEntryDTO> traObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE transfer_entry_details  ");
            sb.append(" SET options=?,vouChallanNo=?,voucherDate=?,amount=?,teYear=?,teNumber=?,");  
            sb.append("hcodeNew=?,hoaNew=?,desNew=?,scodeNew=?,");
            sb.append("debitNew=?,creditNew=?,remarks=?,session_id=?,cur_date=? ");
            sb.append(" WHERE transferEntryId=? ");
            
            for(TransferEntryDTO Obj : traObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{     
                        Obj.getOptions().trim(),
                        Obj.getVouChallanNo(),
                        Obj.getVoucherDate().trim(),
                        Obj.getAmount(),
                        Obj.getTeYear().trim(),
                        Obj.getTeNumber(),
                        Obj.getHcodeNew().trim(),
                        Obj.getHoaNew().trim(),    
                        Obj.getDesNew().trim(), 
                        Obj.getScodeNew().trim(), 
                        Obj.getDebitNew(), 
                        Obj.getCreditNew(), 
                        Obj.getRemarks().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getTransferEntryId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateTransferEnScDetail:",e);
        }
        return retVal;
    }
    
    
    public long getTENumForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM transfer_entry_details WHERE teNumber=(SELECT MAX(teNumber) FROM transfer_entry_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   TransferEntryDTO icdto = new TransferEntryDTO();
                    icdto.setTeNumber((long)(resultMap.get("teNumber")));
                    maxNo = (long)(resultMap.get("teNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTENumForIncrement:",e);
        }
        return maxNo;
    }
    
     public List<TransferEntryDTO> getTransferEnPdfReById(final long id) throws AppException {
        List<TransferEntryDTO> traDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT a.vouChallanNo,a.voucherDate,a.amount,a.teNumber,v.voucherNo,v.poNumber,   ");
            sb.append(" v.chequeNo,v.chequeDate,h.hoaname,h.shortcode,h.creamt,h.debamt ");
            sb.append(" FROM transfer_entry_details a ");
            sb.append(" JOIN voucher_no_entry v ON a.vouChallanNo = v.voucherNo ");
            sb.append(" JOIN voucher_no_hoa_details h On v.poNumber = h.poNumBer ");
            sb.append(" WHERE a.transferEntryId=? ");
            sb.append(" ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TransferEntryDTO indto = new TransferEntryDTO();
                    
                    indto.setVouChallanNo((Long) (retMap.get("vouChallanNo")));
                    indto.setVoucherDate((String) (retMap.get("voucherDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));                    
                    indto.setTeNumber((Long) (retMap.get("teNumber")));
                    indto.getVouNoObj().setVoucherNo((String) (retMap.get("voucherNo")));
                    indto.getVouNoObj().setPoNumber((String) (retMap.get("poNumber")));
                    indto.getVouNoObj().setChequeNo((String) (retMap.get("chequeNo")));
                    String chgeDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("chequeDate")));
                    indto.getVouNoObj().setChequeDate((dateUtil.dateConvertionFromDBToJSP(chgeDate)));
                    indto.getVouNoHoaItems().setHoaname((String) (retMap.get("hoaname")));
                    indto.getVouNoHoaItems().setShortcode((String) (retMap.get("shortcode")));
                    indto.getVouNoHoaItems().setCreamt((BigDecimal) (retMap.get("creamt")));
                    indto.getVouNoHoaItems().setDebamt((BigDecimal) (retMap.get("debamt")));
                    traDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTransferEnPdfReById:", e);
        }
        return traDTO;
    }
    
}
