/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.util;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;

public class ConditionalValidationPageController extends PageController {
  public void validateModelUpdates(LifecycleContext context) {
    /*
     * If the region isn't changing and the country isn't changing then validate the binding container
     */
    if (EL.test("#{empty requestScope.RegionChanged && empty requestScope.CountryChanged}")) {
      super.validateModelUpdates(context);
    }
  }
  /*
   * Just before rendering the before, if the requestScope attribute "RegionChanged" is set
   * then we know we're changing the region and we should set the value of the CountryId binding
   * to null to force the user to pick something from the country list for the newly-selected
   * Region.
   */
  public void prepareRender(LifecycleContext context) {
    if (EL.test("#{not empty requestScope.RegionChanged}")) {
      EL.set("#{bindings.CountryId.inputValue}",null);
    }  
    super.prepareRender(context);
  }
}
