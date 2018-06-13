/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.TypeDTO;
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
public class TypeMasterDAOImpl {
    
    private static final Logger logger = Logger.getLogger(TypeMasterDAOImpl.class);

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
    public TypeMasterDAOImpl(){
        
    }
    
    
    public int insertTypeMaster(final List<TypeDTO> typeObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO type_master_details  ");
            sb.append("(typeId,code,type,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");
            
            for(TypeDTO Obj : typeObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getTypeId(),
                        Obj.getCode().trim(),
                        Obj.getType().trim(),                                               
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertTypeMaster:",e);
        }
        return retVal;
    }
    
    public List<TypeDTO> getTypeMaRecord() throws AppException {
        List<TypeDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM type_master_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TypeDTO indto = new TypeDTO();
                    indto.setTypeId((Long) (retMap.get("typeId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setType((String) (retMap.get("type")));                                       
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTypeMaRecord:", e);
        }
        return enqDTO;
    }
    
    public List<TypeDTO> getTypeMaReById(final long id) throws AppException {
        List<TypeDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM type_master_details WHERE typeId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TypeDTO indto = new TypeDTO();
                    indto.setTypeId((Long) (retMap.get("typeId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setType((String) (retMap.get("type")));                   
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTypeMaReById:", e);
        }
        return enqDTO;
    }
    
    public int updateTypeMaDetail(final List<TypeDTO> typeObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE type_master_details  ");
            sb.append(" SET code=?,type=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE typeId=? ");
            
            for(TypeDTO obj : typeObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getCode().trim(),                        
                        obj.getType().trim(),                          
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getTypeId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateTypeMaDetail:",e);
        }
        return retVal;
    }
}
