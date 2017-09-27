/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller.backing;
import demo.model.common.TestModule;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
public class PageLoadExampleBacking implements PagePhaseListener {
  public String myButton_action() {
    // Add event code here...
    return null;
  }
  public void beforePhase(PagePhaseEvent event) {
    if (event.getPhaseId() == Lifecycle.PREPARE_MODEL_ID) {
      if (!isPostback())
      /*
       * This is an alternative to using an ADF action binding
       * to invoke the method. We get the custom interface for
       * the AppModule Business Service, then call a method on it.
       */
      getTestModule().doSomething();
    }
  }
  public void afterPhase(PagePhaseEvent event) { }
  private TestModule getTestModule() {
    return (TestModule)resolveExpression("#{data.TestModuleDataControl.dataProvider");
  }  
  private boolean isPostback() {
    return Boolean.TRUE.equals(resolveExpression("#{adfFacesContext.postback}"));
  }
  private Object resolveExpression(String expression) {
    FacesContext ctx = FacesContext.getCurrentInstance();
    Application app = ctx.getApplication();
    ValueBinding bind = app.createValueBinding(expression);
    return bind.getValue(ctx);
  }    
}
