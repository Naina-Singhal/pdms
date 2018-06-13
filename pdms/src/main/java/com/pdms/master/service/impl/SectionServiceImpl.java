/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.SectionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.SectionDAOImpl;
import com.pdms.utils.EncryptDecrypt;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class SectionServiceImpl {
    
    private static final Logger logger = Logger.getLogger(SectionServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private SectionDAOImpl sectionDAO;
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public SectionServiceImpl() {

    }

    /*
    Default Constructor 
     */
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<SectionDTO> getAllSectionDetails(final HttpServletRequest request) throws AppException {
        
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String employeeType = "";
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            sessUserID = empDTO.getEmployeeID();
            employeeType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            logger.info("--------employeeType123------"+employeeType);
        }               
        return sectionDAO.getAllSectionDetails(employeeType);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public SectionDTO getSeclectedSectionByID(final String encSectionID) throws AppException {
        
        long sectionID = 0;
        if(!StringUtils.isEmpty(encSectionID))
        {
            String decSectionID = encryptDecrypt.decrypt(encSectionID);
            if(NumberUtils.isNumber(decSectionID))
            {
                sectionID = Long.parseLong(decSectionID);
            }
        }
        return sectionDAO.getSeclectedSectionByID(sectionID);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertSectionDetail(final SectionDTO sectionObj, final long sessUserID) throws AppException {
        
        long dupCnt = this.checkDuplicateSection(sectionObj.getSectionCode(),sectionObj.getSectionName());
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return sectionDAO.insertSectionDetail(sectionObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateSectionDetail(final SectionDTO sectionObj, final long sessUserID) throws AppException {
        long sectionID = 0;
        if(!StringUtils.isEmpty(sectionObj.getEncFieldValue()))
        {
            String decSectionID = encryptDecrypt.decrypt(sectionObj.getEncFieldValue());
            if(NumberUtils.isNumber(decSectionID))
            {
                sectionID = Long.parseLong(decSectionID);
            }
        }
        
        sectionObj.setSectionID(sectionID);
        
        long dupCnt = 0;
        
        SectionDTO exisSecObj = this.getSeclectedSectionByID(sectionObj.getEncFieldValue());
        if((!exisSecObj.getSectionCode().equalsIgnoreCase(sectionObj.getSectionCode()))
            &&(!exisSecObj.getSectionName().equalsIgnoreCase(sectionObj.getSectionName())))
        {
            dupCnt= this.checkDuplicateSection(sectionObj.getSectionCode(),
                    sectionObj.getSectionName());
        }
        else if((!exisSecObj.getSectionCode().equalsIgnoreCase(sectionObj.getSectionCode())))
        {
            dupCnt= this.checkDuplicateSection(sectionObj.getSectionCode(),"0");
        }
        else if((!exisSecObj.getSectionName().equalsIgnoreCase(sectionObj.getSectionName())))
        {
            dupCnt= this.checkDuplicateSection("0",sectionObj.getSectionName());
        }
        
        if(dupCnt > 0)
        {
            return -1;
        }
        else
        {
            return sectionDAO.updateSectionDetail(sectionObj, sessUserID);
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public long checkDuplicateSection(final String sectionCode,final String sectionName) throws AppException
    {
        return sectionDAO.checkDuplicateSection(sectionCode,sectionName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public SectionDTO getSeclectedSectionDetailByName(final String sectionName) throws AppException
    {
        return sectionDAO.getSeclectedSectionDetailByName(sectionName);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<SectionDTO> getSectionDeBySecCode(final String sectionCode) throws AppException
    {
        return sectionDAO.getSectionDeBySecCode(sectionCode);
    } 
}
