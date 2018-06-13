/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.TaxDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.TaxDTO;
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
public class TaxServiceImpl {
    private static final Logger logger = Logger.getLogger(TaxServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private TaxDAOImpl taxDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public TaxServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertTaxDetail(final List<TaxDTO> taxObj, final long sessUserID) throws AppException
    {
        
            return taxDAOImpl.insertTaxDetail(taxObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TaxDTO> getTaxRecord() throws AppException
    {
        
            return taxDAOImpl.getTaxRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TaxDTO> getTaxRecordById(final long id) throws AppException
    {
        
            return taxDAOImpl.getTaxRecordById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateTaxDetail(final List<TaxDTO> taxObj, final long sessUserID) throws AppException
    {
        
            return taxDAOImpl.updateTaxDetail(taxObj, sessUserID);
    }
}
