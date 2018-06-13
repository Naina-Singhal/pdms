/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.SignatoryDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
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
public class PaymentDAOImpl {
    
     private static final Logger logger = Logger.getLogger(PaymentDAOImpl.class);

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
    public PaymentDAOImpl(){
        
    }
    
    
    public int insertPaymentDetail(final List<PaymentDTO> payObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO payment_details  ");
            sb.append("(paymentID,paymentCode,paymentName,paymentDes,session_id,pay_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(PaymentDTO PYDTO : payObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{PYDTO.getPaymentID(),
                        PYDTO.getPaymentCode().trim(),
                        PYDTO.getPaymentName().trim(),
                        PYDTO.getPaymentDes().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public List<PaymentDTO> getPaymentDetails() throws AppException {
        List<PaymentDTO> pyDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM payment_details ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO.setPaymentID((Long) (retMap.get("paymentID")));
                    paymentDTO.setPaymentCode((String) (retMap.get("paymentCode")));
                    paymentDTO.setPaymentName((String) (retMap.get("paymentName")));
                    paymentDTO.setPaymentDes((String) (retMap.get("vendor_name")));
                    paymentDTO.setPaymentDes((String) (retMap.get("paymentDes")));
                    pyDTO.add(paymentDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return pyDTO;
    }
    
    public List<PaymentDTO> getPaymentReById(final long id) throws AppException {
        List<PaymentDTO> PYDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM payment_details WHERE paymentID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PaymentDTO pdto = new PaymentDTO();
                    pdto.setPaymentID((Long) (retMap.get("paymentID")));
                    pdto.setPaymentCode((String) (retMap.get("paymentCode")));
                    pdto.setPaymentName((String) (retMap.get("paymentName")));
                    pdto.setPaymentDes((String) (retMap.get("vendor_name")));
                    pdto.setPaymentDes((String) (retMap.get("paymentDes")));                   
                    PYDTO.add(pdto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return PYDTO;
    }
    
    public long checkDuplicatePayment(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {            
            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM payment_details ";
                qry+=" WHERE LOWER(paymentName) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//                
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM payment_details ";
                qry+=" WHERE LOWER(paymentCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM payment_details ";
                qry+=" WHERE LOWER(paymentCode)=? AND LOWER(paymentName) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase(),gName.toLowerCase()},
                        Long.class);//                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateUnit:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public int updatePaymentDetail(final List<PaymentDTO> payObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE payment_details  ");
            sb.append(" SET paymentCode=?,paymentName=?,paymentDes=?,session_id=?,pay_date=? ");            
            sb.append(" WHERE paymentID=? ");
            
            for(PaymentDTO SDTO : payObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SDTO.getPaymentCode().trim(),
                        SDTO.getPaymentName().trim(),
                        SDTO.getPaymentDes().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        SDTO.getPaymentID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public PaymentDTO getSelectedPayment(final long paymentId) throws AppException
    {
        PaymentDTO pymObj = new PaymentDTO();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT paymentID,paymentCode,paymentName,paymentDes ");
            sb.append(" FROM payment_details WHERE  paymentID=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{paymentId});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    pymObj.setPaymentID((Long) (resultMap.get("paymentID")));
                    pymObj.setPaymentCode((String) (resultMap.get("paymentCode")));
                    pymObj.setPaymentName((String) (resultMap.get("paymentName")));
                    pymObj.setPaymentDes((String) (resultMap.get("vendor_name")));
                    pymObj.setPaymentDes((String) (resultMap.get("paymentDes")));           
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedUnit:",e);
        }
        return pymObj;
    }
}
