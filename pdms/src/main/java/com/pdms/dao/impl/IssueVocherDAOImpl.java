/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.dto.IssueVoucherDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.RcivControlDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.IssueVouItemsDTO;
import com.pdms.itemsDto.receipt.IssueVoucherTempDTO;
import com.pdms.itemsDto.receipt.RcivControlItemsDTO;
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
public class IssueVocherDAOImpl {
    
    
    private static final Logger logger = Logger.getLogger(IssueVocherDAOImpl.class);

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
    public IssueVocherDAOImpl(){
        
    }
    
    public int insertIssueVoucher(final IssueVoucherTempDTO issueVoucher, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO issue_voucher_details  ");
                        sb.append("(id,ivControlNo,ivControlDate,rcivNumber,rcivDate,issueType,section,");
                        sb.append("disposal,deliveryAt,itemCovered,joAllocation,storeGroupCode,cardNumber,cardCode,");
                        sb.append("upToDateBal,describe_iv,partNumber,minLevel,mlReachedDat,date,qtyLaIssue,");
                        sb.append("basicRate,rateDate,waRate,rate,currencyCode,receivedBy,surplusQty,");
                        sb.append("lifoRate,indentorName,issuedByName,authorByName,");
                        sb.append("authorByDate,session_user_id,iv_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<IssueVoucherDTO> issueDto = issueVoucher.getIssueDTO();
                        for (IssueVoucherDTO iv : issueDto) {
                            
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{iv.getId(),
                                        iv.getIvControlNo().trim(),
                                        iv.getIvControlDate().trim(),
                                        iv.getRcivNumber().trim(),
                                        iv.getRcivDate().trim(),
                                        iv.getIssueType().trim(),
                                        iv.getSection().trim(),
                                        iv.getDisposal().trim(),
                                        iv.getDeliveryAt().trim(),
                                        iv.getItemCovered().trim(),
                                        iv.getJoAllocation().trim(),
                                        iv.getStoreGroupCode().trim(),
                                        iv.getCardNumber().trim(),
                                        iv.getCardCode().trim(),
                                        iv.getUpToDateBal().trim(),
                                        iv.getDescribe().trim(),
                                        iv.getPartNumber().trim(),
                                        iv.getMinLevel().trim(),
                                        iv.getMlReachedDat().trim(),
                                        iv.getDate().trim(),
                                        iv.getQtyLaIssue().trim(),
                                        iv.getBasicRate().trim(),
                                        iv.getRateDate().trim(),
                                        iv.getWaRate().trim(),
                                        iv.getRate().trim(),
                                        iv.getCurrencyCode().trim(),
                                        iv.getReceivedBy().trim(),
                                        iv.getSurplusQty().trim(),
                                        iv.getLifoRate().trim(),
                                        iv.getIndentorName().trim(),
                                        iv.getIssuedByName().trim(),
                                        iv.getAuthorByName().trim(),
                                        iv.getAuthorByDate().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO issue_voucher_items  ");
                        sbItem.append("(issueVouItemId,ConNumber,groupp,cardNo,item,unit,qtyRqud,available,");
                        sbItem.append("qtyIssue,remarks,upd,qtyBalance,qtyShort,waRate,lifoRate,userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<IssueVouItemsDTO> items = issueVoucher.getIssueItemsDTO();
                        for (IssueVouItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getIssueVouItemId(),
                                        itemObj.getConNumber(),
                                        itemObj.getGroup(),
                                        itemObj.getCardNo().trim(),
                                        itemObj.getItem().trim(),
                                        itemObj.getUnit().trim(),
                                        itemObj.getQtyRqud().trim(),
                                        itemObj.getAvailable().trim(),
                                        itemObj.getQtyIssue().trim(),
                                        itemObj.getRemarks().trim(),
                                        itemObj.getUpd().trim(),
                                        itemObj.getQtyBalance().trim(),
                                        itemObj.getQtyShort().trim(),
                                        itemObj.getWaRate().trim(),
                                        itemObj.getLifoRate().trim(),
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
                        logger.error("Exception Occured In:insertIssueVoucher");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertIssueVoucher: " + e.getMessage());
        }
    }
    
    public List<IssueVoucherDTO> getIssueVoucherRecord() throws AppException {
        List<IssueVoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM issue_voucher_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IssueVoucherDTO dTO = new IssueVoucherDTO();
                    dTO.setId((Long) (retMap.get("id")));
                    dTO.setIvControlNo((String) (retMap.get("ivControlNo")));
                    dTO.setIvControlDate((String) (retMap.get("ivControlDate")));
                    dTO.setRcivNumber((String) (retMap.get("rcivNumber")));
                    dTO.setRcivDate((String) (retMap.get("rcivDate")));  
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setDisposal((String) (retMap.get("disposal")));
                    dTO.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setJoAllocation((String) (retMap.get("joAllocation")));
                    dTO.setStoreGroupCode((String) (retMap.get("storeGroupCode")));
                    dTO.setCardNumber((String) (retMap.get("cardNumber")));
                    dTO.setCardCode((String) (retMap.get("cardCode")));
                    dTO.setUpToDateBal((String) (retMap.get("upToDateBal")));  
                    dTO.setDescribe((String) (retMap.get("describe_iv")));
                    dTO.setPartNumber((String) (retMap.get("partNumber")));
                    dTO.setMinLevel((String) (retMap.get("minLevel")));
                    dTO.setMlReachedDat((String) (retMap.get("mlReachedDat")));
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setQtyLaIssue((String) (retMap.get("qtyLaIssue")));
                    dTO.setBasicRate((String) (retMap.get("basicRate")));
                    dTO.setRateDate((String) (retMap.get("rateDate")));
                    dTO.setWaRate((String) (retMap.get("waRate")));
                    dTO.setRate((String) (retMap.get("rate")));  
                    dTO.setCurrencyCode((String) (retMap.get("currencyCode")));
                    dTO.setReceivedBy((String) (retMap.get("receivedBy")));
                    dTO.setSurplusQty((String) (retMap.get("surplusQty")));
                    dTO.setLifoRate((String) (retMap.get("lifoRate")));                    
                    dTO.setIndentorName((String) (retMap.get("indentorName")));                                       
                    dTO.setIssuedByName((String) (retMap.get("issuedByName")));                    
                    dTO.setAuthorByName((String) (retMap.get("authorByName")));                    
                    dTO.setAuthorByDate((String) (retMap.get("authorByDate")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIssueVoucherRecord:", e);
        }
        return list;
    }
    
    public List<IssueVoucherDTO> getIssueVoucherReById(final long id) throws AppException {
        List<IssueVoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM issue_voucher_details WHERE id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IssueVoucherDTO dTO = new IssueVoucherDTO();
                    dTO.setId((Long) (retMap.get("id")));
                    dTO.setIvControlNo((String) (retMap.get("ivControlNo")));
                    dTO.setIvControlDate((String) (retMap.get("ivControlDate")));
                    dTO.setRcivNumber((String) (retMap.get("rcivNumber")));
                    dTO.setRcivDate((String) (retMap.get("rcivDate")));  
                    dTO.setIssueType((String) (retMap.get("issueType")));
                    dTO.setSection((String) (retMap.get("section")));
                    dTO.setDisposal((String) (retMap.get("disposal")));
                    dTO.setDeliveryAt((String) (retMap.get("deliveryAt")));
                    dTO.setItemCovered((String) (retMap.get("itemCovered")));
                    dTO.setJoAllocation((String) (retMap.get("joAllocation")));
                    dTO.setStoreGroupCode((String) (retMap.get("storeGroupCode")));
                    dTO.setCardNumber((String) (retMap.get("cardNumber")));
                    dTO.setCardCode((String) (retMap.get("cardCode")));
                    dTO.setUpToDateBal((String) (retMap.get("upToDateBal")));  
                    dTO.setDescribe((String) (retMap.get("describe_iv")));
                    dTO.setPartNumber((String) (retMap.get("partNumber")));
                    dTO.setMinLevel((String) (retMap.get("minLevel")));
                    dTO.setMlReachedDat((String) (retMap.get("mlReachedDat")));
                    dTO.setDate((String) (retMap.get("date")));
                    dTO.setQtyLaIssue((String) (retMap.get("qtyLaIssue")));
                    dTO.setBasicRate((String) (retMap.get("basicRate")));
                    dTO.setRateDate((String) (retMap.get("rateDate")));
                    dTO.setWaRate((String) (retMap.get("waRate")));
                    dTO.setRate((String) (retMap.get("rate")));  
                    dTO.setCurrencyCode((String) (retMap.get("currencyCode")));
                    dTO.setReceivedBy((String) (retMap.get("receivedBy")));
                    dTO.setSurplusQty((String) (retMap.get("surplusQty")));
                    dTO.setLifoRate((String) (retMap.get("lifoRate")));                    
                    dTO.setIndentorName((String) (retMap.get("indentorName")));                                        
                    dTO.setIssuedByName((String) (retMap.get("issuedByName")));                    
                    dTO.setAuthorByName((String) (retMap.get("authorByName")));                    
                    dTO.setAuthorByDate((String) (retMap.get("authorByDate")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIssueVoucherReById:", e);
        }
        return list;
    }
    
    public int updateIssueVoucherDetail(final List<IssueVoucherDTO> issueVoucher, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE issue_voucher_details  ");
            sb.append(" SET ivControlNo=?,ivControlDate=?,rcivNumber=?,rcivDate=?,issueType=?,section=?,disposal=?,deliveryAt=?,");  
            sb.append("itemCovered=?,joAllocation=?,storeGroupCode=?,cardNumber=?,cardCode=?,upToDateBal=?,describe_iv=?,partNumber=?, ");
            sb.append("minLevel=?,mlReachedDat=?,date=?,qtyLaIssue=?,basicRate=?,rateDate=?,waRate=?,rate=?, ");
            sb.append("currencyCode=?,receivedBy=?,surplusQty=?,lifoRate=?,indentorName=?,issuedByName=?, ");
            sb.append("authorByName=?,authorByDate=?,session_user_id=?,iv_date=?  ");
            sb.append(" WHERE id=? ");
            
            for(IssueVoucherDTO iv : issueVoucher) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        iv.getIvControlNo().trim(),
                        iv.getIvControlDate().trim(),
                        iv.getRcivNumber().trim(),
                        iv.getRcivDate().trim(),
                        iv.getIssueType().trim(),
                        iv.getSection().trim(),
                        iv.getDisposal().trim(),
                        iv.getDeliveryAt().trim(),
                        iv.getItemCovered().trim(),
                        iv.getJoAllocation().trim(),
                        iv.getStoreGroupCode().trim(),
                        iv.getCardNumber().trim(),
                        iv.getCardCode().trim(),
                        iv.getUpToDateBal().trim(),
                        iv.getDescribe().trim(),
                        iv.getPartNumber().trim(),
                        iv.getMinLevel().trim(),
                        iv.getMlReachedDat().trim(),
                        iv.getDate().trim(),
                        iv.getQtyLaIssue().trim(),
                        iv.getBasicRate().trim(),
                        iv.getRateDate().trim(),
                        iv.getWaRate().trim(),
                        iv.getRate().trim(),
                        iv.getCurrencyCode().trim(),
                        iv.getReceivedBy().trim(),
                        iv.getSurplusQty().trim(),
                        iv.getLifoRate().trim(),                        
                        iv.getIndentorName().trim(),                        
                        iv.getIssuedByName().trim(),                        
                        iv.getAuthorByName().trim(),
                        iv.getAuthorByDate().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        iv.getId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateIssueVoucherDetail:",e);
        }
        return retVal;
    }
    
}
