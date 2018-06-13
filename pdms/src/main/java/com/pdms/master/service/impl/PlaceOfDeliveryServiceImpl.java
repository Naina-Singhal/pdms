/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PlaceOfDeliveryDAOImpl;
import com.pdms.utils.EncryptDecrypt;
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
 * @author hpasupuleti
 */
@Service
public class PlaceOfDeliveryServiceImpl {
    
    private static final Logger logger = Logger.getLogger(PlaceOfDeliveryServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private PlaceOfDeliveryDAOImpl podDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public PlaceOfDeliveryServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<PlaceOfDeliveryDTO> getAllPlaceOfDeliveryDetails() throws AppException
    {
        return podDAO.getAllPlaceOfDeliveryDetails();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public PlaceOfDeliveryDTO getSelectedPlaceOfDeliveryDetail(final String encPodID) throws AppException 
    {
        long podID = 0;
        if(!StringUtils.isEmpty(encPodID))
        {
            String decPodID = encryptDecrypt.decrypt(encPodID);
            if(NumberUtils.isNumber(decPodID))
            {
                podID = Long.parseLong(decPodID);
            }
        }
        
        return podDAO.getSelectedPlaceOfDeliveryDetail(podID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertPlaceOfDeliveryDetail(final PlaceOfDeliveryDTO podObj, final long sessUserID)
            throws AppException
    {
        long dupCnt = this.checkDuplicatePOD(podObj.getPlaceOfDelivery());
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return podDAO.insertPlaceOfDeliveryDetail(podObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updatePlaceOfDeliveryDetail(final PlaceOfDeliveryDTO podObj, final long sessUserID)
            throws AppException
    {
        long podID = 0;
        if(!StringUtils.isEmpty(podObj.getEncFieldValue()))
        {
            String decPodID = encryptDecrypt.decrypt(podObj.getEncFieldValue());
            if(NumberUtils.isNumber(decPodID))
            {
                podID = Long.parseLong(decPodID);
            }
        }
        
        podObj.setPlaceOfDeliveryID(podID);
        
        PlaceOfDeliveryDTO exisPodObj = this.getSelectedPlaceOfDeliveryDetail(podObj.getEncFieldValue());
        
        long dupCnt=0;
        if(!exisPodObj.getPlaceOfDelivery().equalsIgnoreCase(podObj.getPlaceOfDelivery())){
            dupCnt = this.checkDuplicatePOD(podObj.getPlaceOfDelivery());
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return podDAO.updatePlaceOfDeliveryDetail(podObj, sessUserID);
        }
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicatePOD(final String pod) throws AppException
    {
        return podDAO.checkDuplicatePOD(pod);
    }
    
}
