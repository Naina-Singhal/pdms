/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.HeadOfAccountDTO;
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
public class HeadOfAccountDAOImpl {

    private static final Logger logger = Logger.getLogger(HeadOfAccountDAOImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */
 /*
    Default Constructor 
     */
    public HeadOfAccountDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<HeadOfAccountDTO> getAllHeadOfAccountDetails() throws AppException {
        List<HeadOfAccountDTO> hodLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_head_of_account_id,account_type,account_code,account_desc,status_id ");
            sb.append(" FROM head_of_account_master ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    HeadOfAccountDTO hodObj = new HeadOfAccountDTO();

                    hodObj.setHeadOfAccountID((Long) (retMap.get("pk_head_of_account_id")));
                    hodObj.setAccountType((String) (retMap.get("account_type")));
                    hodObj.setAccountCode((String) (retMap.get("account_code")));
                    hodObj.setDescription((String) (retMap.get("account_desc")));
                    hodObj.setEncFieldValue(encryptDecrypt.encrypt(hodObj.getHeadOfAccountID() + ""));
                    hodObj.setRowStatusKey((Long) (retMap.get("status_id")));

                    hodLi.add(hodObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllHeadOfAccountDetails:", e);
        }

        return hodLi;
    }

    public HeadOfAccountDTO getSelectedHeadOfAccountDetail(final long hodID) throws AppException {
        HeadOfAccountDTO hodObj = new HeadOfAccountDTO();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_head_of_account_id,account_type,account_code,account_desc,status_id ");
            sb.append(" FROM head_of_account_master WHERE pk_head_of_account_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{hodID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    hodObj.setHeadOfAccountID((Long) (retMap.get("pk_head_of_account_id")));
                    hodObj.setAccountType((String) (retMap.get("account_type")));
                    hodObj.setAccountCode((String) (retMap.get("account_code")));
                    hodObj.setDescription((String) (retMap.get("account_desc")));
                    hodObj.setEncFieldValue(encryptDecrypt.encrypt(hodObj.getHeadOfAccountID() + ""));
                    hodObj.setRowStatusKey((Long) (retMap.get("status_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedHeadOfAccountDetail:", e);
        }

        return hodObj;
    }

    public int insertHeadOfAccountDetail(final HeadOfAccountDTO hodObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO head_of_account_master ");
            sb.append("(account_type,account_code,account_desc,status_id,created_by,created_on) ");
            sb.append(" VALUES (?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{hodObj.getAccountType().trim(),
                        hodObj.getAccountCode().trim(),
                        hodObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertHeadOfAccountDetail:", e);
        }
        return retVal;
    }

    public int updateHeadOfAccountDetail(final HeadOfAccountDTO hodObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE head_of_account_master  SET ");
            sb.append("account_type=?,account_code=?,account_desc=?,status_id=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE pk_head_of_account_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{hodObj.getAccountType().trim(),
                        hodObj.getAccountCode().trim(),
                        hodObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        hodObj.getHeadOfAccountID()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateHeadOfAccountDetail:", e);
        }
        return retVal;
    }
    
    
    public long checkDuplicateHOA(final String accountType, final String accountCode) throws AppException {
        long dpCnt = 0;
        try {

            if(accountType.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM head_of_account_master ";
                qry+=" WHERE  LOWER(account_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{accountCode.trim().toLowerCase()},
                        Long.class);
            }
            else if(accountCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM head_of_account_master ";
                qry+=" WHERE LOWER(account_type)=?  ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{accountType.trim().toLowerCase()},
                        Long.class);
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM head_of_account_master ";
                qry+=" WHERE LOWER(account_type)=? AND LOWER(account_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{accountType.trim().toLowerCase(),
                        accountCode.trim().toLowerCase()},
                        Long.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateHOA:: " + e.getMessage());
        }
        return dpCnt;
    }

}
