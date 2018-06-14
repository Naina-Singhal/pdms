/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.master.dto.SignatoryDTO;
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
public class SignatoryDAOImpl {
    
    private static final Logger logger = Logger.getLogger(SignatoryDAOImpl.class);

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
    public SignatoryDAOImpl(){
        
    }
    
    
    public int insertSignatoryDetail(final List<SignatoryDTO> sinObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO signatory_details  ");
            sb.append("(signatoryId,signatoryCode,signatoryName,signatoryDes,session_id,sign_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(SignatoryDTO SNDTO : sinObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SNDTO.getSignatoryId(),                                                
                        SNDTO.getSignatoryCode().trim(),
                        SNDTO.getSignatoryName().trim(),
                        SNDTO.getSignatoryDes().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateSignatory(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {            
            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM signatory_details ";
                qry+=" WHERE LOWER(signatoryName) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//                
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM signatory_details ";
                qry+=" WHERE LOWER(signatoryCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM signatory_details ";
                qry+=" WHERE LOWER(signatoryCode)=? AND LOWER(signatoryName) = ?";

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
    
    public List<SignatoryDTO> getSignatoryDetails() throws AppException {
        List<SignatoryDTO> SNDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM signatory_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    SignatoryDTO dTO = new SignatoryDTO();
                    dTO.setSignatoryId((Long) (retMap.get("signatoryId")));
                    dTO.setSignatoryCode((String) (retMap.get("signatoryCode")));
                    dTO.setSignatoryName((String) (retMap.get("signatoryName")));
                    dTO.setSignatoryDes((String) (retMap.get("signatoryDes")));                    
                    SNDTO.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return SNDTO;
    }
    
    public List<SignatoryDTO> getSignatoryReById(final long id) throws AppException {
        List<SignatoryDTO> STDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM signatory_details WHERE signatoryId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    SignatoryDTO sdto = new SignatoryDTO();
                    sdto.setSignatoryId((Long) (retMap.get("signatoryId")));
                    sdto.setSignatoryCode((String) (retMap.get("signatoryCode")));
                    sdto.setSignatoryName((String) (retMap.get("signatoryName")));
                    sdto.setSignatoryDes((String) (retMap.get("signatoryDes")));                     
                    STDTO.add(sdto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return STDTO;
    }
    
    public int updateSignatoryDetail(final List<SignatoryDTO> singObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE signatory_details  ");
            sb.append(" SET signatoryCode=?,signatoryName=?,signatoryDes=?,session_id=?,sign_date=? ");            
            sb.append(" WHERE signatoryId=? ");
            
            for(SignatoryDTO SDTO : singObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SDTO.getSignatoryCode().trim(),
                        SDTO.getSignatoryName().trim(),
                        SDTO.getSignatoryDes().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        SDTO.getSignatoryId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public SignatoryDTO getSelectedSignatory(final long signatoryID) throws AppException
    {
        SignatoryDTO sgObj = new SignatoryDTO();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT signatoryId,signatoryCode,signatoryName,signatoryDes ");
            sb.append(" FROM signatory_details WHERE  signatoryId=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{signatoryID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    sgObj.setSignatoryId((Long) (resultMap.get("signatoryId")));
                    sgObj.setSignatoryCode((String) (resultMap.get("signatoryCode")));
                    sgObj.setSignatoryName((String) (resultMap.get("signatoryName")));
                    sgObj.setSignatoryDes((String) (resultMap.get("signatoryDes")));              
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedUnit:",e);
        }
        return sgObj;
    }
}
