/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.dto.MasterLookupDTO;
import com.pdms.exception.AppException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class MasterLookUpDAOImpl {
    
     private static final Logger logger = Logger.getLogger(MasterLookUpDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public MasterLookUpDAOImpl() {

    }

    /*
    Default Constructor 
     */
    
    
    public List<MasterLookupDTO> getMasterEntriesByLookUpType(final String lookUpType) throws AppException
    {
        List<MasterLookupDTO> masterLookUpLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT lookup_id,lookup_name ");
            sb.append(" FROM master_lookup m  ");
            sb.append(" WHERE status_id=3 AND lookup_type=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{lookUpType.trim()});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    MasterLookupDTO lookUpObj = new MasterLookupDTO();
                    
                    lookUpObj.setLookUpID((Long)(resultMap.get("lookup_id")));
                    lookUpObj.setLookUpName((String)(resultMap.get("lookup_name")));
                    
                    masterLookUpLi.add(lookUpObj);
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMasterEntriesByLookUpType:",e);
        }
        return masterLookUpLi;
    }
    
    
    public MasterLookupDTO getMasterLookUpByLookUpValueAndType(final String lookUpValue,final String lookUpType) throws AppException
    {
        MasterLookupDTO lookUpObj = new MasterLookupDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT lookup_id,lookup_name ");
            sb.append(" FROM master_lookup m  ");
            sb.append(" WHERE status_id=3 AND lookup_type=? AND lookup_name= ? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{lookUpType.trim(),lookUpValue.trim()});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    lookUpObj.setLookUpID((Long)(resultMap.get("lookup_id")));
                    lookUpObj.setLookUpName((String)(resultMap.get("lookup_name")));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMasterLookUpByLookUpValueAndType:",e);
        }
        return lookUpObj;
    }
    
    
    public long getMasterLookUpIDByLookUpValueAndType(final String lookUpType,final String lookUpValue) throws AppException
    {
        long retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT lookup_id ");
            sb.append(" FROM master_lookup m  ");
            sb.append(" WHERE status_id=3 AND lookup_type=? AND lookup_name= ? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{lookUpType.trim(),lookUpValue.trim()});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    retVal = (Long)(resultMap.get("lookup_id"));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMasterLookUpIDByLookUpValueAndType:",e);
        }
        return retVal;
    }
    
    public MasterLookupDTO getMasterLookUpByLookUpID(final long lookUpID) throws AppException
    {
        MasterLookupDTO lookUpObj = new MasterLookupDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT lookup_id,lookup_name ");
            sb.append(" FROM master_lookup m  ");
            sb.append(" WHERE status_id=3 AND lookup_id=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{lookUpID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    lookUpObj.setLookUpID((Long)(resultMap.get("lookup_id")));
                    lookUpObj.setLookUpName((String)(resultMap.get("lookup_name")));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMasterLookUpByLookUpID:",e);
        }
        return lookUpObj;
    }
}
