/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.GeneralTermsDTO;
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
 * @author myassessment
 */
@Repository
public class GeneralTermsDAOImpl {
    private static final Logger logger = Logger.getLogger(GeneralTermsDAOImpl.class);

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
    public GeneralTermsDAOImpl(){
        
    }
    
    
    public int insertGeneralTermsDetail(final List<GeneralTermsDTO> termObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO general_terms_details  ");
            sb.append("(descriptionId,description, "); 
            sb.append("session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?)");
            
            for(GeneralTermsDTO Obj : termObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getDescriptionId(),                        
                        Obj.getDescription().trim(),                                             
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertGeneralTermsDetail:",e);
        }
        return retVal;
    }
    
    public List<GeneralTermsDTO> getGeneralTermsRecord() throws AppException {
        List<GeneralTermsDTO> DTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM general_terms_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    GeneralTermsDTO dto = new GeneralTermsDTO();
                    dto.setDescriptionId((Long) (retMap.get("descriptionId")));
                    dto.setDescription((String) (retMap.get("description")));                                       
                    DTO.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getGeneralTermsRecord:", e);
        }
        return DTO;
    }
    
    public List<GeneralTermsDTO> getGeneralTermsReById(final long id) throws AppException {
        List<GeneralTermsDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM general_terms_details WHERE descriptionId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    GeneralTermsDTO indto = new GeneralTermsDTO();
                    indto.setDescriptionId((Long) (retMap.get("descriptionId")));
                    indto.setDescription((String) (retMap.get("description")));                                       
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getGeneralTermsReById:", e);
        }
        return enqDTO;
    }
    
    public int updateGeneralTermsDetail(final List<GeneralTermsDTO> termObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE general_terms_details  ");
            sb.append(" SET description=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE descriptionId=? ");
            
            for(GeneralTermsDTO obj : termObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getDescription().trim(),                          
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getDescriptionId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateGeneralTermsDetail:",e);
        }
        return retVal;
    }
    
}
