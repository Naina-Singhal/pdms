/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;

/**
 *
 * @author hpasupuleti
 */
//@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ExceptionDTO implements Serializable{

    private static final long serialVersionUID = -7685128056923520473L;
    
    
    private String exceptionCode;
    private String exceptionMessage;
    private Exception exception;

    /**
     * @return the exceptionCode
     */
    public String getExceptionCode() {
        return exceptionCode;
    }

    /**
     * @param exceptionCode the exceptionCode to set
     */
    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    /**
     * @return the exceptionMessage
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * @param exceptionMessage the exceptionMessage to set
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    
    public ExceptionDTO(final String paramExceptionCode,final String paramExceptionMessage)
    {
        this.exceptionCode=paramExceptionCode;
        this.exceptionMessage=paramExceptionMessage;
    }
    public ExceptionDTO(final String paramExceptionCode,final String paramExceptionMessage,final Exception paramException)
    {
        this.exceptionCode=paramExceptionCode;
        this.exceptionMessage=paramExceptionMessage;
        this.exception = paramException;
    }
    
     public ExceptionDTO()
     {
         
     }

    /**
     * @return the exceptionStackTrace
     */
    public Exception getException() {
        return exception;
    }

    /**
     * @param exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    
    
}
