/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.http.HttpSessionCookieImpl;
import oracle.jbo.common.ampool.EnvInfoProvider;


public class DynamicJDBCHttpSessionCookieImpl extends HttpSessionCookieImpl {
  public DynamicJDBCHttpSessionCookieImpl(java.lang.String applicationId,
                               java.lang.String sessionId,
                               ApplicationPool pool) {
    super(applicationId, sessionId, pool);
  }
  public DynamicJDBCHttpSessionCookieImpl(java.lang.String applicationId,
                               java.lang.String sessionId,
                               ApplicationPool pool,
                               java.security.Principal userPrincipal,
                               javax.servlet.http.HttpServletRequest request) {
    super(applicationId, sessionId, pool, userPrincipal, request);
  }
  
  public DynamicJDBCHttpSessionCookieImpl(java.lang.String applicationId,
                               java.lang.String sessionId,
                               ApplicationPool pool,
                               java.security.Principal userPrincipal,
                               javax.servlet.http.HttpSession session) {
    super(applicationId, sessionId, pool, userPrincipal, session);
  }
                             
  public void setEnvInfoProvider(EnvInfoProvider envInfoProvider) {
    if(envInfoProvider!=null) {
      super.setEnvInfoProvider(envInfoProvider);
    }
  }
}
