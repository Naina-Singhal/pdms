/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.PublicTenderDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.PublicTenderVendorDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.IndentServiceImpl;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class IndentTenderDAOImpl {

    private static final Logger logger = Logger.getLogger(IndentTenderDAOImpl.class);

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
    private IndentServiceImpl indentService;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public IndentTenderDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<IndentFormDTO> getAllTendorIndentForms(final long sessionUserID,
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
            sb.append(" WHERE ");
            sb.append(" (k.lookup_name=? OR k.lookup_name=? OR k.lookup_name=? OR k.lookup_name=? ) ");
            //sb.append(" AND ");
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
            
            //sb.append(signAuthority );
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{
                        ApplicationConstants.INDENT_STATUS_MAPPED,
                        ApplicationConstants.INDENT_STATUS_RAISED_TENDER,
                        ApplicationConstants.INDENT_STATUS_TENDER_ACCEPTED,
                        ApplicationConstants.INDENT_STATUS_RAISED_PO
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

    public int insertPublicTenderDetails(final PublicTenderDTO tenderObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder itemsb = new StringBuilder();
                        StringBuilder iupdsb = new StringBuilder();

                        long iStatusID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_RAISED_TENDER);
                        
                        long  tStatusId=masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_TENDER_STATUS,
                                ApplicationConstants.TENDER_STATUS_RAISED);
                        
                        String appStatus = ApplicationConstants.INDENT_STATUS_RAISED_TENDER;

                        sb.append(" INSERT INTO public_tender_details ");
                        sb.append(" (fk_indent_id,fk_tender_type_id,file_no,tender_cost,no_of_sets,sale_last_date, ");
                        sb.append(" due_date,date_of_opening,ewd,created_by,created_on,status_id) ");
                        sb.append(" VALUES ");
                        sb.append(" (?,?,?,?,?,?,?,?,?,?,?,?) ");

                        itemsb.append(" INSERT INTO public_tender_item_details ");
                        itemsb.append(" (fk_public_tender_id,indentSlNo,fk_item_id,fk_category_id,brief_description,item_qty,item_units,");
                        itemsb.append(" created_by,created_on)  ");
                        itemsb.append(" VALUES ");
                        itemsb.append(" (?,?,?,?,?,?,?,?,?) ");

                        iupdsb.append(" UPDATE indent_form SET ");
                        iupdsb.append(" status_id=?, modified_by=?, modified_on=? ");
                        iupdsb.append(" WHERE pk_indent_form_id=? ");

                        if (!tenderObj.getTenderItemsLi().isEmpty()) {
                            int ptRet = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{tenderObj.getIndentID(),
                                        tenderObj.getTenderTypeID(),
                                        tenderObj.getFileNo(),
                                        tenderObj.getTenderCost(),
                                        tenderObj.getSetsNo(),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getSaleLastDate()),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getDueDate()),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getOpeningDate()),
                                        tenderObj.getEwd(),                                        
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime(),
                                        tStatusId
                                    });
                            int insTenderID = getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
                            int insCnt=0;
                            for (PublicTenderItemsDTO tItemsObj : tenderObj.getTenderItemsLi()) {                                
                                int tItRet = getJdbcTemplate().update(itemsb.toString(),
                                        new Object[]{insTenderID,
                                            tItemsObj.getItemObj().getIndentSlNo(),
                                            tItemsObj.getItemObj().getItemID(),
                                            tItemsObj.getItemObj().getCategoryDTO().getCategoryID(),
                                            tItemsObj.getBreifDesc(),
                                            tItemsObj.getItemQty(),
                                            tItemsObj.getItemUnits(),                                            
                                            sessUserID,
                                            dateUtil.generateDBCurrentDateTime()
                                        });

                                if (tItRet > 0) {
                                    insCnt++;
                                } else {
                                    transactionStatus.setRollbackOnly();
                                    return -1;
                                }
                            }

                            int iUpdRes = getJdbcTemplate().update(iupdsb.toString(),
                                    new Object[]{
                                        iStatusID,
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime(),
                                        tenderObj.getIndentID()
                                    });
                            
                            StringBuilder ilogsb = new StringBuilder();

                            ilogsb.append(" INSERT INTO indent_form_log ");
                            ilogsb.append(" (fk_indent_form_id,fk_emp_id,action_performed,status_id,created_on,comments)  ");
                            ilogsb.append(" VALUES ");
                            ilogsb.append(" (?,?,?,?,?,?) ");

                            int logRetVal = getJdbcTemplate().update(ilogsb.toString(),
                                    new Object[]{tenderObj.getIndentID(),
                                        sessUserID,
                                        appStatus,
                                        iStatusID,
                                        dateUtil.generateDBCurrentDateTime(),
                                        ""
                                    });
                            

                            if ((iUpdRes > 0) && (logRetVal>0)) {
                                retVal = insCnt;
                            } else {
                                transactionStatus.setRollbackOnly();
                                return -1;
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertPublicTenderDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPublicTenderDetails:", e);
        }
    }
    
    
    public int updatePublicTenderDetails(final PublicTenderDTO tenderObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder itemsb = new StringBuilder();
                        
                        sb.append(" UPDATE public_tender_details SET ");
                        sb.append(" fk_tender_type_id=?,file_no=?,tender_cost=?,no_of_sets=?,sale_last_date=?,due_date=?, ");
                        sb.append(" date_of_opening=?,ewd=?,modified_by=?,modified_on=? ");
                        sb.append(" WHERE ");
                        sb.append(" pk_public_tender_id=? ");

                        itemsb.append(" UPDATE public_tender_item_details SET ");
                        itemsb.append(" brief_description=?,modified_by=?,modified_on=? ");
                        itemsb.append(" WHERE fk_public_tender_id=? AND fk_item_id=?  ");
                       
                        if (!tenderObj.getTenderItemsLi().isEmpty()) {
                            int ptRet = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{
                                        tenderObj.getTenderTypeID(),
                                        tenderObj.getFileNo(),
                                        tenderObj.getTenderCost(),
                                        tenderObj.getSetsNo(),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getSaleLastDate()),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getDueDate()),
                                        dateUtil.dateConvertionFromJSPToDB(tenderObj.getOpeningDate()),
                                        tenderObj.getEwd(),                                        
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime(),
                                        tenderObj.getpTenderID()
                                    });
                            int insCnt=0;
                            for (PublicTenderItemsDTO tItemsObj : tenderObj.getTenderItemsLi()) {
                                int tItRet = getJdbcTemplate().update(itemsb.toString(),
                                        new Object[]{tItemsObj.getBreifDesc(),
                                            sessUserID,
                                            dateUtil.generateDBCurrentDateTime(),
                                            tenderObj.getpTenderID(),
                                            tItemsObj.getItemObj().getItemID()
                                        });
                                if (tItRet > 0) {
                                    insCnt++;
                                } else {
                                    transactionStatus.setRollbackOnly();
                                    return -1;
                                }
                            }

                           
                            if (insCnt>0) {
                                retVal = insCnt;
                            } else {
                                transactionStatus.setRollbackOnly();
                                return -1;
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertPublicTenderDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPublicTenderDetails:", e);
        }
    }
    
    
    public List<PublicTenderDTO> fetchAllPublicTenders() throws AppException
    {
        List<PublicTenderDTO> pTenderLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT p.pk_public_tender_id,p.fk_indent_id,p.file_no,p.tender_cost, ");
            sb.append(" p.no_of_sets,p.sale_last_date,p.due_date,p.date_of_opening,p.ewd,p.status_id, ");
            sb.append(" b.lookup_name TederStatus,p.fk_tender_type_id,c.lookup_name TenderType ");
            sb.append(" FROM public_tender_details p ");
            sb.append(" JOIN master_lookup b ON p.status_id = b.lookup_id ");
            sb.append(" JOIN master_lookup c ON p.fk_tender_type_id = c.lookup_id ");
            sb.append("  ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    
                    PublicTenderDTO pTenderObj = new PublicTenderDTO();
                    
                    pTenderObj.setpTenderID((Long)(retMap.get("pk_public_tender_id")));
                    pTenderObj.setTenderTypeID((Long)(retMap.get("fk_tender_type_id")));
                    pTenderObj.setEncFieldValue(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setIndentID((Integer)(retMap.get("fk_indent_id")));
                    pTenderObj.setFileNo((String)(retMap.get("file_no")));
                    pTenderObj.setTenderCost(((Double)(retMap.get("tender_cost")))+"");
                    pTenderObj.setSetsNo((Long)(retMap.get("no_of_sets")));
                    pTenderObj.setEwd((String)(retMap.get("ewd")));
                    
                    pTenderObj.setRowStatusValue((String)(retMap.get("TederStatus")));
                    pTenderObj.setTenderType((String)(retMap.get("TenderType")));
                    
                    String sLastDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("sale_last_date")));
                    pTenderObj.setSaleLastDate((dateUtil.dateConvertionFromDBToJSP(sLastDate)));
                    String dDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("due_date")));
                    pTenderObj.setDueDate((dateUtil.dateConvertionFromDBToJSP(dDate)));
                    String oDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("date_of_opening")));
                    pTenderObj.setOpeningDate((dateUtil.dateConvertionFromDBToJSP(oDate)));
                    
                    IndentFormDTO indentObj = indentService.getSelectedIndentFormDetail
                                        (encryptDecrypt.encrypt(pTenderObj.getIndentID()+""));
                    
                    pTenderObj.setIndentObj(indentObj);
                    
                    pTenderLi.add(pTenderObj);
                    
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchAllPublicTenders:", e);
        }
        return pTenderLi;
    }
    
    public PublicTenderDTO fetchSelectedPublicTenders(final long tenderID) throws AppException
    {
        PublicTenderDTO pTenderObj = new PublicTenderDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT p.pk_public_tender_id,p.fk_indent_id,p.file_no,p.tender_cost, ");
            sb.append(" p.no_of_sets,p.sale_last_date,p.due_date,p.date_of_opening,p.ewd,p.fk_tender_type_id ");
            sb.append(" FROM public_tender_details p ");
            sb.append(" WHERE p.pk_public_tender_id=? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{tenderID});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    
                    pTenderObj.setpTenderID((Long)(retMap.get("pk_public_tender_id")));
                    pTenderObj.setTenderTypeID((Long)(retMap.get("fk_tender_type_id")));
                    pTenderObj.setEncFieldValue(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setEncTenderID(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setIndentID((Integer)(retMap.get("fk_indent_id")));
                    pTenderObj.setEncIndentID(encryptDecrypt.encrypt(pTenderObj.getIndentID()+""));
                    pTenderObj.setFileNo((String)(retMap.get("file_no")));
                    pTenderObj.setTenderCost(((Double)(retMap.get("tender_cost")))+"");
                    pTenderObj.setSetsNo((Long)(retMap.get("no_of_sets")));
                    pTenderObj.setEwd((String)(retMap.get("ewd")));
                    
                    
                    String sLastDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("sale_last_date")));
                    pTenderObj.setSaleLastDate((dateUtil.dateConvertionFromDBToJSP(sLastDate)));
                    String dDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("due_date")));
                    pTenderObj.setDueDate((dateUtil.dateConvertionFromDBToJSP(dDate)));
                    String oDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("date_of_opening")));
                    pTenderObj.setOpeningDate((dateUtil.dateConvertionFromDBToJSP(oDate)));
                    
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchSelectedPublicTenders:", e);
        }
        return pTenderObj;
    }
    
    public List<PublicTenderItemsDTO> fetchAllPublicTenderItemDetails(final long tenderID,
            final long indentID) throws AppException
    {
        List<PublicTenderItemsDTO> pTenderItemsLi = new LinkedList<>();
        try 
        {
            StringBuilder sb = new StringBuilder();

//            sb.append(" SELECT pk_public_tender_item_id,fk_item_id,p.brief_description,p.item_qty, ");
//            sb.append(" p.item_units ");
//            sb.append(" FROM public_tender_item_details p ");
//            sb.append(" WHERE  fk_public_tender_id=? ");

            sb.append(" SELECT p.pk_public_tender_item_id,p.fk_item_id,p.brief_description,p.item_qty, ");
            sb.append(" p.item_units,i.required_qty,c.item_name,c.item_code,u.unit_name,u.unit_code ");
            sb.append(" FROM public_tender_item_details p ");
            sb.append(" INNER JOIN public_tender_details d ON d.pk_public_tender_id = p.fk_public_tender_id ");
            sb.append(" INNER JOIN indent_items i ON d.fk_indent_id = i.fk_indent_form_id ");
            sb.append(" INNER JOIN item_master c ON c.pk_item_id = p.fk_item_id ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = c.fk_unit_id ");
            sb.append(" WHERE  p.fk_public_tender_id=? AND i.fk_indent_form_id=? ");
            sb.append("  GROUP BY p.fk_item_id ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{tenderID,indentID});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    
                    PublicTenderItemsDTO pTenderItemObj = new PublicTenderItemsDTO();
                    
                    pTenderItemObj.setpTenderItemID((Long)(retMap.get("pk_public_tender_item_id")));
                    pTenderItemObj.getItemObj().setItemID((Long)(retMap.get("fk_item_id")));
                    pTenderItemObj.setBreifDesc((String)(retMap.get("brief_description")));
                    pTenderItemObj.setItemQty((Long)(retMap.get("required_qty")));
                    pTenderItemObj.getItemObj().setItemName((String)(retMap.get("item_name")));
                    pTenderItemObj.getItemObj().setItemCode((String)(retMap.get("item_code")));
                    pTenderItemObj.getItemObj().getUnitDTO().setUnitName((String)(retMap.get("unit_name")));
                    pTenderItemObj.getItemObj().getUnitDTO().setUnitCode((String)(retMap.get("unit_code")));
                    //pTenderItemObj.setItemUnits((String)(retMap.get("item_units")));
                    
                    pTenderItemsLi.add(pTenderItemObj);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchAllPublicTenderItemDetails:", e);
        }
        
        return pTenderItemsLi;
    }

    
    public PublicTenderDTO fetchSelectedPublicTendersByIndent(final long indentID) throws AppException
    {
        PublicTenderDTO pTenderObj = new PublicTenderDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT p.pk_public_tender_id,p.fk_indent_id,p.file_no,p.tender_cost, ");
            sb.append(" p.no_of_sets,p.sale_last_date,p.due_date,p.date_of_opening,p.ewd,p.fk_tender_type_id ");
            sb.append(" FROM public_tender_details p ");
            sb.append(" INNER JOIN indent_form i ON p.fk_indent_id = i.pk_indent_form_id ");
            sb.append(" WHERE p.fk_indent_id=? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{indentID});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    
                    pTenderObj.setpTenderID((Long)(retMap.get("pk_public_tender_id")));
                    pTenderObj.setTenderTypeID((Long)(retMap.get("fk_tender_type_id")));
                    pTenderObj.setEncFieldValue(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setIndentID((Integer)(retMap.get("fk_indent_id")));
                    pTenderObj.setFileNo((String)(retMap.get("file_no")));
                    pTenderObj.setTenderCost(((Double)(retMap.get("tender_cost")))+"");
                    pTenderObj.setSetsNo((Long)(retMap.get("no_of_sets")));
                    pTenderObj.setEwd((String)(retMap.get("ewd")));
                    
                    String sLastDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("sale_last_date")));
                    pTenderObj.setSaleLastDate((dateUtil.dateConvertionFromDBToJSP(sLastDate)));
                    String dDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("due_date")));
                    pTenderObj.setDueDate((dateUtil.dateConvertionFromDBToJSP(dDate)));
                    String oDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("date_of_opening")));
                    pTenderObj.setOpeningDate((dateUtil.dateConvertionFromDBToJSP(oDate)));
                    
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchSelectedPublicTendersByIndent:", e);
        }
        return pTenderObj;
    }
 
    
    
    public int updatePublicTenderMapVendorDetails(final PublicTenderVendorDTO ptVendorObj,
            final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder itemsb = new StringBuilder();
                        
                        long  tStatusId=masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_TENDER_STATUS,
                                ApplicationConstants.TENDER_STATUS_VENDOR_MAPPED);
                        
                        sb.append(" UPDATE public_tender_details SET ");
                        sb.append(" status_id=? ");
                        sb.append(" WHERE ");
                        sb.append(" pk_public_tender_id=? ");

                        itemsb.append(" INSERT INTO public_tender_vendor_mapping  ");
                        itemsb.append(" ( fk_public_tender_id,fk_vendor_id,file_no,date_of_closing,created_by,created_on) ");
                        itemsb.append(" VALUES (?,?,?,?,?,?)  ");
                       
                        int ptvmRes = getJdbcTemplate().update(itemsb.toString(),
                                new Object[]{
                                    ptVendorObj.getTenderID(),
                                    ptVendorObj.getVendorID(),
                                    ptVendorObj.getFileNo(),
                                    dateUtil.dateConvertionFromJSPToDB(ptVendorObj.getClosingDate()),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateTime()
                                });
                        int insCnt = getJdbcTemplate().update(sb.toString(),
                                new Object[]{
                                    tStatusId,
                                    ptVendorObj.getTenderID()
                                });
                        

                        if ((ptvmRes > 0)&&(insCnt > 0)) {
                            retVal = insCnt;
                        } else {
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertPublicTenderDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPublicTenderDetails:", e);
        }
    }
    
    public PublicTenderVendorDTO fetchSelectedPublicTenderVendorsByTender(final long tender) throws AppException
    {
        PublicTenderVendorDTO ptVendorObj = new PublicTenderVendorDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT a.fk_public_tender_id,a.fk_vendor_id,a.file_no,a.date_of_closing,");
            sb.append(" a.po_no,a.po_gen_date,a.po_offer,");
            sb.append(" b.vendor_code,b.vendor_name,b.vendor_address,b.vendor_city,b.vendor_pin, ");
            sb.append(" b.vendor_phone,b.vendor_fax,b.vendor_email,b.registration_type,b.expiry_date, ");
            sb.append(" b.vendor_pan,b.vendor_rating ");
            sb.append(" FROM public_tender_vendor_mapping a ");
            sb.append(" JOIN vendor_details b ON b.pk_vendor_id=a.fk_vendor_id ");
            sb.append(" WHERE a.fk_public_tender_id=? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{tender});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    ptVendorObj.setVendorID((Long)(retMap.get("fk_vendor_id")));
                    ptVendorObj.setTenderID((Long)(retMap.get("fk_public_tender_id")));
                    ptVendorObj.setFileNo((String)(retMap.get("file_no")));
                    String closeDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("date_of_closing")));
                    ptVendorObj.setClosingDate(dateUtil.dateConvertionFromDBToJSP(closeDate));
                    ptVendorObj.getVendorObj().setVendorCode((String)(retMap.get("vendor_code")));
                    ptVendorObj.getVendorObj().setVendorName((String)(retMap.get("vendor_name")));
                    
                    if(retMap.get("po_no")!= null){
                        ptVendorObj.setPoNumber((String)(retMap.get("po_no")));
                    }
                    
                    if(retMap.get("po_gen_date")!= null){
                        String poDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("po_gen_date")));
                        ptVendorObj.setPoGenDate(poDate);
                    }
                    
                    if(retMap.get("po_offer")!= null){
                        ptVendorObj.setPoOffer((String)(retMap.get("po_offer")));
                    }
                    
                    ptVendorObj.getVendorObj().setVendorAddress((String) (retMap.get("vendor_address")));
                    ptVendorObj.getVendorObj().setVendorCity((String) (retMap.get("vendor_city")));
                    ptVendorObj.getVendorObj().setVendorPin((String) (retMap.get("vendor_pin")));
                    ptVendorObj.getVendorObj().setVendorPhone((String) (retMap.get("vendor_phone")));
                    ptVendorObj.getVendorObj().setVendorFax((String) (retMap.get("vendor_fax")));
                    ptVendorObj.getVendorObj().setVendorEmail((String) (retMap.get("vendor_email")));
                    ptVendorObj.getVendorObj().setRegistrationType((String) (retMap.get("registration_type")));
                    ptVendorObj.getVendorObj().setExpiraryDate((String) (retMap.get("expiry_date")));
                    ptVendorObj.getVendorObj().setVendorPan((String) (retMap.get("vendor_pan")));
                    ptVendorObj.getVendorObj().setVendorRating(((Long) (retMap.get("vendor_rating"))) + "");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchSelectedPublicTenderVendorsByTender:", e);
        }
        
        return ptVendorObj;
    }
    
    
    
    public int updatePublicTenderUpdatePODetails(final PublicTenderVendorDTO ptVendorObj,
            final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder sb = new StringBuilder();
                        StringBuilder itemsb = new StringBuilder();
                        
                        long  tStatusId=masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_TENDER_STATUS,
                                ApplicationConstants.TENDER_STATUS_PO_GENERATED);
                        
                        sb.append(" UPDATE public_tender_details SET ");
                        sb.append(" status_id=? ");
                        sb.append(" WHERE ");
                        sb.append(" pk_public_tender_id=? ");

                        itemsb.append(" UPDATE public_tender_vendor_mapping  SET ");
                        itemsb.append(" po_no=?,po_gen_date=?,po_offer=?,modified_by=?,modified_on=?  ");
                        itemsb.append(" WHERE ");
                        itemsb.append(" fk_public_tender_id=? ");//AND fk_vendor_id=? 
                       
                        int ptvmRes = getJdbcTemplate().update(itemsb.toString(),
                                new Object[]{
                                    ptVendorObj.getPoNumber(),
                                    dateUtil.generateDBCurrentDateTime(),
                                    ptVendorObj.getPoOffer(),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateTime(),
                                    ptVendorObj.getTenderID()
                                });
                        int insCnt = getJdbcTemplate().update(sb.toString(),
                                new Object[]{
                                    tStatusId,
                                    ptVendorObj.getTenderID()
                                });
                        

                        if ((ptvmRes > 0)&&(insCnt > 0)) {
                            retVal = insCnt;
                        } else {
                            transactionStatus.setRollbackOnly();
                            return -1;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updatePublicTenderUpdatePODetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePublicTenderUpdatePODetails:", e);
        }
    }
    
    public List<PublicTenderItemsDTO> fetAllPublicTenderItemDeById(final long tenderID) throws AppException
    {
        List<PublicTenderItemsDTO> pTenderItemsLi = new LinkedList<>();
        try 
        {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT p.pk_public_tender_item_id,p.fk_item_id,p.brief_description,p.item_qty, ");
            sb.append(" p.item_units,c.item_name,c.item_code,u.unit_name,u.unit_code,z.category_code, ");
            sb.append(" z.pk_category_id,z.category_name FROM public_tender_item_details p ");
            //sb.append(" INNER JOIN public_tender_details d ON d.pk_public_tender_id = p.fk_public_tender_id ");
            //sb.append(" INNER JOIN indent_items i ON d.fk_indent_id = i.fk_indent_form_id ");
            sb.append(" INNER JOIN item_master c ON c.pk_item_id = p.fk_item_id ");
            sb.append(" INNER JOIN category_master z ON z.pk_category_id = p.fk_category_id ");
            sb.append(" JOIN unit_master u ON p.item_units = u.pk_unit_id ");
            sb.append(" WHERE  p.fk_public_tender_id=? ");
            //sb.append("  GROUP BY p.fk_item_id ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{tenderID});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    
                    PublicTenderItemsDTO pTenderItemObj = new PublicTenderItemsDTO();
                    
                    pTenderItemObj.setpTenderItemID((Long)(retMap.get("pk_public_tender_item_id")));
                    pTenderItemObj.getItemObj().setItemID((Long)(retMap.get("fk_item_id")));
                    pTenderItemObj.setBreifDesc((String)(retMap.get("brief_description")));                    
                    pTenderItemObj.setItemQty((Long)(retMap.get("item_qty")));
                    pTenderItemObj.setItemUnits((String)(retMap.get("item_units")));
                    pTenderItemObj.getItemObj().setItemName((String)(retMap.get("item_name")));
                    pTenderItemObj.getItemObj().setItemCode((String)(retMap.get("item_code")));
                    pTenderItemObj.getItemObj().getUnitDTO().setUnitName((String)(retMap.get("unit_name")));
                    pTenderItemObj.getItemObj().getUnitDTO().setUnitCode((String)(retMap.get("unit_code")));
                    pTenderItemObj.getItemObj().getCategoryDTO().setCategoryCode((String)(retMap.get("category_code")));
                    pTenderItemObj.getItemObj().getCategoryDTO().setCategoryID((Long)(retMap.get("pk_category_id")));
                    pTenderItemObj.getItemObj().getCategoryDTO().setCategoryName((String)(retMap.get("category_name")));
                    //pTenderItemObj.setItemUnits((String)(retMap.get("item_units")));
                    
                    pTenderItemsLi.add(pTenderItemObj);
                    
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetAllPublicTenderItemDeById:", e);
        }
        
        return pTenderItemsLi;
    }
    
    
    public List<PublicTenderDTO> getPublicTenderDeByFileNo(final long fileNO) throws AppException
    {
        List<PublicTenderDTO> tenList = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT p.pk_public_tender_id,p.fk_indent_id,p.file_no,p.tender_cost, ");
            sb.append(" p.no_of_sets,p.sale_last_date,p.due_date,p.date_of_opening,p.ewd,p.fk_tender_type_id ");
            sb.append(" FROM public_tender_details p ");
            sb.append(" WHERE p.file_no=? ");
            
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{fileNO});
            
            if(!resList.isEmpty())
            {
                for (Map<String, Object> retMap : resList) {
                    PublicTenderDTO pTenderObj = new PublicTenderDTO();
                    pTenderObj.setpTenderID((Long)(retMap.get("pk_public_tender_id")));
                    pTenderObj.setTenderTypeID((Long)(retMap.get("fk_tender_type_id")));
                    pTenderObj.setEncFieldValue(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setEncTenderID(encryptDecrypt.encrypt(pTenderObj.getpTenderID()+""));
                    pTenderObj.setIndentID((Integer)(retMap.get("fk_indent_id")));
                    pTenderObj.setEncIndentID(encryptDecrypt.encrypt(pTenderObj.getIndentID()+""));
                    pTenderObj.setFileNo((String)(retMap.get("file_no")));
                    pTenderObj.setTenderCost(((Double)(retMap.get("tender_cost")))+"");
                    pTenderObj.setSetsNo((Long)(retMap.get("no_of_sets")));
                    pTenderObj.setEwd((String)(retMap.get("ewd")));                                      
                    String sLastDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("sale_last_date")));
                    pTenderObj.setSaleLastDate((dateUtil.dateConvertionFromDBToJSP(sLastDate)));
                    String dDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("due_date")));
                    pTenderObj.setDueDate((dateUtil.dateConvertionFromDBToJSP(dDate)));
                    String oDate = dateUtil.convertTimestampToString((Timestamp) (retMap.get("date_of_opening")));
                    pTenderObj.setOpeningDate((dateUtil.dateConvertionFromDBToJSP(oDate)));
                    tenList.add(pTenderObj);
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPublicTenderDeByFileNo:", e);
        }
        return tenList;
    }
}

