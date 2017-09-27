/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import oracle.adf.controller.faces.lifecycle.ADFPhaseListener;
import oracle.adf.controller.v2.lifecycle.PageLifecycle;

import test.view.util.EL;
public class CustomADFPhaseListener extends ADFPhaseListener {
  public CustomADFPhaseListener() {
  }
  /*
   * Return the application-scoped managed bean that
   * implements our custom ADF page lifecycle subclass
   * of FacesPageLifecycle.
   */
  protected PageLifecycle createPageLifecycle() {
    return (PageLifecycle)EL.get("#{PageLifecycle}");
  }
}
