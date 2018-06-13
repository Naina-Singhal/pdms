/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.CategoryDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hpasupuleti
 */
@Repository
public class CategoryDAOImpl {
    
    private static final Logger logger = Logger.getLogger(CategoryDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
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
    public CategoryDAOImpl() {

    }

    /*
    Default Constructor 
     */
    
    
    public List<CategoryDTO> getAllCategories() throws AppException
    {
        List<CategoryDTO> categoriesLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append("SELECT pk_category_id,category_code,category_name ");
            sb.append(" FROM category_master  ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    CategoryDTO categoryObj = new CategoryDTO();
                    
                    categoryObj.setCategoryID((Long)(resultMap.get("pk_category_id")));
                    categoryObj.setCategoryCode((String)(resultMap.get("category_code")));
                    categoryObj.setCategoryName((String)(resultMap.get("category_name")));
                    categoryObj.setEncFieldValue
                            (encryptDecrypt.encrypt(categoryObj.getCategoryID()+""));
                    
                    categoriesLi.add(categoryObj);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllCategories:",e);
        }
        return categoriesLi;
    }
    
    
    public CategoryDTO getSelectedCategoryByID(final long categoryID) throws AppException
    {            
        CategoryDTO categoryObj = new CategoryDTO();
        
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append("SELECT pk_category_id,category_code,category_name,description ");
            sb.append(" FROM category_master  ");
            sb.append(" WHERE pk_category_id=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{categoryID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    categoryObj.setCategoryID((Long)(resultMap.get("pk_category_id")));
                    categoryObj.setCategoryCode((String)(resultMap.get("category_code")));
                    categoryObj.setCategoryName((String)(resultMap.get("category_name")));
                    categoryObj.setDescription((String)(resultMap.get("description")));
                    categoryObj.setEncFieldValue
                            (encryptDecrypt.encrypt(categoryObj.getCategoryID()+""));
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedCategoryByID:",e);
        }
        return categoryObj;
    }
    
    
    public int insertCategoryDetail(final CategoryDTO categoryObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO category_master ");
            sb.append("(category_code,category_name,status_id,description,created_by,created_on) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{categoryObj.getCategoryCode().trim(),
                        categoryObj.getCategoryName().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        categoryObj.getDescription().trim(),
                        sessUserID, dateUtil.generateDBCurrentDateInString()
                    });
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertCategoryDetail:",e);
        }
        
        return retVal;
    }
    
    
    public int updateCategoryDetail(final CategoryDTO categoryObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE category_master SET ");
            sb.append("category_code=?,category_name=?,status_id=?,description=?,modified_by=?,modified_on=? ");
            sb.append(" WHERE pk_category_id=? ");
            
            
            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{categoryObj.getCategoryCode().trim(),
                        categoryObj.getCategoryName().trim(),
                        categoryObj.getRowStatusKey(),
                        categoryObj.getDescription().trim(),
                        sessUserID, 
                        dateUtil.generateDBCurrentDateInString(),
                        categoryObj.getCategoryID()
                    });
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateCategoryDetail:",e);
        }
        
        return retVal;
    }
    
    
    public int deleteCategoryDetail(final long categoryID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" DELETE FROM category_master ");
            sb.append(" WHERE pk_category_id=? ");
            
            
            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{categoryID});
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : deleteCategoryDetail:",e);
        }
        
        return retVal;
    }
    
    
    public int checkDuplicateCategory(final String catName, final String catCode) throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();

            if(catName.equalsIgnoreCase("0"))
            {
                sb.append(" SELECT COUNT(1)  ");
                sb.append(" FROM category_master ");
                sb.append(" WHERE LOWER(category_code)=?  ");

                retVal = getJdbcTemplate().queryForObject(sb.toString(),
                        new Object[]{
                            catCode.trim().toLowerCase()},
                        Integer.class);
            }
            else if(catCode.equalsIgnoreCase("0"))
            {
                sb.append(" SELECT COUNT(1)  ");
                sb.append(" FROM category_master ");
                sb.append(" WHERE LOWER(category_name)=? ");

                retVal = getJdbcTemplate().queryForObject(sb.toString(),
                        new Object[]{
                            catName.toLowerCase()},
                        Integer.class);
            }
            else
            {
                sb.append(" SELECT COUNT(1)  ");
                sb.append(" FROM category_master ");
                sb.append(" WHERE LOWER(category_code)=? AND LOWER(category_name)=? ");

                retVal = getJdbcTemplate().queryForObject(sb.toString(),
                        new Object[]{
                            catCode.trim().toLowerCase(),
                            catName.toLowerCase()},
                        Integer.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : checkDuplicateCategory:", e);
        }
        return retVal;
    }
    
}
