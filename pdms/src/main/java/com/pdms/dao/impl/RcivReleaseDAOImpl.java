/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.dto.RcivReleaseDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.RcivReleaseItemsDTO;
import com.pdms.itemsDto.receipt.RcivReleaseTempDTO;
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
public class RcivReleaseDAOImpl {
    
    
    private static final Logger logger = Logger.getLogger(RcivReleaseDAOImpl.class);

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
    public RcivReleaseDAOImpl(){
        
    }
    
    
    public int insertRcivRelease(final RcivReleaseTempDTO rcivReleObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        
                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO rciv_release_details  ");
                        sb.append("(rcivReleaseID,poNumberR,storeRequisitionR,plantR,dateR,issueTypeR,issueToR,");
                        sb.append("disposalR,issueFromOthSecNaR,requisitionNumberR,requisitionNoDateR,");
                        sb.append("indentorNameR,sectionR,jobAllocationR,deliveryAtR,itemCoveredR,authorisedByR,");
                        sb.append("authorisedByDateR,authorisedRemarksR,releasedYesNoR,releasedByR,releasedByDateR,releasedRemarksR,");
                        sb.append("session_user_id,rciv_R_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<RcivReleaseDTO> rcivDto = rcivReleObj.getReleaseDTO();
                        for (RcivReleaseDTO reObj : rcivDto) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{reObj.getRcivReleaseID(),
                                        reObj.getPoNumberR(),
                                        reObj.getStoreRequisitionR().trim(),
                                        reObj.getPlantR().trim(),
                                        reObj.getDateR().trim(),
                                        reObj.getIssueTypeR().trim(),
                                        reObj.getIssueToR().trim(),
                                        reObj.getDisposalR().trim(),
                                        reObj.getIssueFromOthSecNaR().trim(),
                                        reObj.getRequisitionNumberR().trim(),
                                        reObj.getRequisitionNoDateR().trim(),
                                        reObj.getIndentorNameR().trim(),
                                        reObj.getSectionR().trim(),
                                        reObj.getJobAllocationR().trim(),
                                        reObj.getDeliveryAtR().trim(),
                                        reObj.getItemCoveredR().trim(),
                                        reObj.getAuthorisedByR().trim(),
                                        reObj.getAuthorisedByDateR().trim(),
                                        reObj.getAuthorisedRemarksR().trim(),
                                        reObj.getReleasedYesNoR().trim(),
                                        reObj.getReleasedByR().trim(),
                                        reObj.getReleasedByDateR().trim(),
                                        reObj.getReleasedRemarksR().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO rciv_release_items  ");
                        sbItem.append("(rcivReleaseItemId,poNumBerR,requisitionNoR,storeR,groupCodeR,storeCardNoR,itemR,");
                        sbItem.append("itemDesR,unitR,requiredQtyR,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<RcivReleaseItemsDTO> items = rcivReleObj.getReleaseItemsDTO();
                        for (RcivReleaseItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getRcivReleaseItemId(),
                                        itemObj.getPoNumBerR(),
                                        itemObj.getRequisitionNoR(),
                                        itemObj.getStoreR().trim(),
                                        itemObj.getGroupCodeR().trim(),
                                        itemObj.getStoreCardNoR().trim(),
                                        itemObj.getItemR().trim(),
                                        itemObj.getItemDesR().trim(),
                                        itemObj.getUnitR().trim(),
                                        itemObj.getRequiredQtyR().trim(),
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
                        logger.error("Exception Occured In:insertRcivRelease");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertRcivRelease: " + e.getMessage());
        }
    }
    
    public List<RcivReleaseDTO> getRcivReleaseRecord() throws AppException {
        List<RcivReleaseDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM rciv_release_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivReleaseDTO dtObj = new RcivReleaseDTO();
                    dtObj.setRcivReleaseID((Long) (retMap.get("rcivReleaseID")));     
                    dtObj.setPoNumberR((Long) (retMap.get("poNumberR")));
                    dtObj.setStoreRequisitionR((String) (retMap.get("storeRequisitionR")));
                    dtObj.setPlantR((String) (retMap.get("plantR")));
                    dtObj.setDateR((String) (retMap.get("dateR")));  
                    dtObj.setIssueTypeR((String) (retMap.get("issueTypeR")));
                    dtObj.setIssueToR((String) (retMap.get("issueToR")));
                    dtObj.setDisposalR((String) (retMap.get("disposalR")));
                    dtObj.setIssueFromOthSecNaR((String) (retMap.get("issueFromOthSecNaR")));
                    dtObj.setRequisitionNumberR((String) (retMap.get("requisitionNumberR")));
                    dtObj.setRequisitionNoDateR((String) (retMap.get("requisitionNoDateR")));
                    dtObj.setIndentorNameR((String) (retMap.get("indentorNameR")));  
                    dtObj.setSectionR((String) (retMap.get("sectionR")));
                    dtObj.setJobAllocationR((String) (retMap.get("jobAllocationR")));
                    dtObj.setDeliveryAtR((String) (retMap.get("deliveryAtR")));
                    dtObj.setItemCoveredR((String) (retMap.get("itemCoveredR")));
                    dtObj.setAuthorisedByR((String) (retMap.get("authorisedByR")));                    
                    dtObj.setAuthorisedByDateR((String) (retMap.get("authorisedByDateR")));                    
                    dtObj.setAuthorisedRemarksR((String) (retMap.get("authorisedRemarksR")));
                    dtObj.setReleasedYesNoR((String) (retMap.get("releasedYesNoR")));
                    dtObj.setReleasedByR((String) (retMap.get("releasedByR")));                    
                    dtObj.setReleasedByDateR((String) (retMap.get("releasedByDateR")));
                    dtObj.setReleasedRemarksR((String) (retMap.get("releasedRemarksR")));
                    list.add(dtObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivReleaseRecord:", e);
        }
        return list;
    }
    
    public List<RcivReleaseDTO> getRcivReleaseReById(final long id) throws AppException {
        List<RcivReleaseDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM rciv_release_details WHERE rcivReleaseID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivReleaseDTO dtObj = new RcivReleaseDTO();
                    dtObj.setRcivReleaseID((Long) (retMap.get("rcivReleaseID")));
                    dtObj.setPoNumberR((Long) (retMap.get("poNumberR")));
                    dtObj.setStoreRequisitionR((String) (retMap.get("storeRequisitionR")));
                    dtObj.setPlantR((String) (retMap.get("plantR")));
                    dtObj.setDateR((String) (retMap.get("dateR")));  
                    dtObj.setIssueTypeR((String) (retMap.get("issueTypeR")));
                    dtObj.setIssueToR((String) (retMap.get("issueToR")));
                    dtObj.setDisposalR((String) (retMap.get("disposalR")));
                    dtObj.setIssueFromOthSecNaR((String) (retMap.get("issueFromOthSecNaR")));
                    dtObj.setRequisitionNumberR((String) (retMap.get("requisitionNumberR")));
                    dtObj.setRequisitionNoDateR((String) (retMap.get("requisitionNoDateR")));  
                    dtObj.setIndentorNameR((String) (retMap.get("indentorNameR")));  
                    dtObj.setSectionR((String) (retMap.get("sectionR")));
                    dtObj.setJobAllocationR((String) (retMap.get("jobAllocationR")));
                    dtObj.setDeliveryAtR((String) (retMap.get("deliveryAtR")));
                    dtObj.setItemCoveredR((String) (retMap.get("itemCoveredR")));
                    dtObj.setAuthorisedByR((String) (retMap.get("authorisedByR")));                    
                    dtObj.setAuthorisedByDateR((String) (retMap.get("authorisedByDateR")));                    
                    dtObj.setAuthorisedRemarksR((String) (retMap.get("authorisedRemarksR")));
                    dtObj.setReleasedYesNoR((String) (retMap.get("releasedYesNoR")));
                    dtObj.setReleasedByR((String) (retMap.get("releasedByR")));                    
                    dtObj.setReleasedByDateR((String) (retMap.get("releasedByDateR")));
                    dtObj.setReleasedRemarksR((String) (retMap.get("releasedRemarksR")));
                    list.add(dtObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivReleaseReById:", e);
        }
        return list;
    }
    
    public int updateRcivReleaseDetail(final List<RcivReleaseDTO> rcivReleObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE rciv_release_details  ");
            sb.append(" SET poNumberR=?,storeRequisitionR=?,plantR=?,dateR=?,issueTypeR=?,issueToR=?,disposalR=?,issueFromOthSecNaR=?,");  
            sb.append(" requisitionNumberR=?,requisitionNoDateR=?,indentorNameR=?,sectionR=?,jobAllocationR=?, ");
            sb.append(" deliveryAtR=?,itemCoveredR=?,authorisedByR=?,authorisedByDateR=?,authorisedRemarksR=?,releasedYesNoR=?,releasedByR=?, ");
            sb.append(" releasedByDateR=?,releasedRemarksR=?,session_user_id=?,rciv_R_date=?  ");
            sb.append(" WHERE rcivReleaseID=? ");
            
            for(RcivReleaseDTO reObj : rcivReleObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{   
                        reObj.getPoNumberR(),
                        reObj.getStoreRequisitionR().trim(),
                        reObj.getPlantR().trim(),
                        reObj.getDateR().trim(),
                        reObj.getIssueTypeR().trim(),                        
                        reObj.getIssueToR().trim(),
                        reObj.getDisposalR().trim(),
                        reObj.getIssueFromOthSecNaR().trim(),
                        reObj.getRequisitionNumberR().trim(),
                        reObj.getRequisitionNoDateR().trim(),  
                        reObj.getIndentorNameR().trim(),
                        reObj.getSectionR().trim(),
                        reObj.getJobAllocationR().trim(),
                        reObj.getDeliveryAtR().trim(),
                        reObj.getItemCoveredR().trim(),
                        reObj.getAuthorisedByR().trim(),                        
                        reObj.getAuthorisedByDateR().trim(),
                        reObj.getAuthorisedRemarksR().trim(),
                        reObj.getReleasedYesNoR().trim(),
                        reObj.getReleasedByR().trim(),                        
                        reObj.getReleasedByDateR().trim(),
                        reObj.getReleasedRemarksR().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        reObj.getRcivReleaseID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateRcivReleaseDetail:",e);
        }
        return retVal;
    }
    
    
    public List<RcivReleaseItemsDTO> getRcivReleaseItemsByPoNo(final long PoNumber) throws AppException {
        List<RcivReleaseItemsDTO> list = new LinkedList<>();
        try {
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.rcivReleaseItemId,a.poNumBerR,a.requisitionNoR,a.storeR,a.groupCodeR, ");
            sb.append("a.storeCardNoR,a.itemR,a.itemDesR,a.unitR,a.requiredQtyR,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name,i.description ");
            sb.append(" FROM rciv_release_items a ");
            sb.append(" JOIN unit_master u ON a.unitR = u.unit_code");
            sb.append(" JOIN item_master i ON a.itemR = i.item_code");
            sb.append(" WHERE poNumBerR =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{PoNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivReleaseItemsDTO dto = new RcivReleaseItemsDTO();
                    dto.setRcivReleaseItemId((Long) (retMap.get("rcivReleaseItemId")));
                    dto.setPoNumBerR((Long) (retMap.get("poNumBerR")));
                    dto.setRequisitionNoR((Long) (retMap.get("requisitionNoR")));
                    dto.setStoreR((String) (retMap.get("storeR")));
                    dto.setGroupCodeR((String) (retMap.get("groupCodeR")));  
                    dto.setStoreCardNoR((String) (retMap.get("storeCardNoR")));
                    dto.setItemR((String) (retMap.get("itemR")));
                    dto.setItemDesR((String) (retMap.get("itemDesR")));
                    dto.setUnitR((String) (retMap.get("unitR")));
                    dto.setRequiredQtyR((String) (retMap.get("requiredQtyR"))); 
                    dto.getUnitObj().setUnitName((String)(retMap.get("unit_name")));  
                    dto.getItemObj().setItemName((String)(retMap.get("item_name")));
                    dto.getItemObj().setDescription((String)(retMap.get("description")));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivReleaseItemsByPoNo:", e);
        }
        return list;
    }

}
