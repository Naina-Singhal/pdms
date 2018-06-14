/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.dto.SignatoryDTO;
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
public class RtgsDAOImpl {
    private static final Logger logger = Logger.getLogger(RtgsDAOImpl.class);

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
    public RtgsDAOImpl(){
        
    }
    
    
    public int insertRtgsDetail(final List<RtgsDTO> rtgsObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO rtgs_details  ");
            sb.append("(rtgsId,vendorCode,rtgsNo,accountNo,ifscCode,branch,city,session_id,rtgs_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?)");
            
            for(RtgsDTO RDTO : rtgsObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{RDTO.getRtgsId(),                                                
                        RDTO.getVendorCode().trim(),
                        RDTO.getRtgsNo(),
                        RDTO.getAccountNo().trim(), 
                        RDTO.getIfscCode().trim(),
                        RDTO.getBranch().trim(),
                        RDTO.getCity().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertRtgsDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateRtgs(final String gCode) throws AppException {
        long dpCnt = 0;
        try {            
                String qry = "SELECT COUNT(1) FROM rtgs_details ";
                qry+=" WHERE LOWER(vendorCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//  

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateRtgs:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public List<RtgsDTO> getRtgsDetails() throws AppException {
        List<RtgsDTO> RDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM rtgs_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RtgsDTO dTO = new RtgsDTO();
                    dTO.setRtgsId((Long) (retMap.get("rtgsId")));
                    dTO.setVendorCode((String) (retMap.get("vendorCode")));
                    dTO.setRtgsNo((Long) (retMap.get("rtgsNo")));
                    dTO.setAccountNo((String) (retMap.get("accountNo")));
                    dTO.setIfscCode((String) (retMap.get("ifscCode")));  
                    dTO.setBranch((String) (retMap.get("branch")));
                    dTO.setCity((String) (retMap.get("city")));
                    RDTO.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRtgsDetails:", e);
        }
        return RDTO;
    }
    
    public List<RtgsDTO> getRtgsReById(final long id) throws AppException {
        List<RtgsDTO> STDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM rtgs_details WHERE rtgsId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RtgsDTO dTO = new RtgsDTO();
                    dTO.setRtgsId((Long) (retMap.get("rtgsId")));
                    dTO.setVendorCode((String) (retMap.get("vendorCode")));
                    dTO.setRtgsNo((Long) (retMap.get("rtgsNo")));
                    dTO.setAccountNo((String) (retMap.get("accountNo")));
                    dTO.setIfscCode((String) (retMap.get("ifscCode")));  
                    dTO.setBranch((String) (retMap.get("branch")));
                    dTO.setCity((String) (retMap.get("city")));                     
                    STDTO.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRtgsReById:", e);
        }
        return STDTO;
    }
    
    public RtgsDTO getSelectedRtgs(final long rtgsID) throws AppException
    {
        RtgsDTO rtgsObj = new RtgsDTO();
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM rtgs_details WHERE  rtgsId=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{rtgsID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    rtgsObj.setRtgsId((Long) (resultMap.get("rtgsId")));
                    rtgsObj.setVendorCode((String) (resultMap.get("vendorCode")));
                    rtgsObj.setRtgsNo((Long) (resultMap.get("rtgsNo")));
                    rtgsObj.setAccountNo((String) (resultMap.get("accountNo")));
                    rtgsObj.setIfscCode((String) (resultMap.get("ifscCode")));  
                    rtgsObj.setBranch((String) (resultMap.get("branch")));
                    rtgsObj.setCity((String) (resultMap.get("city")));                 
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedRtgs:",e);
        }
        return rtgsObj;
    }
    
    public int updateRtgsDetail(final List<RtgsDTO> rtgsObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE rtgs_details  ");
            sb.append(" SET vendorCode=?,rtgsNo=?,accountNo=?,ifscCode=?,branch=?,city=?,session_id=?,rtgs_date=? ");            
            sb.append(" WHERE rtgsId=? ");
            
            for(RtgsDTO SDTO : rtgsObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SDTO.getVendorCode().trim(),
                        SDTO.getRtgsNo(),
                        SDTO.getAccountNo().trim(),
                        SDTO.getIfscCode().trim(),
                        SDTO.getBranch().trim(),
                        SDTO.getCity().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        SDTO.getRtgsId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateRtgsDetail:",e);
        }
        return retVal;
    }
    
    public long getRtgsNoFrIncre() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM rtgs_details WHERE rtgsNo=(SELECT MAX(rtgsNo) FROM rtgs_details) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                   RtgsDTO vdto = new RtgsDTO();                    
                    vdto.setRtgsNo((Long)(resultMap.get("rtgsNo")));
                    maxNo = (Long) resultMap.get("rtgsNo"); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRtgsNoFrIncre:",e);
        }
        return maxNo;
    }
    
    public List<RtgsDTO> getRtgsNoByVenCode(final String venCode) throws AppException {
        List<RtgsDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT rtgsNo FROM rtgs_details WHERE vendorCode ='"+venCode+"' ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RtgsDTO dTO = new RtgsDTO();
                    dTO.setRtgsNo((long) (retMap.get("rtgsNo")));                    
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRtgsNoByVenCode:", e);
        }
        return list;
    }
}
