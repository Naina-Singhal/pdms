/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.dto.OilDataDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
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
public class OilDataDAOImpl {
    private static final Logger logger = Logger.getLogger(OilDataDAOImpl.class);

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
    public OilDataDAOImpl(){
        
    }
    
    
    public int insertOilDaEntry(final List<OilDataDTO> oilObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO oil_entry_details  ");
            sb.append("(oilDataId,posr,poNumber,supplierName,description,invoiceNo,invoiceDate,");
            sb.append("invoiceAmt,invoiceQty,rate,state,scDiscount,ed_rate,");
            sb.append("edAmount,edCess,addDiscount,freight,fcAmount,cst,cstAmt,");
            sb.append("unitRate,qtyRecord,totalAmt,pprNo,amountPaid,qtyReceivedRate,");
            sb.append("session_id,oil_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?)");
            
            for(OilDataDTO oilDto : oilObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{oilDto.getOilDataId(),
                            oilDto.getPosr().trim(),
                            oilDto.getPoNumber(),
                            oilDto.getSupplierName().trim(),
                            oilDto.getDescription().trim(),
                            oilDto.getInvoiceNo(),
                            oilDto.getInvoiceDate().trim(),
                            oilDto.getInvoiceAmt(),
                            oilDto.getInvoiceQty().trim(),
                            oilDto.getRate(),
                            oilDto.getState().trim(),
                            oilDto.getScDiscount(),                           
                            oilDto.getEd_rate(),
                            oilDto.getEdAmount(),
                            oilDto.getEdCess(),
                            oilDto.getAddDiscount(),
                            oilDto.getFreight(),
                            oilDto.getFcAmount(),
                            oilDto.getCst().trim(),
                            oilDto.getCstAmt(),
                            oilDto.getUnitRate(),
                            oilDto.getQtyRecord().trim(),
                            oilDto.getTotalAmt(),
                            oilDto.getPprNo(),
                            oilDto.getAmountPaid(),
                            oilDto.getQtyReceivedRate(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertOilDaEntry:",e);
        }
        return retVal;
    }
    
    public List<OilDataDTO> getOilDataRecord() throws AppException {
        List<OilDataDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM oil_entry_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    OilDataDTO dTO = new OilDataDTO();
                    dTO.setOilDataId((Long) (retMap.get("oilDataId")));
                    dTO.setPosr((String) (retMap.get("posr")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setDescription((String) (retMap.get("description")));  
                    dTO.setInvoiceNo((Long) (retMap.get("invoiceNo")));
                    dTO.setInvoiceDate((String) (retMap.get("invoiceDate")));
                    dTO.setInvoiceAmt((BigDecimal) (retMap.get("invoiceAmt")));
                    dTO.setInvoiceQty((String) (retMap.get("invoiceQty")));
                    dTO.setRate((BigDecimal) (retMap.get("rate")));
                    dTO.setState((String) (retMap.get("state")));
                    dTO.setScDiscount((BigDecimal) (retMap.get("scDiscount")));                    
                    dTO.setEd_rate((BigDecimal) (retMap.get("ed_rate")));
                    dTO.setEdAmount((BigDecimal) (retMap.get("edAmount")));
                    dTO.setEdCess((BigDecimal) (retMap.get("edCess")));
                    dTO.setAddDiscount((BigDecimal) (retMap.get("addDiscount")));
                    dTO.setFreight((BigDecimal) (retMap.get("freight")));
                    dTO.setFcAmount((BigDecimal) (retMap.get("fcAmount")));
                    dTO.setCst((String) (retMap.get("cst")));
                    dTO.setCstAmt((BigDecimal) (retMap.get("cstAmt")));
                    dTO.setUnitRate((BigDecimal) (retMap.get("unitRate")));
                    dTO.setQtyRecord((String) (retMap.get("qtyRecord")));
                    dTO.setTotalAmt((BigDecimal) (retMap.get("totalAmt")));
                    dTO.setPprNo((Long) (retMap.get("pprNo")));
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid"))); 
                    dTO.setQtyReceivedRate((BigDecimal) (retMap.get("qtyReceivedRate")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getOilDataRecord:", e);
        }
        return list;
    }
    
    public List<OilDataDTO> getOilDataReByID(final long id) throws AppException {
        List<OilDataDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM oil_entry_details WHERE oilDataId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    OilDataDTO dTO = new OilDataDTO();
                    dTO.setOilDataId((Long) (retMap.get("oilDataId")));
                    dTO.setPosr((String) (retMap.get("posr")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setDescription((String) (retMap.get("description")));  
                    dTO.setInvoiceNo((Long) (retMap.get("invoiceNo")));
                    dTO.setInvoiceDate((String) (retMap.get("invoiceDate")));
                    dTO.setInvoiceAmt((BigDecimal) (retMap.get("invoiceAmt")));
                    dTO.setInvoiceQty((String) (retMap.get("invoiceQty")));
                    dTO.setRate((BigDecimal) (retMap.get("rate")));
                    dTO.setState((String) (retMap.get("state")));
                    dTO.setScDiscount((BigDecimal) (retMap.get("scDiscount")));                    
                    dTO.setEd_rate((BigDecimal) (retMap.get("ed_rate")));
                    dTO.setEdAmount((BigDecimal) (retMap.get("edAmount")));
                    dTO.setEdCess((BigDecimal) (retMap.get("edCess")));
                    dTO.setAddDiscount((BigDecimal) (retMap.get("addDiscount")));
                    dTO.setFreight((BigDecimal) (retMap.get("freight")));
                    dTO.setFcAmount((BigDecimal) (retMap.get("fcAmount")));
                    dTO.setCst((String) (retMap.get("cst")));
                    dTO.setCstAmt((BigDecimal) (retMap.get("cstAmt")));
                    dTO.setUnitRate((BigDecimal) (retMap.get("unitRate")));
                    dTO.setQtyRecord((String) (retMap.get("qtyRecord")));
                    dTO.setTotalAmt((BigDecimal) (retMap.get("totalAmt")));
                    dTO.setPprNo((Long) (retMap.get("pprNo")));
                    dTO.setAmountPaid((BigDecimal) (retMap.get("amountPaid"))); 
                    dTO.setQtyReceivedRate((BigDecimal) (retMap.get("qtyReceivedRate")));
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getOilDataReByID:", e);
        }
        return List;
    }
    
    public int updateOilDataDetail(final List<OilDataDTO> oilObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE oil_entry_details  ");
            sb.append(" SET posr=?,poNumber=?,supplierName=?,description=?,invoiceNo=?,invoiceDate=?,invoiceAmt=?,");  
            sb.append(" invoiceQty=?,rate=?,state=?,scDiscount=?,ed_rate=?,edAmount=?,edCess=?, ");
            sb.append(" addDiscount=?,freight=?,fcAmount=?,cst=?,cstAmt=?,unitRate=?,qtyRecord=?, ");
            sb.append(" totalAmt=?,pprNo=?,amountPaid=?,qtyReceivedRate=?,session_id=?,oil_date=?  ");
            sb.append(" WHERE oilDataId=? ");
            
            for(OilDataDTO oilDto : oilObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                            oilDto.getPosr().trim(),
                            oilDto.getPoNumber(),
                            oilDto.getSupplierName().trim(),
                            oilDto.getDescription().trim(),
                            oilDto.getInvoiceNo(),
                            oilDto.getInvoiceDate().trim(),
                            oilDto.getInvoiceAmt(),
                            oilDto.getInvoiceQty().trim(),
                            oilDto.getRate(),
                            oilDto.getState().trim(),
                            oilDto.getScDiscount(),                           
                            oilDto.getEd_rate(),
                            oilDto.getEdAmount(),
                            oilDto.getEdCess(),
                            oilDto.getAddDiscount(),
                            oilDto.getFreight(),
                            oilDto.getFcAmount(),
                            oilDto.getCst().trim(),
                            oilDto.getCstAmt(),
                            oilDto.getUnitRate(),
                            oilDto.getQtyRecord().trim(),
                            oilDto.getTotalAmt(),
                            oilDto.getPprNo(),
                            oilDto.getAmountPaid(),
                            oilDto.getQtyReceivedRate(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            oilDto.getOilDataId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateOilDataDetail:",e);
        }
        return retVal;
    }
   
}
