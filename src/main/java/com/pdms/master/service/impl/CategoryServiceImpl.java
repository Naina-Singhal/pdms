/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.CategoryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.CategoryDAOImpl;
import com.pdms.utils.EncryptDecrypt;
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
public class CategoryServiceImpl {
    
    
    private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private CategoryDAOImpl categoryDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public CategoryServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<CategoryDTO> getAllCategories() throws AppException
    {
        return categoryDAO.getAllCategories();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public CategoryDTO getSelectedCategoryByID(final String encCategoryID) throws AppException
    { 
        long categoryID = 0;
        if(!StringUtils.isEmpty(encCategoryID))
        {
            String decCategoryID = encryptDecrypt.decrypt(encCategoryID);
            if(NumberUtils.isNumber(decCategoryID))
            {
                categoryID = Long.parseLong(decCategoryID);
            }
        }
        return categoryDAO.getSelectedCategoryByID(categoryID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertCategoryDetail(final CategoryDTO categoryObj, final long sessUserID) throws AppException
    {
        long dupCnt = this.checkDuplicateCategory
            (categoryObj.getCategoryName(), categoryObj.getCategoryCode());
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return categoryDAO.insertCategoryDetail(categoryObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateCategoryDetail(final CategoryDTO categoryObj, final long sessUserID) throws AppException
    {
        long categoryID = 0;
        if(!StringUtils.isEmpty(categoryObj.getEncFieldValue()))
        {
            String decCategoryID = encryptDecrypt.decrypt(categoryObj.getEncFieldValue());
            if(NumberUtils.isNumber(decCategoryID))
            {
                categoryID = Long.parseLong(decCategoryID);
            }
        }
        categoryObj.setCategoryID(categoryID);
        
        CategoryDTO exisCatObj = this.getSelectedCategoryByID(categoryObj.getEncFieldValue());
        long dupCnt=0;
        if(!exisCatObj.getCategoryCode().equalsIgnoreCase(categoryObj.getCategoryCode())
                &&(!exisCatObj.getCategoryName().equalsIgnoreCase(categoryObj.getCategoryName())))
        {
            dupCnt=this.checkDuplicateCategory(categoryObj.getCategoryName(), categoryObj.getCategoryCode());
        }
        else if(!exisCatObj.getCategoryCode().equalsIgnoreCase(categoryObj.getCategoryCode()))
        {
            dupCnt=this.checkDuplicateCategory("0", categoryObj.getCategoryCode());
        }
        else if(!exisCatObj.getCategoryName().equalsIgnoreCase(categoryObj.getCategoryName()))
        {
            dupCnt=this.checkDuplicateCategory(categoryObj.getCategoryName(), "0");
        }
        else
        {
            dupCnt=0;
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return categoryDAO.updateCategoryDetail(categoryObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int checkDuplicateCategory(final String catName, final String catCode) throws AppException {
        return categoryDAO.checkDuplicateCategory(catName, catCode);
    }
    
}
