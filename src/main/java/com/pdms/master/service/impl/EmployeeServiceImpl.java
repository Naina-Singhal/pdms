/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeTypeDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.EmployeeDAOImpl;
import com.pdms.utils.CommonUtil;
import com.pdms.utils.EncryptDecrypt;
import com.pdms.utils.GenerateRandomNumber;
import java.util.ArrayList;
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
public class EmployeeServiceImpl {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private EmployeeDAOImpl empDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private EmployeeTypeServiceImpl empTypeService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private GenerateRandomNumber generateRandom;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public EmployeeServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<EmployeeLoginDTO> getAllEmployeeDetails(final HttpServletRequest request) throws AppException {
        String empType = "";
        long sessUserID = 0;
        String employeeType = "";
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            sessUserID = empDTO.getEmployeeID();
            employeeType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            List<Long> empTypeIDList = new ArrayList<>();

            if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
                empType = "";
                String empTypeID = "";
                EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();
                empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN);
                empTypeIDList.add(empTypeObj.getEmployeeTypeID());

                empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN);

                empTypeIDList.add(empTypeObj.getEmployeeTypeID());
                
                empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN);

                empTypeIDList.add(empTypeObj.getEmployeeTypeID());
                
                empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN);

                empTypeIDList.add(empTypeObj.getEmployeeTypeID());

                empTypeID = StringUtils.join(empTypeIDList, ",");

                empType = "AND c.pk_employee_type_id IN (" + empTypeID + ")";
            } else {
                EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();
                empTypeObj = empTypeService.getEmployeeTypeDetailsByName(employeeType);
                String empTypeID = "";
                empTypeIDList.add(empTypeObj.getEmployeeTypeID());

                if (!StringUtils.isEmpty(employeeType)) {
                    if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
                    }
                    if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
                    }
                    if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER);
                    }
                    if (employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER);
                    }
                    empTypeIDList.add(empTypeObj.getEmployeeTypeID());
                }

                empTypeID = StringUtils.join(empTypeIDList, ",");

                empType = "AND c.pk_employee_type_id IN (" + empTypeID + ")";
            }
        }
        return empDAO.getAllEmployeeDetails(empType);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<EmployeeLoginDTO> getAllActiveEmployeeDetails() throws AppException {
        return empDAO.getAllActiveEmployeeDetails();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long getMaxEmployeeID() throws AppException {
        return empDAO.getMaxEmployeeID();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertEmployeeDetails(final EmployeeLoginDTO empObj, final HttpServletRequest request)
            throws AppException {
        int resVal = 0;
        try {
            HttpSession session = request.getSession(false);
            long sessUserID = 0;
            String employeeType = "";
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

                sessUserID = empDTO.getEmployeeID();
                employeeType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();

            }
            /*
            if(empObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeID() <= 0)
            {
                EmployeeTypeDTO empTypeObj = new EmployeeTypeDTO();
                if(!StringUtils.isEmpty(employeeType))
                {
                    if(employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN))
                    {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName
                                    (ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
                    }
                    if(employeeType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN))
                    {
                        empTypeObj = empTypeService.getEmployeeTypeDetailsByName
                                        (ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
                    }
                }
                empObj.getEmployeeProfileDTO().setEmployeeTypeDTO(empTypeObj);
            }
             */

            String username = commonUtil.generateUserName(empObj.getEmployeeProfileDTO().getFirstName(),
                    empObj.getEmployeeProfileDTO().getLastName(), this.getMaxEmployeeID());

            String sessionKey = generateRandom.generateSessionKeyToken();
            //encryptDecrypt.setApplicationKey(sessionKey);

            empObj.setUsername(username);
            empObj.setPassword(encryptDecrypt.encrypt(username.toLowerCase()).trim());
            empObj.setLoginAttempts(0);
            empObj.setLockedFlag(0);
            empObj.setPasswordFlag(0);
            empObj.setSessionKey(sessionKey);

            long dupCnt = this.checkDuplicateEmployees(empObj.getEmployeeProfileDTO().getIcNumber());
            if (dupCnt > 0) {
                resVal = -1;
            } else {
                resVal = empDAO.insertEmployeeDetails(empObj, sessUserID);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resVal;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public EmployeeLoginDTO getSelectedEmployeeDetails(final String encEmpID) throws AppException {
        long empID = 0;
        if (!StringUtils.isEmpty(encEmpID)) {
            String decEmpID = encryptDecrypt.decrypt(encEmpID);
            if (NumberUtils.isNumber(decEmpID)) {
                empID = Long.parseLong(decEmpID);
            }
        }

        return empDAO.getSelectedEmployeeDetails(empID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateEmployeeDetails(final EmployeeLoginDTO empObj, final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String employeeType = "";
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            sessUserID = empDTO.getEmployeeID();
            employeeType = empDTO.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();

        }

        EmployeeLoginDTO exisEmpDetails = this.getSelectedEmployeeDetails(empObj.getEncFieldValue());

        long empID = 0;
        if (!StringUtils.isEmpty(empObj.getEncFieldValue())) {
            String decEmpID = encryptDecrypt.decrypt(empObj.getEncFieldValue());
            if (NumberUtils.isNumber(decEmpID)) {
                empID = Long.parseLong(decEmpID);
            }
        }
        empObj.setEmployeeID(empID);
        long dupCnt = 0;
        if (!exisEmpDetails.getEmployeeProfileDTO().getIcNumber().
                equalsIgnoreCase(empObj.getEmployeeProfileDTO().getIcNumber())) {
            dupCnt = this.checkDuplicateEmployees(empObj.getEmployeeProfileDTO().getIcNumber());
        }
        if (dupCnt > 0) {
            return -1;
        } else {
            return empDAO.updateEmployeeDetails(empObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateUserPassword(final HttpServletRequest request, final EmployeeLoginDTO loginObj)
            throws AppException {

        long userID = 0;
        HttpSession session = request.getSession(false);
        String curPassword = "";
        int retVal = 0;
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            userID = empDTO.getEmployeeID();
            curPassword = empDTO.getCurrentPassword();
        }

        if (loginObj.getCurrentPassword().equalsIgnoreCase(curPassword)) {
            String newPassword = loginObj.getNewPassword();
            String confPassword = loginObj.getConfPassword();
            if (newPassword.equalsIgnoreCase(confPassword)) {
                retVal = empDAO.updateUserPassword(encryptDecrypt.encrypt(confPassword), userID);
            } else {
                retVal = -1;
            }
        } else {
            retVal = -2;
        }

        return retVal;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateUserProfile(final EmployeeLoginDTO loginObj, final HttpServletRequest request)
            throws AppException {
        long userID = 0;
        HttpSession session = request.getSession(false);
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            EmployeeLoginDTO empDTO = (EmployeeLoginDTO) (session.getAttribute(SessionConstants.USER_SESSION));

            userID = empDTO.getEmployeeID();
        }

        loginObj.setEmployeeID(userID);

        return empDAO.updateUserProfile(loginObj);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateEmployees(final String icNumber) throws AppException {
        return empDAO.checkDuplicateEmployees(icNumber);
    }
}
