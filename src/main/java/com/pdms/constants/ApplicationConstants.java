/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.constants;

/**
 *
 * @author hpasupuleti
 */
public class ApplicationConstants {
    
    public static final String DB_INSERT_FLAG="I";
    public static final String DB_UPDATE_FLAG="U";
    public static final String DB_DELETE_FLAG="D";
    public static final String DB_SUCCESS_FLAG="S";
    public static final String DB_FAILURE_FLAG="F";
    public static final String DB_UNLOCK_FLAG="UL";
    public static final String DB_ACTIVATE_FLAG="AC";
    
    public static final int ACTIVE_FLAG_VALUE=3;
    public static final int IN_ACTIVE_FLAG_VALUE=2;
    
    public static final String CONSTANT_ENTITY_STATUS="StatusType";
    public static final int ENTITY_STATUS_ACTIVE_FLAG=3;
    public static final int ENTITY_STATUS_IN_ACTIVE_FLAG=2;
    public static final int ENTITY_STATUS_IN_PROGRESS_FLAG=1;
    public static final int ENTITY_STATUS_CLOSED_FALG=4;
    
    public static final String ENTITY_STATUS_ACTIVE_CONSTANT="Active";
    public static final String ENTITY_STATUS_IN_ACTIVE_CONSTANT="In Active";
    public static final String ENTITY_STATUS_IN_PROGRESS_CONSTANT="In Progress";
    public static final String ENTITY_STATUS_CLOSED_CONSTANT="Closed";
    
    public static final String CONSTANT_INDENT_STATUS="IndentStatus";
    public static final String INDENT_STATUS_UN_MAPPED="Un Mapped";
    public static final String INDENT_STATUS_MAPPED="Mapped";
    public static final String INDENT_STATUS_CREATED="Created";
    public static final String INDENT_STATUS_SIGNED="Signed";
    public static final String INDENT_STATUS_APPROVED="Approved";
    public static final String INDENT_STATUS_REVERTED="Reverted";
    public static final String INDENT_STATUS_REJECTED="Rejected";
    public static final String INDENT_STATUS_MODIFIED="Modified";
    public static final String INDENT_STATUS_SENT_TO_VENDOR="Sent To Vendor";
    public static final String INDENT_STATUS_RECORDED="Recorded";
    public static final String INDENT_STATUS_ORDERED="Ordered";
    public static final String INDENT_STATUS_RAISED_TENDER="Raised Tender";
    public static final String INDENT_STATUS_TENDER_ACCEPTED="Tender Accepted";
    public static final String INDENT_STATUS_RAISED_PO="Raised PO";
    
    public static final String CONSTANT_TENDER_STATUS="Tender Status";
    public static final String TENDER_STATUS_RAISED="Raised";
    public static final String TENDER_STATUS_VENDOR_MAPPED="Vendor Mapped";
    public static final String TENDER_STATUS_PO_GENERATED="PO Generated";
    
    
    public static final String EMPLOYEE_TYPE_PUADMIN="Purchase Unit Administrator";
    public static final String EMPLOYEE_TYPE_PUUSER="Purchase Unit User";
    public static final String EMPLOYEE_TYPE_SECADMIN="Plant Admin";
    public static final String EMPLOYEE_TYPE_SECUSER="Plant User";
    public static final String EMPLOYEE_TYPE_ACCOUNT_ADMIN="Accounts Admin";
    public static final String EMPLOYEE_TYPE_ACCOUNT_USER="Accounts User";
    public static final String EMPLOYEE_TYPE_STORES_ADMIN="Stores Admin";
    public static final String EMPLOYEE_TYPE_STORES_USER="Stores User";
    public static final String EMPLOYEE_TYPE_SUPERADMIN="SUPERADMIN";
    public static final String EMPLOYEE_TYPE_ALL_SECTIONS_USER="All Sections User";
    
    public static final String INDENT_ACTION_PERFORMED_CREATED="Indent Created";
    public static final String INDENT_ACTION_PERFORMED_SIGNED="Indent Signed";
    public static final String INDENT_ACTION_PERFORMED_APPROVED="Indent Approved";
    public static final String INDENT_ACTION_PERFORMED_REJECTED_APPROVER="Indent Rejected By Approving Authority";
    public static final String INDENT_ACTION_PERFORMED_REVERTED_APPROVER="Indent Reverted By Approving Authority";
    
    public static final String DEFAULT_PUADMIN_SECTION="RPU ADMIN";
    public static final String DEFAULT_PLANT_ADMIN_SECTION="PLANT ADMIN";
    public static final String DEFAULT_ACCOUNT_ADMIN_SECTION="ACCOUNTS ADMIN";
    public static final String DEFAULT_STORE_ADMIN_SECTION="STORE ADMIN";
    
    
    public static final String APPLICATION_FILE_UPLOAD_LOCATION="D:/Indent/";
    
    public static final String CONSTANT_VENDOR_REGISTRATION_TYPE="VendorRegistrationType";
    
    public static final String CONSTANT_TENDER_TYPE="TenderType";
    
    public static final String CONSTANT_TENDER_ENVELOPE_TYPE="TenderEnvelopeType";
    
    public static final String CONSTANT_TENDER_CONTRACT_TYPE="ContractType";
        
    public static final String CONSTANT_TENDER_BIDDING_TYPE="BiddingType";
    
    public static final String CONSTANT_TENDER_SUBMIT_MODE="TenderSubmitMode";
    
    public static final String CONSTANT_TENDER_PAYMENT_MODE="TenderPaymentMode";
    
}
