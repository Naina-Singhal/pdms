/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
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
 * @author hpasupuleti
 */
@Repository
public class UnitDAOImpl {
    
    private static final Logger logger = Logger.getLogger(UnitDAOImpl.class);

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
    public UnitDAOImpl() {

    }

    /*
    Default Constructor 
     */
    
    
    public List<UnitDTO> getAllUnits() throws AppException
    {
        List<UnitDTO> unitLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT pk_unit_id,unit_name,unit_code,description ");
            sb.append(" FROM unit_master  ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    UnitDTO unitObj = new UnitDTO();
                    
                    unitObj.setUnitID((Long)(resultMap.get("pk_unit_id")));
                    unitObj.setUnitCode((String)(resultMap.get("unit_code")));
                    unitObj.setUnitName((String)(resultMap.get("unit_name")));
                    unitObj.setUnitDescription((String)(resultMap.get("description")));
                    unitObj.setEncFieldValue
                            (encryptDecrypt.encrypt(unitObj.getUnitID()+""));
                    
                    unitLi.add(unitObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllUnits:",e);
        }
        return unitLi;
    }
    
    public UnitDTO getSelectedUnit(final long unitID) throws AppException
    {
        UnitDTO unitObj = new UnitDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT pk_unit_id,unit_name,unit_code,description ");
            sb.append(" FROM unit_master WHERE  pk_unit_id=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{unitID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    
                    unitObj.setUnitID((Long)(resultMap.get("pk_unit_id")));
                    unitObj.setUnitCode((String)(resultMap.get("unit_code")));
                    unitObj.setUnitName((String)(resultMap.get("unit_name")));
                    unitObj.setUnitDescription((String)(resultMap.get("description")));
                    unitObj.setEncFieldValue
                            (encryptDecrypt.encrypt(unitObj.getUnitID()+""));
                    
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedUnit:",e);
        }
        return unitObj;
    }
    
    
    public int insertUnitDetail(final UnitDTO unitObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO unit_master  ");
            sb.append("(unit_name,unit_code,description,created_by,created_on) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");
            
            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{unitObj.getUnitName().trim(),
                        unitObj.getUnitCode().trim(),
                        unitObj.getUnitDescription().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    
    public int updateUnitDetail(final UnitDTO unitObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE unit_master SET  ");
            sb.append("unit_name=?,unit_code=?,description=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE pk_unit_id=? ");
            
            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{unitObj.getUnitName().trim(),
                        unitObj.getUnitCode().trim(),
                        unitObj.getUnitDescription().trim(),
                        //ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        unitObj.getUnitID()
                    });
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateUnit(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {

            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM unit_master ";
                qry+=" WHERE LOWER(unit_name) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM unit_master ";
                qry+=" WHERE LOWER(unit_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM unit_master ";
                qry+=" WHERE LOWER(unit_code)=? AND LOWER(unit_name) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase(),gName.toLowerCase()},
                        Long.class);//
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateUnit:: " + e.getMessage());
        }
        return dpCnt;
    }
    
    
}
