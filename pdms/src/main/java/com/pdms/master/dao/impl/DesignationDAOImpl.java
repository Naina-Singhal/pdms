/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.DesignationDTO;
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
public class DesignationDAOImpl {

    private static final Logger logger = Logger.getLogger(DesignationDAOImpl.class);

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
    public DesignationDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<DesignationDTO> getAllDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_designation_id,designation_code,designation_name,is_signing_authority, ");
            sb.append(" is_approving_authority,description,status_id  ");
            sb.append(" FROM designation_master ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllDesignations:", e);
        }
        return desigLi;
    }

    public List<DesignationDTO> getAllApprovingAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_designation_id,designation_code,designation_name,is_signing_authority, ");
            sb.append(" is_approving_authority,description,status_id  ");
            sb.append(" FROM designation_master WHERE is_approving_authority =1 ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllApprovingAuthorityDesignations:", e);
        }
        return desigLi;
    }

    public List<DesignationDTO> getAllDesignationsbyEmployeeType(final long empTypeID) throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_designation_id,designation_code,designation_name,is_signing_authority, ");
            sb.append(" is_approving_authority,description,status_id  ");
            sb.append(" FROM designation_master WHERE ");

            String strWhere = "";
            if (empTypeID > 0) {
                strWhere = "fk_employee_type_id  =" + empTypeID;
            } else {
                strWhere = "fk_employee_type_id = fk_employee_type_id";
            }

            sb.append(strWhere);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllDesignationsbyEmployeeType:", e);
        }
        return desigLi;
    }

    public List<DesignationDTO> getAllSigningAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_designation_id,designation_code,designation_name,is_signing_authority, ");
            sb.append(" is_approving_authority,description,status_id  ");
            sb.append(" FROM designation_master WHERE is_signing_authority =1 ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllSigningAuthorityDesignations:", e);
        }
        return desigLi;
    }

    public DesignationDTO getSelectedDesignationByID(final long designationID) throws AppException {
        DesignationDTO designationDTO = new DesignationDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_designation_id,designation_code,designation_name,is_signing_authority, ");
            sb.append(" is_approving_authority,description,status_id  ");
            sb.append(" FROM designation_master ");
            sb.append(" WHERE pk_designation_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{designationID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedDesignationByID:", e);
        }
        return designationDTO;
    }

    public int insertDesignationDetail(final DesignationDTO designationObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO designation_master ");
            sb.append(" (designation_code,designation_name,is_signing_authority,is_approving_authority, ");
            sb.append(" description,status_id,created_by,created_on,fk_employee_type_id)  ");
            sb.append(" VALUES ");
            sb.append(" (?,?,?,?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{designationObj.getDesignationCode().trim(),
                        designationObj.getDesignationName().trim(),
                        designationObj.getIsSigningAuthority(),
                        designationObj.getIsApprovingAuthority(),
                        designationObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        designationObj.getEmployeeTypeID()
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertDesignationDetail:", e);
        }
        return retVal;
    }

    public int updateDesignationDetail(final DesignationDTO designationObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE designation_master SET ");
            sb.append(" designation_code=?,designation_name=?,is_signing_authority=?,is_approving_authority=?, ");
            sb.append(" description=?,status_id=?,modified_by=?,modified_on=?,fk_employee_type_id=?  ");
            sb.append(" WHERE pk_designation_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{designationObj.getDesignationCode().trim(),
                        designationObj.getDesignationName().trim(),
                        designationObj.getIsSigningAuthority(),
                        designationObj.getIsApprovingAuthority(),
                        designationObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        designationObj.getEmployeeTypeID(),
                        designationObj.getDesignationID(),});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateDesignationDetail:", e);
        }
        return retVal;
    }

    public long checkDuplicateDesignation(final String designationCode,
            final String desigName) throws AppException {
        long dpCnt = 0;
        try {

            if (designationCode.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM designation_master ";
                qry += " WHERE LOWER(designation_name)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{
                            desigName.trim().toLowerCase()},
                        Long.class);
            } else if (desigName.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM designation_master ";
                qry += " WHERE LOWER(designation_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{designationCode.trim().toLowerCase()},
                        Long.class);
            } else {
                String qry = "SELECT COUNT(1) FROM designation_master ";
                qry += " WHERE LOWER(designation_code)=? AND LOWER(designation_name)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{designationCode.trim().toLowerCase(),
                            desigName.trim().toLowerCase()},
                        Long.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateDesignation:: " + e.getMessage());
        }
        return dpCnt;
    }

    public List<DesignationDTO> getAllPlantApprovingAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.pk_designation_id,a.designation_code,a.designation_name,a.is_signing_authority, ");
            sb.append(" a.is_approving_authority,a.description,a.status_id  ");
            sb.append(" FROM designation_master a ");
            sb.append(" JOIN employee_type_master b ON  a.fk_employee_type_id = b.pk_employee_type_id ");
            sb.append(" WHERE a.is_approving_authority =1 AND (b.employee_type_name=? OR b.employee_type_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_SECADMIN,
                        ApplicationConstants.EMPLOYEE_TYPE_SECUSER});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllPlantApprovingAuthorityDesignations:", e);
        }
        return desigLi;
    }
    
    
    public List<DesignationDTO> getAllRPUApprovingAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.pk_designation_id,a.designation_code,a.designation_name,a.is_signing_authority, ");
            sb.append(" a.is_approving_authority,a.description,a.status_id  ");
            sb.append(" FROM designation_master a ");
            sb.append(" JOIN employee_type_master b ON  a.fk_employee_type_id = b.pk_employee_type_id ");
            sb.append(" WHERE a.is_approving_authority =1 AND (b.employee_type_name=? OR b.employee_type_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_PUADMIN,
                        ApplicationConstants.EMPLOYEE_TYPE_PUUSER});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllRPUApprovingAuthorityDesignations:", e);
        }
        return desigLi;
    }
    
    
    public List<DesignationDTO> getAllAccountsApprovingAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.pk_designation_id,a.designation_code,a.designation_name,a.is_signing_authority, ");
            sb.append(" a.is_approving_authority,a.description,a.status_id  ");
            sb.append(" FROM designation_master a ");
            sb.append(" JOIN employee_type_master b ON  a.fk_employee_type_id = b.pk_employee_type_id ");
            sb.append(" WHERE a.is_approving_authority =1 AND (b.employee_type_name=? OR b.employee_type_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN,
                        ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllAccountsApprovingAuthorityDesignations:", e);
        }
        return desigLi;
    }
    
    public List<DesignationDTO> getAllStoresApprovingAuthorityDesignations() throws AppException {
        List<DesignationDTO> desigLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.pk_designation_id,a.designation_code,a.designation_name,a.is_signing_authority, ");
            sb.append(" a.is_approving_authority,a.description,a.status_id  ");
            sb.append(" FROM designation_master a ");
            sb.append(" JOIN employee_type_master b ON  a.fk_employee_type_id = b.pk_employee_type_id ");
            sb.append(" WHERE a.is_approving_authority =1 AND (b.employee_type_name=? OR b.employee_type_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN,
                        ApplicationConstants.EMPLOYEE_TYPE_STORES_USER});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    DesignationDTO designationDTO = new DesignationDTO();

                    designationDTO.setDesignationID((Long) (retMap.get("pk_designation_id")));
                    designationDTO.setDesignationName((String) (retMap.get("designation_name")));
                    designationDTO.setDesignationCode((String) (retMap.get("designation_code")));
                    designationDTO.setIsSigningAuthority((Long) (retMap.get("is_signing_authority")));
                    designationDTO.setIsApprovingAuthority((Long) (retMap.get("is_approving_authority")));
                    designationDTO.setDescription((String) (retMap.get("description")));
                    designationDTO.setRowStatusKey((Long) (retMap.get("status_id")));
                    designationDTO.setEncFieldValue(encryptDecrypt.encrypt(designationDTO.getDesignationID() + ""));

                    desigLi.add(designationDTO);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllStoresApprovingAuthorityDesignations:", e);
        }
        return desigLi;
    }

}
