/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;

public class MainPageController extends PageController {
    public void onLogout(PageLifecycleContext ctx) {
      ((HttpServletRequest)ctx.getEnvironment().getRequest()).getSession(true).invalidate();
    }
}
