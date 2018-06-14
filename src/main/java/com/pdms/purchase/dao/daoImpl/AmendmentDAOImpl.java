/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.dao.daoImpl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DescriptionDAOImpl;
import com.pdms.purchase.dto.AmendmentDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
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
public class AmendmentDAOImpl {
    
     private static final Logger logger = Logger.getLogger(AmendmentDAOImpl.class);

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
    public AmendmentDAOImpl(){
        
    }
    
    
    public int insertAmendmentDetail(final List<AmendmentDTO> ameObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO amendment_details  ");
            sb.append("(amendmentId,fileNumber,poNumber,vendorName,vendorAddress,BreifDescription, "); 
            sb.append("BgClause,reference,date,annexure,code,temp, "); 
            sb.append("bgNumber,bgDate,amount,expireDate,forOne,deliveryPeriod, "); 
            sb.append("otherAmendments,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(AmendmentDTO AmeDTO : ameObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{AmeDTO.getAmendmentId(),
                        AmeDTO.getFileNumber(),
                        AmeDTO.getPoNumber(),
                        AmeDTO.getVendorName().trim(),
                        AmeDTO.getVendorAddress().trim(),
                        AmeDTO.getBreifDescription().trim(),
                        AmeDTO.getBgClause().trim(),
                        AmeDTO.getReference().trim(),
                        AmeDTO.getDate().trim(),
                        AmeDTO.getAnnexure().trim(),
                        AmeDTO.getCode().trim(),
                        AmeDTO.getTemp().trim(),
                        AmeDTO.getBgNumber().trim(),
                        AmeDTO.getBgDate().trim(),
                        AmeDTO.getAmount(),
                        AmeDTO.getExpireDate().trim(),
                        AmeDTO.getForOne().trim(),
                        AmeDTO.getDeliveryPeriod().trim(),
                        AmeDTO.getOtherAmendments().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertIbcNoDetail:",e);
        }
        return retVal;
    }
    
    public List<AmendmentDTO> getAmendmentRecord() throws AppException {
        List<AmendmentDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM amendment_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    AmendmentDTO indto = new AmendmentDTO();
                    indto.setAmendmentId((Long) (retMap.get("amendmentId")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber")));
                    indto.setPoNumber((Long) (retMap.get("poNumber"))); 
                    indto.setVendorName((String) (retMap.get("vendorName")));
                    indto.setVendorAddress((String) (retMap.get("vendorAddress")));
                    indto.setBreifDescription((String) (retMap.get("BreifDescription")));
                    indto.setBgClause((String) (retMap.get("BgClause")));
                    indto.setReference((String) (retMap.get("reference")));
                    indto.setDate((String) (retMap.get("date")));
                    indto.setAnnexure((String) (retMap.get("annexure")));
                    indto.setCode((String) (retMap.get("code"))); 
                    indto.setTemp((String) (retMap.get("temp")));
                    indto.setBgNumber((String) (retMap.get("bgNumber")));
                    indto.setBgDate((String) (retMap.get("bgDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setExpireDate((String) (retMap.get("expireDate")));
                    indto.setForOne((String) (retMap.get("forOne")));
                    indto.setDeliveryPeriod((String) (retMap.get("deliveryPeriod")));
                    indto.setOtherAmendments((String) (retMap.get("otherAmendments")));
                    list.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAmendmentRecord:", e);
        }
        return list;
    }
    
    public List<AmendmentDTO> getAmendmentReById(final long id) throws AppException {
        List<AmendmentDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM amendment_details WHERE amendmentId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    AmendmentDTO indto = new AmendmentDTO();
                    indto.setAmendmentId((Long) (retMap.get("amendmentId")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber")));
                    indto.setPoNumber((Long) (retMap.get("poNumber"))); 
                    indto.setVendorName((String) (retMap.get("vendorName")));
                    indto.setVendorAddress((String) (retMap.get("vendorAddress")));
                    indto.setBreifDescription((String) (retMap.get("BreifDescription")));
                    indto.setBgClause((String) (retMap.get("BgClause")));
                    indto.setReference((String) (retMap.get("reference")));
                    indto.setDate((String) (retMap.get("date")));
                    indto.setAnnexure((String) (retMap.get("annexure")));
                    indto.setCode((String) (retMap.get("code"))); 
                    indto.setTemp((String) (retMap.get("temp")));
                    indto.setBgNumber((String) (retMap.get("bgNumber")));
                    indto.setBgDate((String) (retMap.get("bgDate")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setExpireDate((String) (retMap.get("expireDate")));
                    indto.setForOne((String) (retMap.get("forOne")));
                    indto.setDeliveryPeriod((String) (retMap.get("deliveryPeriod")));
                    indto.setOtherAmendments((String) (retMap.get("otherAmendments")));
                    List.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAmendmentReById:", e);
        }
        return List;
    }
    
    public int updateAmendmentDetail(final List<AmendmentDTO> ameObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE amendment_details  ");
            sb.append(" SET fileNumber=?,poNumber=?,vendorName=?,vendorAddress=?,BreifDescription=?,BgClause=?,reference=?,date=?,");  
            sb.append("annexure=?,code=?,temp=?,bgNumber=?,bgDate=?,amount=?,expireDate=?,forOne=?, ");
            sb.append("deliveryPeriod=?,otherAmendments=?,session_id=?,cur_date=?  WHERE amendmentId=? ");
            
            for(AmendmentDTO AmeDTO : ameObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        AmeDTO.getFileNumber(),
                        AmeDTO.getPoNumber(),
                        AmeDTO.getVendorName().trim(),
                        AmeDTO.getVendorAddress().trim(),
                        AmeDTO.getBreifDescription().trim(),
                        AmeDTO.getBgClause().trim(),
                        AmeDTO.getReference().trim(),
                        AmeDTO.getDate().trim(),
                        AmeDTO.getAnnexure().trim(),
                        AmeDTO.getCode().trim(),
                        AmeDTO.getTemp().trim(),
                        AmeDTO.getBgNumber().trim(),
                        AmeDTO.getBgDate().trim(),
                        AmeDTO.getAmount(),
                        AmeDTO.getExpireDate().trim(),
                        AmeDTO.getForOne().trim(),
                        AmeDTO.getDeliveryPeriod().trim(),
                        AmeDTO.getOtherAmendments().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        AmeDTO.getAmendmentId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateAmendmentDetail:",e);
        }
        return retVal;
    }
    
    
}
