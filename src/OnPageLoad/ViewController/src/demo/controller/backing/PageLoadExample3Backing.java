/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller.backing;
import oracle.adf.controller.faces.context.FacesPageLifecycleContext;
import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.adf.controller.v2.context.PageLifecycleContext;
public class PageLoadExample3Backing extends PageController {
  private BindingContainer bindings;
  public PageLoadExample3Backing() {
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String commandButton_action() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Next");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    return null;
  }
  public void onNext(PageLifecycleContext context) {
    context.getEventActionBinding().invoke();
    System.out.println("#### Next ###");
  }
  public void prepareModel(LifecycleContext context) {
    super.prepareModel(context);
  }
}
