/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import javax.servlet.http.HttpServletRequest;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import test.model.common.TestModule;

public class SalaryRangeAction extends DataForwardAction  {

  protected void prepareModel(DataActionContext ctx) throws Exception {
    HttpServletRequest req = ctx.getHttpServletRequest();
    TestModule tm = (TestModule)ctx.getBindingContext().findDataControl("TestModuleDataControl").getDataProvider();
    tm.prepareForSalaryRangeQuery(req.getParameter("low"),
                                  req.getParameter("high"));
    super.prepareModel(ctx);
  }

  protected void handleLifecycle(DataActionContext ctx) throws Exception {
    super.handleLifecycle(ctx);
    /*
     * Set a request attribute to indicate if this is a postback or not
     */
     if (ctx.getEvents() != null && ctx.getEvents().get(0) != null) {
       ctx.getHttpServletRequest().setAttribute("EventName",ctx.getEvents().get(0));      
     }
  }
  
}
