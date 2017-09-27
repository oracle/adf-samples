/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.util.Hashtable;
import java.util.Properties;
import javax.servlet.http.HttpSession;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.EnvInfoProvider;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.http.HttpSessionCookieFactory;

public class CustomJDBCURLSessionCookieFactory extends HttpSessionCookieFactory {
  public SessionCookie createSessionCookie(String name,
                                           String value,
                                           ApplicationPool pool, 
                                           Properties properties) { 
    SessionCookie cookie = super.createSessionCookie(name, value, pool, properties);
    Hashtable env = pool.getEnvironment();
    env.remove(Configuration.DB_USERNAME_PROPERTY); 
    env.remove(Configuration.DB_PASSWORD_PROPERTY); 
    EnvInfoProvider provider = new CustomJDBCURLEnvInfoProvider();
    cookie.setEnvInfoProvider(provider);
    return cookie; 
  } 

  public Class getSessionCookieClass() {
    try {
      return Class.forName("test.CustomJDBCURLHttpSessionCookieImpl");
    }
    catch (ClassNotFoundException cnfe) 
    {
      cnfe.printStackTrace();
      return null;
    }
  }
} 
