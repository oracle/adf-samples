/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import test.model.common.TestModule;

public class DeleteMultipleEmpsAction extends DataForwardAction  {
  public void onDeleteMultiple(DataActionContext ctx) {
    TestModule tm = (TestModule)ctx.getBindingContext().findDataControl("TestModuleDataControl").getDataProvider();
    tm.deleteMultipleEmpsByEmpno(ctx.getHttpServletRequest().getParameterValues("empnos"));
  }
}
