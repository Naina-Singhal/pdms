/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.GroupDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
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
public class EmployeeGroupMappingDAOImpl {

    private static final Logger logger = Logger.getLogger(EmployeeGroupMappingDAOImpl.class);
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
    public EmployeeGroupMappingDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<EmployeeLoginDTO> getAllPurchaseUnitEmployeeDetails() throws AppException {
        List<EmployeeLoginDTO> empLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_emp_id,a.emp_name,a.password,a.locked_flag,a.login_attempts,a.password_flag,a.session_salt,");
            sb.append("b.ic_number,b.first_name,b.middle_name,b.last_name,b.email,b.gender,b.fk_employee_type_id, ");
            sb.append("b.fk_section_id,c.employee_type_name,d.section_name, ");
            sb.append("b.fk_designation_id,e.designation_name,e.is_signing_authority,e.is_approving_authority, ");
            sb.append(" g.group_name,g.group_code,g.pk_group_id ");
            sb.append(" FROM employee_login a ");
            sb.append(" JOIN employee_profile b on a.pk_emp_id = b.fk_employee_id  ");
            sb.append(" JOIN employee_type_master c on c.pk_employee_type_id = b.fk_employee_type_id  ");
            sb.append(" JOIN designation_master e on e.pk_designation_id = b.fk_designation_id  ");
            sb.append(" LEFT JOIN section_master d on d.pk_section_id = b.fk_section_id  ");
            sb.append(" LEFT JOIN employee_group_mapping f on a.pk_emp_id = f.fk_employee_id  ");
            sb.append(" LEFT JOIN group_master g on g.pk_group_id = f.fk_group_id  ");
            sb.append(" WHERE c.employee_type_name = ? ");
            sb.append(" GROUP BY a.pk_emp_id ");

            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_PUUSER});

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
                    if (resultMap.get("group_name") != null) {
                        empDTO.getGroupDTO().setGroupName((String) (resultMap.get("group_name")));
                    }
                    if (resultMap.get("group_code") != null) {
                        empDTO.getGroupDTO().setGroupCode((String) (resultMap.get("group_code")));
                    }
                    if (resultMap.get("pk_group_id") != null) {
                        empDTO.getGroupDTO().setGroupID((Long) (resultMap.get("pk_group_id")));
                        empDTO.getGroupDTO().setEncFieldValue
                                (encryptDecrypt.encrypt(empDTO.getGroupDTO().getGroupID()+""));
                    }

                    empLi.add(empDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred in getAllPurchaseUnitEmployeeDetails:", e);
        }
        return empLi;
    }

    public int deleteMappedEmployeesFromGroup(final long groupID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" DELETE FROM employee_group_mapping ");
            sb.append(" WHERE fk_group_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(), new Object[]{groupID});
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred in deleteMappedEmployeesFromGroup:", e);
        }

        return retVal;
    }

    public int insertGroupEmployeeMappingDetails(final GroupDTO grpObj, final long sessUserID) 
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO employee_group_mapping  ");
                        sb.append(" ( fk_employee_id,fk_group_id,description,created_on,created_by) ");
                        sb.append("  VALUES  ");
                        sb.append(" (?,?,?,?,?)  ");
                        
                        int insCount=0;
                        
                        if(!grpObj.getEmpList().isEmpty())
                        {
                            for(Long empID : grpObj.getEmpList())
                            {
                                int resVal = getJdbcTemplate().update(sb.toString(),
                                        new Object[]{empID,
                                            grpObj.getGroupID(),
                                            "",
                                            dateUtil.generateDBCurrentDateTime(),
                                            sessUserID
                                        });
                                if(resVal>0)
                                {
                                    insCount++;
                                }
                            }
                        }
                        
                        if(grpObj.getEmpList().size() == insCount)
                        {
                            retVal=1;
                        }
                        else
                        {
                            transactionStatus.setRollbackOnly();
                            retVal = 0;
                        }
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertGroupEmployeeMappingDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertGroupEmployeeMappingDetails:: " + e.getMessage());
        }

    }
    
    
    public List<GroupDTO> getAllMappedEmployeeDetails(final long groupID) throws AppException
    {
        List<GroupDTO> groupLi = new ArrayList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT a.fk_employee_id,a.fk_group_id,b.first_name,b.last_name   ");
            sb.append(" FROM employee_group_mapping a  ");
            sb.append(" JOIN employee_profile b ON a.fk_employee_id = b.fk_employee_id  ");
            sb.append(" WHERE a.fk_group_id=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{groupID});

            if (!resultList.isEmpty()) {
                for (Map<String, Object> resultMap : resultList) {
                    
                    GroupDTO groupObj = new GroupDTO();
                    groupObj.setEmployeeID((Long)(resultMap.get("fk_employee_id")));
                    groupObj.setGroupID((Long)(resultMap.get("fk_group_id")));
                    groupObj.setFirstName((String)(resultMap.get("first_name")));
                    groupObj.setLastName((String)(resultMap.get("last_name")));
                    
                    groupLi.add(groupObj);
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "getAllMappedEmployeeDetails:: " + e.getMessage());
        }
        return groupLi;
    }
    
}
