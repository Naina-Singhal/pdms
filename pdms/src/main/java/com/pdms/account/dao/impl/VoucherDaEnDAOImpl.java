/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.ItemsDto.HoaItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.account.ItemsDto.RvVouDaEnItemsDTO;
import com.pdms.account.ItemsDto.VouDaEnBillsDTO;
import com.pdms.account.ItemsDto.VouTempDTO;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.RtgsDAOImpl;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author myassessment
 */
@Repository
public class VoucherDaEnDAOImpl {
    private static final Logger logger = Logger.getLogger(VoucherDaEnDAOImpl.class);

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
    private TransactionTemplate transactionTemplate;
    
    @Autowired
    private DateUtil dateUtil;
    
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public VoucherDaEnDAOImpl(){
        
    }
    
    
    public int insertVoucherDaEntry(final VouTempDTO vouObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    int retvalHoa = 0;
                    int retvalBil = 0;
                    int retvalRv = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO voucher_data_entry  ");
                        sb.append("(voucherID,fileNumber,poNumber,posrFileNo,bgClause,nameOfSupplr,gcode,fileClosed,modeOfPaymt,");
                        sb.append("abc,bbys,scode,rpumOrmngr,cqeDdR,pprNo,");
                        sb.append("lrNo,bankLRC,lrDT,quantity,ibcNo,pprNo2,amountPaid,");
                        sb.append("dateOfpmt,recoveries,defference,rsInWords,");
                        sb.append("compCode,session_id,voucher_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,");
                        sb.append("?,?,?,?,?,?,?,?,?,");
                        sb.append("?,?,?,?,?,?,?,?,?,?)");

                        List<VoucherDTO> vouDto = vouObj.getVoucherDTO();
                        for (VoucherDTO VDTO : vouDto) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{VDTO.getVoucherID(),
                                        VDTO.getFileNumber(),
                                        VDTO.getPoNumber(),
                                        VDTO.getPosrFileNo().trim(),
                                        VDTO.getBgClause().trim(),
                                        VDTO.getNameOfSupplr().trim(),
                                        VDTO.getGcode().trim(),
                                        VDTO.getFileClosed().trim(),
                                        VDTO.getModeOfPaymt().trim(),
                                        VDTO.getAbc().trim(),
                                        VDTO.getBbys().trim(),
                                        VDTO.getScode().trim(),
                                        VDTO.getRpumOrmngr().trim(),
                                        VDTO.getCqeDdR().trim(),
                                        VDTO.getPprNo(),
                                        VDTO.getLrNo().trim(),
                                        VDTO.getBankLRC().trim(),
                                        VDTO.getLrDT().trim(),
                                        VDTO.getQuantity().trim(),
                                        VDTO.getIbcNo().trim(),
                                        VDTO.getPprNo2().trim(),
                                        VDTO.getAmountPaid().trim(),
                                        VDTO.getDateOfpmt().trim(),
                                        VDTO.getRecoveries().trim(),
                                        VDTO.getDefference().trim(),
                                        VDTO.getRsInWords().trim(),
                                        VDTO.getCompCode(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO voucher_items_details  ");
                        sbItem.append("(itemsInVouDaEnId,fileNumber,poNumBer,rate,igst,cgst,");
                        sbItem.append("sgst,hsnCode,gbys,qtyReceived)  ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?)");

                        List<ItemsInVouDaEnDTO> items = vouObj.getItemsDTO();
                        for (ItemsInVouDaEnDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getItemsInVouDaEnId(),
                                        itemObj.getFileNumber(),
                                        itemObj.getPoNumBer(),
                                        itemObj.getRate(),
                                        itemObj.getIgst(),
                                        itemObj.getCgst(),
                                        itemObj.getSgst(),
                                        itemObj.getHsnCode().trim(),
                                        itemObj.getGbys().trim(),
                                        itemObj.getQtyReceived().trim()
                                    });
                        }
                        
                        List<HoaItemsInVouDaEnDTO> hoa = vouObj.getHoaDTO();
                        StringBuilder sbHoa = new StringBuilder();
                        sbHoa.append(" INSERT INTO voucher_hoa_details  ");
                        sbHoa.append("(hoaItemsInVouDaEnId,fileNumber,poNumBer,hoaName,shortCode,cbcbcb,");
                        sbHoa.append("debitamt,creditAmt)  ");
                        sbHoa.append(" VALUES ");
                        sbHoa.append("(?,?,?,?,?,?,?,?)");
                        
                        for (HoaItemsInVouDaEnDTO hoaObj : hoa) {
                            retvalHoa = getJdbcTemplate().update(sbHoa.toString(),
                                    new Object[]{hoaObj.getHoaItemsInVouDaEnId(),
                                        hoaObj.getFileNumber(),
                                        hoaObj.getPoNumBer(),
                                        hoaObj.getHoaName().trim(),
                                        hoaObj.getShortCode().trim(),
                                        hoaObj.getCbcbcb(),
                                        hoaObj.getDebitamt(),
                                        hoaObj.getCreditAmt()                                        
                                    });
                        }
                        
                        List<VouDaEnBillsDTO> bills = vouObj.getBillsDTO();
                        StringBuilder sbBil = new StringBuilder();
                        sbBil.append(" INSERT INTO voucher_bills_details  ");
                        sbBil.append("(vouDaEnBillsId,fileNumber,poNumBer,billNUM,billDATE) ");                        
                        sbBil.append(" VALUES ");
                        sbBil.append("(?,?,?,?,?)");
                        
                        for (VouDaEnBillsDTO bilObj : bills) {
                            retvalBil = getJdbcTemplate().update(sbBil.toString(),
                                    new Object[]{bilObj.getVouDaEnBillsId(),
                                        bilObj.getFileNumber(),
                                        bilObj.getPoNumBer(),
                                        bilObj.getBillNUM(),
                                        bilObj.getBillDATE().trim()
                                    });
                        }
                        
                        List<RvVouDaEnItemsDTO> rv = vouObj.getRvDTO();
                        StringBuilder sbRv = new StringBuilder();
                        sbRv.append(" INSERT INTO voucher_rv_details  ");
                        sbRv.append("(rvVouDaEnItems,fileNumber,poNumBer,rvnumber,rvdate,userid,curdate) ");                        
                        sbRv.append(" VALUES ");
                        sbRv.append("(?,?,?,?,?,?,?)");
                        
                        for (RvVouDaEnItemsDTO rvObj : rv) {
                            retvalRv = getJdbcTemplate().update(sbRv.toString(),
                                    new Object[]{rvObj.getRvVouDaEnItems(),
                                        rvObj.getFileNumber(),
                                        rvObj.getPoNumBer(),
                                        rvObj.getRvnumber(),                                        
                                        dateUtil.dateConvertionFromJSPToDB(rvObj.getRvdate().trim()),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }
                       

                        if ((retValForm > 0) && (retvalItems > 0) && (retvalHoa > 0) && (retvalBil > 0) && (retvalRv > 0)) {
                            retVal = retvalItems;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -5;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVoucherDaEntry");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertVoucherDaEntry:: " + e.getMessage());
        }

    }
    
    public List<VoucherDTO> getVoucherDaRecord() throws AppException {
        List<VoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM voucher_data_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherDTO dTO = new VoucherDTO();
                    dTO.setVoucherID((Long) (retMap.get("voucherID")));
                    dTO.setFileNumber((Long) (retMap.get("fileNumber")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setPosrFileNo((String) (retMap.get("posrFileNo")));
                    dTO.setBgClause((String) (retMap.get("bgClause")));
                    dTO.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    dTO.setGcode((String) (retMap.get("gcode")));  
                    dTO.setFileClosed((String) (retMap.get("fileClosed")));
                    dTO.setModeOfPaymt((String) (retMap.get("modeOfPaymt")));
                    dTO.setAbc((String) (retMap.get("abc")));
                    dTO.setBbys((String) (retMap.get("bbys")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setRpumOrmngr((String) (retMap.get("rpumOrmngr")));
                    dTO.setCqeDdR((String) (retMap.get("cqeDdR")));                    
                    dTO.setPprNo((long) (retMap.get("pprNo")));
                    dTO.setLrNo((String) (retMap.get("lrNo")));
                    dTO.setBankLRC((String) (retMap.get("bankLRC")));
                    dTO.setLrDT((String) (retMap.get("lrDT")));
                    dTO.setQuantity((String) (retMap.get("quantity")));
                    dTO.setIbcNo((String) (retMap.get("ibcNo")));
                    dTO.setPprNo2((String) (retMap.get("pprNo2")));
                    dTO.setAmountPaid((String) (retMap.get("amountPaid")));
                    dTO.setDateOfpmt((String) (retMap.get("dateOfpmt")));                    
                    dTO.setRecoveries((String) (retMap.get("recoveries")));
                    dTO.setDefference((String) (retMap.get("defference")));
                    dTO.setRsInWords((String) (retMap.get("rsInWords")));
                    dTO.setCompCode((long) (retMap.get("compCode")));
                    list.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherDaRecord:", e);
        }
        return list;
    }
    
    public long getPprNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM voucher_data_entry WHERE pprNo=(SELECT MAX(pprNo) FROM voucher_data_entry) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   VoucherDTO vdto = new VoucherDTO();                    
                    vdto.setPprNo((long)(resultMap.get("pprNo")));
                    maxNo = (long)(resultMap.get("pprNo")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public long getCompCodeForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM voucher_data_entry WHERE compCode=(SELECT MAX(compCode) FROM voucher_data_entry) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   VoucherDTO vdto = new VoucherDTO();                    
                    vdto.setCompCode((long)(resultMap.get("compCode")));
                    maxNo = (long)(resultMap.get("compCode")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCompCodeForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<VoucherDTO> getVoucherPoDetails(final long poNum) throws AppException {
        List<VoucherDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT voucherID,poNumber,nameOfSupplr,modeOfPaymt,ibcNo,pprNo");
            sb.append(" FROM voucher_data_entry WHERE poNumber="+poNum);
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
            throw new AppException("Exception Occurred : getVoucherPoDetails:", e);
        }
        return list;
    }
    
    public List<VoucherDTO> getVoucherDaEnReById(final long id) throws AppException {
        List<VoucherDTO> List = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM voucher_data_entry WHERE voucherID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VoucherDTO dTO = new VoucherDTO();
                    dTO.setVoucherID((Long) (retMap.get("voucherID")));
                    dTO.setFileNumber((Long) (retMap.get("fileNumber")));
                    dTO.setPoNumber((Long) (retMap.get("poNumber")));
                    dTO.setPosrFileNo((String) (retMap.get("posrFileNo")));
                    dTO.setBgClause((String) (retMap.get("bgClause")));
                    dTO.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    dTO.setGcode((String) (retMap.get("gcode")));  
                    dTO.setFileClosed((String) (retMap.get("fileClosed")));
                    dTO.setModeOfPaymt((String) (retMap.get("modeOfPaymt")));
                    dTO.setAbc((String) (retMap.get("abc")));
                    dTO.setBbys((String) (retMap.get("bbys")));
                    dTO.setScode((String) (retMap.get("scode")));
                    dTO.setRpumOrmngr((String) (retMap.get("rpumOrmngr")));
                    dTO.setCqeDdR((String) (retMap.get("cqeDdR")));                    
                    dTO.setPprNo((long) (retMap.get("pprNo")));
                    dTO.setLrNo((String) (retMap.get("lrNo")));
                    dTO.setBankLRC((String) (retMap.get("bankLRC")));
                    dTO.setLrDT((String) (retMap.get("lrDT")));
                    dTO.setQuantity((String) (retMap.get("quantity")));
                    dTO.setIbcNo((String) (retMap.get("ibcNo")));
                    dTO.setPprNo2((String) (retMap.get("pprNo2")));
                    dTO.setAmountPaid((String) (retMap.get("amountPaid")));
                    dTO.setDateOfpmt((String) (retMap.get("dateOfpmt")));                    
                    dTO.setRecoveries((String) (retMap.get("recoveries")));
                    dTO.setDefference((String) (retMap.get("defference")));
                    dTO.setRsInWords((String) (retMap.get("rsInWords")));
                    dTO.setCompCode((long) (retMap.get("compCode")));
                    List.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVoucherDaEnReById:", e);
        }
        return List;
    }
    
    public int updateVoucherDaEnDetail(final List<VoucherDTO> vouObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE voucher_data_entry  ");
            sb.append(" SET fileNumber=?,poNumber=?,posrFileNo=?,bgClause=?,nameOfSupplr=?,gcode=?, ");  
            sb.append(" fileClosed=?,modeOfPaymt=?,abc=?,bbys=?,scode=?,rpumOrmngr=?, ");
            sb.append(" cqeDdR=?,pprNo=?,lrNo=?,bankLRC=?,lrDT=?,quantity=?, ");
            sb.append(" ibcNo=?,pprNo2=?,amountPaid=?,dateOfpmt=?,recoveries=?,defference=?, ");
            sb.append(" rsInWords=?,compCode=?,session_id=?,voucher_date=?  ");            
            sb.append(" WHERE voucherID=? ");
            
            for(VoucherDTO VDTO : vouObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                            VDTO.getFileNumber(),
                            VDTO.getPoNumber(),                            
                            VDTO.getPosrFileNo().trim(),
                            VDTO.getBgClause().trim(),
                            VDTO.getNameOfSupplr().trim(),
                            VDTO.getGcode().trim(),
                            VDTO.getFileClosed().trim(),
                            VDTO.getModeOfPaymt().trim(),
                            VDTO.getAbc().trim(),
                            VDTO.getBbys().trim(),
                            VDTO.getScode().trim(),
                            VDTO.getRpumOrmngr().trim(),
                            VDTO.getCqeDdR().trim(),                           
                            VDTO.getPprNo(),
                            VDTO.getLrNo().trim(),
                            VDTO.getBankLRC().trim(),
                            VDTO.getLrDT().trim(),
                            VDTO.getQuantity().trim(),
                            VDTO.getIbcNo().trim(),
                            VDTO.getPprNo2().trim(),
                            VDTO.getAmountPaid().trim(),
                            VDTO.getDateOfpmt().trim(),                            
                            VDTO.getRecoveries().trim(),
                            VDTO.getDefference().trim(),
                            VDTO.getRsInWords().trim(),
                            VDTO.getCompCode(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime(),
                            VDTO.getVoucherID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateVoucherDaEnDetail:",e);
        }
        return retVal;
    }
    
    public List<ItemsInVouDaEnDTO> getVouDaEnItemWithBillEnBeDates(final long poNum) throws AppException {
        List<ItemsInVouDaEnDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.rate,a.igst,a.cgst,a.sgst,a.hsnCode,a.gbys,a.qtyReceived,");
            sb.append(" b.poNumber,b.billNo,b.billDate,b.billAmt,b.gstinNo ");            
            sb.append(" FROM voucher_items_details a ");
            sb.append(" JOIN bill_entry b ON a.poNumBer = b.poNumber ");
            sb.append(" ");
            
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ItemsInVouDaEnDTO BTO = new ItemsInVouDaEnDTO();                    
                    BTO.setRate((BigDecimal) (retMap.get("rate")));
                    BTO.setIgst((BigDecimal) (retMap.get("igst")));
                    BTO.setCgst((BigDecimal) (retMap.get("cgst")));
                    BTO.setSgst((BigDecimal) (retMap.get("sgst")));
                    BTO.setHsnCode((String) (retMap.get("hsnCode")));
                    BTO.setGbys((String) (retMap.get("gbys")));
                    BTO.setQtyReceived((String) (retMap.get("qtyReceived")));
                    BTO.getBillObj().setPoNumber((String) (retMap.get("poNumber")));                    
                    BTO.getBillObj().setBillNo((long) (retMap.get("billNo")));  
                    BTO.getBillObj().setBillDate((String) (retMap.get("billDate")));                    
                    BTO.getBillObj().setBillAmt((BigDecimal) (retMap.get("billAmt")));                    
                    BTO.getBillObj().setGstinNo((String) (retMap.get("gstinNo")));
                    list.add(BTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVouDaEnItemWithBillEnBeDates:", e);
        }
        logger.info("--------"+list);
        return list;
    }
}
