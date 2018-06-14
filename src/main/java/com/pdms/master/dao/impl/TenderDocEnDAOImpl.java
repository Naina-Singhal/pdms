/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.TenderDocMaDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
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

/**
 *
 * @author myassessment
 */
@Repository
public class TenderDocEnDAOImpl {
    
     private static final Logger logger = Logger.getLogger(TenderDocEnDAOImpl.class);

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
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public TenderDocEnDAOImpl(){
        
    }
    
    
    public int insertTenderDocDetail(final List<TenderDocMaDTO> tenObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO tender_document_details  ");
            sb.append("(tenderDocID,tenderNo,tenderDate,tenderAmt,noOfCopies,dateOfIssue, "); 
            sb.append("issueClose,dateOfOpen,fileNo,tenderreceFr,bDescription,session_id,ten_cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(TenderDocMaDTO TENDTO : tenObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{TENDTO.getTenderDocID(),
                        TENDTO.getTenderNo().trim(),
                        TENDTO.getTenderDate().trim(),
                        TENDTO.getTenderAmt(),
                        TENDTO.getNoOfCopies().trim(),
                        TENDTO.getDateOfIssue(),
                        TENDTO.getIssueClose().trim(),
                        TENDTO.getDateOfOpen().trim(),
                        TENDTO.getFileNo().trim(),
                        TENDTO.getTenderreceFr().trim(),
                        TENDTO.getbDescription().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertTenderDocDetail:",e);
        }
        return retVal;
    }
    
    public List<TenderDocMaDTO> getTenderDocEnDetails() throws AppException {
        List<TenderDocMaDTO> TenDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM tender_document_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TenderDocMaDTO tendto = new TenderDocMaDTO();
                    tendto.setTenderDocID((Long) (retMap.get("tenderDocID")));
                    tendto.setTenderNo((String) (retMap.get("tenderNo")));
                    tendto.setTenderDate((String) (retMap.get("tenderDate")));
                    tendto.setTenderAmt((BigDecimal) (retMap.get("tenderAmt")));
                    tendto.setNoOfCopies((String) (retMap.get("noOfCopies")));
                    tendto.setDateOfIssue((String) (retMap.get("dateOfIssue")));
                    tendto.setIssueClose((String) (retMap.get("issueClose")));
                    tendto.setDateOfOpen((String) (retMap.get("dateOfOpen")));
                    tendto.setFileNo((String) (retMap.get("fileNo")));
                    tendto.setTenderreceFr((String) (retMap.get("tenderreceFr")));
                    tendto.setbDescription((String) (retMap.get("bDescription")));
                    TenDTO.add(tendto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTenderDocEnDetails:", e);
        }
        return TenDTO;
    }
    
    public List<TenderDocMaDTO> getTenderDocReById(final long id) throws AppException {
        List<TenderDocMaDTO> TDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM tender_document_details WHERE tenderDocID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TenderDocMaDTO TenDto = new TenderDocMaDTO();
                    TenDto.setTenderDocID((Long) (retMap.get("tenderDocID")));
                    TenDto.setTenderNo((String) (retMap.get("tenderNo")));
                    TenDto.setTenderDate((String) (retMap.get("tenderDate")));
                    TenDto.setTenderAmt((BigDecimal) (retMap.get("tenderAmt")));
                    TenDto.setNoOfCopies((String) (retMap.get("noOfCopies")));
                    TenDto.setDateOfIssue((String) (retMap.get("dateOfIssue")));
                    TenDto.setIssueClose((String) (retMap.get("issueClose")));
                    TenDto.setDateOfOpen((String) (retMap.get("dateOfOpen")));
                    TenDto.setFileNo((String) (retMap.get("fileNo")));
                    TenDto.setTenderreceFr((String) (retMap.get("tenderreceFr")));
                    TenDto.setbDescription((String) (retMap.get("bDescription")));       
                    TDTO.add(TenDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTenderDocReById:", e);
        }
        return TDTO;
    }
    
    public long checkDuplicateTenderDoc(final String tenderNo) throws AppException {
        long dpCnt = 0;
        try {           
                String qry = "SELECT COUNT(1) FROM tender_document_details ";
                qry+=" WHERE LOWER(tenderNo) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{tenderNo.trim().toLowerCase()},
                        Long.class);      

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateTenderDoc:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public TenderDocMaDTO getSelectedTenderDoc(final long tenDocID) throws AppException
    {
        TenderDocMaDTO tenObj = new TenderDocMaDTO();
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM tender_document_details WHERE  tenderDocID=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{tenDocID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {                    
                    tenObj.setTenderDocID((Long) (retMap.get("tenderDocID")));
                    tenObj.setTenderNo((String) (retMap.get("tenderNo")));
                    tenObj.setTenderDate((String) (retMap.get("tenderDate")));
                    tenObj.setTenderAmt((BigDecimal) (retMap.get("tenderAmt")));
                    tenObj.setNoOfCopies((String) (retMap.get("noOfCopies")));
                    tenObj.setDateOfIssue((String) (retMap.get("dateOfIssue")));
                    tenObj.setIssueClose((String) (retMap.get("issueClose")));
                    tenObj.setDateOfOpen((String) (retMap.get("dateOfOpen")));
                    tenObj.setFileNo((String) (retMap.get("fileNo")));
                    tenObj.setTenderreceFr((String) (retMap.get("tenderreceFr")));
                    tenObj.setbDescription((String) (retMap.get("bDescription")));                
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedTenderDoc:",e);
        }
        return tenObj;
    }
    
    public int updateTenderDocDetail(final List<TenderDocMaDTO> tenObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE tender_document_details  ");
            sb.append(" SET tenderNo=?,tenderDate=?,tenderAmt=?,noOfCopies=?,dateOfIssue=?,issueClose=?,dateOfOpen=?, ");            
            sb.append(" fileNo=?,tenderreceFr=?,bDescription=?,session_id=?,ten_cur_date=?  WHERE tenderDocID=? ");
            
            for(TenderDocMaDTO SDTO : tenObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SDTO.getTenderNo().trim(),
                        SDTO.getTenderDate().trim(),
                        SDTO.getTenderAmt(),
                        SDTO.getNoOfCopies().trim(),
                        SDTO.getDateOfIssue().trim(),
                        SDTO.getIssueClose().trim(),
                        SDTO.getDateOfOpen().trim(),
                        SDTO.getFileNo().trim(),
                        SDTO.getTenderreceFr().trim(),
                        SDTO.getbDescription().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        SDTO.getTenderDocID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateTenderDocDetail:",e);
        }
        return retVal;
    }
    
    public List<TenderDocMaDTO> getTenderDomentsByFile(final long fileNo) throws AppException
    {
        List<TenderDocMaDTO> List = new LinkedList<>();
        
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM tender_document_details WHERE  fileNo=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{fileNo});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {       
                    TenderDocMaDTO tenObj = new TenderDocMaDTO();
                    tenObj.setTenderDocID((Long) (retMap.get("tenderDocID")));
                    tenObj.setTenderNo((String) (retMap.get("tenderNo")));
                    tenObj.setTenderDate((String) (retMap.get("tenderDate")));
                    tenObj.setTenderAmt((BigDecimal) (retMap.get("tenderAmt")));
                    tenObj.setNoOfCopies((String) (retMap.get("noOfCopies")));
                    tenObj.setDateOfIssue((String) (retMap.get("dateOfIssue")));
                    tenObj.setIssueClose((String) (retMap.get("issueClose")));
                    tenObj.setDateOfOpen((String) (retMap.get("dateOfOpen")));
                    tenObj.setFileNo((String) (retMap.get("fileNo")));
                    tenObj.setTenderreceFr((String) (retMap.get("tenderreceFr")));
                    tenObj.setbDescription((String) (retMap.get("bDescription")));  
                    List.add(tenObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTenderDomentsByFile:",e);
        }
        return List;
    }
}
