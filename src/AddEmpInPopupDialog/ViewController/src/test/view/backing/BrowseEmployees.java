/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.faces.context.AdfFacesContext;
import oracle.adf.view.faces.event.ReturnEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import test.view.util.EL;
public class BrowseEmployees {
  private BindingContainer bindings;
  public void onReturnFromDialog(ReturnEvent returnEvent) {
    if (returnEvent.getReturnValue() != null) {
      /* My attempts to declaratively set this requestScope
       * property using an <af:setActionListener> on the
       * (Save New Employee) button in the AddEmployee.jspx
       * page did not work -- not sure why not! -- so I'm
       * setting it here instead. This attribute is used as
       * a flag to trigger the declaratively requerying
       * of the Employees view object instance being
       * displayed in the BrowseEmployees.jspx page.
       */
      EL.set("#{requestScope.addedNewEmployee}",true);
      DCIteratorBinding iter = (DCIteratorBinding)EL.get("#{bindings.ROEmpsViewByHiredateIterator}");
      iter.executeQuery();
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
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onSaveNewEmployee() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = bindings.getOperationBinding("Commit");
    Object result = operationBinding.execute();
    if (!operationBinding.getErrors().isEmpty()) {
      return null;
    }
    /*
     * Conditionally return from the dialog if no
     * errors were found in the execute() call on the
     * "Commit" binding above.
     */
    AdfFacesContext.getCurrentInstance().returnFromDialog(EL.get("#{bindings.Empno.inputValue}"),null);
    return null;
  }
}
