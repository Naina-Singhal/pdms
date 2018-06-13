/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
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
public class UserLoginDAOImpl {

    private static final Logger logger = Logger.getLogger(UserLoginDAOImpl.class);

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
    public UserLoginDAOImpl() {

    }

    /*
    Default Constructor 
     */

 /*
        Methods
     */
    public EmployeeLoginDTO fetchUserDetailsByUserName(final String username)
            throws AppException {
        EmployeeLoginDTO empDTO = new EmployeeLoginDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id,a.emp_name,a.password,a.locked_flag,a.login_attempts,a.password_flag,a.session_salt,");
            sb.append("b.ic_number,b.first_name,b.middle_name,b.last_name,b.email,b.gender,b.fk_employee_type_id,b.phone, ");
            sb.append("b.fk_section_id,c.employee_type_name,d.section_name, ");
            sb.append("b.fk_designation_id,e.designation_name,e.is_signing_authority,e.is_approving_authority ");
            sb.append(" FROM employee_login a ");
            sb.append(" JOIN employee_profile b on a.pk_emp_id = b.fk_employee_id  ");
            sb.append(" JOIN employee_type_master c on c.pk_employee_type_id = b.fk_employee_type_id  ");
            sb.append(" LEFT JOIN section_master d on d.pk_section_id = b.fk_section_id  ");
            sb.append(" LEFT JOIN designation_master e on e.pk_designation_id = b.fk_designation_id  ");
            sb.append(" WHERE b.status_id=3 AND a.emp_name=?");

            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{username.trim()});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    empDTO.setEmployeeID((Long) (resultMap.get("pk_emp_id")));
                    empDTO.setUsername((String) (resultMap.get("emp_name")));
                    empDTO.setPassword((String) (resultMap.get("password")));
                    empDTO.setCurrentPassword(encryptDecrypt.decrypt(empDTO.getPassword()));
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
                    if(resultMap.get("phone") != null)
                    {
                        empDTO.getEmployeeProfileDTO().setPhone((String) (resultMap.get("phone")));
                    }
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
            throw new AppException("Exception Occurred in fetchUserDetailsByUserName:", e);
        }
        return empDTO;
    }

    public long validateUserPassword(final String username, final String password) throws AppException {
        long resVal = 0;
        try {
            logger.info("In validateUserPassword:username:" + username);
            logger.info("In validateUserPassword:password:" + password);

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id ");
            sb.append(" FROM employee_login a ");
            sb.append(" WHERE a.emp_name=? AND  a.password=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{username.trim(), password.trim()});
//                    Long.class);
            if (!resList.isEmpty()) {
                resVal = 1;
            }
        } catch (Exception e) {
            throw new AppException("Exception occurred :validateUserPassword", e);
        }
        return resVal;
    }

    public String checkUserSessionInfo(final int userId) throws AppException {
        String retSessionID = "";
        try {
            logger.info("In checkUserSessionInfo:username:" + userId);

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT session_token,fk_emp_id FROM session_info ");
            sb.append(" WHERE fk_emp_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{userId});
            if (!resList.isEmpty()) {
                Map<String, Object> retMap = resList.get(0);
                retSessionID = (String) (retMap.get("session_token"));
            }
        } catch (Exception e) {
            throw new AppException("Exception occurred :checkUserSessionInfo", e);
        }
        return retSessionID;
    }

    public int insertUserSessionInfo(final long userId, final String session_id, final String session_token)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO session_info  ");
            sb.append("(fk_emp_id,long_date_time,session_id,session_token) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?)");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{userId,
                        dateUtil.generateDBCurrentDateTime(),
                        session_id,
                        session_token
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUserSessionInfo:", e);
        }

        return retVal;
    }

    public int updateUserSessionInfo(final int userId) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE session_info SET   ");
            sb.append("long_date_time= ? ");
            sb.append(" WHERE fk_emp_id=?");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{
                        dateUtil.generateDBCurrentDateTime(),
                        userId
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUserSessionInfo:", e);
        }

        return retVal;
    }

    public int deleteUserSessionInfo(final long userId) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" DELETE FROM session_info WHERE fk_emp_id=?");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{
                        userId
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUserSessionInfo:", e);
        }

        return retVal;
    }

}
