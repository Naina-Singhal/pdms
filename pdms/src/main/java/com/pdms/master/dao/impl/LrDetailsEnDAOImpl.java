/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.LrEntryDTO;
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
public class LrDetailsEnDAOImpl {
    
     private static final Logger logger = Logger.getLogger(LrDetailsEnDAOImpl.class);

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
    public LrDetailsEnDAOImpl(){
        
    }
    
    
    public int insertLRNoDetail(final List<LrEntryDTO> lrObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO lr_Number_details  ");
            sb.append("(lrEntryID,poNumber,nameOfSupplr,billNumber,billDate,billAmount, "); 
            sb.append("ibcNumber,lrNumber,lrDate,nameTransporter,session_id,lr_cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(LrEntryDTO LRDTO : lrObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{LRDTO.getLrEntryID(),
                        LRDTO.getPoNumber().trim(),
                        LRDTO.getNameOfSupplr().trim(),
                        LRDTO.getBillNumber().trim(),
                        LRDTO.getBillDate().trim(),
                        LRDTO.getBillAmount(),
                        LRDTO.getIbcNumber().trim(),
                        LRDTO.getLrNumber().trim(),
                        LRDTO.getLrDate(),
                        LRDTO.getNameTransporter().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertLRNoDetail:",e);
        }
        return retVal;
    }
    
    public List<LrEntryDTO> getLrEntryDetails() throws AppException {
        List<LrEntryDTO> LrDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM lr_Number_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEntryDTO ledto = new LrEntryDTO();
                    ledto.setLrEntryID((Long) (retMap.get("lrEntryID")));
                    ledto.setPoNumber((String) (retMap.get("poNumber")));
                    ledto.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    ledto.setBillNumber((String) (retMap.get("billNumber")));
                    ledto.setBillDate((String) (retMap.get("billDate")));
                    ledto.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    ledto.setIbcNumber((String) (retMap.get("ibcNumber")));
                    ledto.setLrNumber((String) (retMap.get("lrNumber")));
                    ledto.setLrDate((String) (retMap.get("lrDate")));
                    ledto.setNameTransporter((String) (retMap.get("nameTransporter")));
                    LrDTO.add(ledto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrEntryDetails:", e);
        }
        return LrDTO;
    }
    
    public long checkDuplicateLrDetails(final String lrNumber, final String suplier) throws AppException {
        long dpCnt = 0;
        try {            
            if(lrNumber.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM lr_number_details ";
                qry+=" WHERE LOWER(nameOfSupplr) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{suplier.trim().toLowerCase()},
                        Long.class);//                
            }
            else if(suplier.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM lr_number_details ";
                qry+=" WHERE LOWER(lrNumber) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{lrNumber.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM lr_number_details ";
                qry+=" WHERE LOWER(lrNumber)=? AND LOWER(nameOfSupplr) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{lrNumber.trim().toLowerCase(),suplier.toLowerCase()},
                        Long.class);//                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateLrDetails:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public List<LrEntryDTO> getLrDetailsReById(final long id) throws AppException {
        List<LrEntryDTO> LRDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM lr_number_details WHERE lrEntryID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEntryDTO lrDto = new LrEntryDTO();
                    lrDto.setLrEntryID((Long) (retMap.get("lrEntryID")));
                    lrDto.setPoNumber((String) (retMap.get("poNumber")));
                    lrDto.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    lrDto.setBillNumber((String) (retMap.get("billNumber")));
                    lrDto.setBillDate((String) (retMap.get("billDate")));
                    lrDto.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    lrDto.setIbcNumber((String) (retMap.get("ibcNumber")));
                    lrDto.setLrNumber((String) (retMap.get("lrNumber")));
                    lrDto.setLrDate((String) (retMap.get("lrDate")));
                    lrDto.setNameTransporter((String) (retMap.get("nameTransporter")));                 
                    LRDTO.add(lrDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrDetailsReById:", e);
        }
        return LRDTO;
    }
    
    public LrEntryDTO getSelectedLrDetails(final long LrEntryID) throws AppException
    {
        LrEntryDTO LrObj = new LrEntryDTO();
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM lr_number_details WHERE  lrEntryID=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{LrEntryID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {
                    LrObj.setLrEntryID((Long) (retMap.get("lrEntryID")));
                    LrObj.setPoNumber((String) (retMap.get("poNumber")));
                    LrObj.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    LrObj.setBillNumber((String) (retMap.get("billNumber")));
                    LrObj.setBillDate((String) (retMap.get("billDate")));
                    LrObj.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    LrObj.setIbcNumber((String) (retMap.get("ibcNumber")));
                    LrObj.setLrNumber((String) (retMap.get("lrNumber")));
                    LrObj.setLrDate((String) (retMap.get("lrDate")));
                    LrObj.setNameTransporter((String) (retMap.get("nameTransporter")));                    
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedLrDetails:",e);
        }
        return LrObj;
    }
    
    public int updateLeDetailsDetail(final List<LrEntryDTO> LrObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE lr_number_details  ");
            sb.append(" SET poNumber=?,nameOfSupplr=?,billNumber=?,billDate=?,billAmount=?,ibcNumber=?, ");            
            sb.append(" lrNumber=?,lrDate=?,nameTransporter=?,session_id=?,lr_cur_date=? WHERE lrEntryID=? ");
            
            for(LrEntryDTO LDTO : LrObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{LDTO.getPoNumber().trim(),
                        LDTO.getNameOfSupplr().trim(),
                        LDTO.getBillNumber().trim(),
                        LDTO.getBillDate().trim(),
                        LDTO.getBillAmount(),
                        LDTO.getIbcNumber().trim(),
                        LDTO.getLrNumber().trim(),
                        LDTO.getLrDate().trim(),
                        LDTO.getNameTransporter().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        LDTO.getLrEntryID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateLeDetailsDetail:",e);
        }
        return retVal;
    }
    
    public List<LrEntryDTO> getLrDetailsReByPONO(final long PoNo) throws AppException {
        List<LrEntryDTO> lrList = new LinkedList<>();
        try {
            StringBuilder sbb = new StringBuilder();
            sbb.append(" SELECT * FROM lr_number_details WHERE poNumber =? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sbb.toString(),
                    new Object[]{PoNo});
            
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEntryDTO lrDto = new LrEntryDTO();
                    lrDto.setLrEntryID((Long) (retMap.get("lrEntryID")));
                    lrDto.setPoNumber((String) (retMap.get("poNumber")));
                    lrDto.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    lrDto.setBillNumber((String) (retMap.get("billNumber")));
                    lrDto.setBillDate((String) (retMap.get("billDate")));
                    lrDto.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    lrDto.setIbcNumber((String) (retMap.get("ibcNumber")));
                    lrDto.setLrNumber((String) (retMap.get("lrNumber")));
                    lrDto.setLrDate((String) (retMap.get("lrDate")));
                    lrDto.setNameTransporter((String) (retMap.get("nameTransporter")));                 
                    lrList.add(lrDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrDetailsReByPONO:", e);
        }
        return lrList;
    }
    
    public List<LrEntryDTO> getLrDeByPoFrPdf(final long lrid) throws AppException {
        List<LrEntryDTO> lrList = new LinkedList<>();
        try {
            StringBuilder sbb = new StringBuilder();
            sbb.append(" SELECT  a.poNumber,a.nameOfSupplr,a.billNumber,a.billDate,a.billAmount,a.lrNumber, ");
            sbb.append(" a.lrDate,a.nameTransporter,v.vendor_name ");
            sbb.append(" ");
            sbb.append(" FROM lr_number_details a ");
            sbb.append(" JOIN vendor_details v ON a.nameOfSupplr = v.vendor_code ");
            sbb.append(" ");
            sbb.append(" WHERE a.lrEntryID=? ");
            
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sbb.toString(),
                    new Object[]{lrid});
            
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LrEntryDTO lrDto = new LrEntryDTO();
                    
                    lrDto.setPoNumber((String) (retMap.get("poNumber")));
                    lrDto.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    lrDto.setBillNumber((String) (retMap.get("billNumber")));
                    lrDto.setBillDate((String) (retMap.get("billDate")));
                    lrDto.setBillAmount((BigDecimal) (retMap.get("billAmount")));                    
                    lrDto.setLrNumber((String) (retMap.get("lrNumber")));
                    lrDto.setLrDate((String) (retMap.get("lrDate")));
                    lrDto.setNameTransporter((String) (retMap.get("nameTransporter")));   
                    lrDto.getVenObj().setVendorName((String) (retMap.get("vendor_name"))); 
                    lrList.add(lrDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getLrDeByPoFrPdf:", e);
        }
        return lrList;
    }
}
