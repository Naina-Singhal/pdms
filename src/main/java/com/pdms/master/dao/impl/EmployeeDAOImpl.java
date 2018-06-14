/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeProfileDTO;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class EmployeeDAOImpl {

    private static final Logger logger = Logger.getLogger(EmployeeDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
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
    public EmployeeDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<EmployeeLoginDTO> getAllEmployeeDetails(final String empTypeQry) throws AppException {
        List<EmployeeLoginDTO> empLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id,a.emp_name,a.password,a.locked_flag,a.login_attempts,a.password_flag,a.session_salt,");
            sb.append("b.ic_number,b.first_name,b.middle_name,b.last_name,b.email,b.gender,b.fk_employee_type_id, ");
            sb.append("b.fk_section_id,c.employee_type_name,d.section_name, ");
            sb.append("b.fk_designation_id,e.designation_name,e.is_signing_authority,e.is_approving_authority ");
            sb.append(" FROM employee_login a ");
            sb.append(" JOIN employee_profile b on a.pk_emp_id = b.fk_employee_id  ");
            sb.append(" JOIN employee_type_master c on c.pk_employee_type_id = b.fk_employee_type_id  ");
            sb.append(" LEFT JOIN section_master d on d.pk_section_id = b.fk_section_id  ");
            sb.append(" LEFT JOIN designation_master e on e.pk_designation_id = b.fk_designation_id  ");
            sb.append(" WHERE c.employee_type_name <> ? ");
            sb.append(empTypeQry);

            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    EmployeeLoginDTO empDTO = new EmployeeLoginDTO();

                    empDTO.setEmployeeID((Long) (resultMap.get("pk_emp_id")));
                    empDTO.setEncFieldValue(encryptDecrypt.encrypt(empDTO.getEmployeeID() + ""));
                    empDTO.setUsername((String) (resultMap.get("emp_name")));
                    String password = ((String) (resultMap.get("password")));
                    empDTO.setLockedFlag((Integer) (resultMap.get("locked_flag")));
                    empDTO.setLoginAttempts((Integer) (resultMap.get("login_attempts")));
                    empDTO.setPasswordFlag((Integer) (resultMap.get("password_flag")));
                    empDTO.setSessionKey((String) (resultMap.get("session_salt")));
                    //encryptDecrypt.setApplicationKey(empDTO.getSessionKey());
                    empDTO.setPassword(encryptDecrypt.decrypt(password));
                    empDTO.getEmployeeProfileDTO().setIcNumber((String) (resultMap.get("ic_number")));
                    empDTO.getEmployeeProfileDTO().setFirstName((String) (resultMap.get("first_name")));
                    empDTO.getEmployeeProfileDTO().setMiddleName((String) (resultMap.get("middle_name")));
                    empDTO.getEmployeeProfileDTO().setLastName((String) (resultMap.get("last_name")));
                    empDTO.getEmployeeProfileDTO().setEmployeeEmail((String) (resultMap.get("email")));
                    empDTO.getEmployeeProfileDTO().setGender((String) (resultMap.get("gender")));

                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeID((Long) (resultMap.get("fk_employee_type_id")));
                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeName((String) (resultMap.get("employee_type_name")));

                    if (empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID() > 0) {
                        empDTO.getSectionDTO().
                                setSectionID((Long) (resultMap.get("fk_section_id")));
                        empDTO.getSectionDTO().
                                setSectionName((String) (resultMap.get("section_name")));

                        empDTO.getDesignationDTO().
                                setDesignationID((Long) (resultMap.get("fk_designation_id")));
                        empDTO.getDesignationDTO().
                                setDesignationName((String) (resultMap.get("designation_name")));
                        if (resultMap.get("is_approving_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsApprovingAuthority((Long) (resultMap.get("is_approving_authority")));
                        }
                        if (resultMap.get("is_signing_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsSigningAuthority((Long) (resultMap.get("is_signing_authority")));
                        }
                    }

                    empLi.add(empDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred in getAllEmployeeDetails:", e);
        }
        return empLi;
    }

    public List<EmployeeLoginDTO> getAllActiveEmployeeDetails() throws AppException {
        List<EmployeeLoginDTO> empLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id,a.emp_name,a.password,a.locked_flag,a.login_attempts,a.password_flag,a.session_salt,");
            sb.append("b.ic_number,b.first_name,b.middle_name,b.last_name,b.email,b.gender,b.fk_employee_type_id, ");
            sb.append("b.fk_section_id,c.employee_type_name,d.section_name, ");
            sb.append("b.fk_designation_id,e.designation_name,e.is_signing_authority,e.is_approving_authority ");
            sb.append(" FROM employee_login a ");
            sb.append(" JOIN employee_profile b on a.pk_emp_id = b.fk_employee_id  ");
            sb.append(" JOIN employee_type_master c on c.pk_employee_type_id = b.fk_employee_type_id  ");
            sb.append(" LEFT JOIN section_master d on d.pk_section_id = b.fk_section_id  ");
            sb.append(" LEFT JOIN designation_master e on e.pk_designation_id = b.fk_designation_id  ");
            sb.append(" WHERE b.status_id=3 ");

            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    EmployeeLoginDTO empDTO = new EmployeeLoginDTO();

                    empDTO.setEmployeeID((Long) (resultMap.get("pk_emp_id")));
                    empDTO.setEncFieldValue(encryptDecrypt.encrypt(empDTO.getEmployeeID() + ""));
                    empDTO.setUsername((String) (resultMap.get("emp_name")));
                    empDTO.setPassword((String) (resultMap.get("password")));
                    empDTO.setLockedFlag((Integer) (resultMap.get("locked_flag")));
                    empDTO.setLoginAttempts((Integer) (resultMap.get("login_attempts")));
                    empDTO.setPasswordFlag((Integer) (resultMap.get("password_flag")));
                    empDTO.setSessionKey((String) (resultMap.get("session_salt")));
                    empDTO.getEmployeeProfileDTO().setIcNumber((String) (resultMap.get("ic_number")));
                    empDTO.getEmployeeProfileDTO().setFirstName((String) (resultMap.get("first_name")));
                    empDTO.getEmployeeProfileDTO().setMiddleName((String) (resultMap.get("middle_name")));
                    empDTO.getEmployeeProfileDTO().setLastName((String) (resultMap.get("last_name")));
                    empDTO.getEmployeeProfileDTO().setEmployeeEmail((String) (resultMap.get("email")));
                    empDTO.getEmployeeProfileDTO().setGender((String) (resultMap.get("gender")));

                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeID((Long) (resultMap.get("fk_employee_type_id")));
                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeName((String) (resultMap.get("employee_type_name")));

                    if (empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID() > 0) {
                        empDTO.getSectionDTO().
                                setSectionID((Long) (resultMap.get("fk_section_id")));
                        empDTO.getSectionDTO().
                                setSectionName((String) (resultMap.get("section_name")));

                        empDTO.getDesignationDTO().
                                setDesignationID((Long) (resultMap.get("fk_designation_id")));
                        empDTO.getDesignationDTO().
                                setDesignationName((String) (resultMap.get("designation_name")));
                        if (resultMap.get("is_approving_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsApprovingAuthority((Long) (resultMap.get("is_approving_authority")));
                        }
                        if (resultMap.get("is_signing_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsSigningAuthority((Long) (resultMap.get("is_signing_authority")));
                        }
                    }

                    empLi.add(empDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred in getAllActiveEmployeeDetails:", e);
        }
        return empLi;
    }

    public int insertEmployeeDetails(final EmployeeLoginDTO empObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    StringBuilder loginSB = new StringBuilder();
                    StringBuilder profileSB = new StringBuilder();
                    try {
                        loginSB.append(" INSERT INTO employee_login ");
                        loginSB.append(" (emp_name,password,locked_flag,login_attempts,password_flag, ");
                        loginSB.append(" session_salt,status_id,created_on) ");
                        loginSB.append(" VALUE (?,?,?,?,?,?,?,?)  ");

                        profileSB.append(" INSERT INTO employee_profile ");
                        profileSB.append("( fk_employee_id,ic_number,first_name,middle_name,last_name,");
                        profileSB.append(" fk_designation_id,fk_section_id,fk_employee_type_id,email,");
                        profileSB.append(" gender,status_id,created_by,created_on) ");
                        profileSB.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ");

                        String username = empObj.getEmployeeProfileDTO().getIcNumber().trim();
                        String password = encryptDecrypt.encrypt(username);
                        
                        
                        int empLoginID = getJdbcTemplate().update(loginSB.toString(),
                                new Object[]{username.trim(),
                                    password.trim(),
                                    empObj.getLockedFlag(),
                                    empObj.getLoginAttempts(),
                                    empObj.getPasswordFlag(),
                                    empObj.getSessionKey().trim(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    dateUtil.generateDBCurrentDateTime()});

                        int insUserID = getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);

                        EmployeeProfileDTO empProfileObj = empObj.getEmployeeProfileDTO();

                        int empProfileID = getJdbcTemplate().update(profileSB.toString(),
                                new Object[]{insUserID,
                                    empProfileObj.getIcNumber().trim(),
                                    empProfileObj.getFirstName().trim(),
                                    empProfileObj.getMiddleName().trim(),
                                    empProfileObj.getLastName().trim(),
                                    empObj.getDesignationDTO().getDesignationID(),
                                    empObj.getSectionDTO().getSectionID(),
                                    empProfileObj.getEmployeeTypeDTO().getEmployeeTypeID(),
                                    empProfileObj.getEmployeeEmail().trim(),
                                    empProfileObj.getGender().trim(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateTime()});
                        if ((empLoginID > 0) && (empProfileID > 0)) {
                            retVal = empLoginID;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertEmployeeDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertEmployeeDetails:: " + e.getMessage());
        }
    }

    public long getMaxEmployeeID() throws AppException {
        long empID = 0;
        try {

            String qry = "SELECT MAX(pk_emp_id) EmpID FROM employee_login ";

            empID = getJdbcTemplate().queryForObject(qry, Long.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "getMaxEmployeeID:: " + e.getMessage());
        }
        return empID;
    }

    public EmployeeLoginDTO getSelectedEmployeeDetails(final long empID) throws AppException {
        EmployeeLoginDTO empDTO = new EmployeeLoginDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id,a.emp_name,a.password,a.locked_flag,a.login_attempts,a.password_flag,a.session_salt,");
            sb.append("b.ic_number,b.first_name,b.middle_name,b.last_name,b.email,b.gender,b.fk_employee_type_id, ");
            sb.append("b.fk_section_id,c.employee_type_name,d.section_name, ");
            sb.append("b.fk_designation_id,e.designation_name,e.is_signing_authority,e.is_approving_authority ");
            sb.append(" FROM employee_login a ");
            sb.append(" JOIN employee_profile b on a.pk_emp_id = b.fk_employee_id  ");
            sb.append(" JOIN employee_type_master c on c.pk_employee_type_id = b.fk_employee_type_id  ");
            sb.append(" LEFT JOIN section_master d on d.pk_section_id = b.fk_section_id  ");
            sb.append(" LEFT JOIN designation_master e on e.pk_designation_id = b.fk_designation_id  ");
            sb.append(" WHERE a.pk_emp_id = ? ");

            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{empID});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {

                    empDTO.setEmployeeID((Long) (resultMap.get("pk_emp_id")));
                    empDTO.setEncFieldValue(encryptDecrypt.encrypt(empDTO.getEmployeeID() + ""));
                    empDTO.setUsername((String) (resultMap.get("emp_name")));
                    empDTO.setPassword((String) (resultMap.get("password")));
                    empDTO.setLockedFlag((Integer) (resultMap.get("locked_flag")));
                    empDTO.setLoginAttempts((Integer) (resultMap.get("login_attempts")));
                    empDTO.setPasswordFlag((Integer) (resultMap.get("password_flag")));
                    empDTO.setSessionKey((String) (resultMap.get("session_salt")));
                    empDTO.getEmployeeProfileDTO().setIcNumber((String) (resultMap.get("ic_number")));
                    empDTO.getEmployeeProfileDTO().setFirstName((String) (resultMap.get("first_name")));
                    empDTO.getEmployeeProfileDTO().setMiddleName((String) (resultMap.get("middle_name")));
                    empDTO.getEmployeeProfileDTO().setLastName((String) (resultMap.get("last_name")));
                    empDTO.getEmployeeProfileDTO().setEmployeeEmail((String) (resultMap.get("email")));
                    empDTO.getEmployeeProfileDTO().setGender((String) (resultMap.get("gender")));

                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeID((Long) (resultMap.get("fk_employee_type_id")));
                    empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().
                            setEmployeeTypeName((String) (resultMap.get("employee_type_name")));

                    if (empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID() > 0) {
                        empDTO.getSectionDTO().
                                setSectionID((Long) (resultMap.get("fk_section_id")));
                        empDTO.getSectionDTO().
                                setSectionName((String) (resultMap.get("section_name")));

                        empDTO.getDesignationDTO().
                                setDesignationID((Long) (resultMap.get("fk_designation_id")));
                        empDTO.getDesignationDTO().
                                setDesignationName((String) (resultMap.get("designation_name")));
                        if (resultMap.get("is_approving_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsApprovingAuthority((Long) (resultMap.get("is_approving_authority")));
                        }
                        if (resultMap.get("is_signing_authority") != null) {
                            empDTO.getDesignationDTO().
                                    setIsSigningAuthority((Long) (resultMap.get("is_signing_authority")));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred in getSelectedEmployeeDetails:", e);
        }
        return empDTO;
    }

    public int updateEmployeeDetails(final EmployeeLoginDTO empObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    StringBuilder loginSB = new StringBuilder();
                    StringBuilder profileSB = new StringBuilder();
                    try {
                        
                        loginSB.append(" UPDATE employee_login SET ");
                        loginSB.append(" emp_name=? ,password=?  ");
                        loginSB.append(" WHERE pk_emp_id=?  ");
                        
                        String username = empObj.getEmployeeProfileDTO().getIcNumber().trim();
                        String password = encryptDecrypt.encrypt(username);
                        
                        
                        int empLoginID = getJdbcTemplate().update(loginSB.toString(),
                                new Object[]{username.trim(),
                                    password.trim(),
                                    empObj.getEmployeeID()});
                        
                        profileSB.append(" UPDATE employee_profile SET ");
                        profileSB.append(" ic_number=?,first_name=?,middle_name=?,last_name=?,");
                        profileSB.append(" fk_designation_id=?,fk_section_id=?,fk_employee_type_id=?,email=?,");
                        profileSB.append(" gender=?,status_id=?,modified_by=?,modified_on=? ");
                        profileSB.append(" WHERE fk_employee_id =? ");

                        EmployeeProfileDTO empProfileObj = empObj.getEmployeeProfileDTO();

                        int empProfileID = getJdbcTemplate().update(profileSB.toString(),
                                new Object[]{
                                    empProfileObj.getIcNumber().trim(),
                                    empProfileObj.getFirstName().trim(),
                                    empProfileObj.getMiddleName().trim(),
                                    empProfileObj.getLastName().trim(),
                                    empObj.getDesignationDTO().getDesignationID(),
                                    empObj.getSectionDTO().getSectionID(),
                                    empProfileObj.getEmployeeTypeDTO().getEmployeeTypeID(),
                                    empProfileObj.getEmployeeEmail().trim(),
                                    empProfileObj.getGender().trim(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateTime(),
                                    empObj.getEmployeeID()});
                        if ((empProfileID > 0)) {
                            retVal = empProfileID;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = 0;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateEmployeeDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateEmployeeDetails:: " + e.getMessage());
        }
    }

    public int updateUserPassword(final String password, final long userID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE employee_login SET ");
            sb.append(" password=?, modified_on=?  ");
            sb.append(" WHERE pk_emp_id=? ");

            resVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{password.trim(),
                        dateUtil.generateDBCurrentDateTime(),
                        userID});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserPassword:: " + e.getMessage());
        }
        return resVal;
    }

    public int updateUserProfile(final EmployeeLoginDTO loginObj) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("UPDATE employee_profile SET ");
            sb.append(" first_name=?,last_name=?,email=?,phone=?,gender=?,modified_by=?, modified_on=?  ");
            sb.append(" WHERE fk_employee_id=? ");

            resVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{loginObj.getEmployeeProfileDTO().getFirstName().trim(),
                        loginObj.getEmployeeProfileDTO().getLastName().trim(),
                        loginObj.getEmployeeProfileDTO().getEmployeeEmail().trim(),
                        loginObj.getEmployeeProfileDTO().getPhone().trim(),
                        loginObj.getEmployeeProfileDTO().getGender().trim(),
                        loginObj.getEmployeeID(),
                        dateUtil.generateDBCurrentDateTime(),
                        loginObj.getEmployeeID()
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateUserProfile:: " + e.getMessage());
        }
        return resVal;
    }
    
    
    public long checkDuplicateEmployees(final String icNumber) throws AppException {
        long empID = 0;
        try {

            String qry = "SELECT COUNT(1) FROM employee_profile WHERE LOWER(ic_number)=?";

            empID = getJdbcTemplate().queryForObject(qry, 
                    new Object[]{icNumber.trim().toLowerCase()},
                    Long.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateEmployees:: " + e.getMessage());
        }
        return empID;
    }
}
