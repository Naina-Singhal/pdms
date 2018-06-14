/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.ItemServiceImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
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
public class ItemDAOImpl {

    private static final Logger logger = Logger.getLogger(ItemDAOImpl.class);
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
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private ItemServiceImpl itemService;

    /*
    End : Autowiring the required Class instances
     */
 /*
    Default Constructor 
     */
    public ItemDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<ItemDTO> getAllItemDetails() throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" b.category_name,b.category_code,u.unit_name ");
            sb.append("  FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            //sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" LEFT JOIN unit_master u ON a.fk_unit_id = u.pk_unit_id ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));                    
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                    //itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
//                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));
//                    if (retMap.get("avail_stock") != null) {
//                        itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
//                    } else {
//                        itemObj.setCurrentStock(0);
//                    }

                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllItemDetails:", e);
        }
        return itemLi;
    }

    public List<ItemDTO> getAllActiveItemDetails() throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" a.status_id,b.category_name,b.category_code,c.avail_stock,u.unit_name,u.pk_unit_id ");
            sb.append(" FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = a.fk_unit_id ");
            sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" WHERE  a.status_id=3 ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));
                    if (retMap.get("avail_stock") != null) {
                        itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    } else {
                        itemObj.setCurrentStock(0);
                    }

                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllActiveItemDetails:", e);
        }

        return itemLi;
    }

    public ItemDTO getSelectedItemDetail(final long itemID) throws AppException {
        ItemDTO itemObj = new ItemDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" a.status_id,b.category_name,b.category_code,c.avail_stock,u.unit_name,u.pk_unit_id  ");
            sb.append(" FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = a.fk_unit_id ");
            sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" WHERE  a.pk_item_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{itemID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.getCategoryDTO().setDescription((String) (retMap.get("description")));

                    if (retMap.get("pk_unit_id") != null) {
                        itemObj.getUnitDTO().setUnitID((Long) (retMap.get("pk_unit_id")));
                        itemObj.getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                    } else {
                        itemObj.getUnitDTO().setUnitID(0);
                        itemObj.getUnitDTO().setUnitName("");
                    }

                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));

                    if (retMap.get("avail_stock") != null) {
                        itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    } else {
                        itemObj.setCurrentStock(0);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedItemDetail:", e);
        }
        return itemObj;
    }

    public int insertItemDetail(final ItemDTO itemObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        StringBuilder sb = new StringBuilder();

                        StringBuilder stksb = new StringBuilder();

                        sb.append(" INSERT INTO item_master ");
                        sb.append("(fk_category_id,item_name,item_code,fk_unit_id,description,  ");
                        sb.append(" status_id,created_by,created_on) ");
                        sb.append(" VALUES (?,?,?,?,?,?,?,?)");

                        stksb.append(" INSERT INTO item_stock ");
                        stksb.append(" (fk_item_id,fk_category_id,avail_stock,created_by,created_on) ");
                        stksb.append(" VALUES ");
                        stksb.append(" (?,?,?,?,?) ");

                        retVal = getJdbcTemplate().update(sb.toString(),
                                new Object[]{itemObj.getCategoryDTO().getCategoryID(),
                                    itemObj.getItemName().trim(),
                                    itemObj.getItemCode().trim(),
                                    itemObj.getUnitDTO().getUnitID(),
                                    itemObj.getDescription().trim(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString()}
                        );

                        int insItemID = getJdbcTemplate().queryForObject("select last_insert_id()", Integer.class);
                        logger.info("------------"+insItemID);
                        int sRetVal = getJdbcTemplate().update(stksb.toString(),
                                new Object[]{insItemID,
                                    itemObj.getCategoryDTO().getCategoryID(),
                                    itemObj.getCurrentStock(),
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString()}
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertItemDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertItemDetail:: " + e.getMessage());
        }
    }

    public int updateItemDetail(final List<ItemDTO> itemObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {
                    int retVal = 0;
                    try {
                        
                        
                        // UPDATE ITEM MASTER WITH ITEM DETAILS
                        StringBuilder sb = new StringBuilder();
                        sb.append(" UPDATE item_master SET ");
                        sb.append(" fk_category_id=?,item_name=?,item_code=?,fk_unit_id=?,description=?,  ");
                        sb.append(" status_id=?,modified_by=?,modified_on=? ");
                        sb.append(" WHERE pk_item_id=? ");

                        for(ItemDTO iteObj: itemObj){
                        ItemDTO exisItemObj = itemService.getSelectedItemDetail(iteObj.getEncFieldValue());
                        retVal = getJdbcTemplate().update(sb.toString(),
                                new Object[]{iteObj.getCategoryID(),
                                    iteObj.getItemName().trim(),
                                    iteObj.getItemCode().trim(),
                                    iteObj.getUnitID(),
                                    iteObj.getDescription().trim(),
                                    ApplicationConstants.ACTIVE_FLAG_VALUE,
                                    sessUserID,
                                    dateUtil.generateDBCurrentDateInString(),
                                    iteObj.getItemID()});
                        
                        
                        StringBuilder stkselb = new StringBuilder();
                        long stkId = 0;

                        // CHECK IF THE ITEM CATEGORY IS CHANGED 
                        // IF CHANGED WE NEED TO DELETE THE STOCK OF OLD ITEM MAPPED TO OLD CATEGORY 
                        // AND INSERT A NEW RECORD WITH NEW CATEGORY.
                        // ELSE WE JUST NEED TO UPDATE THE ITEM STOCK.
                        if (exisItemObj.getCategoryDTO().getCategoryID()
                                == iteObj.getCategoryID()) {
                            stkselb.append(" SELECT pk_item_stock_id FROM item_stock ");
                            stkselb.append(" WHERE fk_item_id=? AND fk_category_id=? ");

                            stkId = getJdbcTemplate().queryForObject(stkselb.toString(),
                                    new Object[]{iteObj.getItemID(),
                                        iteObj.getCategoryID()},
                                    Long.class);
                        } else {
                            stkselb.append(" SELECT pk_item_stock_id FROM item_stock ");
                            stkselb.append(" WHERE fk_item_id=? AND fk_category_id=? ");
                            logger.info(iteObj.getItemID()+"----hkhkhhkkh-----"+exisItemObj.getCategoryDTO().getCategoryID());
                            stkId = getJdbcTemplate().queryForObject(stkselb.toString(),
                                    new Object[]{iteObj.getItemID(),
                                        exisItemObj.getCategoryDTO().getCategoryID()},
                                    Long.class);

                            StringBuilder stkdb = new StringBuilder();
                            stkdb.append(" DELETE FROM item_stock ");
                            stkdb.append(" WHERE pk_item_stock_id=? ");

                            int delVal = getJdbcTemplate().update(stkdb.toString(),
                                    new Object[]{stkId}
                            );

                            stkId = 0;
                        }

                        if (stkId > 0) {
                            StringBuilder stkub = new StringBuilder();

                            stkub.append(" UPDATE item_stock SET ");
                            stkub.append(" fk_item_id=?,fk_category_id=?,avail_stock=?,");
                            stkub.append(" modified_by=?,modified_on=? ");
                            stkub.append(" WHERE pk_item_stock_id=? ");

                            int sRetVal = getJdbcTemplate().update(stkub.toString(),
                                    new Object[]{iteObj.getItemID(),
                                        iteObj.getCategoryID(),
                                        iteObj.getCurrentStock(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateInString(),
                                        stkId}
                            );
                        } else {
                            StringBuilder stkib = new StringBuilder();

                            stkib.append(" INSERT INTO item_stock ");
                            stkib.append(" (fk_item_id,fk_category_id,avail_stock,created_by,created_on) ");
                            stkib.append(" VALUES ");
                            stkib.append(" (?,?,?,?,?) ");

                            int insVal = getJdbcTemplate().update(stkib.toString(),
                                    new Object[]{iteObj.getItemID(),
                                        iteObj.getCategoryID(),
                                        iteObj.getCurrentStock(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateInString()}
                            );
                        }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:updateItemDetail");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }

                    return retVal;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "updateItemDetail:: " + e.getMessage());
        }
    }

    public List<ItemDTO> getCategoryWiseItemDetails(final long categoryID) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" a.status_id,b.category_name,b.category_code,c.avail_stock, ");
            sb.append(" u.pk_unit_id,u.unit_name,u.unit_code ");
            sb.append(" FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = a.fk_unit_id ");
            sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" WHERE  a.status_id=3 AND a.fk_category_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{categoryID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));
                    if (retMap.get("avail_stock") != null) {
                        itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    } else {
                        itemObj.setCurrentStock(0);
                    }
                    if (retMap.get("pk_unit_id") != null) {
                        itemObj.getUnitDTO().setUnitID((Long) (retMap.get("pk_unit_id")));
                        itemObj.getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                        itemObj.getUnitDTO().setUnitCode((String) (retMap.get("unit_code")));
                    } else {
                        itemObj.getUnitDTO().setUnitID(0);
                        itemObj.getUnitDTO().setUnitName("");
                        itemObj.getUnitDTO().setUnitCode("");
                    }

                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCategoryWiseItemDetails:", e);
        }

        return itemLi;
    }

    public long checkDuplicateItems(final String itemCode, final String itemName) throws AppException {
        long empID = 0;
        try {

            if (itemCode.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM item_master ";
                qry += " WHERE  LOWER(item_name) =? ";

                empID = getJdbcTemplate().queryForObject(qry,
                        new Object[]{
                            itemName.trim().toLowerCase()},
                        Long.class);
            } else if (itemName.equalsIgnoreCase("0")) {
                String qry = "SELECT COUNT(1) FROM item_master ";
                qry += " WHERE LOWER(item_code)=? ";

                empID = getJdbcTemplate().queryForObject(qry,
                        new Object[]{itemCode.trim().toLowerCase()},
                        Long.class);
            } else {
                String qry = "SELECT COUNT(1) FROM item_master ";
                qry += " WHERE LOWER(item_code)=? AND LOWER(item_name) =? ";

                empID = getJdbcTemplate().queryForObject(qry,
                        new Object[]{itemCode.trim().toLowerCase(),
                            itemName.trim().toLowerCase()},
                        Long.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateItems:: " + e.getMessage());
        }
        return empID;
    }

    
    public List<ItemDTO> getCategoryWiseItemDeByCate(final long categoryID) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" a.status_id,b.category_name,b.category_code,c.avail_stock, ");
            sb.append(" u.pk_unit_id,u.unit_name,u.unit_code ");
            sb.append(" FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            sb.append(" LEFT JOIN unit_master u ON u.pk_unit_id = a.fk_unit_id ");
            sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" WHERE  a.fk_category_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{categoryID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));
                    
                    itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    
                    if (retMap.get("pk_unit_id") != null) {
                        itemObj.getUnitDTO().setUnitID((Long) (retMap.get("pk_unit_id")));
                        itemObj.getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                        itemObj.getUnitDTO().setUnitCode((String) (retMap.get("unit_code")));
                    } else {
                        itemObj.getUnitDTO().setUnitID(0);
                        itemObj.getUnitDTO().setUnitName("");
                        itemObj.getUnitDTO().setUnitCode("");
                    }

                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getCategoryWiseItemDeByCate:", e);
        }

        return itemLi;
    }
    
    public List<ItemDTO> getItemMasterDeById(final long itemID) throws AppException {
        List<ItemDTO> itemList = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT a.pk_item_id,a.fk_category_id,a.fk_unit_id,a.item_name, ");
            sb.append(" a.item_code,a.description,i.avail_stock  FROM item_master a ");
            sb.append(" JOIN item_stock i ON a.pk_item_id = i.fk_item_id ");
            sb.append(" WHERE pk_item_id=?  ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{itemID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    ItemDTO itemObj = new ItemDTO();
                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    itemObj.getUnitDTO().setUnitID((Long) (retMap.get("fk_unit_id")));//                  
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));

                    //if (retMap.get("avail_stock") != null) {
                   //     itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
                    //} else {
                     //   itemObj.setCurrentStock(0);
                    //}
                    itemList.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemMasterDeById:", e);
        }
        return itemList;
    }
    
    
    public long getTotalRowCount() throws AppException {
        long count = 0;
        try {
            String qry = "SELECT COUNT(*) FROM item_master ";                

                count = getJdbcTemplate().queryForObject(qry,
                        new Object[]{},
                        Long.class);
                
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemMasterDeById:", e);
        }
        return count;
    }
    
    public List<ItemDTO> getItemDetailsPagination(final long endIndex, final long pageLength) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT a.pk_item_id,a.fk_category_id,a.item_name,a.item_code,a.description,  ");
            sb.append(" b.category_name,b.category_code,u.unit_name ");
            sb.append("  FROM item_master a ");
            sb.append(" JOIN category_master b ON a.fk_category_id = b.pk_category_id ");
            //sb.append(" LEFT JOIN item_stock c ON c.fk_item_id = a.pk_item_id ");
            sb.append(" LEFT JOIN unit_master u ON a.fk_unit_id = u.pk_unit_id ");
            sb.append(" ORDER BY a.pk_item_id ASC LIMIT ?,? ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
            new Object[]{endIndex,pageLength});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {

                    ItemDTO itemObj = new ItemDTO();

                    itemObj.setItemID((Long) (retMap.get("pk_item_id")));
                    itemObj.getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));                    
                    itemObj.setItemName((String) (retMap.get("item_name")));
                    itemObj.setItemCode((String) (retMap.get("item_code")));
                    itemObj.setDescription((String) (retMap.get("description")));
                    itemObj.getCategoryDTO().setCategoryName((String) (retMap.get("category_name")));
                    itemObj.getCategoryDTO().setCategoryCode((String) (retMap.get("category_code")));
                    itemObj.getUnitDTO().setUnitName((String) (retMap.get("unit_name")));
                    //itemObj.setRowStatusKey((Long) (retMap.get("status_id")));
//                    itemObj.setEncFieldValue(encryptDecrypt.encrypt(itemObj.getItemID() + ""));
//                    if (retMap.get("avail_stock") != null) {
//                        itemObj.setCurrentStock((Long) (retMap.get("avail_stock")));
//                    } else {
//                        itemObj.setCurrentStock(0);
//                    }

                    itemLi.add(itemObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllItemDetails:", e);
        }
        return itemLi;
    }
    
    public ArrayList<String> getItemOnlyCodes(final String keywords) throws AppException {
        ArrayList <String> result = new ArrayList<String>();
        ItemDTO IbC = new ItemDTO();        
        try {
            if (keywords.matches(".*[a-zA-Z].*")) {
                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT item_name FROM item_master WHERE item_name LIKE '%" + keywords + "%' ");
                List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                if (!resList.isEmpty()) {

                    for (Map<String, Object> retMap : resList) {
                        result.add((String) (retMap.get("item_name")));
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(" SELECT item_code FROM item_master WHERE item_code LIKE '%" + keywords + "%' ");
                List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                if (!resList.isEmpty()) {

                    for (Map<String, Object> retMap : resList) {
                        result.add((String) (retMap.get("item_code")));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemOnlyCodes:", e);
        }
        return result;
    }
    
    public List<ItemDTO> getItemMaDeByItemName(final String itemName) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_item_id,fk_unit_id,item_name,item_code,description ");
            sb.append(" FROM item_master WHERE item_name =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{itemName});                    

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
            throw new AppException("Exception Occurred : getItemMaDeByItemName:", e);
        }
        return itemLi;
    }
}
