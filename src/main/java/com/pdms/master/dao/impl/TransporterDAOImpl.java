/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.dto.PaymentDTO;
import com.pdms.dto.TransporterDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dto.CurrencyDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
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
public class TransporterDAOImpl {
    
    private static final Logger logger = Logger.getLogger(TransporterDAOImpl.class);

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
    public TransporterDAOImpl(){
        
    }
    public int insertTransporterData(final List<TransporterDTO> transObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO transporter_details  ");
            sb.append("(transpId,transpName,transCode,transDes,session_id,trans_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?)");
            
            for(TransporterDTO TSDTO : transObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{TSDTO.getTranspId(),
                        TSDTO.getTranspName().trim(),
                        TSDTO.getTransCode().trim(),
                        TSDTO.getTransDes().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public List<TransporterDTO> getTransportRecord() throws AppException {
        List<TransporterDTO> tpDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM transporter_details ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TransporterDTO trDTO = new TransporterDTO();
                    trDTO.setTranspId((Long) (retMap.get("transpId")));
                    trDTO.setTransCode((String) (retMap.get("transCode")));
                    trDTO.setTranspName((String) (retMap.get("transpName")));
                    trDTO.setTransDes((String) (retMap.get("transDes")));                    
                    tpDTO.add(trDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return tpDTO;
    }
    
    public List<TransporterDTO> getTransporterReById(final long id) throws AppException {
        List<TransporterDTO> TRDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM transporter_details WHERE transpId ="+id);
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    TransporterDTO TDTO = new TransporterDTO();
                    TDTO.setTranspId((Long) (retMap.get("transpId")));
                    TDTO.setTransCode((String) (retMap.get("transCode")));
                    TDTO.setTranspName((String) (retMap.get("transpName")));
                    TDTO.setTransDes((String) (retMap.get("transDes")));                    
                    TRDTO.add(TDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllVendorDetails:", e);
        }
        return TRDTO;
    }
    
    public long updateTransporterDetail(final List<TransporterDTO> transObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE transporter_details  ");
            sb.append(" SET transpName=?,transCode=?,transDes=?,session_id=?,trans_date=? ");            
            sb.append(" WHERE transpId=? ");
            
            for(TransporterDTO tdto : transObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{tdto.getTranspName().trim(),
                        tdto.getTransCode().trim(),
                        tdto.getTransDes().trim(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        tdto.getTranspId()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public long checkDuplicateTransporter(final String gCode, final String gName) throws AppException {
        long dpCnt = 0;
        try {

            if(gCode.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM transporter_details ";
                qry+=" WHERE LOWER(transpName) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gName.trim().toLowerCase()},
                        Long.class);//                
            }
            else if(gName.equalsIgnoreCase("0"))
            {
                String qry = "SELECT COUNT(1) FROM transporter_details ";
                qry+=" WHERE LOWER(transCode) = ?";
                
                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase()},
                        Long.class);//                
            }
            else
            {
                String qry = "SELECT COUNT(1) FROM transporter_details ";
                qry+=" WHERE LOWER(transCode)=? AND LOWER(transpName) = ?";

                dpCnt = getJdbcTemplate().queryForObject(qry,
                        new Object[]{gCode.trim().toLowerCase(),gName.toLowerCase()},
                        Long.class);//                
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicateUnit:: " + e.getMessage());
        }       
        return dpCnt;
    }
    
    public TransporterDTO getSelectedTransporter(final long transporterID) throws AppException
    {
        TransporterDTO trnObj = new TransporterDTO();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT transpId,transpName,transCode,transDes ");
            sb.append(" FROM transporter_details WHERE  transpId=? ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{transporterID});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    
                    trnObj.setTranspId((Long)(resultMap.get("transpId")));
                    trnObj.setTransCode((String)(resultMap.get("transCode")));
                    trnObj.setTranspName((String)(resultMap.get("transpName")));
                    trnObj.setTransDes((String)(resultMap.get("transDes")));
                    
                    
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedUnit:",e);
        }
        return trnObj;
    }
}
