/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.service.serviceImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.service.impl.DdNumberServiceImpl;
import com.pdms.despatch.dao.daoImpl.PostageUpDAOImpl;
import com.pdms.despatch.dto.PostageCloseDTO;
import com.pdms.despatch.dto.PostageUpDTO;
import com.pdms.despatch.itemsDto.PostageItemsDTO;
import com.pdms.despatch.itemsDto.PostageTempDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naagu
 */
@Service
public class PostageUpServiceImpl {
    
    private static final Logger logger = Logger.getLogger(PostageUpServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private PostageUpDAOImpl postDAOImpl;
    
    @Autowired
    private DateUtil dateUtil;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public PostageUpServiceImpl(){
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertPostageUpDetails(final PostageTempDTO upObj, final long sessUserID) throws AppException {
        String curDate = null;
        int statu = 0;
        List<PostageUpDTO> po = upObj.getPostageDTO();
        for (PostageUpDTO p : po) {
            curDate = p.getToDayDate();
        }

        List<PostageCloseDTO> clos = this.getPostageCloseReByDate(curDate);
        for (PostageCloseDTO c : clos) {
            statu = c.getStatus();
        }
        if (statu == 1) {
            return -7;
        } else {
            return postDAOImpl.insertPostageUpDetails(upObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostageUpDTO> getPostageUpRecord() throws AppException
    {
        
            return postDAOImpl.getPostageUpRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostageUpDTO> getPostageReById(final long id) throws AppException
    {
        
            return postDAOImpl.getPostageReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePostageDetail(final List<PostageUpDTO> upObj, final long sessUserID) throws AppException
    {
        
            return postDAOImpl.updatePostageDetail(upObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int saveCloseButton(final List<PostageCloseDTO> closeObj, final long sessUserID) throws AppException
    {
        
            return postDAOImpl.saveCloseButton(closeObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostageCloseDTO> getPostageCloseReByDate(final String todayDate) throws AppException
    {
        
            return postDAOImpl.getPostageCloseReByDate(todayDate);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostageItemsDTO> getPostagePdfByDate(final String posdate) throws AppException
    {
        
            return postDAOImpl.getPostagePdfByDate(posdate);
                
    }
}
