/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.backing;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.binding.OperationBinding;
/**
 * Assumes that the current binding container has a "Rollback" action binding.
 */
public class RollbackHelperBase {
  public void executeRollbackActionAfterDisablingExecuteOnRollback() {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding("#{bindings}");
    DCBindingContainer bindings = (DCBindingContainer)vb.getValue(fc);
    if (bindings != null) {
      bindings.setExecuteOnRollback(false);
      OperationBinding ob = bindings.getOperationBinding("Rollback");
      if (ob != null) {
        ob.execute();
      } else {
        throw new RuntimeException("Binding container has no 'Rollback' action binding");
      }
    }
  }
}
