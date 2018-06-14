/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dao.daoImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.despatch.dto.InwardDTO;
import com.pdms.despatch.itemsDto.InwardPoItemsDTO;
import com.pdms.despatch.itemsDto.InwardTempDTO;
import com.pdms.despatch.itemsDto.InwardVenItemsDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author naagu
 */
@Repository
public class InwardDAOImpl {
    
    private static final Logger logger = Logger.getLogger(InwardDAOImpl.class);

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
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public InwardDAOImpl(){
        
    }
    
    public int insertInwardDetails(final InwardTempDTO inObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    int retVenItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO inward_details  ");
                        sb.append("(inwardId,slno,fileNumber,type,receiveDate,");
                        sb.append("session_id,cur_date)");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?)");

                        List<InwardDTO> inwaObj = inObj.getInwardDTO();
                        for (InwardDTO Obj : inwaObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{Obj.getInwardId(),
                                        Obj.getSlno(),
                                        Obj.getFileNumber(),
                                        Obj.getType().trim(),
                                        Obj.getReceiveDate().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO inward_poitems_details  ");
                        sbItem.append("(poItemsId,filenumber,ponumber,posrno,vendorcode,");                        
                        sbItem.append("userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?)");

                        List<InwardPoItemsDTO> items = inObj.getInwardPoDTO();
                        for (InwardPoItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getPoItemsId(),
                                        itemObj.getFilenumber(),
                                        itemObj.getPonumber(),
                                        itemObj.getPosrno().trim(),
                                        itemObj.getVendorcode().trim(),                                        
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }
                        
                        StringBuilder sbVen = new StringBuilder();
                        sbVen.append(" INSERT INTO inward_venitems_details  ");
                        sbVen.append("(venItemId,filenumber,itemid,vendorid,");                        
                        sbVen.append("userid,cur_date) ");
                        sbVen.append(" VALUES ");
                        sbVen.append("(?,?,?,?,?,?)");

                        List<InwardVenItemsDTO> item = inObj.getInwardVenDTO();
                        for (InwardVenItemsDTO itemObj : item) {
                            retVenItems = getJdbcTemplate().update(sbVen.toString(),
                                    new Object[]{itemObj.getVenItemId(),
                                        itemObj.getFilenumber(),
                                        itemObj.getItemid(),
                                        itemObj.getVendorid(),                                                                               
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }

                        if ((retValForm > 0) && (retvalItems > 0) && (retVenItems > 0)) {
                            retVal = retvalItems;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -5;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertInwardDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertInwardDetails:: " + e.getMessage());
        }
    }
    
    public List<InwardDTO> getInwardRecord() throws AppException {
        List<InwardDTO> inDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM inward_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    InwardDTO indto = new InwardDTO();
                    indto.setInwardId((Long) (retMap.get("inwardId")));
                    indto.setSlno((Long) (retMap.get("slno")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber"))); 
                    indto.setType((String) (retMap.get("type")));
                    indto.setReceiveDate((String) (retMap.get("receiveDate")));                    
                    inDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getInwardRecord:", e);
        }
        return inDTO;
    }
    
    public List<InwardDTO> getInwardReById(final long id) throws AppException {
        List<InwardDTO> waDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM inward_details WHERE inwardId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    InwardDTO indto = new InwardDTO();
                    indto.setInwardId((Long) (retMap.get("inwardId")));
                    indto.setSlno((Long) (retMap.get("slno")));
                    indto.setFileNumber((Long) (retMap.get("fileNumber"))); 
                    indto.setType((String) (retMap.get("type")));
                    indto.setReceiveDate((String) (retMap.get("receiveDate")));                    
                    waDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getInwardReById:", e);
        }
        return waDTO;
    }
    
    public int updateInwardDetail(final List<InwardDTO> inObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE inward_details  ");
            sb.append(" SET slno=?,fileNumber=?,type=?,receiveDate=?,");  
            sb.append("session_id=?,cur_date=? ");
            sb.append(" WHERE inwardId=? ");
            
            for(InwardDTO Obj : inObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        Obj.getSlno(),
                        Obj.getFileNumber(),    
                        Obj.getType().trim(), 
                        Obj.getReceiveDate().trim(),                           
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getInwardId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateInwardDetail:",e);
        }
        return retVal;
    }
    
    public long getSlNoInwardForIncrem() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM inward_details WHERE slno=(SELECT MAX(slno) FROM inward_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   InwardDTO icdto = new InwardDTO();
                    icdto.setSlno((long)(resultMap.get("slno")));
                    maxNo = (long)(resultMap.get("slno")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSlNoInwardForIncrem:",e);
        }
        return maxNo;
    }
    
}
