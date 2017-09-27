/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;

public class RefreshOnDemandAction extends MyDataForwardAction  {
  public void onRefreshData(DataActionContext ctx) {
    executeQueryForIter(ctx,"DeptView1Iterator");
  }
}
