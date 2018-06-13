/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.ItemsDto.HoaItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.OcbVouNoDTO;
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import com.pdms.account.ItemsDto.VouNoTempDTO;
import com.pdms.account.dto.LcTableDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.dto.RcivControlDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.RcivControlItemsDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
 * @author naagu
 */
@Repository
public class VoucherNoDAOImpl {
    private static final Logger logger = Logger.getLogger(VoucherNoDAOImpl.class);

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
    public VoucherNoDAOImpl(){
        
    }
    
    
    public int insertVoucherNoEntry(final VouNoTempDTO vounoObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    int retvalOcb = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO voucher_No_entry  ");
                        sb.append("(voucherNoId,compCode,supplierName,posr,poNumber,pprNumber,");
                        sb.append("voucherNo,");
                        sb.append("voucherDate,chequeNo,chequeDate,chequeAmount,cqe_dd_rtgs,bankCode,bank,");
                        sb.append("req_number,lcBalanceAmt,rtgsNumber,ibcNumber,");
                        sb.append("session_id,voucher_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,");
                        sb.append("?,?,?,?,");
                        sb.append("?,?,?,?,?,?)");

                        List<VoucherNoDTO> vouobj = vounoObj.getVoucherNoDTO();
                        for (VoucherNoDTO VDTO : vouobj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{VDTO.getVoucherNoId(),
                                        VDTO.getCompCode(),
                                        VDTO.getSupplierName().trim(),
                                        VDTO.getPosr().trim(),
                                        VDTO.getPoNumber().trim(),
                                        VDTO.getPprNumber().trim(),
                                        VDTO.getVoucherNo(),
                                        VDTO.getVoucherDate().trim(),
                                        VDTO.getChequeNo().trim(),
                                        dateUtil.dateConvertionFromJSPToDB(VDTO.getChequeDate().trim()),
                                        VDTO.getChequeAmount(),
                                        VDTO.getCqe_dd_rtgs().trim(),
                                        VDTO.getBankCode().trim(),
                                        VDTO.getBank().trim(),
                                        VDTO.getReq_number(),
                                        VDTO.getLcBalanceAmt(),
                                        VDTO.getRtgsNumber().trim(),
                                        VDTO.getIbcNumber(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO voucher_no_hoa_details  ");
                        sbItem.append("(vouHoaItemId,compCode,poNumBer,hoaname,shortcode,cbamt,creamt,debamt,");
                        sbItem.append("userid,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?)");

                        List<VouNoHoaItemsDTO> items = vounoObj.getHoaDTO();
                        for (VouNoHoaItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getVouHoaItemId(),
                                        itemObj.getCompCode(),
                                        itemObj.getPoNumBer(),
                                        itemObj.getHoaname().trim(),
                                        itemObj.getShortcode().trim(),
                                        itemObj.getCbamt(),
                                        itemObj.getCreamt(),
                                        itemObj.getDebamt(),                                        
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }
                        
                        StringBuilder sbOcb = new StringBuilder();
                        sbOcb.append(" INSERT INTO voucher_no_ocb  ");
                        sbOcb.append("(ocbId,compCode,poNumBer,month,openbalance,closebalance,dateofentry,");
                        sbOcb.append("userid,cur_date) ");
                        sbOcb.append(" VALUES ");
                        sbOcb.append("(?,?,?,?,?,?,?,?,?)");

                        List<OcbVouNoDTO> ocb = vounoObj.getOcbDTO();
                        for (OcbVouNoDTO itemObj : ocb) {
                            retvalOcb = getJdbcTemplate().update(sbOcb.toString(),
                                    new Object[]{itemObj.getOcbId(),
                                        itemObj.getCompCode(),
                                        itemObj.getPoNumBer(),                                        
                                        dateUtil.dateConvertionFromJSPToDB(itemObj.getMonth().trim()),
                                        itemObj.getOpenbalance(),
                                        itemObj.getClosebalance(),
                                        itemObj.getDateofentry(),                                                                              
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }

                        if ((retValForm > 0) && (retvalItems > 0) && (retvalOcb > 0)) {
                            retVal = retvalItems;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -5;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVoucherNoEntry");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertVoucherNoEntry: " + e.getMessage());
        }

    }
    
    public List<VoucherNoDTO> getVoucherNoDaRecord() throws AppException {
        List<VoucherNoDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM voucher_no_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherNoDTO dTO = new VoucherNoDTO();
                    dTO.setVoucherNoId((Long) (retMap.get("voucherNoId")));
                    dTO.setCompCode((long) (retMap.get("compCode")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setPosr((String) (retMap.get("posr")));
                    dTO.setPoNumber((String) (retMap.get("poNumber")));  
                    dTO.setPprNumber((String) (retMap.get("pprNumber")));                                   
                    dTO.setVoucherNo((String) (retMap.get("voucherNo")));
                    dTO.setVoucherDate((String) (retMap.get("voucherDate")));
                    dTO.setChequeNo((String) (retMap.get("chequeNo")));                    
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("chequeDate")));
                    dTO.setChequeDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    dTO.setChequeAmount((BigDecimal) (retMap.get("chequeAmount")));
                    dTO.setCqe_dd_rtgs((String) (retMap.get("cqe_dd_rtgs")));
                    dTO.setBankCode((String) (retMap.get("bankCode")));
                    dTO.setBank((String) (retMap.get("bank")));
                    dTO.setReq_number((Long) (retMap.get("req_number")));
                    dTO.setLcBalanceAmt((BigDecimal) (retMap.get("lcBalanceAmt")));
                    dTO.setRtgsNumber((String) (retMap.get("rtgsNumber")));
                    dTO.setIbcNumber((Long)(retMap.get("ibcNumber")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNoDataRecord:", e);
        }
        return list;
    }
    
    
    
    public List<VoucherNoDTO> getVoucherNos() throws AppException {
        List<VoucherNoDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT voucherNoId,compCode,voucherNo FROM voucher_no_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherNoDTO dTO = new VoucherNoDTO();
                    dTO.setCompCode((long) (retMap.get("compCode")));
                    dTO.setVoucherNo((String) (retMap.get("voucherNo")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNos:", e);
        }
        return list;
    }
    
    public long getRequisitionNoFrInc() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM voucher_no_entry WHERE req_number=(SELECT MAX(req_number) FROM voucher_no_entry) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                   VoucherNoDTO vdto = new VoucherNoDTO();                    
                    vdto.setReq_number((Long)(resultMap.get("req_number")));
                    maxNo = (Long) resultMap.get("req_number"); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getRequisitionNoFrInc:",e);
        }
        return maxNo;
    }
    
    public List<VoucherDTO> getVoucherDaEnDeByCompCode(final long compCode) throws AppException {
        List<VoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT voucherID,poNumber,nameOfSupplr,modeOfPaymt,ibcNo,pprNo");
            sb.append(" FROM voucher_data_entry WHERE compCode="+compCode);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherDTO dTO = new VoucherDTO();
                    dTO.setVoucherID((Long) (retMap.get("voucherID")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));                    
                    dTO.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));                    
                    dTO.setModeOfPaymt((String) (retMap.get("modeOfPaymt")));
                    dTO.setIbcNo((String) (retMap.get("ibcNo")));
                    dTO.setPprNo((Long) (retMap.get("pprNo")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherDaEnDeByCompCode:", e);
        }
        return list;
    }
    
    public List<VoucherNoDTO> getVoucherNoDeByReqNo(final long reqNumber) throws AppException {
        List<VoucherNoDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT voucherNoId,supplierName,voucherNo,voucherDate,req_number FROM voucher_no_entry ");
            sb.append(" WHERE req_number ="+reqNumber);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherNoDTO dTO = new VoucherNoDTO();
                    dTO.setVoucherNoId((Long) (retMap.get("voucherNoId")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setVoucherNo((String) (retMap.get("voucherNo")));
                    dTO.setVoucherDate((String) (retMap.get("voucherDate")));
                    dTO.setReq_number((Long) (retMap.get("req_number")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNoDeByReqNo:", e);
        }
        return list;
    }
    
    public List<VoucherNoDTO> getVoucherNumberReById(final long id) throws AppException {
        List<VoucherNoDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM voucher_no_entry WHERE voucherNoId=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherNoDTO dTO = new VoucherNoDTO();
                    dTO.setVoucherNoId((Long) (retMap.get("voucherNoId")));
                    dTO.setCompCode((long) (retMap.get("compCode")));
                    dTO.setSupplierName((String) (retMap.get("supplierName")));
                    dTO.setPosr((String) (retMap.get("posr")));
                    dTO.setPoNumber((String) (retMap.get("poNumber")));  
                    dTO.setPprNumber((String) (retMap.get("pprNumber")));                                   
                    dTO.setVoucherNo((String) (retMap.get("voucherNo")));
                    dTO.setVoucherDate((String) (retMap.get("voucherDate")));
                    dTO.setChequeNo((String) (retMap.get("chequeNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("chequeDate")));
                    dTO.setChequeDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    dTO.setChequeAmount((BigDecimal) (retMap.get("chequeAmount")));
                    dTO.setCqe_dd_rtgs((String) (retMap.get("cqe_dd_rtgs")));
                    dTO.setBankCode((String) (retMap.get("bankCode")));
                    dTO.setBank((String) (retMap.get("bank")));
                    dTO.setReq_number((Long) (retMap.get("req_number")));
                    dTO.setLcBalanceAmt((BigDecimal) (retMap.get("lcBalanceAmt")));
                    dTO.setRtgsNumber((String) (retMap.get("rtgsNumber")));
                    dTO.setIbcNumber((Long)(retMap.get("ibcNumber")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNumberReById:", e);
        }
        return list;
    }
    
    public int updateVoucherNumberDetail(final List<VoucherNoDTO> vounoObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE voucher_no_entry  ");
            sb.append(" SET compCode=?,supplierName=?,posr=?,poNumber=?,pprNumber=?,voucherNo=?,");  
            sb.append("voucherDate=?,chequeNo=?,chequeDate=?,chequeAmount=?,cqe_dd_rtgs=?,bankCode=?, ");
            sb.append("bank=?,req_number=?,lcBalanceAmt=?,rtgsNumber=?,ibcNumber=?,session_id=?,voucher_date=? ");
            sb.append(" WHERE voucherNoId=? ");
            
            for(VoucherNoDTO VDTO : vounoObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        VDTO.getCompCode(),
                            VDTO.getSupplierName().trim(),
                            VDTO.getPosr().trim(),
                            VDTO.getPoNumber().trim(),
                            VDTO.getPprNumber().trim(),                                                       
                            VDTO.getVoucherNo(),
                            VDTO.getVoucherDate().trim(),
                            VDTO.getChequeNo().trim(),
                            dateUtil.dateConvertionFromJSPToDB(VDTO.getChequeDate().trim()),
                            VDTO.getChequeAmount(),
                            VDTO.getCqe_dd_rtgs().trim(),
                            VDTO.getBankCode().trim(),
                            VDTO.getBank().trim(),
                            VDTO.getReq_number(),
                            VDTO.getLcBalanceAmt(),
                            VDTO.getRtgsNumber().trim(),  
                            VDTO.getIbcNumber(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            VDTO.getVoucherNoId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateVoucherNumberDetail:",e);
        }
        return retVal;
    }
    
    public List<HoaItemsInVouDaEnDTO> getVoucherDaEnHoaByPo(final long Ponumber) throws AppException {
        List<HoaItemsInVouDaEnDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM voucher_hoa_details WHERE poNumBer=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{Ponumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    HoaItemsInVouDaEnDTO dTO = new HoaItemsInVouDaEnDTO();
                    dTO.setHoaItemsInVouDaEnId((Long) (retMap.get("hoaItemsInVouDaEnId")));
                    dTO.setFileNumber((Long) (retMap.get("fileNumber")));
                    dTO.setPoNumBer((Long) (retMap.get("poNumBer")));
                    dTO.setHoaName((String) (retMap.get("hoaName")));
                    dTO.setShortCode((String) (retMap.get("shortCode")));
                    dTO.setCbcbcb((BigDecimal) (retMap.get("cbcbcb")));
                    dTO.setDebitamt((BigDecimal) (retMap.get("debitamt")));  
                    dTO.setCreditAmt((BigDecimal) (retMap.get("creditAmt")));
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherDaEnHoaByPo:", e);
        }
        return List;
    }
    
    public int saveLcTableDe(final List<LcTableDTO> lcObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO lc_table_details  ");
            sb.append("(lcTableId,monthlc,opbalance,aamount,total,cmamount, "); 
            sb.append("cbamount,dateoe,user_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?)");
            
            for(LcTableDTO IBCDTO : lcObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{IBCDTO.getLcTableId(),                        
                        dateUtil.dateConvertionFromJSPToDB(IBCDTO.getMonthlc().trim()),
                        IBCDTO.getOpbalance(),
                        IBCDTO.getAamount(),
                        IBCDTO.getTotal(),
                        IBCDTO.getCmamount(),
                        IBCDTO.getCbamount(),
                        IBCDTO.getDateoe().trim(),                        
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : saveLcTableDe:",e);
        }
        return retVal;
    }
    
    public List<LcTableDTO> getOpenBalanceRecord() throws AppException {
        List<LcTableDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT lcTableId,monthlc,opbalance,aamount FROM lc_table_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    LcTableDTO dTO = new LcTableDTO();
                    dTO.setLcTableId((Long) (retMap.get("lcTableId")));
                    String monlc = dateUtil.convertTimestampToString((Timestamp) (retMap.get("monthlc")));
                    dTO.setMonthlc((dateUtil.dateConvertionFromDBToJSP(monlc))); 
                    dTO.setOpbalance((BigDecimal) (retMap.get("opbalance")));
                    dTO.setAamount((BigDecimal) (retMap.get("aamount")));                    
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getOpenBalanceRecord:", e);
        }
        return List;
    }
    
    public List<VoucherNoDTO> getVoucherNoDeOnDate(final String onDate) throws AppException {
        List<VoucherNoDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            String onDateDb = dateUtil.dateConvertionFromJSPToDB(onDate);
            sb.append(" SELECT  a.supplierName,a.poNumber,a.voucherNo,a.voucherDate,a.chequeNo,a.chequeDate,");
            sb.append(" a.chequeAmount,a.bankCode,a.rtgsNumber,r.vendorCode,r.accountNo,r.ifscCode,");
            sb.append(" r.branch,r.city,v.vendor_name,b.bankName ");
            sb.append(" FROM voucher_no_entry a  ");
            sb.append(" JOIN rtgs_details r ON a.rtgsNumber = r.rtgsNo ");
            sb.append(" JOIN vendor_details v ON r.vendorCode = v.vendor_code ");
            sb.append(" JOIN bank_details b ON a.bankCode = b.bankCode  ");
            sb.append("  ");
            sb.append(" WHERE a.chequeDate=? ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{onDateDb});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherNoDTO dTO = new VoucherNoDTO();                   
                    dTO.setSupplierName((String) (retMap.get("supplierName")));                    
                    dTO.setPoNumber((String) (retMap.get("poNumber")));                                                       
                    dTO.setVoucherNo((String) (retMap.get("voucherNo")));
                    dTO.setVoucherDate((String) (retMap.get("voucherDate")));
                    dTO.setChequeNo((String) (retMap.get("chequeNo")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("chequeDate")));
                    dTO.setChequeDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    dTO.setChequeAmount((BigDecimal) (retMap.get("chequeAmount")));                    
                    dTO.setBankCode((String) (retMap.get("bankCode")));             
                    dTO.setRtgsNumber((String) (retMap.get("rtgsNumber"))); 
                    dTO.getRtgsObj().setVendorCode((String) (retMap.get("vendorCode")));
                    dTO.getRtgsObj().setAccountNo((String) (retMap.get("accountNo")));
                    dTO.getRtgsObj().setIfscCode((String) (retMap.get("ifscCode")));
                    dTO.getRtgsObj().setBranch((String) (retMap.get("branch")));
                    dTO.getRtgsObj().setCity((String) (retMap.get("city")));
                    dTO.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    dTO.getBankObj().setBankName((String) (retMap.get("bankName")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNoDeOnDate:", e);
        }
        return list;
    }
    
    
    public List<VouNoHoaItemsDTO> getVoucherNoItemsByHoaPdf(final String hoAccount, final String hoaDate) throws AppException {
        List<VouNoHoaItemsDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            String hoaDateDb = dateUtil.dateConvertionFromJSPToDB(hoaDate);
            logger.info(hoAccount+"---------"+hoaDateDb);
            sb.append(" SELECT  a.compCode,a.hoaname,a.shortcode,a.creamt,a.debamt, ");
            sb.append(" v.supplierName,v.poNumber,v.voucherNo,v.voucherDate, ");
            sb.append(" d.vendor_name ");
            sb.append(" FROM voucher_no_hoa_details a ");
            sb.append(" JOIN voucher_no_entry v ON a.compCode = v.compCode ");
            sb.append(" JOIN vendor_details d ON v.supplierName = d.vendor_code ");            
            sb.append(" WHERE  a.hoaname=? AND DATE(a.cur_date)=? ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{hoAccount,hoaDateDb});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VouNoHoaItemsDTO dTO = new VouNoHoaItemsDTO();                    
                    dTO.setCompCode((Long) (retMap.get("compCode")));
                    dTO.setHoaname((String) (retMap.get("hoaname")));
                    dTO.setShortcode((String) (retMap.get("shortcode")));
                    dTO.setCreamt((BigDecimal) (retMap.get("creamt")));
                    dTO.setDebamt((BigDecimal) (retMap.get("debamt"))); 
                    dTO.getVouObj().setPoNumber((String) (retMap.get("poNumber")));
                    dTO.getVouObj().setVoucherNo((String) (retMap.get("voucherNo")));
                    dTO.getVouObj().setVoucherDate((String) (retMap.get("voucherDate")));
                    dTO.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherNoItemsByHoaPdf:", e);
        }
        return List;
    }
}
