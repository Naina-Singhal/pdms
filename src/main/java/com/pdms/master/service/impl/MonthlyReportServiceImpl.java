/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EnquiryMaDAOImpl;
import com.pdms.master.dao.impl.MonthlyReportDAOImpl;
import com.pdms.master.dto.EnquiryDTO;
import com.pdms.master.dto.MonthlyReportDTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author naagu
 */
@Service
public class MonthlyReportServiceImpl {
    private static final Logger logger = Logger.getLogger(MonthlyReportServiceImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private MonthlyReportDAOImpl monthlyDAOImpl;
    
     /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    
    public MonthlyReportServiceImpl(){
        
    }
    
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertMonthlyReportDetail(final List<MonthlyReportDTO> monObj, final long sessUserID) throws AppException
    {
        
            return monthlyDAOImpl.insertMonthlyReportDetail(monObj, sessUserID);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MonthlyReportDTO> getMonthlyReportRecord() throws AppException
    {
        
            return monthlyDAOImpl.getMonthlyReportRecord();
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public List<MonthlyReportDTO> getMonthlyReportRecordById(final long id) throws AppException
    {
        
            return monthlyDAOImpl.getMonthlyReportRecordById(id);
                
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateMonthlyReportDetail(final List<MonthlyReportDTO> monObj, final long sessUserID) throws AppException
    {
        
            return monthlyDAOImpl.updateMonthlyReportDetail(monObj, sessUserID);
                
    }
}
