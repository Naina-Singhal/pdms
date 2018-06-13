/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.HoaCodesDAOImpl;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dto.HoaCodesDTO;
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
public class HoaCodesServiceImpl {
     private static final Logger logger = Logger.getLogger(HoaCodesServiceImpl.class);
    
    @Autowired
    private HoaCodesDAOImpl hoaCodesDAOImpl;
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertHoaCodesDetail(final List<HoaCodesDTO> hoaObj, final long sessUserID) throws AppException {
        long dupCont = 0;
        for (HoaCodesDTO cdObj : hoaObj) {
            dupCont = this.checkDuplicateHoaCodes(cdObj.getAccountName(), cdObj.getShortCode());
           
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return hoaCodesDAOImpl.insertHoaCodesDetail(hoaObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateHoaCodes(final String hoa_Name, final String shortCode) throws AppException {
        return hoaCodesDAOImpl.checkDuplicateHoaCodes(hoa_Name, shortCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HoaCodesDTO> getHoaShortCodesDetails() throws AppException {
        return hoaCodesDAOImpl.getHoaShortCodesDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HoaCodesDTO> getHoaCodesReById(final long id) throws AppException {
        return hoaCodesDAOImpl.getHoaCodesReById(id);
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HoaCodesDTO> getSelectedHoaShortCodes(final long hoaCodeId) throws AppException {
        return hoaCodesDAOImpl.getSelectedHoaShortCodes(hoaCodeId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateHoaCodesDetails(final List<HoaCodesDTO> hoaObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(HoaCodesDTO Obj: hoaObj){            
            List<HoaCodesDTO> existObject = this.getSelectedHoaShortCodes(Obj.getHoaCodeId());            
            for (HoaCodesDTO a : existObject) {
                
                if (!(a.getShortCode().equalsIgnoreCase(Obj.getShortCode())) ) {
                    
                    dupCount = this.checkDuplicateHoaCodes(a.getAccountName(), Obj.getShortCode());
                    
                } 
            }
        }        
        if (dupCount > 0) {
            return -1;
        } else {
            return hoaCodesDAOImpl.updateHoaCodesDetails(hoaObj, sessUserID);
        } 
    }
    
     @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<HoaCodesDTO> getHoaCodesReByAcName(final String acName) throws AppException {
        return hoaCodesDAOImpl.getHoaCodesReByAcName(acName);
    }
}
