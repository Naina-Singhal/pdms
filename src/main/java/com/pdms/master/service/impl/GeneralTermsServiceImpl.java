/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.GeneralTermsDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.GeneralTermsDTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author myassessment
 */
@Service
public class GeneralTermsServiceImpl {
    
    private static final Logger logger = Logger.getLogger(GeneralTermsServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private GeneralTermsDAOImpl termsDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public GeneralTermsServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertGeneralTermsDetail(final List<GeneralTermsDTO> termObj, final long sessUserID) throws AppException
    {
        
            return termsDAOImpl.insertGeneralTermsDetail(termObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<GeneralTermsDTO> getGeneralTermsRecord() throws AppException
    {
        
            return termsDAOImpl.getGeneralTermsRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<GeneralTermsDTO> getGeneralTermsReById(final long id) throws AppException
    {
        
            return termsDAOImpl.getGeneralTermsReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateGeneralTermsDetail(final List<GeneralTermsDTO> termObj, final long sessUserID) throws AppException
    {
        
            return termsDAOImpl.updateGeneralTermsDetail(termObj, sessUserID);
                
    }
}
