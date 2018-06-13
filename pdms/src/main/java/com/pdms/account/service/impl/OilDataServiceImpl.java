/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.account.service.impl;

import com.pdms.account.dao.impl.OilDataDAOImpl;
import com.pdms.account.dao.impl.VoucherDaEnDAOImpl;
import com.pdms.account.dto.OilDataDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.exception.AppException;
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
public class OilDataServiceImpl {
     private static final Logger logger = Logger.getLogger(OilDataServiceImpl.class);
    
    @Autowired
    private OilDataDAOImpl oilDataDAOImpl;
    /*
    Default Constructor 
     */
    public OilDataServiceImpl(){
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertOilDaEntry(final List<OilDataDTO> oilObj, final long sessUserID) throws AppException {
        return oilDataDAOImpl.insertOilDaEntry(oilObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<OilDataDTO> getOilDataRecord() throws AppException {
        return oilDataDAOImpl.getOilDataRecord();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<OilDataDTO> getOilDataReByID(final long id) throws AppException {
        return oilDataDAOImpl.getOilDataReByID(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateOilDataDetail(final List<OilDataDTO> oilObj, final long sessUserID) throws AppException {
        return oilDataDAOImpl.updateOilDataDetail(oilObj, sessUserID);
    }
    
}
