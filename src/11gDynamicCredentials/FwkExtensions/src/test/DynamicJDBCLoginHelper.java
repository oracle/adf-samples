/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.sql.SQLException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;
/*
 * Attempts to use the ADFContext from code the fires before the
 * call to super.doFilter() in a subclass of the ADFBindingFilter
 * (whose doFilter() method instantiates the HttpBindingRequestHandler
 * which, in turn, initializes the ServletADFContext) end up getting
 * a Default implementation of the ADFContext interface which is
 * not web-aware, and hence attempts to use the session map or access
 * the session do not work as expected. So, I ended up having to pass
 * in the HttpSession to the helper methods that get called from the
 * ADFBindingFilter subclass code that runs before the call to 
 * super.doFilter().
 */
public class DynamicJDBCLoginHelper {
    private static final int INVALID_USERNAME_PASSWORD_ORACLE_ERROR = 1017;
    private static final int ACCOUNT_LOCKED_ORACLE_ERROR = 28000;    
    private static final int NO_SUITABLE_DRIVER = 0;    
    private static final int NETWORK_CONNECTION_ERROR = 17002;    
    
    /**
     * Returns true if user is currently logged in
     */
    public static boolean isLoggedIn(HttpSession session) {
        boolean loggedIn = isLoggedInUsingHttpSession(session);
        return loggedIn;
    }

    public static void logIn(HttpSession session) {
         logInUsingHttpSession(session);
    }

    public static void logOut(HttpSession session) {
         logOutUsingHttpSession(session);
    }
    public static boolean isFailedDBConnectErrorCode(SQLException s) {
        int errorCode = s.getErrorCode();
        return (errorCode == INVALID_USERNAME_PASSWORD_ORACLE_ERROR ||
            errorCode == ACCOUNT_LOCKED_ORACLE_ERROR||
            errorCode == NO_SUITABLE_DRIVER   ||
            errorCode == NETWORK_CONNECTION_ERROR
            );
    }    

    // ---- PRIVATE IMPLEMENTATION ----    
    
    private static final String LOGGEDIN_ATTR = "loggedin";
    private static final String NON_NULL_VALUE = "x";

    private static boolean isLoggedInUsingHttpSession(HttpSession session) {
        boolean loggedIn = session.getAttribute(LOGGEDIN_ATTR) != null;
        return loggedIn;
    }
    private static void logInUsingHttpSession(HttpSession session) {
        session.setAttribute(LOGGEDIN_ATTR,NON_NULL_VALUE);
    }    
    private static void logOutUsingHttpSession(HttpSession session) {
        session.removeAttribute(LOGGEDIN_ATTR);
    }
}
