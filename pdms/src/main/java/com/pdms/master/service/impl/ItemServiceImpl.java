/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.ItemDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.ItemDAOImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hpasupuleti
 */
@Service
public class ItemServiceImpl {

    private static final Logger logger = Logger.getLogger(ItemServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private ItemDAOImpl itemDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public ItemServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getAllItemDetails(final long statusID) throws AppException {

//        if (statusID == 0) {
//            return itemDAO.getAllItemDetails();
//        } else {
//            return itemDAO.getAllActiveItemDetails();
//        }
//        
        return itemDAO.getAllItemDetails();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public ItemDTO getSelectedItemDetail(final String encItemID) throws AppException {
        long itemID = 0;
        if (!StringUtils.isEmpty(encItemID)) {
            String decItemID = encryptDecrypt.decrypt(encItemID);
            if (NumberUtils.isNumber(decItemID)) {
                itemID = Long.parseLong(decItemID);
            }
        }

        return itemDAO.getSelectedItemDetail(itemID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertItemDetail(final ItemDTO itemObj, final long sessUserID) throws AppException {
        long dpCnt = itemDAO.checkDuplicateItems(itemObj.getItemCode(),itemObj.getItemName());
        if(dpCnt > 0)
        {
            return -1;
        }
        else
        {
            return itemDAO.insertItemDetail(itemObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateItemDetail(final List<ItemDTO> itemObj, final long sessUserID) throws AppException {

        long dpCnt=0;
        long itemID = 0;
        for(ItemDTO itr: itemObj){
        if (!StringUtils.isEmpty(itr.getEncFieldValue())) {
            String decItemID = encryptDecrypt.decrypt(itr.getEncFieldValue());
            if (NumberUtils.isNumber(decItemID)) {
                itemID = Long.parseLong(decItemID);
            }
        }
        
        itr.setItemID(itemID);        
        ItemDTO exisItemObj = this.getSelectedItemDetail(itr.getEncFieldValue());
        
        
        if((!itr.getItemCode().equalsIgnoreCase(exisItemObj.getItemCode())) &&
                (!itr.getItemName().equalsIgnoreCase(exisItemObj.getItemName())))
        {
            dpCnt = itemDAO.checkDuplicateItems(itr.getItemCode(),itr.getItemName());
        }
        if((!itr.getItemCode().equalsIgnoreCase(exisItemObj.getItemCode())) )
        {
            dpCnt = itemDAO.checkDuplicateItems(itr.getItemCode(),"0");
        }
        if((!itr.getItemName().equalsIgnoreCase(exisItemObj.getItemName())))
        {
            dpCnt = itemDAO.checkDuplicateItems("0",itr.getItemName());
        }
        
        }
        
        if(dpCnt > 0)
        {
            return -1;
        }
        else
        {
            return itemDAO.updateItemDetail(itemObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getCategoryWiseItemDetails(final long categoryID) throws AppException {
        return itemDAO.getCategoryWiseItemDetails(categoryID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateItems(final String itemCode, final String itemName) throws AppException {
        return itemDAO.checkDuplicateItems(itemCode,itemName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getCategoryWiseItemDeByCate(final long categoryID) throws AppException {
        return itemDAO.getCategoryWiseItemDeByCate(categoryID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemMasterDeById(final long itemID) throws AppException {
        return itemDAO.getItemMasterDeById(itemID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getTotalRowCount() throws AppException {
        return itemDAO.getTotalRowCount();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemDetailsPagination(final long endIndex, final long pageLength) throws AppException {
        return itemDAO.getItemDetailsPagination(endIndex, pageLength);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public ArrayList<String> getItemOnlyCodes(final String keywords) throws AppException {
        return itemDAO.getItemOnlyCodes(keywords);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ItemDTO> getItemMaDeByItemName(final String itemName) throws AppException {
        return itemDAO.getItemMaDeByItemName(itemName);
    }

}
