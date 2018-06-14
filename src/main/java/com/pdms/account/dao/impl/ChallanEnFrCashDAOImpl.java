/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.exception.AppException;
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
public class ChallanEnFrCashDAOImpl {
    private static final Logger logger = Logger.getLogger(ChallanEnFrCashDAOImpl.class);

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
    public ChallanEnFrCashDAOImpl(){
        
    }
    
    
    public int insertChallanEntryCash(final List<ChallanEnFrCashDTO> chaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO challan_cash_details  ");
            sb.append("(challanId,challanNo,challanDate,month,challanYear,challanAmt,headOfAc,");
            sb.append("scode,detailedHead,subHead,desOfHead,details,total,");            
            sb.append("session_id,challan_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,");            
            sb.append("?,?,?,?,?)");
            
            for(ChallanEnFrCashDTO VDTO : chaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{VDTO.getChallanId(),
                            VDTO.getChallanNo(),
                            dateUtil.dateConvertionFromJSPToDB(VDTO.getChallanDate().trim()),
                            VDTO.getMonth().trim(),
                            VDTO.getChallanYear().trim(),
                            VDTO.getChallanAmt(),
                            VDTO.getHeadOfAc().trim(),
                            VDTO.getScode().trim(),
                            VDTO.getDetailedHead().trim(),
                            VDTO.getSubHead().trim(),
                            VDTO.getDesOfHead().trim(),
                            VDTO.getDetails().trim(),                           
                            VDTO.getTotal(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertChallanEntryCash:",e);
        }
        return retVal;
    }
    
    public List<ChallanEnFrCashDTO> getChallanEnFrCashDaRecord() throws AppException {
        List<ChallanEnFrCashDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM challan_cash_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ChallanEnFrCashDTO dTO = new ChallanEnFrCashDTO();
                    dTO.setChallanId((Long) (retMap.get("challanId")));
                    dTO.setChallanNo((Long) (retMap.get("challanNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("challanDate")));
                    dTO.setChallanDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    dTO.setMonth((String) (retMap.get("month")));
                    dTO.setChallanYear((String) (retMap.get("challanYear")));  
                    dTO.setChallanAmt((BigDecimal) (retMap.get("challanAmt")));
                    dTO.setHeadOfAc((String) (retMap.get("headOfAc")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setDetailedHead((String) (retMap.get("detailedHead")));
                    dTO.setSubHead((String) (retMap.get("subHead")));
                    dTO.setDesOfHead((String) (retMap.get("desOfHead")));
                    dTO.setDetails((String) (retMap.get("details")));                    
                    dTO.setTotal((BigDecimal) (retMap.get("total")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChallanEnFrCashDaRecord:", e);
        }
        return list;
    }
    
    public long getChalNoEnFrCashForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM challan_cash_details WHERE challanNo=(SELECT MAX(challanNo) FROM challan_cash_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   InspectionClearanceDTO icdto = new InspectionClearanceDTO();
                    icdto.setGrOrrNumber((long)(resultMap.get("challanNo")));
                    maxNo = (long)(resultMap.get("challanNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChalNoEnFrCashForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<ChallanEnFrCashDTO> getChallanEnCashReById(final long id) throws AppException {
        List<ChallanEnFrCashDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM challan_cash_details WHERE challanId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ChallanEnFrCashDTO dTO = new ChallanEnFrCashDTO();
                    dTO.setChallanId((Long) (retMap.get("challanId")));
                    dTO.setChallanNo((Long) (retMap.get("challanNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("challanDate")));
                    dTO.setChallanDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    dTO.setMonth((String) (retMap.get("month")));
                    dTO.setChallanYear((String) (retMap.get("challanYear")));  
                    dTO.setChallanAmt((BigDecimal) (retMap.get("challanAmt")));
                    dTO.setHeadOfAc((String) (retMap.get("headOfAc")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setDetailedHead((String) (retMap.get("detailedHead")));
                    dTO.setSubHead((String) (retMap.get("subHead")));
                    dTO.setDesOfHead((String) (retMap.get("desOfHead")));
                    dTO.setDetails((String) (retMap.get("details")));                    
                    dTO.setTotal((BigDecimal) (retMap.get("total")));
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getChallanEnCashReById:", e);
        }
        return List;
    }
    
    public int updateChallanEnFrCashDetail(final List<ChallanEnFrCashDTO> chaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE challan_cash_details  ");
            sb.append(" SET challanNo=?,challanDate=?,month=?,challanYear=?,");  
            sb.append("challanAmt=?,headOfAc=?,scode=?,detailedHead=?,subHead=?, ");
            sb.append("desOfHead=?,details=?,total=?,session_id=?,challan_date=?  ");
            sb.append(" WHERE challanId=? ");
            
            for(ChallanEnFrCashDTO VDTO : chaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                            VDTO.getChallanNo(),
                            dateUtil.dateConvertionFromJSPToDB(VDTO.getChallanDate().trim()),
                            VDTO.getMonth().trim(),
                            VDTO.getChallanYear().trim(),
                            VDTO.getChallanAmt(),
                            VDTO.getHeadOfAc().trim(),
                            VDTO.getScode().trim(),
                            VDTO.getDetailedHead().trim(),
                            VDTO.getSubHead().trim(),
                            VDTO.getDesOfHead().trim(),
                            VDTO.getDetails().trim(),                           
                            VDTO.getTotal(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            VDTO.getChallanId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateChallanEnFrCashDetail:",e);
        }
        return retVal;
    }
    
}
