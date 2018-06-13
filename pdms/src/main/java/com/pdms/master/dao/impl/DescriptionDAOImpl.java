/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.DescriptionDTO;
import com.pdms.dto.PaymentDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
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
public class DescriptionDAOImpl {
    
     private static final Logger logger = Logger.getLogger(DescriptionDAOImpl.class);

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
    public DescriptionDAOImpl(){
        
    }
    
    
    public int insertDescriptionDetail(final List<DescriptionDTO> desObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO description_details  ");
            sb.append("(descriptionID,description,session_id,des_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?)");
            
            for(DescriptionDTO DSDTO : desObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{DSDTO.getDescriptionID(),
                        DSDTO.getDescription().trim(),                        
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public List<DescriptionDTO> getDescriptionDetails() throws AppException {
        List<DescriptionDTO> DESDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM description_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DescriptionDTO ddto = new DescriptionDTO();
                    ddto.setDescriptionID((Long) (retMap.get("descriptionID")));
                    ddto.setDescription((String) (retMap.get("description")));                    
                    DESDTO.add(ddto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return DESDTO;
    }
    
    public List<DescriptionDTO> getDescriptionReById(final long id) throws AppException {
        List<DescriptionDTO> diDto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM description_details WHERE descriptionID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    DescriptionDTO dTO = new DescriptionDTO();
                    dTO.setDescriptionID((Long) (retMap.get("descriptionID")));
                    dTO.setDescription((String) (retMap.get("description")));               
                    diDto.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDescriptionReById:", e);
        }
        return diDto;
    }
    
    public int updateDescriptionDetail(final List<DescriptionDTO> DesObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE description_details  ");
            sb.append(" SET description=?,session_id=?,des_date=? ");            
            sb.append(" WHERE descriptionID=? ");
            
            for(DescriptionDTO dto : DesObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{dto.getDescription().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        dto.getDescriptionID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateDescriptionDetail:",e);
        }
        return retVal;
    }
}
