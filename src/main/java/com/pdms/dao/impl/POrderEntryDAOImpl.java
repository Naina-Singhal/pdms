/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.dto.DesignationDTO;
import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.POrderEntryDTO;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.PoReleaseItemsDTO;
import com.pdms.itemsDto.receipt.PoReleaseTempDTO;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
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
 * @author myassessment
 */
@Repository
public class POrderEntryDAOImpl {
    
    private static final Logger logger = Logger.getLogger(PoEntryDAOImpl.class);

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
    public POrderEntryDAOImpl(){
        
    }
    
    public int insertPOrderEnDetail(final PoReleaseTempDTO poedObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;                    
                    try {

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO po_entry_details  ");
                        sb.append("(pOrderEntryID,poReleaseType,poNumber,date,poDate,scheDelDate,");
                        sb.append("indentRefNo,indentor,section,supplier,itemsCovered,");
                        sb.append("remarks,poValue,poValPaid,currencyType,currencyCode,");
                        sb.append("headOfAccount,placeOfDel,paymentTerms,payingAuthority,sess_user_id,po_entry_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<POrderEntryDTO> pord = poedObj.getPoReleaseDTO();
                        for (POrderEntryDTO pod : pord) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{pod.getpOrderEntryID(),
                                        pod.getPoReleaseType().trim(),
                                        pod.getPoNumber(),
                                        pod.getDate().trim(),
                                        pod.getPoDate().trim(),
                                        pod.getScheDelDate().trim(),
                                        pod.getIndentRefNo().trim(),
                                        pod.getIndentor().trim(),
                                        pod.getSection().trim(),
                                        pod.getSupplier().trim(),
                                        pod.getItemsCovered().trim(),
                                        pod.getRemarks().trim(),
                                        pod.getPoValue().trim(),
                                        pod.getPoValPaid().trim(),
                                        pod.getCurrencyType().trim(),
                                        pod.getCurrencyCode().trim(),
                                        pod.getHeadOfAccount().trim(),
                                        pod.getPlaceOfDel().trim(),
                                        pod.getPaymentTerms().trim(),
                                        pod.getPayingAuthority().trim(),
                                        // ApplicationConstants.ACTIVE_FLAG_VALUE,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()

                                    });
                        }
                        
                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO po_release_items  ");
                        sbItem.append("(poReleaseItemsId,poNumBer,code,groupCode,storeCardNo,itemDes,");
                        sbItem.append(" qtyOrder,unitQty,lumpsum,pAndf,excise,cst,");
                        sbItem.append(" surcharge,turnOver,");
                        sbItem.append("service,itemYetReq,poValue,discount,userid,cur_date)  ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                        List<PoReleaseItemsDTO> items = poedObj.getPoItemsDTO();
                        for (PoReleaseItemsDTO itemObj : items) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{itemObj.getPoReleaseItemsId(),
                                        itemObj.getPoNumBer(),
                                        itemObj.getCode().trim(),
                                        itemObj.getGroupCode().trim(),
                                        itemObj.getStoreCardNo().trim(),
                                        itemObj.getItemDes().trim(),
                                        itemObj.getQtyOrder().trim(),
                                        itemObj.getUnitQty().trim(),
                                        itemObj.getLumpsum().trim(),
                                        itemObj.getpAndf(),
                                        itemObj.getExcise().trim(),
                                        itemObj.getCst().trim(),
                                        itemObj.getSurcharge(),
                                        itemObj.getTurnOver().trim(),
                                        itemObj.getService().trim(),
                                        itemObj.getItemYetReq().trim(),
                                        itemObj.getPoValue().trim(),
                                        itemObj.getDiscount(),
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
                        logger.error("Exception Occured In:insertPOrderEnDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertPOrderEnDetail:: " + e.getMessage());
        }
    }
    
    public List<POrderEntryDTO> getPOrderEnRecord() throws AppException
    {
        List<POrderEntryDTO> PoRecord = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM po_entry_details ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    POrderEntryDTO POED = new POrderEntryDTO();                     
                    POED.setpOrderEntryID((Long)(resultMap.get("pOrderEntryID")));
                    POED.setPoNumber((long)(resultMap.get("poNumber")));                   
                    POED.setDate((String)(resultMap.get("date")));
                    POED.setPoDate((String)(resultMap.get("poDate")));
                    POED.setScheDelDate((String)(resultMap.get("scheDelDate")));                    
                    POED.setIndentRefNo((String)(resultMap.get("indentRefNo")));                    
                    POED.setIndentor((String)(resultMap.get("indentor")));
                    POED.setSection((String)(resultMap.get("section")));
                    POED.setSupplier((String)(resultMap.get("supplier")));
                    POED.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    POED.setRemarks((String)(resultMap.get("remarks")));
                    POED.setPoValue((String)(resultMap.get("poValue")));
                    POED.setPoValPaid((String)(resultMap.get("poValPaid")));                    
                    POED.setCurrencyType((String)(resultMap.get("currencyType")));
                    POED.setCurrencyCode((String)(resultMap.get("currencyCode")));
                    POED.setHeadOfAccount((String)(resultMap.get("headOfAccount")));
                    POED.setPlaceOfDel((String)(resultMap.get("placeOfDel")));
                    POED.setPaymentTerms((String)(resultMap.get("paymentTerms")));
                    POED.setPayingAuthority((String)(resultMap.get("payingAuthority")));
                    POED.setEncFieldValue
                            (encryptDecrypt.encrypt(POED.getpOrderEntryID()+""));
                    
                    PoRecord.add(POED);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPOrderEnRecord:",e);
        }
        return PoRecord;
    }
    
    public List<POrderEntryDTO> getPoNoFromPoEn(String fromDate, String toDate) throws AppException
    {
        List<POrderEntryDTO> PoOrEntryLi = new LinkedList<>();
        try
        {
            String fromDateDb = dateUtil.dateConvertionFromJSPToDB(fromDate);
            String toDateDb = dateUtil.dateConvertionFromJSPToDB(toDate);
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT poNumber,poValue ");
            sb.append(" FROM po_entry WHERE (preparedDate BETWEEN '"+fromDateDb+"' AND '"+toDateDb+"')");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {   
                    
                    POrderEntryDTO pOrderEntryDTO = new POrderEntryDTO();
                    pOrderEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    pOrderEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    //pOrderEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    PoOrEntryLi.add(pOrderEntryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoNoFromPoEn:",e);
        }
        return PoOrEntryLi;
    }
    
    
    public List<PlaceOfDeliveryDTO> getPlaceOfDelivery() throws AppException
    {
        List<PlaceOfDeliveryDTO> placeOfDelLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM place_of_delivery_master ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                    PlaceOfDeliveryDTO placeOfDeliveryDTO = new PlaceOfDeliveryDTO();                    
                    placeOfDeliveryDTO.setPlaceOfDeliveryID((Long)(resultMap.get("pk_place_of_delivery_id")));
                    placeOfDeliveryDTO.setPlaceOfDelivery((String)(resultMap.get("place_of_delivery")));
                    placeOfDeliveryDTO.setDescription((String)(resultMap.get("description")));
                    placeOfDelLi.add(placeOfDeliveryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPlaceOfDelivery:",e);
        }
        return placeOfDelLi;
    }
    
    
    public List<SectionDTO> getSectionName() throws AppException
    {
        List<SectionDTO> SectionLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT section_code,section_name ");
            sb.append(" FROM section_master ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                    SectionDTO sectionDTO = new SectionDTO();
                    sectionDTO.setSectionCode((String)(resultMap.get("section_code")));
                    sectionDTO.setSectionName((String)(resultMap.get("section_name")));                    
                    SectionLi.add(sectionDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSectionName:",e);
        }
        return SectionLi;
    }
    
    public List<DesignationDTO> getDesignationName() throws AppException
    {
        List<DesignationDTO> DesignationLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT designation_code,designation_name ");
            sb.append(" FROM designation_master ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                
                    DesignationDTO designationDTO = new DesignationDTO();
                    designationDTO.setDesignationCode((String)(resultMap.get("designation_code")));
                    designationDTO.setDesignationName((String)(resultMap.get("designation_name")));                                     
                    DesignationLi.add(designationDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getDesignationName:",e);
        }
        return DesignationLi;
    }
    
    public List<HeadOfAccountDTO> getHeadOfAccount() throws AppException
    {
        List<HeadOfAccountDTO> HeadOfAcLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT account_type,account_code ");
            sb.append(" FROM head_of_account_master ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                
                    HeadOfAccountDTO headOfAccountDTO = new HeadOfAccountDTO();
                    headOfAccountDTO.setAccountType((String)(resultMap.get("account_type")));
                    headOfAccountDTO.setAccountCode((String)(resultMap.get("account_code")));                                     
                    HeadOfAcLi.add(headOfAccountDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getHeadOfAccount:",e);
        }
        return HeadOfAcLi;
    }
    
    public List<VendorDTO> getVendorNames() throws AppException
    {
        List<VendorDTO> vendor_name = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT vendor_code,vendor_name ");
            sb.append(" FROM vendor_details ");
            
            List<Map<String, Object>> resultVenList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultVenList.isEmpty())
            {
                for (Map<String, Object> resultMap1 : resultVenList) { 
                    VendorDTO vendorDTO = new VendorDTO();
                    vendorDTO.setVendorCode((String)(resultMap1.get("vendor_code")));
                    vendorDTO.setVendorName((String)(resultMap1.get("vendor_name")));
                    vendor_name.add(vendorDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVendorNames:",e);
        }
        return vendor_name;
    }
    
    public List<POrderEntryDTO> getPoNoOnlyFromPoEn() throws AppException
    {
        List<POrderEntryDTO> PoOrEntryLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT poNumber,poValue ");
            sb.append(" FROM po_entry ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {   
                    
                    POrderEntryDTO pOrderEntryDTO = new POrderEntryDTO();
                    pOrderEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    pOrderEntryDTO.setPoValue((String)(resultMap.get("poValue")));                    
                    PoOrEntryLi.add(pOrderEntryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoNoOnlyFromPoEn:",e);
        }
        return PoOrEntryLi;
    }
    
    public List<POrderEntryDTO> getPOrderEnDeByPoNo(final long poNumBer) throws AppException
    {
        List<POrderEntryDTO> PoRecord = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM po_entry_details WHERE poNumber ="+poNumBer);
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    POrderEntryDTO POED = new POrderEntryDTO();                     
                    POED.setpOrderEntryID((Long)(resultMap.get("pOrderEntryID")));
                    POED.setPoReleaseType((String)(resultMap.get("poReleaseType")));
                    POED.setPoNumber((long)(resultMap.get("poNumber")));                   
                    POED.setDate((String)(resultMap.get("date")));
                    POED.setPoDate((String)(resultMap.get("poDate")));
                    POED.setScheDelDate((String)(resultMap.get("scheDelDate")));                    
                    POED.setIndentRefNo((String)(resultMap.get("indentRefNo")));                    
                    POED.setIndentor((String)(resultMap.get("indentor")));
                    POED.setSection((String)(resultMap.get("section")));
                    POED.setSupplier((String)(resultMap.get("supplier")));
                    POED.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    POED.setRemarks((String)(resultMap.get("remarks")));
                    POED.setPoValue((String)(resultMap.get("poValue")));
                    POED.setPoValPaid((String)(resultMap.get("poValPaid")));                    
                    POED.setCurrencyType((String)(resultMap.get("currencyType")));
                    POED.setCurrencyCode((String)(resultMap.get("currencyCode")));
                    POED.setHeadOfAccount((String)(resultMap.get("headOfAccount")));
                    POED.setPlaceOfDel((String)(resultMap.get("placeOfDel")));
                    POED.setPaymentTerms((String)(resultMap.get("paymentTerms")));
                    POED.setPayingAuthority((String)(resultMap.get("payingAuthority")));
                    POED.setEncFieldValue
                            (encryptDecrypt.encrypt(POED.getpOrderEntryID()+""));
                    PoRecord.add(POED);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPOrderEnDeByPoNo:",e);
        }
        return PoRecord;
    }
    
    public List<POrderEntryDTO> getPoReleaseReById(final long id) throws AppException {
        List<POrderEntryDTO> poDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM po_entry_details WHERE pOrderEntryID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    POrderEntryDTO POED = new POrderEntryDTO();                     
                    POED.setpOrderEntryID((Long)(resultMap.get("pOrderEntryID")));
                    POED.setPoReleaseType((String)(resultMap.get("poReleaseType")));
                    POED.setPoNumber((long)(resultMap.get("poNumber")));                   
                    POED.setDate((String)(resultMap.get("date")));
                    POED.setPoDate((String)(resultMap.get("poDate")));
                    POED.setScheDelDate((String)(resultMap.get("scheDelDate")));                    
                    POED.setIndentRefNo((String)(resultMap.get("indentRefNo")));                    
                    POED.setIndentor((String)(resultMap.get("indentor")));
                    POED.setSection((String)(resultMap.get("section")));
                    POED.setSupplier((String)(resultMap.get("supplier")));
                    POED.setItemsCovered((String)(resultMap.get("itemsCovered")));
                    POED.setRemarks((String)(resultMap.get("remarks")));
                    POED.setPoValue((String)(resultMap.get("poValue")));
                    POED.setPoValPaid((String)(resultMap.get("poValPaid")));                    
                    POED.setCurrencyType((String)(resultMap.get("currencyType")));
                    POED.setCurrencyCode((String)(resultMap.get("currencyCode")));
                    POED.setHeadOfAccount((String)(resultMap.get("headOfAccount")));
                    POED.setPlaceOfDel((String)(resultMap.get("placeOfDel")));
                    POED.setPaymentTerms((String)(resultMap.get("paymentTerms")));
                    POED.setPayingAuthority((String)(resultMap.get("payingAuthority")));
                    POED.setEncFieldValue
                            (encryptDecrypt.encrypt(POED.getpOrderEntryID()+""));
                    
                    poDTO.add(POED);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoReleaseReById:", e);
        }
        return poDTO;
    }
    
    public int updatePoReleaseDetail(final List<POrderEntryDTO> poedObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE po_entry_details  ");
            sb.append(" SET poReleaseType=?,poNumber=?,date=?,poDate=?,scheDelDate=?,");  
            sb.append("indentRefNo=?,indentor=?, ");
            sb.append("section=?,supplier=?,itemsCovered=?,remarks=?,poValue=?, ");
            sb.append("poValPaid=?,currencyType=?,currencyCode=?,headOfAccount=?,placeOfDel=?, ");
            sb.append("paymentTerms=?,payingAuthority=?,sess_user_id=?,po_entry_date=? ");
            sb.append(" WHERE pOrderEntryID=? ");
            
            for(POrderEntryDTO pod : poedObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ 
                        pod.getPoReleaseType().trim(),
                        pod.getPoNumber(),                        
                        pod.getDate().trim(),
                        pod.getPoDate().trim(),
                        pod.getScheDelDate().trim(),                        
                        pod.getIndentRefNo().trim(),                        
                        pod.getIndentor().trim(),
                        pod.getSection().trim(),
                        pod.getSupplier().trim(),
                        pod.getItemsCovered().trim(),
                        pod.getRemarks().trim(),
                        pod.getPoValue().trim(),
                        pod.getPoValPaid().trim(),                        
                        pod.getCurrencyType().trim(),
                        pod.getCurrencyCode().trim(),                        
                        pod.getHeadOfAccount().trim(),
                        pod.getPlaceOfDel().trim(),
                        pod.getPaymentTerms().trim(),
                        pod.getPayingAuthority().trim(),                           
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        pod.getpOrderEntryID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePoReleaseDetail:",e);
        }
        return retVal;
    }
   
    public List<IndentFormDTO> getAllIndentFormsByIndentId(final long id) throws AppException {
        List<IndentFormDTO> indentLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            //a.fk_item_id,d.item_name,d.item_code,
            sb.append("SELECT a.pk_indent_form_id,a.indent_number,a.received_date,a.estimated_cost,a.indent_date,");
            sb.append(" a.fk_section_id,b.section_code,b.section_name,a.fk_approve_authority_id,c.designation_code,c.designation_name, ");
            sb.append(" a.fk_category_id,e.category_name,e.category_code,a.fk_employee_id,f.ic_number,f.first_name,f.last_name, ");
            sb.append(" a.fk_place_of_delivery_id,g.place_of_delivery,a.fk_mode_of_purchase_id,a.fk_head_of_account_id, ");
            sb.append(" h.mode_of_purchase,i.account_type,a.description_details,a.mta,a.brief_description, ");
            sb.append(" a.other_information,a.fk_signing_authority_id,j.designation_code,j.designation_name, ");
            sb.append(" a.status_id,k.lookup_name indent_status,l.file_no,m.group_name,a.indent_type_ctq,a.indent_type_prop,a.comments ");
            sb.append(" FROM indent_form a ");
            sb.append(" JOIN category_master e ON a.fk_category_id = e.pk_category_id ");
            sb.append(" JOIN employee_profile f ON a.fk_employee_id = f.fk_employee_id ");
            sb.append(" JOIN place_of_delivery_master g ON a.fk_place_of_delivery_id = g.pk_place_of_delivery_id ");
            sb.append(" JOIN head_of_account_master i ON i.pk_head_of_account_id = a.fk_head_of_account_id ");
            sb.append(" JOIN master_lookup k ON k.lookup_id = a.status_id ");
            sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" JOIN employee_type_master etm ON f.fk_employee_type_id = etm.pk_employee_type_id ");
            //sb.append(" LEFT JOIN item_master dd ON a.fk_category_id = dd.fk_category_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" WHERE a.pk_indent_form_id = ? ");
            //sb.append(subQuery);
            
            //below one is example query for using WHERE clause
            //WHERE etm.employee_type_name IN  ('Stores Admin','Stores User') AND (k.lookup_name = 'Created' OR k.lookup_name = 'Modified' OR k.lookup_name = 'Reverted') 
            
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{id});

            if (!resList.isEmpty()) {
                //encryptDecrypt.setApplicationKey(sessionKey);

                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO indentObj = new IndentFormDTO();

                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    if (retMap.get("received_date") != null) {
                        String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                        indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
                    } else {
                        indentObj.setRecevidedDate("");
                    }
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("indent_date")));
                    indentObj.setIndentDate((dateUtil.dateConvertionFromDBToJSP(indDate)));
                    indentObj.setEstimatedCost(((Double) (retMap.get("estimated_cost"))) + "");
                    indentObj.setSectionId((Long) (retMap.get("fk_section_id")));

                    indentObj.getSectionObj().setSectionID((Long) (retMap.get("fk_section_id")));
                    indentObj.getSectionObj().setSectionCode((String) (retMap.get("section_code")));
                    indentObj.getSectionObj().setSectionName((String) (retMap.get("section_name")));

                    indentObj.setApproveAuthorityId((Long) (retMap.get("fk_approve_authority_id")));
                    indentObj.getApprovingAuthorityObj().
                            setDesignationID((Long) (retMap.get("fk_approve_authority_id")));
                    indentObj.getApprovingAuthorityObj().setDesignationCode((String) (retMap.get("designation_code")));
                    indentObj.getApprovingAuthorityObj().setDesignationName((String) (retMap.get("designation_name")));


                    
                    indentObj.getCategoryObj().setCategoryID((Long) (retMap.get("fk_category_id")));
                    indentObj.getCategoryObj().setCategoryCode((String) (retMap.get("category_code")));
                    indentObj.getCategoryObj().setCategoryName((String) (retMap.get("category_name")));
                    
//                  indentObj.setItemId((Long) (retMap.get("fk_item_id")));
//                    indentObj.getItemObj().setItemID((Long) (retMap.get("pk_item_id")));
//                    indentObj.getItemObj().setItemName((String) (retMap.get("item_name")));
//                    indentObj.getItemObj().setItemCode((String) (retMap.get("item_code")));

                    indentObj.setEmployeeProfileId((Long) (retMap.get("fk_employee_id")));
                    indentObj.getEmpProfileDTo().setEmployeeProfileID((Long) (retMap.get("fk_employee_id")));
                    indentObj.getEmpProfileDTo().setIcNumber((String) (retMap.get("ic_number")));
                    indentObj.getEmpProfileDTo().setFirstName((String) (retMap.get("first_name")));
                    indentObj.getEmpProfileDTo().setLastName((String) (retMap.get("last_name")));

                    indentObj.setPlaceOfDeliveryId((Long) (retMap.get("fk_place_of_delivery_id")));
                    indentObj.getPodObj().setPlaceOfDeliveryID((Long) (retMap.get("fk_place_of_delivery_id")));
                    indentObj.getPodObj().setPlaceOfDelivery((String) (retMap.get("place_of_delivery")));

                    indentObj.setModeOfPurchaseId((Long) (retMap.get("fk_mode_of_purchase_id")));
                    indentObj.getMopObj().setModeOfPurchaseID((Long) (retMap.get("fk_mode_of_purchase_id")));
                    indentObj.getMopObj().setModeOfPurchase((String) (retMap.get("mode_of_purchase")));

                    indentObj.setHeadOfAccountId((Long) (retMap.get("fk_head_of_account_id")));
                    indentObj.getHoaObj().setHeadOfAccountID((Long) (retMap.get("fk_head_of_account_id")));
                    indentObj.getHoaObj().setAccountType((String) (retMap.get("account_type")));

                    indentObj.setDescriptionDetails((String) (retMap.get("description_details")));
                    indentObj.setMta((String) (retMap.get("mta")));
                    indentObj.setBriefDescription((String) (retMap.get("brief_description")));
                    indentObj.setIndentTypeCTQ((String) (retMap.get("indent_type_ctq")));
                    indentObj.setIndentTypeProp((String) (retMap.get("indent_type_prop")));
                    indentObj.setOtherInformation((String) (retMap.get("other_information")));

                    indentObj.setSigningAuthorityId((Long) (retMap.get("fk_signing_authority_id")));
                    indentObj.getSigningAuthorityObj().setDesignationID((Long) (retMap.get("fk_signing_authority_id")));
                    indentObj.getSigningAuthorityObj().setDesignationCode((String) (retMap.get("designation_code")));
                    indentObj.getSigningAuthorityObj().setDesignationName((String) (retMap.get("designation_name")));

                    indentObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    indentObj.setIndentStatus((String) (retMap.get("indent_status")));

                    if (retMap.get("file_no") != null) {
                        indentObj.setFileNo(((Long) (retMap.get("file_no"))) + "");
                        indentObj.setMappedGroupName((String) (retMap.get("group_name")));
                    } else {
                        indentObj.setFileNo("");
                        indentObj.setMappedGroupName("");
                    }

                    indentObj.setComments((String) (retMap.get("comments")));

                    indentLi.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllIndentFormsByIndentId:", e);
        }
        return indentLi;
    }
    
    
    public List<PoReleaseItemsDTO> getPoReleaseItemsReByPo(final long pono) throws AppException {
        List<PoReleaseItemsDTO> poItemsList = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            //sb.append(" SELECT * FROM po_release_items WHERE poNumBer=? ");
            sb.append(" SELECT a.poReleaseItemsId,a.poNumBer,a.code,a.groupCode,a.storeCardNo, ");
            sb.append("a.itemDes,a.qtyOrder,a.unitQty,");
            sb.append("u.unit_name, ");
            sb.append("i.item_name ");
            sb.append(" FROM po_release_items a ");
            sb.append(" JOIN unit_master u ON a.unitQty = u.unit_code");
            sb.append(" JOIN item_master i ON a.itemDes = i.item_code");
            sb.append(" WHERE poNumBer =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{pono});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    PoReleaseItemsDTO poItems = new PoReleaseItemsDTO();                     
                    poItems.setPoReleaseItemsId((Long)(resultMap.get("poReleaseItemsId")));
                    poItems.setPoNumBer((Long)(resultMap.get("poNumBer")));
                    poItems.setCode((String)(resultMap.get("code")));                   
                    poItems.setGroupCode((String)(resultMap.get("groupCode")));
                    poItems.setStoreCardNo((String)(resultMap.get("storeCardNo")));
                    poItems.setItemDes((String)(resultMap.get("itemDes")));                    
                    poItems.setQtyOrder((String)(resultMap.get("qtyOrder")));                    
                    poItems.setUnitQty((String)(resultMap.get("unitQty")));
                    poItems.getUnitObj().setUnitName((String)(resultMap.get("unit_name")));  
                    poItems.getItemObj().setItemName((String)(resultMap.get("item_name")));
                    poItemsList.add(poItems);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoReleaseItemsReByPo:", e);
        }
        return poItemsList;
    }
    
}
