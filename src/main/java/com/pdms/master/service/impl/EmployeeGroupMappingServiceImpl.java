/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.GroupDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EmployeeGroupMappingDAOImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class EmployeeGroupMappingServiceImpl {
    
    private static final Logger logger = Logger.getLogger(EmployeeGroupMappingServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private EmployeeGroupMappingDAOImpl empGrpMapDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public EmployeeGroupMappingServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<EmployeeLoginDTO> getAllPurchaseUnitEmployeeDetails() throws AppException 
    {
        return empGrpMapDAO.getAllPurchaseUnitEmployeeDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int mapEmployeesToGroup(final GroupDTO groupObj, final HttpServletRequest request) throws AppException
    {
        long sessUserID = 0;
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            sessUserID = empDTO.getEmployeeID();
        }
        
        return empGrpMapDAO.insertGroupEmployeeMappingDetails(groupObj, sessUserID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateEmployeesToGroup(final GroupDTO groupObj, final HttpServletRequest request) throws AppException
    {
        long sessUserID = 0;
        
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            sessUserID = empDTO.getEmployeeID();
        }
        
        int delRetVal = empGrpMapDAO.deleteMappedEmployeesFromGroup(groupObj.getGroupID());
        
        return empGrpMapDAO.insertGroupEmployeeMappingDetails(groupObj, sessUserID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<GroupDTO> getAllMappedEmployeeDetails(final String strGroupID) throws AppException
    {
        long groupID = 0;
        if (!StringUtils.isEmpty(strGroupID)) {
            String decGroupID = encryptDecrypt.decrypt(strGroupID);
            if (NumberUtils.isNumber(decGroupID)) {
                groupID = Long.parseLong(decGroupID);
            }
        }
        return empGrpMapDAO.getAllMappedEmployeeDetails(groupID);
    }
}
