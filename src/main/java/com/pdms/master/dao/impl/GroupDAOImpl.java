/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.GroupDTO;
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
public class GroupDAOImpl {
    private static final Logger logger = Logger.getLogger(GroupDAOImpl.class);
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
    public GroupDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<GroupDTO> getAllGroupDetails() throws AppException {
        List<GroupDTO> groupLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_group_id,group_name,group_code,status_id,description ");
            sb.append(" FROM group_master ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    GroupDTO groupObj = new GroupDTO();

                    groupObj.setGroupID((Long) (retMap.get("pk_group_id")));
                    groupObj.setEncFieldValue(encryptDecrypt.encrypt(groupObj.getGroupID() + ""));
                    groupObj.setGroupCode((String) (retMap.get("group_code")));
                    groupObj.setGroupName((String) (retMap.get("group_name")));
                    groupObj.setDescription((String) (retMap.get("description")));
                    groupObj.setRowStatusKey((Long) (retMap.get("status_id")));

                    groupLi.add(groupObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllGroupDetails:", e);
        }
        return groupLi;
    }

    public GroupDTO getSelectedGroupDetailByID(final long groupID) throws AppException {
        GroupDTO groupObj = new GroupDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_group_id,group_name,group_code,status_id,description ");
            sb.append(" FROM group_master WHERE pk_group_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{groupID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    groupObj.setGroupID((Long) (retMap.get("pk_group_id")));
                    groupObj.setEncFieldValue(encryptDecrypt.encrypt(groupObj.getGroupID() + ""));
                    groupObj.setGroupCode((String) (retMap.get("group_code")));
                    groupObj.setGroupName((String) (retMap.get("group_name")));
                    groupObj.setDescription((String) (retMap.get("description")));
                    groupObj.setRowStatusKey((Long) (retMap.get("status_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedGroupDetailByID:", e);
        }

        return groupObj;
    }

    public int insertGroupDetails(final GroupDTO groupObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO group_master ");
            sb.append(" (group_name,group_code,status_id,description,created_by,created_on) ");
            sb.append(" VALUES (?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{groupObj.getGroupName().trim(),
                        groupObj.getGroupCode().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        groupObj.getDescription().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertGroupDetails:", e);
        }
        return retVal;
    }

    public int updateGroupDetails(final GroupDTO groupObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" UPDATE group_master SET ");
            sb.append(" group_name=?,group_code=?,status_id=?,description=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE  pk_group_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{groupObj.getGroupName().trim(),
                        groupObj.getGroupCode().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        groupObj.getDescription().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        groupObj.getGroupID()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateGroupDetails:", e);
        }
        return retVal;
    }
    
    
    public long checkDuplicateGroup(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {

            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM group_master ";
                qry+=" WHERE LOWER(group_name) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM group_master ";
                qry+=" WHERE LOWER(group_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM group_master ";
                qry+=" WHERE LOWER(group_code)=? AND LOWER(group_name) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase(),gName.toLowerCase()},
                        Long.class);//
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateGroup:: " + e.getMessage());
        }
        return dpCnt;
    }

}
