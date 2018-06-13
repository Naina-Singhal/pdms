/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.LrEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.LrEnDeFrStoresDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class LrDeEnStoresDAOImpl {
    
     private static final Logger logger = Logger.getLogger(LrDeEnStoresDAOImpl.class);

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
    public LrDeEnStoresDAOImpl(){
        
    }
    
    
    public int insertLRNoStoresDetail(final List<LrEnDeFrStoresDTO> lrObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO lr_stores_details  ");
            sb.append("(lrEnDeStoresId,poNumber,vendorName,challanNo,rrAndLrNumber,rrAndLrDate,numOfPackage, "); 
            sb.append("freight,fromPlace,toPlace,nameTransporter,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(LrEnDeFrStoresDTO Obj : lrObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getLrEnDeStoresId(),
                        Obj.getPoNumber(),
                        Obj.getVendorName().trim(),
                        Obj.getChallanNo(),
                        Obj.getRrAndLrNumber(),
                        dateUtil.dateConvertionFromJSPToDB(Obj.getRrAndLrDate().trim()),
                        Obj.getNumOfPackage().trim(),
                        Obj.getFreight().trim(),
                        Obj.getFromPlace().trim(),
                        Obj.getToPlace().trim(),
                        Obj.getNameTransporter().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertLRNoStoresDetail:",e);
        }
        return retVal;
    }
    
    public List<LrEnDeFrStoresDTO> getLrEntryStoresDetails() throws AppException {
        List<LrEnDeFrStoresDTO> LrDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM lr_stores_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEnDeFrStoresDTO ledto = new LrEnDeFrStoresDTO();
                    ledto.setLrEnDeStoresId((Long) (retMap.get("lrEnDeStoresId")));
                    ledto.setPoNumber((Long) (retMap.get("poNumber")));
                    ledto.setVendorName((String) (retMap.get("vendorName")));
                    ledto.setChallanNo((Long) (retMap.get("challanNo")));
                    ledto.setRrAndLrNumber((Long) (retMap.get("rrAndLrNumber")));                    
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("rrAndLrDate")));
                    ledto.setRrAndLrDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    ledto.setNumOfPackage((String) (retMap.get("numOfPackage")));
                    ledto.setFreight((String) (retMap.get("freight")));
                    ledto.setFromPlace((String) (retMap.get("fromPlace")));
                    ledto.setToPlace((String) (retMap.get("toPlace")));
                    ledto.setNameTransporter((String) (retMap.get("nameTransporter")));
                    LrDTO.add(ledto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrEntryStoresDetails:", e);
        }
        return LrDTO;
    }
    
    public List<LrEnDeFrStoresDTO> getLrStoresDetailsReById(final long id) throws AppException {
        List<LrEnDeFrStoresDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM lr_stores_details WHERE lrEnDeStoresId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEnDeFrStoresDTO ledto = new LrEnDeFrStoresDTO();
                    ledto.setLrEnDeStoresId((Long) (retMap.get("lrEnDeStoresId")));
                    ledto.setPoNumber((Long) (retMap.get("poNumber")));
                    ledto.setVendorName((String) (retMap.get("vendorName")));
                    ledto.setChallanNo((Long) (retMap.get("challanNo")));
                    ledto.setRrAndLrNumber((Long) (retMap.get("rrAndLrNumber")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("rrAndLrDate")));
                    ledto.setRrAndLrDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    ledto.setNumOfPackage((String) (retMap.get("numOfPackage")));
                    ledto.setFreight((String) (retMap.get("freight")));
                    ledto.setFromPlace((String) (retMap.get("fromPlace")));
                    ledto.setToPlace((String) (retMap.get("toPlace")));
                    ledto.setNameTransporter((String) (retMap.get("nameTransporter")));
                    List.add(ledto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrStoresDetailsReById:", e);
        }
        return List;
    }
    
    public int updateLrStoresDetailsDetail(final List<LrEnDeFrStoresDTO> lrObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE lr_stores_details  ");
            sb.append(" SET poNumber=?,vendorName=?,challanNo=?,rrAndLrNumber=?,rrAndLrDate=?,numOfPackage=?,freight=?, ");            
            sb.append(" fromPlace=?,toPlace=?,nameTransporter=?,session_id=?,cur_date=? WHERE lrEnDeStoresId=? ");
            
            for(LrEnDeFrStoresDTO Obj : lrObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{
                        Obj.getPoNumber(),
                        Obj.getVendorName().trim(),
                        Obj.getChallanNo(),
                        Obj.getRrAndLrNumber(),
                        dateUtil.dateConvertionFromJSPToDB(Obj.getRrAndLrDate().trim()),
                        Obj.getNumOfPackage().trim(),
                        Obj.getFreight().trim(),
                        Obj.getFromPlace().trim(),
                        Obj.getToPlace().trim(),
                        Obj.getNameTransporter().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getLrEnDeStoresId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateLrStoresDetailsDetail:",e);
        }
        return retVal;
    }
    
    public List<LrEnDeFrStoresDTO> getLrStoresReByPoNo(final long ponumber) throws AppException {
        List<LrEnDeFrStoresDTO> dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM lr_stores_details WHERE poNumber=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{ponumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEnDeFrStoresDTO ledto = new LrEnDeFrStoresDTO();
                    ledto.setLrEnDeStoresId((Long) (retMap.get("lrEnDeStoresId")));
                    ledto.setPoNumber((Long) (retMap.get("poNumber")));
                    ledto.setVendorName((String) (retMap.get("vendorName")));
                    ledto.setChallanNo((Long) (retMap.get("challanNo")));
                    ledto.setRrAndLrNumber((Long) (retMap.get("rrAndLrNumber")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("rrAndLrDate")));
                    ledto.setRrAndLrDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    ledto.setNumOfPackage((String) (retMap.get("numOfPackage")));
                    ledto.setFreight((String) (retMap.get("freight")));
                    ledto.setFromPlace((String) (retMap.get("fromPlace")));
                    ledto.setToPlace((String) (retMap.get("toPlace")));
                    ledto.setNameTransporter((String) (retMap.get("nameTransporter")));
                    dto.add(ledto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrStoresReByPoNo:", e);
        }
        return dto;
    }
}
