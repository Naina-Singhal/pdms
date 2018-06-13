/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.BankMasterDTO;
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
public class BankMasterDAOImpl {
    private static final Logger logger = Logger.getLogger(BankMasterDAOImpl.class);

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
    public BankMasterDAOImpl(){
        
    }
    
    
    public int insertBankDetail(final List<BankMasterDTO> bankObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO bank_details  ");
            sb.append("(bankId,bankCode,bankName,branch,ifscCode,city,session_id,bank_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?)");
            
            for(BankMasterDTO BDTO : bankObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{BDTO.getBankId(),                                                
                        BDTO.getBankCode().trim(),
                        BDTO.getBankName(),
                        BDTO.getBranch().trim(), 
                        BDTO.getIfscCode().trim(),
                        BDTO.getCity().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertBankDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateBank(final String gCode) throws AppException {
        long dpCnt = 0;
        try {            
                String qry = "SELECT COUNT(1) FROM bank_details ";
                qry+=" WHERE LOWER(bankCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//  

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateBank:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public List<BankMasterDTO> getBankMasterDetails() throws AppException {
        List<BankMasterDTO> RDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bank_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BankMasterDTO dTO = new BankMasterDTO();
                    dTO.setBankId((Long) (retMap.get("bankId")));
                    dTO.setBankCode((String) (retMap.get("bankCode")));
                    dTO.setBankName((String) (retMap.get("bankName")));
                    dTO.setBranch((String) (retMap.get("branch")));
                    dTO.setIfscCode((String) (retMap.get("ifscCode")));  
                    dTO.setCity((String) (retMap.get("city")));                    
                    RDTO.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankMasterDetails:", e);
        }
        return RDTO;
    }
    
    public List<BankMasterDTO> getBankReById(final long id) throws AppException {
        List<BankMasterDTO> STDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bank_details WHERE bankId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BankMasterDTO dtoObj = new BankMasterDTO();
                    dtoObj.setBankId((Long) (retMap.get("bankId")));
                    dtoObj.setBankCode((String) (retMap.get("bankCode")));
                    dtoObj.setBankName((String) (retMap.get("bankName")));
                    dtoObj.setBranch((String) (retMap.get("branch")));
                    dtoObj.setIfscCode((String) (retMap.get("ifscCode")));  
                    dtoObj.setCity((String) (retMap.get("city")));                       
                    STDTO.add(dtoObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankReById:", e);
        }
        return STDTO;
    }
    
    public int updateBankDetail(final List<BankMasterDTO> bankObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE bank_details  ");
            sb.append(" SET bankCode=?,bankName=?,branch=?,ifscCode=?,city=?,session_id=?,bank_date=? ");            
            sb.append(" WHERE bankId=? ");
            
            for(BankMasterDTO Obj : bankObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getBankCode().trim(),
                        Obj.getBankName().trim(),
                        Obj.getBranch().trim(),
                        Obj.getIfscCode().trim(),
                        Obj.getCity().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getBankId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateBankDetail:",e);
        }
        return retVal;
    }
    
    public BankMasterDTO getSelectedBank(final long bankId) throws AppException
    {
        BankMasterDTO bankObj = new BankMasterDTO();
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM bank_details WHERE  bankId=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{bankId});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {
                    bankObj.setBankId((Long) (retMap.get("bankId")));
                    bankObj.setBankCode((String) (retMap.get("bankCode")));
                    bankObj.setBankName((String) (retMap.get("bankName")));
                    bankObj.setBranch((String) (retMap.get("branch")));
                    bankObj.setIfscCode((String) (retMap.get("ifscCode")));  
                    bankObj.setCity((String) (retMap.get("city")));                 
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedBank:",e);
        }
        return bankObj;
    }
}
