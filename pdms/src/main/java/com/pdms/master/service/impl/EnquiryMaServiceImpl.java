/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
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
public class EnquiryMaServiceImpl {
    
    private static final Logger logger = Logger.getLogger(EnquiryMaServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private EnquiryMaDAOImpl enquiryMaDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public EnquiryMaServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertEnquiryDetail(final List<EnquiryDTO> enqObj, final long sessUserID) throws AppException
    {
        
            return enquiryMaDAOImpl.insertEnquiryDetail(enqObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EnquiryDTO> getEnquiryRecord() throws AppException
    {
        
            return enquiryMaDAOImpl.getEnquiryRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<EnquiryDTO> getEnquiryRecordById(final long id) throws AppException
    {
        
            return enquiryMaDAOImpl.getEnquiryRecordById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateEnquiryDetail(final List<EnquiryDTO> enqObj, final long sessUserID) throws AppException
    {
        
            return enquiryMaDAOImpl.updateEnquiryDetail(enqObj, sessUserID);
                
    }
}
