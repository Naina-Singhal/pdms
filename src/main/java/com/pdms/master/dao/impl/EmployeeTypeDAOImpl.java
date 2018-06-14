/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.EmployeeTypeDTO;
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
public class EmployeeTypeDAOImpl {
    private static final Logger logger = Logger.getLogger(EmployeeTypeDAOImpl.class);
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
    public EmployeeTypeDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<EmployeeTypeDTO> getAllEmployeeTypeDetails() throws AppException {

        List<EmployeeTypeDTO> employeeTypeLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_employee_type_id,employee_type_name, ");
            sb.append(" description,status_id ");
            sb.append(" FROM employee_type_master ");
            sb.append(" WHERE employee_type_name <> ? ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();

                    empTypeObj.setEmployeeTypeID((Long) (retMap.get("pk_employee_type_id")));
                    empTypeObj.setEmployeeTypeName((String) (retMap.get("employee_type_name")));
                    empTypeObj.setDescription((String) (retMap.get("description")));
                    empTypeObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    empTypeObj.setEncFieldValue(encryptDecrypt.encrypt(empTypeObj.getEmployeeTypeID()+""));

                    employeeTypeLi.add(empTypeObj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllEmployeeTypeDetails:", e);
        }

        return employeeTypeLi;
    }

    public List<EmployeeTypeDTO> getSelectedEmployeeTypeDetails(final long empTypeID) throws AppException {

        List<EmployeeTypeDTO> employeeTypeLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_employee_type_id,employee_type_name, ");
            sb.append(" description,status_id ");
            sb.append(" FROM employee_type_master ");
            sb.append(" WHERE pk_employee_type_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{empTypeID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();

                    empTypeObj.setEmployeeTypeID((Long) (retMap.get("pk_employee_type_id")));
                    empTypeObj.setEmployeeTypeName((String) (retMap.get("employee_type_name")));
                    empTypeObj.setDescription((String) (retMap.get("description")));
                    empTypeObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    empTypeObj.setEncFieldValue(encryptDecrypt.encrypt(empTypeObj.getEmployeeTypeID()+""));

                    employeeTypeLi.add(empTypeObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedEmployeeTypeDetails:", e);
        }

        return employeeTypeLi;
    }

    public int insertEmployeeTypeDetail(final EmployeeTypeDTO employeeTypeObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO employee_type_master ");
            sb.append(" (employee_type_name,description,status_id,created_by,created_on) ");
            sb.append(" VALUES ");
            sb.append(" (?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{employeeTypeObj.getEmployeeTypeName(),
                        employeeTypeObj.getDescription(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertEmployeeTypeDetail:", e);
        }
        return retVal;
    }

    public int updateEmployeeTypeDetail(final EmployeeTypeDTO employeeTypeObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE employee_type_master SET ");
            sb.append(" employee_type_name=?,description=?,");
            sb.append(" status_id=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE  pk_employee_type_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{employeeTypeObj.getEmployeeTypeName(),
                        employeeTypeObj.getDescription(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        employeeTypeObj.getEmployeeTypeID()});
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertEmployeeTypeDetail:", e);
        }
        return retVal;
    }

    
    public EmployeeTypeDTO getEmployeeTypeDetailsByName(final String empTypeName) throws AppException {
        EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_employee_type_id,employee_type_name, ");
            sb.append(" description,status_id ");
            sb.append(" FROM employee_type_master ");
            sb.append(" WHERE employee_type_name=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{empTypeName});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    empTypeObj.setEmployeeTypeID((Long) (retMap.get("pk_employee_type_id")));
                    empTypeObj.setEmployeeTypeName((String) (retMap.get("employee_type_name")));
                    empTypeObj.setDescription((String) (retMap.get("description")));
                    empTypeObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    empTypeObj.setEncFieldValue(encryptDecrypt.encrypt(empTypeObj.getEmployeeTypeID()+""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getEmployeeTypeDetailsByName:", e);
        }

        return empTypeObj;
    }
    
}
