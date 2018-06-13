/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeTypeDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EmployeeTypeDAOImpl;
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
public class EmployeeTypeServiceImpl {
    
    private static final Logger logger = Logger.getLogger(EmployeeTypeServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private EmployeeTypeDAOImpl empTypeDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public EmployeeTypeServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EmployeeTypeDTO> getAllEmployeeTypeDetails() throws AppException
    {
        return empTypeDAO.getAllEmployeeTypeDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EmployeeTypeDTO> getSelectedEmployeeTypeDetails(final String encEmpTypeID) throws AppException 
    {
        long empTypeID=0;
        if(!StringUtils.isEmpty(encEmpTypeID))
        {
            String decEmpTypeID = encryptDecrypt.decrypt(encEmpTypeID);
            if(NumberUtils.isNumber(decEmpTypeID))
            {
                empTypeID = Long.parseLong(decEmpTypeID);
            }
        }
        return empTypeDAO.getSelectedEmployeeTypeDetails(empTypeID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertEmployeeTypeDetail(final EmployeeTypeDTO employeeTypeObj, final long sessUserID)
            throws AppException
    {
        return empTypeDAO.insertEmployeeTypeDetail(employeeTypeObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateEmployeeTypeDetail(final EmployeeTypeDTO employeeTypeObj, final long sessUserID)
            throws AppException
    {
        long empTypeID=0;
        if(!StringUtils.isEmpty(employeeTypeObj.getEncFieldValue()))
        {
            String decEmpTypeID = encryptDecrypt.decrypt(employeeTypeObj.getEncFieldValue());
            if(NumberUtils.isNumber(decEmpTypeID))
            {
                empTypeID = Long.parseLong(decEmpTypeID);
            }
        }
        
        employeeTypeObj.setEmployeeTypeID(empTypeID);
        return empTypeDAO.updateEmployeeTypeDetail(employeeTypeObj, sessUserID);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public EmployeeTypeDTO getEmployeeTypeDetailsByName(final String empTypeName) throws AppException
    {
        return empTypeDAO.getEmployeeTypeDetailsByName(empTypeName);
    }
}
