 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.RtgsDAOImpl;
import com.pdms.master.dao.impl.SignatoryDAOImpl;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.master.dto.SignatoryDTO;
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
public class RtgsServiceImpl {
    private static final Logger logger = Logger.getLogger(RtgsServiceImpl.class);
    
    @Autowired
    private RtgsDAOImpl dAOImpl;
    
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertRtgsDetail(final List<RtgsDTO> rtgsObj, final long sessUserID) throws AppException {
        long dupCont = 0;
        for (RtgsDTO cdObj : rtgsObj) {
             
            dupCont = this.checkDuplicateRtgs(cdObj.getVendorCode()); 
        }       
        if (dupCont > 0) {
            return -1;
        } else {
            return dAOImpl.insertRtgsDetail(rtgsObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateRtgs(final String gCode) throws AppException {
        return dAOImpl.checkDuplicateRtgs(gCode);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RtgsDTO> getRtgsDetails() throws AppException {
        return dAOImpl.getRtgsDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<RtgsDTO> getRtgsReById(final long id) throws AppException {
        return dAOImpl.getRtgsReById(id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public RtgsDTO getSelectedRtgs(final long rtgsID) throws AppException {

        return dAOImpl.getSelectedRtgs(rtgsID);

    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
     public int updateRtgsDetail(final List<RtgsDTO> rtgsObj, final long sessUserID) throws AppException
    {   
        long dupCount = 0;
        for(RtgsDTO Obj: rtgsObj){            
            RtgsDTO existObject = this.getSelectedRtgs(Obj.getRtgsId());
            if(!(existObject.getVendorCode().equalsIgnoreCase(Obj.getVendorCode())) ){
                dupCount = this.checkDuplicateRtgs(Obj.getVendorCode());
                
                if(dupCount == 0){
                    dupCount = this.checkDuplicateRtgs(Obj.getVendorCode());                    
                }                
            }else if(!(existObject.getVendorCode().equalsIgnoreCase(Obj.getVendorCode()))){
                
                dupCount = this.checkDuplicateRtgs(Obj.getVendorCode());
                
            }
        }
        if (dupCount > 0) {
            return -1;
        } else {
            return dAOImpl.updateRtgsDetail(rtgsObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getRtgsNoFrIncre() throws AppException {

        return dAOImpl.getRtgsNoFrIncre();

    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<RtgsDTO> getRtgsNoByVenCode(final String venCode) throws AppException {
        return dAOImpl.getRtgsNoByVenCode(venCode);
    }
}
