/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.dto.TransporterDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
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
public class CurrencyDAOImpl {
    
    private static final Logger logger = Logger.getLogger(CurrencyDAOImpl.class);

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
    public CurrencyDAOImpl(){
        
    }
    
    
    public int insertCurrencyDetail(final List<CurrencyDTO> curObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO currency_details  ");
            sb.append("(currencyID,currencyName,currencyCode,currencyDes,session_id,cur_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(CurrencyDTO CYDTO : curObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{CYDTO.getCurrencyId(),
                        CYDTO.getCurrencyName().trim(),
                        CYDTO.getCurrencyCode().trim(),
                        CYDTO.getCurrencyDes().trim(),
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
    
    public List<CurrencyDTO> getCurrencyDetails() throws AppException {
        List<CurrencyDTO> CUDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM currency_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CurrencyDTO CDTO = new CurrencyDTO();
                    CDTO.setCurrencyId((Long) (retMap.get("currencyID")));
                    CDTO.setCurrencyCode((String) (retMap.get("currencyCode")));
                    CDTO.setCurrencyName((String) (retMap.get("currencyName")));
                    CDTO.setCurrencyDes((String) (retMap.get("currencyDes")));                    
                    CUDTO.add(CDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return CUDTO;
    }
    
    public List<CurrencyDTO> getCurrencyReById(final long id) throws AppException {
        List<CurrencyDTO> CUDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM currency_details WHERE currencyID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CurrencyDTO CDTO = new CurrencyDTO();
                    CDTO.setCurrencyId((Long) (retMap.get("currencyID")));
                    CDTO.setCurrencyCode((String) (retMap.get("currencyCode")));
                    CDTO.setCurrencyName((String) (retMap.get("currencyName")));
                    CDTO.setCurrencyDes((String) (retMap.get("currencyDes")));                    
                    CUDTO.add(CDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return CUDTO;
    }
    
    public int updateCurrencyDetail(final List<CurrencyDTO> curObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE currency_details  ");
            sb.append(" SET currencyName=?,currencyCode=?,currencyDes=?,session_id=?,cur_date=? ");            
            sb.append(" WHERE currencyID=? ");
            
            for(CurrencyDTO CYDTO : curObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{CYDTO.getCurrencyName().trim(),
                        CYDTO.getCurrencyCode().trim(),
                        CYDTO.getCurrencyDes().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        CYDTO.getCurrencyId()
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
    
    public long checkDuplicateCurrency(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {

            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM currency_details ";
                qry+=" WHERE LOWER(currencyName) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//                
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM currency_details ";
                qry+=" WHERE LOWER(currencyCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM currency_details ";
                qry+=" WHERE LOWER(currencyCode)=? AND LOWER(currencyName) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase(),gName.toLowerCase()},
                        Long.class);//                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateUnit:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public CurrencyDTO getSelectedCurrency(final long currencyID) throws AppException
    {
        CurrencyDTO cuObj = new CurrencyDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT currencyID,currencyName,currencyCode,currencyDes ");
            sb.append(" FROM currency_details WHERE  currencyID=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{currencyID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    cuObj.setCurrencyId((Long)(resultMap.get("currencyID")));
                    cuObj.setCurrencyName((String)(resultMap.get("currencyName")));
                    cuObj.setCurrencyCode((String)(resultMap.get("currencyCode")));
                    cuObj.setCurrencyDes((String)(resultMap.get("currencyDes")));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedUnit:",e);
        }
        return cuObj;
    }
    
}
