/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.MasterLookUpDAOImpl;
import com.pdms.dto.MasterLookupDTO;
import com.pdms.exception.AppException;
import java.util.List;
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
public class MasterLookUpServiceImpl {

    private static final Logger logger = Logger.getLogger(MasterLookUpServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private MasterLookUpDAOImpl masterLookUpDAO;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public MasterLookUpServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MasterLookupDTO> getMasterEntriesByLookUpType(final String lookUpType) throws AppException {
        return masterLookUpDAO.getMasterEntriesByLookUpType(lookUpType);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public MasterLookupDTO getMasterLookUpByLookUpValueAndType(final String lookUpValue, final String lookUpType) throws AppException {
        return masterLookUpDAO.getMasterLookUpByLookUpValueAndType(lookUpValue, lookUpType);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getMasterLookUpIDByLookUpValueAndType(final String lookUpType, final String lookUpValue) throws AppException {
        return masterLookUpDAO.getMasterLookUpIDByLookUpValueAndType(lookUpType, lookUpValue);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public MasterLookupDTO getMasterLookUpByLookUpID(final long lookUpID) throws AppException
    {
        return masterLookUpDAO.getMasterLookUpByLookUpID(lookUpID);
    }
}
