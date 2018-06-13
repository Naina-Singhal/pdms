/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.dao.impl;

import com.pdms.account.ItemsDto.BillTempDTO;
import com.pdms.account.dto.BillEntryDTO;
import com.pdms.account.dto.BillEntryItemsDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PaymentDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
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
public class BillEntryDAOImpl {
    private static final Logger logger = Logger.getLogger(BillEntryDAOImpl.class);

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
    public BillEntryDAOImpl(){
        
    }
    
    public int insertBillDaEntry(final BillTempDTO billObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO bill_entry  ");
                        sb.append("(billId,poNumber,supplierName,paymentTerm,billNo,billDate,dcNo,");
                        sb.append("billAmt,lrEnclosed,gstinNo,billReceDate,session_id,bill_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<BillEntryDTO> formObj = billObj.getBillEnDTO();
                        for (BillEntryDTO BDTO : formObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{BDTO.getBillId(),
                                        BDTO.getPoNumber().trim(),
                                        BDTO.getSupplierName().trim(),
                                        BDTO.getPaymentTerm().trim(),
                                        BDTO.getBillNo(),
                                        BDTO.getBillDate().trim(),
                                        BDTO.getDcNo().trim(),
                                        BDTO.getBillAmt(),
                                        BDTO.getLrEnclosed().trim(),
                                        BDTO.getGstinNo().trim(),
                                        BDTO.getBillReceDate().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO bill_entry_items  ");
                        sbItem.append("(billEnItemId,poNumBer,item_code,rate,rcdQty,gbys,gstPer,igst,");
                        sbItem.append("sgst,cgst,gst,hsnCode,session_id,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<BillEntryItemsDTO> itemsObj = billObj.getBillItemsDTO();
                        for (BillEntryItemsDTO iteObj : itemsObj) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{iteObj.getBillEnItemId(),
                                        iteObj.getPoNumBer(),
                                        iteObj.getItems().trim(),
                                        iteObj.getRate(),
                                        iteObj.getRcdQty(),
                                        iteObj.getGbys().trim(),
                                        iteObj.getGstPer(),
                                        iteObj.getIgst(),
                                        iteObj.getSgst(),
                                        iteObj.getCgst(),
                                        iteObj.getGst(),
                                        iteObj.getHsnCode().trim(),
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
                        logger.error("Exception Occured In:insertMaterialReceipt");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertMaterialReceipt:: " + e.getMessage());
        }
    }

    public List<BillEntryDTO> getBillEntryRecord() throws AppException {
        List<BillEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bill_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BillEntryDTO BTO = new BillEntryDTO();
                    BTO.setBillId((Long) (retMap.get("billId")));
                    BTO.setPoNumber((String) (retMap.get("poNumber")));
                    BTO.setSupplierName((String) (retMap.get("supplierName")));
                    BTO.setPaymentTerm((String) (retMap.get("paymentTerm")));
                    BTO.setBillNo((long) (retMap.get("billNo")));  
                    BTO.setBillDate((String) (retMap.get("billDate")));
                    BTO.setDcNo((String) (retMap.get("dcNo")));
                    BTO.setBillAmt((BigDecimal) (retMap.get("billAmt")));
                    BTO.setLrEnclosed((String) (retMap.get("lrEnclosed")));
                    BTO.setGstinNo((String) (retMap.get("gstinNo")));
                    list.add(BTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBillEntryRecord:", e);
        }
        return list;
    }
    
    public List<PoEntryDTO> getVenderNaFrPoEn() throws AppException {
        List<PoEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT venderName FROM po_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PoEntryDTO poDto = new PoEntryDTO();                    
                    poDto.setVenderName((String) (retMap.get("venderName")));                    
                    list.add(poDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVenderNaFrPoEn:", e);
        }
        return list;
    }
    
    public List<PaymentDTO> getPaymentTerms() throws AppException {
        List<PaymentDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT paymentCode, paymentName, paymentDes FROM payment_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PaymentDTO pyDto = new PaymentDTO();                    
                    pyDto.setPaymentCode((String) (retMap.get("paymentCode"))); 
                    pyDto.setPaymentName((String) (retMap.get("paymentName"))); 
                    pyDto.setPaymentDes((String) (retMap.get("paymentDes"))); 
                    list.add(pyDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPaymentTerms:", e);
        }
        return list;
    }
    
    public List<VendorDTO> getGstinNumberFrVen() throws AppException {
        List<VendorDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT gstinNumber FROM vendor_details ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO veDto = new VendorDTO();                    
                    veDto.setGstinNumber((String) (retMap.get("gstinNumber")));                     
                    list.add(veDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getGstinNumberFrVen:", e);
        }
        return list;
    }
    
    public List<BillEntryDTO> getBillEntryNumRe() throws AppException {
        List<BillEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT billId,billNo,billDate FROM bill_entry ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BillEntryDTO bill = new BillEntryDTO();
                    bill.setBillId((Long) (retMap.get("billId")));                    
                    bill.setBillNo((long) (retMap.get("billNo")));  
                    bill.setBillDate((String) (retMap.get("billDate")));                    
                    list.add(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBillEntryNumRe:", e);
        }
        return list;
    }
    
    public List<BillEntryDTO> getBillEnEditLi(final long billID) throws AppException {
        List<BillEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bill_entry WHERE billId ="+billID);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BillEntryDTO biDto = new BillEntryDTO();    
                    biDto.setBillId((Long) (retMap.get("billId")));
                    biDto.setPoNumber((String) (retMap.get("poNumber")));  
                    biDto.setSupplierName((String) (retMap.get("supplierName")));
                    biDto.setPaymentTerm((String) (retMap.get("paymentTerm")));
                    biDto.setBillNo((Long) (retMap.get("billNo")));
                    biDto.setBillDate((String) (retMap.get("billDate")));
                    biDto.setDcNo((String) (retMap.get("dcNo")));  
                    biDto.setBillAmt((BigDecimal) (retMap.get("billAmt")));
                    biDto.setLrEnclosed((String) (retMap.get("lrEnclosed")));
                    biDto.setGstinNo((String) (retMap.get("gstinNo")));                    
                    list.add(biDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBillEnEditLi:", e);
        }
        return list;
    }
    
    public int updateBillEntry(final List<BillEntryDTO> billObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE bill_entry  ");
            sb.append(" SET poNumber=?,supplierName=?,paymentTerm=?,billNo=?,billDate=?,dcNo=?,billAmt=?,lrEnclosed=?,gstinNo=?,session_id=?,bill_date=? ");            
            sb.append(" WHERE billId=? ");
            
            for(BillEntryDTO bDto : billObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{bDto.getPoNumber().trim(),
                        bDto.getSupplierName().trim(),
                        bDto.getPaymentTerm().trim(),
                        bDto.getBillNo(),
                        bDto.getBillDate().trim(),
                        bDto.getDcNo().trim(),
                        bDto.getBillAmt(),
                        bDto.getLrEnclosed().trim(),
                        bDto.getGstinNo().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        bDto.getBillId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateBillEntry:",e);
        }
        return retVal;
    }
    
    public List<BillEntryDTO> getBillEntryReByPoNo(final long poNum) throws AppException {
        List<BillEntryDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bill_entry WHERE poNumber ="+poNum);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    BillEntryDTO BTO = new BillEntryDTO();
                    BTO.setBillId((Long) (retMap.get("billId")));
                    BTO.setPoNumber((String) (retMap.get("poNumber")));
                    BTO.setSupplierName((String) (retMap.get("supplierName")));
                    BTO.setPaymentTerm((String) (retMap.get("paymentTerm")));
                    BTO.setBillNo((long) (retMap.get("billNo")));  
                    BTO.setBillDate((String) (retMap.get("billDate")));
                    BTO.setDcNo((String) (retMap.get("dcNo")));
                    BTO.setBillAmt((BigDecimal) (retMap.get("billAmt")));
                    BTO.setLrEnclosed((String) (retMap.get("lrEnclosed")));
                    BTO.setGstinNo((String) (retMap.get("gstinNo")));
                    list.add(BTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBillEntryReByPoNo:", e);
        }
        return list;
    }
    

    
    public List<PoDeItemsDTO> getPoDeItemsByPoNumber(final long poNum) throws AppException
    {
         List<PoDeItemsDTO> list = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT a.poDeItemsId,a.fpoNumber,a.itemCode,a.quantity,a.unit, ");
            sb.append("a.rate,a.gst,a.pfCharges,a.discount,a.indentslno,a.frieght,a.salesTax,a.total,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name,i.description ");
            sb.append(" FROM po_entry_items_details a ");
            sb.append(" JOIN unit_master u ON a.unit = u.unit_code");
            sb.append(" JOIN item_master i ON a.itemCode = i.item_code");
            sb.append(" WHERE fpoNumber =? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
            new Object[]{poNum});
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {                    
                    PoDeItemsDTO itemObj = new PoDeItemsDTO();
                    itemObj.setPoDeItemsId((Long) (retMap.get("poDeItemsId")));
                    itemObj.setPoNum((Long) (retMap.get("fpoNumber")));
                    itemObj.setItemCode((String) (retMap.get("itemCode")));
                    itemObj.setQuantity((String) (retMap.get("quantity")));
                    itemObj.setUnit((String) (retMap.get("unit")));  
                    itemObj.setRate((BigDecimal) (retMap.get("rate")));
                    itemObj.setGst((String) (retMap.get("gst")));    
                    itemObj.setPfCharges((BigDecimal) (retMap.get("pfCharges")));  
                    itemObj.setDiscount((BigDecimal) (retMap.get("discount")));  
                    itemObj.setIndentslno((Long) (retMap.get("indentslno"))); 
                    itemObj.setFrieght((BigDecimal) (retMap.get("frieght")));
                    itemObj.setSalesTax((String) (retMap.get("salesTax")));
                    itemObj.setTotal((BigDecimal) (retMap.get("total")));
                    itemObj.getUnitObj().setUnitName((String)(retMap.get("unit_name")));  
                    itemObj.getItemObj().setItemName((String)(retMap.get("item_name")));
                    itemObj.getItemObj().setDescription((String)(retMap.get("description")));
                    list.add(itemObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoDeItemsByPoNumber:",e);
        }
        return list;
    }
    
    public List<ItemDTO> getItemMaDeByItemCode(final long itemCode) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_item_id,fk_unit_id,item_name,item_code,description ");
            sb.append(" FROM item_master WHERE item_code ="+itemCode);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ItemDTO iObj = new ItemDTO();
                    iObj.setItemID((Long) (retMap.get("pk_item_id")));   
                    iObj.getUnitDTO().setUnitID((Long) (retMap.get("fk_unit_id"))); 
                    iObj.setItemName((String) (retMap.get("item_name"))); 
                    iObj.setItemCode((String) (retMap.get("item_code"))); 
                    iObj.setDescription((String) (retMap.get("description"))); 
                    itemLi.add(iObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemMaDeByItemCode:", e);
        }
        return itemLi;
    }
    
    public List<UnitDTO> getUnitDeByUnitCode(final long unitCode) throws AppException {
        List<UnitDTO> unitLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_unit_id,unit_name,unit_code,description ");
            sb.append(" FROM unit_master WHERE unit_code ="+unitCode);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    UnitDTO uObj = new UnitDTO();
                    uObj.setUnitID((Long) (retMap.get("pk_unit_id")));   
                    uObj.setUnitName((String) (retMap.get("unit_name"))); 
                    uObj.setUnitCode((String) (retMap.get("unit_code"))); 
                    uObj.setUnitDescription((String) (retMap.get("description"))); 
                    unitLi.add(uObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUnitDeByUnitCode:", e);
        }
        return unitLi;
    }
    
    public int insertBillEntryItemsData(final List<BillEntryItemsDTO> itemsObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO bill_entry_items  ");
            sb.append("(billEnItemId,poNumBer,item_code,rate,rcdQty,gbys,gstPer,igst,");            
            sb.append("sgst,cgst,gst,hsnCode,session_id,cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            
            for(BillEntryItemsDTO iteObj : itemsObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{iteObj.getBillEnItemId(),
                            iteObj.getPoNumBer(),
                            iteObj.getItems().trim(),
                            iteObj.getRate(),
                            iteObj.getRcdQty(),
                            iteObj.getGbys().trim(),
                            iteObj.getGstPer(),
                            iteObj.getIgst(),
                            iteObj.getSgst(),
                            iteObj.getCgst(),
                            iteObj.getGst(),    
                            iteObj.getHsnCode().trim(),
                            sessUserID,
                            dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertBillEntryItemsData:",e);
        }
        return retVal;
    }
    
    public List<BillEntryItemsDTO> getBillItemsListByPoNo(final long poNumBer) throws AppException {        
        List<BillEntryItemsDTO> secLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT poNumBer,item_code,rate,rcdQty,gbys,igst,sgst,cgst,gst,hsnCode  ");
            sb.append(" FROM bill_entry_items WHERE poNumBer=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{poNumBer});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    BillEntryItemsDTO itemsObj = new BillEntryItemsDTO();
                    itemsObj.setPoNumBer((Long)(retMap.get("poNumBer")));
                    itemsObj.setItems((String) (retMap.get("item_code")));
                    itemsObj.setRate((BigDecimal) (retMap.get("rate")));
                    itemsObj.setRcdQty((Long) (retMap.get("rcdQty")));
                    itemsObj.setGbys((String) (retMap.get("gbys")));
                    itemsObj.setIgst((BigDecimal) (retMap.get("igst")));
                    itemsObj.setSgst((BigDecimal) (retMap.get("sgst")));
                    itemsObj.setCgst((BigDecimal) (retMap.get("cgst")));
                    itemsObj.setGst((BigDecimal) (retMap.get("gst")));
                    itemsObj.setHsnCode((String) (retMap.get("hsnCode")));
                    secLi.add(itemsObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBillItemsListByPoNo:", e);
        }

        return secLi;
    }
    
    
    public List<ItemDTO> getItemMaDeByCategotyId(final long category_id) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_item_id,item_name,item_code,description ");
            sb.append(" FROM item_master WHERE fk_category_id=?");

                   
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{category_id});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ItemDTO iObj = new ItemDTO();
                    iObj.setItemID((Long) (retMap.get("pk_item_id")));
                    iObj.setItemName((String) (retMap.get("item_name"))); 
                    iObj.setItemCode((String) (retMap.get("item_code"))); 
                    iObj.setDescription((String) (retMap.get("description"))); 
                    itemLi.add(iObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemMaDeByCategotyId:", e);
        }
        return itemLi;
    }
}
