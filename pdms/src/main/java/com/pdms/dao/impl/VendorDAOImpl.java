/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.dto.VendorItemsDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
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
public class VendorDAOImpl {

    private static final Logger logger = Logger.getLogger(VendorDAOImpl.class);

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

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public VendorDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<VendorDTO> getAllVendorDetails(final long statusID) throws AppException {
        List<VendorDTO> vendorLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.vendor_address,v.vendor_city,v.vendor_pin, ");
            sb.append(" v.vendor_phone,v.vendor_fax,v.vendor_email,v.registration_type,v.expiry_date, ");
            sb.append(" v.vendor_pan,v.vendor_rating,v.source,v.comments ");
            sb.append(" FROM vendor_details v ");
            //sb.append(" WHERE v.status_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vendorObj = new VendorDTO();

                    vendorObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vendorObj.setEncVendorID(encryptDecrypt.encrypt(vendorObj.getVendorID() + ""));
                    vendorObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vendorObj.setVendorName((String) (retMap.get("vendor_name")));
                    vendorObj.setVendorAddress((String) (retMap.get("vendor_address")));
                    vendorObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vendorObj.setVendorPin((String) (retMap.get("vendor_pin")));
                    vendorObj.setVendorPhone((String) (retMap.get("vendor_phone")));
                    vendorObj.setVendorFax((String) (retMap.get("vendor_fax")));
                    vendorObj.setVendorEmail((String) (retMap.get("vendor_email")));
                    vendorObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vendorObj.setExpiraryDate((String) (retMap.get("expiry_date")));
                    vendorObj.setVendorPan((String) (retMap.get("vendor_pan")));
                    vendorObj.setVendorRating(((Long) (retMap.get("vendor_rating"))) + "");
                    vendorObj.setSource((String) (retMap.get("source")));
                    vendorObj.setComments((String) (retMap.get("comments")));

                    vendorLi.add(vendorObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return vendorLi;
    }

    public List<VendorItemsDTO> getAllVendorItemDetails(final long vendorID) throws AppException {
        List<VendorItemsDTO> vendorItemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_vendor_item_id,v.fk_category_id,v.fk_item_id,c.category_code,");
            sb.append(" c.category_name,i.item_name,i.item_code,v.status_id,m.lookup_name  ");
            sb.append(" FROM vendor_item_details v ");
            sb.append(" JOIN category_master c ON v.fk_category_id = c.pk_category_id ");
            sb.append(" JOIN item_master i ON i.pk_item_id = v.fk_item_id ");
            sb.append(" JOIN master_lookup m ON m.lookup_id = v.status_id  ");
            sb.append(" WHERE v.fk_vendor_id =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{vendorID});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {

                    VendorItemsDTO vItemObj = new VendorItemsDTO();

                    vItemObj.setVendorItemID((Long) (retMap.get("pk_vendor_item_id")));
                    vItemObj.setEncVendorItemID(encryptDecrypt.encrypt(vItemObj.getVendorItemID() + ""));
                    vItemObj.setCategoryID((Long) (retMap.get("fk_category_id")));
                    vItemObj.setItemID((Long) (retMap.get("fk_item_id")));
                    vItemObj.getCategoryObj().setCategoryCode((String) (retMap.get("category_code")));
                    vItemObj.getCategoryObj().setCategoryName((String) (retMap.get("category_name")));
                    vItemObj.getItemObj().setItemCode((String) (retMap.get("item_code")));
                    vItemObj.getItemObj().setItemName((String) (retMap.get("item_name")));
                    vItemObj.setRowStatusKey((Long)(retMap.get("status_id")));
                    vItemObj.setRowStatusValue((String)(retMap.get("lookup_name")));

                    vendorItemLi.add(vItemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorItemDetails:", e);
        }
        return vendorItemLi;
    }

    public VendorDTO getSelectedVendorDetails(final long vendorID) throws AppException {
        VendorDTO vendorObj = new VendorDTO();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.contact_person,v.vendor_address,v.vendor_city,v.vendor_pin, ");
            sb.append(" v.vendor_phone,v.vendor_fax,v.vendor_email,v.registration_type,v.expiry_date, ");
            sb.append(" v.vendor_pan,v.vendor_rating,v.source,v.comments ");
            sb.append(" FROM vendor_details v ");
            sb.append(" WHERE v.pk_vendor_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{vendorID});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {

                    vendorObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vendorObj.setEncVendorID(encryptDecrypt.encrypt(vendorObj.getVendorID() + ""));
                    vendorObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vendorObj.setVendorName((String) (retMap.get("vendor_name")));
                    vendorObj.setContactPerson((String) (retMap.get("contact_person")));
                    vendorObj.setVendorAddress((String) (retMap.get("vendor_address")));
                    vendorObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vendorObj.setVendorPin((String) (retMap.get("vendor_pin")));
                    vendorObj.setVendorPhone((String) (retMap.get("vendor_phone")));
                    vendorObj.setVendorFax((String) (retMap.get("vendor_fax")));
                    vendorObj.setVendorEmail((String) (retMap.get("vendor_email")));
                    vendorObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vendorObj.setExpiraryDate((String) (retMap.get("expiry_date")));
                    vendorObj.setVendorPan((String) (retMap.get("vendor_pan")));
                    vendorObj.setVendorRating(((Long) (retMap.get("vendor_rating"))) + "");
                    vendorObj.setSource((String) (retMap.get("source")));
                    vendorObj.setComments((String) (retMap.get("comments")));

                    List<VendorItemsDTO> vendorItemLi = this.getAllVendorItemDetails(vendorID);
                    if (!vendorItemLi.isEmpty()) {
                        vendorObj.setVendorItemsLi(vendorItemLi);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedVendorDetails:", e);
        }
        return vendorObj;
    }

    public int checkVendorExistence(final String vendorName) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT COUNT(1)  ");
            sb.append(" FROM vendor_details v ");
            sb.append(" WHERE v.vendor_name=? ");

            retVal = getJdbcTemplate().queryForObject(sb.toString(),
                    new Object[]{vendorName},
                    Integer.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : checkVendorExistence:", e);
        }
        return retVal;
    }

    public int insertVendorDetails(final VendorDTO vendorObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("INSERT INTO vendor_details ");
            sb.append(" (vendor_code,vendor_name,contact_person,vendor_address,vendor_city,vendor_pin,vendor_phone,vendor_fax, ");
            sb.append(" vendor_email,registration_type,expiry_date,vendor_pan,vendor_rating,source,comments,gstinNumber, ");
            sb.append(" status_id,created_by,created_on) ");
            sb.append(" VALUES ");
            sb.append(" (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{vendorObj.getVendorCode(),
                        vendorObj.getVendorName(),
                        vendorObj.getContactPerson(),
                        vendorObj.getVendorAddress(),
                        vendorObj.getVendorCity(),
                        vendorObj.getVendorPin(),
                        vendorObj.getVendorPhone(),
                        vendorObj.getVendorFax(),
                        vendorObj.getVendorEmail(),
                        vendorObj.getRegistrationType(),
                        vendorObj.getExpiraryDate(),
                        vendorObj.getVendorPan(),
                        vendorObj.getVendorRating(),
                        vendorObj.getSource(),
                        vendorObj.getComments(),
                        vendorObj.getGstinNumber(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });

            int insVendorID = getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);

            retVal = insVendorID;

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertVendorDetails:", e);
        }
        return retVal;
    }

    public int updateVendorDetails(final VendorDTO vendorObj, final long sessUserID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" UPDATE vendor_details SET ");
            sb.append(" vendor_code=?,vendor_name=?,contact_person=?,vendor_address=?,vendor_city=?,vendor_pin=?,vendor_phone=?, ");
            sb.append(" vendor_fax=?,vendor_email=?,registration_type=?,expiry_date=?,vendor_pan=?, ");
            sb.append(" vendor_rating=?,source=?,comments=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE ");
            sb.append(" pk_vendor_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{vendorObj.getVendorCode(),
                        vendorObj.getVendorName(),
                        vendorObj.getContactPerson(),
                        vendorObj.getVendorAddress(),
                        vendorObj.getVendorCity(),
                        vendorObj.getVendorPin(),
                        vendorObj.getVendorPhone(),
                        vendorObj.getVendorFax(),
                        vendorObj.getVendorEmail(),
                        vendorObj.getRegistrationType(),
                        vendorObj.getExpiraryDate(),
                        vendorObj.getVendorPan(),
                        vendorObj.getVendorRating(),
                        vendorObj.getSource(),
                        vendorObj.getComments(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        vendorObj.getVendorID()
                    });

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateVendorDetails:", e);
        }
        return retVal;
    }

    public int insertVendorItemDetails(final List<VendorItemsDTO> vendorItemLi, final long vendorID, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {

                        StringBuilder sb = new StringBuilder();

                        sb.append("INSERT INTO vendor_item_details ");
                        sb.append(" (fk_vendor_id,fk_category_id,fk_item_id,created_by,created_on) ");
                        sb.append(" VALUES ");
                        sb.append(" (?,?,?,?,?) ");

                        if (!vendorItemLi.isEmpty()) {
                            for (VendorItemsDTO vendorItemObj : vendorItemLi) {
                                int res = getJdbcTemplate().update(sb.toString(),
                                        new Object[]{
                                            vendorID,
                                            vendorItemObj.getCategoryID(),
                                            vendorItemObj.getItemID(),
                                            sessUserID,
                                            dateUtil.generateDBCurrentDateTime()
                                        });
                                if (res <= 0) {
                                    transactionStatus.setRollbackOnly();
                                    retVal = -1;
                                    break;
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertVendorItemDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertVendorItemDetails() :: " + e.getMessage());
        }
    }

    public int deleteSelectedVendorItemDetails(final long vendorItemID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            //sb.append(" DELETE FROM vendor_item_details WHERE pk_vendor_item_id =? ");
            sb.append(" UPDATE vendor_item_details SET status_id=? WHERE pk_vendor_item_id =? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ApplicationConstants.IN_ACTIVE_FLAG_VALUE,
                        vendorItemID});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "deleteSelectedVendorItemDetails() :: " + e.getMessage());
        }
        return retVal;
    }
    
    
    public int mapSelectedVendorItemDetails(final long vendorItemID) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            //sb.append(" DELETE FROM vendor_item_details WHERE pk_vendor_item_id =? ");
            sb.append(" UPDATE vendor_item_details SET status_id=? WHERE pk_vendor_item_id =? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{ApplicationConstants.ACTIVE_FLAG_VALUE,
                        vendorItemID});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "mapSelectedVendorItemDetails() :: " + e.getMessage());
        }
        return retVal;
    }

    public int insertSelectedVendorItemDetails(final VendorItemsDTO vendorItemObj, final long sessUserID)
            throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {

                        StringBuilder dsb = new StringBuilder();
                        StringBuilder sb = new StringBuilder();

                        dsb.append(" DELETE FROM vendor_item_details ");
                        dsb.append(" WHERE fk_vendor_id=?  AND fk_category_id=? ");

                        getJdbcTemplate().update(dsb.toString(),
                                new Object[]{
                                    vendorItemObj.getVendorID(),
                                    vendorItemObj.getCategoryID()
                                });

                        sb.append("INSERT INTO vendor_item_details ");
                        sb.append(" (fk_vendor_id,fk_category_id,fk_item_id,status_id,created_by,created_on) ");
                        sb.append(" VALUES ");
                        sb.append(" (?,?,?,?,?,?) ");

                        if (!vendorItemObj.getSelItemList().isEmpty()) {
                            for (Long itemID : vendorItemObj.getSelItemList()) {
                                int res = getJdbcTemplate().update(sb.toString(),
                                        new Object[]{
                                            vendorItemObj.getVendorID(),
                                            vendorItemObj.getCategoryID(),
                                            itemID,
                                            ApplicationConstants.ACTIVE_FLAG_VALUE,
                                            sessUserID,
                                            dateUtil.generateDBCurrentDateTime()
                                        });
                                if (res <= 0) {
                                    transactionStatus.setRollbackOnly();
                                    retVal = -1;
                                    break;
                                } else {
                                    retVal++;
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertSelectedVendorItemDetails");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertSelectedVendorItemDetails() :: " + e.getMessage());
        }
    }

    public long checkDuplicateVendor(final String vCode, final String vName) throws AppException {
        long dpCnt = 0;
        try {

            if (vCode.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM vendor_details ";
                qry += " WHERE LOWER(vendor_name)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{
                            vName.trim().toLowerCase()},
                        Long.class);
            } else if (vName.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM vendor_details ";
                qry += " WHERE LOWER(vendor_code)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{vCode.trim().toLowerCase()},
                        Long.class);
            } else {
                String qry = "SELECT COUNT(1) FROM vendor_details ";
                qry += " WHERE LOWER(vendor_code)=? OR LOWER(vendor_name)=? ";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{vCode.trim().toLowerCase(),
                            vName.trim().toLowerCase()},
                        Long.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateDesignation:: " + e.getMessage());
        }
        return dpCnt;
    }
    
    public long getNextVendorIDToGenerateVendorCode() throws AppException {
        long vendorID = 0;
        try {
            String qry = "SELECT IFNULL(MAX(pk_vendor_id),0) vendorId FROM vendor_details ";
            
            vendorID = getJdbcTemplate().queryForObject(qry,Integer.class);            
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "getNextVendorIDToGenerateVendorCode:: " + e.getMessage());
        }
        return vendorID;
    }
    
    public List<VendorDTO> getGstinNoByVendorName(final String venCode) throws AppException {
        List<VendorDTO> list = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT gstinNumber FROM vendor_details WHERE vendor_code ='"+venCode+"' ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vTO = new VendorDTO();                    
                    vTO.setGstinNumber((String) (retMap.get("gstinNumber")));                    
                    list.add(vTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getGstinNoByVendorName:", e);
        }
        return list;
    }
    
    public List<VendorDTO> getVendorNameByCode(final String venCode) throws AppException {
        List<VendorDTO> list1 = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT pk_vendor_id,vendor_code,vendor_name,vendor_address,vendor_city, ");
            sb.append("vendor_phone FROM vendor_details WHERE vendor_code ='"+venCode+"' ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO dTO = new VendorDTO();            
                    dTO.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    dTO.setVendorCode((String) (retMap.get("vendor_code")));
                    dTO.setVendorName((String) (retMap.get("vendor_name")));  
                    dTO.setVendorAddress((String) (retMap.get("vendor_address")));
                    dTO.setVendorCity((String) (retMap.get("vendor_city")));
                    dTO.setVendorPhone((String) (retMap.get("vendor_phone")));
                    list1.add(dTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVendorNameByCode:", e);
        }
        return list1;
    }
    
    
    public List<VendorDTO> getVendorDeByVendorID(final long vendorID) throws AppException {
       List<VendorDTO> VenList =  new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT v.pk_vendor_id,v.vendor_code,v.vendor_name,v.contact_person,v.vendor_address,v.vendor_city,v.vendor_pin, ");
            sb.append(" v.vendor_phone,v.vendor_fax,v.vendor_email,v.registration_type,v.expiry_date, ");
            sb.append(" v.vendor_pan,v.vendor_rating,v.source,v.comments ");
            sb.append(" FROM vendor_details v ");
            sb.append(" WHERE v.pk_vendor_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{vendorID});

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                     VendorDTO vendorObj = new VendorDTO();
                    vendorObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vendorObj.setEncVendorID(encryptDecrypt.encrypt(vendorObj.getVendorID() + ""));
                    vendorObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vendorObj.setVendorName((String) (retMap.get("vendor_name")));
                    vendorObj.setContactPerson((String) (retMap.get("contact_person")));
                    vendorObj.setVendorAddress((String) (retMap.get("vendor_address")));
                    vendorObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vendorObj.setVendorPin((String) (retMap.get("vendor_pin")));
                    vendorObj.setVendorPhone((String) (retMap.get("vendor_phone")));
                    vendorObj.setVendorFax((String) (retMap.get("vendor_fax")));
                    vendorObj.setVendorEmail((String) (retMap.get("vendor_email")));
                    vendorObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vendorObj.setExpiraryDate((String) (retMap.get("expiry_date")));
                    vendorObj.setVendorPan((String) (retMap.get("vendor_pan")));
                    vendorObj.setVendorRating(((Long) (retMap.get("vendor_rating"))) + "");
                    vendorObj.setSource((String) (retMap.get("source")));
                    vendorObj.setComments((String) (retMap.get("comments")));
                    VenList.add(vendorObj);
                    List<VendorItemsDTO> vendorItemLi = this.getAllVendorItemDetails(vendorID);
                    if (!vendorItemLi.isEmpty()) {
                        vendorObj.setVendorItemsLi(vendorItemLi);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedVendorDetails:", e);
        }
        return VenList;
    }

    public String getVendorCodeById(final long venId) throws AppException {
        String code = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT vendor_code FROM vendor_details WHERE pk_vendor_id ='"+venId+"' ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vTO = new VendorDTO();                    
                    code = (String)(retMap.get("vendor_code"));                    
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getGstinNoByVendorName:", e);
        }       
        return code;
    }
    
    public ArrayList<String> getVendorOnlyCodesSearch(final String keywords) throws AppException {
        ArrayList <String> result = new ArrayList<String>();                
        try {
            
            boolean a = keywords.matches(".*\\d+.*");
            if (keywords.matches(".*[a-zA-Z].*")){   
                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT vendor_name FROM vendor_details WHERE vendor_name LIKE '%" + keywords + "%' ");
                List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                if (!resList.isEmpty()) {

                    for (Map<String, Object> retMap : resList) {
                        result.add((String) (retMap.get("vendor_name")));
                    }
                }                
            }else{
                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT vendor_code FROM vendor_details WHERE vendor_code LIKE '%" + keywords + "%' ");
                List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                if (!resList.isEmpty()) {

                    for (Map<String, Object> retMap : resList) {
                        result.add((String) (retMap.get("vendor_code")));
                    }
                }                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVendorOnlyCodesSearch:", e);
        }
        return result;
    }
    
    public String getVendorDetailsByName(final String vendor_name) throws AppException {
        String venCode = "";
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT vendor_code FROM vendor_details WHERE vendor_name ='"+vendor_name+"' ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vendorObj = new VendorDTO();

                   
                    venCode = ((String) (retMap.get("vendor_code")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVendorDetailsByName:", e);
        }
        return venCode;
    }
}
