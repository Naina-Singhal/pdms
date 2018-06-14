/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentFormFileDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.MasterLookUpServiceImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.commons.lang.StringUtils;
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
public class IndentTransactDAOImpl {

    private static final Logger logger = Logger.getLogger(IndentTransactDAOImpl.class);

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
    public IndentTransactDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public int insertIndentFormDetail(final IndentFormDTO indentForm, final long sessUserID,
            final long sessUserSecID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;

                    try {
                        long unMapStatusID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_CREATED);

                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO indent_form ");
                        //sb.append(" (indent_number,received_date,estimated_cost,indent_date, ");
                        sb.append(" (indent_number,estimated_cost,indent_date, ");
                        sb.append(" fk_section_id,fk_approve_authority_id,fk_category_id,fk_employee_id,fk_place_of_delivery_id, ");
                        sb.append(" fk_mode_of_purchase_id,fk_head_of_account_id,description_details,mta,brief_description, ");
                        sb.append(" indent_type_ctq,indent_type_prop,other_information,fk_signing_authority_id,status_id,created_by,created_on) ");
                        //sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
                        sb.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

                        retVal = getJdbcTemplate().update(sb.toString(),
                                new Object[]{indentForm.getIndentNumber(),
                                    //dateUtil.dateConvertionFromJSPToDB(indentForm.getRecevidedDate()),
                                    indentForm.getEstimatedCost(),
                                    dateUtil.dateConvertionFromJSPToDB(indentForm.getIndentDate()),
                                    sessUserSecID,
                                    indentForm.getApproveAuthorityId(),
                                    indentForm.getCategoryObj().getCategoryID(),
                                    sessUserID,
                                    indentForm.getPlaceOfDeliveryId(),
                                    indentForm.getModeOfPurchaseId(),
                                    indentForm.getHeadOfAccountId(),
                                    indentForm.getDescriptionDetails(),
                                    indentForm.getMta(),
                                    indentForm.getBriefDescription(),
                                    indentForm.getIndentTypeCTQ(),
                                    indentForm.getIndentTypeProp(),
                                    indentForm.getOtherInformation(),
                                    indentForm.getSigningAuthorityId(),
                                    unMapStatusID,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString()
                                });

                        int insIndentID = getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);

                        StringBuilder isb = new StringBuilder();

                        isb.append(" INSERT INTO indent_items ");
                        isb.append("(fk_indent_form_id,fk_category_id,fk_item_id,indent_slno,required_qty,");
                        isb.append(" created_by,created_on) ");
                        isb.append(" VALUES  ");
                        isb.append("(?,?,?,?,?,?,?)");

                        if (!indentForm.getItemDTOLi().isEmpty()) {
                            for (ItemDTO itemObj : indentForm.getItemDTOLi()) {
                                if (!StringUtils.isEmpty(itemObj.getRequiredStock())
                                        && (itemObj.getItemID() > 0)) {
                                    retVal = getJdbcTemplate().update(isb.toString(),
                                            new Object[]{insIndentID,
                                                indentForm.getCategoryObj().getCategoryID(),
                                                itemObj.getItemID(),
                                                itemObj.getIndentSlNo(),
                                                itemObj.getRequiredStock(),
                                                sessUserID,
                                                dateUtil.generateDBCurrentDateTime()}
                                    );
                                }
                            }
                        }

                        StringBuilder ilogsb = new StringBuilder();

                        ilogsb.append(" INSERT INTO indent_form_log ");
                        ilogsb.append(" (fk_indent_form_id,fk_emp_id,action_performed,status_id,created_on)  ");
                        ilogsb.append(" VALUES ");
                        ilogsb.append(" (?,?,?,?,?) ");

                        int logRetVal = getJdbcTemplate().update(ilogsb.toString(),
                                new Object[]{insIndentID,
                                    sessUserID,
                                    ApplicationConstants.INDENT_ACTION_PERFORMED_CREATED,
                                    unMapStatusID,
                                    dateUtil.generateDBCurrentDateTime()}
                        );

                        retVal = insIndentID;
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertIndentFormDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertIndentFormDetail() :: " + e.getMessage());
        }

    }

    public int insertIndentFormUploadedFileDetail(final IndentFormFileDTO fileDTO, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder isb = new StringBuilder();

                        isb.append(" INSERT INTO indent_form_uploads ");
                        isb.append("(fk_indent_form_id,file_name,file_path,status_id,");
                        isb.append(" created_by,created_on) ");
                        isb.append(" VALUES  ");
                        isb.append("(?,?,?,?,?,?)");
                        
                        retVal = getJdbcTemplate().update(isb.toString(),
                                new Object[]{fileDTO.getIndentFormID(),
                                    fileDTO.getFileName(),
                                    fileDTO.getFilePath(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateTime()}
                        );
                        
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertIndentFormUploadedFileDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertIndentFormDetail() :: " + e.getMessage());
        }
    }

    public int updateIndentFormDetail(final IndentFormDTO indentForm, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;

                    try {
                        StringBuilder sb = new StringBuilder();
                        //received_date=?,
                        sb.append(" UPDATE indent_form SET ");
                        sb.append(" indent_number=?,estimated_cost=?,indent_date=?, ");
                        sb.append(" fk_section_id=?,fk_approve_authority_id=?,fk_category_id=?,fk_employee_id=?,fk_place_of_delivery_id=?, ");
                        sb.append(" fk_mode_of_purchase_id=?,fk_head_of_account_id=?,description_details=?,mta=?,brief_description=?, ");
                        sb.append(" indent_type_ctq=?,indent_type_prop=?,other_information=?,fk_signing_authority_id=?,status_id=?,modified_by=?,modified_on=? ");
                        sb.append(" WHERE pk_indent_form_id=? ");

                        retVal = getJdbcTemplate().update(sb.toString(),
                                new Object[]{indentForm.getIndentNumber(),
                                    //dateUtil.dateConvertionFromJSPToDB(indentForm.getRecevidedDate()),
                                    indentForm.getEstimatedCost(),
                                    dateUtil.dateConvertionFromJSPToDB(indentForm.getIndentDate()),
                                    indentForm.getSectionId(),
                                    indentForm.getApproveAuthorityId(),
                                    indentForm.getCategoryObj().getCategoryID(),
                                    //indentForm.getEmployeeProfileId(),
                                    sessUserID,
                                    indentForm.getPlaceOfDeliveryId(),
                                    indentForm.getModeOfPurchaseId(),
                                    indentForm.getHeadOfAccountId(),
                                    indentForm.getDescriptionDetails(),
                                    indentForm.getMta(),
                                    indentForm.getBriefDescription(),
                                    indentForm.getIndentTypeCTQ(),
                                    indentForm.getIndentTypeProp(),
                                    indentForm.getOtherInformation(),
                                    indentForm.getSigningAuthorityId(),
                                    indentForm.getRowStatusKey(),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString(),
                                    indentForm.getIndentFormID()
                                });

                        StringBuilder delitemsb = new StringBuilder();

                        delitemsb.append(" DELETE FROM indent_items ");
                        delitemsb.append(" WHERE fk_indent_form_id= ? ");

                        retVal = getJdbcTemplate().update(delitemsb.toString(),
                                new Object[]{indentForm.getIndentFormID()});

                        StringBuilder isb = new StringBuilder();

                        isb.append(" INSERT INTO indent_items ");
                        isb.append("(fk_indent_form_id,fk_category_id,fk_item_id,indent_slno,required_qty,");
                        isb.append(" created_by,created_on) ");
                        isb.append(" VALUES  ");
                        isb.append("(?,?,?,?,?,?,?)");

                        if (!indentForm.getItemDTOLi().isEmpty()) {
                            for (ItemDTO itemObj : indentForm.getItemDTOLi()) {
                                if (!StringUtils.isEmpty(itemObj.getRequiredStock())
                                        && (itemObj.getItemID() > 0)) {
                                    retVal = getJdbcTemplate().update(isb.toString(),
                                            new Object[]{indentForm.getIndentFormID(),
                                                indentForm.getCategoryObj().getCategoryID(),
                                                itemObj.getItemID(),
                                                itemObj.getIndentSlNo(),
                                                itemObj.getRequiredStock(),
                                                sessUserID,
                                                dateUtil.generateDBCurrentDateTime()}
                                    );
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateIndentFormDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateIndentFormDetail() :: " + e.getMessage());
        }

    }

    public int approveIndentFormDetail(final IndentFormDTO indentForm, final long sessUserID,
            final String indentStatus)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    long appStatusID = 0;
                    String appStatus = "";
                    try {

                        if (indentStatus.equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_APPROVED)) {
                            appStatusID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                    ApplicationConstants.INDENT_STATUS_APPROVED);
                            appStatus = ApplicationConstants.INDENT_ACTION_PERFORMED_SIGNED;
                        } else if (indentStatus.equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_REJECTED)) {
                            appStatusID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                    ApplicationConstants.INDENT_STATUS_REJECTED);
                            appStatus = ApplicationConstants.INDENT_ACTION_PERFORMED_REJECTED_APPROVER;
                        } else if (indentStatus.equalsIgnoreCase(ApplicationConstants.INDENT_STATUS_REVERTED)) {
                            appStatusID = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                    ApplicationConstants.INDENT_STATUS_REVERTED);
                            appStatus = ApplicationConstants.INDENT_ACTION_PERFORMED_REVERTED_APPROVER;
                        }

                        StringBuilder sb = new StringBuilder();
                        sb.append(" UPDATE indent_form SET ");//received_date=?,
                        sb.append(" estimated_cost=?,indent_date=?, ");
                        sb.append(" fk_place_of_delivery_id=?, ");
                        sb.append(" fk_head_of_account_id=?,description_details=?,mta=?,brief_description=?, ");
                        sb.append(" indent_type_ctq=?,indent_type_prop=?,other_information=?,comments=?, ");
                        sb.append(" fk_signing_authority_id=?,status_id=?,modified_by=?,modified_on=? ");
                        sb.append(" WHERE pk_indent_form_id=? ");

                        retVal = getJdbcTemplate().update(sb.toString(),
                                new Object[]{
                                    //dateUtil.dateConvertionFromJSPToDB(indentForm.getRecevidedDate()),
                                    indentForm.getEstimatedCost(),
                                    dateUtil.dateConvertionFromJSPToDB(indentForm.getIndentDate()),
                                    indentForm.getPlaceOfDeliveryId(),
                                    indentForm.getHeadOfAccountId(),
                                    indentForm.getDescriptionDetails(),
                                    indentForm.getMta(),
                                    indentForm.getBriefDescription(),
                                    indentForm.getIndentTypeCTQ(),
                                    indentForm.getIndentTypeProp(),
                                    indentForm.getOtherInformation(),
                                    indentForm.getComments(),
                                    indentForm.getSigningAuthorityId(),
                                    appStatusID,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString(),
                                    indentForm.getIndentFormID()
                                });

                        StringBuilder delitemsb = new StringBuilder();

                        delitemsb.append(" DELETE FROM indent_items ");
                        delitemsb.append(" WHERE fk_indent_form_id= ? ");

                        retVal = getJdbcTemplate().update(delitemsb.toString(),
                                new Object[]{indentForm.getIndentFormID()});

                        StringBuilder isb = new StringBuilder();

                        isb.append(" INSERT INTO indent_items ");
                        isb.append("(fk_indent_form_id,fk_category_id,fk_item_id,indent_slno,required_qty,");
                        isb.append(" created_by,created_on) ");
                        isb.append(" VALUES  ");
                        isb.append("(?,?,?,?,?,?,?)");

                        if (!indentForm.getItemDTOLi().isEmpty()) {
                            for (ItemDTO itemObj : indentForm.getItemDTOLi()) {
                                if (!StringUtils.isEmpty(itemObj.getRequiredStock())) {
                                    retVal = getJdbcTemplate().update(isb.toString(),
                                            new Object[]{indentForm.getIndentFormID(),
                                                indentForm.getCategoryObj().getCategoryID(),
                                                itemObj.getItemID(),
                                                itemObj.getIndentSlNo(),
                                                itemObj.getRequiredStock(),
                                                sessUserID,
                                                dateUtil.generateDBCurrentDateTime()}
                                    );
                                }
                            }
                        }

                        StringBuilder ilogsb = new StringBuilder();

                        ilogsb.append(" INSERT INTO indent_form_log ");
                        ilogsb.append(" (fk_indent_form_id,fk_emp_id,action_performed,status_id,created_on,comments)  ");
                        ilogsb.append(" VALUES ");
                        ilogsb.append(" (?,?,?,?,?,?) ");

                        int logRetVal = getJdbcTemplate().update(ilogsb.toString(),
                                new Object[]{indentForm.getIndentFormID(),
                                    sessUserID,
                                    appStatus,
                                    appStatusID,
                                    dateUtil.generateDBCurrentDateTime(),
                                    indentForm.getComments()
                                });

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateIndentFormDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateIndentFormDetail() :: " + e.getMessage());
        }

    }

    public int signAndMappingIndentToFile(final IndentFormDTO indentObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;

                    StringBuilder insb = new StringBuilder();
                    StringBuilder upsb = new StringBuilder();
                    try {

                        String appStatus = ApplicationConstants.INDENT_STATUS_MAPPED;
                        long indentStatus = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_MAPPED);

                        insb.append(" INSERT INTO indent_file_mapping ");
                        insb.append(" (fk_group_id,file_no,fk_indent_form_id,created_by,created_on) ");
                        insb.append(" VALUES (?,?,?,?,?) ");

                        upsb.append(" UPDATE indent_form SET ");
                        upsb.append(" received_date=?,status_id=?,fk_mode_of_purchase_id=?, ");
                        upsb.append(" modified_by=?, modified_on=? ");
                        upsb.append(" WHERE pk_indent_form_id=? ");

                        int fMapRetId = getJdbcTemplate().update(insb.toString(),
                                new Object[]{indentObj.getGroupObj().getGroupID(),
                                    indentObj.getFileNo(),
                                    indentObj.getIndentFormID(),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString()
                                });

                        int updIndentId = getJdbcTemplate().update(upsb.toString(),
                                new Object[]{
                                    dateUtil.dateConvertionFromJSPToDB(indentObj.getRecevidedDate()),
                                    indentStatus,
                                    indentObj.getModeOfPurchaseId(),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString(),
                                    indentObj.getIndentFormID()
                                });

                        StringBuilder ilogsb = new StringBuilder();

                        ilogsb.append(" INSERT INTO indent_form_log ");
                        ilogsb.append(" (fk_indent_form_id,fk_emp_id,action_performed,status_id,created_on,comments)  ");
                        ilogsb.append(" VALUES ");
                        ilogsb.append(" (?,?,?,?,?,?) ");

                        int logRetVal = getJdbcTemplate().update(ilogsb.toString(),
                                new Object[]{indentObj.getIndentFormID(),
                                    sessUserID,
                                    appStatus,
                                    indentStatus,
                                    dateUtil.generateDBCurrentDateTime(),
                                    ""
                                });

                        if (fMapRetId > 0 && updIndentId > 0) {
                            retVal = 1;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = 0;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:mappingIndentsToFile");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "mappingIndentsToFile() :: " + e.getMessage());
        }
    }

    public int mappingIndentsToFile(final List<IndentFileMappingDTO> fileMapDTOLi)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;

                    StringBuilder insb = new StringBuilder();
                    StringBuilder upsb = new StringBuilder();
                    try {

                        long indentStatus = masterService.getMasterLookUpIDByLookUpValueAndType(ApplicationConstants.CONSTANT_INDENT_STATUS,
                                ApplicationConstants.INDENT_STATUS_MAPPED);

                        insb.append(" INSERT INTO indent_file_mapping ");
                        insb.append(" (fk_group_id,file_no,fk_indent_form_id,created_by,created_on) ");
                        insb.append(" VALUES (?,?,?,?,?) ");

                        upsb.append(" UPDATE indent_form SET ");
                        upsb.append(" status_id=?, modified_by=?, modified_on=? ");
                        upsb.append(" WHERE pk_indent_form_id=? ");

                        int retCount = 0;

                        if (!fileMapDTOLi.isEmpty()) {
                            for (IndentFileMappingDTO fileMapObj : fileMapDTOLi) {
                                int fMapRetId = getJdbcTemplate().update(insb.toString(),
                                        new Object[]{fileMapObj.getFileGroupID(),
                                            fileMapObj.getFileNo(),
                                            fileMapObj.getIndentFormID(),
                                            fileMapObj.getCreatedByUserID(),
                                            dateUtil.generateDBCurrentDateInString()
                                        });
                                if (fMapRetId > 0) {
                                    int updIndentId = getJdbcTemplate().update(upsb.toString(),
                                            new Object[]{indentStatus,
                                                fileMapObj.getCreatedByUserID(),
                                                dateUtil.generateDBCurrentDateInString(),
                                                fileMapObj.getIndentFormID()
                                            });
                                }
                                retCount++;
                            }
                        }
                        if (retCount == fileMapDTOLi.size()) {
                            retVal = 1;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = 0;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:mappingIndentsToFile");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "mappingIndentsToFile() :: " + e.getMessage());
        }
    }

    
    public int deleteIndentFormUploadedFileDetail(final long uploadedFileID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder isb = new StringBuilder();

                        isb.append(" DELETE FROM indent_form_uploads ");
                        isb.append(" WHERE pk_indent_form_upload_id=? ");
                        
                        retVal = getJdbcTemplate().update(isb.toString(),
                                new Object[]{uploadedFileID}
                        );
                        
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:deleteIndentFormUploadedFileDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "deleteIndentFormUploadedFileDetail() :: " + e.getMessage());
        }
    }

    
}
