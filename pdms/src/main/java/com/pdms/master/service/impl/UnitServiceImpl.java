/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.UnitDAOImpl;
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
public class UnitServiceImpl {
    
    private static final Logger logger = Logger.getLogger(UnitServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private UnitDAOImpl unitDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public UnitServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<UnitDTO> getAllUnits() throws AppException
    {
        return unitDAO.getAllUnits();
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public UnitDTO getSelectedUnit(final String encUnitID) throws AppException
    {
        long unitID = 0;
        if(!StringUtils.isEmpty(encUnitID))
        {
            String decUnitID = encryptDecrypt.decrypt(encUnitID);
            if(NumberUtils.isNumber(decUnitID))
            {
                unitID = Long.parseLong(decUnitID);
            }
        }
        return unitDAO.getSelectedUnit(unitID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertUnitDetail(final UnitDTO unitObj, final long sessUserID) throws AppException
    {
        long dupCnt = this.checkDuplicateUnit(unitObj.getUnitCode(), unitObj.getUnitName());
        if (dupCnt > 0) {
            return -1;
        } else {
            return unitDAO.insertUnitDetail(unitObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateUnitDetail(final UnitDTO unitObj, final long sessUserID) throws AppException
    {
        long unitID = 0;
        if(!StringUtils.isEmpty(unitObj.getEncFieldValue()))
        {
            String decUnitID = encryptDecrypt.decrypt(unitObj.getEncFieldValue());
            if(NumberUtils.isNumber(decUnitID))
            {
                unitID = Long.parseLong(decUnitID);
            }
        }
        
        unitObj.setUnitID(unitID);
        
        long dupCnt = 0;
        UnitDTO exisUnitObj = this.getSelectedUnit(unitObj.getEncFieldValue());

        if((exisUnitObj.getUnitCode().equalsIgnoreCase(unitObj.getUnitCode())) &&
                (exisUnitObj.getUnitName().equalsIgnoreCase(unitObj.getUnitName())))
        {
            dupCnt = this.checkDuplicateUnit(unitObj.getUnitCode(), unitObj.getUnitName());
        }
        else if(!(exisUnitObj.getUnitCode().equalsIgnoreCase(unitObj.getUnitCode())))
        {
            dupCnt = this.checkDuplicateUnit(unitObj.getUnitCode(), "0");
        }
        else if(!(exisUnitObj.getUnitName().equalsIgnoreCase(unitObj.getUnitName())))
        {
            dupCnt = this.checkDuplicateUnit("0", unitObj.getUnitName());
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return unitDAO.updateUnitDetail(unitObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateUnit(final String gCode, final String gName) throws AppException {
        return unitDAO.checkDuplicateUnit(gCode, gName);
    }
}
