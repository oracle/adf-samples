/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.jsf;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import toystore.controller.JSFUtils;
import toystore.controller.Utils;
public class RegisterPageActions {
  private BindingContainer bindings;
  public RegisterPageActions() {
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onRegister() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    Utils.getAppUserManager().signIn(JSFUtils.ELAsString("#{bindings.Username.inputValue}"));
    return "success";
  }
}
