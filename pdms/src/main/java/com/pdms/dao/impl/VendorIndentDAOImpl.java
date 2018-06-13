/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.account.service.impl.BillEntryServiceImpl;
import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.service.impl.MasterLookUpServiceImpl;
import com.pdms.service.impl.VendorServiceImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class VendorIndentDAOImpl {

    private static final Logger logger = Logger.getLogger(VendorIndentDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private MasterLookUpServiceImpl masterService;   
    
    @Autowired
    private VendorServiceImpl venSerImpl;
    

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public VendorIndentDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<IndentFormDTO> getAllVendorIndentForms(final long sessionUserID,
            final long signDesigID)
            throws AppException {
        List<IndentFormDTO> indentLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_indent_form_id,a.indent_number,a.received_date,a.estimated_cost,a.indent_date,");
            sb.append(" a.fk_section_id,b.section_code,b.section_name,a.fk_approve_authority_id,c.designation_code,c.designation_name, ");
            sb.append(" a.fk_category_id,e.category_name,e.category_code,a.fk_employee_id,f.first_name,f.last_name, ");
            sb.append(" a.fk_place_of_delivery_id,g.place_of_delivery,a.fk_mode_of_purchase_id,a.fk_head_of_account_id, ");
            sb.append(" h.mode_of_purchase,i.account_type,a.description_details,a.mta,a.brief_description, ");
            sb.append(" a.other_information,a.fk_signing_authority_id,j.designation_code,j.designation_name, ");
            sb.append(" a.status_id,k.lookup_name indent_status,l.file_no,m.group_name,a.indent_type_ctq,a.indent_type_prop ");
            sb.append(" FROM indent_form a ");
            sb.append(" JOIN category_master e ON a.fk_category_id = e.pk_category_id ");
            sb.append(" JOIN employee_profile f ON a.fk_employee_id = f.fk_employee_id ");
            sb.append(" JOIN place_of_delivery_master g ON a.fk_place_of_delivery_id = g.pk_place_of_delivery_id ");
            sb.append(" JOIN head_of_account_master i ON i.pk_head_of_account_id = a.fk_head_of_account_id ");
            sb.append(" JOIN master_lookup k ON k.lookup_id = a.status_id ");
            sb.append(" LEFT JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" WHERE (k.lookup_name=? ) AND ");
            sb.append("  ");
            //a.fk_section_id=? AND OR k.lookup_name=? OR k.lookup_name=?
            String signAuthority="";
            if(signDesigID <= 0)
            {
                signAuthority = "a.fk_signing_authority_id = a.fk_signing_authority_id";
            }
            else
            {
                signAuthority = "a.fk_signing_authority_id ="+signDesigID+"";
            }
            
            sb.append(signAuthority );
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{
                        ApplicationConstants.INDENT_STATUS_MAPPED
                    });

            if (!resList.isEmpty()) {

                //encryptDecrypt.setApplicationKey(sessionKey);
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO indentObj = new IndentFormDTO();

                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                    indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
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

                    indentObj.setEmployeeProfileId((Long) (retMap.get("fk_employee_id")));
                    indentObj.getEmpProfileDTo().setEmployeeProfileID((Long) (retMap.get("fk_employee_id")));
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

                    indentLi.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllApprovingAuthorityIndentForms:", e);
        }
        return indentLi;
    }

    public long checkVendorItemStatus(final long vendorID, final long indentID,
            final long itemID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT COUNT(1) ");
            sb.append(" FROM vendor_indent_item_mapping ");
            sb.append(" WHERE fk_vendor_id=? AND fk_item_id=? AND fk_indent_id=? ");
            sb.append("  ");

            resVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{vendorID, itemID, indentID},
                    Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : checkVendorItemStatus:", e);
        }

        return resVal;
    }
    
    
    public String checkVendorItemCurrentStatus(final long vendorID, final long indentID,
            final long itemID) throws AppException {
        String resVal = "";
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT b.lookup_name ");
            sb.append(" FROM vendor_indent_item_mapping a ");
            sb.append(" JOIN master_lookup b ON a.vendor_item_status_id = b.lookup_id  ");
            sb.append(" WHERE fk_vendor_id=? AND fk_item_id=? AND fk_indent_id=? ");
            sb.append("  ");

            resVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{vendorID, itemID, indentID},
                    String.class);
        } catch (EmptyResultDataAccessException e) {
            return " ";
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : checkVendorItemStatus:", e);
        }

        return resVal;
    }

    public long deleteVendorItems(final long indentID,
            final long itemID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" DELETE ");
            sb.append(" FROM vendor_indent_item_mapping ");
            sb.append(" WHERE fk_item_id=? AND fk_indent_id=? ");
            sb.append("  ");

            resVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{itemID, indentID},
                    Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : deleteVendorItems:", e);
        }

        return resVal;
    }

    public long updateVendorIndentMappedItems(final long vendorID, final long indentID,
            final long itemID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE vendor_indent_item_mapping SET ");
            sb.append(" fk_item_id=0,fk_indent_id=0,fk_category_id=0,vendor_item_status_id=0,sent=0,");
            sb.append(" file_no=0  WHERE fk_vendor_id=? AND fk_item_id=? AND fk_indent_id=? ");

            resVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{vendorID, itemID, indentID});
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateVendorIndentMappedItems:", e);
        }

        return resVal;
    }

    public long getCountOfVendorItemStatus(final String vendorItemStatus, final long vendorId,
            final long itemID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            if (vendorItemStatus.equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_SENT_TO_VENDOR)) {
                sb.append(" SELECT COUNT(1) ");
                sb.append(" FROM vendor_indent_item_mapping ");
                sb.append(" WHERE sent=1 AND fk_vendor_id=? ");
                sb.append(" GROUP BY fk_vendor_id ");
            } else if (vendorItemStatus.equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_RECORDED)) {
                sb.append(" SELECT COUNT(1) ");
                sb.append(" FROM comparative_statement ");
                sb.append(" WHERE  fk_vendor_id=?  ");
                sb.append(" GROUP BY fk_vendor_id ");
            }

            resVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{vendorId},
                    Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCountOfVendorItemStatus:", e);
        }

        return resVal;
    }

    public List<VendorDTO> fetchItemRelatedVendorDetails(final long itemID, final long indent_id) throws AppException {
        List<VendorDTO> vendorLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.vendor_city, ");
            sb.append(" v.registration_type,v.comments,a.fk_indent_id,a.file_no,a.fk_item_id  ");
            sb.append(" FROM vendor_details v ");
            sb.append(" JOIN vendor_item_details vt ON v.pk_vendor_id = vt.fk_vendor_id ");
            sb.append(" LEFT JOIN vendor_indent_item_mapping a ON v.pk_vendor_id = a.fk_vendor_id  ");
            sb.append(" AND a.fk_item_id = vt.fk_item_id AND fk_indent_id='"+indent_id+"' ");
            sb.append(" WHERE v.status_id=3  ");
            sb.append(" AND vt.fk_item_id=? ");
            sb.append(" GROUP BY v.pk_vendor_id ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{itemID});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vendorObj = new VendorDTO();

                    vendorObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vendorObj.setEncVendorID(encryptDecrypt.encrypt(vendorObj.getVendorID() + ""));
                    vendorObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vendorObj.setVendorName((String) (retMap.get("vendor_name")));
                    vendorObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vendorObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vendorObj.setComments((String) (retMap.get("comments")));

                    long sentCount = this.getCountOfVendorItemStatus(ApplicationConstants.INDENT_STATUS_SENT_TO_VENDOR,
                            vendorObj.getVendorID(), itemID);
                    long recCount = this.getCountOfVendorItemStatus(ApplicationConstants.INDENT_STATUS_RECORDED, vendorObj.getVendorID(), itemID);
                    String ven_code = venSerImpl.getVendorCodeById(vendorObj.getVendorID());                    
                    long orderCount = this.getCountOfVendorOrder(ven_code);

                    vendorObj.setSentCount(sentCount);
                    vendorObj.setRecordedCount(recCount);
                    vendorObj.setOrderedCount(orderCount);

                    if (retMap.get("fk_indent_id") != null) {
                        vendorObj.setIndentID((Long) (retMap.get("fk_indent_id")));
                    } else {
                        vendorObj.setIndentID(0);
                    }
                    if (retMap.get("file_no") != null) {
                        vendorObj.setFileNo((Long) (retMap.get("file_no")));
                    } else {
                        vendorObj.setFileNo(0);
                    }
                    
                    if (retMap.get("fk_item_id") != null) {
                        vendorObj.setItemID((Long) (retMap.get("fk_item_id")));
                    } else {
                        vendorObj.setItemID(0);
                    }

                    vendorLi.add(vendorObj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchItemRelatedVendorDetails:", e);
        }
        return vendorLi;
    }

    public long insertVendorIndentItemMappingDetails(final VendorItemsDTO vItemObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        long sentID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_SENT_TO_VENDOR);

                        StringBuilder sb = new StringBuilder();

                        sb.append(" INSERT INTO vendor_indent_item_mapping ");
                        sb.append(" (fk_vendor_id,fk_indent_id,fk_category_id,fk_item_id,status_id,");
                        sb.append(" vendor_item_status_id,created_by,created_on,sent,file_no) ");
                        sb.append(" VALUES ");
                        sb.append(" (?,?,?,?,?,?,?,?,?,?) ");

                        if (!vItemObj.getSelVendorList().isEmpty()) {
                            for (String vendorVals : vItemObj.getSelVendorList()) {                                
                                long vId = Long.parseLong(encryptDecrypt.decrypt(vendorVals));
                                int resVal = 0;
                                long chkValue = checkVendorItemStatus(vId, vItemObj.getItemID(),
                                        vItemObj.getIndentID());
                                if (chkValue <= 0) {
                                    resVal = getJdbcTemplate().update(sb.toString(),
                                            new Object[]{encryptDecrypt.decrypt(vendorVals),
                                                vItemObj.getIndentID(),
                                                vItemObj.getCategoryID(),
                                                vItemObj.getItemID(),
                                                ApplicationConstants.ACTIVE_FLAG_VALUE,
                                                sentID,
                                                sessUserID,
                                                dateUtil.generateDBCurrentDateTime(),
                                                1,
                                                vItemObj.getFileNo()
                                            });
                                } else {
                                    resVal = 1;
                                }
                                if (resVal > 0) {
                                    retVal++;
                                } else {
                                    transactionStatus.setRollbackOnly();
                                    return -1;
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVendorIndentItemMappingDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertVendorIndentItemMappingDetails:", e);
        }

    }

    public List<VendorDTO> fetchAllVendorsForSelectedItem(final long itemID) throws AppException {
        List<VendorDTO> vItemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.vendor_city, ");
            sb.append(" v.registration_type,v.comments,a.fk_indent_id,a.fk_item_id,c.indent_number,");
            sb.append(" c.indent_date, a.sent,a.recorderd,a.ordered,a.pk_vendor_indent_item_mapping_id ");
            sb.append(" FROM vendor_details v ");
            sb.append(" INNER JOIN vendor_indent_item_mapping a ON v.pk_vendor_id = a.fk_vendor_id ");
            sb.append(" INNER JOIN indent_form c ON c.pk_indent_form_id = a.fk_indent_id ");
            sb.append(" WHERE v.status_id=3  AND a.fk_item_id=? ");
            sb.append(" GROUP BY v.pk_vendor_id ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{itemID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    VendorDTO vItemObj = new VendorDTO();

                    vItemObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vItemObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vItemObj.setVendorName((String) (retMap.get("vendor_name")));
                    vItemObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vItemObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vItemObj.setComments((String) (retMap.get("comments")));
                    vItemObj.getIndentFormObj().setIndentFormID((Long) (retMap.get("fk_indent_id")));
                    vItemObj.setItemID((Long) (retMap.get("fk_item_id")));
                    vItemObj.getIndentFormObj().setIndentNumber((String) (retMap.get("indent_number")));
                    String indDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("indent_date")));
                    vItemObj.getIndentFormObj().setIndentDate((dateUtil.dateConvertionFromDBToJSP(indDate)));

                    vItemObj.setSentCount((Long) (retMap.get("sent")));
                    vItemObj.setRecordedCount((Long) (retMap.get("recorderd")));
                    vItemObj.setOrderedCount((Long) (retMap.get("ordered")));
                    
                    String vCurrentStat = checkVendorItemCurrentStatus(vItemObj.getVendorID(),
                                vItemObj.getIndentFormObj().getIndentFormID(),itemID);

                    vItemObj.setRowStatusValue(vCurrentStat);
                    
                    vItemObj.setIndentID((Long)(retMap.get("pk_vendor_indent_item_mapping_id")));

                    vItemLi.add(vItemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchAllVendorsForSelectedItem:", e);
        }
        return vItemLi;
    }

    
    public long updateVendorIndentItemMappingRecordedDetails(final VendorItemsDTO vItemObj, 
            final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        long recordID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_RECORDED);
                        
                        StringBuilder sb = new StringBuilder();
                        sb.append(" UPDATE vendor_indent_item_mapping SET ");
                        sb.append(" vendor_item_status_id=?,recorderd=1,modified_by=?,modified_on=? ");
                        sb.append(" WHERE pk_vendor_indent_item_mapping_id=? ");
                        
                        
                        if (!vItemObj.getSelVendorList().isEmpty()) {
                            for (String vendMapID : vItemObj.getSelVendorList()) {

                                int resVal = getJdbcTemplate().update(sb.toString(),
                                        new Object[]{recordID,
                                            sessUserID,
                                            dateUtil.generateDBCurrentDateTime(),
                                            vendMapID
                                        });

                                if (resVal > 0) {
                                    retVal++;
                                } else {
                                    transactionStatus.setRollbackOnly();
                                    return -1;
                                }
                            }
                        }
                            
                        

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVendorIndentItemMappingDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertVendorIndentItemMappingDetails:", e);
        }

    }
    
    public long updateVendorIndentItemMappingDetails(final VendorItemsDTO vItemObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        long recordID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_RECORDED);

                        long orderID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_ORDERED);

                        StringBuilder sb = new StringBuilder();
                        long vStatus = 0;
                        int arrIndex=0;
                        if (!vItemObj.getSelOptionArr().isEmpty()) {
                            for (Long selUpdID : vItemObj.getSelOptionArr()) {
                                if (selUpdID > 0) {
                                    long vendID = vItemObj.getVendorIDArr().get(arrIndex);
                                    long indentID = vItemObj.getIndentIDArr().get(arrIndex);
                                    if (selUpdID == 2) {
                                        sb.append(" UPDATE vendor_indent_item_mapping SET ");
                                        sb.append(" vendor_item_status_id=?,recorderd=1,modified_by,modified_on ");
                                        sb.append(" WHERE fk_vendor_id=? AND fk_item_id=? AND fk_indent_id=? ");

                                        vStatus = recordID;
                                    }
                                    if (selUpdID == 3) {
                                        sb.append(" UPDATE vendor_indent_item_mapping SET ");
                                        sb.append(" vendor_item_status_id=?,ordered=1,modified_by,modified_on ");
                                        sb.append(" WHERE fk_vendor_id=? AND fk_item_id=? AND fk_indent_id=? ");

                                        vStatus = orderID;
                                    }

                                    int resVal = getJdbcTemplate().update(sb.toString(),
                                            new Object[]{vStatus,
                                                vendID,
                                                vItemObj.getItemID(),
                                                indentID,
                                                sessUserID,
                                                dateUtil.generateDBCurrentDateTime()});

                                    if (resVal > 0) {
                                        retVal++;
                                    } else {
                                        transactionStatus.setRollbackOnly();
                                        return -1;
                                    }
                                }
                                arrIndex++;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVendorIndentItemMappingDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertVendorIndentItemMappingDetails:", e);
        }

    }
    
    public long getCountOfVendorOrder(final String Code) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            
                sb.append(" SELECT COUNT(1) ");
                sb.append(" FROM po_entry ");
                sb.append(" WHERE  venderName=? ");
                sb.append(" GROUP BY venderName ");
            

            resVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{Code},
                    Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCountOfVendorOrder:", e);
        }

        return resVal;
    }
    
    public List<VendorItemsDTO> getFileNoInVendorItems(final long itemId, final long indentId) throws AppException {
        List<VendorItemsDTO> dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT file_no,fk_item_id FROM vendor_indent_item_mapping WHERE fk_indent_id=? AND fk_item_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{indentId,itemId});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    VendorItemsDTO MRDTO = new VendorItemsDTO();                    
                      
                    
                    if (resultMap.get("file_no") != null) {
                        MRDTO.setFileNo((Long)(resultMap.get("file_no")));
                    } else {
                        MRDTO.setFileNo(0);
                    }
                    if (resultMap.get("fk_item_id") != null) {
                        MRDTO.setItemID((Long)(resultMap.get("fk_item_id")));
                    } else {
                        MRDTO.setItemID(0);
                    }
                    dto.add(MRDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getFileNoInVendorItems:", e);
        }
        return dto;
    }
    
    public List<VendorItemsDTO> getVenItemMappingByfileNo(final long fileNumber) throws AppException {
        List<VendorItemsDTO> dto = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT a.fk_vendor_id,a.file_no,a.fk_item_id,i.pk_item_id,i.item_name,i.item_code,");
            sb.append(" v.pk_vendor_id,v.vendor_name,v.vendor_city ");
            sb.append(" FROM vendor_indent_item_mapping a");
            sb.append(" INNER JOIN item_master i ON a.fk_item_id = i.pk_item_id ");
            sb.append(" INNER JOIN vendor_details v ON a.fk_vendor_id = v.pk_vendor_id ");            
            sb.append(" WHERE a.file_no=? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{fileNumber});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    VendorItemsDTO MRDTO = new VendorItemsDTO();                    
                      
                    MRDTO.setVendorID((Long)(resultMap.get("fk_vendor_id")));
                    if (resultMap.get("file_no") != null) {
                        MRDTO.setFileNo((Long)(resultMap.get("file_no")));
                    } else {
                        MRDTO.setFileNo(0);
                    }
                    if (resultMap.get("fk_item_id") != null) {
                        MRDTO.setItemID((Long)(resultMap.get("fk_item_id")));
                    } else {
                        MRDTO.setItemID(0);
                    }
                    MRDTO.getItemObj().setItemID((Long)(resultMap.get("pk_item_id")));
                    MRDTO.getItemObj().setItemName((String)(resultMap.get("item_name")));
                    MRDTO.getItemObj().setItemCode((String)(resultMap.get("item_code")));
                    MRDTO.getVendorObj().setVendorID((Long)(resultMap.get("pk_vendor_id")));
                    MRDTO.getVendorObj().setVendorName((String)(resultMap.get("vendor_name")));
                    MRDTO.getVendorObj().setVendorCity((String)(resultMap.get("vendor_city")));
                    dto.add(MRDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVenItemMappingByfileNo:", e);
        }
        return dto;
    }
}
