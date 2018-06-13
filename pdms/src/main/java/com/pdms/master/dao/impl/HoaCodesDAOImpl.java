/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.HoaCodesDTO;
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
public class HoaCodesDAOImpl {
    
    private static final Logger logger = Logger.getLogger(HoaCodesDAOImpl.class);

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
    public HoaCodesDAOImpl(){
        
    }
    
    
    public int insertHoaCodesDetail(final List<HoaCodesDTO> hoaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO hoa_codes_details  ");
            sb.append("(hoaCodeId,accountName,shortCode,description,session_id,hoa_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(HoaCodesDTO RDTO : hoaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{RDTO.getHoaCodeId(),                                                
                        RDTO.getAccountName().trim(),
                        RDTO.getShortCode().trim(),
                        RDTO.getDescription().trim(),                         
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertHoaCodesDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateHoaCodes(final String hoa_Name, final String shortCode) throws AppException {
        long dpCnt = 0;
        try {

            String qry = "SELECT COUNT(1) FROM hoa_codes_details ";
            qry += " WHERE LOWER(accountName) = ? AND LOWER(shortCode) = ?";

            dpCnt = getJdbcTemplate().queryForObject(qry,
                    new Object[]{hoa_Name.trim().toLowerCase(), shortCode.trim().toLowerCase() },
                    Long.class);// 

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicate Hoa Short codes:: " + e.getMessage());
        }
        return dpCnt;
    }
    
    public List<HoaCodesDTO> getHoaShortCodesDetails() throws AppException {
        List<HoaCodesDTO> HDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM hoa_codes_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    HoaCodesDTO dTO = new HoaCodesDTO();
                    dTO.setHoaCodeId((Long) (retMap.get("hoaCodeId")));
                    dTO.setAccountName((String) (retMap.get("accountName")));
                    dTO.setShortCode((String) (retMap.get("shortCode")));
                    dTO.setDescription((String) (retMap.get("description")));                    
                    HDTO.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getHoaShortCodesDetails:", e);
        }
        return HDTO;
    }
    
    public List<HoaCodesDTO> getHoaCodesReById(final long id) throws AppException {
        List<HoaCodesDTO> hoaDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM hoa_codes_details WHERE hoaCodeId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    HoaCodesDTO HTO = new HoaCodesDTO();
                    HTO.setHoaCodeId((Long) (retMap.get("hoaCodeId")));
                    HTO.setAccountName((String) (retMap.get("accountName")));
                    HTO.setShortCode((String) (retMap.get("shortCode")));
                    HTO.setDescription((String) (retMap.get("description")));                    
                    hoaDTO.add(HTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getHoaCodesReById:", e);
        }
        return hoaDTO;
    }
    
    public List<HoaCodesDTO> getSelectedHoaShortCodes(final long hoaCodeId) throws AppException
    {
        List<HoaCodesDTO> hoaLi = new LinkedList<>();
        
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM hoa_codes_details WHERE  hoaCodeId=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{hoaCodeId});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {
                    HoaCodesDTO hoaObj = new HoaCodesDTO();
                    hoaObj.setHoaCodeId((Long) (retMap.get("hoaCodeId")));
                    hoaObj.setAccountName((String) (retMap.get("accountName")));
                    hoaObj.setShortCode((String) (retMap.get("shortCode")));
                    hoaObj.setDescription((String) (retMap.get("description")));     
                    hoaLi.add(hoaObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedHoaShortCodes:",e);
        }
        return hoaLi;
    }
    
    public int updateHoaCodesDetails(final List<HoaCodesDTO> hoaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE  hoa_codes_details  ");
            sb.append(" SET accountName=?,shortCode=?,description=?,session_id=?,hoa_date=? ");            
            sb.append(" WHERE hoaCodeId=? ");
            
            for(HoaCodesDTO HDTO : hoaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{HDTO.getAccountName().trim(),
                        HDTO.getShortCode().trim(),
                        HDTO.getDescription().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        HDTO.getHoaCodeId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateHoaCodesDetails:",e);
        }
        return retVal;
    }
    
    public List<HoaCodesDTO> getHoaCodesReByAcName(final String acName) throws AppException {
        List<HoaCodesDTO> hoaDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM hoa_codes_details WHERE accountName = '"+acName+"' ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    HoaCodesDTO HTO = new HoaCodesDTO();
                    HTO.setHoaCodeId((Long) (retMap.get("hoaCodeId")));
                    HTO.setAccountName((String) (retMap.get("accountName")));
                    HTO.setShortCode((String) (retMap.get("shortCode")));
                    HTO.setDescription((String) (retMap.get("description")));                    
                    hoaDTO.add(HTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getHoaCodesReByAcName:", e);
        }
        return hoaDTO;
    }
}
