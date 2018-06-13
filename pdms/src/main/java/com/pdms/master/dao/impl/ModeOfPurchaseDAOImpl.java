/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.ModeOfPurchaseDTO;
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
public class ModeOfPurchaseDAOImpl {

    private static final Logger logger = Logger.getLogger(ModeOfPurchaseDAOImpl.class);
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
    public ModeOfPurchaseDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<ModeOfPurchaseDTO> getAllModeOfPurchaseDetails() throws AppException {
        List<ModeOfPurchaseDTO> mopLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_mode_of_purchase_id,mode_of_purchase,description,status_id   ");
            sb.append(" FROM mode_of_purchase_master ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    ModeOfPurchaseDTO mopObj = new ModeOfPurchaseDTO();
                    mopObj.setModeOfPurchaseID((Long) (retMap.get("pk_mode_of_purchase_id")));
                    mopObj.setModeOfPurchase((String) (retMap.get("mode_of_purchase")));
                    mopObj.setDescription((String) (retMap.get("description")));
                    mopObj.setEncFieldValue(encryptDecrypt.encrypt(mopObj.getModeOfPurchaseID() + ""));
                    mopObj.setRowStatusKey((Long) (retMap.get("status_id")));

                    mopLi.add(mopObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllModeOfPurchaseDetails:", e);
        }

        return mopLi;
    }

    public ModeOfPurchaseDTO getSelectedModeOfPurchaseDetail(final long mopID) throws AppException {
        ModeOfPurchaseDTO mopObj = new ModeOfPurchaseDTO();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_mode_of_purchase_id,mode_of_purchase,description,status_id   ");
            sb.append(" FROM mode_of_purchase_master ");
            sb.append(" WHERE  pk_mode_of_purchase_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{mopID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    mopObj.setModeOfPurchaseID((Long) (retMap.get("pk_mode_of_purchase_id")));
                    mopObj.setModeOfPurchase((String) (retMap.get("mode_of_purchase")));
                    mopObj.setDescription((String) (retMap.get("description")));
                    mopObj.setEncFieldValue(encryptDecrypt.encrypt(mopObj.getModeOfPurchaseID() + ""));
                    mopObj.setRowStatusKey((Long) (retMap.get("status_id")));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedModeOfPurchaseDetail:", e);
        }

        return mopObj;
    }

    public int insertModeOfPurchaseDetail(final ModeOfPurchaseDTO mopObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO  mode_of_purchase_master  ");
            sb.append(" (mode_of_purchase,description,status_id,created_by,created_on )  ");
            sb.append(" VALUES (?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{mopObj.getModeOfPurchase().trim(),
                        mopObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertModeOfPurchaseDetail:", e);
        }
        return retVal;
    }

    public int updateModeOfPurchaseDetail(final ModeOfPurchaseDTO mopObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE mode_of_purchase_master SET ");
            sb.append(" mode_of_purchase=?,description=?,status_id=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE pk_mode_of_purchase_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{mopObj.getModeOfPurchase().trim(),
                        mopObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        mopObj.getModeOfPurchaseID()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateModeOfPurchaseDetail:", e);
        }
        return retVal;
    }

    
    public long checkDuplicateMOP(final String mop) throws AppException {
        long dpCnt = 0;
        try {

            String qry = "SELECT COUNT(1) FROM mode_of_purchase_master ";
            qry+=" WHERE LOWER(mode_of_purchase)=? ";

            dpCnt = getJdbcTemplate().queryForObject(qry,
                    new Object[]{mop.trim().toLowerCase()},
                    Long.class);//,gName.toLowerCase()

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateMOP:: " + e.getMessage());
        }
        return dpCnt;
    }
    
}
