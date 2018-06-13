/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.dao.daoImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.despatch.dto.InwardDTO;
import com.pdms.despatch.dto.PostEntryDTO;
import com.pdms.despatch.itemsDto.InwardPoItemsDTO;
import com.pdms.despatch.itemsDto.InwardVenItemsDTO;
import com.pdms.despatch.itemsDto.PostEntryTempDTO;
import com.pdms.despatch.itemsDto.PostPoItemsDTO;
import com.pdms.despatch.itemsDto.PostVenItemsDTO;
import com.pdms.exception.AppException;
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
public class PostEntryDAOImpl {
    
    private static final Logger logger = Logger.getLogger(PostEntryDAOImpl.class);

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
    public PostEntryDAOImpl(){
        
    }
    
    public int insertPostEntryDetails(final PostEntryTempDTO poObj, final long sessUserID) throws AppException {
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
                        sb.append(" INSERT INTO post_entry_details  ");
                        sb.append("(postEntryId,dispatchNo,fileNo,groupp,nature,");
                        sb.append("amount,typeOfDispatch,serviceTax,total,balance,session_id,cur_date)");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<PostEntryDTO> peObj = poObj.getPostDTO();
                        for (PostEntryDTO Obj : peObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{Obj.getPostEntryId(),
                                        Obj.getDispatchNo(),
                                        Obj.getFileNo(),
                                        Obj.getGroup(),
                                        Obj.getNature().trim(),
                                        Obj.getAmount(),
                                        Obj.getTypeOfDispatch(),
                                        Obj.getServiceTax(),
                                        Obj.getTotal(),
                                        Obj.getBalance(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO post_poitems_details  ");
                        sbItem.append("(postPoitemId,dispatchno,filenumber,ponumber,posrno,vendorcode,");
                        sbItem.append("deliverydate,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?)");

                        List<PostPoItemsDTO> items = poObj.getPostPoDTO();
                        for (PostPoItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getPostPoitemId(),
                                        itemObj.getDispatchno(),
                                        itemObj.getFilenumber(),
                                        itemObj.getPonumber(),
                                        itemObj.getPosrno().trim(),
                                        itemObj.getVendorcode().trim(),
                                        itemObj.getDeliverydate().trim(),                                       
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }

                        StringBuilder sbVen = new StringBuilder();
                        sbVen.append(" INSERT INTO post_venitems_details  ");
                        sbVen.append("(postVenItemId,dispatchno,filenumber,itemid,vendorid,");
                        sbVen.append("amount,userid,cur_date) ");
                        sbVen.append(" VALUES ");
                        sbVen.append("(?,?,?,?,?,?,?,?)");

                        List<PostVenItemsDTO> item = poObj.getPostVenDTO();
                        for (PostVenItemsDTO itemObj : item) {
                            retVenItems = getJdbcTemplate().update(sbVen.toString(),
                                    new Object[]{itemObj.getPostVenItemId(),
                                        itemObj.getDispatchno(),
                                        itemObj.getFilenumber(),
                                        itemObj.getItemid(),
                                        itemObj.getVendorid(),
                                        itemObj.getAmount(),
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
                        logger.error("Exception Occured In:insertPostEntryDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertPostEntryDetails:: " + e.getMessage());
        }
    }
    
    public List<PostEntryDTO> getPostEntryRecord() throws AppException {
        List<PostEntryDTO> poDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT a.postEntryId,a.dispatchNo,a.fileNo,a.groupp,");
            sb.append("a.nature,a.amount,a.typeOfDispatch,a.serviceTax,a.total,a.balance,");
            sb.append("t.code,t.name ");
            sb.append(" FROM post_entry_details a ");
            sb.append(" JOIN dispatch_master_details t ON a.typeOfDispatch = t.code ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostEntryDTO indto = new PostEntryDTO();
                    indto.setPostEntryId((Long) (retMap.get("postEntryId")));
                    indto.setDispatchNo((Long) (retMap.get("dispatchNo")));
                    indto.setFileNo((Long) (retMap.get("fileNo"))); 
                    indto.setGroup((String) (retMap.get("groupp")));
                    indto.setNature((String) (retMap.get("nature")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setTypeOfDispatch((String) (retMap.get("typeOfDispatch")));
                    indto.setServiceTax((BigDecimal) (retMap.get("serviceTax")));
                    indto.setTotal((BigDecimal) (retMap.get("total")));
                    indto.setBalance((BigDecimal) (retMap.get("balance")));
                    indto.getTodsObj().setCode((String) (retMap.get("code")));
                    indto.getTodsObj().setName((String) (retMap.get("name")));
                    poDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostEntryRecord:", e);
        }
        return poDTO;
    }
    
    public List<PostEntryDTO> getPostEntryReById(final long id) throws AppException {
        List<PostEntryDTO> poDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM post_entry_details WHERE postEntryId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostEntryDTO indto = new PostEntryDTO();
                    indto.setPostEntryId((Long) (retMap.get("postEntryId")));
                    indto.setDispatchNo((Long) (retMap.get("dispatchNo")));
                    indto.setFileNo((Long) (retMap.get("fileNo"))); 
                    indto.setGroup((String) (retMap.get("groupp")));
                    indto.setNature((String) (retMap.get("nature")));
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.setTypeOfDispatch((String) (retMap.get("typeOfDispatch")));
                    indto.setServiceTax((BigDecimal) (retMap.get("serviceTax")));
                    indto.setTotal((BigDecimal) (retMap.get("total")));
                    indto.setBalance((BigDecimal) (retMap.get("balance")));
                    poDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostEntryReById:", e);
        }
        return poDTO;
    }
    
    public int updatePostEntryDetail(final List<PostEntryDTO> poObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE post_entry_details  ");
            sb.append(" SET dispatchNo=?,fileNo=?,groupp=?,nature=?,amount=?,typeOfDispatch=?,");  
            sb.append("serviceTax=?,total=?,balance=?,session_id=?,cur_date=? ");
            sb.append(" WHERE postEntryId=? ");
            
            for(PostEntryDTO Obj : poObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        Obj.getDispatchNo(),
                        Obj.getFileNo(),    
                        Obj.getGroup().trim(), 
                        Obj.getNature().trim(), 
                        Obj.getAmount(), 
                        Obj.getTypeOfDispatch().trim(), 
                        Obj.getServiceTax(), 
                        Obj.getTotal(),
                        Obj.getBalance(),                          
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        Obj.getPostEntryId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePostEntryDetail:",e);
        }
        return retVal;
    }
    
    public long getDispatchNoNumForIncr() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM post_entry_details WHERE dispatchNo=(SELECT MAX(dispatchNo) FROM post_entry_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   PostEntryDTO icdto = new PostEntryDTO();
                    icdto.setDispatchNo((long)(resultMap.get("dispatchNo")));
                    maxNo = (long)(resultMap.get("dispatchNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDispatchNoNumForIncr:",e);
        }
        return maxNo;
    }
    
    public List<PostVenItemsDTO> getPostEntryVenReByDisNo(final long dispatchNo) throws AppException {
        List<PostVenItemsDTO> poDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT a.postVenItemId,a.dispatchno,a.filenumber,a.itemid,a.vendorid,a.amount, ");  
            sb.append(" v.vendor_name,v.vendor_city ");
            sb.append(" FROM post_venitems_details a ");
            sb.append(" JOIN vendor_details v ON a.vendorid = v.pk_vendor_id ");
            sb.append(" WHERE a.dispatchno=?");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{dispatchNo});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PostVenItemsDTO indto = new PostVenItemsDTO();
                    
                    indto.setPostVenItemId((Long) (retMap.get("postVenItemId")));
                    indto.setDispatchno((Long) (retMap.get("dispatchno"))); 
                    indto.setFilenumber((Long) (retMap.get("filenumber"))); 
                    indto.setItemid((Long) (retMap.get("itemid"))); 
                    indto.setVendorid((Long) (retMap.get("vendorid"))); 
                    indto.setAmount((BigDecimal) (retMap.get("amount")));
                    indto.getVendorObj().setVendorName((String) (retMap.get("vendor_name")));
                    indto.getVendorObj().setVendorCity((String) (retMap.get("vendor_city")));
                    poDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPostEntryVenReByDisNo:", e);
        }
        return poDTO;
    }
}
