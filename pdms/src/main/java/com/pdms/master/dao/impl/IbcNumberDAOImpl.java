/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.DescriptionDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.RtgsDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author myassessment
 */
@Repository
public class IbcNumberDAOImpl {
    
     private static final Logger logger = Logger.getLogger(DescriptionDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DateUtil dateUtil;
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public IbcNumberDAOImpl(){
        
    }
    
    
    public int insertIbcNoDetail(final List<IbcNumberDTO> ibcObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO ibc_Number_details  ");
            sb.append("(ibcID,poNumber,nameOfSupplr,billNumber,billDate,billAmount, "); 
            sb.append("ibcNumber,ibcDate,ibcAmount,ibcBank,session_id,ibc_cur_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(IbcNumberDTO IBCDTO : ibcObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{IBCDTO.getIbcID(),
                        IBCDTO.getPoNumber(),
                        IBCDTO.getNameOfSupplr().trim(),
                        IBCDTO.getBillNumber().trim(),
                        IBCDTO.getBillDate().trim(),
                        IBCDTO.getBillAmount(),
                        IBCDTO.getIbcNumber(),
                        IBCDTO.getIbcDate().trim(),
                        IBCDTO.getIbcAmount(),
                        IBCDTO.getIbcBank().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertIbcNoDetail:",e);
        }
        return retVal;
    }
    
     public List<IbcNumberDTO> getIbcNoDetails() throws AppException {
        List<IbcNumberDTO> IbcDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM ibc_Number_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IbcNumberDTO indto = new IbcNumberDTO();
                    indto.setIbcID((Long) (retMap.get("ibcID")));
                    indto.setPoNumber((Long) (retMap.get("poNumber")));
                    indto.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    indto.setBillNumber((String) (retMap.get("billNumber")));
                    indto.setBillDate((String) (retMap.get("billDate")));
                    indto.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    indto.setIbcNumber((Long) (retMap.get("ibcNumber")));
                    indto.setIbcDate((String) (retMap.get("ibcDate")));
                    indto.setIbcAmount((BigDecimal) (retMap.get("ibcAmount")));
                    indto.setIbcBank((String) (retMap.get("ibcBank")));
                    IbcDTO.add(indto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIbcNoDetails:", e);
        }
        return IbcDTO;
    }
     
    public long checkDuplicateIbc(final Long PoNo, final String supName) throws AppException {
        long dpCnt = 0;
        try {            
            if(supName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM ibc_Number_details ";
                qry+=" WHERE LOWER(poNumber) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{PoNo},
                        Long.class);//                
            }
            else if(PoNo.equals(0))
            {
                String qry = "SELECT COUNT(1) FROM ibc_Number_details ";
                qry+=" WHERE LOWER(nameOfSupplr) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{supName.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM ibc_Number_details ";
                qry+=" WHERE LOWER(nameOfSupplr)=? AND LOWER(poNumber) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{supName.trim().toLowerCase(),PoNo},
                        Long.class);//                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateIbc:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public List<IbcNumberDTO> getIbcNoReById(final long id) throws AppException {
        List<IbcNumberDTO> IBDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM ibc_Number_details WHERE ibcID ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IbcNumberDTO IbC = new IbcNumberDTO();
                    IbC.setIbcID((Long) (retMap.get("ibcID")));
                    IbC.setPoNumber((Long) (retMap.get("poNumber")));
                    IbC.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    IbC.setBillNumber((String) (retMap.get("billNumber")));
                    IbC.setBillDate((String) (retMap.get("billDate")));
                    IbC.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    IbC.setIbcNumber((Long) (retMap.get("ibcNumber")));
                    IbC.setIbcDate((String) (retMap.get("ibcDate")));
                    IbC.setIbcAmount((BigDecimal) (retMap.get("ibcAmount")));
                    IbC.setIbcBank((String) (retMap.get("ibcBank")));                     
                    IBDTO.add(IbC);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIbcNoReById:", e);
        }
        return IBDTO;
    }
    
    public IbcNumberDTO getSelectedIbcNumber(final long ibcNoId) throws AppException
    {
        IbcNumberDTO ibcObj = new IbcNumberDTO();
        try
        {
            StringBuilder sb = new StringBuilder(); 
            sb.append(" SELECT * FROM ibc_Number_details WHERE  ibcID=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{ibcNoId});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> retMap : resultList) {
                    ibcObj.setIbcID((Long) (retMap.get("ibcID")));
                    ibcObj.setPoNumber((Long) (retMap.get("poNumber")));
                    ibcObj.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    ibcObj.setBillNumber((String) (retMap.get("billNumber")));
                    ibcObj.setBillDate((String) (retMap.get("billDate")));
                    ibcObj.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    ibcObj.setIbcNumber((Long) (retMap.get("ibcNumber")));
                    ibcObj.setIbcDate((String) (retMap.get("ibcDate")));
                    ibcObj.setIbcAmount((BigDecimal) (retMap.get("ibcAmount")));
                    ibcObj.setIbcBank((String) (retMap.get("ibcBank")));              
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedRtgs:",e);
        }
        return ibcObj;
    }
    
    public int updateIbcNoDetail(final List<IbcNumberDTO> ibcObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE ibc_Number_details  ");
            sb.append(" SET poNumber=?,nameOfSupplr=?,billNumber=?,billDate=?,billAmount=?,ibcNumber=?,ibcDate=?,ibcAmount=?,ibcBank=?,session_id=?,ibc_cur_date=? ");            
            sb.append(" WHERE ibcID=? ");
            
            for(IbcNumberDTO SDTO : ibcObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{SDTO.getPoNumber(),
                        SDTO.getNameOfSupplr().trim(),
                        SDTO.getBillNumber().trim(),
                        SDTO.getBillDate().trim(),
                        SDTO.getBillAmount(),
                        SDTO.getIbcNumber(),
                        SDTO.getIbcDate().trim(),
                        SDTO.getIbcAmount(),
                        SDTO.getIbcBank().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        SDTO.getIbcID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updateRtgsDetail:",e);
        }
        return retVal;
    }
    
    public long getIbcNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();            
            sb.append(" SELECT * FROM ibc_Number_details WHERE ibcNumber=(SELECT MAX(ibcNumber) FROM ibc_Number_details) ");            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   IbcNumberDTO vdto = new IbcNumberDTO();                    
                    vdto.setIbcNumber((long)(resultMap.get("ibcNumber")));
                    maxNo = (long)(resultMap.get("ibcNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIbcNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<IbcNumberDTO> getIbcNoDeByPoNo(final long poNum) throws AppException {
        List<IbcNumberDTO> IBDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM ibc_Number_details WHERE poNumber ="+poNum);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IbcNumberDTO IbC = new IbcNumberDTO();
                    IbC.setIbcID((Long) (retMap.get("ibcID")));
                    IbC.setPoNumber((Long) (retMap.get("poNumber")));
                    IbC.setNameOfSupplr((String) (retMap.get("nameOfSupplr")));
                    IbC.setBillNumber((String) (retMap.get("billNumber")));
                    IbC.setBillDate((String) (retMap.get("billDate")));
                    IbC.setBillAmount((BigDecimal) (retMap.get("billAmount")));
                    IbC.setIbcNumber((Long) (retMap.get("ibcNumber")));
                    IbC.setIbcDate((String) (retMap.get("ibcDate")));
                    IbC.setIbcAmount((BigDecimal) (retMap.get("ibcAmount")));
                    IbC.setIbcBank((String) (retMap.get("ibcBank")));                     
                    IBDTO.add(IbC);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getIbcNoDeByPoNo:", e);
        }
        return IBDTO;
    }
    
    public long ibcNoExistOrNotInVouDaRe(final long ibcNo) throws AppException
    {
        long existOrNot = 0;
        long existOrNotI = 0;
        long retval = 0;
        try
        {
            String qry = "SELECT COUNT(1) FROM voucher_data_entry  WHERE ibcNo = ?";               
                
                existOrNot = getJdbcTemplate().queryForObject(qry,
                        new Object[]{ibcNo},
                        Long.class);//     
            String qryIbc = "SELECT COUNT(1) FROM ibc_number_details  WHERE ibcNumber = ?";     
                existOrNotI = getJdbcTemplate().queryForObject(qryIbc,
                        new Object[]{ibcNo},
                        Long.class);//  
                if(existOrNot > 0 && existOrNotI > 0){
                    retval = 1;
                }else{
                    retval = -1;
                }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : ibcNoExistOrNotInVouDaRe:",e);
        }
        return retval;
    }
}
