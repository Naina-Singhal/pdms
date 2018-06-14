/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.exception.AppException;
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
 * @author hpasupuleti
 */
@Repository
public class PlaceOfDeliveryDAOImpl {

    private static final Logger logger = Logger.getLogger(SectionDAOImpl.class);
    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    /*
    End : Autowiring the required Class instances
     */
 /*
    Default Constructor 
     */
    public PlaceOfDeliveryDAOImpl() {

    }

    /*
    Default Constructor 
     */
    public List<PlaceOfDeliveryDTO> getAllPlaceOfDeliveryDetails() throws AppException {
        List<PlaceOfDeliveryDTO> podLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_place_of_delivery_id,place_of_delivery,description,status_id   ");
            sb.append(" FROM place_of_delivery_master; ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    PlaceOfDeliveryDTO podObj = new PlaceOfDeliveryDTO();

                    podObj.setPlaceOfDeliveryID((Long) (retMap.get("pk_place_of_delivery_id")));
                    podObj.setPlaceOfDelivery((String) (retMap.get("place_of_delivery")));
                    podObj.setDescription((String) (retMap.get("description")));
                    podObj.setEncFieldValue(encryptDecrypt.encrypt(podObj.getPlaceOfDeliveryID() + ""));
                    podObj.setRowStatusKey((Long) (retMap.get("status_id")));

                    podLi.add(podObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getAllPlaceOfDeliveryDetails:", e);
        }
        return podLi;
    }

    public PlaceOfDeliveryDTO getSelectedPlaceOfDeliveryDetail(final long podID) throws AppException {
        PlaceOfDeliveryDTO podObj = new PlaceOfDeliveryDTO();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT pk_place_of_delivery_id,place_of_delivery,description,status_id   ");
            sb.append(" FROM place_of_delivery_master  WHERE pk_place_of_delivery_id=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{podID});

            if (!resList.isEmpty()) {
                for (Map<String, Object> retMap : resList) {
                    podObj.setPlaceOfDeliveryID((Long) (retMap.get("pk_place_of_delivery_id")));
                    podObj.setPlaceOfDelivery((String) (retMap.get("place_of_delivery")));
                    podObj.setDescription((String) (retMap.get("description")));
                    podObj.setEncFieldValue(encryptDecrypt.encrypt(podObj.getPlaceOfDeliveryID() + ""));
                    podObj.setRowStatusKey((Long) (retMap.get("status_id")));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getSelectedPlaceOfDeliveryDetail:", e);
        }

        return podObj;
    }

    public int insertPlaceOfDeliveryDetail(final PlaceOfDeliveryDTO podObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT  INTO place_of_delivery_master ");
            sb.append(" (place_of_delivery,description,status_id,created_by,created_on)   ");
            sb.append(" VALUES (?,?,?,?,?) ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{podObj.getPlaceOfDelivery().trim(),
                        podObj.getDescription().trim(),
                        ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPlaceOfDeliveryDetail:", e);
        }
        return retVal;
    }

    public int updatePlaceOfDeliveryDetail(final PlaceOfDeliveryDTO podObj, final long sessUserID)
            throws AppException {
        int retVal = 0;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE place_of_delivery_master SET ");
            sb.append(" place_of_delivery=?,description=?,status_id=?,modified_by=?,modified_on=?   ");
            sb.append(" WHERE pk_place_of_delivery_id=? ");

            retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{podObj.getPlaceOfDelivery().trim(),
                        podObj.getDescription().trim(),
                        podObj.getRowStatusKey(),
                        sessUserID,
                        dateUtil.generateDBCurrentDateInString(),
                        podObj.getPlaceOfDeliveryID()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePlaceOfDeliveryDetail:", e);
        }
        return retVal;
    }
    
    
    
    public long checkDuplicatePOD(final String pod) throws AppException {
        long dpCnt = 0;
        try {

            String qry = "SELECT COUNT(1) FROM place_of_delivery_master ";
            qry+=" WHERE LOWER(place_of_delivery)=? ";

            dpCnt = getJdbcTemplate().queryForObject(qry,
                    new Object[]{pod.trim().toLowerCase()},
                    Long.class);//,gName.toLowerCase()

        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "checkDuplicatePOD:: " + e.getMessage());
        }
        return dpCnt;
    }
}
