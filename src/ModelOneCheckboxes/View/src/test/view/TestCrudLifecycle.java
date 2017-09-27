/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import javax.servlet.http.HttpServletRequest;
import oracle.adf.controller.lifecycle.LifecycleContext;
import oracle.adf.controller.lifecycle.PageLifecycle;
import oracle.adf.model.binding.DCUtil;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;
import java.util.Map;

public class TestCrudLifecycle extends PageLifecycle  {
  private boolean isNullOrEmpty(String s) {
    return s == null || s.equals("");
  }
  private void setBindingFalseIfParamMissing(LifecycleContext ctx, String binding) {
    HttpServletRequest req = ctx.getHttpServletRequest();
    if (isNullOrEmpty(req.getParameter(binding)) &&
        isNullOrEmpty(req.getParameter(DCUtil.DATA_PREFIX+binding))) {
      JUCtrlValueBinding b = (JUCtrlValueBinding)ctx.getBindingContainer().findCtrlBinding(binding);
      if (!"N".equals(b.getInputValue())) {
        b.setInputValue("N");
      }
    }      
  }
  public void onCommit(LifecycleContext ctx) {
    ctx.getEventActionBinding().doIt();
  }
  protected void updateModel(LifecycleContext ctx, Map updatedValues) {
    super.updateModel(ctx, updatedValues);
    setBindingFalseIfParamMissing(ctx,"OptionOne");
    setBindingFalseIfParamMissing(ctx,"OptionTwo");
  }
}
