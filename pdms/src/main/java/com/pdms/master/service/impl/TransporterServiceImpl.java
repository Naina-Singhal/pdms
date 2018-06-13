/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.dto.TransporterDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PaymentDAOImpl;
import com.pdms.master.dao.impl.TransporterDAOImpl;
import java.util.List;
import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;
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
public class TransporterServiceImpl {
    private static final Logger logger = Logger.getLogger(TransporterServiceImpl.class);
    
    @Autowired
    private TransporterDAOImpl transporterDAOImpl;
    
    public TransporterServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertTransporterData(final List<TransporterDTO> transObj, final long sessUserID) throws AppException {
        long dupCnt = 0;
        for (TransporterDTO obj : transObj) {

            dupCnt = this.checkDuplicateTransporter("0", obj.getTranspName());

            if (dupCnt == 0) {

                dupCnt = this.checkDuplicateTransporter(obj.getTransCode(), "0");

            }
            if (dupCnt == 0) {

                dupCnt = this.checkDuplicateTransporter(obj.getTransCode(), obj.getTranspName());

            }
            logger.info("---while save----" + dupCnt);
        }
        if (dupCnt > 0) {
            return -1;
        } else {
            return transporterDAOImpl.insertTransporterData(transObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TransporterDTO> getTransportRecord() throws AppException
    {
        
            return transporterDAOImpl.getTransportRecord();
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<TransporterDTO> getTransporterReById(final long id) throws AppException
    {
        
            return transporterDAOImpl.getTransporterReById(id);
        
    }
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long updateTransporterDetail(final List<TransporterDTO> transObj, final long sessUserID) throws AppException
    {        
        long dupCnt = 0;
        for(TransporterDTO obj: transObj){
            
            TransporterDTO existObject = this.getSelectedTransporter(obj.getTranspId());            
            if(!(existObject.getTransCode().equalsIgnoreCase(obj.getTransCode())) &&
                    !(existObject.getTranspName().equalsIgnoreCase(obj.getTranspName()))){
                dupCnt = this.checkDuplicateTransporter(obj.getTransCode(), obj.getTranspName());
                
                if(dupCnt == 0){
                    dupCnt = this.checkDuplicateTransporter(obj.getTransCode(), "0");                    
                }
                if(dupCnt == 0){
                    dupCnt = this.checkDuplicateTransporter("0", obj.getTranspName());                   
                }
                
                
            }else if(!(existObject.getTransCode().equalsIgnoreCase(obj.getTransCode()))){
                
                dupCnt = this.checkDuplicateTransporter(obj.getTransCode(), "0");
                
            }else if(!(existObject.getTranspName().equalsIgnoreCase(obj.getTranspName()))){
                
                dupCnt = this.checkDuplicateTransporter("0", obj.getTranspName());
                
            }
        }
        if (dupCnt > 0) {
            return -1;
        } else {
            return transporterDAOImpl.updateTransporterDetail(transObj, sessUserID);
        } 
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateTransporter(final String gCode, final String gName) throws AppException {
        return transporterDAOImpl.checkDuplicateTransporter(gCode, gName);         
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public TransporterDTO getSelectedTransporter(final long transporterID) throws AppException
    {
        return transporterDAOImpl.getSelectedTransporter(transporterID);
    }
}
