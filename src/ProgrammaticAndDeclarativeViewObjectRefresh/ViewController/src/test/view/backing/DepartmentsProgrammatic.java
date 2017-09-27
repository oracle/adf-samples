/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import test.view.util.OnPageLoadBackingBeanBase;
import test.view.util.EL;

public class DepartmentsProgrammatic extends OnPageLoadBackingBeanBase {
  /*
   * See section 10.5.4.3 "Using Custom ADF Page Lifecycle to Invoke an onPageLoad Backing Bean Method"
   * in the ADF Developer's Guide for Forms/4GL Developers for an explanation
   * of how to setup the OnPageLoadBackingBeanBase class and configure the
   * ControllerClass of your page definition to make this work.
   * 
   * We need to use the afterPrepareModel() method of the backing bean base
   * instead of onPageLoad since onPageLoad fires before prepareModel and
   * before prepareModel the iterator bindings have not been bound yet
   * to trying to perform operations like executeQuery() on them before
   * the prepareModel will be treated as no-ops.
   */
  public void afterPrepareModel() {
    super.afterPrepareModel();
    if (EL.test("#{adfFacesContext.postback == false and not empty requestScope.refreshFlag}")) {
      getIteratorBinding("DepartmentsIterator").executeQuery();
    }
  }
}
