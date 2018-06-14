/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.dto.MaterialRequisitionDTO;
import com.pdms.dto.RcivAuthorisationDTO;
import com.pdms.dto.RcivControlDTO;
import com.pdms.dto.RcivReleaseDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivControlItemsDTO;
import com.pdms.itemsDto.receipt.RcivControlTempDTO;
import com.pdms.itemsDto.receipt.RcivReleaseItemsDTO;
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
public class RcivControlDAOImpl {
    
    private static final Logger logger = Logger.getLogger(RcivControlDAOImpl.class);

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
    public RcivControlDAOImpl(){
        
    }
    
    
    public int insertRcivControlData(final RcivControlTempDTO rcivCtlObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO rciv_control_details  ");
                        sb.append("(rcicControlID,poNumberC,authorisedStatusC,storeRequisitionC,plantC,dateC,issueTypeC,");
                        sb.append("issueToC,disposalC,issueFrOtherSecNaC,requisitionNumberC,requisitionDateC,controlNumberC,controlDateC,");
                        sb.append("indentorNameC,sectionC,jobAllocationC,deliveryAtC,itemCoveredC,authorisedByNoC,");
                        sb.append("authorisedByDateC,authorisedRemarksC,releasedByNoC,releasedByDateC,releasedRemarksC,");
                        sb.append("session_user_id,rciv_C_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        
                        List<RcivControlDTO> contDto = rcivCtlObj.getControlDTO();
                        for (RcivControlDTO conObj : contDto) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{conObj.getRcicControlID(),
                                        conObj.getPoNumberC(),
                                        conObj.getAuthorisedStatusC().trim(),
                                        conObj.getStoreRequisitionC().trim(),
                                        conObj.getPlantC().trim(),
                                        conObj.getDateC().trim(),
                                        conObj.getIssueTypeC().trim(),
                                        conObj.getIssueToC().trim(),
                                        conObj.getDisposalC().trim(),
                                        conObj.getIssueFrOtherSecNaC().trim(),
                                        conObj.getRequisitionNumberC().trim(),
                                        conObj.getRequisitionDateC().trim(),
                                        conObj.getControlNumberC(),
                                        conObj.getControlDateC().trim(),
                                        conObj.getIndentorNameC().trim(),
                                        conObj.getSectionC().trim(),
                                        conObj.getJobAllocationC().trim(),
                                        conObj.getDeliveryAtC().trim(),
                                        conObj.getItemCoveredC().trim(),
                                        conObj.getAuthorisedByNoC().trim(),
                                        conObj.getAuthorisedByDateC().trim(),
                                        conObj.getAuthorisedRemarksC().trim(),
                                        conObj.getReleasedByNoC().trim(),
                                        conObj.getReleasedByDateC().trim(),
                                        conObj.getReleasedRemarksC().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO rciv_control_items  ");
                        sbItem.append("(rcivContrItemId,conNumberC,requisitionNoC,storeC,groupCodeC,storeCardNoC,itemC,itemDesC,");
                        sbItem.append("unitC,requiredQtyC,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<RcivControlItemsDTO> items = rcivCtlObj.getControlItemsDTO();
                        for (RcivControlItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getRcivContrItemId(),
                                        itemObj.getConNumberC(),
                                        itemObj.getRequisitionNoC(),
                                        itemObj.getStoreC().trim(),
                                        itemObj.getGroupCodeC().trim(),
                                        itemObj.getStoreCardNoC().trim(),
                                        itemObj.getItemC().trim(),
                                        itemObj.getItemDesC().trim(),
                                        itemObj.getUnitC().trim(),
                                        itemObj.getRequiredQtyC().trim(),
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
                        logger.error("Exception Occured In:insertRcivControlData");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertRcivControlData: " + e.getMessage());
        }
    }

    public List<RcivControlDTO> getRcivControlRecord() throws AppException {
        List<RcivControlDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM rciv_control_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivControlDTO dTO = new RcivControlDTO();
                    dTO.setRcicControlID((Long) (retMap.get("rcicControlID")));     
                    dTO.setPoNumberC((Long) (retMap.get("poNumberC")));
                    dTO.setAuthorisedStatusC((String) (retMap.get("authorisedStatusC")));
                    dTO.setStoreRequisitionC((String) (retMap.get("storeRequisitionC")));
                    dTO.setPlantC((String) (retMap.get("plantC")));  
                    dTO.setDateC((String) (retMap.get("dateC")));
                    dTO.setIssueTypeC((String) (retMap.get("issueTypeC")));
                    dTO.setIssueToC((String) (retMap.get("issueToC")));
                    dTO.setDisposalC((String) (retMap.get("disposalC")));
                    dTO.setIssueFrOtherSecNaC((String) (retMap.get("issueFrOtherSecNaC")));
                    dTO.setRequisitionNumberC((String) (retMap.get("requisitionNumberC")));
                    dTO.setRequisitionDateC((String) (retMap.get("requisitionDateC")));
                    dTO.setControlNumberC((Long) (retMap.get("controlNumberC")));
                    dTO.setControlDateC((String) (retMap.get("controlDateC")));                    
                    dTO.setIndentorNameC((String) (retMap.get("indentorNameC")));
                    dTO.setSectionC((String) (retMap.get("sectionC")));
                    dTO.setJobAllocationC((String) (retMap.get("jobAllocationC")));
                    dTO.setDeliveryAtC((String) (retMap.get("deliveryAtC")));
                    dTO.setItemCoveredC((String) (retMap.get("itemCoveredC")));
                    dTO.setAuthorisedByNoC((String) (retMap.get("authorisedByNoC")));                    
                    dTO.setAuthorisedByDateC((String) (retMap.get("authorisedByDateC")));
                    dTO.setAuthorisedRemarksC((String) (retMap.get("authorisedRemarksC")));
                    dTO.setReleasedByNoC((String) (retMap.get("releasedByNoC")));                    
                    dTO.setReleasedByDateC((String) (retMap.get("releasedByDateC")));                    
                    dTO.setReleasedRemarksC((String) (retMap.get("releasedRemarksC")));
                    
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivControlRecord:", e);
        }
        return list;
    }
    
    public List<RcivControlDTO> getRcivControlReById(final long id) throws AppException {
        List<RcivControlDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM rciv_control_details WHERE rcicControlID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivControlDTO dTO = new RcivControlDTO();
                    dTO.setRcicControlID((Long) (retMap.get("rcicControlID")));      
                    dTO.setPoNumberC((Long) (retMap.get("poNumberC")));
                    dTO.setAuthorisedStatusC((String) (retMap.get("authorisedStatusC")));
                    dTO.setStoreRequisitionC((String) (retMap.get("storeRequisitionC")));
                    dTO.setPlantC((String) (retMap.get("plantC")));  
                    dTO.setDateC((String) (retMap.get("dateC")));
                    dTO.setIssueTypeC((String) (retMap.get("issueTypeC")));
                    dTO.setIssueToC((String) (retMap.get("issueToC")));
                    dTO.setDisposalC((String) (retMap.get("disposalC")));
                    dTO.setIssueFrOtherSecNaC((String) (retMap.get("issueFrOtherSecNaC")));
                    dTO.setRequisitionNumberC((String) (retMap.get("requisitionNumberC")));
                    dTO.setRequisitionDateC((String) (retMap.get("requisitionDateC")));
                    dTO.setControlNumberC((Long) (retMap.get("controlNumberC")));
                    dTO.setControlDateC((String) (retMap.get("controlDateC")));                    
                    dTO.setIndentorNameC((String) (retMap.get("indentorNameC")));
                    dTO.setSectionC((String) (retMap.get("sectionC")));
                    dTO.setJobAllocationC((String) (retMap.get("jobAllocationC")));
                    dTO.setDeliveryAtC((String) (retMap.get("deliveryAtC")));
                    dTO.setItemCoveredC((String) (retMap.get("itemCoveredC")));
                    dTO.setAuthorisedByNoC((String) (retMap.get("authorisedByNoC")));                    
                    dTO.setAuthorisedByDateC((String) (retMap.get("authorisedByDateC")));
                    dTO.setAuthorisedRemarksC((String) (retMap.get("authorisedRemarksC")));
                    dTO.setReleasedByNoC((String) (retMap.get("releasedByNoC")));                    
                    dTO.setReleasedByDateC((String) (retMap.get("releasedByDateC")));                    
                    dTO.setReleasedRemarksC((String) (retMap.get("releasedRemarksC")));
                    
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivControlReById:", e);
        }
        return list;
    }
    
    public int updateRcivControlDetail(final List<RcivControlDTO> rcivCtlObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE rciv_control_details  ");
            sb.append(" SET poNumberC=?,authorisedStatusC=?,storeRequisitionC=?,plantC=?,dateC=?,issueTypeC=?,issueToC=?,disposalC=?,");  
            sb.append("issueFrOtherSecNaC=?,requisitionNumberC=?,requisitionDateC=?,controlNumberC=?,controlDateC=?,indentorNameC=?,sectionC=?,");
            sb.append("jobAllocationC=?,deliveryAtC=?,itemCoveredC=?,authorisedByNoC=?,authorisedByDateC=?,authorisedRemarksC=?,releasedByNoC=?,");            
            sb.append("releasedByDateC=?,releasedRemarksC=?,session_user_id=?,rciv_C_date=?  ");
            sb.append(" WHERE rcicControlID=? ");
            
            for(RcivControlDTO conObj : rcivCtlObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{      
                        conObj.getPoNumberC(),
                        conObj.getAuthorisedStatusC().trim(),
                        conObj.getStoreRequisitionC().trim(),
                        conObj.getPlantC().trim(),
                        conObj.getDateC().trim(),
                        conObj.getIssueTypeC().trim(),
                        conObj.getIssueToC().trim(),
                        conObj.getDisposalC().trim(),
                        conObj.getIssueFrOtherSecNaC().trim(),
                        conObj.getRequisitionNumberC().trim(),
                        conObj.getRequisitionDateC().trim(),
                        conObj.getControlNumberC(),
                        conObj.getControlDateC().trim(),                        
                        conObj.getIndentorNameC().trim(),
                        conObj.getSectionC().trim(),
                        conObj.getJobAllocationC().trim(),
                        conObj.getDeliveryAtC().trim(),
                        conObj.getItemCoveredC().trim(),
                        conObj.getAuthorisedByNoC().trim(),                        
                        conObj.getAuthorisedByDateC().trim(),
                        conObj.getAuthorisedRemarksC().trim(),
                        conObj.getReleasedByNoC().trim(),                          
                        conObj.getReleasedByDateC().trim(), 
                        conObj.getReleasedRemarksC().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        conObj.getRcicControlID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateRcivControlDetail:",e);
        }
        return retVal;
    }
    
    public long getControlForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM rciv_control_details WHERE controlNumberC=(SELECT MAX(controlNumberC) FROM rciv_control_details) ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   RcivControlDTO icdto = new RcivControlDTO();
                    icdto.setControlNumberC((long)(resultMap.get("controlNumberC")));
                    maxNo = (long)(resultMap.get("controlNumberC")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getControlForIncrement:",e);
        }
        return maxNo;
    }
    
    
    public List<RcivControlDTO> getRcivControlReByControlNo(final long conNo) throws AppException {
        List<RcivControlDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM rciv_control_details WHERE controlNumberC=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{conNo});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivControlDTO dTO = new RcivControlDTO();
                    dTO.setRcicControlID((Long) (retMap.get("rcicControlID")));      
                    dTO.setPoNumberC((Long) (retMap.get("poNumberC")));
                    dTO.setAuthorisedStatusC((String) (retMap.get("authorisedStatusC")));
                    dTO.setStoreRequisitionC((String) (retMap.get("storeRequisitionC")));
                    dTO.setPlantC((String) (retMap.get("plantC")));  
                    dTO.setDateC((String) (retMap.get("dateC")));
                    dTO.setIssueTypeC((String) (retMap.get("issueTypeC")));
                    dTO.setIssueToC((String) (retMap.get("issueToC")));
                    dTO.setDisposalC((String) (retMap.get("disposalC")));
                    dTO.setIssueFrOtherSecNaC((String) (retMap.get("issueFrOtherSecNaC")));
                    dTO.setRequisitionNumberC((String) (retMap.get("requisitionNumberC")));
                    dTO.setRequisitionDateC((String) (retMap.get("requisitionDateC")));
                    dTO.setControlNumberC((Long) (retMap.get("controlNumberC")));
                    dTO.setControlDateC((String) (retMap.get("controlDateC")));                    
                    dTO.setIndentorNameC((String) (retMap.get("indentorNameC")));
                    dTO.setSectionC((String) (retMap.get("sectionC")));
                    dTO.setJobAllocationC((String) (retMap.get("jobAllocationC")));
                    dTO.setDeliveryAtC((String) (retMap.get("deliveryAtC")));
                    dTO.setItemCoveredC((String) (retMap.get("itemCoveredC")));
                    dTO.setAuthorisedByNoC((String) (retMap.get("authorisedByNoC")));                    
                    dTO.setAuthorisedByDateC((String) (retMap.get("authorisedByDateC")));
                    dTO.setAuthorisedRemarksC((String) (retMap.get("authorisedRemarksC")));
                    dTO.setReleasedByNoC((String) (retMap.get("releasedByNoC")));                    
                    dTO.setReleasedByDateC((String) (retMap.get("releasedByDateC")));                    
                    dTO.setReleasedRemarksC((String) (retMap.get("releasedRemarksC")));
                    
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivControlReByControlNo:", e);
        }
        return list;
    }
    
    public List<RcivControlItemsDTO> getRcivControlItemsByPoNo(final long ConNumber) throws AppException {
        List<RcivControlItemsDTO> list = new LinkedList<>();
        try {
            
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.rcivContrItemId,a.conNumberC,a.requisitionNoC,a.storeC,a.groupCodeC, ");
            sb.append("a.storeCardNoC,a.itemC,a.itemDesC,a.unitC,a.requiredQtyC,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name,i.description ");
            sb.append(" FROM rciv_control_items a ");
            sb.append(" JOIN unit_master u ON a.unitC = u.unit_code");
            sb.append(" JOIN item_master i ON a.itemC = i.item_code");
            sb.append(" WHERE conNumberC =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{ConNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    RcivControlItemsDTO dto = new RcivControlItemsDTO();
                    dto.setRcivContrItemId((Long) (retMap.get("rcivContrItemId")));
                    dto.setConNumberC((Long) (retMap.get("conNumberC")));
                    dto.setRequisitionNoC((Long) (retMap.get("requisitionNoC")));
                    dto.setStoreC((String) (retMap.get("storeC")));
                    dto.setGroupCodeC((String) (retMap.get("groupCodeC")));  
                    dto.setStoreCardNoC((String) (retMap.get("storeCardNoC")));
                    dto.setItemC((String) (retMap.get("itemC")));
                    dto.setItemDesC((String) (retMap.get("itemDesC")));
                    dto.setUnitC((String) (retMap.get("unitC")));
                    dto.setRequiredQtyC((String) (retMap.get("requiredQtyC"))); 
                    dto.getUnitObj().setUnitName((String)(retMap.get("unit_name")));  
                    dto.getItemObj().setItemName((String)(retMap.get("item_name")));
                    dto.getItemObj().setDescription((String)(retMap.get("description")));
                    list.add(dto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRcivControlItemsByPoNo:", e);
        }
        return list;
    }

}
