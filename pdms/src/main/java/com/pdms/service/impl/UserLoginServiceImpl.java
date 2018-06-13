/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.service.impl;

import com.pdms.dao.impl.UserLoginDAOImpl;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.exception.AppException;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import com.pdms.utils.GenerateRandomNumber;
import javax.servlet.http.HttpServletRequest;
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
public class UserLoginServiceImpl {

    private static final Logger logger = Logger.getLogger(UserLoginServiceImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private UserLoginDAOImpl userLoginDAO;

    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired 
    private GenerateRandomNumber generateRandom;

    /*
    End : Autowiring the required Class instances
     */

 /*
    Default Constructor 
     */
    public UserLoginServiceImpl() {

    }

    /*
    Default Constructor 
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public EmployeeLoginDTO validateEmployeeLogin(final HttpServletRequest requestObj)
            throws AppException {
        String username = "";
        String pswd = "";
        String reqpswd="";
        EmployeeLoginDTO empDTO = new EmployeeLoginDTO();
        try {
            if (requestObj.getParameter("username") != null) {
                username = requestObj.getParameter("username");
            }
            if (requestObj.getParameter("password") != null) {
                reqpswd = requestObj.getParameter("password");
            }

            empDTO = userLoginDAO.fetchUserDetailsByUserName(username);
            if (empDTO.getEmployeeID() > 0) {
                //encryptDecrypt.setApplicationKey(empDTO.getSessionKey());
                pswd = encryptDecrypt.encrypt(reqpswd).trim();
                logger.info("In validateEmployeeLogin:pswd:"+pswd);
                long valPswd = userLoginDAO.validateUserPassword(username, pswd);
                if(valPswd >0)
                {
                    String session_token = generateRandom.generateSessionKeyToken();
                    
                    //DELETE PREVIUOS USER SESSION INFO IF ANY - 
                    // NO NEED OF SHOWING ACTIVE SESSION MESSAGE TO USER
                    
                    this.deleteUserSessionInfo(empDTO.getEmployeeID());
                    this.insertUserSessionInfo(empDTO.getEmployeeID(), username, empDTO.getSessionKey(),
                            session_token);
                    empDTO.setSessionToken(session_token);
                    empDTO.setValidationMessage("");
                }
                else
                {
                    empDTO.setValidationMessage("Password doesnot match.");
                }
                
            } else {
                empDTO.setValidationMessage("Username doesnot exists.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception occurred:validateEmployeeLogin:", e);
        }

        return empDTO;
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public String checkUserSessionInfo(final int userId) throws AppException
    {
        return userLoginDAO.checkUserSessionInfo(userId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int insertUserSessionInfo(final long userId, final String userName, 
            final String session_id,final String session_token)
            throws AppException
    {
        return userLoginDAO.insertUserSessionInfo(userId, session_id, session_token);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int deleteUserSessionInfo(final long userId)
            throws AppException
    {
        return userLoginDAO.deleteUserSessionInfo(userId);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false,
            rollbackFor = {AppException.class,RuntimeException.class, Exception.class})
    public int updateUserSessionInfo(final int userId)
            throws AppException
    {
        return userLoginDAO.updateUserSessionInfo(userId);
    }

}
