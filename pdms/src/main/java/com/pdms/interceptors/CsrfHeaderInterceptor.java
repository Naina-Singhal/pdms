/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.interceptors;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author hpasupuleti
 */
public class CsrfHeaderInterceptor extends OncePerRequestFilter {

    // name of the cookie
    public static final String XSRF_TOKEN_COOKIE_NAME = "XSRF-TOKEN";

    // name of the header to be receiving from the client
    public static final String XSRF_TOKEN_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Get csrf attribute from request
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName()); // Or "_csrf" (See CSRFFilter.doFilterInternal).

        if (csrf != null) { // if csrf attribute was found

            String token = csrf.getToken();

            if (token != null) { // if there is a token

                // set the cookie
                Cookie cookie = new Cookie(XSRF_TOKEN_COOKIE_NAME, token);
                cookie.setPath("/");
                response.addCookie(cookie);

                // CORS requests can't see the cookie if domains are different,
                // even if httpOnly is false. So, let's add it as a header as well.  
                response.addHeader(XSRF_TOKEN_HEADER_NAME, token);

                System.out.println("Sending CSRF token to client: " + token);
            }
        }
        filterChain.doFilter(request, response);
    }

}
