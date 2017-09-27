/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.jbo.uicli.binding.JUCtrlAttrsBinding;

public class TestPageAction extends DataForwardAction  {
  protected void handleLifecycle(DataActionContext ctx) throws Exception {
    super.handleLifecycle(ctx);
    DCBindingContainer bc = ctx.getBindingContainer();
    if (bc.findCtrlBinding("Foo") == null) {
      bc.addControlBinding("Foo",new JUCtrlAttrsBinding(null,bc.findIteratorBinding("DeptView1Iterator"),new String[]{"Foo"}));
    }
  }

}
