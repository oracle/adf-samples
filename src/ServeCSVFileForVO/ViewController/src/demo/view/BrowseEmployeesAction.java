/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.struts.actions.DataForwardAction;
public class BrowseEmployeesAction extends DataForwardAction {
  protected void prepareModel(DataActionContext ctx) throws Exception {
    super.prepareModel(ctx);
    ctx.getBindingContainer().findIteratorBinding("FindEmpView1Iterator")
       .setFindMode(true);
  }
  public void onSearch(DataActionContext ctx) {
    ctx.getBindingContainer().findIteratorBinding("EmpView1Iterator")
       .executeQuery();
  }
}
