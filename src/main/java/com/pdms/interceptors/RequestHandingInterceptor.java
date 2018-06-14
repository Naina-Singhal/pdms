/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.interceptors;

import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.service.impl.UserLoginServiceImpl;
import com.pdms.utils.DBConnection;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hpasupuleti
 */
@Component
//@PropertySource(value = "classpath:application.properties",ignoreResourceNotFound = true)
public class RequestHandingInterceptor implements HandlerInterceptor {

    private static boolean redirectFlag = false;

    @Autowired
    private UserLoginServiceImpl userLoginService;
    
//  
//    @Value("${application.url}")
//    private String APP_URL; 
//    
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        
        try {
            
            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("application.properties");
            Properties prop = new Properties();
            prop.load(input);

            
            final String APP_URL = prop.get("application.url").toString().trim();
            final String redirectPath = APP_URL + "/logout";
            final String siteHomePagePath = APP_URL + "/site.htm";
            final String loginRedirectPath = APP_URL + "/login.htm";
            final String logoutRedirectPath = APP_URL + "/logout.htm";
            final String loginValidatePath = APP_URL + "/validatelogin.htm";
            final String inActiveSessionPath = APP_URL + "/sessionInactive.htm";
            
            
            if (session != null) {
                
                if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                    redirectFlag = true;
                    EmployeeLoginDTO empDTO= (EmployeeLoginDTO)session.getAttribute(SessionConstants.USER_SESSION);
                    int userId = Integer.parseInt(empDTO.getEmployeeID()+"");
                    String sessionID = empDTO.getSessionToken();
                    if (!StringUtils.isEmpty(sessionID)) {
                        String dbSessionID = userLoginService.checkUserSessionInfo(userId);
                        if ((!StringUtils.isEmpty(dbSessionID)) && (dbSessionID.equalsIgnoreCase(sessionID))) {
                            userLoginService.updateUserSessionInfo(userId);
                            return true;
                        } else {
                            response.sendRedirect(redirectPath);
                            return true;
                        }
                    } else {
                        response.sendRedirect(redirectPath);
                        return true;
                    }

                } else {
                    String reqURL = request.getRequestURL().toString();
                    if((!reqURL.equalsIgnoreCase(loginRedirectPath)) 
                        && (!reqURL.equalsIgnoreCase(loginValidatePath))
                        && (!reqURL.equalsIgnoreCase(siteHomePagePath))
                        && (!reqURL.equalsIgnoreCase(inActiveSessionPath))
                        && (!reqURL.equalsIgnoreCase(logoutRedirectPath)))
                    {
                        response.sendRedirect(redirectPath);
                        return true;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        System.out.println("Post-handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("After completion handle");
    }
}
