/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.jsf;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import toystore.controller.JSFUtils;
public class ConfirmShippingInfoActions {
  private BindingContainer bindings;
  public ConfirmShippingInfoActions() {
  }  
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onFinalizeOrder() {
    /*
     * TODO: Workaround for Bug# 4719021 where this is invoked twice
     */
    if (JSFUtils.getRequestAttribute("newOrderId") == null) {
      BindingContainer bindings = getBindings();
      OperationBinding operationBinding = bindings.getOperationBinding("finalizeOrder");
      String result = (String)operationBinding.execute();
      JSFUtils.setRequestAttribute("newOrderId",result);
      return "thankyou";
    }
    return null;
  }
}
