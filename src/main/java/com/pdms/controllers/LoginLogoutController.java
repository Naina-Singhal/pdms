/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.controllers;

import com.google.gson.Gson;
import com.pdms.constants.SessionConstants;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.dto.ExceptionDTO;
import com.pdms.dto.GroupDTO;
import com.pdms.dto.IbcNumberDTO;
import com.pdms.dto.UserPermissionDTO;
import com.pdms.exception.AppException;
import com.pdms.master.service.impl.EmployeeServiceImpl;
import com.pdms.service.impl.UserLoginServiceImpl;
import com.pdms.service.impl.UserPermissionServiceImpl;
import com.pdms.utils.DeleteCookies;
import com.pdms.utils.EncryptDecrypt;
import com.pdms.utils.GenerateRandomNumber;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hpasupuleti
 */
@Controller
public class LoginLogoutController {
    
    private static final Logger log = Logger.getLogger(LoginLogoutController.class);
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DeleteCookies deleteCookies;
    
    @Autowired
    private GenerateRandomNumber generateRandom;
    
    @Autowired
    private UserLoginServiceImpl userLoginService;
    
    @Autowired
    private EmployeeServiceImpl empService;
    
    @Autowired
    private UserPermissionServiceImpl permissionServiceImpl;
    
    @RequestMapping(value = "/site")
    public ModelAndView SiteHomePageView(HttpServletRequest request,
            HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        session.invalidate();
        
        return new ModelAndView("SiteHome");
    }
    
    
    @RequestMapping(value = "/login")
    public ModelAndView LoginView(HttpServletRequest request,
            HttpServletResponse response)
    {
        HttpSession session = request.getSession();
        session.invalidate();
        String message="";
        if(request.getParameter("msg") != null)
        {
            message = request.getParameter("msg");
        }
        return new ModelAndView("Login","message",message);
    }
    
    @RequestMapping(value = "/logout")
    public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        log.info("Start : logout");
        try {
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                log.info("In logOut:User Session Found");
                EmployeeLoginDTO userSessionDto = (EmployeeLoginDTO) 
                        session.getAttribute(SessionConstants.USER_SESSION);
                if (userSessionDto != null) {
                    userLoginService.deleteUserSessionInfo(Integer.parseInt(userSessionDto.getEmployeeID()+""));
                }
            }
            session.invalidate();
            deleteCookies.eraseCookies(request, response);
        } catch (Exception ie) {
            log.debug("Exception Occured while Executing the "
                    + "logOut() :: " + ie.getMessage());
            ExceptionDTO exception = new ExceptionDTO("ERR:401", "Unable to Find JSP", ie);
            return new ModelAndView("/commons/Exception", "exceptionBean", exception);
        }
        return new ModelAndView("redirect:/login.htm");
        //return new ModelAndView("redirect:/loginpage.htm");
    }
    
    @RequestMapping(value = "/validatelogin")
    public ModelAndView ValidateLogin(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView mv = new ModelAndView();
        HttpSession session = request.getSession();
        try
        {
            EmployeeLoginDTO empDTO = userLoginService.validateEmployeeLogin(request);
            if(StringUtils.isEmpty(empDTO.getValidationMessage()))
            {
                session.setAttribute(SessionConstants.USER_SESSION, empDTO);
                mv.setViewName("redirect:/home.htm");
            }
            else
            {
                mv.setViewName("redirect:/login.htm");
                mv.addObject("msg", empDTO.getValidationMessage());
            }
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "ValidateLogin :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/Exception", "exceptionBean", exception);
        }
        
        return mv;
    }
    
    @RequestMapping(value = "/home")
    public ModelAndView HomePageView(HttpServletRequest request,
            HttpServletResponse response)
    {
        ModelAndView modelView = new ModelAndView();
        try
        {               
            modelView.addObject("userPermiLi", permissionServiceImpl.getUserPermissionObj(request));
            modelView.setViewName("Home");
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        return modelView;
    }
    
    
    @RequestMapping(value = "/getSessionTime", method = RequestMethod.POST)
    public @ResponseBody
    String getUserSessionTime(HttpServletRequest request, HttpServletResponse response) {
        String returnSessionTime = "0";
        try {
            HttpSession session = request.getSession();
            returnSessionTime = session.getMaxInactiveInterval() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnSessionTime;
    }
    
    
    @RequestMapping(value = "/sessionInactive")
    public ModelAndView sessionInactive(final HttpServletRequest request,
            final HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            session.invalidate();
            deleteCookies.eraseCookies(request, response);
        } catch (Exception e) {
            e.getMessage();
        }

        return new ModelAndView("redirect:/logout.htm");
    }
    
    @RequestMapping(value = "/usrprofile")
    public ModelAndView UserProfilePageView(HttpServletRequest request,
            HttpServletResponse response,
            Model model)
    {
        HttpSession session = request.getSession();
        ModelAndView mv = new ModelAndView();
        try
        {
            EmployeeLoginDTO userSessionDto = new EmployeeLoginDTO();
            EmployeeLoginDTO userDto = new EmployeeLoginDTO();
            if (session.getAttribute(SessionConstants.USER_SESSION) != null) {
                log.info("In logOut:User Session Found");
                userSessionDto = (EmployeeLoginDTO) 
                        session.getAttribute(SessionConstants.USER_SESSION);
            }
         
            mv.addObject("userSessObj", userSessionDto);
//            if(model.asMap().containsKey("msg"))
//            {
//                mv.addObject("msg", model.asMap().get("msg"));
//            }
//            
            mv.setViewName("commons/UserProfile");
        }
        catch(Exception e)
        {
            log.debug("Exception Occured while Executing the "
                    + "ValidateLogin :: " + e.getMessage());
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/Exception", "exceptionBean", exception);
        }
        return mv;
    }
    
    
    @RequestMapping(value = "/updprofile")
    public ModelAndView UpdateUserProfileAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("userProfileObj") EmployeeLoginDTO empLogin,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        
        try
        {
            int resVal = empService.updateUserProfile(empLogin, request);
            if(resVal > 0)
            {
                redirectAttributes.addAttribute("msg", "Profile updated Successfully.Please re-login again.");
                modelView.setViewName("redirect:/login.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    @RequestMapping(value = "/updpswd")
    public ModelAndView UpdateUserPasswordAction(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute("userProfileObj") EmployeeLoginDTO empLogin,
            RedirectAttributes redirectAttributes)
    {
        ModelAndView modelView = new ModelAndView();
        
        try
        {
            int resVal = empService.updateUserPassword(request, empLogin);
            if(resVal > 0)
            {
                redirectAttributes.addAttribute("msg", "Password updated Successfully. Please re-login again.");
                modelView.setViewName("redirect:/login.htm");
            }
            else if(resVal == -2)
            {
                redirectAttributes.addFlashAttribute("msg", "Current password doesn't match.");
                modelView.setViewName("redirect:/usrprofile.htm");
            }
            else
            {
                ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred in Transaction. Please contact Administrator.");
                return new ModelAndView("/commons/AppException", "exceptionBean", exception);
            }
        }
        catch(Exception e)
        {
            ExceptionDTO exception = new ExceptionDTO
                    ("ERR:401", "Problem Occurred. Please contact Administrator.", e);
            return new ModelAndView("/commons/AppException", "exceptionBean", exception);
        }
        
        return modelView;
    }
    
    
}
