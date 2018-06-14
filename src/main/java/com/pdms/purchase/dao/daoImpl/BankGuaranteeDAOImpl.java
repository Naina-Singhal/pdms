/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.dao.daoImpl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DescriptionDAOImpl;
import com.pdms.purchase.dto.BankGuaranteeDTO;
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
 * @author naagu
 */
@Repository
public class BankGuaranteeDAOImpl {
    private static final Logger logger = Logger.getLogger(BankGuaranteeDAOImpl.class);

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
    public BankGuaranteeDAOImpl(){
        
    }
    
    
    public int insertBankGuaranteeDetail(final List<BankGuaranteeDTO> guaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO bank_guarantee_details  ");
            sb.append("(bankGuaranteeId,Slno,tempone,poNumber,posr,modeBgDd,vendorName, "); 
            sb.append("bank,tempTwo,bgNumber,bgDate,amount,expireDate,gracePeriod, ");
            sb.append("retiredDate,entryDate,remarks,status,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(BankGuaranteeDTO GuaDTO : guaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{GuaDTO.getBankGuaranteeId(),
                        GuaDTO.getSlno(),
                        GuaDTO.getTempone().trim(),
                        GuaDTO.getPoNumber(),
                        GuaDTO.getPosr().trim(),
                        GuaDTO.getModeBgDd(),
                        GuaDTO.getVendorName(),
                        GuaDTO.getBank().trim(),
                        GuaDTO.getTempTwo(),
                        GuaDTO.getBgNumber().trim(),
                        GuaDTO.getBgDate().trim(),
                        GuaDTO.getAmount(),
                        GuaDTO.getExpireDate().trim(),
                        GuaDTO.getGracePeriod(),
                        GuaDTO.getRetiredDate(),
                        GuaDTO.getEntryDate().trim(),
                        GuaDTO.getRemarks(),
                        GuaDTO.getStatus().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertBankGuaranteeDetail:",e);
        }
        return retVal;
    }
    
    public List<BankGuaranteeDTO> getBankGuaranteeDeByPo(final long poNum) throws AppException
    {
        List<BankGuaranteeDTO> list = new LinkedList<>();
        
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM bank_guarantee_details WHERE  poNumber=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{poNum});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {
                    BankGuaranteeDTO ibcObj = new BankGuaranteeDTO();
                    ibcObj.setBankGuaranteeId((Long) (retMap.get("bankGuaranteeId")));
                    ibcObj.setSlno((Long) (retMap.get("Slno")));
                    ibcObj.setTempone((String) (retMap.get("tempone")));
                    ibcObj.setPoNumber((Long) (retMap.get("poNumber")));
                    ibcObj.setPosr((String) (retMap.get("posr")));
                    ibcObj.setModeBgDd((String) (retMap.get("modeBgDd")));
                    ibcObj.setVendorName((String) (retMap.get("vendorName")));
                    ibcObj.setBank((String) (retMap.get("bank")));
                    ibcObj.setTempTwo((String) (retMap.get("tempTwo")));
                    ibcObj.setBgNumber((String) (retMap.get("bgNumber")));
                    ibcObj.setBgDate((String) (retMap.get("bgDate"))); 
                    ibcObj.setAmount((BigDecimal) (retMap.get("amount")));
                    ibcObj.setExpireDate((String) (retMap.get("expireDate")));
                    ibcObj.setGracePeriod((String) (retMap.get("gracePeriod")));
                    ibcObj.setRetiredDate((String) (retMap.get("retiredDate")));
                    ibcObj.setEntryDate((String) (retMap.get("entryDate")));
                    ibcObj.setRemarks((String) (retMap.get("remarks")));
                    ibcObj.setStatus((String) (retMap.get("status")));
                    list.add(ibcObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankGuaranteeDeByPo:",e);
        }
        return list;
    }
    
    public List<BankGuaranteeDTO> getBankGuaranteeRecord() throws AppException {
        List<BankGuaranteeDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM bank_guarantee_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BankGuaranteeDTO ibcObj = new BankGuaranteeDTO();
                    ibcObj.setBankGuaranteeId((Long) (retMap.get("bankGuaranteeId")));
                    ibcObj.setSlno((Long) (retMap.get("Slno")));
                    ibcObj.setTempone((String) (retMap.get("tempone")));
                    ibcObj.setPoNumber((Long) (retMap.get("poNumber")));
                    ibcObj.setPosr((String) (retMap.get("posr")));
                    ibcObj.setModeBgDd((String) (retMap.get("modeBgDd")));
                    ibcObj.setVendorName((String) (retMap.get("vendorName")));
                    ibcObj.setBank((String) (retMap.get("bank")));
                    ibcObj.setTempTwo((String) (retMap.get("tempTwo")));
                    ibcObj.setBgNumber((String) (retMap.get("bgNumber")));
                    ibcObj.setBgDate((String) (retMap.get("bgDate"))); 
                    ibcObj.setAmount((BigDecimal) (retMap.get("amount")));
                    ibcObj.setExpireDate((String) (retMap.get("expireDate")));
                    ibcObj.setGracePeriod((String) (retMap.get("gracePeriod")));
                    ibcObj.setRetiredDate((String) (retMap.get("retiredDate")));
                    ibcObj.setEntryDate((String) (retMap.get("entryDate")));
                    ibcObj.setRemarks((String) (retMap.get("remarks")));
                    ibcObj.setStatus((String) (retMap.get("status")));
                    list.add(ibcObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankGuaranteeRecord:", e);
        }
        return list;
    }
    
    public List<BankGuaranteeDTO> getBankGuaranteeReById(final long id) throws AppException {
        List<BankGuaranteeDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM bank_guarantee_details WHERE bankGuaranteeId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BankGuaranteeDTO ibcObj = new BankGuaranteeDTO();
                    ibcObj.setBankGuaranteeId((Long) (retMap.get("bankGuaranteeId")));
                    ibcObj.setSlno((Long) (retMap.get("Slno")));
                    ibcObj.setTempone((String) (retMap.get("tempone")));
                    ibcObj.setPoNumber((Long) (retMap.get("poNumber")));
                    ibcObj.setPosr((String) (retMap.get("posr")));
                    ibcObj.setModeBgDd((String) (retMap.get("modeBgDd")));
                    ibcObj.setVendorName((String) (retMap.get("vendorName")));
                    ibcObj.setBank((String) (retMap.get("bank")));
                    ibcObj.setTempTwo((String) (retMap.get("tempTwo")));
                    ibcObj.setBgNumber((String) (retMap.get("bgNumber")));
                    ibcObj.setBgDate((String) (retMap.get("bgDate"))); 
                    ibcObj.setAmount((BigDecimal) (retMap.get("amount")));
                    ibcObj.setExpireDate((String) (retMap.get("expireDate")));
                    ibcObj.setGracePeriod((String) (retMap.get("gracePeriod")));
                    ibcObj.setRetiredDate((String) (retMap.get("retiredDate")));
                    ibcObj.setEntryDate((String) (retMap.get("entryDate")));
                    ibcObj.setRemarks((String) (retMap.get("remarks")));
                    ibcObj.setStatus((String) (retMap.get("status")));
                    List.add(ibcObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankGuaranteeReById:", e);
        }
        return List;
    }
    
    public int updateBankGuaranteeDetail(final List<BankGuaranteeDTO> guaObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE bank_guarantee_details  ");
            sb.append(" SET Slno=?,tempone=?,poNumber=?,posr=?,modeBgDd=?,vendorName=?,bank=?,tempTwo=?,");  
            sb.append("bgNumber=?,bgDate=?,amount=?,expireDate=?,gracePeriod=?,retiredDate=?,entryDate=?,remarks=?, ");
            sb.append("status=?,session_id=?,cur_date=?  WHERE bankGuaranteeId=? ");
            
            for(BankGuaranteeDTO GuaDTO : guaObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        GuaDTO.getSlno(),
                        GuaDTO.getTempone().trim(),
                        GuaDTO.getPoNumber(),
                        GuaDTO.getPosr().trim(),
                        GuaDTO.getModeBgDd(),
                        GuaDTO.getVendorName(),
                        GuaDTO.getBank().trim(),
                        GuaDTO.getTempTwo(),
                        GuaDTO.getBgNumber().trim(),
                        GuaDTO.getBgDate().trim(),
                        GuaDTO.getAmount(),
                        GuaDTO.getExpireDate().trim(),
                        GuaDTO.getGracePeriod(),
                        GuaDTO.getRetiredDate(),
                        GuaDTO.getEntryDate().trim(),
                        GuaDTO.getRemarks(),
                        GuaDTO.getStatus().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        GuaDTO.getBankGuaranteeId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateBankGuaranteeDetail:",e);
        }
        return retVal;
    }
    
     public long geneSlNoIncreFrBankGua() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM bank_guarantee_details WHERE Slno=(SELECT MAX(Slno) FROM bank_guarantee_details) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   BankGuaranteeDTO vdto = new BankGuaranteeDTO();                    
                    vdto.setSlno((long)(resultMap.get("Slno")));
                    maxNo = (long)(resultMap.get("Slno")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : geneSlNoIncreFrBankGua:",e);
        }
        return maxNo;
    }
     
    public List<BankGuaranteeDTO> getBankGuaranteeReByIdPdf(final long id) throws AppException {
        List<BankGuaranteeDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("SELECT a.Slno,a.tempone,a.poNumber,a.posr,a.modeBgDd,a.vendorName,a.bank,a.tempTwo,a.bgNumber,a.bgDate, ");
            sb.append(" a.amount,a.expireDate,a.gracePeriod,a.retiredDate,a.entryDate,a.remarks,a.status, ");
            sb.append(" v.vendor_name,v.vendor_address,v.vendor_city,v.vendor_pin, ");
            sb.append(" b.bankName,b.branch,b.ifscCode,b.city ");
            sb.append(" FROM bank_guarantee_details a ");
            sb.append(" JOIN vendor_details v ON a.vendorName = v.vendor_code ");
            sb.append(" JOIN bank_details b ON a.bank = b.bankCode ");
            sb.append(" WHERE a.bankGuaranteeId=? ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BankGuaranteeDTO ibcObj = new BankGuaranteeDTO();                    
                    ibcObj.setSlno((Long) (retMap.get("Slno")));
                    ibcObj.setTempone((String) (retMap.get("tempone")));
                    ibcObj.setPoNumber((Long) (retMap.get("poNumber")));
                    ibcObj.setPosr((String) (retMap.get("posr")));
                    ibcObj.setModeBgDd((String) (retMap.get("modeBgDd")));
                    ibcObj.setVendorName((String) (retMap.get("vendorName")));
                    ibcObj.setBank((String) (retMap.get("bank")));
                    ibcObj.setTempTwo((String) (retMap.get("tempTwo")));
                    ibcObj.setBgNumber((String) (retMap.get("bgNumber")));
                    ibcObj.setBgDate((String) (retMap.get("bgDate"))); 
                    ibcObj.setAmount((BigDecimal) (retMap.get("amount")));
                    ibcObj.setExpireDate((String) (retMap.get("expireDate")));
                    ibcObj.setGracePeriod((String) (retMap.get("gracePeriod")));
                    ibcObj.setRetiredDate((String) (retMap.get("retiredDate")));
                    ibcObj.setEntryDate((String) (retMap.get("entryDate")));
                    ibcObj.setRemarks((String) (retMap.get("remarks")));
                    ibcObj.setStatus((String) (retMap.get("status")));
                    ibcObj.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    ibcObj.getVenObj().setVendorAddress((String) (retMap.get("vendor_address")));
                    ibcObj.getVenObj().setVendorCity((String) (retMap.get("vendor_city")));
                    ibcObj.getVenObj().setVendorPin((String) (retMap.get("vendor_pin")));
                    ibcObj.getBankObj().setBankName((String) (retMap.get("bankName")));
                    ibcObj.getBankObj().setBranch((String) (retMap.get("branch")));
                    ibcObj.getBankObj().setIfscCode((String) (retMap.get("ifscCode")));
                    ibcObj.getBankObj().setCity((String) (retMap.get("city")));
                    List.add(ibcObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBankGuaranteeReByIdPdf:", e);
        }
        return List;
    }
    
}
