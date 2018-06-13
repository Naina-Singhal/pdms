/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFormDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.MasterLookUpServiceImpl;
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
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class IndentWorkListDAOImpl {

    private static final Logger logger = Logger.getLogger(IndentWorkListDAOImpl.class);

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

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public IndentWorkListDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<IndentFormDTO> getAllPlantAccountStoresAdminIndentForms(final String qryCondition,
            final String subQuery) throws AppException {
        List<IndentFormDTO> indentLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            //a.fk_item_id,d.item_name,d.item_code,
            sb.append("SELECT a.pk_indent_form_id,a.indent_number,a.received_date,a.estimated_cost,a.indent_date,");
            sb.append(" a.fk_section_id,b.section_code,b.section_name,a.fk_approve_authority_id,c.designation_code,c.designation_name, ");
            sb.append(" a.fk_category_id,e.category_name,e.category_code,a.fk_employee_id,f.first_name,f.last_name, ");
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
            sb.append(" JOIN  employee_type_master etm ON f.fk_employee_type_id = etm.pk_employee_type_id");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            //sb.append(" WHERE etm.employee_type_name IN (?) ");
            //sb.append(subQuery);
            
            //below one is example query for using WHERE clause
            //WHERE etm.employee_type_name IN  ('Stores Admin','Stores User') AND (k.lookup_name = 'Created' OR k.lookup_name = 'Modified' OR k.lookup_name = 'Reverted') 
            
            System.out.println("Query : "+subQuery);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{});

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

//                    indentObj.setItemId((Long) (retMap.get("fk_item_id")));
//                    indentObj.getItemObj().setItemID((Long) (retMap.get("fk_item_id")));
//                    indentObj.getItemObj().setItemName((String) (retMap.get("item_name")));
//                    indentObj.getItemObj().setItemCode((String) (retMap.get("item_code")));
//                    
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

                    indentObj.setComments((String) (retMap.get("comments")));

                    indentLi.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllIndentForms:", e);
        }
        return indentLi;
    }

    public List<IndentFormDTO> getAllApprovingAuthorityIndentForms(final long sessionUserID, 
            final long approverDesigID)
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
            sb.append(" WHERE  (a.fk_approve_authority_id=? OR a.created_by=?)  "); //a.fk_section_id=? AND 
            //sb.append(" AND (k.lookup_name=? OR k.lookup_name=? OR k.lookup_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{approverDesigID,
                        sessionUserID
                    });//sectionID,

//                            ApplicationConstants.INDENT_STATUS_CREATED,
//                            ApplicationConstants.INDENT_STATUS_CREATED,
//                            ApplicationConstants.INDENT_STATUS_REVERTED,
//                            ApplicationConstants.INDENT_STATUS_REJECTED,
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

    public List<IndentFormDTO> getAllSigningAuthorityIndentForms(final long sessionUserID, 
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
            sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" WHERE  a.fk_signing_authority_id=? AND ");
            sb.append(" (k.lookup_name<>? OR k.lookup_name<>? OR k.lookup_name<>?) ");
            //a.fk_section_id=? AND 

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{
                        signDesigID,
                        ApplicationConstants.INDENT_STATUS_CREATED,
                        ApplicationConstants.INDENT_STATUS_MODIFIED,
                        ApplicationConstants.INDENT_STATUS_REVERTED
                    });

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

    public List<IndentFormDTO> getAllCreatedUserIndentForms(final long sessionUserID)
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
            sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" JOIN category_master e ON a.fk_category_id = e.pk_category_id ");
            sb.append(" JOIN employee_profile f ON a.fk_employee_id = f.fk_employee_id ");
            sb.append(" JOIN place_of_delivery_master g ON a.fk_place_of_delivery_id = g.pk_place_of_delivery_id ");
            sb.append(" JOIN head_of_account_master i ON i.pk_head_of_account_id = a.fk_head_of_account_id ");
            sb.append(" JOIN master_lookup k ON k.lookup_id = a.status_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" WHERE   a.fk_employee_id=?  "); //a.fk_section_id=? AND

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sessionUserID});//sectionID

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
            throw new AppException("Exception Occurred : getAllCreatedUserIndentForms:", e);
        }
        return indentLi;
    }

    public List<IndentFormDTO> getAllPurchaseUnitUserIndentForms(final long sessionUserID)
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
            sb.append(" JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" JOIN employee_group_mapping em ON m.pk_group_id = em.fk_group_id ");
            sb.append(" LEFT JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" WHERE  em.fk_employee_id=?  ");
            sb.append(" GROUP BY a.pk_indent_form_id ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sessionUserID});

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
            throw new AppException("Exception Occurred : getAllCreatedUserIndentForms:", e);
        }
        return indentLi;
    }

    public List<IndentFormDTO> getAllPUAdminIndentForms()
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
            sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" JOIN  employee_type_master etm ON b.fk_employee_type_id = etm.pk_employee_type_id");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            //sb.append(" WHERE  ");
            //sb.append(" (k.lookup_name<>? AND k.lookup_name<>? AND k.lookup_name<>?) ");
            //a.fk_section_id=? AND 

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{
                        //ApplicationConstants.INDENT_STATUS_CREATED,
                        //ApplicationConstants.INDENT_STATUS_MODIFIED,
                        //ApplicationConstants.INDENT_STATUS_REVERTED
                    });

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

}
