/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.ComparativeStatementDTO;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.service.impl.MasterLookUpServiceImpl;
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
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class ComparativeStatementDAOImpl {

    private static final Logger logger = Logger.getLogger(ComparativeStatementDAOImpl.class);

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
    public ComparativeStatementDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<IndentFormDTO> getAllCSTIndentForms() throws AppException {
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
            sb.append(" (k.lookup_name=?) ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{
                        ApplicationConstants.INDENT_STATUS_MAPPED
                    });

            if (!resList.isEmpty()) {

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

    public List<VendorDTO> fetchItemRelatedVendorCSTDetails(final long itemID)
            throws AppException {
        List<VendorDTO> vendorLi = new LinkedList<>();
        try {

            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.vendor_city, ");
            sb.append(" v.registration_type,v.comments,a.fk_indent_id,a.fk_item_id,cs.cst_id  ");
            sb.append(" FROM vendor_details v ");
            sb.append(" JOIN vendor_item_details vt ON v.pk_vendor_id = vt.fk_vendor_id ");
            sb.append(" LEFT JOIN vendor_indent_item_mapping a ON v.pk_vendor_id = a.fk_vendor_id  ");
            sb.append(" AND a.fk_item_id = vt.fk_item_id ");
            sb.append(" LEFT JOIN comparative_statement cs ON v.pk_vendor_id = cs.fk_vendor_id  ");
            sb.append(" AND cs.fk_item_id = vt.fk_item_id ");
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

                    if (retMap.get("fk_indent_id") != null) {
                        vendorObj.setIndentID((Long) (retMap.get("fk_indent_id")));
                    } else {
                        vendorObj.setIndentID(0);
                    }
                    if (retMap.get("fk_item_id") != null) {
                        vendorObj.setItemID((Long) (retMap.get("fk_item_id")));
                    } else {
                        vendorObj.setItemID(0);
                    }
                    if (retMap.get("cst_id") != null) {
                        vendorObj.setCstID((Long) (retMap.get("cst_id")));
                        vendorObj.setEncCstID(encryptDecrypt.encrypt(vendorObj.getCstID() + ""));
                    } else {
                        vendorObj.setCstID(0);
                        vendorObj.setEncCstID(encryptDecrypt.encrypt(vendorObj.getCstID() + ""));
                    }

                    vendorLi.add(vendorObj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchItemRelatedVendorCSTDetails:", e);
        }
        return vendorLi;
    }

    public List<ComparativeStatementDTO> fetchCSTDetailsForSelectedVendor(final long fiNo, final int vid)
            throws AppException {
            List<ComparativeStatementDTO>  list = new LinkedList<>();
        try {
            StringBuilder sbf = new StringBuilder();

            sbf.append(" SELECT a.cst_id,a.cst_type,a.fk_vendor_id,a.fk_item_id,a.basic_rate,a.price, ");
            sbf.append(" a.discount,a.excise_duty,a.sales_tax,a.pack_fwd_charges,a.freight,a.inspection, ");
            sbf.append(" a.testing_amount,a.installation, a.service_tax_percent,a.service_tax,a.sample,");
            sbf.append(" a.intrest,a.delivery_period,a.payment,a.validity_days,a.validity_date, ");
            sbf.append(" a.remarks,a.landing_cost,a.insurance,a.customs_duty,a.cleaning_charges, ");
            sbf.append(" a.inland_freight, v.vendor_name,i.item_name,a.total_amount,a.total_landing_cost ");
            sbf.append(" FROM comparative_statement a  ");
            sbf.append(" INNER JOIN vendor_details v ON a.fk_vendor_id = v.pk_vendor_id  ");
            sbf.append(" INNER JOIN item_master i ON a.fk_item_id = i.pk_item_id  ");
            sbf.append(" WHERE a.fileNumber=?  ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sbf.toString(),
                    new Object[]{fiNo});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    ComparativeStatementDTO cstDTO = new ComparativeStatementDTO();
                    cstDTO.setCstID((Long) (retMap.get("cst_id")));
                    cstDTO.setEncCstID(encryptDecrypt.encrypt(cstDTO.getCstID() + ""));
                    cstDTO.setVendorID((Long) (retMap.get("fk_vendor_id")));
                    cstDTO.setItemID((Long) (retMap.get("fk_item_id")));
                    cstDTO.setBasicRate(((BigDecimal) (retMap.get("basic_rate"))));
                    cstDTO.setPrice((String) (retMap.get("price")));
                    cstDTO.setDiscount(((BigDecimal) (retMap.get("discount"))));
                    cstDTO.setExciseDuty(((String) (retMap.get("excise_duty"))) + "");
                    cstDTO.setSalesTax(((String) (retMap.get("sales_tax"))) + "");
                    cstDTO.setPacketForwardCharges(((String) (retMap.get("pack_fwd_charges"))) + "");
                    cstDTO.setFreight(((String) (retMap.get("freight"))) + "");
                    cstDTO.setInspection((BigDecimal) (retMap.get("inspection")));
                    cstDTO.setTestingAmount(((BigDecimal) (retMap.get("testing_amount"))));
                    cstDTO.setInstallation(((BigDecimal) (retMap.get("installation"))));
                    cstDTO.setServiceTaxPercent(((String) (retMap.get("service_tax_percent"))) + "");
                    cstDTO.setServiceTax(((BigDecimal) (retMap.get("service_tax"))));
                    cstDTO.setSample(((String) (retMap.get("sample"))) + "");
                    cstDTO.setIntrest(((String) (retMap.get("intrest"))) + "");
                    cstDTO.setDeliveryPeriod(((String) (retMap.get("delivery_period"))) + "");
                    cstDTO.setPayment((String) (retMap.get("payment")));
                    cstDTO.setValidityDays((String) (retMap.get("validity_days")));
                    cstDTO.setValidityDate((String) (retMap.get("validity_date")));
                    cstDTO.setRemarks((String) (retMap.get("remarks")));
                    cstDTO.setLandingCost(((BigDecimal) (retMap.get("landing_cost"))));
                    cstDTO.setCstType((String) (retMap.get("cst_type")));
                    cstDTO.setInsurance(((String) (retMap.get("insurance"))) + "");
                    cstDTO.setCustomDuty(((String) (retMap.get("customs_duty"))) + "");
                    cstDTO.setCleaningCharges(((BigDecimal) (retMap.get("cleaning_charges"))));
                    cstDTO.setInlandFreight(((String) (retMap.get("inland_freight"))) + "");
                    cstDTO.setVendorName((String) (retMap.get("vendor_name")));
                    cstDTO.setItemName((String) (retMap.get("item_name")));
                    cstDTO.setTotalAmount((BigDecimal) (retMap.get("total_amount")));
                    cstDTO.setTotalLandingCost((BigDecimal) (retMap.get("total_landing_cost")));
                    list.add(cstDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchCSTDetailsForSelectedVendor:", e);
        }
        return list;
    }

    public int insertCSTDetails(final List<ComparativeStatementDTO> cstDTO, final long sessUserID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sbf = new StringBuilder();

            sbf.append(" INSERT INTO comparative_statement  ");
            sbf.append(" (fileNumber,fk_categoryId,cst_type,fk_vendor_id,fk_item_id,itemqty,units,basic_rate,price,  ");
            sbf.append(" discount,subtotal,excise_duty,sales_tax,pack_fwd_charges,freight,inspection,  ");
            sbf.append(" testing_amount,gstonitit,anyother,octroitax,installation, service_tax_percent,service_tax,trainingcha,sample,");
            sbf.append(" intrest,delivery_period,payment,validity_days,validity_date, ");
            sbf.append(" remarks,landing_cost,insurance,customs_duty,cleaning_charges,inland_freight,  ");
            sbf.append(" total_amount,total_landing_cost,hsncode,mrprice,created_on)");
            sbf.append(" VALUES  ");
            sbf.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ");
            sbf.append("   ");
            sbf.append("   ");
            
            for(ComparativeStatementDTO iteObj : cstDTO) {
            resVal = jdbcTemplate.update(sbf.toString(),
                    new Object[]{
                        iteObj.getFileNumber(),
                        iteObj.getCategoryId(),
                        iteObj.getCstType().trim(),
                        iteObj.getVendorID(),
                        iteObj.getItemID(),
                        iteObj.getItemqty(),
                        iteObj.getUnits(),
                        iteObj.getBasicRate(),
                        iteObj.getPrice().trim(),
                        iteObj.getDiscount(),
                        iteObj.getSubtotal(),
                        iteObj.getExciseDuty().trim(),
                        iteObj.getSalesTax().trim(),
                        iteObj.getPacketForwardCharges().trim(),
                        iteObj.getFreight().trim(),
                        iteObj.getInspection(),
                        iteObj.getTestingAmount(),
                        iteObj.getGstonitit(),
                        iteObj.getAnyother(),
                        iteObj.getOctroitax(),
                        iteObj.getInstallation(),
                        iteObj.getServiceTaxPercent().trim(),
                        iteObj.getServiceTax(),
                        iteObj.getTrainingcha(),
                        iteObj.getSample().trim(),
                        iteObj.getIntrest().trim(),
                        iteObj.getDeliveryPeriod().trim(),
                        iteObj.getPayment().trim(),
                        iteObj.getValidityDays(),
                        iteObj.getValidityDate().trim(),
                        iteObj.getRemarks().trim(),
                        iteObj.getLandingCost(),
                        iteObj.getInsurance().trim(),
                        iteObj.getCustomDuty().trim(),
                        iteObj.getCleaningCharges(),
                        iteObj.getInlandFreight().trim(),
                        iteObj.getTotalAmount(),
                        iteObj.getTotalLandingCost(),
                        iteObj.getHsncode().trim(),
                        iteObj.getMrprice(),
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertCSTDetails:", e);
        }
        return resVal;
    }

    public int updateCSTDetails(final ComparativeStatementDTO cstDTO, final long sessUserID) throws AppException {
        int resVal = 0;
        try {
            StringBuilder sbf = new StringBuilder();

            sbf.append(" UPDATE comparative_statement  SET ");
            sbf.append(" cst_type=?,fk_vendor_id=?,fk_item_id=?,basic_rate=?,price=?,  ");
            sbf.append(" discount=?,excise_duty=?,sales_tax=?,pack_fwd_charges=?,freight=?,inspection=?,  ");
            sbf.append(" testing_amount=?,installation=?, service_tax_percent=?,service_tax=?,sample=?,");
            sbf.append(" intrest=?,delivery_period=?,payment=?,validity_days=?,validity_date=?, ");
            sbf.append(" remarks=?,landing_cost=?,insurance=?,customs_duty=?,cleaning_charges=?,");
            sbf.append(" total_amount=?,total_landing_cost=?,inland_freight=?  ");
            sbf.append(" WHERE  ");
            sbf.append(" cst_id=?  ");
            sbf.append("   ");

            resVal = jdbcTemplate.update(sbf.toString(),
                    new Object[]{
                        cstDTO.getCstType().trim(),
                        cstDTO.getVendorID(),
                        cstDTO.getItemID(),
                        cstDTO.getBasicRate(),
                        cstDTO.getPrice().trim(),
                        cstDTO.getDiscount(),
                        cstDTO.getExciseDuty().trim(),
                        cstDTO.getSalesTax().trim(),
                        cstDTO.getPacketForwardCharges().trim(),
                        cstDTO.getFreight().trim(),
                        cstDTO.getInspection(),
                        cstDTO.getTestingAmount(),
                        cstDTO.getInstallation(),
                        cstDTO.getServiceTaxPercent().trim(),
                        cstDTO.getServiceTax(),
                        cstDTO.getSample().trim(),
                        cstDTO.getIntrest().trim(),
                        cstDTO.getDeliveryPeriod().trim(),
                        cstDTO.getPayment().trim(),
                        cstDTO.getValidityDays(),
                        cstDTO.getValidityDate().trim(),
                        cstDTO.getRemarks().trim(),
                        cstDTO.getLandingCost(),
                        cstDTO.getInsurance().trim(),
                        cstDTO.getCustomDuty().trim(),
                        cstDTO.getCleaningCharges(),
                        cstDTO.getTotalAmount(),
                        cstDTO.getTotalLandingCost(),
                        cstDTO.getInlandFreight().trim(),
                        
                        cstDTO.getCstID()
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateCSTDetails:", e);
        }
        return resVal;
    }
    
    public long getIndentdIdByFileNo(final long file_no) throws AppException {
        long indentNo = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_indent_file_mapping_id  ");
            
            sb.append(" FROM indent_file_mapping WHERE file_no=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{file_no});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO formD =new IndentFormDTO();
                    
                    indentNo = (Long) (retMap.get("pk_indent_file_mapping_id"));
                    logger.info("---------------------"+indentNo);
                    //sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIndentdIdByFileNo:", e);
        }

        return indentNo;
    }
    
    public long getIndentFormIdByFileNo(final long file_no) throws AppException {
        long indentNo = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT fk_indent_form_id  ");
            
            sb.append(" FROM indent_file_mapping WHERE file_no=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{file_no});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO formD =new IndentFormDTO();                    
                    indentNo = (Long) (retMap.get("fk_indent_form_id"));                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIndentFormIdByFileNo:", e);
        }

        return indentNo;
    }
    
    public List<IndentFormDTO> getFileNumbersList() throws AppException {
        List<IndentFormDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT file_no  ");
            
            sb.append(" FROM indent_file_mapping ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO formD =new IndentFormDTO();
                    
                    formD.setFileNoFrMapp((long) (retMap.get("file_no")));
                    //logger.info("---------------------"+indentNo);
                    //sectionObj.setEncFieldValue(encryptDecrypt.encrypt(sectionObj.getSectionID() + ""));
                    list.add(formD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getFileNumbersList:", e);
        }

        return list;
    }
    
    public List<IndentFileMappingDTO> getIndentdFormIdByFileNo(final long file_no) throws AppException {        
        List<IndentFileMappingDTO> fiList = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.fk_group_id,a.fk_indent_form_id,");
            sb.append("g.group_name,g.group_code");
            sb.append(" FROM indent_file_mapping a");
            sb.append(" JOIN group_master g ON a.fk_group_id = g.pk_group_id");
            sb.append(" WHERE a.file_no=? ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{file_no});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    IndentFileMappingDTO formD = new IndentFileMappingDTO();  
                    formD.setFileGroupID((Long) (retMap.get("fk_group_id")));
                    formD.setIndentFormID((Long) (retMap.get("fk_indent_form_id")));
                    formD.getGroupDTO().setGroupName((String) (retMap.get("group_name")));
                    formD.getGroupDTO().setGroupCode((String) (retMap.get("group_code")));
                    fiList.add(formD);                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIndentdFormIdByFileNo:", e);
        }

        return fiList;
    }
    
    public List<ComparativeStatementDTO> fetchCSTDetailsForSelectedItem(final long fiNo, final long itemId)
            throws AppException {
            List<ComparativeStatementDTO>  list = new LinkedList<>();
        try {
            StringBuilder sbf = new StringBuilder();

            sbf.append(" SELECT a.cst_id,a.cst_type,a.fk_vendor_id,a.fk_item_id,a.basic_rate,a.price, ");
            sbf.append(" a.discount,a.excise_duty,a.sales_tax,a.pack_fwd_charges,a.freight,a.inspection, ");
            sbf.append(" a.testing_amount,a.installation, a.service_tax_percent,a.service_tax,a.sample,");
            sbf.append(" a.intrest,a.delivery_period,a.payment,a.validity_days,a.validity_date, ");
            sbf.append(" a.remarks,a.landing_cost,a.insurance,a.customs_duty,a.cleaning_charges, ");
            sbf.append(" a.itemqty,a.units,a.subtotal,a.trainingcha,a.gstonitit,a.anyother,a.octroitax,a.hsncode,a.mrprice,");
            sbf.append(" a.inland_freight, v.vendor_name,v.vendor_code,i.item_name,a.total_amount,a.total_landing_cost,c.category_name, ");
            sbf.append(" u.unit_name,u.unit_code ");
            sbf.append(" FROM comparative_statement a  ");
            sbf.append(" INNER JOIN vendor_details v ON a.fk_vendor_id = v.pk_vendor_id  ");
            sbf.append(" INNER JOIN item_master i ON a.fk_item_id = i.pk_item_id  ");
            sbf.append(" INNER JOIN category_master c ON a.fk_categoryId = c.pk_category_id  ");
            sbf.append(" JOIN unit_master u ON a.units = u.pk_unit_id ");
            sbf.append(" WHERE a.fileNumber=? AND a.fk_item_id=? ORDER BY a.total_landing_cost ASC ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sbf.toString(),
                    new Object[]{fiNo,itemId});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    ComparativeStatementDTO cstDTO = new ComparativeStatementDTO();
                    cstDTO.setCstID((Long) (retMap.get("cst_id")));
                    cstDTO.setEncCstID(encryptDecrypt.encrypt(cstDTO.getCstID() + ""));
                    cstDTO.setVendorID((Long) (retMap.get("fk_vendor_id")));
                    cstDTO.setItemID((Long) (retMap.get("fk_item_id")));
                    cstDTO.setBasicRate(((BigDecimal) (retMap.get("basic_rate"))));
                    cstDTO.setPrice((String) (retMap.get("price")));
                    cstDTO.setDiscount(((BigDecimal) (retMap.get("discount"))));
                    cstDTO.setExciseDuty(((String) (retMap.get("excise_duty"))) + "");
                    cstDTO.setSalesTax(((String) (retMap.get("sales_tax"))) + "");
                    cstDTO.setPacketForwardCharges(((String) (retMap.get("pack_fwd_charges"))) + "");
                    cstDTO.setFreight(((String) (retMap.get("freight"))) + "");
                    cstDTO.setInspection((BigDecimal) (retMap.get("inspection")));
                    cstDTO.setTestingAmount(((BigDecimal) (retMap.get("testing_amount"))));
                    cstDTO.setInstallation(((BigDecimal) (retMap.get("installation"))));
                    cstDTO.setServiceTaxPercent(((String) (retMap.get("service_tax_percent"))) + "");
                    cstDTO.setServiceTax(((BigDecimal) (retMap.get("service_tax"))));
                    cstDTO.setSample(((String) (retMap.get("sample"))) + "");
                    cstDTO.setIntrest(((String) (retMap.get("intrest"))) + "");
                    cstDTO.setDeliveryPeriod(((String) (retMap.get("delivery_period"))) + "");
                    cstDTO.setPayment((String) (retMap.get("payment")));
                    cstDTO.setValidityDays((String) (retMap.get("validity_days")));
                    cstDTO.setValidityDate((String) (retMap.get("validity_date")));
                    cstDTO.setRemarks((String) (retMap.get("remarks")));
                    cstDTO.setLandingCost(((BigDecimal) (retMap.get("landing_cost"))));
                    cstDTO.setCstType((String) (retMap.get("cst_type")));
                    cstDTO.setInsurance(((String) (retMap.get("insurance"))) + "");
                    cstDTO.setCustomDuty(((String) (retMap.get("customs_duty"))) + "");
                    cstDTO.setCleaningCharges(((BigDecimal) (retMap.get("cleaning_charges"))));
                    
                    cstDTO.setItemqty(((Long) (retMap.get("itemqty"))));
                    cstDTO.setUnits(((Long) (retMap.get("units"))));
                    cstDTO.setSubtotal(((BigDecimal) (retMap.get("subtotal"))));
                    cstDTO.setTrainingcha(((BigDecimal) (retMap.get("trainingcha"))));
                    cstDTO.setGstonitit(((String) (retMap.get("gstonitit"))));
                    cstDTO.setAnyother(((BigDecimal) (retMap.get("anyother"))));
                    cstDTO.setOctroitax(((String) (retMap.get("octroitax"))));
                    cstDTO.setHsncode(((String) (retMap.get("hsncode"))));
                    cstDTO.setMrprice(((BigDecimal) (retMap.get("mrprice"))));
                    
                    cstDTO.setInlandFreight(((String) (retMap.get("inland_freight"))) + "");
                    cstDTO.setVendorName((String) (retMap.get("vendor_name")));
                    cstDTO.setItemName((String) (retMap.get("item_name")));
                    cstDTO.setTotalAmount((BigDecimal) (retMap.get("total_amount")));
                    cstDTO.setTotalLandingCost((BigDecimal) (retMap.get("total_landing_cost")));
                    cstDTO.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    cstDTO.getVenObj().setVendorCode((String) (retMap.get("vendor_code")));
                    cstDTO.getItemObj().setItemName((String) (retMap.get("item_name")));
                    cstDTO.getCatObj().setCategoryName((String) (retMap.get("category_name")));
                    cstDTO.getItemObj().getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                    cstDTO.getItemObj().getUnitDTO().setUnitCode((String) (retMap.get("unit_code")));
                    list.add(cstDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : fetchCSTDetailsForSelectedItem:", e);
        }
        return list;
    }
    
    public List<ComparativeStatementDTO> cstDetailsForVendorBased(final long fileNo, final long vendorID)
            throws AppException {
            List<ComparativeStatementDTO>  list = new LinkedList<>();
        try {
            StringBuilder sbf = new StringBuilder();

            sbf.append(" SELECT a.cst_id,a.cst_type,a.fk_vendor_id,a.fk_item_id,a.basic_rate,a.price, ");
            sbf.append(" a.discount,a.excise_duty,a.sales_tax,a.pack_fwd_charges,a.freight,a.inspection, ");
            sbf.append(" a.testing_amount,a.installation, a.service_tax_percent,a.service_tax,a.sample,");
            sbf.append(" a.intrest,a.delivery_period,a.payment,a.validity_days,a.validity_date, ");
            sbf.append(" a.remarks,a.landing_cost,a.insurance,a.customs_duty,a.cleaning_charges, ");
            sbf.append(" a.itemqty,a.units,a.subtotal,a.trainingcha,a.gstonitit,a.anyother,a.octroitax,a.hsncode,a.mrprice,");
            sbf.append(" a.inland_freight, v.vendor_name,i.item_name,i.item_code,a.total_amount,a.total_landing_cost,c.category_name,");
            sbf.append(" u.unit_name,u.unit_code ");
            sbf.append(" FROM comparative_statement a  ");
            sbf.append(" INNER JOIN vendor_details v ON a.fk_vendor_id = v.pk_vendor_id  ");
            sbf.append(" INNER JOIN item_master i ON a.fk_item_id = i.pk_item_id  ");
            sbf.append(" INNER JOIN category_master c ON a.fk_categoryId = c.pk_category_id  ");
            sbf.append(" JOIN unit_master u ON a.units = u.pk_unit_id ");
            sbf.append(" WHERE a.fileNumber=? AND a.fk_vendor_id=? ORDER BY a.total_landing_cost ASC ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sbf.toString(),
                    new Object[]{fileNo,vendorID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    ComparativeStatementDTO cstDTO = new ComparativeStatementDTO();
                    cstDTO.setCstID((Long) (retMap.get("cst_id")));
                    cstDTO.setEncCstID(encryptDecrypt.encrypt(cstDTO.getCstID() + ""));
                    cstDTO.setVendorID((Long) (retMap.get("fk_vendor_id")));
                    cstDTO.setItemID((Long) (retMap.get("fk_item_id")));
                    cstDTO.setBasicRate(((BigDecimal) (retMap.get("basic_rate"))));
                    cstDTO.setPrice((String) (retMap.get("price")));
                    cstDTO.setDiscount(((BigDecimal) (retMap.get("discount"))));
                    cstDTO.setExciseDuty(((String) (retMap.get("excise_duty"))) + "");
                    cstDTO.setSalesTax(((String) (retMap.get("sales_tax"))) + "");
                    cstDTO.setPacketForwardCharges(((String) (retMap.get("pack_fwd_charges"))) + "");
                    cstDTO.setFreight(((String) (retMap.get("freight"))) + "");
                    cstDTO.setInspection((BigDecimal) (retMap.get("inspection")));
                    cstDTO.setTestingAmount(((BigDecimal) (retMap.get("testing_amount"))));
                    cstDTO.setInstallation(((BigDecimal) (retMap.get("installation"))));
                    cstDTO.setServiceTaxPercent(((String) (retMap.get("service_tax_percent"))) + "");
                    cstDTO.setServiceTax(((BigDecimal) (retMap.get("service_tax"))));
                    cstDTO.setSample(((String) (retMap.get("sample"))) + "");
                    cstDTO.setIntrest(((String) (retMap.get("intrest"))) + "");
                    cstDTO.setDeliveryPeriod(((String) (retMap.get("delivery_period"))) + "");
                    cstDTO.setPayment((String) (retMap.get("payment")));
                    cstDTO.setValidityDays((String) (retMap.get("validity_days")));
                    cstDTO.setValidityDate((String) (retMap.get("validity_date")));
                    cstDTO.setRemarks((String) (retMap.get("remarks")));
                    cstDTO.setLandingCost(((BigDecimal) (retMap.get("landing_cost"))));
                    cstDTO.setCstType((String) (retMap.get("cst_type")));
                    cstDTO.setInsurance(((String) (retMap.get("insurance"))) + "");
                    cstDTO.setCustomDuty(((String) (retMap.get("customs_duty"))) + "");
                    cstDTO.setCleaningCharges(((BigDecimal) (retMap.get("cleaning_charges"))));
                    
                    cstDTO.setItemqty(((Long) (retMap.get("itemqty"))));
                    cstDTO.setUnits(((Long) (retMap.get("units"))));
                    cstDTO.setSubtotal(((BigDecimal) (retMap.get("subtotal"))));
                    cstDTO.setTrainingcha(((BigDecimal) (retMap.get("trainingcha"))));
                    cstDTO.setGstonitit(((String) (retMap.get("gstonitit"))));
                    cstDTO.setAnyother(((BigDecimal) (retMap.get("anyother"))));
                    cstDTO.setOctroitax(((String) (retMap.get("octroitax"))));
                    cstDTO.setHsncode(((String) (retMap.get("hsncode"))));
                    cstDTO.setMrprice(((BigDecimal) (retMap.get("mrprice"))));
                    
                    cstDTO.setInlandFreight(((String) (retMap.get("inland_freight"))) + "");
                    cstDTO.setVendorName((String) (retMap.get("vendor_name")));
                    cstDTO.setItemName((String) (retMap.get("item_name")));                    
                    cstDTO.setTotalAmount((BigDecimal) (retMap.get("total_amount")));
                    cstDTO.setTotalLandingCost((BigDecimal) (retMap.get("total_landing_cost")));
                    cstDTO.getVenObj().setVendorName((String) (retMap.get("vendor_name")));
                    cstDTO.getItemObj().setItemName((String) (retMap.get("item_name")));
                    cstDTO.getItemObj().setItemCode((String) (retMap.get("item_code")));
                    cstDTO.getCatObj().setCategoryName((String) (retMap.get("category_name")));
                    cstDTO.getItemObj().getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                    cstDTO.getItemObj().getUnitDTO().setUnitCode((String) (retMap.get("unit_code")));
                    list.add(cstDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : cstDetailsForVendorBased:", e);
        }
        return list;
    }
}
