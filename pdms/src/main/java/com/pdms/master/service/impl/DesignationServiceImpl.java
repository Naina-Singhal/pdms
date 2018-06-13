/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.service.impl;

import com.pdms.constants.ApplicationConstants;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.DesignationDTO;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.EmployeeTypeDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.DesignationDAOImpl;
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
public class DesignationServiceImpl {

    private static final Logger logger = Logger.getLogger(DesignationServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private DesignationDAOImpl desigDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;

    @Autowired
    private EmployeeTypeServiceImpl empTypeService;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public DesignationServiceImpl() {

    }

    /*
    Default Constructor 
     */
 /*
        Methods 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<DesignationDTO> getAllDesignations() throws AppException {
        return desigDAO.getAllDesignations();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<DesignationDTO> getAllSigningAuthorityDesignations() throws AppException {
        return desigDAO.getAllSigningAuthorityDesignations();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<DesignationDTO> getAllApprovingAuthorityDesignations() throws AppException {
        return desigDAO.getAllApprovingAuthorityDesignations();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public DesignationDTO getSelectedDesignationByID(final String encDesignationID) throws AppException {
        long desigID = 0;
        if (!StringUtils.isEmpty(encDesignationID)) {
            String decDesigID = encryptDecrypt.decrypt(encDesignationID);
            if (NumberUtils.isNumber(decDesigID)) {
                desigID = Long.parseLong(decDesigID);
            }
        }

        return desigDAO.getSelectedDesignationByID(desigID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int insertDesignationDetail(final DesignationDTO designationObj, final HttpServletRequest request)
            throws AppException {

        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String empType = "";
        long empTypeID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
        }

        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
            designationObj.setIsSigningAuthority(0);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        }else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
            if (designationObj.getIsSigningAuthority() > 0) {
                EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
                designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
            } else if (designationObj.getIsApprovingAuthority() > 0) {
                EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
                designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
            } else {
                designationObj.setEmployeeTypeID(0);
            }
        }

        long dupCnt = this.checkDuplicateDesignation(designationObj.getDesignationCode(),
                designationObj.getDesignationName());
        if (dupCnt > 0) {
            return -1;
        } else {
            return desigDAO.insertDesignationDetail(designationObj, sessUserID);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public int updateDesignationDetail(final DesignationDTO designationObj, final HttpServletRequest request)
            throws AppException {
        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String empType = "";
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
        }

        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
            designationObj.setIsSigningAuthority(0);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
            //designationObj.setIsApprovingAuthority(0);
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER);
            designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SUPERADMIN)) {
            if (designationObj.getIsSigningAuthority() > 0) {
                EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
                designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
            } else if (designationObj.getIsApprovingAuthority() > 0) {
                EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
                designationObj.setEmployeeTypeID(empTypeDTO.getEmployeeTypeID());
            } else {
                designationObj.setEmployeeTypeID(0);
            }

        }

        long desigID = 0;
        if (!StringUtils.isEmpty(designationObj.getEncFieldValue())) {
            String decDesigID = encryptDecrypt.decrypt(designationObj.getEncFieldValue());
            if (NumberUtils.isNumber(decDesigID)) {
                desigID = Long.parseLong(decDesigID);
            }
        }
        designationObj.setDesignationID(desigID);

        DesignationDTO exisDesigObj = this.getSelectedDesignationByID(designationObj.getEncFieldValue());
        long dupCnt = 0;

        if ((!exisDesigObj.getDesignationCode().equalsIgnoreCase(designationObj.getDesignationCode()))
                && (!exisDesigObj.getDesignationName().equalsIgnoreCase(designationObj.getDesignationName()))) {
            dupCnt = this.checkDuplicateDesignation(designationObj.getDesignationCode(),
                    designationObj.getDesignationName());
        } else if ((!exisDesigObj.getDesignationCode().equalsIgnoreCase(designationObj.getDesignationCode()))) {
            dupCnt = this.checkDuplicateDesignation(designationObj.getDesignationCode(), "0");
        } else if ((!exisDesigObj.getDesignationName().equalsIgnoreCase(designationObj.getDesignationName()))) {
            dupCnt = this.checkDuplicateDesignation("0",
                    designationObj.getDesignationName());
        }

        if (dupCnt > 0) {
            return -1;
        } else {
            return desigDAO.updateDesignationDetail(designationObj, sessUserID);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<DesignationDTO> getAllDesignationsbyEmployeeType(final HttpServletRequest request)
            throws AppException {

        HttpSession session = request.getSession(false);
        long sessUserID = 0;
        String empType = "";
        long empTypeID = 0;
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
            sessUserID = empLoginObj.getEmployeeID();
        }

        if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_SECUSER);
            empTypeID = empTypeDTO.getEmployeeTypeID();
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_PUUSER);
            empTypeID = empTypeDTO.getEmployeeTypeID();
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER);
            empTypeID = empTypeDTO.getEmployeeTypeID();
        } else if (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN)) {
            EmployeeTypeDTO empTypeDTO = empTypeService.getEmployeeTypeDetailsByName(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER);
            empTypeID = empTypeDTO.getEmployeeTypeID();
        }

        return desigDAO.getAllDesignationsbyEmployeeType(empTypeID);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public long checkDuplicateDesignation(final String designationCode,
            final String desigName) throws AppException {
        return desigDAO.checkDuplicateDesignation(designationCode, desigName);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class, RuntimeException.class, Exception.class})
    public List<DesignationDTO> getApprovingAuthorityDesignations(final HttpServletRequest request) throws AppException {

        String empType = "";
        HttpSession session = request.getSession(false);
        EmployeeLoginDTO empLoginObj = new EmployeeLoginDTO();
        if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
            empLoginObj = (EmployeeLoginDTO) session.getAttribute(SessionConstants.USER_SESSION);

            empType = empLoginObj.getEmployeeProfileDTO().getEmployeeTypeDTO().getEmployeeTypeName();
        }

//        if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_SECUSER))) {
//            return desigDAO.getAllPlantApprovingAuthorityDesignations();
//        } else if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_PUUSER))) {
//            return desigDAO.getAllRPUApprovingAuthorityDesignations();
//        } else if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_ADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_STORES_USER))) {
//            return desigDAO.getAllStoresApprovingAuthorityDesignations();
//        } else if ((empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_ADMIN))
//                || (empType.equalsIgnoreCase(ApplicationConstants.EMPLOYEE_TYPE_ACCOUNT_USER))) {
//            return desigDAO.getAllAccountsApprovingAuthorityDesignations();
//        }

        return desigDAO.getAllApprovingAuthorityDesignations();
    }

}
