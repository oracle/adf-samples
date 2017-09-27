/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.model.binding.DCIteratorBinding;

public class RefreshEveryTimeAction extends MyDataForwardAction  {
  protected void prepareModel(DataActionContext ctx) throws Exception {
    executeQueryForIter(ctx,"DeptView1Iterator");
    super.prepareModel(ctx);
  }
}
