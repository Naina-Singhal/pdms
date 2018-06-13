/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.MonthlyReportDTO;
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
 * @author naagu
 */
@Repository
public class MonthlyReportDAOImpl {
    private static final Logger logger = Logger.getLogger(MonthlyReportDAOImpl.class);

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
    public MonthlyReportDAOImpl(){
        
    }
    
    
    public int insertMonthlyReportDetail(final List<MonthlyReportDTO> monObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO monthly_report_master  ");
            sb.append(" (monthlyReId,code,name,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");
            
            for(MonthlyReportDTO Obj : monObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getMonthlyReId(),
                        Obj.getCode().trim(),
                        Obj.getName().trim(),                                                
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertMonthlyReportDetail:",e);
        }
        return retVal;
    }
    
    public List<MonthlyReportDTO> getMonthlyReportRecord() throws AppException {
        List<MonthlyReportDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM monthly_report_master ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    MonthlyReportDTO indto = new MonthlyReportDTO();
                    indto.setMonthlyReId((Long) (retMap.get("monthlyReId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setName((String) (retMap.get("name")));                                       
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMonthlyReportRecord:", e);
        }
        return enqDTO;
    }
    
    public List<MonthlyReportDTO> getMonthlyReportRecordById(final long id) throws AppException {
        List<MonthlyReportDTO> enqDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM monthly_report_master WHERE monthlyReId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    MonthlyReportDTO indto = new MonthlyReportDTO();
                    indto.setMonthlyReId((Long) (retMap.get("monthlyReId")));
                    indto.setCode((String) (retMap.get("code")));
                    indto.setName((String) (retMap.get("name")));                  
                    enqDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMonthlyReportRecordById:", e);
        }
        return enqDTO;
    }
    
    public int updateMonthlyReportDetail(final List<MonthlyReportDTO> monObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE monthly_report_master  ");
            sb.append(" SET code=?,name=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE monthlyReId=? ");
            
            for(MonthlyReportDTO obj : monObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{obj.getCode().trim(),                        
                        obj.getName().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        obj.getMonthlyReId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateMonthlyReportDetail:",e);
        }
        return retVal;
    }
    
    
}
