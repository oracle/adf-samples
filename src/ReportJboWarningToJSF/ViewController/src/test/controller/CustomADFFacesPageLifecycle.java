/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.faces.application.FacesMessage.Severity;

import oracle.adf.controller.faces.lifecycle.FacesPageLifecycle;
import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCErrorHandler;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboExceptionHandler;
import oracle.jbo.JboWarning;

import test.model.InformationalMessage;

public class CustomADFFacesPageLifecycle extends FacesPageLifecycle implements JboExceptionHandler {
  public CustomADFFacesPageLifecycle() {
  }
  public void prepareModel(LifecycleContext ctx) {
        /*
         * Force a one-time check to make sure we can find the data control.
         * This allows us to more proactively warn the user if they have
         * not setup their datasource correctly.
         */
      DCErrorHandler deh = ctx.getBindingContext().getErrorHandler();
      if (!(deh instanceof CustomErrorHandler)) {
          deh = new CustomErrorHandler(true);
          ctx.getBindingContext().setErrorHandler(deh);
      }
      super.prepareModel(ctx);
      registerAsExceptionHandler(ctx.getBindingContext());
    }
    
  public void handleException(Exception ex, boolean lastEntryInPiggyback) {
    // do nothing these are already handled
  }
  /**
   * Handle a JboWarning by reporting it to Faces as a FacesMessage
   * 
   * If it happens to be our InformationalMessage subclass, then report
   * that as a faces informational message, otherwise report it as
   * a faces warning.
   * 
   * @param warning
   */
  public void handleWarning(JboWarning warning) {
    Severity sev = (warning instanceof InformationalMessage) ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_WARN;
    FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(sev,warning.getMessage(),null));
  }
  public void finishedProcessingPiggyback(Exception[] exArray) {
    // do nothing these are already handled
  }
  private void registerAsExceptionHandler(BindingContext bctx) {
    Iterator iter = bctx.dataControlsIterator();
    if (iter != null) {
      DCDataControl dc = (DCDataControl)iter.next();
      if (dc instanceof DCJboDataControl) {
        ApplicationModule am = (ApplicationModule)dc.getDataProvider();
        JboExceptionHandler jeh = am.getExceptionHandler();
        if (jeh == null || !(jeh instanceof CustomADFFacesPageLifecycle)) {
          am.setExceptionHandler(this);
        }
      }
    }
  }    
}
