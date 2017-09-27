/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;

public class EmployeeDeleteConfirmAction extends DataForwardAction  {
  public void onDelete(DataActionContext ctx) {
    if (ctx.getEventActionBinding() != null) ctx.getEventActionBinding().doIt();
    ctx.getBindingContainer()
       .findIteratorBinding("EmpViewIterator")
       .getDataControl().commitTransaction();
  }
  
}
