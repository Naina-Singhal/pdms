/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dao.daoImpl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.despatch.dto.DispatchEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dto.RtgsDTO;
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
public class DispatchEntryDAOImpl {
    
    private static final Logger logger = Logger.getLogger(DispatchEntryDAOImpl.class);

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
    public DispatchEntryDAOImpl(){
        
    }
    
    
    public int insertDispatchEnDetail(final List<DispatchEntryDTO> disObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO dispatch_Entry_details  ");
            sb.append("(dispatchEnId,fileNumber,purpose,reference,type,");  
            sb.append("receiver,remarks,reasonFrSend,sendDate,session_id,disEn_date )");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?)");
            
            for(DispatchEntryDTO disDto : disObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{disDto.getDispatchEnId(), 
                        disDto.getFileNumber(),
                        disDto.getPurpose().trim(), 
                        disDto.getReference().trim(),
                        disDto.getType().trim(),
                        disDto.getReceiver().trim(),
                        disDto.getRemarks().trim(),                        
                        disDto.getReasonFrSend().trim(),
                        disDto.getSendDate().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertDispatchEnDetail:",e);
        }
        return retVal;
    }
    
    public List<DispatchEntryDTO> getFileMovementRecord() throws AppException {
        List<DispatchEntryDTO> Dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dispatch_Entry_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DispatchEntryDTO indto = new DispatchEntryDTO();
                    indto.setDispatchEnId((Long) (retMap.get("dispatchEnId")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber")));
                    indto.setPurpose((String) (retMap.get("purpose"))); 
                    indto.setReference((String) (retMap.get("reference")));
                    indto.setType((String) (retMap.get("type")));
                    indto.setReceiver((String) (retMap.get("receiver")));
                    indto.setRemarks((String) (retMap.get("remarks")));
                    indto.setReasonFrSend((String) (retMap.get("reasonFrSend")));
                    indto.setSendDate((String) (retMap.get("sendDate")));
                    Dto.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getFileMovementRecord:", e);
        }
        return Dto;
    }
    
    public List<DispatchEntryDTO> getFileMovementReById(final long id) throws AppException {
        List<DispatchEntryDTO> dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dispatch_Entry_details WHERE dispatchEnId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DispatchEntryDTO indto = new DispatchEntryDTO();
                    indto.setDispatchEnId((Long) (retMap.get("dispatchEnId")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber")));
                    indto.setPurpose((String) (retMap.get("purpose"))); 
                    indto.setReference((String) (retMap.get("reference")));
                    indto.setType((String) (retMap.get("type")));
                    indto.setReceiver((String) (retMap.get("receiver")));
                    indto.setRemarks((String) (retMap.get("remarks")));
                    indto.setReasonFrSend((String) (retMap.get("reasonFrSend")));
                    indto.setSendDate((String) (retMap.get("sendDate")));
                    dto.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getFileMovementReById:", e);
        }
        return dto;
    }
    
    public int updateFileMovementDetail(final List<DispatchEntryDTO> disObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE dispatch_Entry_details  ");
            sb.append(" SET fileNumber=?,purpose=?,reference=?,type=?,");  
            sb.append("receiver=?,remarks=?,reasonFrSend=?,sendDate=?,session_id=?,disEn_date=? ");
            sb.append(" WHERE dispatchEnId=? ");
            
            for(DispatchEntryDTO disDto : disObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        disDto.getFileNumber(),
                        disDto.getPurpose().trim(), 
                        disDto.getReference().trim(),
                        disDto.getType().trim(),
                        disDto.getReceiver().trim(),
                        disDto.getRemarks().trim(),                        
                        disDto.getReasonFrSend().trim(),
                        disDto.getSendDate().trim(),                           
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        disDto.getDispatchEnId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateFileMovementDetail:",e);
        }
        return retVal;
    }
    
    
}
