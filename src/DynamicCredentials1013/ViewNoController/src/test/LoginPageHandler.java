/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.adf.controller.lifecycle.LifecycleContext;
import oracle.adf.controller.lifecycle.PageLifecycle;

public class LoginPageHandler extends PageLifecycle  {
  public void onLogout(LifecycleContext ctx) {
    ctx.getHttpServletRequest().getSession(true).invalidate();
    ctx.setForwardPath("login.jsp");
  }
}
