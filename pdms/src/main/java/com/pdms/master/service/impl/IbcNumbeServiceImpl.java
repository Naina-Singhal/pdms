/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.DescriptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DescriptionDAOImpl;
import com.pdms.master.dao.impl.IbcNumberDAOImpl;
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
public class IbcNumbeServiceImpl {
    
    private static final Logger logger = Logger.getLogger(IbcNumbeServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private IbcNumberDAOImpl ibcNumberDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public IbcNumbeServiceImpl(){
        
    }
    
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertIbcNoDetail(final List<IbcNumberDTO> ibcObj, final long sessUserID) throws AppException
    {
//        long dupCont = 0;
//        for (IbcNumberDTO cdObj : ibcObj) {
//            dupCont = this.checkDuplicateIbc(0l, cdObj.getNameOfSupplr());            
//            if (dupCont == 0) {
//                dupCont = this.checkDuplicateIbc(cdObj.getPoNumber(), "0");                
//            }
//            if (dupCont == 0) {
//                dupCont = this.checkDuplicateIbc(cdObj.getPoNumber(), cdObj.getNameOfSupplr());                
//            }
//        }       
//        if (dupCont > 0) {
//            return -1;
//        } else {
            return ibcNumberDAOImpl.insertIbcNoDetail(ibcObj, sessUserID);
//        }        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IbcNumberDTO> getIbcNoDetails() throws AppException
    {
        
            return ibcNumberDAOImpl.getIbcNoDetails();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateIbc(final Long ibcNo, final String supName) throws AppException {
        
        return ibcNumberDAOImpl.checkDuplicateIbc(ibcNo, supName);
                       
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IbcNumberDTO> getIbcNoReById(final long id) throws AppException {
        
        return ibcNumberDAOImpl.getIbcNoReById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public IbcNumberDTO getSelectedIbcNumber(final long ibcNoId) throws AppException {
        
        return ibcNumberDAOImpl.getSelectedIbcNumber(ibcNoId);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public int updateIbcNoDetail(final List<IbcNumberDTO> ibcObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        long exitOrNot = 0;
        for(IbcNumberDTO Obj: ibcObj){            
            IbcNumberDTO existObject = this.getSelectedIbcNumber(Obj.getIbcID());
//            if(!(existObject.getPoNumber().equals(Obj.getPoNumber())) &&
//                    !(existObject.getNameOfSupplr().equalsIgnoreCase(Obj.getNameOfSupplr()))){
//                dupCount = this.checkDuplicateIbc(Obj.getPoNumber(), Obj.getNameOfSupplr());
//                
//                if(dupCount == 0){
//                    dupCount = this.checkDuplicateIbc(Obj.getPoNumber(), "0");                    
//                }
//                if(dupCount == 0){
//                    dupCount = this.checkDuplicateIbc(0L, Obj.getNameOfSupplr());                   
//                }
                
                
//            }else if(!(existObject.getPoNumber().equals(Obj.getPoNumber()))){
//                
//                dupCount = this.checkDuplicateIbc(Obj.getPoNumber(), "0");
//                
//            }else if(!(existObject.getNameOfSupplr().equalsIgnoreCase(Obj.getNameOfSupplr()))){
//                
//                dupCount = this.checkDuplicateIbc(0L, Obj.getNameOfSupplr());
//                
//            }
            
            exitOrNot = this.ibcNoExistOrNotInVouDaRe(Obj.getIbcNumber());
            logger.info(exitOrNot);
        }       
        
 //       if (dupCount > 0) {
 //           return -1;
 //       } else 
        if(exitOrNot > 0){
            return -2;
        }else {
            return ibcNumberDAOImpl.updateIbcNoDetail(ibcObj, sessUserID);
        } 
    }
     
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long getIbcNoForIncrement() throws AppException {
        
        return ibcNumberDAOImpl.getIbcNoForIncrement();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<IbcNumberDTO> getIbcNoDeByPoNo(final long poNum) throws AppException {
        
        return ibcNumberDAOImpl.getIbcNoDeByPoNo(poNum);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long ibcNoExistOrNotInVouDaRe(final long ibcNo) throws AppException {
        
        return ibcNumberDAOImpl.ibcNoExistOrNotInVouDaRe(ibcNo);
                
    }
}
