/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.UserPermissionDAOImpl;
import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.PagePermiDTO;
import com.pdms.dto.UserPermissionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author myassessment
 */
@Service
public class UserPermissionServiceImpl {
    
    private static final Logger logger = Logger.getLogger(UserPermissionServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private UserPermissionDAOImpl dAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public UserPermissionServiceImpl(){
        
    }
    
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<UserPermissionDTO> getUserPermissions(final String ic_no) throws AppException
    {
        
            return dAOImpl.getUserPermissions(ic_no);
        
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EmployeeProfileDTO> getUserProfileReco() throws AppException
    {
        
            return dAOImpl.getUserProfileReco();
        
    }
    
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PagePermiDTO> getPagepermiData() throws AppException
    {
        
            return dAOImpl.getPagepermiData();
        
    }
    
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int getUserPermiActive(final String ic_no, final String page_id) throws AppException
    {
        
            return dAOImpl.getUserPermiActive(ic_no, page_id);
        
    }
    
      @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertUserPermission(final List<UserPermissionDTO> perObj, final long sessUserID) throws AppException
    {
        
            return dAOImpl.insertUserPermission(perObj, sessUserID);
        
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<UserPermissionDTO> getUserPermissionObj(HttpServletRequest request) throws AppException
    {
        
            return dAOImpl.getUserPermissionObj(request);
        
    }
    
}
