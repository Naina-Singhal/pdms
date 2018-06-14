/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dto.DescriptionDTO;
import com.pdms.dto.PaymentDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DescriptionDAOImpl;
import com.pdms.master.dao.impl.PaymentDAOImpl;
import com.pdms.master.service.impl.PaymentServiceImpl;
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
public class DescriptionServiceImpl {
    private static final Logger logger = Logger.getLogger(PaymentServiceImpl.class);
    
    @Autowired
    private DescriptionDAOImpl descriptionDAOImpl;
    
    public DescriptionServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertDescriptionDetail(final List<DescriptionDTO> desObj, final long sessUserID) throws AppException
    {
        
            return descriptionDAOImpl.insertDescriptionDetail(desObj, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DescriptionDTO> getDescriptionDetails() throws AppException
    {
        
            return descriptionDAOImpl.getDescriptionDetails();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<DescriptionDTO> getDescriptionReById(final long id) throws AppException
    {
        
            return descriptionDAOImpl.getDescriptionReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateDescriptionDetail(final List<DescriptionDTO> DesObj, final long sessUserID) throws AppException
    {   
             return descriptionDAOImpl.updateDescriptionDetail(DesObj, sessUserID);
             
    }
    
}
