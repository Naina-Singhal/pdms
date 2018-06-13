/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.TypeOfDispatchDTO;
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
 * @author naagu
 */
@Repository
public class TypeOfDispatchDAOImpl {
    
     private static final Logger logger = Logger.getLogger(TypeOfDispatchDAOImpl.class);

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
    public TypeOfDispatchDAOImpl(){
        
    }
    
    
    public int insertDispatchMaster(final List<TypeOfDispatchDTO> disObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO dispatch_master_details  ");
            sb.append("(typeDispatchId,code,name,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");
            
            for(TypeOfDispatchDTO Obj : disObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getTypeDispatchId(),
                        Obj.getCode(),
                        Obj.getName().trim(),                                               
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertDispatchMaster:",e);
        }
        return retVal;
    }
    
    public List<TypeOfDispatchDTO> getDispatchMaRecord() throws AppException {
        List<TypeOfDispatchDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dispatch_master_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TypeOfDispatchDTO indto = new TypeOfDispatchDTO();
                    indto.setTypeDispatchId((Long) (retMap.get("typeDispatchId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setName((String) (retMap.get("name")));                                       
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDispatchMaRecord:", e);
        }
        return enqDTO;
    }
    
    public List<TypeOfDispatchDTO> getDispatchMaReById(final long id) throws AppException {
        List<TypeOfDispatchDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM dispatch_master_details WHERE typeDispatchId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TypeOfDispatchDTO indto = new TypeOfDispatchDTO();
                    indto.setTypeDispatchId((Long) (retMap.get("typeDispatchId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setName((String) (retMap.get("name")));                   
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDispatchMaReById:", e);
        }
        return enqDTO;
    }
    
    public int updateDispatchMaDetail(final List<TypeOfDispatchDTO> disObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE dispatch_master_details  ");
            sb.append(" SET code=?,name=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE typeDispatchId=? ");
            
            for(TypeOfDispatchDTO obj : disObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getCode().trim(),                        
                        obj.getName().trim(),                          
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getTypeDispatchId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateDispatchMaDetail:",e);
        }
        return retVal;
    }
    
    
}
