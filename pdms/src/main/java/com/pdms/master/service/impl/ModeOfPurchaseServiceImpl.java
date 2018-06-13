/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.dto.HeadOfAccountDTO;
import com.pdms.dto.ModeOfPurchaseDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.ModeOfPurchaseDAOImpl;
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
public class ModeOfPurchaseServiceImpl {

    private static final Logger logger = Logger.getLogger(ModeOfPurchaseServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private ModeOfPurchaseDAOImpl mopDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public ModeOfPurchaseServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<ModeOfPurchaseDTO> getAllModeOfPurchaseDetails() throws AppException {
        return mopDAO.getAllModeOfPurchaseDetails();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public ModeOfPurchaseDTO getSelectedModeOfPurchaseDetail(final String encMopID) throws AppException {

        long mopID = 0;
        if (!StringUtils.isEmpty(encMopID)) {
            String decMopID = encryptDecrypt.decrypt(encMopID);
            if (NumberUtils.isNumber(decMopID)) {
                mopID = Long.parseLong(decMopID);
            }
        }

        return mopDAO.getSelectedModeOfPurchaseDetail(mopID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertModeOfPurchaseDetail(final ModeOfPurchaseDTO mopObj, final long sessUserID)
            throws AppException {
        
        long dpCnt = this.checkDuplicateMOP(mopObj.getModeOfPurchase());
        if(dpCnt > 0)
        {
            return -1;
        }
        else
        {
            return mopDAO.insertModeOfPurchaseDetail(mopObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateModeOfPurchaseDetail(final ModeOfPurchaseDTO mopObj, final long sessUserID)
            throws AppException {

        long mopID = 0;
        if (!StringUtils.isEmpty(mopObj.getEncFieldValue())) {
            String decMopID = encryptDecrypt.decrypt(mopObj.getEncFieldValue());
            if (NumberUtils.isNumber(decMopID)) {
                mopID = Long.parseLong(decMopID);
            }
        }

        mopObj.setModeOfPurchaseID(mopID);

        ModeOfPurchaseDTO exisMopObj = this.getSelectedModeOfPurchaseDetail(mopObj.getEncFieldValue());
        
        long dupCnt =0;
        if(!exisMopObj.getModeOfPurchase().equalsIgnoreCase(mopObj.getModeOfPurchase())){
            dupCnt = this.checkDuplicateMOP(mopObj.getModeOfPurchase());
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return mopDAO.updateModeOfPurchaseDetail(mopObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateMOP(final String mop) throws AppException
    {
        return mopDAO.checkDuplicateMOP(mop);
    }

}
