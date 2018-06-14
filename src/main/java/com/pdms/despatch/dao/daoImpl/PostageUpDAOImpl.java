/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dao.daoImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.despatch.dto.InwardDTO;
import com.pdms.despatch.dto.PostageCloseDTO;
import com.pdms.despatch.dto.PostageUpDTO;
import com.pdms.despatch.itemsDto.InwardPoItemsDTO;
import com.pdms.despatch.itemsDto.InwardVenItemsDTO;
import com.pdms.despatch.itemsDto.PostageItemsDTO;
import com.pdms.despatch.itemsDto.PostageTempDTO;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author naagu
 */
@Repository
public class PostageUpDAOImpl {
    
    private static final Logger logger = Logger.getLogger(PostageUpDAOImpl.class);

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
    public PostageUpDAOImpl(){
        
    }
    
    
    public int insertPostageUpDetails(final PostageTempDTO upObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;                    
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO postage_details  ");
                        sb.append("(postageId,toDayDate,totalAmt,otBalance,ctBalance,");
                        sb.append("postage,session_id,cur_date)");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?)");

                        List<PostageUpDTO> postObj = upObj.getPostageDTO();
                        for (PostageUpDTO Obj : postObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{Obj.getPostageId(),
                                        Obj.getToDayDate().trim(),
                                        Obj.getTotalAmt(),
                                        Obj.getOtBalance(),
                                        Obj.getCtBalance(),
                                        Obj.getPostage().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO postage_items_details  ");
                        sbItem.append("(postageItemId,dispatchno,nature,vendorid,tods,amount,");
                        sbItem.append("userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?)");

                        List<PostageItemsDTO> items = upObj.getPostageItemsDTO();
                        for (PostageItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getPostageItemId(),
                                        itemObj.getDispatchno(),
                                        itemObj.getNature().trim(),
                                        itemObj.getVendorid(),
                                        itemObj.getTods().trim(),
                                        itemObj.getAmount(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }

                        

                        if ((retValForm > 0) && (retvalItems > 0)) {
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
    
    public List<PostageUpDTO> getPostageUpRecord() throws AppException {
        List<PostageUpDTO> upDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM postage_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostageUpDTO indto = new PostageUpDTO();
                    indto.setPostageId((Long) (retMap.get("postageId")));
                    indto.setToDayDate((String) (retMap.get("toDayDate")));
                    indto.setTotalAmt((BigDecimal) (retMap.get("totalAmt"))); 
                    indto.setOtBalance((BigDecimal) (retMap.get("otBalance")));
                    indto.setCtBalance((BigDecimal) (retMap.get("ctBalance")));
                    indto.setPostage((String) (retMap.get("postage")));                    
                    upDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostageUpRecord:", e);
        }
        return upDTO;
    }
    
    public List<PostageUpDTO> getPostageReById(final long id) throws AppException {
        List<PostageUpDTO> pgDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM postage_details WHERE postageId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostageUpDTO indto = new PostageUpDTO();
                    indto.setPostageId((Long) (retMap.get("postageId")));
                    indto.setToDayDate((String) (retMap.get("toDayDate")));
                    indto.setTotalAmt((BigDecimal) (retMap.get("totalAmt"))); 
                    indto.setOtBalance((BigDecimal) (retMap.get("otBalance")));
                    indto.setCtBalance((BigDecimal) (retMap.get("ctBalance")));
                    indto.setPostage((String) (retMap.get("postage")));                    
                    pgDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostageReById:", e);
        }
        return pgDTO;
    }
    
    public int updatePostageDetail(final List<PostageUpDTO> upObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE postage_details  ");
            sb.append(" SET toDayDate=?,totalAmt=?,otBalance=?,ctBalance=?,");  
            sb.append("postage=?,session_id=?,cur_date=? ");
            sb.append(" WHERE postageId=? ");
            
            for(PostageUpDTO Obj : upObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        Obj.getToDayDate().trim(),
                        Obj.getTotalAmt(),    
                        Obj.getOtBalance(), 
                        Obj.getCtBalance(), 
                        Obj.getPostage().trim(),                                                
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getPostageId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePostageDetail:",e);
        }
        return retVal;
    }
    
    public int saveCloseButton(final List<PostageCloseDTO> closeObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO postage_close  ");
            sb.append("(closeId,toDayDate,status,user_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?)");            
            
            for(PostageCloseDTO Obj : closeObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{Obj.getCloseId(),                       
                        dateUtil.dateConvertionFromJSPToDB( Obj.getToDayDate().trim()),
                        Obj.getStatus(),                                               
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : saveCloseButton:",e);
        }
        return retVal;
    }
    
    public List<PostageCloseDTO> getPostageCloseReByDate(final String todayDate) throws AppException {
        List<PostageCloseDTO> pgDTO = new LinkedList<>();
        try {
            String fromDateDb = dateUtil.dateConvertionFromJSPToDB(todayDate);
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT closeId,toDayDate,status FROM postage_close WHERE toDayDate=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{fromDateDb});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostageCloseDTO indto = new PostageCloseDTO();
                    indto.setCloseId((Long) (retMap.get("closeId")));                    
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("toDayDate")));
                    indto.setToDayDate(preDate);
                    indto.setStatus((int) (retMap.get("status")));
                    pgDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostageCloseReByDate:", e);
        }
        return pgDTO;
    }
    
    public List<PostageItemsDTO> getPostagePdfByDate(final String posdate) throws AppException {
        List<PostageItemsDTO> pgDTO = new LinkedList<>();
        try {
            
            String DateDb = dateUtil.dateConvertionFromJSPToDB(posdate);
            logger.info("------gggg----------"+DateDb);
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT  a.dispatchno,a.nature,a.vendorid,a.tods,a.amount, ");
            sb.append(" p.fileNo,v.vendor_name,v.vendor_city ");
            sb.append(" ");
            sb.append(" FROM postage_items_details a ");
            sb.append(" JOIN post_entry_details p ON a.dispatchno = p.dispatchNo ");
            sb.append(" JOIN vendor_details v ON a.vendorid = v.pk_vendor_id ");
            sb.append("  WHERE DATE(a.cur_date)=? ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{DateDb});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostageItemsDTO indto = new PostageItemsDTO();
                    indto.setDispatchno((Long) (retMap.get("dispatchno")));
                    indto.setNature((String) (retMap.get("nature")));
                    indto.setVendorid((long) (retMap.get("vendorid"))); 
                    indto.setTods((String) (retMap.get("tods")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));                   
                    indto.getPostObj().setFileNo((Long) (retMap.get("fileNo")));   
                    indto.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    indto.getVenObj().setVendorCity((String) (retMap.get("vendor_city")));
                    pgDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostagePdfByDate:", e);
        }
        return pgDTO;
    }
}
