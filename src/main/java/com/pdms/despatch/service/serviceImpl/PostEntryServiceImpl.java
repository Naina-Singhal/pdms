/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.despatch.service.serviceImpl;

import com.pdms.account.dao.impl.DdNumberDAOImpl;
import com.pdms.account.dto.DdNumberDTO;
import com.pdms.account.service.impl.DdNumberServiceImpl;
import com.pdms.despatch.dao.daoImpl.PostEntryDAOImpl;
import com.pdms.despatch.dto.PostEntryDTO;
import com.pdms.despatch.itemsDto.PostEntryTempDTO;
import com.pdms.despatch.itemsDto.PostVenItemsDTO;
import com.pdms.exception.AppException;
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
public class PostEntryServiceImpl {
    
    private static final Logger logger = Logger.getLogger(PostEntryServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private PostEntryDAOImpl postEntryDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public PostEntryServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPostEntryDetails(final PostEntryTempDTO poObj, final long sessUserID) throws AppException
    {
        
            return postEntryDAOImpl.insertPostEntryDetails(poObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostEntryDTO> getPostEntryRecord() throws AppException
    {
        
            return postEntryDAOImpl.getPostEntryRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostEntryDTO> getPostEntryReById(final long id) throws AppException
    {
        
            return postEntryDAOImpl.getPostEntryReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePostEntryDetail(final List<PostEntryDTO> poObj, final long sessUserID) throws AppException
    {
        
            return postEntryDAOImpl.updatePostEntryDetail(poObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getDispatchNoNumForIncr() throws AppException
    {
        
            return postEntryDAOImpl.getDispatchNoNumForIncr();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PostVenItemsDTO> getPostEntryVenReByDisNo(final long dispatchNo) throws AppException
    {
        
            return postEntryDAOImpl.getPostEntryVenReByDisNo(dispatchNo);
                
    }
}
