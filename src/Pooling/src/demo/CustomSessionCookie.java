/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.http.HttpSessionCookieImpl;
/**
 *  An empty custom SessionCookie implementation.  This stub could be used
 *  to implement custom SessionCookie logic.
 */
public class CustomSessionCookie extends HttpSessionCookieImpl {
  public CustomSessionCookie(String applicationId, String sessionId,
    ApplicationPool pool) {
    super(applicationId, sessionId, pool);
  }
  public CustomSessionCookie(String applicationId, String sessionId,
    ApplicationPool pool, Principal user, HttpSession session) {
    super(applicationId, sessionId, pool, user);
  }
}
