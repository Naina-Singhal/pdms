/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.CsrvPreparationItemsDTO;
import com.pdms.itemsDto.receipt.MaterialRequisitionItemsDTO;
import com.pdms.itemsDto.receipt.MaterialRequisitionTempDTO;
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
public class MaterialRequisitionDAOImpl {
    
    private static final Logger logger = Logger.getLogger(MaterialRequisitionDAOImpl.class);

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
    public MaterialRequisitionDAOImpl(){
        
    }
    
    
    public int insertMaterialRequisition(final MaterialRequisitionTempDTO matrsObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO material_requisition_details  ");
                        sb.append("(materialReqId,poNumber,pleaseSelIssType,whetherDispIss,releaseSecReq,otherSecName,secOrDivision,jobAllocation,");
                        sb.append("itemCovered,toBeDeliAt,issueType,reqNumber,date,vrDate,");
                        sb.append("disposalIssue,fromTo,woNumber,woDate,releasrReqd,fromSection,deliveredAt,");
                        sb.append("itemSelected,indentor,section,recdBy,authorisedBy,");
                        sb.append("releasedBy,issuedBy,session_user_id,mr_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<MaterialRequisitionDTO> formData = matrsObj.getRequiDTO();
                        for (MaterialRequisitionDTO maDto : formData) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{maDto.getMaterialReqId(),
                                        maDto.getPoNumber(),
                                        maDto.getPleaseSelIssType().trim(),
                                        maDto.getWhetherDispIss().trim(),
                                        maDto.getReleaseSecReq().trim(),
                                        maDto.getOtherSecName().trim(),
                                        maDto.getSecOrDivision().trim(),
                                        maDto.getJobAllocation().trim(),
                                        maDto.getItemCovered().trim(),
                                        maDto.getToBeDeliAt().trim(),
                                        maDto.getIssueType().trim(),
                                        maDto.getReqNumber(),
                                        maDto.getDate().trim(),
                                        maDto.getVrDate().trim(),
                                        maDto.getDisposalIssue().trim(),
                                        maDto.getFromTo().trim(),
                                        maDto.getWoNumber().trim(),
                                        maDto.getWoDate().trim(),
                                        maDto.getReleasrReqd().trim(),
                                        maDto.getFromSection().trim(),
                                        maDto.getDeliveredAt().trim(),
                                        maDto.getItemSelected().trim(),
                                        maDto.getIndentor().trim(),
                                        maDto.getSection().trim(),
                                        maDto.getRecdBy().trim(),
                                        maDto.getAuthorisedBy().trim(),
                                        maDto.getReleasedBy().trim(),
                                        maDto.getIssuedBy().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        
                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO material_requisition_items  ");
                        sbItem.append("(mateRequiItemsId,requisitionNo,categoryId,store,groupCode,cardNo,");
                        sbItem.append("item,itemDes,unit,qtyRged,qtyIssued,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<MaterialRequisitionItemsDTO> items = matrsObj.getRequiItemsDTO();
                        for (MaterialRequisitionItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getMateRequiItemsId(),
                                        itemObj.getRequisitionNo(),
                                        itemObj.getCategoryId(),
                                        itemObj.getStore().trim(),
                                        itemObj.getGroupCode().trim(),
                                        itemObj.getCardNo().trim(),
                                        itemObj.getItem().trim(),
                                        itemObj.getItemDes().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getQtyRged().trim(),
                                        itemObj.getQtyIssued().trim(),
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
    
    public List<MaterialRequisitionDTO> getMaterialRequiRecord() throws AppException {
        List<MaterialRequisitionDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM material_requisition_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    MaterialRequisitionDTO dTO = new MaterialRequisitionDTO();
                    dTO.setMaterialReqId((Long) (retMap.get("materialReqId")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setPleaseSelIssType((String) (retMap.get("pleaseSelIssType")));
                    dTO.setWhetherDispIss((String) (retMap.get("whetherDispIss")));
                    dTO.setReleaseSecReq((String) (retMap.get("releaseSecReq")));
                    dTO.setOtherSecName((String) (retMap.get("otherSecName")));  
                    dTO.setSecOrDivision((String) (retMap.get("secOrDivision")));
                    dTO.setJobAllocation((String) (retMap.get("jobAllocation")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setToBeDeliAt((String) (retMap.get("toBeDeliAt")));
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setReqNumber((Long) (retMap.get("reqNumber")));
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setVrDate((String) (retMap.get("vrDate")));
                    dTO.setDisposalIssue((String) (retMap.get("disposalIssue")));
                    dTO.setFromTo((String) (retMap.get("fromTo")));  
                    dTO.setWoNumber((String) (retMap.get("woNumber")));
                    dTO.setWoDate((String) (retMap.get("woDate")));
                    dTO.setReleasrReqd((String) (retMap.get("releasrReqd")));
                    dTO.setFromSection((String) (retMap.get("fromSection")));
                    dTO.setDeliveredAt((String) (retMap.get("deliveredAt")));
                    dTO.setItemSelected((String) (retMap.get("itemSelected")));
                    dTO.setIndentor((String) (retMap.get("indentor")));  
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setRecdBy((String) (retMap.get("recdBy")));
                    dTO.setAuthorisedBy((String) (retMap.get("authorisedBy")));                    
                    dTO.setReleasedBy((String) (retMap.get("releasedBy")));                    
                    dTO.setIssuedBy((String) (retMap.get("issuedBy")));                                       
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRequiRecord:", e);
        }
        return list;
    }
    
    public List<MaterialRequisitionDTO> getMaterialRequisitionReById(final long id) throws AppException {
        List<MaterialRequisitionDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM material_requisition_details WHERE materialReqId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    MaterialRequisitionDTO dTO = new MaterialRequisitionDTO();
                    dTO.setMaterialReqId((Long) (retMap.get("materialReqId")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setPleaseSelIssType((String) (retMap.get("pleaseSelIssType")));
                    dTO.setWhetherDispIss((String) (retMap.get("whetherDispIss")));
                    dTO.setReleaseSecReq((String) (retMap.get("releaseSecReq")));
                    dTO.setOtherSecName((String) (retMap.get("otherSecName")));  
                    dTO.setSecOrDivision((String) (retMap.get("secOrDivision")));
                    dTO.setJobAllocation((String) (retMap.get("jobAllocation")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setToBeDeliAt((String) (retMap.get("toBeDeliAt")));
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setReqNumber((Long) (retMap.get("reqNumber")));
                    dTO.setDate((String) (retMap.get("date")));  
                    dTO.setVrDate((String) (retMap.get("vrDate")));
                    dTO.setDisposalIssue((String) (retMap.get("disposalIssue")));
                    dTO.setFromTo((String) (retMap.get("fromTo")));  
                    dTO.setWoNumber((String) (retMap.get("woNumber")));
                    dTO.setWoDate((String) (retMap.get("woDate")));
                    dTO.setReleasrReqd((String) (retMap.get("releasrReqd")));
                    dTO.setFromSection((String) (retMap.get("fromSection")));
                    dTO.setDeliveredAt((String) (retMap.get("deliveredAt")));
                    dTO.setItemSelected((String) (retMap.get("itemSelected")));
                    dTO.setIndentor((String) (retMap.get("indentor")));  
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setRecdBy((String) (retMap.get("recdBy")));
                    dTO.setAuthorisedBy((String) (retMap.get("authorisedBy")));                    
                    dTO.setReleasedBy((String) (retMap.get("releasedBy")));                    
                    dTO.setIssuedBy((String) (retMap.get("issuedBy")));                                       
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRequisitionReById:", e);
        }
        return list;
    }
    
    public int updateMaterialRquisitionDetail(final List<MaterialRequisitionDTO> matrsObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE material_requisition_details  ");
            sb.append(" SET poNumber=?,pleaseSelIssType=?,whetherDispIss=?,releaseSecReq=?,otherSecName=?,secOrDivision=?,jobAllocation=?,itemCovered=?, ");  
            sb.append("toBeDeliAt=?,issueType=?,reqNumber=?,date=?,vrDate=?,disposalIssue=?, ");
            sb.append("fromTo=?,woNumber=?,woDate=?,releasrReqd=?,fromSection=?,deliveredAt=?,itemSelected=?, ");
            sb.append("indentor=?,section=?,recdBy=?,authorisedBy=?,releasedBy=?, ");
            sb.append("issuedBy=?,session_user_id=?,mr_date=?  ");
            sb.append(" WHERE materialReqId=? ");
            
            for(MaterialRequisitionDTO maDto : matrsObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{   
                        maDto.getPoNumber(),
                        maDto.getPleaseSelIssType().trim(),
                        maDto.getWhetherDispIss().trim(),
                        maDto.getReleaseSecReq().trim(),
                        maDto.getOtherSecName().trim(),
                        maDto.getSecOrDivision().trim(),
                        maDto.getJobAllocation().trim(),
                        maDto.getItemCovered().trim(),
                        maDto.getToBeDeliAt().trim(),
                        maDto.getIssueType().trim(),
                        maDto.getReqNumber(),
                        maDto.getDate().trim(),                        
                        maDto.getVrDate().trim(),
                        maDto.getDisposalIssue().trim(),
                        maDto.getFromTo().trim(),
                        maDto.getWoNumber().trim(),
                        maDto.getWoDate().trim(),
                        maDto.getReleasrReqd().trim(),
                        maDto.getFromSection().trim(),
                        maDto.getDeliveredAt().trim(),
                        maDto.getItemSelected().trim(),
                        maDto.getIndentor().trim(),                       
                        maDto.getSection().trim(),
                        maDto.getRecdBy().trim(),
                        maDto.getAuthorisedBy().trim(),                        
                        maDto.getReleasedBy().trim(),                        
                        maDto.getIssuedBy().trim(),                                               
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        maDto.getMaterialReqId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateMaterialRquisitionDetail:",e);
        }
        return retVal;
    }
    
    public long getRequisitionForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM material_requisition_details WHERE reqNumber=(SELECT MAX(reqNumber) FROM material_requisition_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   MaterialRequisitionDTO icdto = new MaterialRequisitionDTO();
                    icdto.setReqNumber((long)(resultMap.get("reqNumber")));
                    maxNo = (long)(resultMap.get("reqNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRequisitionForIncrement:",e);
        }
        return maxNo;
    }
    


 public List<MaterialRequisitionDTO> getMaterialRequiReByRequiNo(final long requisiNo) throws AppException {
        List<MaterialRequisitionDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM material_requisition_details WHERE reqNumber=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{requisiNo});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    MaterialRequisitionDTO dTO = new MaterialRequisitionDTO();
                    dTO.setMaterialReqId((Long) (retMap.get("materialReqId")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setPleaseSelIssType((String) (retMap.get("pleaseSelIssType")));
                    dTO.setWhetherDispIss((String) (retMap.get("whetherDispIss")));
                    dTO.setReleaseSecReq((String) (retMap.get("releaseSecReq")));
                    dTO.setOtherSecName((String) (retMap.get("otherSecName")));  
                    dTO.setSecOrDivision((String) (retMap.get("secOrDivision")));
                    dTO.setJobAllocation((String) (retMap.get("jobAllocation")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setToBeDeliAt((String) (retMap.get("toBeDeliAt")));
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setReqNumber((Long) (retMap.get("reqNumber")));
                    dTO.setDate((String) (retMap.get("date")));  
                    dTO.setVrDate((String) (retMap.get("vrDate")));
                    dTO.setDisposalIssue((String) (retMap.get("disposalIssue")));
                    dTO.setFromTo((String) (retMap.get("fromTo")));  
                    dTO.setWoNumber((String) (retMap.get("woNumber")));
                    dTO.setWoDate((String) (retMap.get("woDate")));
                    dTO.setReleasrReqd((String) (retMap.get("releasrReqd")));
                    dTO.setFromSection((String) (retMap.get("fromSection")));
                    dTO.setDeliveredAt((String) (retMap.get("deliveredAt")));
                    dTO.setItemSelected((String) (retMap.get("itemSelected")));
                    dTO.setIndentor((String) (retMap.get("indentor")));  
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setRecdBy((String) (retMap.get("recdBy")));
                    dTO.setAuthorisedBy((String) (retMap.get("authorisedBy")));                    
                    dTO.setReleasedBy((String) (retMap.get("releasedBy")));                    
                    dTO.setIssuedBy((String) (retMap.get("issuedBy")));                                       
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaterialRequiReByRequiNo:", e);
        }
        return list;
    }
 
}