/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.jbo.ApplicationModule;

public class TestPageAction extends DataForwardAction  {
  protected void handleLifecycle(DataActionContext ctx) throws Exception {
    DCJboDataControl dc = (DCJboDataControl)ctx.getBindingContext().findDataControl("HRModuleDataControl");
    dc.setReleaseLevel(ApplicationModule.RELEASE_LEVEL_RESERVED);
    super.handleLifecycle(ctx);
  }
}
