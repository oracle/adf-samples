/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;
import oracle.adf.view.faces.component.core.input.CoreInputText;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
public class TableAndFormPage {
  private BindingContainer bindings;
  private CoreInputText deptnoField;
  private CoreInputText dnameField;
  private CoreInputText locField;
  public TableAndFormPage() {
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onCreateButtonClicked() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Create");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    resetFormFieldValues();
    return null;
  }
  private void resetFormFieldValues() {
    getDeptnoField().resetValue();
    getDnameField().resetValue();
    getLocField().resetValue();
  }
  public void setDeptnoField(CoreInputText deptnoField) {
    this.deptnoField = deptnoField;
  }
  public CoreInputText getDeptnoField() {
    return deptnoField;
  }
  public void setDnameField(CoreInputText dnameField) {
    this.dnameField = dnameField;
  }
  public CoreInputText getDnameField() {
    return dnameField;
  }
  public void setLocField(CoreInputText locField) {
    this.locField = locField;
  }
  public CoreInputText getLocField() {
    return locField;
  }
  public String onDeleteButtonClicked() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Delete");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    resetFormFieldValues();
    return null;
  }
  public String onRollbackButtonClicked() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = 
      bindings.getOperationBinding("Rollback");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    resetFormFieldValues();
    return null;
  }
}
