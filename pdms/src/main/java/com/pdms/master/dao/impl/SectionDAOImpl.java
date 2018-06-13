/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.SectionDTO;
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
public class SectionDAOImpl {

    private static final Logger logger = Logger.getLogger(SectionDAOImpl.class);
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
    public SectionDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<SectionDTO> getAllSectionDetails(final String empTypeName) throws AppException {
        List<SectionDTO> sectionLi = new LinkedList<>();
        
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_section_id,a.section_code,a.section_name,a.description,a.status_id, ");
            sb.append(" a.fk_employee_type_id ");
            sb.append(" FROM section_master a ");
            sb.append(" JOIN employee_type_master b ON a.fk_employee_type_id = b.pk_employee_type_id ");
            //sb.append(" WHERE b.employee_type_name IN (?) ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    SectionDTO sectionObj = new SectionDTO();

                    sectionObj.setSectionID((Long) (retMap.get("pk_section_id")));
                    sectionObj.setEmployeeTypeID((Long) (retMap.get("fk_employee_type_id")));
                    sectionObj.setSectionCode((String) (retMap.get("section_code")));
                    sectionObj.setSectionName((String) (retMap.get("section_name")));
                    sectionObj.setDescription((String) (retMap.get("description")));
                    sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    sectionObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    sectionLi.add(sectionObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllSectionDetails:", e);
        }
        return sectionLi;
    }

    public SectionDTO getSeclectedSectionByID(final long sectionID) throws AppException {
        SectionDTO sectionObj = new SectionDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_section_id,section_code,section_name,description,status_id,  ");
            sb.append(" fk_employee_type_id  ");
            sb.append(" FROM section_master WHERE pk_section_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sectionID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    sectionObj.setSectionID(sectionID);
                    sectionObj.setEmployeeTypeID((Long) (retMap.get("fk_employee_type_id")));
                    sectionObj.setSectionCode((String) (retMap.get("section_code")));
                    sectionObj.setSectionName((String) (retMap.get("section_name")));
                    sectionObj.setDescription((String) (retMap.get("description")));
                    sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    sectionObj.setRowStatusKey((Long) (retMap.get("status_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSeclectedSectionByID:", e);
        }

        return sectionObj;
    }

    public int insertSectionDetail(final SectionDTO sectionObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" INSERT INTO section_master ");
            sb.append(" (section_code,section_name,fk_employee_type_id,description,status_id,");
            sb.append(" created_by,created_on) ");
            sb.append(" VALUES (?,?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{sectionObj.getSectionCode().trim(),
                        sectionObj.getSectionName().trim(),
                        sectionObj.getEmployeeTypeID(),
                        sectionObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertSectionDetail:", e);
        }

        return retVal;
    }

    public int updateSectionDetail(final SectionDTO sectionObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" UPDATE section_master SET ");
            sb.append(" section_code=?,section_name=?,description=?,status_id=?,");
            sb.append(" fk_employee_type_id=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE  pk_section_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{sectionObj.getSectionCode().trim(),
                        sectionObj.getSectionName().trim(),
                        sectionObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sectionObj.getEmployeeTypeID(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        sectionObj.getSectionID()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateSectionDetail:", e);
        }

        return retVal;
    }

    
    public long checkDuplicateSection(final String secCode, final String secName) throws AppException {
        long dpCnt = 0;
        try {

            if(secCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM section_master ";
                qry+=" WHERE LOWER(section_name)= ? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{
                        secName.trim().toLowerCase()},
                        Long.class);
            }
            else if(secName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM section_master ";
                qry+=" WHERE LOWER(section_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{secCode.trim().toLowerCase()},
                        Long.class);
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM section_master ";
                qry+=" WHERE LOWER(section_code)=? OR LOWER(section_name)= ? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{secCode.trim().toLowerCase(),
                        secName.trim().toLowerCase()},
                        Long.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateSection:: " + e.getMessage());
        }
        return dpCnt;
    }
    
    
    public SectionDTO getSeclectedSectionDetailByName(final String sectionName) throws AppException {
        SectionDTO sectionObj = new SectionDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_section_id,section_code,section_name,description,status_id  ");
            sb.append(" FROM section_master WHERE section_name=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sectionName});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    sectionObj.setSectionID((Long)(retMap.get("pk_section_id")));
                    sectionObj.setSectionCode((String) (retMap.get("section_code")));
                    sectionObj.setSectionName((String) (retMap.get("section_name")));
                    sectionObj.setDescription((String) (retMap.get("description")));
                    sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    sectionObj.setRowStatusKey((Long) (retMap.get("status_id")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSeclectedSectionByID:", e);
        }

        return sectionObj;
    }
    
    public List<SectionDTO> getSectionDeBySecCode(final String sectionCode) throws AppException {        
        List<SectionDTO> secLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_section_id,section_code,section_name,description,status_id  ");
            sb.append(" FROM section_master WHERE section_code=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sectionCode});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    SectionDTO sectionObj = new SectionDTO();
                    sectionObj.setSectionID((Long)(retMap.get("pk_section_id")));
                    sectionObj.setSectionCode((String) (retMap.get("section_code")));
                    sectionObj.setSectionName((String) (retMap.get("section_name")));
                    sectionObj.setDescription((String) (retMap.get("description")));
                    sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    sectionObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    secLi.add(sectionObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSectionDeBySecCode:", e);
        }

        return secLi;
    }
    
   
}
