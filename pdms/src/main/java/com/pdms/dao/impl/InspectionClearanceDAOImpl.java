/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.InspectionCleaItemsDTO;
import com.pdms.itemsDto.receipt.InspectionCleaTempDTO;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
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
 * @author myassessment
 */
@Repository
public class InspectionClearanceDAOImpl {
    
    private static final Logger logger = Logger.getLogger(InspectionClearanceDAOImpl.class);

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
    public InspectionClearanceDAOImpl(){
        
    }
 
    public int insertInspeClearance(final InspectionCleaTempDTO inspectObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO inspection_clearance_details  ");
                        sb.append("(inspeClearID,dbNumber,dbDate,section,plant,");
                        sb.append("purchaseUnit,poLastNo,poDate,grOrrNumber,grOrrDate,itemsCovered,");
                        sb.append("inspectedBy,inspeCleaRemarks,");
                        sb.append("session_user_id,inspection_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<InspectionClearanceDTO> insform = inspectObj.getInspecDTO();
                        for (InspectionClearanceDTO ICDTO : insform) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{ICDTO.getInspeClearID(),
                                        ICDTO.getDbNumber().trim(),
                                        ICDTO.getDbDate().trim(),
                                        ICDTO.getSection().trim(),
                                        ICDTO.getPlant().trim(),
                                        ICDTO.getPurchaseUnit().trim(),
                                        ICDTO.getPoLastNo().trim(),
                                        ICDTO.getPoDate().trim(),
                                        ICDTO.getGrOrrNumber(),
                                        ICDTO.getGrOrrDate().trim(),
                                        ICDTO.getItemsCovered().trim(),
                                        ICDTO.getInspectedBy().trim(),
                                        ICDTO.getInspeCleaRemarks().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO inspection_clearance_items  ");
                        sbItem.append("(inspeCleItemsId,dbNumBer,code,groupCode,storeCardNo,item,");
                        sbItem.append("unit,orderQty,");
                        sbItem.append("dispatchQty,updatee,acceptedQty,rejectedQty,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<InspectionCleaItemsDTO> items = inspectObj.getInspecItemsDTO();
                        for (InspectionCleaItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getInspeCleItemsId(),
                                        itemObj.getDbNumBer(),
                                        itemObj.getCode().trim(),
                                        itemObj.getGroupCode().trim(),
                                        itemObj.getStoreCardNo().trim(),
                                        itemObj.getItem().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getOrderQty().trim(),
                                        itemObj.getDispatchQty().trim(),
                                        itemObj.getUpdate().trim(),
                                        itemObj.getAcceptedQty().trim(),
                                        itemObj.getRejectedQty().trim(),
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
                        logger.error("Exception Occured In:insertInspeClearance");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertInspeClearance: " + e.getMessage());
        }
    }
    
    public List<InspectionClearanceDTO> getInspectionClearanceRecord() throws AppException
    {
        List<InspectionClearanceDTO> inspeCleaRe = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM inspection_clearance_details ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    InspectionClearanceDTO ICRDTO  = new InspectionClearanceDTO();                    
                    ICRDTO.setInspeClearID((Long)(resultMap.get("inspeClearID")));
                    ICRDTO.setDbNumber((String)(resultMap.get("dbNumber")));
                    ICRDTO.setDbDate((String)(resultMap.get("dbDate")));                    
                    ICRDTO.setSection((String)(resultMap.get("section")));
                    ICRDTO.setPlant((String)(resultMap.get("plant")));
                    ICRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    ICRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    ICRDTO.setPoDate((String)(resultMap.get("poDate")));
                    ICRDTO.setGrOrrNumber((long)(resultMap.get("grOrrNumber")));
                    ICRDTO.setGrOrrDate((String)(resultMap.get("grOrrDate")));
                    ICRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));                    
                    ICRDTO.setInspectedBy((String)(resultMap.get("inspectedBy")));
                    ICRDTO.setInspeCleaRemarks((String)(resultMap.get("inspeCleaRemarks")));                    
                    ICRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(ICRDTO.getInspeClearID()+""));
                    
                    inspeCleaRe.add(ICRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getInspectionClearanceRecord:",e);
        }
        return inspeCleaRe;
    }
    
    public long getICNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM inspection_clearance_details WHERE grOrrNumber=(SELECT MAX(grOrrNumber) FROM inspection_clearance_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   InspectionClearanceDTO icdto = new InspectionClearanceDTO();
                    icdto.setGrOrrNumber((long)(resultMap.get("grOrrNumber")));
                    maxNo = (long)(resultMap.get("grOrrNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllUnits:",e);
        }
        return maxNo;
    }
    
    public List<InspectionClearanceDTO> getInspecCleareReById(final long id) throws AppException {
        List<InspectionClearanceDTO> insDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM inspection_clearance_details WHERE inspeClearID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    InspectionClearanceDTO ICRDTO  = new InspectionClearanceDTO();                    
                    ICRDTO.setInspeClearID((Long)(resultMap.get("inspeClearID")));
                    ICRDTO.setDbNumber((String)(resultMap.get("dbNumber")));
                    ICRDTO.setDbDate((String)(resultMap.get("dbDate")));                    
                    ICRDTO.setSection((String)(resultMap.get("section")));
                    ICRDTO.setPlant((String)(resultMap.get("plant")));
                    ICRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    ICRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    ICRDTO.setPoDate((String)(resultMap.get("poDate")));
                    ICRDTO.setGrOrrNumber((long)(resultMap.get("grOrrNumber")));
                    ICRDTO.setGrOrrDate((String)(resultMap.get("grOrrDate")));
                    ICRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));                    
                    ICRDTO.setInspectedBy((String)(resultMap.get("inspectedBy")));
                    ICRDTO.setInspeCleaRemarks((String)(resultMap.get("inspeCleaRemarks")));                    
                    ICRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(ICRDTO.getInspeClearID()+""));
                    
                    insDTO.add(ICRDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getInspecCleareReById:", e);
        }
        return insDTO;
    }
    
    public int updateInspectCleareDetail(final List<InspectionClearanceDTO> inspectObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE inspection_clearance_details  ");
            sb.append(" SET dbNumber=?,dbDate=?,section=?,plant=?,");  
            sb.append("purchaseUnit=?,poLastNo=?,poDate=?,grOrrNumber=?,grOrrDate=?, ");
            sb.append("itemsCovered=?,inspectedBy=?,inspeCleaRemarks=?,session_user_id=?,inspection_date=?  ");
            sb.append(" WHERE inspeClearID=? ");
            
            for(InspectionClearanceDTO ICDTO : inspectObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        ICDTO.getDbNumber().trim(),
                        ICDTO.getDbDate().trim(),                        
                        ICDTO.getSection().trim(),
                        ICDTO.getPlant().trim(),
                        ICDTO.getPurchaseUnit().trim(),
                        ICDTO.getPoLastNo().trim(),
                        ICDTO.getPoDate().trim(),
                        ICDTO.getGrOrrNumber(),
                        ICDTO.getGrOrrDate().trim(),
                        ICDTO.getItemsCovered().trim(),                        
                        ICDTO.getInspectedBy().trim(),
                        ICDTO.getInspeCleaRemarks().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        ICDTO.getInspeClearID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateInspectCleareDetail:",e);
        }
        return retVal;
    }
    
    public List<InspectionCleaItemsDTO> getInspectionCleaItemsByDbNo(final long DbNuMber) throws AppException
    {
        List<InspectionCleaItemsDTO> inspList = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT a.inspeCleItemsId,a.dbNumBer,a.code,a.groupCode,a.storeCardNo, ");
            sb.append("a.item,a.unit,a.orderQty,a.dispatchQty,a.updatee,a.acceptedQty,a.rejectedQty,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name ");
            sb.append(" FROM inspection_clearance_items a ");
            sb.append(" JOIN unit_master u ON a.unit = u.unit_code");
            sb.append(" JOIN item_master i ON a.item = i.item_code");
            sb.append(" WHERE dbNumBer =? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
            new Object[]{DbNuMber});
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                    InspectionCleaItemsDTO MRDTO = new InspectionCleaItemsDTO();                    
                    MRDTO.setInspeCleItemsId((Long)(resultMap.get("inspeCleItemsId")));   
                    MRDTO.setDbNumBer((Long)(resultMap.get("dbNumBer")));
                    MRDTO.setCode((String)(resultMap.get("code")));
                    MRDTO.setGroupCode((String)(resultMap.get("groupCode")));
                    MRDTO.setStoreCardNo((String)(resultMap.get("storeCardNo")));
                    MRDTO.setItem((String)(resultMap.get("item")));
                    MRDTO.setUnit((String)(resultMap.get("unit")));
                    MRDTO.setOrderQty((String)(resultMap.get("orderQty")));
                    MRDTO.setDispatchQty((String)(resultMap.get("dispatchQty")));
                    MRDTO.setUpdate((String)(resultMap.get("updatee")));  
                    MRDTO.setAcceptedQty((String)(resultMap.get("acceptedQty"))); 
                    MRDTO.setRejectedQty((String)(resultMap.get("rejectedQty"))); 
                    MRDTO.getUnitObj().setUnitName((String)(resultMap.get("unit_name")));  
                    MRDTO.getItemObj().setItemName((String)(resultMap.get("item_name")));
                    inspList.add(MRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getInspectionCleaItemsByDbNo:",e);
        }
        return inspList;
    }
    
}
