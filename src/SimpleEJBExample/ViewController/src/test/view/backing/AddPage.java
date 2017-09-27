/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import test.model.Contact;

import test.view.backing.util.ExampleAppBackingBeanBase;
import test.view.util.EL;
import test.view.util.OnPageLoadBackingBeanBase;
import oracle.adf.controller.v2.context.PageLifecycleContext;
/**
 * JSF Backing Bean for the AddPage page
 */
public class AddPage extends ExampleAppBackingBeanBase {
  // ------------------------- Custom Logic  ----------------------
  /**
   * Action Method for (OK) button to add a contact
   */
  public String onClickOK() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding =  bindings.getOperationBinding("addContact");
    Contact newContact = (Contact)operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    handleContactModified(newContact);
    return "BackToList";
  }
  // -------------------- Properties/Constants --------------------
   private final String ITERNAME = "ContactIter";
   private BindingContainer bindings;
   public BindingContainer getBindings() {
     return this.bindings;
   }
   public void setBindings(BindingContainer bindings) {
     this.bindings = bindings;
   }
  
}
