/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.ReceiverMaDTO;
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
public class ReceiverMaDAOImpl {
     private static final Logger logger = Logger.getLogger(ReceiverMaDAOImpl.class);

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
    public ReceiverMaDAOImpl(){
        
    }
    
    
    public int insertReceiverDetail(final List<ReceiverMaDTO> recObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO receiver_master_details  ");
            sb.append("(receiverId,receiverCode,receiverName, "); 
            sb.append("session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");
            
            for(ReceiverMaDTO Obj : recObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getReceiverId(),
                        Obj.getReceiverCode().trim(),
                        Obj.getReceiverName().trim(),                                                
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertReceiverDetail:",e);
        }
        return retVal;
    }
    
    public List<ReceiverMaDTO> getReceiverRecord() throws AppException {
        List<ReceiverMaDTO> recDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM receiver_master_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ReceiverMaDTO indto = new ReceiverMaDTO();
                    indto.setReceiverId((Long) (retMap.get("receiverId")));
                    indto.setReceiverCode((String) (retMap.get("receiverCode")));   
                    indto.setReceiverName((String) (retMap.get("receiverName")));   
                    recDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getReceiverRecord:", e);
        }
        return recDTO;
    }
    
    public List<ReceiverMaDTO> getReceiverRecordById(final long id) throws AppException {
        List<ReceiverMaDTO> DTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM receiver_master_details WHERE receiverId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ReceiverMaDTO dto = new ReceiverMaDTO();
                    dto.setReceiverId((Long) (retMap.get("receiverId")));
                    dto.setReceiverCode((String) (retMap.get("receiverCode")));   
                    dto.setReceiverName((String) (retMap.get("receiverName")));                                     
                    DTO.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getReceiverRecordById:", e);
        }
        return DTO;
    }
    
    public int updateReceiverDetail(final List<ReceiverMaDTO> recObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE receiver_master_details  ");
            sb.append(" SET receiverCode=?,receiverName=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE receiverId=? ");
            
            for(ReceiverMaDTO obj : recObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getReceiverCode().trim(),                      
                        obj.getReceiverName().trim(),                         
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getReceiverId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateReceiverDetail:",e);
        }
        return retVal;
    }
    
    
}
