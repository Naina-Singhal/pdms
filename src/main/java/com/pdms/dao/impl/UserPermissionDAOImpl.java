/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dao.impl;

import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeProfileDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.LrEntryDTO;
import com.pdms.dto.PagePermiDTO;
import com.pdms.dto.UserPermissionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DescriptionDAOImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author myassessment
 */
@Repository
public class UserPermissionDAOImpl {
    
     private static final Logger logger = Logger.getLogger(UserPermissionDAOImpl.class);

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
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public UserPermissionDAOImpl(){
        
    }
    
    
    
    public List<UserPermissionDTO> getUserPermissions(final String ic_no) throws AppException {
        List<UserPermissionDTO> UPDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM user_permissions WHERE IC_No='"+ic_no+"' ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    UserPermissionDTO updto = new UserPermissionDTO();
                    updto.setUserPermissionID((Long) (retMap.get("userPermissionID")));
                    updto.setIC_No((String) (retMap.get("IC_No")));
                    updto.setD1((int) (retMap.get("d1")));
                    updto.setD2((int) (retMap.get("d2")));
                    updto.setD3((int) (retMap.get("d3")));
                    updto.setD4((int) (retMap.get("d4")));
                    updto.setD5((int) (retMap.get("d5")));
                    updto.setD6((int) (retMap.get("d6")));
                    updto.setD7((int) (retMap.get("d7")));
                    updto.setD8((int) (retMap.get("d8")));
                    updto.setD9((int) (retMap.get("d9")));
                    updto.setD10((int) (retMap.get("d10")));
                    updto.setD11((int) (retMap.get("d11")));
                    updto.setD12((int) (retMap.get("d12")));
                    updto.setD13((int) (retMap.get("d13")));
                    updto.setD14((int) (retMap.get("d14")));
                    updto.setD15((int) (retMap.get("d15")));
                    updto.setD16((int) (retMap.get("d16")));
                    updto.setD17((int) (retMap.get("d17")));
                    updto.setD18((int) (retMap.get("d18")));
                    updto.setD19((int) (retMap.get("d19")));
                    updto.setD20((int) (retMap.get("d20")));
                    updto.setD21((int) (retMap.get("d21")));
                    updto.setD22((int) (retMap.get("d22")));
                    updto.setD23((int) (retMap.get("d23")));
                    updto.setD24((int) (retMap.get("d24")));
                    updto.setD25((int) (retMap.get("d25")));
                    updto.setD26((int) (retMap.get("d26")));
                    updto.setD27((int) (retMap.get("d27")));
                    
                    updto.setM1((int) (retMap.get("m1")));
                    updto.setM2((int) (retMap.get("m2")));
                    updto.setM3((int) (retMap.get("m3")));
                    updto.setM4((int) (retMap.get("m4")));
                    updto.setM5((int) (retMap.get("m5")));
                    updto.setM6((int) (retMap.get("m6")));
                    updto.setM7((int) (retMap.get("m7")));
                    updto.setM8((int) (retMap.get("m8")));
                    updto.setM9((int) (retMap.get("m9")));
                    updto.setM10((int) (retMap.get("m10")));
                    updto.setM11((int) (retMap.get("m11")));
                    updto.setM12((int) (retMap.get("m12")));
                    updto.setM13((int) (retMap.get("m13")));
                    updto.setM14((int) (retMap.get("m14")));
                    updto.setM15((int) (retMap.get("m15")));
                    updto.setM16((int) (retMap.get("m16")));
                    updto.setM17((int) (retMap.get("m17")));
                    updto.setM18((int) (retMap.get("m18")));
                    updto.setM19((int) (retMap.get("m19")));
                    updto.setM20((int) (retMap.get("m20")));
                    updto.setM21((int) (retMap.get("m21")));
                    updto.setM22((int) (retMap.get("m22")));
                    updto.setM23((int) (retMap.get("m23")));
                    updto.setM24((int) (retMap.get("m24")));
                    updto.setM25((int) (retMap.get("m25")));
                    updto.setM26((int) (retMap.get("m26")));
                    updto.setM27((int) (retMap.get("m27")));
                    updto.setM28((int) (retMap.get("m28")));
                    updto.setM29((int) (retMap.get("m29")));
                    updto.setM30((int) (retMap.get("m30")));
                    updto.setM31((int) (retMap.get("m31")));
                    updto.setM32((int) (retMap.get("m32")));
                    
                    updto.setC1((int) (retMap.get("c1")));
                    updto.setC2((int) (retMap.get("c2")));
                    updto.setC3((int) (retMap.get("c3")));
                    updto.setC4((int) (retMap.get("c4")));
                    updto.setC5((int) (retMap.get("c5")));
                    updto.setC6((int) (retMap.get("c6")));
                    updto.setC7((int) (retMap.get("c7")));
                    updto.setC8((int) (retMap.get("c8")));
                    updto.setC9((int) (retMap.get("c9")));
                    updto.setC10((int) (retMap.get("c10")));
                    updto.setC11((int) (retMap.get("c11")));
                    updto.setC12((int) (retMap.get("c12")));
                    updto.setC13((int) (retMap.get("c13")));
                    updto.setC14((int) (retMap.get("c14")));
                    updto.setC15((int) (retMap.get("c15")));
                    updto.setC16((int) (retMap.get("c16")));
                    updto.setC17((int) (retMap.get("c17")));
                    updto.setC18((int) (retMap.get("c18")));
                    
                    updto.setB1((int) (retMap.get("b1")));
                    updto.setB2((int) (retMap.get("b2")));
                    updto.setB3((int) (retMap.get("b3")));
                    updto.setB4((int) (retMap.get("b4")));
                    updto.setB5((int) (retMap.get("b5")));
                    updto.setB6((int) (retMap.get("b6")));
                    updto.setB7((int) (retMap.get("b7")));
                    
                    updto.setE1((int) (retMap.get("e1")));
                    updto.setE2((int) (retMap.get("e2")));
                    updto.setE3((int) (retMap.get("e3")));
                    updto.setE4((int) (retMap.get("e4")));
                    updto.setE5((int) (retMap.get("e5")));
                    updto.setE6((int) (retMap.get("e6")));
                    updto.setE7((int) (retMap.get("e7")));
                    updto.setE8((int) (retMap.get("e8")));
                    updto.setE9((int) (retMap.get("e9")));
                    updto.setE10((int) (retMap.get("e10")));
                    updto.setE11((int) (retMap.get("e11")));
                    updto.setE12((int) (retMap.get("e12")));
                    updto.setE13((int) (retMap.get("e13")));
                    updto.setE14((int) (retMap.get("e14")));
                    
                    
                    updto.setT1((int) (retMap.get("t1")));
                    updto.setT2((int) (retMap.get("t2")));
                    updto.setT3((int) (retMap.get("t3")));
                    updto.setT4((int) (retMap.get("t4")));
                    updto.setT5((int) (retMap.get("t5")));
                    updto.setT6((int) (retMap.get("t6")));
                    updto.setT7((int) (retMap.get("t7")));
                    updto.setT8((int) (retMap.get("t8")));
                    updto.setT9((int) (retMap.get("t9")));
                    
                    updto.setV1((int) (retMap.get("v1")));
                    updto.setV2((int) (retMap.get("v2")));
                    updto.setV3((int) (retMap.get("v3")));
                    updto.setV4((int) (retMap.get("v4")));
                    updto.setV5((int) (retMap.get("v5")));
                    updto.setV6((int) (retMap.get("v6")));
                    updto.setV7((int) (retMap.get("v7")));
                    updto.setV8((int) (retMap.get("v8")));
                    
                    updto.setG1((int) (retMap.get("g1")));
                    updto.setG2((int) (retMap.get("g2")));
                    updto.setG3((int) (retMap.get("g3")));
                    updto.setG4((int) (retMap.get("g4")));
                    updto.setG5((int) (retMap.get("g5")));
                    updto.setG6((int) (retMap.get("g6")));
                    updto.setG7((int) (retMap.get("g7")));
                    updto.setG8((int) (retMap.get("g8")));
                    updto.setG9((int) (retMap.get("g9")));
                    updto.setG10((int) (retMap.get("g10")));
                    
                    updto.setU1((int) (retMap.get("u1")));
                    updto.setP1((int) (retMap.get("p1")));
                    updto.setP2((int) (retMap.get("p2")));
                    updto.setP3((int) (retMap.get("p3")));
                    updto.setP4((int) (retMap.get("p4")));
                    UPDTO.add(updto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUserPermissions:", e);
        }
        return UPDTO;
    }
    
    public int getUserPermiActive(final String ic_no, final String page_id) throws AppException {
             int resList = 0;
             int ic;
        try {
            String qry1 = " SELECT COUNT(*) FROM user_permissions  WHERE IC_No='" + ic_no + "' ";
            ic = getJdbcTemplate().queryForObject(qry1, Integer.class);
            logger.info("--------iccheckat ----------" + ic);
            if (ic == 1) {
                String qry = " SELECT " + page_id + " FROM user_permissions WHERE IC_No='" + ic_no + "' ";
                resList = getJdbcTemplate().queryForObject(qry, Integer.class);
                logger.info("--------returnat ----------" + resList);
            } else {
                resList = 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUserPermiActive:", e);
        }
        return resList;
    }
    
     public List<EmployeeProfileDTO> getUserProfileReco() throws AppException {
        List<EmployeeProfileDTO> EpdDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM employee_profile ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    EmployeeProfileDTO emdto = new EmployeeProfileDTO();
                    emdto.setEmployeeProfileID((Long) (retMap.get("fk_employee_id")));
                    emdto.setIcNumber((String) (retMap.get("ic_number")));
                    emdto.setFirstName((String) (retMap.get("first_name")));
                    emdto.setMiddleName((String) (retMap.get("middle_name")));
                    emdto.setLastName((String) (retMap.get("last_name")));
                    emdto.setEmployeeEmail((String) (retMap.get("email")));                    
                    EpdDTO.add(emdto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUserProfileReco:", e);
        }
        return EpdDTO;
    }
     
    public List<PagePermiDTO> getPagepermiData() throws AppException {
        List<PagePermiDTO> PgDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM user_permission_pages ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PagePermiDTO pgdto = new PagePermiDTO();
                    pgdto.setId((Long) (retMap.get("id")));
                    pgdto.setPage_id((String) (retMap.get("page_id")));
                    pgdto.setPage_name((String) (retMap.get("page_name")));  
                    pgdto.setPage_group((String) (retMap.get("page_group")));
                    PgDTO.add(pgdto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPagepermiData:", e);
        }
        return PgDTO;
    } 
    
    public int insertUserPermission(final List<UserPermissionDTO> perObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        int icValid = 0;
        String ic_no = "";
        try
        {   
            for(UserPermissionDTO a: perObj){
                ic_no = a.getIC_No();
            }
            logger.info("----icnosave-----"+ic_no);
            String qry1 = " SELECT COUNT(*) FROM user_permissions  WHERE IC_No='" + ic_no + "' ";
            icValid = getJdbcTemplate().queryForObject(qry1, Integer.class);
            logger.info("--------iccheckat ----------" + icValid);
            if (icValid == 1) {      //update exist record
                StringBuilder sb = new StringBuilder();

                sb.append("UPDATE user_permissions SET ");
                sb.append(" d1=?,d2=?,d3=?,d4=?,d5=?,d6=?,d7=?,d8=?,d9=?,d10=?, ");
                sb.append(" d11=?,d12=?,d13=?,d14=?,d15=?,d16=?,d17=?,d18=?,d19=?,d20=?,d21=?,");
                sb.append(" d22=?,d23=?,d24=?,d25=?,d26=?,d27=?,m1=?,m2=?,m3=?,m4=?,m5=?,m6=?,m7=?,m8=?,m9=?, ");
                sb.append(" m10=?,m11=?,m12=?,m13=?,m14=?,m15=?,m16=?,m17=?,");
                sb.append(" m18=?,m19=?,m20=?,m21=?,m22=?,m23=?,m24=?,m25=?,m26=?,m27=?,m28=?,m29=?,m30=?,m31=?, ");
                sb.append(" m32=?,c1=?,c2=?,c3=?,c4=?,c5=?,c6=?,c7=?,c8=?,c9=?,c10=?,c11=?, ");
                sb.append(" c12=?,c13=?,c14=?,c15=?,c16=?,c17=?,c18=?,");
                sb.append(" b1=?,b2=?,b3=?,b4=?,b5=?,b6=?,b7=?,e1=?,e2=?,e3=?,");
                sb.append(" e4=?,e5=?,e6=?,e7=?,e8=?,e9=?,e10=?,e11=?,e12=?,e13=?,e14=?, ");
                sb.append(" t1=?,t2=?,t3=?,t4=?,t5=?,t6=?,t7=?,t8=?,t9=?, ");
                sb.append(" v1=?,v2=?,v3=?,v4=?,v5=?,v6=?,v7=?,v8=?,");
                sb.append(" g1=?,g2=?,g3=?,g4=?,g5=?,g6=?,g7=?,g8=?,g9=?,g10=?,u1=?,p1=?,p2=?,p3=?,p4=? ");
                sb.append(" WHERE IC_No=? ");
                
                for(UserPermissionDTO LRDTO : perObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                        new Object[]{LRDTO.getD1(),
                            LRDTO.getD2(),
                            LRDTO.getD3(),
                            LRDTO.getD4(),
                            LRDTO.getD5(),
                            LRDTO.getD6(),
                            LRDTO.getD7(),
                            LRDTO.getD8(),
                            LRDTO.getD9(),
                            LRDTO.getD10(),
                            LRDTO.getD11(),
                            LRDTO.getD12(),
                            LRDTO.getD13(),
                            LRDTO.getD14(),
                            LRDTO.getD15(),
                            LRDTO.getD16(),
                            LRDTO.getD17(),
                            LRDTO.getD18(),
                            LRDTO.getD19(),
                            LRDTO.getD20(),
                            LRDTO.getD21(),
                            LRDTO.getD22(),
                            LRDTO.getD23(),
                            LRDTO.getD24(),
                            LRDTO.getD25(),
                            LRDTO.getD26(),
                            LRDTO.getD27(),
                            
                            LRDTO.getM1(),
                            LRDTO.getM2(),
                            LRDTO.getM3(),
                            LRDTO.getM4(),
                            LRDTO.getM5(),
                            LRDTO.getM6(),
                            LRDTO.getM7(),
                            LRDTO.getM8(),
                            LRDTO.getM9(),
                            LRDTO.getM10(),
                            LRDTO.getM11(),
                            LRDTO.getM12(),
                            LRDTO.getM13(),
                            LRDTO.getM14(),
                            LRDTO.getM15(),
                            LRDTO.getM16(),
                            LRDTO.getM17(),
                            LRDTO.getM18(),
                            LRDTO.getM19(),
                            LRDTO.getM20(),
                            LRDTO.getM21(),
                            LRDTO.getM22(),
                            LRDTO.getM23(),
                            LRDTO.getM24(),
                            LRDTO.getM25(),
                            LRDTO.getM26(),
                            LRDTO.getM27(),
                            LRDTO.getM28(),
                            LRDTO.getM29(),
                            LRDTO.getM30(),
                            LRDTO.getM31(),
                            LRDTO.getM32(),
                            
                            LRDTO.getC1(),
                            LRDTO.getC2(),
                            LRDTO.getC3(),
                            LRDTO.getC4(),
                            LRDTO.getC5(),
                            LRDTO.getC6(),
                            LRDTO.getC7(),
                            LRDTO.getC8(),
                            LRDTO.getC9(),
                            LRDTO.getC10(),
                            LRDTO.getC11(),
                            LRDTO.getC12(),
                            LRDTO.getC13(),
                            LRDTO.getC14(),
                            LRDTO.getC15(),
                            LRDTO.getC16(),
                            LRDTO.getC17(),
                            LRDTO.getC18(),
                            
                            LRDTO.getB1(),
                            LRDTO.getB2(),
                            LRDTO.getB3(),
                            LRDTO.getB4(),
                            LRDTO.getB5(),
                            LRDTO.getB6(),
                            LRDTO.getB7(),
                            
                            LRDTO.getE1(),
                            LRDTO.getE2(),
                            LRDTO.getE3(),
                            LRDTO.getE4(),
                            LRDTO.getE5(),
                            LRDTO.getE6(),
                            LRDTO.getE7(),
                            LRDTO.getE8(),
                            LRDTO.getE9(),
                            LRDTO.getE10(),
                            LRDTO.getE11(),
                            LRDTO.getE12(),
                            LRDTO.getE13(),
                            LRDTO.getE14(),
                            
                            LRDTO.getT1(),
                            LRDTO.getT2(),
                            LRDTO.getT3(),
                            LRDTO.getT4(),
                            LRDTO.getT5(),
                            LRDTO.getT6(),
                            LRDTO.getT7(),
                            LRDTO.getT8(),
                            LRDTO.getT9(),
                            
                            LRDTO.getV1(),
                            LRDTO.getV2(),
                            LRDTO.getV3(),
                            LRDTO.getV4(),
                            LRDTO.getV5(),
                            LRDTO.getV6(),
                            LRDTO.getV7(),
                            LRDTO.getV8(),                            
                            
                            LRDTO.getG1(),
                            LRDTO.getG2(),
                            LRDTO.getG3(),
                            LRDTO.getG4(),
                            LRDTO.getG5(),
                            LRDTO.getG6(),
                            LRDTO.getG7(),
                            LRDTO.getG8(),
                            LRDTO.getG9(),
                            LRDTO.getG10(),
                            
                            LRDTO.getU1(),
                            LRDTO.getP1(),
                            LRDTO.getP2(),
                            LRDTO.getP3(),
                            LRDTO.getP4(),
                            LRDTO.getIC_No()
                        
                    });
            }
                
                return 3;
            }else{  //insert new record
            
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO user_permissions  ");
            sb.append("(IC_No,d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11, "); 
            sb.append("d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25,d26,d27,");
            sb.append("m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12, ");
            sb.append("m13,m14,m15,m16,m17,m18,m19,m20,m21,m22,m23,m24,m25,m26,m27,m28,m29,m30,m31,");
            sb.append("m32,c1,c2,c3,c4,c5,c6,c7,c8,c9,c10, ");
            sb.append("c11,c12,c13,c14,c15,c16,c17,c18,");
            sb.append("b1,b2,b3,b4,b5,b6,b7,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14, ");
            sb.append("t1,t2,t3,t4,t5,t6,t7,t8,t9, ");
            sb.append("v1,v2,v3,v4,v5,v6,v7,v8, ");
            sb.append("g1,g2,g3,g4,g5,g6,g7,g8,g9,g10,u1,p1,p2,p3,p4) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,");
            sb.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            
            for(UserPermissionDTO LRDTO : perObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                        new Object[]{LRDTO.getIC_No(),
                            LRDTO.getD1(),
                            LRDTO.getD2(),
                            LRDTO.getD3(),
                            LRDTO.getD4(),
                            LRDTO.getD5(),
                            LRDTO.getD6(),
                            LRDTO.getD7(),
                            LRDTO.getD8(),
                            LRDTO.getD9(),
                            LRDTO.getD10(),
                            LRDTO.getD11(),
                            LRDTO.getD12(),
                            LRDTO.getD13(),
                            LRDTO.getD14(),
                            LRDTO.getD15(),
                            LRDTO.getD16(),
                            LRDTO.getD17(),
                            LRDTO.getD18(),
                            LRDTO.getD19(),
                            LRDTO.getD20(),
                            LRDTO.getD21(),
                            LRDTO.getD22(),
                            LRDTO.getD23(),
                            LRDTO.getD24(),
                            LRDTO.getD25(),
                            LRDTO.getD26(),
                            LRDTO.getD27(),
                            
                            LRDTO.getM1(),
                            LRDTO.getM2(),
                            LRDTO.getM3(),
                            LRDTO.getM4(),
                            LRDTO.getM5(),
                            LRDTO.getM6(),
                            LRDTO.getM7(),
                            LRDTO.getM8(),
                            LRDTO.getM9(),
                            LRDTO.getM10(),
                            LRDTO.getM11(),
                            LRDTO.getM12(),
                            LRDTO.getM13(),
                            LRDTO.getM14(),
                            LRDTO.getM15(),
                            LRDTO.getM16(),
                            LRDTO.getM17(), 
                            LRDTO.getM18(), 
                            LRDTO.getM19(), 
                            LRDTO.getM20(),
                            LRDTO.getM21(),
                            LRDTO.getM22(),
                            LRDTO.getM23(),
                            LRDTO.getM24(),
                            LRDTO.getM25(),
                            LRDTO.getM26(),
                            LRDTO.getM27(),
                            LRDTO.getM28(),
                            LRDTO.getM29(),
                            LRDTO.getM30(),
                            LRDTO.getM31(),
                            LRDTO.getM32(),
                            
                            LRDTO.getC1(),
                            LRDTO.getC2(),
                            LRDTO.getC3(),
                            LRDTO.getC4(),
                            LRDTO.getC5(),
                            LRDTO.getC6(),
                            LRDTO.getC7(),
                            LRDTO.getC8(),
                            LRDTO.getC9(),
                            LRDTO.getC10(),
                            LRDTO.getC11(),
                            LRDTO.getC12(),
                            LRDTO.getC13(),
                            LRDTO.getC14(),
                            LRDTO.getC15(),
                            LRDTO.getC16(),
                            LRDTO.getC17(),
                            LRDTO.getC18(),
                            
                            LRDTO.getB1(),
                            LRDTO.getB2(),
                            LRDTO.getB3(),
                            LRDTO.getB4(),
                            LRDTO.getB5(),
                            LRDTO.getB6(),
                            LRDTO.getB7(),
                            
                            LRDTO.getE1(),
                            LRDTO.getE2(),
                            LRDTO.getE3(),
                            LRDTO.getE4(),
                            LRDTO.getE5(),
                            LRDTO.getE6(),
                            LRDTO.getE7(),
                            LRDTO.getE8(),
                            LRDTO.getE9(),
                            LRDTO.getE10(),
                            LRDTO.getE11(),
                            LRDTO.getE12(),
                            LRDTO.getE13(),
                            LRDTO.getE14(),
                            
                            LRDTO.getT1(),
                            LRDTO.getT2(),
                            LRDTO.getT3(),
                            LRDTO.getT4(),
                            LRDTO.getT5(),
                            LRDTO.getT6(),
                            LRDTO.getT7(),
                            LRDTO.getT8(),
                            LRDTO.getT9(),
                            
                            LRDTO.getV1(),
                            LRDTO.getV2(),
                            LRDTO.getV3(),
                            LRDTO.getV4(),
                            LRDTO.getV5(),
                            LRDTO.getV6(),
                            LRDTO.getV7(),
                            LRDTO.getV8(),
                            
                            LRDTO.getG1(),
                            LRDTO.getG2(),
                            LRDTO.getG3(),
                            LRDTO.getG4(),
                            LRDTO.getG5(),
                            LRDTO.getG6(),
                            LRDTO.getG7(),
                            LRDTO.getG8(),
                            LRDTO.getG9(),
                            LRDTO.getG10(),
                            
                            LRDTO.getU1(),
                            LRDTO.getP1(),
                            LRDTO.getP2(),
                            LRDTO.getP3(),
                            LRDTO.getP4(),
                    });
            }
        }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUserPermission:",e);
        }
        return retVal;
    }
    
    public List<UserPermissionDTO> getUserPermissionObj(HttpServletRequest request) throws AppException {
        List<UserPermissionDTO> userPermiLi = new LinkedList<>();
        try {
            HttpSession session = request.getSession();            
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION)); 
            String empIcNo = empDTO.getEmployeeProfileDTO().getIcNumber();            
            userPermiLi = permissionServiceImpl.getUserPermissions(empIcNo);            
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUserPermissionObj:", e);
        }
        return userPermiLi;
    }
}
