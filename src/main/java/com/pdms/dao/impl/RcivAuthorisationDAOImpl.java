/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.RcivAuthorisationItemsDTO;
import com.pdms.itemsDto.receipt.RcivAuthorisationTempDTO;
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
 * @author STEINMETZ
 */
@Repository
public class RcivAuthorisationDAOImpl {
     
    private static final Logger logger = Logger.getLogger(RcivAuthorisationDAOImpl.class);

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
    public RcivAuthorisationDAOImpl(){
        
    }
    
    
    public int insertRcivAuthorisation(final RcivAuthorisationTempDTO rcivAuthObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO rciv_authorisation_details  ");
                        sb.append("(rcivAuthoriID,poNumber,authoriStatus,storeRequisition,plant,date,issueType,");
                        sb.append("issueTo,disposal,issueFromOSecNa,requisitionNo,requisitionDate,controlDate,");
                        sb.append("indentorName,section,jobAllocation,deliveryAt,itemCovered,AuthorisedYesNo,");
                        sb.append("authorisedBy,authorisedByDate,remarks,session_user_id,rciv_A_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<RcivAuthorisationDTO> authForm = rcivAuthObj.getAuthoDTO();
                        for (RcivAuthorisationDTO rcivObj : authForm) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{rcivObj.getRcivAuthoriID(),
                                        rcivObj.getPoNumber(),
                                        rcivObj.getAuthoriStatus().trim(),
                                        rcivObj.getStoreRequisition().trim(),
                                        rcivObj.getPlant().trim(),
                                        rcivObj.getDate().trim(),
                                        rcivObj.getIssueType().trim(),
                                        rcivObj.getIssueTo().trim(),
                                        rcivObj.getDisposal().trim(),
                                        rcivObj.getIssueFromOSecNa().trim(),
                                        rcivObj.getRequisitionNo().trim(),
                                        rcivObj.getRequisitionDate().trim(),
                                        rcivObj.getControlDate().trim(),
                                        rcivObj.getIndentorName().trim(),
                                        rcivObj.getSection().trim(),
                                        rcivObj.getJobAllocation().trim(),
                                        rcivObj.getDeliveryAt().trim(),
                                        rcivObj.getItemCovered().trim(),
                                        rcivObj.getAuthorisedYesNo().trim(),
                                        rcivObj.getAuthorisedBy().trim(),
                                        rcivObj.getAuthorisedByDate().trim(),
                                        rcivObj.getRemarks().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO rciv_authorisation_items  ");
                        sbItem.append("(rcivAuthoItemId,poNumBer,requisitionNo,store,groupCode,storeCardNo,item,");
                        sbItem.append("itemDes,unit,requiredQty,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<RcivAuthorisationItemsDTO> items = rcivAuthObj.getAuthoItemsDTO();
                        for (RcivAuthorisationItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getRcivAuthoItemId(),
                                        itemObj.getPoNumBer(),
                                        itemObj.getRequisitionNo(),
                                        itemObj.getStore().trim(),
                                        itemObj.getGroupCode().trim(),
                                        itemObj.getStoreCardNo().trim(),
                                        itemObj.getItem().trim(),
                                        itemObj.getItemDes().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getRequiredQty().trim(),
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
                        logger.error("Exception Occured In:insertRcivAuthorisation");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertRcivAuthorisation: " + e.getMessage());
        }
    }

    public List<RcivAuthorisationDTO> getRcivAuthoriRecord() throws AppException {
        List<RcivAuthorisationDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM rciv_authorisation_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivAuthorisationDTO dTO = new RcivAuthorisationDTO();
                    dTO.setRcivAuthoriID((Long) (retMap.get("rcivAuthoriID"))); 
                    dTO.setPoNumber((Long) (retMap.get("poNumber"))); 
                    dTO.setAuthoriStatus((String) (retMap.get("authoriStatus")));
                    dTO.setStoreRequisition((String) (retMap.get("storeRequisition")));
                    dTO.setPlant((String) (retMap.get("plant")));  
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setIssueTo((String) (retMap.get("issueTo")));
                    dTO.setDisposal((String) (retMap.get("disposal")));
                    dTO.setIssueFromOSecNa((String) (retMap.get("issueFromOSecNa")));
                    dTO.setRequisitionNo((String) (retMap.get("requisitionNo")));
                    dTO.setRequisitionDate((String) (retMap.get("requisitionDate")));                    
                    dTO.setControlDate((String) (retMap.get("controlDate")));                     
                    dTO.setIndentorName((String) (retMap.get("indentorName")));
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setJobAllocation((String) (retMap.get("jobAllocation")));
                    dTO.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setAuthorisedYesNo((String) (retMap.get("AuthorisedYesNo")));
                    dTO.setAuthorisedBy((String) (retMap.get("authorisedBy"))); 
                    dTO.setAuthorisedByDate((String) (retMap.get("authorisedByDate")));
                    dTO.setRemarks((String) (retMap.get("remarks")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivAuthoriRecord:", e);
        }
        return list;
    }
    
    public List<RcivAuthorisationDTO> getRcivAuthorisationReById(final long id) throws AppException {
        List<RcivAuthorisationDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM rciv_authorisation_details WHERE rcivAuthoriID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivAuthorisationDTO dTO = new RcivAuthorisationDTO();
                    dTO.setRcivAuthoriID((Long) (retMap.get("rcivAuthoriID"))); 
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setAuthoriStatus((String) (retMap.get("authoriStatus")));
                    dTO.setStoreRequisition((String) (retMap.get("storeRequisition")));
                    dTO.setPlant((String) (retMap.get("plant")));  
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setIssueTo((String) (retMap.get("issueTo")));
                    dTO.setDisposal((String) (retMap.get("disposal")));
                    dTO.setIssueFromOSecNa((String) (retMap.get("issueFromOSecNa")));
                    dTO.setRequisitionNo((String) (retMap.get("requisitionNo")));
                    dTO.setRequisitionDate((String) (retMap.get("requisitionDate")));                    
                    dTO.setControlDate((String) (retMap.get("controlDate")));                     
                    dTO.setIndentorName((String) (retMap.get("indentorName")));
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setJobAllocation((String) (retMap.get("jobAllocation")));
                    dTO.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setAuthorisedYesNo((String) (retMap.get("AuthorisedYesNo")));
                    dTO.setAuthorisedBy((String) (retMap.get("authorisedBy"))); 
                    dTO.setAuthorisedByDate((String) (retMap.get("authorisedByDate")));
                    dTO.setRemarks((String) (retMap.get("remarks")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivAuthorisationReById:", e);
        }
        return list;
    }
    
    public int updateRcivAuthorisationDetail(final List<RcivAuthorisationDTO> rcivAuthObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE rciv_authorisation_details  ");
            sb.append(" SET poNumber=?,authoriStatus=?,storeRequisition=?,plant=?,date=?,issueType=?,issueTo=?,disposal=?,");  
            sb.append("issueFromOSecNa=?,requisitionNo=?,requisitionDate=?,controlDate=?,indentorName=?,section=?, ");
            sb.append("jobAllocation=?,deliveryAt=?,itemCovered=?,AuthorisedYesNo=?,authorisedBy=?,authorisedByDate=?,remarks=?, ");
            sb.append("session_user_id=?,rciv_A_date=?  WHERE rcivAuthoriID=? ");
            
            for(RcivAuthorisationDTO rcivObj : rcivAuthObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ 
                        rcivObj.getPoNumber(),
                        rcivObj.getAuthoriStatus().trim(),
                        rcivObj.getStoreRequisition().trim(),
                        rcivObj.getPlant().trim(),
                        rcivObj.getDate().trim(),
                        rcivObj.getIssueType().trim(),
                        rcivObj.getIssueTo().trim(),
                        rcivObj.getDisposal().trim(),
                        rcivObj.getIssueFromOSecNa().trim(),
                        rcivObj.getRequisitionNo().trim(),
                        rcivObj.getRequisitionDate().trim(),                        
                        rcivObj.getControlDate().trim(),                        
                        rcivObj.getIndentorName().trim(),
                        rcivObj.getSection().trim(),
                        rcivObj.getJobAllocation().trim(),
                        rcivObj.getDeliveryAt().trim(),
                        rcivObj.getItemCovered().trim(),
                        rcivObj.getAuthorisedYesNo().trim(),
                        rcivObj.getAuthorisedBy().trim(),                        
                        rcivObj.getAuthorisedByDate().trim(),
                        rcivObj.getRemarks().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        rcivObj.getRcivAuthoriID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateRcivAuthorisationDetail:",e);
        }
        return retVal;
    }
    
     
    public List<RcivAuthorisationItemsDTO> getRcivAuthorisaItemsByPoNo(final long PoNumber) throws AppException {
        List<RcivAuthorisationItemsDTO> list = new LinkedList<>();
        try {
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.rcivAuthoItemId,a.poNumBer,a.requisitionNo,a.store,a.groupCode, ");
            sb.append("a.storeCardNo,a.item,a.itemDes,a.unit,a.requiredQty,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name,i.description ");
            sb.append(" FROM rciv_authorisation_items a ");
            sb.append(" JOIN unit_master u ON a.unit = u.unit_code");
            sb.append(" JOIN item_master i ON a.item = i.item_code");
            sb.append(" WHERE poNumBer =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{PoNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivAuthorisationItemsDTO dto = new RcivAuthorisationItemsDTO();
                    dto.setRcivAuthoItemId((Long) (retMap.get("rcivAuthoItemId")));
                    dto.setPoNumBer((Long) (retMap.get("poNumBer")));
                    dto.setRequisitionNo((Long) (retMap.get("requisitionNo")));
                    dto.setStore((String) (retMap.get("store")));
                    dto.setGroupCode((String) (retMap.get("groupCode")));  
                    dto.setStoreCardNo((String) (retMap.get("storeCardNo")));
                    dto.setItem((String) (retMap.get("item")));
                    dto.setItemDes((String) (retMap.get("itemDes")));
                    dto.setUnit((String) (retMap.get("unit")));
                    dto.setRequiredQty((String) (retMap.get("requiredQty"))); 
                    dto.getUnitObj().setUnitName((String)(retMap.get("unit_name")));  
                    dto.getItemObj().setItemName((String)(retMap.get("item_name")));
                    dto.getItemObj().setDescription((String)(retMap.get("description")));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivAuthorisaItemsByPoNo:", e);
        }
        return list;
    }
    
}
