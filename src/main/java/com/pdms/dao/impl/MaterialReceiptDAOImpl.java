/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.MaterialReceItemDTO;
import com.pdms.dto.MaterialReceLrCleDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
import com.pdms.itemsDto.receipt.MaterialReceiptTempDTO;
import com.pdms.itemsDto.receipt.PoReleaseItemsDTO;
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
public class MaterialReceiptDAOImpl {
    
    
    private static final Logger logger = Logger.getLogger(MaterialReceiptDAOImpl.class);

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
    public MaterialReceiptDAOImpl(){
        
    }
    
    public int insertMaterialReceipt(final MaterialReceiptTempDTO mareObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO material_receipt_details  ");
                        sb.append("(materialRecID,mateReceiptType,excessQuaAllow,dbNumber,date,purchaseUnit,");
                        sb.append("section,plant,poLastNo,poLNoDate,delayRchaNo,");
                        sb.append("delayDate,lrORrrNo,lrDate,lrClearance,");
                        sb.append("transportCharges,tranChaType,packingCharges,otherDemCha,");
                        sb.append("itemsCovered,remarks,locInRecSec,userDbId,session_id,mr_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<MaterialReceiptDTO> mateObj = mareObj.getMateReceiptDTO();
                        for (MaterialReceiptDTO MTRT : mateObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{MTRT.getMaterialRecID(),
                                        MTRT.getMateReceiptType().trim(),
                                        MTRT.getExcessQuaAllow().trim(),
                                        MTRT.getDbNumber(),
                                        MTRT.getDate().trim(),
                                        MTRT.getPurchaseUnit().trim(),
                                        MTRT.getSection().trim(),
                                        MTRT.getPlant().trim(),
                                        MTRT.getPoLastNo().trim(),
                                        MTRT.getPoLNoDate().trim(),
                                        MTRT.getDelayRchaNo().trim(),
                                        MTRT.getDelayDate().trim(),
                                        MTRT.getLrORrrNo().trim(),
                                        MTRT.getLrDate().trim(),
                                        MTRT.getLrClearance().trim(),                                        
                                        MTRT.getTransportCharges().trim(),
                                        MTRT.getTranChaType().trim(),
                                        MTRT.getPackingCharges().trim(),
                                        MTRT.getOtherDemCha().trim(),
                                        MTRT.getItemsCovered().trim(),
                                        MTRT.getRemarks().trim(),
                                        MTRT.getLocInRecSec().trim(),
                                        MTRT.getUserDbId().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO material_receipt_items  ");
                        sbItem.append("(mateReceiptItemsId,dbNumBer,itemCode,groupp,cardNo,unit,");
                        sbItem.append("orderr,dispatchQty,");
                        sbItem.append("accepted,updataed,userid,cur_date) ");                        
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<MaterialReceiptItemsDTO> items = mareObj.getMeteReceItemsDTO();
                        for (MaterialReceiptItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getMateReceiptItemsId(),
                                        itemObj.getDbNumBer(),
                                        itemObj.getItemCode().trim(),
                                        itemObj.getGroup().trim(),
                                        itemObj.getCardNo().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getOrder().trim(),
                                        itemObj.getDispatchQty().trim(),
                                        itemObj.getAccepted().trim(),
                                        itemObj.getUpdataed().trim(),                                        
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
                        logger.error("Exception Occured In:insertMaterialReceipt");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertMaterialReceipt:: " + e.getMessage());
        }
    }

    
     public int insertIssueVoucherItemData(final List<MaterialReceItemDTO> mrit, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO issue_voucher_details_log  ");
            sb.append("(mateReceItemId,itemCode,group_item,cardNo,unit,order_item,dispatched,");             
            sb.append("accepted,updatedQty,session_user_id,iv_item_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?)");
            
            for(MaterialReceItemDTO item : mrit) {                
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{item.getMateReceItemId(),
                        item.getItemCode().trim(),
                        item.getGroup().trim(),
                        item.getCardNo().trim(),
                        item.getUnit().trim(),
                        item.getOrder().trim(),
                        item.getDispatched().trim(),
                        item.getAccepted().trim(),
                        item.getUpdatedQty().trim(),                        
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                        
                    });
                
            }
            
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertIssueVoucherItemData:",e);            
        }
        
        return retVal;
    }
     
     public List<MaterialReceiptDTO> getMaterialReceRecord() throws AppException
    {
        List<MaterialReceiptDTO> mateReceRe = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM material_receipt_details ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    MaterialReceiptDTO MRDTO = new MaterialReceiptDTO();                    
                    MRDTO.setMaterialRecID((Long)(resultMap.get("materialRecID")));   
                    MRDTO.setMateReceiptType((String)(resultMap.get("mateReceiptType")));
                    MRDTO.setExcessQuaAllow((String)(resultMap.get("excessQuaAllow")));
                    MRDTO.setDbNumber((Long)(resultMap.get("dbNumber")));
                    MRDTO.setDate((String)(resultMap.get("date")));
                    MRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    MRDTO.setSection((String)(resultMap.get("section")));
                    MRDTO.setPlant((String)(resultMap.get("plant")));
                    MRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    MRDTO.setPoLNoDate((String)(resultMap.get("poLNoDate")));
                    MRDTO.setDelayRchaNo((String)(resultMap.get("delayRchaNo")));
                    MRDTO.setDelayDate((String)(resultMap.get("delayDate")));                    
                    MRDTO.setLrORrrNo((String)(resultMap.get("lrORrrNo")));
                    MRDTO.setLrDate((String)(resultMap.get("lrDate")));
                    MRDTO.setLrClearance((String)(resultMap.get("lrClearance"))); 
                    MRDTO.setTransportCharges((String)(resultMap.get("transportCharges")));
                    MRDTO.setTranChaType((String)(resultMap.get("tranChaType")));
                    MRDTO.setPackingCharges((String)(resultMap.get("packingCharges")));                    
                    MRDTO.setOtherDemCha((String)(resultMap.get("otherDemCha")));
                    MRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    MRDTO.setRemarks((String)(resultMap.get("remarks")));
                    MRDTO.setLocInRecSec((String)(resultMap.get("locInRecSec")));                    
                    MRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(MRDTO.getMaterialRecID()+""));
                    
                    mateReceRe.add(MRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialReceRecord:",e);
        }
        return mateReceRe;
    }
     
    public List<MaterialReceiptDTO> getMaterialRecReByDbNo(final int DbNumber) throws AppException
    {
        List<MaterialReceiptDTO> mateReceRe = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM material_receipt_details WHERE dbNumber ="+DbNumber);
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                    MaterialReceiptDTO MRDTO = new MaterialReceiptDTO();                    
                    MRDTO.setMaterialRecID((Long)(resultMap.get("materialRecID")));     
                    MRDTO.setMateReceiptType((String)(resultMap.get("mateReceiptType")));
                    MRDTO.setExcessQuaAllow((String)(resultMap.get("excessQuaAllow")));
                    MRDTO.setDbNumber((Long)(resultMap.get("dbNumber")));
                    MRDTO.setDate((String)(resultMap.get("date")));
                    MRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    MRDTO.setSection((String)(resultMap.get("section")));
                    MRDTO.setPlant((String)(resultMap.get("plant")));
                    MRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    MRDTO.setPoLNoDate((String)(resultMap.get("poLNoDate")));
                    MRDTO.setDelayRchaNo((String)(resultMap.get("delayRchaNo")));
                    MRDTO.setDelayDate((String)(resultMap.get("delayDate")));                    
                    MRDTO.setLrORrrNo((String)(resultMap.get("lrORrrNo")));
                    MRDTO.setLrDate((String)(resultMap.get("lrDate")));
                    MRDTO.setLrClearance((String)(resultMap.get("lrClearance")));
                    MRDTO.setTransportCharges((String)(resultMap.get("transportCharges")));
                    MRDTO.setTranChaType((String)(resultMap.get("tranChaType")));
                    MRDTO.setPackingCharges((String)(resultMap.get("packingCharges")));                    
                    MRDTO.setOtherDemCha((String)(resultMap.get("otherDemCha")));
                    MRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    MRDTO.setRemarks((String)(resultMap.get("remarks")));
                    MRDTO.setLocInRecSec((String)(resultMap.get("locInRecSec")));
                    MRDTO.setUserDbId((String)(resultMap.get("userDbId")));
                    MRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(MRDTO.getMaterialRecID()+""));
                    
                    mateReceRe.add(MRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRecReByDbNo:",e);
        }
        return mateReceRe;
    }
     
    public List<MaterialReceiptDTO> getMaterialReceiptReById(final long id) throws AppException {
        List<MaterialReceiptDTO> dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM material_receipt_details WHERE materialRecID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    MaterialReceiptDTO MRDTO = new MaterialReceiptDTO();                    
                    MRDTO.setMaterialRecID((Long)(resultMap.get("materialRecID")));   
                    MRDTO.setMateReceiptType((String)(resultMap.get("mateReceiptType")));
                    MRDTO.setExcessQuaAllow((String)(resultMap.get("excessQuaAllow")));
                    MRDTO.setDbNumber((Long)(resultMap.get("dbNumber")));
                    MRDTO.setDate((String)(resultMap.get("date")));
                    MRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    MRDTO.setSection((String)(resultMap.get("section")));
                    MRDTO.setPlant((String)(resultMap.get("plant")));
                    MRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    MRDTO.setPoLNoDate((String)(resultMap.get("poLNoDate")));
                    MRDTO.setDelayRchaNo((String)(resultMap.get("delayRchaNo")));
                    MRDTO.setDelayDate((String)(resultMap.get("delayDate")));                    
                    MRDTO.setLrORrrNo((String)(resultMap.get("lrORrrNo")));
                    MRDTO.setLrDate((String)(resultMap.get("lrDate")));
                    MRDTO.setLrClearance((String)(resultMap.get("lrClearance")));  
                    MRDTO.setTransportCharges((String)(resultMap.get("transportCharges")));
                    MRDTO.setTranChaType((String)(resultMap.get("tranChaType")));
                    MRDTO.setPackingCharges((String)(resultMap.get("packingCharges")));                    
                    MRDTO.setOtherDemCha((String)(resultMap.get("otherDemCha")));
                    MRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    MRDTO.setRemarks((String)(resultMap.get("remarks")));
                    MRDTO.setLocInRecSec((String)(resultMap.get("locInRecSec")));  
                    MRDTO.setUserDbId((String)(resultMap.get("userDbId")));        
                    MRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(MRDTO.getMaterialRecID()+""));
                    
                    dto.add(MRDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialReceiptReById:", e);
        }
        return dto;
    }
    
    public int updateMaterialReceiptDetail(final List<MaterialReceiptDTO> mareObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE material_receipt_details  ");
            sb.append(" SET mateReceiptType=?,excessQuaAllow=?,dbNumber=?,date=?,");  
            sb.append("purchaseUnit=?,section=?,plant=?,poLastNo=?,poLNoDate=?, ");
            sb.append("delayRchaNo=?,delayDate=?,lrORrrNo=?,lrDate=?,lrClearance=?, ");
            sb.append("transportCharges=?,tranChaType=?,packingCharges=?, ");
            sb.append("otherDemCha=?,itemsCovered=?,remarks=?,locInRecSec=?,userDbId=?, ");
            sb.append("session_id=?,mr_date=? ");
            sb.append(" WHERE materialRecID=? ");
            
            for(MaterialReceiptDTO MTRT : mareObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ 
                        MTRT.getMateReceiptType().trim(),
                        MTRT.getExcessQuaAllow().trim(),
                        MTRT.getDbNumber(),
                        MTRT.getDate().trim(),
                        MTRT.getPurchaseUnit().trim(),
                        MTRT.getSection().trim(),
                        MTRT.getPlant().trim(),
                        MTRT.getPoLastNo().trim(),
                        MTRT.getPoLNoDate().trim(),
                        MTRT.getDelayRchaNo().trim(),
                        MTRT.getDelayDate().trim(),                        
                        MTRT.getLrORrrNo().trim(),
                        MTRT.getLrDate().trim(),
                        MTRT.getLrClearance().trim(), 
                        MTRT.getTransportCharges().trim(),
                        MTRT.getTranChaType().trim(),
                        MTRT.getPackingCharges().trim(),                        
                        MTRT.getOtherDemCha().trim(),
                        MTRT.getItemsCovered().trim(),
                        MTRT.getRemarks().trim(),
                        MTRT.getLocInRecSec().trim(),
                        MTRT.getUserDbId().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        MTRT.getMaterialRecID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateMaterialReceiptDetail:",e);
        }
        return retVal;
    }
    
    public int insertMaterialReceCleData(final List<MaterialReceLrCleDTO> cleObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO material_clearance_details  ");
            sb.append("(clearanceMaReId,poNumBer,lrNumberr,lrDatee,lrClearanceDate,");           
            sb.append("session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?)");
            
            for(MaterialReceLrCleDTO dto : cleObj) {                
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{dto.getClearanceMaReId(),                        
                        dto.getPoNumBer(),
                        dto.getLrNumberr(),
                        dto.getLrDatee().trim(),
                        dto.getLrClearanceDate().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertMaterialReceCleData:",e);
        }
        return retVal;
    }
    
    public List<MaterialReceiptDTO> getMateReceDbNosByUser(final long userID) throws AppException {
        List<MaterialReceiptDTO> LiDto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM material_receipt_details WHERE userDbId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{userID});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    MaterialReceiptDTO MRDTO = new MaterialReceiptDTO();                    
                    MRDTO.setMaterialRecID((Long)(resultMap.get("materialRecID")));  
                    MRDTO.setMateReceiptType((String)(resultMap.get("mateReceiptType")));
                    MRDTO.setExcessQuaAllow((String)(resultMap.get("excessQuaAllow")));
                    MRDTO.setDbNumber((Long)(resultMap.get("dbNumber")));
                    MRDTO.setDate((String)(resultMap.get("date")));
                    MRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    MRDTO.setSection((String)(resultMap.get("section")));
                    MRDTO.setPlant((String)(resultMap.get("plant")));
                    MRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    MRDTO.setPoLNoDate((String)(resultMap.get("poLNoDate")));
                    MRDTO.setDelayRchaNo((String)(resultMap.get("delayRchaNo")));
                    MRDTO.setDelayDate((String)(resultMap.get("delayDate")));                    
                    MRDTO.setLrORrrNo((String)(resultMap.get("lrORrrNo")));
                    MRDTO.setLrDate((String)(resultMap.get("lrDate")));
                    MRDTO.setLrClearance((String)(resultMap.get("lrClearance"))); 
                    MRDTO.setTransportCharges((String)(resultMap.get("transportCharges")));
                    MRDTO.setTranChaType((String)(resultMap.get("tranChaType")));
                    MRDTO.setPackingCharges((String)(resultMap.get("packingCharges")));                    
                    MRDTO.setOtherDemCha((String)(resultMap.get("otherDemCha")));
                    MRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    MRDTO.setRemarks((String)(resultMap.get("remarks")));
                    MRDTO.setLocInRecSec((String)(resultMap.get("locInRecSec")));  
                    MRDTO.setUserDbId((String)(resultMap.get("userDbId")));        
                    MRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(MRDTO.getMaterialRecID()+""));
                    
                    LiDto.add(MRDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMateReceDbNosByUser:", e);
        }
        return LiDto;
    }
    
    public long getDbNumForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM material_receipt_details WHERE dbNumber=(SELECT MAX(dbNumber) FROM material_receipt_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   MaterialReceiptDTO icdto = new MaterialReceiptDTO();
                    icdto.setDbNumber((long)(resultMap.get("dbNumber")));
                    maxNo = (long)(resultMap.get("dbNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDbNumForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<MaterialReceiptDTO> getMaterialRecReByPoNo(final int PoNuMber) throws AppException
    {
        List<MaterialReceiptDTO> mateReceList = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM material_receipt_details WHERE dbNumber =? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
            new Object[]{PoNuMber});
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                    MaterialReceiptDTO MRDTO = new MaterialReceiptDTO();                    
                    MRDTO.setMaterialRecID((Long)(resultMap.get("materialRecID")));   
                    MRDTO.setMateReceiptType((String)(resultMap.get("mateReceiptType")));
                    MRDTO.setExcessQuaAllow((String)(resultMap.get("excessQuaAllow")));
                    MRDTO.setDbNumber((Long)(resultMap.get("dbNumber")));
                    MRDTO.setDate((String)(resultMap.get("date")));
                    MRDTO.setPurchaseUnit((String)(resultMap.get("purchaseUnit")));
                    MRDTO.setSection((String)(resultMap.get("section")));
                    MRDTO.setPlant((String)(resultMap.get("plant")));
                    MRDTO.setPoLastNo((String)(resultMap.get("poLastNo")));
                    MRDTO.setPoLNoDate((String)(resultMap.get("poLNoDate")));
                    MRDTO.setDelayRchaNo((String)(resultMap.get("delayRchaNo")));
                    MRDTO.setDelayDate((String)(resultMap.get("delayDate")));                    
                    MRDTO.setLrORrrNo((String)(resultMap.get("lrORrrNo")));
                    MRDTO.setLrDate((String)(resultMap.get("lrDate")));
                    MRDTO.setLrClearance((String)(resultMap.get("lrClearance")));
                    MRDTO.setTransportCharges((String)(resultMap.get("transportCharges")));
                    MRDTO.setTranChaType((String)(resultMap.get("tranChaType")));
                    MRDTO.setPackingCharges((String)(resultMap.get("packingCharges")));                    
                    MRDTO.setOtherDemCha((String)(resultMap.get("otherDemCha")));
                    MRDTO.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    MRDTO.setRemarks((String)(resultMap.get("remarks")));
                    MRDTO.setLocInRecSec((String)(resultMap.get("locInRecSec")));
                    MRDTO.setUserDbId((String)(resultMap.get("userDbId")));
                    MRDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(MRDTO.getMaterialRecID()+""));
                    
                    mateReceList.add(MRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRecReByPoNo:",e);
        }
        return mateReceList;
    }
    
    
    public List<MaterialReceiptItemsDTO> getMaterialRecItemsReByDbNo(final long DbNuMber) throws AppException
    {
        List<MaterialReceiptItemsDTO> mateReceList = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT a.mateReceiptItemsId,a.dbNumBer,a.itemCode,a.groupp,a.cardNo, ");
            sb.append("a.unit,a.orderr,a.dispatchQty,a.accepted,a.updataed,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name ");
            sb.append(" FROM material_receipt_items a ");
            sb.append(" JOIN unit_master u ON a.unit = u.unit_code");
            sb.append(" JOIN item_master i ON a.itemCode = i.item_code");
            sb.append(" WHERE dbNumBer =? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
            new Object[]{DbNuMber});
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                    MaterialReceiptItemsDTO MRDTO = new MaterialReceiptItemsDTO();                    
                    MRDTO.setMateReceiptItemsId((Long)(resultMap.get("mateReceiptItemsId")));   
                    MRDTO.setDbNumBer((Long)(resultMap.get("dbNumBer")));
                    MRDTO.setItemCode((String)(resultMap.get("itemCode")));
                    MRDTO.setGroup((String)(resultMap.get("groupp")));
                    MRDTO.setCardNo((String)(resultMap.get("cardNo")));
                    MRDTO.setUnit((String)(resultMap.get("unit")));
                    MRDTO.setOrder((String)(resultMap.get("orderr")));
                    MRDTO.setDispatchQty((String)(resultMap.get("dispatchQty")));
                    MRDTO.setAccepted((String)(resultMap.get("accepted")));
                    MRDTO.setUpdataed((String)(resultMap.get("updataed")));  
                    MRDTO.getUnitObj().setUnitName((String)(resultMap.get("unit_name")));  
                    MRDTO.getItemObj().setItemName((String)(resultMap.get("item_name")));
                    mateReceList.add(MRDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRecItemsReByPoNo:",e);
        }
        return mateReceList;
    }
    
}
