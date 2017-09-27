/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.JboException;

import test.model.Contact;

import test.view.backing.util.ExampleAppBackingBeanBase;
import test.view.util.EL;

/**
 * JSF Backing Bean for the ListPage page
 */
public class ListPage extends ExampleAppBackingBeanBase {
  // ------------------------- Custom Logic  ----------------------
  /**
   * Programmic hookpoint that fires just after ADF prepareModel phase
   */
  public void afterPrepareModel() {
    super.afterPrepareModel();
    setModifiedContactAsCurrentIfNeededInIterator(ITERNAME);
  }

  /**
   * Action handler for the (Remove) contact button
   */
  public String onClickRemoveContact() {
    BindingContainer bindings = getBindings();
    Contact c = (Contact)EL.get("#{row.dataProvider}");
    OperationBinding operationBinding = 
      bindings.getOperationBinding("removeContact");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    getIteratorBinding("findContactsByNameIter").executeQuery();
    handleContactDeleted(c);
    return null;
  }
  // -------------------- Properties/Constants --------------------
  private final String ITERNAME = "findContactsByNameIter";
  private BindingContainer bindings;
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
}
