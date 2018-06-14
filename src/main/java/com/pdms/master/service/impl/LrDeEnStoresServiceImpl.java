/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.LrEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.LrDeEnStoresDAOImpl;
import com.pdms.master.dao.impl.LrDetailsEnDAOImpl;
import com.pdms.master.dto.LrEnDeFrStoresDTO;
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
public class LrDeEnStoresServiceImpl {
    private static final Logger logger = Logger.getLogger(LrDeEnStoresServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private LrDeEnStoresDAOImpl lrstodAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public LrDeEnStoresServiceImpl(){
        
    }
    
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertLRNoStoresDetail(final List<LrEnDeFrStoresDTO> lrObj, final long sessUserID) throws AppException
    {
        return lrstodAOImpl.insertLRNoStoresDetail(lrObj, sessUserID);
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEnDeFrStoresDTO> getLrEntryStoresDetails() throws AppException
    {
        
            return lrstodAOImpl.getLrEntryStoresDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEnDeFrStoresDTO> getLrStoresDetailsReById(final long id) throws AppException
    {
        
            return lrstodAOImpl.getLrStoresDetailsReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateLrStoresDetailsDetail(final List<LrEnDeFrStoresDTO> lrObj, final long sessUserID) throws AppException
    {
        
            return lrstodAOImpl.updateLrStoresDetailsDetail(lrObj, sessUserID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEnDeFrStoresDTO> getLrStoresReByPoNo(final long ponumber) throws AppException
    {
        
            return lrstodAOImpl.getLrStoresReByPoNo(ponumber);
    }
    
}
