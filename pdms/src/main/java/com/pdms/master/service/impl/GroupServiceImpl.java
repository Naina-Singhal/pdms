/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.GroupDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.GroupDAOImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class GroupServiceImpl {

    private static final Logger logger = Logger.getLogger(GroupServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private GroupDAOImpl groupDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public GroupServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<GroupDTO> getAllGroupDetails() throws AppException {
        return groupDAO.getAllGroupDetails();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public GroupDTO getSelectedGroupDetailByID(final String encGroupID) throws AppException {
        long groupID = 0;
        if (!StringUtils.isEmpty(encGroupID)) {
            String decGroupID = encryptDecrypt.decrypt(encGroupID);
            if (NumberUtils.isNumber(decGroupID)) {
                groupID = Long.parseLong(decGroupID);
            }
        }

        return groupDAO.getSelectedGroupDetailByID(groupID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertGroupDetails(final GroupDTO groupObj, final long sessUserID) throws AppException {

        long dupCnt = this.checkDuplicateGroup(groupObj.getGroupCode(), groupObj.getGroupName());
        if (dupCnt > 0) {
            return -1;
        } else {
            return groupDAO.insertGroupDetails(groupObj, sessUserID);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateGroupDetails(final GroupDTO groupObj, final long sessUserID) throws AppException {
        long groupID = 0;
        if (!StringUtils.isEmpty(groupObj.getEncFieldValue())) {
            String decGroupID = encryptDecrypt.decrypt(groupObj.getEncFieldValue());
            if (NumberUtils.isNumber(decGroupID)) {
                groupID = Long.parseLong(decGroupID);
            }
        }

        groupObj.setGroupID(groupID);

        long dupCnt = 0;
        GroupDTO exisGrpObj = this.getSelectedGroupDetailByID(groupObj.getEncFieldValue());

        if (!(exisGrpObj.getGroupCode().equalsIgnoreCase(groupObj.getGroupCode()))
                && !(exisGrpObj.getGroupName().equalsIgnoreCase(groupObj.getGroupName()))) {
            dupCnt = this.checkDuplicateGroup(groupObj.getGroupCode(), groupObj.getGroupName());
        }
        else if (!(exisGrpObj.getGroupCode().equalsIgnoreCase(groupObj.getGroupCode()))) {
            dupCnt = this.checkDuplicateGroup(groupObj.getGroupCode(), "0");
        }
        else if (!(exisGrpObj.getGroupName().equalsIgnoreCase(groupObj.getGroupName()))) {
            dupCnt = this.checkDuplicateGroup("0", groupObj.getGroupName());
        }
        
        if (dupCnt > 0) {
            return -1;
        } else {
            return groupDAO.updateGroupDetails(groupObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateGroup(final String gCode, final String gName) throws AppException {
        return groupDAO.checkDuplicateGroup(gCode, gName);
    }

}
