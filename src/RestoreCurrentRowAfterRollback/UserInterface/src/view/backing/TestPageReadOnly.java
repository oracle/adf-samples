/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.backing;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
public class TestPageReadOnly extends RollbackHelperBase {
  private BindingContainer bindings;
  public TestPageReadOnly() {
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onRollback() {
    executeRollbackActionAfterDisablingExecuteOnRollback();
    return null; // stay on the same page.
  }
}
