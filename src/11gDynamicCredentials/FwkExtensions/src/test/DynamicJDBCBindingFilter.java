/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.awt.List;

import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.servlet.ADFBindingFilter;
import oracle.adf.share.ADFContext;

import oracle.jbo.DMLException;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.JBOClass;
import oracle.jbo.common.ampool.ApplicationPoolException;

/**
 * This class extends the ADFBindingFilter to:
 * 
 *    (1) Intercept dynamic credentials username/password information
 *        (easily extendible to include datasource/jdbcURL info, too)
 *        and set that information on the HTTP session where the
 *        DynamicJDBCSessionCookieFactory can access it in order to pass
 *        it to the constructor of the DynamicJDBCEnvInfoProvider that it
 *        creates and sets on the DynamicHttpSessionCookieImpl class.
 *        
 *    (2) Track whether a user is logged in or not with an HTTP Session
 *        attribute, and if not logged in forward to the login page
 *        (whose physical name is retrieved as a web.xml context parameter
 *        named "RedirectToLogin"
 *        
 *    (3) Catch failed login attempts and redirect the user to the login
 *        page after setting a flag that indicates that a login failed.
 *        
 * This class will be used at runtime when you modify the web.xml of a
 * given application to use this DynamicJDBCBindingFilter instead of the
 * default ADFBindingFilter. Study the web.xml file for each "viewcontroller"
 * project in this workspace for an example.
 * 
 *   <filter>
 *     <filter-name>ADFBindingFilter</filter-name>
 *     <filter-class>test.DynamicJDBCBindingFilter</filter-class>
 *   </filter>
 */
public class DynamicJDBCBindingFilter extends ADFBindingFilter {
    public DynamicJDBCBindingFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, 
                         FilterChain chain) throws IOException, 
                                                   ServletException {
        HttpServletRequest svrRequest = (HttpServletRequest)request;
        HttpSession session = svrRequest.getSession(true);
        if (!DynamicJDBCLoginHelper.isLoggedIn(session)) {
            if (!isLoginRequest(svrRequest)) {
                String method = svrRequest.getMethod();
                String requestUri = svrRequest.getRequestURI();
                if (method.equalsIgnoreCase("GET") && 
                    requestUri.endsWith(loginPageRedirectName())) {
                    super.doFilter(request, response, chain);
                } else {
                    forwardToLoginPage(request, response);
                }
            } else {
                try {
                    String usrName = getRequestParamWithOptionalFormPrefix(USERNAME_PARAM,svrRequest);
                    String pswd = getRequestParamWithOptionalFormPrefix(PASSWORD_PARAM,svrRequest);
                    /*
                     * NOTE: If this parameter is null, it will inherit it from the configuration
                     * ----  instead of overriding it.
                     */
                    String jdbcURL = null;
                    

                    /*
                     * COMMENT OUT THIS LINE if you don't want the JDBCURL to be able
                     * to be set from the HTTP request.
                     */
                    jdbcURL = getRequestParamWithOptionalFormPrefix(JDBCURL_PARAM,svrRequest);

                    if (usrName == null || usrName.length() == 0 || 
                        pswd == null || pswd.length() == 0) {
                        throw new BlankUserNameOrPassword();
                    }
                    session.setAttribute(Configuration.DB_USERNAME_PROPERTY, 
                                         usrName);
                    session.setAttribute(Configuration.DB_PASSWORD_PROPERTY, 
                                         pswd);
                    if (jdbcURL != null) {
                        session.setAttribute(Configuration.DB_CONNECT_STRING_PROPERTY, 
                                             jdbcURL);
                    }
                    super.doFilter(request, response, chain);
                    DCBindingContainer bc = (DCBindingContainer)request.getAttribute("bindings");
                    if (bc != null && bc.getExceptionsList() != null) {
                        for (Object e : bc.getExceptionsList()) {
                            if (e instanceof Exception) {
                                if (isFailedLoginException((Exception)e)) {
                                    handleFailedLoginAttempt(svrRequest,response);
                                }
                            }
                        }
                    }
                    if (request.getAttribute(FAILED_ATTR) == null) {
                      DynamicJDBCLoginHelper.logIn(session);
                    }
                } catch (Exception e) {
                    if (isFailedLoginException(e)) {
                      handleFailedLoginAttempt(svrRequest,response);                        
                    } else if (e instanceof ServletException) {
                        throw (ServletException)e;
                    } else {
                        e.printStackTrace();
                        throw new ServletException(e);
                    }
                }
            }
        } else {
            super.doFilter(request, response, chain);
        }
    }
    
    private void handleFailedLoginAttempt(HttpServletRequest request, ServletResponse response) throws ServletException, IOException {
        try {
          response.resetBuffer();
          signalFailedLoginAttempt(request);
        } catch (Exception ee) {
               ee.printStackTrace();
        } finally {
          redirectToLoginPageOnLogonError(request, response);
        }       
    }

    /**
     * Encapsulate detecting if the exception thrown
     * represents a failed login attempt.
     */
    private boolean isFailedLoginException(Exception e) {
        /*
     * In 9.0.5.2, our eager AM acquisition will
     * cause the application pool exception to appear
     * without being wrapped.
     */
        if (e instanceof ApplicationPoolException || 
            e instanceof BlankUserNameOrPassword) {
            return true;
        }
        else if (e instanceof DMLException) {
            Object[] details = ((DMLException)e).getDetails();
            if (details != null && details.length > 0) {
                if (details[0] instanceof SQLException) {
                    SQLException s = (SQLException)details[0];
                    if (DynamicJDBCLoginHelper.isFailedDBConnectErrorCode(s)) {
                        return true;
                    }
                }
            }
        }
        else if (e instanceof ServletException) {
            Throwable rootCause = ((ServletException)e).getRootCause();
            /*
       * In 10.1.2 Struts, our lazy AM acquisition causes
       * the failed database login to be wrapped in a
       * ServletException that contains the ApplicationPoolException
       */
            if (rootCause instanceof ApplicationPoolException) {
                return true;
            }
            /*
       * In 10.1.2 JSP Model 1, our lazy AM acquisition causes
       * the failed database login to be wrapped in a
       * ServletException that contains a JspTagException.
       * The JspTagException does not wrap the original error,
       * but only encapsulate the original errors getMessage() value.
       */
            if (rootCause instanceof JspTagException) {
                String errMsg = ((JspTagException)rootCause).getMessage();
                if (errMsg != null && (errMsg.contains(APP_POOL_ERR_MSG)||
                                       errMsg.contains(DBCONN_ERR_MSG))) {
                    return true;
                }
            }
            if (rootCause instanceof DMLException) {
              Object[] details = ((DMLException)rootCause).getDetails();
              if (details != null && details.length > 0) {
                  if (details[0] instanceof SQLException) {
                      SQLException s = (SQLException)details[0];
                      if (DynamicJDBCLoginHelper.isFailedDBConnectErrorCode(s)) {
                          return true;
                      }
                  }
              }
            }
        }
        return false;
    }
    /**
     * Forwards control to the login page.
     */
    private void forwardToLoginPage(ServletRequest request, 
                                    ServletResponse response) throws ServletException, 
                                                                     IOException {
        HttpServletResponse responseHttp = (HttpServletResponse)response;
        responseHttp.sendRedirect(loginPageRedirectName());
        markResponseCompleteIfUsingJSF();
    }

    private void redirectToLoginPage(ServletRequest request, 
                                     ServletResponse response) throws ServletException, 
                                                                      IOException {
        HttpServletResponse responseHttp = (HttpServletResponse)response;
        responseHttp.sendRedirect(loginPageRedirectName());
        markResponseCompleteIfUsingJSF();  
    }

    private void redirectToLoginPageOnLogonError(ServletRequest request, 
                                                 ServletResponse response) throws ServletException, 
                                                                                  IOException {
        HttpServletResponse responseHttp = (HttpServletResponse)response;
        responseHttp.sendRedirect(loginPageRedirectName((HttpServletRequest)request) + "?failed=true");
        markResponseCompleteIfUsingJSF();        
    }

    /**
     * Signal a failed login attempt
     */
    private void signalFailedLoginAttempt(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        DynamicJDBCLoginHelper.logOut(session);
        request.setAttribute(FAILED_ATTR, NON_NULL_VALUE);
        session.invalidate();
    }
    /*
     * If we are running in a Faces environment, invoke
     * the FacesContext.responseComplete() method after
     * the session invalidate. We use Java reflection
     * so that our code can still work in a Non-Faces
     * environment, too.
     */
    private void markResponseCompleteIfUsingJSF() {
        try {
            Class c = JBOClass.forName("javax.faces.context.FacesContext");
            Method m = c.getMethod("getCurrentInstance", null);
            Object obj = m.invoke(null, null);
          if (obj != null) {
            m = c.getMethod("responseComplete",null);
            m.invoke(obj,null);
          }
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            // Ignore, we're not running in a faces context.
        }        
    }


    /**
     * Returns true if the current request is a login request.
     */
    private boolean isLoginRequest(HttpServletRequest request) {
        return getRequestParamWithOptionalFormPrefix(IS_LOGIN_PAGE_PARAM,request) != null && 
            request.getMethod().equalsIgnoreCase("POST");
    }
    private static final String NON_NULL_VALUE = "x";
    private static final String LOGIN_PAGE_REDIRECT_PARAM = "RedirectToLogin";
    private static final String FACES_URL_PATTERN_PARAM = "FacesURLPattern";
    private static final String FAILED_ATTR = "failed";
    private static final String FORM_PREFIX = "form:";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";
    private static final String JDBCURL_PARAM = "jdbcurl";
    private static final String IS_LOGIN_PAGE_PARAM = "_loginpage";
    private static final String APP_POOL_ERR_MSG = "JBO-30003";
    private static final String DBCONN_ERR_MSG = "JBO-26061";
    private String loginPageRedirectName = null;
    private String facesURLPattern = null;
    private String loginPageRedirectName() {
        return loginPageRedirectName;
    }
    private String loginPageRedirectName(HttpServletRequest request) {
        if (facesURLPattern != null) {
            if (request.getRequestURI().indexOf(facesURLPattern) == -1) {
                return facesURLPattern+loginPageRedirectName;
            }
        }
        return loginPageRedirectName;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        loginPageRedirectName = 
                filterConfig.getServletContext().getInitParameter(LOGIN_PAGE_REDIRECT_PARAM);
        facesURLPattern = 
                filterConfig.getServletContext().getInitParameter(FACES_URL_PATTERN_PARAM);

    }
    private String getRequestParamWithOptionalFormPrefix(String paramName, HttpServletRequest req) {
        Object val = req.getParameter(paramName);
        if (val == null) {
            val = req.getParameter(FORM_PREFIX+paramName);
        }
        String ret = (String)val;
        if (ret != null && ret.length() == 0) {
            ret = null;
        }
        return ret;
    }
}
