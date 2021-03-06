package com.pdms.listner;

import com.pdms.constants.SessionConstants;
import com.pdms.dao.impl.ApplicationStaticDao;
import com.pdms.dto.EmployeeLoginDTO;
import com.pdms.exception.AppException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;
public final class PDMSSessionListener
        implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {
     private static final Logger log = Logger.getLogger(PDMSSessionListener.class);

    private ServletContext context = null;

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        log("contextDestroyed()");
        this.context = null;

    }

    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        this.context = event.getServletContext();
        log("contextInitialized()");

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event The session event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {

        log("sessionCreated('" + event.getSession().getId() + "')");

    }

    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event The session event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        log("sessionDestroyed('" + event.getSession().getId() + "')");
        try {
            long userId;
            if (event.getSession().getAttribute(SessionConstants.USER_SESSION) != null) {
                EmployeeLoginDTO sessionUserLoginDTO = (EmployeeLoginDTO) event.getSession().getAttribute(SessionConstants.USER_SESSION);
                userId = sessionUserLoginDTO.getEmployeeID();
                ApplicationStaticDao sessionDao = new ApplicationStaticDao();
                
                //sessionDao.inValidateUsersessionInfo(userId,ApplicationConstants.DB_INDICATOR_DELETE, sessionUserLoginDTO.getUserSessionID());
                sessionDao.deleteSessionInfoForLogoutUser(userId);
            }
        } catch (AppException ex) {
           ex.printStackTrace();
        }

    }

    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
    private void log(String message) {

        if (context != null) {
            context.log("SessionListener: " + message);
        } else {
            System.out.println("SessionListener: " + message);
        }

    }

    /**
     * Log a message and associated exception to the servlet context application
     * log.
     *
     * @param message Message to be logged
     * @param throwable Exception to be logged
     */
    private void log(String message, Throwable throwable) {

        if (context != null) {
            context.log("SessionListener: " + message, throwable);
        } else {
            System.out.println("SessionListener: " + message);
            throwable.printStackTrace(System.out);
        }

    }

    
}
