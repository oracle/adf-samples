/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import test.model.Contact;

import test.view.backing.util.ExampleAppBackingBeanBase;

/**
 * JSF Backing Bean for the EditPage page
 */
public class EditPage extends ExampleAppBackingBeanBase {
  // ------------------------- Custom Logic  ----------------------  
 /**
  * Action Method for (OK) button to add a contact
  */
  public String onClickOK() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("updateContact");
    Contact editedContact = (Contact)operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    handleContactModified(editedContact);
    return "BackToList";
  }
  // -------------------- Properties/Constants --------------------
   private BindingContainer bindings;
   public BindingContainer getBindings() {
     return this.bindings;
   }
   public void setBindings(BindingContainer bindings) {
     this.bindings = bindings;
   }  
}
