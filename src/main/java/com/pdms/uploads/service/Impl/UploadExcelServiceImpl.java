/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.uploads.service.Impl;

import com.pdms.dao.impl.CsrvPreparationDAOImpl;
import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.PoEntryServiceImpl;
import com.pdms.uploads.dao.Impl.UploadExcelDAOImpl;
import com.pdms.uploads.dto.ExcelUploadDTO;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author STEINMETZ
 */
@Service
public class UploadExcelServiceImpl {
   
     private static final Logger logger = Logger.getLogger(UploadExcelServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private UploadExcelDAOImpl uploadExcelDAOImpl;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
     public UploadExcelServiceImpl(){
        
    }

    /*
    Default Constructor 
     */
    
    /*
        Methods 
     */
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertUploadExcel(List<ExcelUploadDTO> model, final long sessUserID) throws AppException
    {
        
            return uploadExcelDAOImpl.insertUploadExcel(model, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int getUploadExcelList(MultipartFile file, final long sessUserID) throws AppException
    {
        
            return uploadExcelDAOImpl.getUploadExcelList(file, sessUserID);
        
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int getUploadExcelXlsxList(MultipartFile file, final long sessUserID) throws AppException
    {
        
            return uploadExcelDAOImpl.getUploadExcelXlsxList(file, sessUserID);
        
    }
}
