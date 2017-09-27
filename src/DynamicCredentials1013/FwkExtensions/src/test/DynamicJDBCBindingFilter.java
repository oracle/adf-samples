/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import oracle.adf.model.servlet.ADFBindingFilter;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.ApplicationPoolException;

public class DynamicJDBCBindingFilter extends ADFBindingFilter {
  public DynamicJDBCBindingFilter() {
  }
   public void doFilter(ServletRequest request,
                        ServletResponse response,
                        FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest svrRequest = (HttpServletRequest)request;
    HttpSession session = svrRequest.getSession(true);
    if(!isLoggedIn(session)) {
      if(!isLoginRequest(svrRequest)) {
        forwardToLoginPage(request,response);
      }
      else {
        try {
          String usrName = request.getParameter(USERNAME_PARAM);
          String pswd = request.getParameter(PASSWORD_PARAM);
          
          /*
           * If you need to support dynamically changing the JDBC URL
           * (for example to support changing which database instance
           * you are pointing at on the fly, then you need to change
           * the line of code below to assign a non-null value to the
           * jdbcURL variable. You would need to take the user-supplied
           * information and formulate a valid JDBC connection URL connection
           * string (without username and password) that looks like this:
           * 
           *     jdbc:oracle:thin:@localhost:1521:ORCL
           *                       --------- ---- ----
           *                        machine  port  SID
           *                        
           * and then set this the jdbcURL variable to this value. 
           */          
          String jdbcURL = null;
          
          if(usrName == null || usrName.length() == 0 ||
             pswd == null || pswd.length() == 0) 
          {
            throw new BlankUserNameOrPassword();
          }
          session.setAttribute(Configuration.DB_USERNAME_PROPERTY, usrName);
          session.setAttribute(Configuration.DB_PASSWORD_PROPERTY, pswd); 
          if (jdbcURL != null) {
            session.setAttribute(Configuration.DB_CONNECT_STRING_PROPERTY,jdbcURL);
          }
        
          super.doFilter(request, response,chain);
          session.setAttribute(LOGGEDIN_ATTR,NON_NULL_VALUE);
        }
        catch (Exception e) {
          if (isFailedLoginException(e)) {
            signalFailedLoginAttempt(svrRequest);
            forwardToLoginPage(request,response);
          }
          else if (e instanceof ServletException) {
            throw (ServletException)e;
          }
          else {
            throw new ServletException(e);
          }
        }
      }
    }
    else {
      super.doFilter(request, response,chain);
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
        if (errMsg != null && errMsg.startsWith(APP_POOL_ERR_MSG)) {
          return true;
        }
      }
    }
    return false;
  }
  /**
   * Forwards control to the login page.
   */
  private void forwardToLoginPage(ServletRequest request,
                                  ServletResponse response)
          throws ServletException, IOException {
    request.getRequestDispatcher(LOGIN_PAGE_FORWARD).forward(request, response);
  }
  /**
   * Signal a failed login attempt
   */
  private void signalFailedLoginAttempt(HttpServletRequest request) {
    HttpSession session = request.getSession(true);
    session.setAttribute(LOGGEDIN_ATTR, null);
    request.setAttribute(FAILED_ATTR,NON_NULL_VALUE);
    session.invalidate();
  }
  /**
   * Returns true if user is currently logged in
   */
  private boolean isLoggedIn(HttpSession session) {
    return session.getAttribute(LOGGEDIN_ATTR) != null;
  }
  /**
   * Returns true if the current request is a login request.
   */
  private boolean isLoginRequest(HttpServletRequest request) {
    return request.getParameter(IS_LOGIN_PAGE_PARAM) != null && 
           request.getMethod().equalsIgnoreCase("POST");
  }
  private static final String LOGIN_PAGE_FORWARD  = "/login.jsp";
  private static final String LOGGEDIN_ATTR       = "loggedin";
  private static final String FAILED_ATTR         = "failed";
  private static final String USERNAME_PARAM      = "username";
  private static final String PASSWORD_PARAM      = "password";
  private static final String IS_LOGIN_PAGE_PARAM = "_loginpage";
  private static final String NON_NULL_VALUE      = "x";
  private static final String APP_POOL_ERR_MSG    = "JBO-30003";

}
