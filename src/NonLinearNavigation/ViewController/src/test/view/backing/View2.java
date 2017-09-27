/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContainer;
import oracle.adf.model.BindingContext;

import oracle.adf.model.ControlBinding;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.jbo.uicli.binding.JUCtrlActionBinding;

import test.view.util.EL;

public class View2
{
   public View2()
   {
   }

   public void onChangeCurrentMasterRowNavigationListener(ValueChangeEvent valueChangeEvent)
   {
      EL.setValueChangeEventComponentToNewValue(valueChangeEvent);
      executeBinding("RequeryDetail");
   }

   private void executeBinding(String actionBindingName)
   {
      BindingContext bindingContext = BindingContext.getCurrent();
      DCBindingContainer bindingContainer = (DCBindingContainer)bindingContext.getCurrentBindingsEntry();
      JUCtrlActionBinding actionBinding = (JUCtrlActionBinding)bindingContainer.findCtrlBinding(actionBindingName);
      actionBinding.execute();
   }
}
