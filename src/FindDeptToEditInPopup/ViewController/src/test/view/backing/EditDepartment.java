/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import test.view.EL;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.faces.event.ReturnEvent;

public class EditDepartment {
  private DCIteratorBinding getEditDepartmentIterator() {
    return (DCIteratorBinding)EL.get("#{bindings.EditDepartmentIterator}");
  }
  public void onReturnFromSelectDepartmentDialog(ReturnEvent returnEvent) {
    String keyString = (String)returnEvent.getReturnValue();
    if (keyString != null) {
      getEditDepartmentIterator().setCurrentRowWithKey(keyString);
      refreshCurrentPage();
    }
  }
  protected void refreshCurrentPage() {
    FacesContext context = FacesContext.getCurrentInstance();
    String currentView = context.getViewRoot().getViewId();
    ViewHandler vh = context.getApplication().getViewHandler();
    UIViewRoot x = vh.createView(context, currentView);
    x.setViewId(currentView);
    context.setViewRoot(x);
  }  
}
