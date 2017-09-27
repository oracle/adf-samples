/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.security.Principal;

import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.http.HttpSessionCookieImpl;
import oracle.jbo.common.ampool.EnvInfoProvider;


public class DynamicJDBCHttpSessionCookieImpl extends HttpSessionCookieImpl {
  public DynamicJDBCHttpSessionCookieImpl(java.lang.String applicationId,
                               java.lang.String sessionId,
                               ApplicationPool pool) {
    super(applicationId, sessionId, pool);
  }                             
  public DynamicJDBCHttpSessionCookieImpl(String applicationId, String sessionId, 
                               ApplicationPool pool, Principal userPrincipal) {
    super(applicationId, sessionId, pool, userPrincipal);
  }
  public void setEnvInfoProvider(EnvInfoProvider envInfoProvider) {
    if(envInfoProvider!=null) {
      super.setEnvInfoProvider(envInfoProvider);
    }
  }
}
