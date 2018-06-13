/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.purchase.services.Impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.service.impl.IbcNumbeServiceImpl;
import com.pdms.purchase.dao.daoImpl.AmendmentDAOImpl;
import com.pdms.purchase.dto.AmendmentDTO;
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
public class AmendmentServiceImpl {
    
    private static final Logger logger = Logger.getLogger(AmendmentServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private AmendmentDAOImpl amendmentDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public AmendmentServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertAmendmentDetail(final List<AmendmentDTO> ameObj, final long sessUserID) throws AppException
    {
       
            return amendmentDAOImpl.insertAmendmentDetail(ameObj, sessUserID);
             
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<AmendmentDTO> getAmendmentRecord() throws AppException
    {
       
            return amendmentDAOImpl.getAmendmentRecord();
             
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<AmendmentDTO> getAmendmentReById(final long id) throws AppException
    {
       
            return amendmentDAOImpl.getAmendmentReById(id);
             
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateAmendmentDetail(final List<AmendmentDTO> ameObj, final long sessUserID) throws AppException
    {
       
            return amendmentDAOImpl.updateAmendmentDetail(ameObj, sessUserID);
             
    }
    
}
