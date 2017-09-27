/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;

import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.EnvInfoProvider;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.http.HttpSessionCookieFactory;
/**
 * This custom session cookie factory class is configured by setting
 * the jbo.ampool.sessioncookiefactoryclass property in the ADFBC
 * configuration environment properties.
 * 
 * In this workspace, this property is configured on the "TestModuleLocal"
 * configuration of the TestModule application module.
 */
public class DynamicJDBCSessionCookieFactory extends HttpSessionCookieFactory { 
  public SessionCookie createSessionCookie(String name,
                                           String value,
                                           ApplicationPool pool, 
                                           Properties properties) { 
    SessionCookie cookie = super.createSessionCookie(name, value, pool, properties);
    if (properties != null) {
      HttpServletRequest request = (HttpServletRequest)ADFContext.getCurrent().getEnvironment().getRequest();
      HttpSession session = request.getSession(false);
      if(session != null) { 
        Hashtable env = pool.getEnvironment();
        env.remove(Configuration.DB_USERNAME_PROPERTY); 
        env.remove(Configuration.DB_PASSWORD_PROPERTY); 
        EnvInfoProvider provider = new DynamicJDBCEnvInfoProvider(
            (String)session.getAttribute(Configuration.DB_USERNAME_PROPERTY), 
            (String)session.getAttribute(Configuration.DB_PASSWORD_PROPERTY),
            (String)session.getAttribute(Configuration.DB_CONNECT_STRING_PROPERTY)
        ); 
        cookie.setEnvInfoProvider(provider);
      } 
    } 
    return cookie; 
  } 

  public Class getSessionCookieClass() {
    try {
      return Class.forName("test.DynamicJDBCHttpSessionCookieImpl");
    }
    catch (ClassNotFoundException cnfe) 
    {
      cnfe.printStackTrace();
      return null;
    }
  }
} 
