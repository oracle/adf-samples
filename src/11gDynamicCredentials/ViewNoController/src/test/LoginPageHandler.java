/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageLifecycleImpl;

public class LoginPageHandler extends PageLifecycleImpl {
  public void onLogout(PageLifecycleContext ctx) {
    PageLifecycleContext pctx = (PageLifecycleContext)ctx;
    ((HttpServletRequest)pctx.getEnvironment().getRequest()).getSession(true).invalidate();
    pctx.setForwardPath("/login.jsp");
    pctx.setRedirect(true);
  }
}
