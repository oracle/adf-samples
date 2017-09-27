/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.jsf;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import toystore.controller.JSFUtils;
import toystore.controller.Utils;
public class SigninActions {
  //================== Action Methods ==================
  public String onLogin() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("validSignon");
    Object result = operationBinding.execute();
    if (result.equals(Boolean.TRUE)) {
      AttributeBinding username = (AttributeBinding)bindings.getControlBinding("username");
      Utils.getAppUserManager().signIn((String)username.getInputValue());
      return returnOutcome != null ? returnOutcome : "home";
    } else {
      JSFUtils.addMessage("Invalid username/password.");
      return null;
    }
  }
  //================== Properties ==================
  private String returnOutcome;
  private BindingContainer bindings;
  public void setReturnOutcome(String returnOutcome) {
    this.returnOutcome = returnOutcome;
  }
  public String getReturnOutcome() {
    return returnOutcome;
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
}
