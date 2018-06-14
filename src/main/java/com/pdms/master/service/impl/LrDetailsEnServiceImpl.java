/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.LrEntryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
import com.pdms.master.dao.impl.LrDetailsEnDAOImpl;
import com.pdms.master.dto.RtgsDTO;
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
public class LrDetailsEnServiceImpl {
    
    private static final Logger logger = Logger.getLogger(LrDetailsEnServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private LrDetailsEnDAOImpl lrDetailsEnDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public LrDetailsEnServiceImpl(){
        
    }
    
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public int insertLRNoDetail(final List<LrEntryDTO> lrObj, final long sessUserID) throws AppException
    {
        long dupCont = 0;
        for (LrEntryDTO cdObj : lrObj) {
            dupCont = this.checkDuplicateLrDetails("0", cdObj.getNameOfSupplr());            
            if (dupCont == 0) {
                dupCont = this.checkDuplicateLrDetails(cdObj.getLrNumber(), "0");                
            }
            if (dupCont == 0) {
                dupCont = this.checkDuplicateLrDetails(cdObj.getLrNumber(), cdObj.getNameOfSupplr());                
            }
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return lrDetailsEnDAOImpl.insertLRNoDetail(lrObj, sessUserID);
        }
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
      public List<LrEntryDTO> getLrEntryDetails() throws AppException
    {
        
            return lrDetailsEnDAOImpl.getLrEntryDetails();
        
    }
      
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateLrDetails(final String lrNumber, final String suplier) throws AppException
    {
        
            return lrDetailsEnDAOImpl.checkDuplicateLrDetails(lrNumber, suplier);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEntryDTO> getLrDetailsReById(final long id) throws AppException
    {
        
            return lrDetailsEnDAOImpl.getLrDetailsReById(id);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public LrEntryDTO getSelectedLrDetails(final long LrEntryID) throws AppException
    {
        
            return lrDetailsEnDAOImpl.getSelectedLrDetails(LrEntryID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateLeDetailsDetail(final List<LrEntryDTO> LrObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(LrEntryDTO Obj: LrObj){            
            LrEntryDTO existObject = this.getSelectedLrDetails(Obj.getLrEntryID());
            if(!(existObject.getLrNumber().equalsIgnoreCase(Obj.getLrNumber())) &&
                    !(existObject.getNameOfSupplr().equalsIgnoreCase(Obj.getNameOfSupplr()))){
                dupCount = this.checkDuplicateLrDetails(Obj.getLrNumber(), Obj.getNameOfSupplr());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicateLrDetails(Obj.getLrNumber(), "0");                    
                }
                if(dupCount == 0){
                    dupCount = this.checkDuplicateLrDetails("0", Obj.getNameOfSupplr());                   
                }
            }else if(!(existObject.getLrNumber().equalsIgnoreCase(Obj.getLrNumber()))){
                
                dupCount = this.checkDuplicateLrDetails(Obj.getLrNumber(), "0");
                
            }else if(!(existObject.getNameOfSupplr().equalsIgnoreCase(Obj.getNameOfSupplr()))){
                
                dupCount = this.checkDuplicateLrDetails("0", Obj.getNameOfSupplr());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return lrDetailsEnDAOImpl.updateLeDetailsDetail(LrObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEntryDTO> getLrDetailsReByPONO(final long PoNo) throws AppException
    {
        
            return lrDetailsEnDAOImpl.getLrDetailsReByPONO(PoNo);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<LrEntryDTO> getLrDeByPoFrPdf(final long lrid) throws AppException
    {
        
            return lrDetailsEnDAOImpl.getLrDeByPoFrPdf(lrid);
        
    }
}
