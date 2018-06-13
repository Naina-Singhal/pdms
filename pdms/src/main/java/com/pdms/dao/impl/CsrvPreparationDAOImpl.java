/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.CsrvPreparationTempDTO;
import com.pdms.itemsDto.receipt.InspectionCleaItemsDTO;
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
public class CsrvPreparationDAOImpl {
    
    
    private static final Logger logger = Logger.getLogger(PoEntryDAOImpl.class);

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

    public CsrvPreparationDAOImpl(){
        
    }
    
    public int insertCsrvPreparationDetails(final CsrvPreparationTempDTO csrvObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO csrv_preparation_details  ");
                        sb.append("(csrvPreparationID,rvControlNo,date,poNumber,poNodate,dbNumber,dbDate,indentNumber,indentDate,");
                        sb.append("updateDate,updatedBy,deliveryChallanNo,deliveryChallenDate,inspectedBy,section,itemsCovered,deliveryAt,");
                        sb.append("pfCharges,tptCharges,otherCharges,remarks,amountPaid,paymentTerms,indentor,payingAuthority,");
                        sb.append("headOfAccount,inspectRemarks,");
                        sb.append("group_g,cardNumber,code,itemDescription,partNumber,unit,SuppLastReceived,");
                        sb.append("balance,basicRate,lifoRate,waRate,rateDate,mrDate,qtyAccepted,");
                        sb.append("mrUnit,mrPoNumber,rvNumber,mrRemarks,mrLocation,minLevelDate,");
                        sb.append("session_user_id,csrv_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<CSRVpreparationDTO> csObj = csrvObj.getCsrvDTO();
                        for (CSRVpreparationDTO CSDTO : csObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{CSDTO.getCsrvPreparationID(),
                                        CSDTO.getRvControlNo(),
                                        CSDTO.getDate().trim(),
                                        CSDTO.getPoNumber().trim(),
                                        CSDTO.getPoNodate().trim(),
                                        CSDTO.getDbNumber().trim(),
                                        CSDTO.getDbDate().trim(),
                                        CSDTO.getIndentNumber().trim(),
                                        CSDTO.getIndentDate().trim(),
                                        CSDTO.getUpdateDate().trim(),
                                        CSDTO.getUpdatedBy().trim(),
                                        CSDTO.getDeliveryChallanNo().trim(),
                                        CSDTO.getDeliveryChallenDate().trim(),
                                        CSDTO.getInspectedBy().trim(),
                                        CSDTO.getSection().trim(),
                                        CSDTO.getItemsCovered().trim(),
                                        CSDTO.getDeliveryAt().trim(),
                                        CSDTO.getPfCharges().trim(),
                                        CSDTO.getTptCharges().trim(),
                                        CSDTO.getOtherCharges().trim(),
                                        CSDTO.getRemarks().trim(),
                                        CSDTO.getAmountPaid().trim(),
                                        CSDTO.getPaymentTerms().trim(),
                                        CSDTO.getIndentor().trim(),
                                        CSDTO.getPayingAuthority().trim(),
                                        CSDTO.getHeadOfAccount().trim(),
                                        CSDTO.getInspectRemarks().trim(),
                                        //second part
                                        CSDTO.getGroup().trim(),
                                        CSDTO.getCardNumber().trim(),
                                        CSDTO.getCode().trim(),
                                        CSDTO.getItemDescription().trim(),
                                        CSDTO.getPartNumber().trim(),
                                        CSDTO.getUnit().trim(),
                                        CSDTO.getSuppLastReceived().trim(),
                                        CSDTO.getBalance().trim(),
                                        CSDTO.getBasicRate().trim(),
                                        CSDTO.getLifoRate().trim(),
                                        CSDTO.getWaRate().trim(),
                                        CSDTO.getRateDate().trim(),
                                        CSDTO.getMrDate().trim(),
                                        CSDTO.getQtyAccepted().trim(),
                                        CSDTO.getMrUnit().trim(),
                                        CSDTO.getMrPoNumber().trim(),
                                        CSDTO.getRvNumber().trim(),
                                        CSDTO.getMrRemarks().trim(),
                                        CSDTO.getMrLocation().trim(),
                                        CSDTO.getMinLevelDate().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO csrv_preparation_items  ");
                        sbItem.append("(csrvPrepaItemId,dbNumBer,poNumberI,groupCode,storeCode,item,unit,");                        
                        sbItem.append("orderQty,acceptedQty,balanceQty,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<CsrvPreparationItemsDTO> items = csrvObj.getCsrvItemsDTO();
                        for (CsrvPreparationItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getCsrvPrepaItemId(),
                                        itemObj.getDbNumBer(),
                                        itemObj.getPoNumberI(),
                                        itemObj.getGroupCode().trim(),
                                        itemObj.getStoreCode().trim(),
                                        itemObj.getItem().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getOrderQty().trim(),
                                        itemObj.getAcceptedQty().trim(),
                                        itemObj.getBalanceQty().trim(),                                        
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
                        logger.error("Exception Occured In:insertCsrvPreparationDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertCsrvPreparationDetails: " + e.getMessage());
        }
    }
    
    public List<CSRVpreparationDTO> getCsrvPreparationRecord() throws AppException {
        List<CSRVpreparationDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM csrv_preparation_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CSRVpreparationDTO dto = new CSRVpreparationDTO();
                    dto.setCsrvPreparationID((Long) (retMap.get("csrvPreparationID")));
                    dto.setRvControlNo((Long) (retMap.get("rvControlNo")));
                    dto.setDate((String) (retMap.get("date")));
                    dto.setPoNumber((String) (retMap.get("poNumber")));
                    dto.setPoNodate((String) (retMap.get("poNodate")));  
                    dto.setDbNumber((String) (retMap.get("dbNumber")));
                    dto.setDbDate((String) (retMap.get("dbDate")));
                    dto.setIndentNumber((String) (retMap.get("indentNumber")));
                    dto.setIndentDate((String) (retMap.get("indentDate")));
                    dto.setUpdateDate((String) (retMap.get("updateDate")));
                    dto.setUpdatedBy((String) (retMap.get("updatedBy")));
                    dto.setDeliveryChallanNo((String) (retMap.get("deliveryChallanNo")));                    
                    dto.setDeliveryChallenDate((String) (retMap.get("deliveryChallenDate")));
                    dto.setInspectedBy((String) (retMap.get("inspectedBy")));
                    dto.setSection((String) (retMap.get("section")));
                    dto.setItemsCovered((String) (retMap.get("itemsCovered")));
                    dto.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dto.setPfCharges((String) (retMap.get("pfCharges")));  
                    dto.setTptCharges((String) (retMap.get("tptCharges")));
                    dto.setOtherCharges((String) (retMap.get("otherCharges")));
                    dto.setRemarks((String) (retMap.get("remarks")));
                    dto.setAmountPaid((String) (retMap.get("amountPaid")));
                    dto.setPaymentTerms((String) (retMap.get("paymentTerms")));
                    dto.setIndentor((String) (retMap.get("indentor")));
                    dto.setPayingAuthority((String) (retMap.get("payingAuthority")));
                    dto.setHeadOfAccount((String) (retMap.get("headOfAccount")));                    
                    dto.setInspectRemarks((String) (retMap.get("inspectRemarks")));
                    dto.setGroup((String) (retMap.get("group_g")));
                    dto.setCardNumber((String) (retMap.get("cardNumber")));
                    dto.setCode((String) (retMap.get("code")));
                    dto.setItemDescription((String) (retMap.get("itemDescription")));
                    dto.setPartNumber((String) (retMap.get("partNumber")));
                    dto.setUnit((String) (retMap.get("unit")));
                    dto.setSuppLastReceived((String) (retMap.get("SuppLastReceived")));
                    dto.setBalance((String) (retMap.get("balance")));
                    dto.setBasicRate((String) (retMap.get("basicRate")));  
                    dto.setLifoRate((String) (retMap.get("lifoRate")));
                    dto.setWaRate((String) (retMap.get("waRate")));
                    dto.setRateDate((String) (retMap.get("rateDate")));
                    dto.setMrDate((String) (retMap.get("mrDate")));
                    dto.setQtyAccepted((String) (retMap.get("qtyAccepted")));
                    dto.setMrUnit((String) (retMap.get("mrUnit")));
                    dto.setMrPoNumber((String) (retMap.get("mrPoNumber")));  
                    dto.setRvNumber((String) (retMap.get("rvNumber")));
                    dto.setMrRemarks((String) (retMap.get("mrRemarks")));
                    dto.setMrLocation((String) (retMap.get("mrLocation")));
                    dto.setMinLevelDate((String) (retMap.get("minLevelDate")));                    
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCsrvPreparationRecord:", e);
        }
        return list;
    }
    
    public List<CSRVpreparationDTO> getCsrvPreparationReById(final long id) throws AppException {
        List<CSRVpreparationDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM csrv_preparation_details WHERE csrvPreparationID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CSRVpreparationDTO dto = new CSRVpreparationDTO();
                    dto.setCsrvPreparationID((Long) (retMap.get("csrvPreparationID")));
                    dto.setRvControlNo((Long) (retMap.get("rvControlNo")));
                    dto.setDate((String) (retMap.get("date")));
                    dto.setPoNumber((String) (retMap.get("poNumber")));
                    dto.setPoNodate((String) (retMap.get("poNodate")));  
                    dto.setDbNumber((String) (retMap.get("dbNumber")));
                    dto.setDbDate((String) (retMap.get("dbDate")));
                    dto.setIndentNumber((String) (retMap.get("indentNumber")));
                    dto.setIndentDate((String) (retMap.get("indentDate")));
                    dto.setUpdateDate((String) (retMap.get("updateDate")));
                    dto.setUpdatedBy((String) (retMap.get("updatedBy")));
                    dto.setDeliveryChallanNo((String) (retMap.get("deliveryChallanNo")));                    
                    dto.setDeliveryChallenDate((String) (retMap.get("deliveryChallenDate")));
                    dto.setInspectedBy((String) (retMap.get("inspectedBy")));
                    dto.setSection((String) (retMap.get("section")));
                    dto.setItemsCovered((String) (retMap.get("itemsCovered")));
                    dto.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dto.setPfCharges((String) (retMap.get("pfCharges")));  
                    dto.setTptCharges((String) (retMap.get("tptCharges")));
                    dto.setOtherCharges((String) (retMap.get("otherCharges")));
                    dto.setRemarks((String) (retMap.get("remarks")));
                    dto.setAmountPaid((String) (retMap.get("amountPaid")));
                    dto.setPaymentTerms((String) (retMap.get("paymentTerms")));
                    dto.setIndentor((String) (retMap.get("indentor")));
                    dto.setPayingAuthority((String) (retMap.get("payingAuthority")));
                    dto.setHeadOfAccount((String) (retMap.get("headOfAccount")));                    
                    dto.setInspectRemarks((String) (retMap.get("inspectRemarks")));                     
                    dto.setGroup((String) (retMap.get("group_g")));
                    dto.setCardNumber((String) (retMap.get("cardNumber")));
                    dto.setCode((String) (retMap.get("code")));
                    dto.setItemDescription((String) (retMap.get("itemDescription")));
                    dto.setPartNumber((String) (retMap.get("partNumber")));
                    dto.setUnit((String) (retMap.get("unit")));
                    dto.setSuppLastReceived((String) (retMap.get("SuppLastReceived")));
                    dto.setBalance((String) (retMap.get("balance")));
                    dto.setBasicRate((String) (retMap.get("basicRate")));  
                    dto.setLifoRate((String) (retMap.get("lifoRate")));
                    dto.setWaRate((String) (retMap.get("waRate")));
                    dto.setRateDate((String) (retMap.get("rateDate")));
                    dto.setMrDate((String) (retMap.get("mrDate")));
                    dto.setQtyAccepted((String) (retMap.get("qtyAccepted")));
                    dto.setMrUnit((String) (retMap.get("mrUnit")));
                    dto.setMrPoNumber((String) (retMap.get("mrPoNumber")));  
                    dto.setRvNumber((String) (retMap.get("rvNumber")));
                    dto.setMrRemarks((String) (retMap.get("mrRemarks")));
                    dto.setMrLocation((String) (retMap.get("mrLocation")));
                    dto.setMinLevelDate((String) (retMap.get("minLevelDate")));                    
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCsrvPreparationReById:", e);
        }
        return list;
    }
    
    public int updateCsrvPreparationDetail(final List<CSRVpreparationDTO> csrvObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE csrv_preparation_details  ");
            sb.append(" SET rvControlNo=?,date=?,poNumber=?,poNodate=?,dbNumber=?,dbDate=?,indentNumber=?, ");  
            sb.append("indentDate=?,updateDate=?,updatedBy=?,deliveryChallanNo=?,deliveryChallenDate=?,inspectedBy=?,section=?, ");
            sb.append("itemsCovered=?,deliveryAt=?,pfCharges=?,tptCharges=?,otherCharges=?,remarks=?,amountPaid=?, ");
            sb.append("paymentTerms=?,indentor=?,payingAuthority=?,headOfAccount=?,inspectRemarks=?, ");
            sb.append("group_g=?,cardNumber=?,code=?,itemDescription=?,partNumber=?,unit=?,SuppLastReceived=?, ");
            sb.append("balance=?,basicRate=?,lifoRate=?,waRate=?,rateDate=?,mrDate=?,qtyAccepted=?, ");
            sb.append("mrUnit=?,mrPoNumber=?,rvNumber=?,mrRemarks=?,mrLocation=?,minLevelDate=?,session_user_id=?,csrv_date=?  ");
            sb.append(" WHERE csrvPreparationID=? ");
            
            for(CSRVpreparationDTO CSDTO : csrvObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        CSDTO.getRvControlNo(),
                        CSDTO.getDate().trim(),
                        CSDTO.getPoNumber().trim(),
                        CSDTO.getPoNodate().trim(),
                        CSDTO.getDbNumber().trim(),
                        CSDTO.getDbDate().trim(),
                        CSDTO.getIndentNumber().trim(),
                        CSDTO.getIndentDate().trim(),
                        CSDTO.getUpdateDate().trim(),
                        CSDTO.getUpdatedBy().trim(),
                        CSDTO.getDeliveryChallanNo().trim(),
                        CSDTO.getDeliveryChallenDate().trim(),
                        CSDTO.getInspectedBy().trim(),
                        CSDTO.getSection().trim(),
                        CSDTO.getItemsCovered().trim(),
                        CSDTO.getDeliveryAt().trim(),
                        CSDTO.getPfCharges().trim(),
                        CSDTO.getTptCharges().trim(),
                        CSDTO.getOtherCharges().trim(),
                        CSDTO.getRemarks().trim(),
                        CSDTO.getAmountPaid().trim(),
                        CSDTO.getPaymentTerms().trim(),
                        CSDTO.getIndentor().trim(),
                        CSDTO.getPayingAuthority().trim(),
                        CSDTO.getHeadOfAccount().trim(),                       
                        CSDTO.getInspectRemarks().trim(),                                
                        //second part
                        CSDTO.getGroup().trim(),
                        CSDTO.getCardNumber().trim(),
                        CSDTO.getCode().trim(),
                        CSDTO.getItemDescription().trim(),
                        CSDTO.getPartNumber().trim(),
                        CSDTO.getUnit().trim(),
                        CSDTO.getSuppLastReceived().trim(),
                        CSDTO.getBalance().trim(),
                        CSDTO.getBasicRate().trim(),
                        CSDTO.getLifoRate().trim(),
                        CSDTO.getWaRate().trim(),
                        CSDTO.getRateDate().trim(),
                        CSDTO.getMrDate().trim(),
                        CSDTO.getQtyAccepted().trim(),
                        CSDTO.getMrUnit().trim(),
                        CSDTO.getMrPoNumber().trim(),
                        CSDTO.getRvNumber().trim(),
                        CSDTO.getMrRemarks().trim(),
                        CSDTO.getMrLocation().trim(),
                        CSDTO.getMinLevelDate().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        CSDTO.getCsrvPreparationID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateCsrvPreparationDetail:",e);
        }
        return retVal;
    }
    

    public long getRvNumForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM csrv_preparation_details WHERE rvControlNo=(SELECT MAX(rvControlNo) FROM csrv_preparation_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   CSRVpreparationDTO icdto = new CSRVpreparationDTO();
                    icdto.setRvControlNo((long)(resultMap.get("rvControlNo")));
                    maxNo = (long)(resultMap.get("rvControlNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRvNumForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<CSRVpreparationDTO> getCsrvPreparationReByPo(final long PoNumber) throws AppException {
        List<CSRVpreparationDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM csrv_preparation_details WHERE poNumber=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{PoNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CSRVpreparationDTO dto = new CSRVpreparationDTO();
                    dto.setCsrvPreparationID((Long) (retMap.get("csrvPreparationID")));
                    dto.setRvControlNo((Long) (retMap.get("rvControlNo")));
                    dto.setDate((String) (retMap.get("date")));
                    dto.setPoNumber((String) (retMap.get("poNumber")));
                    dto.setPoNodate((String) (retMap.get("poNodate")));  
                    dto.setDbNumber((String) (retMap.get("dbNumber")));
                    dto.setDbDate((String) (retMap.get("dbDate")));
                    dto.setIndentNumber((String) (retMap.get("indentNumber")));
                    dto.setIndentDate((String) (retMap.get("indentDate")));
                    dto.setUpdateDate((String) (retMap.get("updateDate")));
                    dto.setUpdatedBy((String) (retMap.get("updatedBy")));
                    dto.setDeliveryChallanNo((String) (retMap.get("deliveryChallanNo")));                    
                    dto.setDeliveryChallenDate((String) (retMap.get("deliveryChallenDate")));
                    dto.setInspectedBy((String) (retMap.get("inspectedBy")));
                    dto.setSection((String) (retMap.get("section")));
                    dto.setItemsCovered((String) (retMap.get("itemsCovered")));
                    dto.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dto.setPfCharges((String) (retMap.get("pfCharges")));  
                    dto.setTptCharges((String) (retMap.get("tptCharges")));
                    dto.setOtherCharges((String) (retMap.get("otherCharges")));
                    dto.setRemarks((String) (retMap.get("remarks")));
                    dto.setAmountPaid((String) (retMap.get("amountPaid")));
                    dto.setPaymentTerms((String) (retMap.get("paymentTerms")));
                    dto.setIndentor((String) (retMap.get("indentor")));
                    dto.setPayingAuthority((String) (retMap.get("payingAuthority")));
                    dto.setHeadOfAccount((String) (retMap.get("headOfAccount")));                    
                    dto.setInspectRemarks((String) (retMap.get("inspectRemarks")));                     
                    dto.setGroup((String) (retMap.get("group_g")));
                    dto.setCardNumber((String) (retMap.get("cardNumber")));
                    dto.setCode((String) (retMap.get("code")));
                    dto.setItemDescription((String) (retMap.get("itemDescription")));
                    dto.setPartNumber((String) (retMap.get("partNumber")));
                    dto.setUnit((String) (retMap.get("unit")));
                    dto.setSuppLastReceived((String) (retMap.get("SuppLastReceived")));
                    dto.setBalance((String) (retMap.get("balance")));
                    dto.setBasicRate((String) (retMap.get("basicRate")));  
                    dto.setLifoRate((String) (retMap.get("lifoRate")));
                    dto.setWaRate((String) (retMap.get("waRate")));
                    dto.setRateDate((String) (retMap.get("rateDate")));
                    dto.setMrDate((String) (retMap.get("mrDate")));
                    dto.setQtyAccepted((String) (retMap.get("qtyAccepted")));
                    dto.setMrUnit((String) (retMap.get("mrUnit")));
                    dto.setMrPoNumber((String) (retMap.get("mrPoNumber")));  
                    dto.setRvNumber((String) (retMap.get("rvNumber")));
                    dto.setMrRemarks((String) (retMap.get("mrRemarks")));
                    dto.setMrLocation((String) (retMap.get("mrLocation")));
                    dto.setMinLevelDate((String) (retMap.get("minLevelDate")));                    
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCsrvPreparationReByPo:", e);
        }
        return list;
    }
    
    public List<CsrvPreparationItemsDTO> getCsrvPreparaItemsReByPo(final long PoNumber) throws AppException {
        List<CsrvPreparationItemsDTO> list = new LinkedList<>();
        try {
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.csrvPrepaItemId,a.dbNumBer,a.poNumberI,a.groupCode,a.storeCode, ");
            sb.append("a.item,a.unit,a.orderQty,a.acceptedQty,a.balanceQty,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name,i.description ");
            sb.append(" FROM csrv_preparation_items a ");
            sb.append(" JOIN unit_master u ON a.unit = u.unit_code");
            sb.append(" JOIN item_master i ON a.item = i.item_code");
            sb.append(" WHERE poNumberI =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{PoNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    CsrvPreparationItemsDTO dto = new CsrvPreparationItemsDTO();
                    dto.setCsrvPrepaItemId((Long) (retMap.get("csrvPrepaItemId")));
                    dto.setDbNumBer((Long) (retMap.get("dbNumBer")));
                    dto.setPoNumberI((Long) (retMap.get("poNumberI")));
                    dto.setGroupCode((String) (retMap.get("groupCode")));
                    dto.setStoreCode((String) (retMap.get("storeCode")));  
                    dto.setItem((String) (retMap.get("item")));
                    dto.setUnit((String) (retMap.get("unit")));
                    dto.setOrderQty((String) (retMap.get("orderQty")));
                    dto.setAcceptedQty((String) (retMap.get("acceptedQty")));
                    dto.setBalanceQty((String) (retMap.get("balanceQty"))); 
                    dto.getUnitObj().setUnitName((String)(retMap.get("unit_name")));  
                    dto.getItemObj().setItemName((String)(retMap.get("item_name")));
                    dto.getItemObj().setDescription((String)(retMap.get("description")));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCsrvPreparaItemsReByPo:", e);
        }
        return list;
    }
}
