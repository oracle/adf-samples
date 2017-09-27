/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import test.model.common.TestModule;

public class EmployeeListAction extends DataForwardAction  {
  protected void prepareModel(DataActionContext ctx) throws Exception {
    if (ctx.getEvents() == null || ctx.getEvents().size() == 0) {
      TestModule tm = (TestModule)ctx.getBindingContext().findDataControl("TestModuleDataControl").getDataProvider();      tm.prepareToShowEmployeeList();
    }
    super.prepareModel(ctx);
  }
}
