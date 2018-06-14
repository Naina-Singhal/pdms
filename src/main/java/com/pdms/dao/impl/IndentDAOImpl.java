/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentFormFileDTO;
import com.pdms.dto.IndentLogDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.UserPermissionDTO;
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
public class IndentDAOImpl {

    private static final Logger logger = Logger.getLogger(IndentDAOImpl.class);

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
    public IndentDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<IndentFormDTO> getAllIndentForms(final String sessionKey) throws AppException {
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
            //sb.append(" JOIN  employee_type_master etm ON b.fk_employee_type_id = etm.pk_employee_type_id");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                //encryptDecrypt.setApplicationKey(sessionKey);
                
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO indentObj = new IndentFormDTO();

                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    if(retMap.get("received_date") != null)
                    {
                        String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                        indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
                    }
                    else
                    {
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
                    
                    indentObj.setComments((String)(retMap.get("comments")));

                    indentLi.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllIndentForms:", e);
        }
        return indentLi;
    }
    
    
    public List<IndentFormDTO> getUserBasedAllIndentForms(final long sectionID, final long signUserID,
                        final long approveUserID, final long sessionUserID, final String sessionKey) 
                throws AppException {
        List<IndentFormDTO> indentLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            String strWhere="";
            if((signUserID > 0) &&(approveUserID > 0))
            {
                strWhere = "AND (a.fk_signing_authority_id="+signUserID;
                strWhere+= "  OR a.fk_approve_authority_id="+approveUserID+")";
            }
            else if(approveUserID > 0)
            {
                strWhere = "AND a.fk_approve_authority_id="+approveUserID;
                strWhere+=" OR a.created_by="+sessionUserID;
            }
            else if(signUserID > 0)
            {
                strWhere = "AND a.fk_signing_authority_id="+signUserID;
                strWhere+=" OR a.created_by="+sessionUserID;
            }
            else if((signUserID < 0) &&(approveUserID < 0))
            {
                strWhere = "AND a.fk_signing_authority_id=a.fk_signing_authority_id ";
                strWhere+=" AND a.fk_approve_authority_id=fk_approve_authority_id ";
                strWhere+=" AND a.created_by=a.created_by";
            }
            else 
            {
                strWhere = "AND a.fk_signing_authority_id=a.fk_signing_authority_id ";
                strWhere+=" AND a.fk_approve_authority_id=fk_approve_authority_id ";
                strWhere+=" AND a.created_by="+sessionUserID;
            }
            //a.fk_item_id,d.item_name,d.item_code,
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
            sb.append(" WHERE  a.fk_section_id=?   ");
            sb.append( strWhere );
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{sectionID});

            if (!resList.isEmpty()) {
                
                //encryptDecrypt.setApplicationKey(sessionKey);
                
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO indentObj = new IndentFormDTO();

                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    if(retMap.get("received_date") != null)
                    {
                        String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                        indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
                    }
                    else
                    {
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

                    indentLi.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllIndentForms:", e);
        }
        return indentLi;
    }

    public IndentFormDTO getSelectedIndentFormDetail(final long indentID) throws AppException {
        IndentFormDTO indentObj = new IndentFormDTO();
        try {
            StringBuilder sb = new StringBuilder();

            //a.fk_item_id,d.item_name,d.item_code,
            sb.append("SELECT a.pk_indent_form_id,a.indent_number,a.received_date,a.estimated_cost,a.indent_date,");
            sb.append(" a.fk_section_id,b.section_code,b.section_name,a.fk_approve_authority_id,c.designation_code,c.designation_name, ");
            sb.append(" a.fk_category_id,e.category_name,e.category_code,a.fk_employee_id,f.first_name,f.last_name, ");
            sb.append(" a.fk_place_of_delivery_id,g.place_of_delivery,a.fk_mode_of_purchase_id,a.fk_head_of_account_id, ");
            sb.append(" h.mode_of_purchase,i.account_type,a.description_details,a.mta,a.brief_description,");
            sb.append(" a.other_information,a.fk_signing_authority_id,j.designation_code,j.designation_name, ");
            sb.append(" a.status_id,k.lookup_name indent_status,l.file_no,m.group_name,a.indent_type_ctq,a.indent_type_prop,a.comments ");
            sb.append(" FROM indent_form a ");
            //sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            //sb.append(" JOIN item_master d ON a.fk_item_id = d.pk_item_id ");
            sb.append(" JOIN category_master e ON a.fk_category_id = e.pk_category_id ");
            sb.append(" JOIN employee_profile f ON a.fk_employee_id = f.fk_employee_id ");
            sb.append(" JOIN place_of_delivery_master g ON a.fk_place_of_delivery_id = g.pk_place_of_delivery_id ");
            sb.append(" JOIN head_of_account_master i ON i.pk_head_of_account_id = a.fk_head_of_account_id ");
            sb.append(" JOIN master_lookup k ON k.lookup_id = a.status_id ");
            sb.append(" LEFT JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" WHERE a.pk_indent_form_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    if(retMap.get("received_date") != null)
                    {
                        String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                        indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
                    }
                    else
                    {
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
                    indentObj.setEncRowStatusValue(encryptDecrypt.encrypt(indentObj.getRowStatusKey() + ""));
                    indentObj.setIndentStatus((String) (retMap.get("indent_status")));
                    if (retMap.get("file_no") != null) {
                        indentObj.setFileNo(((Long) (retMap.get("file_no"))) + "");
                        indentObj.setMappedGroupName((String) (retMap.get("group_name")));
                    } else {
                        indentObj.setFileNo("");
                        indentObj.setMappedGroupName("");
                    }
                    
                    indentObj.setComments((String)(retMap.get("comments")));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedIndentFormDetail:", e);
        }
        return indentObj;
    }

    public List<ItemDTO> getAllIndentItemDetails(final long indentID) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.fk_item_id,b.item_code,b.item_name,a.indent_slno,a.required_qty,  ");
            sb.append(" u.pk_unit_id,u.unit_name,u.unit_code ");
            sb.append(" FROM indent_items a");
            sb.append(" JOIN item_master b ON a.fk_item_id = b.pk_item_id  ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = b.fk_unit_id ");
            sb.append(" WHERE a.fk_indent_form_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("fk_item_id")));
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setIndentSlNo((Long) (retMap.get("indent_slno")));
                    if(retMap.get("required_qty") != null)
                    {
                        itemObj.setRequiredStock((Long)(retMap.get("required_qty"))+"");
                    }
                    else
                    {
                         itemObj.setRequiredStock("");
                    }
                    if(retMap.get("pk_unit_id") != null)
                    {
                        itemObj.getUnitDTO().setUnitID((Long)(retMap.get("pk_unit_id")));
                        itemObj.getUnitDTO().setUnitName((String)(retMap.get("unit_name")));
                        itemObj.getUnitDTO().setUnitCode((String)(retMap.get("unit_code")));
                    }
                    else
                    {
                        itemObj.getUnitDTO().setUnitID(0);
                        itemObj.getUnitDTO().setUnitName("");
                        itemObj.getUnitDTO().setUnitCode("");
                    }
                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllItemDetails:", e);
        }
        return itemLi;
    }

    
    public List<IndentLogDTO> getAllIndentLog(final long indentID) throws AppException {
        List<IndentLogDTO> indentLogLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.fk_emp_id,a.action_performed,a.comments,a.status_id,a.created_on,  ");
            sb.append(" CONCAT(b.first_name,' ',b.last_name) username,c.designation_name,d.lookup_name ");
            sb.append(" FROM indent_form_log a  ");
            sb.append(" JOIN employee_profile b ON a.fk_emp_id = b.fk_employee_id ");
            sb.append(" JOIN designation_master c ON b.fk_designation_id = c.pk_designation_id ");
            sb.append(" JOIN master_lookup d on a.status_id = d.lookup_id ");
            sb.append(" WHERE a.fk_indent_form_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    IndentLogDTO  indentLogObj = new IndentLogDTO();

                    indentLogObj.setUsername((String)(retMap.get("username")));
                    indentLogObj.setDesignationName((String)(retMap.get("designation_name")));
                    indentLogObj.setRowStatusValue((String)(retMap.get("lookup_name")));
                    String createdDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("created_on")));
                    indentLogObj.setCreatedDate(createdDate);
                    indentLogObj.setComments((String)(retMap.get("comments")));
                    indentLogObj.setActionPerformed((String)(retMap.get("action_performed")));
                    
                    indentLogLi.add(indentLogObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllItemDetails:", e);
        }
        return indentLogLi;
    }

    
    
    public long getMaxFileNumberForIndentMapping() throws AppException {
        long fileNo = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT IFNULL(MAX(file_no),0) file_no  FROM indent_file_mapping ");

            fileNo = getJdbcTemplate().queryForObject(sb.toString(), Long.class);

            if (fileNo <= 0) {
                fileNo = 1;
            } else {
                fileNo = fileNo + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getMaxFileNumberForIndentMapping:", e);
        }

        return fileNo;
    }
    
    public long checkDuplicateIndent(final String indentNo) throws AppException {
        long fileNo = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT COUNT(1) FROM indent_form WHERE indent_number=? ");

            fileNo = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{indentNo}, Integer.class);

           
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : checkDuplicateIndent:", e);
        }

        return fileNo;
    }

    
    
    public List<IndentFormFileDTO> getAllIndentFiles(final long indentID) throws AppException {
        List<IndentFormFileDTO> indentFilesLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_indent_form_upload_id,fk_indent_form_id,file_name,file_path  ");
            sb.append(" FROM indent_form_uploads ");
            sb.append(" WHERE fk_indent_form_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    IndentFormFileDTO  indentFileObj = new IndentFormFileDTO();

                    indentFileObj.setIndentFormID(indentID);
                    indentFileObj.setFileID((Long)(retMap.get("pk_indent_form_upload_id")));
                    indentFileObj.setFileName((String)(retMap.get("file_name")));
                    indentFileObj.setFilePath((String)(retMap.get("file_path")));
                    indentFileObj.setEncFieldValue((String)(encryptDecrypt.encrypt(indentFileObj.getFileID()+"")));
                    
                    indentFilesLi.add(indentFileObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllIndentFiles:", e);
        }
        return indentFilesLi;
    }
    
    
     public IndentFormFileDTO getSelectedIndentFileUploaded(final long indentID, final long fileUploadID) 
             throws AppException {
        IndentFormFileDTO  indentFileObj = new IndentFormFileDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_indent_form_upload_id,fk_indent_form_id,file_name,file_path  ");
            sb.append(" FROM indent_form_uploads ");
            sb.append(" WHERE fk_indent_form_id=? AND pk_indent_form_upload_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID, fileUploadID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    indentFileObj.setIndentFormID(indentID);
                    indentFileObj.setFileID((Long)(retMap.get("pk_indent_form_upload_id")));
                    indentFileObj.setFileName((String)(retMap.get("file_name")));
                    indentFileObj.setFilePath((String)(retMap.get("file_path")));
                    indentFileObj.setEncFieldValue((String)(encryptDecrypt.encrypt(indentFileObj.getFileID()+"")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedIndentFileUploaded:", e);
        }
        return indentFileObj;
    }


    //public MasterLookupDTO getIndentFormStatusInfo()
     
     public List<IndentFormDTO> getIndentFormDeById(final int indentForm_no) throws AppException {
        List<IndentFormDTO> UPDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM indent_form WHERE pk_indent_form_id='"+indentForm_no+"' ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO updto = new IndentFormDTO();                    
                    updto.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    updto.setIndentNumber((String) (retMap.get("indentNumber")));
                    updto.setRecevidedDate((String) (retMap.get("recevidedDate")));
                    updto.setEstimatedCost((String) (retMap.get("estimatedCost")));
                    updto.setIndentDate((String) (retMap.get("indentDate")));
                    updto.setSectionId((Long) (retMap.get("fk_section_id")));
                    updto.setApproveAuthorityId((Long) (retMap.get("fk_approve_authority_id")));
                   
                    updto.setItemCategoryId((Long) (retMap.get("fk_category_id")));
                    updto.setEmployeeProfileId((Long) (retMap.get("fk_employee_id")));
                    updto.setPlaceOfDeliveryId((Long) (retMap.get("fk_place_of_delivery_id")));
                    updto.setModeOfPurchaseId((Long) (retMap.get("fk_mode_of_purchase_id")));
                    updto.setHeadOfAccountId((Long) (retMap.get("fk_head_of_account_id")));                    
                    updto.setDescriptionDetails((String) (retMap.get("description_details")));
                    updto.setMta((String) (retMap.get("mta")));
                    updto.setBriefDescription((String) (retMap.get("brief_description")));
                    
                    UPDTO.add(updto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return UPDTO;
    }
     
    public List<IndentFormDTO> getIndentFormDeByIndentId(final long indentID) throws AppException {
        
        List<IndentFormDTO> InList = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            //a.fk_item_id,d.item_name,d.item_code,
            sb.append("SELECT a.pk_indent_form_id,a.indent_number,a.received_date,a.estimated_cost,a.indent_date,");
            sb.append(" a.fk_section_id,b.section_code,b.section_name,a.fk_approve_authority_id,c.designation_code,c.designation_name, ");
            sb.append(" a.fk_category_id,e.category_name,e.category_code,a.fk_employee_id,f.first_name,f.last_name, ");
            sb.append(" a.fk_place_of_delivery_id,g.place_of_delivery,a.fk_mode_of_purchase_id,a.fk_head_of_account_id, ");
            sb.append(" h.mode_of_purchase,i.account_type,a.description_details,a.mta,a.brief_description,");
            sb.append(" a.other_information,a.fk_signing_authority_id,j.designation_code,j.designation_name, ");
            sb.append(" a.status_id,k.lookup_name indent_status,l.file_no,m.group_name,a.indent_type_ctq,a.indent_type_prop,a.comments ");
            sb.append(" FROM indent_form a ");
            //sb.append(" JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            //sb.append(" JOIN item_master d ON a.fk_item_id = d.pk_item_id ");
            sb.append(" JOIN category_master e ON a.fk_category_id = e.pk_category_id ");
            sb.append(" JOIN employee_profile f ON a.fk_employee_id = f.fk_employee_id ");
            sb.append(" JOIN place_of_delivery_master g ON a.fk_place_of_delivery_id = g.pk_place_of_delivery_id ");
            sb.append(" JOIN head_of_account_master i ON i.pk_head_of_account_id = a.fk_head_of_account_id ");
            sb.append(" JOIN master_lookup k ON k.lookup_id = a.status_id ");
            sb.append(" LEFT JOIN section_master b ON a.fk_section_id = b.pk_section_id ");
            sb.append(" LEFT JOIN designation_master j ON a.fk_signing_authority_id = j.pk_designation_id ");
            sb.append(" LEFT JOIN mode_of_purchase_master h ON h.pk_mode_of_purchase_id = a.fk_mode_of_purchase_id ");
            sb.append(" LEFT JOIN indent_file_mapping l ON a.pk_indent_form_id = l.fk_indent_form_id ");
            sb.append(" LEFT JOIN designation_master c ON a.fk_approve_authority_id = c.pk_designation_id ");
            sb.append(" LEFT JOIN group_master m ON l.fk_group_id = m.pk_group_id ");
            sb.append(" WHERE a.pk_indent_form_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO indentObj = new IndentFormDTO();
                    indentObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    indentObj.setEncFieldValue(encryptDecrypt.encrypt(indentObj.getIndentFormID() + ""));
                    indentObj.setIndentNumber((String) (retMap.get("indent_number")));
                    if(retMap.get("received_date") != null)
                    {
                        String recDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("received_date")));
                        indentObj.setRecevidedDate(dateUtil.dateConvertionFromDBToJSP(recDate));
                    }
                    else
                    {
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
                    indentObj.setEncRowStatusValue(encryptDecrypt.encrypt(indentObj.getRowStatusKey() + ""));
                    indentObj.setIndentStatus((String) (retMap.get("indent_status")));
                    if (retMap.get("file_no") != null) {
                        indentObj.setFileNo(((Long) (retMap.get("file_no"))) + "");
                        indentObj.setMappedGroupName((String) (retMap.get("group_name")));
                    } else {
                        indentObj.setFileNo("");
                        indentObj.setMappedGroupName("");
                    }
                    
                    indentObj.setComments((String)(retMap.get("comments")));
                    InList.add(indentObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedIndentFormDetail:", e);
        }
        return InList;
    }
    
    public List<EmployeeProfileDTO> getEmpProfileDeByEmpID(final long empID) throws AppException {
        List<EmployeeProfileDTO> EpdDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT fk_employee_id,ic_number,first_name,last_name,fk_section_id  FROM employee_profile ");
            sb.append(" WHERE fk_employee_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{empID});
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    EmployeeProfileDTO emdto = new EmployeeProfileDTO();
                    emdto.setEmployeeProfileID((Long) (retMap.get("fk_employee_id")));
                    emdto.setIcNumber((String) (retMap.get("ic_number")));
                    emdto.setFirstName((String) (retMap.get("first_name")));
                    emdto.setLastName((String) (retMap.get("last_name")));
                    emdto.setSectionId((Long) (retMap.get("fk_section_id")));                                        
                    EpdDTO.add(emdto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getEmpProfileDeByEmpID:", e);
        }
        return EpdDTO;
    }
    
    
}
