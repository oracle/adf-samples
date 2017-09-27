/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import oracle.adf.controller.faces.lifecycle.ADFPhaseListener;
import oracle.adf.controller.v2.lifecycle.PageLifecycle;

public class CustomADFPhaseListener extends ADFPhaseListener {
  protected PageLifecycle createPageLifecycle() {
    return new CustomFacesPageLifecycle();
  }
}
