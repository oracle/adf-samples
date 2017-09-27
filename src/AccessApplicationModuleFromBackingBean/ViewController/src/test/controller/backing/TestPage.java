/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller.backing;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.JboException;

import test.controller.ADFBackingBeanBase;
public class TestPage extends ADFBackingBeanBase {
  public String onNext() {
    Object result = executeOperationBinding("Next");
    return operationBindingHasErrors("Next") ? null : "SomewhereElse";
  }
}
