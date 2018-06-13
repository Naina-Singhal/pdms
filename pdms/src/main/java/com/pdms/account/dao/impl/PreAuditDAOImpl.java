/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.PreAuditFrDTO;
import com.pdms.account.dto.PreAuditSeUpDTO;
import com.pdms.despatch.dto.DispatchEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dto.RtgsDTO;
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
public class PreAuditDAOImpl {
    
    private static final Logger logger = Logger.getLogger(PreAuditDAOImpl.class);

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
    public PreAuditDAOImpl(){
        
    }
    
    
    public List<DispatchEntryDTO> getDispatchEnDetails() throws AppException {
        List<DispatchEntryDTO> DTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM dispatch_Entry_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DispatchEntryDTO Obj = new DispatchEntryDTO();
                    Obj.setDispatchEnId((Long) (retMap.get("dispatchEnId")));                    
                    Obj.setFileNumber((Long) (retMap.get("fileNumber")));
                    Obj.setPurpose((String) (retMap.get("purpose")));
                    Obj.setReference((String) (retMap.get("reference")));  
                    Obj.setType((String) (retMap.get("type")));
                    Obj.setSendDate((String) (retMap.get("sendDate")));
                    DTO.add(Obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDispatchEnDetails:", e);
        }
        return DTO;
    }
    
    public int insertPreAuditFrDetail(final List<PreAuditFrDTO> preObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO preaudit_details  ");
            sb.append("(preAuditId,fileNo,fileType,receivedOn,purpose,session_id,preaudit_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?)");
            
            for(PreAuditFrDTO prDto : preObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{prDto.getPreAuditId(),                                                
                        prDto.getFileNo(),
                        prDto.getFileType().trim(),
                        prDto.getReceivedOn().trim(), 
                        prDto.getPurpose().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPreAuditFrDetail:",e);
        }
        return retVal;
    }
    
    public List<PreAuditFrDTO> getPreAuditFrDetails() throws AppException {
        List<PreAuditFrDTO> Lis = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM preaudit_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PreAuditFrDTO Obj = new PreAuditFrDTO();
                    Obj.setPreAuditId((Long) (retMap.get("preAuditId")));
                    Obj.setFileNo((Long) (retMap.get("fileNo")));
                    Obj.setFileType((String) (retMap.get("fileType")));
                    Obj.setReceivedOn((String) (retMap.get("receivedOn")));
                    Obj.setPurpose((String) (retMap.get("purpose")));                    
                    Lis.add(Obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPreAuditFrDetails:", e);
        }
        return Lis;
    }
    
    public int insertPreAuditSeUpDetail(final List<PreAuditSeUpDTO> preseObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO preaud_seup_details  ");
            sb.append("(preAudSeUpId,receivedOn,sendDate,purpose,nod,fileNo,session_id,pre_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?)");
            
            for(PreAuditSeUpDTO seDto : preseObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{seDto.getPreAudSeUpId(),                                                
                        seDto.getReceivedOn().trim(),
                        seDto.getSendDate().trim(),
                        seDto.getPurpose().trim(), 
                        seDto.getNod().trim(),
                        seDto.getFileNo(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPreAuditSeUpDetail:",e);
        }
        return retVal;
    }
}
