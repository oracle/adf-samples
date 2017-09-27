/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.util;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;

public class ConditionalValidationPageController extends PageController {
  public void validateModelUpdates(LifecycleContext context) {
    /*
     * If the row type isn't changing then validate the binding container
     */
    if (EL.test("#{empty requestScope.RowTypeChanged}")) {
      super.validateModelUpdates(context);
    }
  }
}
