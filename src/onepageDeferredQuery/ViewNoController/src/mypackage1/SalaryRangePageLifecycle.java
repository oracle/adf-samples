/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package mypackage1;
import javax.servlet.http.HttpServletRequest;
import oracle.adf.controller.lifecycle.LifecycleContext;
import oracle.adf.controller.lifecycle.PageLifecycle;
import test.model.common.TestModule;

public class SalaryRangePageLifecycle extends PageLifecycle  {
  public void prepareModel(LifecycleContext ctx) throws Exception {
    HttpServletRequest req = ctx.getHttpServletRequest();
    TestModule tm = (TestModule)ctx.getBindingContext().findDataControl("TestModuleDataControl").getDataProvider();
    tm.prepareForSalaryRangeQuery(req.getParameter("low"),
                                  req.getParameter("high"));
    super.prepareModel(ctx);
  }
  public void handleLifecycle(LifecycleContext ctx) throws Exception {
    super.handleLifecycle(ctx);
    /*
     * Set a request attribute to indicate if this is a postback or not
     */
     if (ctx.getEvents() != null && ctx.getEvents().get(0) != null) {
       ctx.getHttpServletRequest().setAttribute("EventName",ctx.getEvents().get(0));      
     }
  }
}
