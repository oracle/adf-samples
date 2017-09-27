/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;

public class MainAction extends DataForwardAction  {
  public void onLogout(DataActionContext ctx) {
    ctx.getHttpServletRequest().getSession(true).invalidate();
  }
  
}
