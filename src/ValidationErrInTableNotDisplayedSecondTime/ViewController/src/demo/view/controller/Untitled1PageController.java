/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.controller;
import demo.model.fwkext.CustomViewRowImpl;
import java.util.List;
import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.jbo.Row;
public class Untitled1PageController extends PageController {
  public void validateModelUpdates(LifecycleContext context) {
    super.validateModelUpdates(context);
    List errors = ((DCBindingContainer)context.getBindingContainer()).getExceptionsList();
    if (errors == null || errors.size() == 0) {
      DCBindingContainer bindings = (DCBindingContainer)context.getBindingContainer();
      Row[] rows = bindings.findIteratorBinding("EmpViewIterator").getRowSetIterator().getAllRowsInRange();
      for (Row row : rows) {
        // If we're tracking a per-row "Valid" dynamic attribute, use it to
        // optimize to revalidate only rows that are not valid.
        if (row instanceof CustomViewRowImpl) {
          if (Boolean.FALSE.equals(row.getAttribute("Valid"))) {
            row.validate();
          }
        }
        else {
          row.validate();
        }
      }
    }
  }
}
