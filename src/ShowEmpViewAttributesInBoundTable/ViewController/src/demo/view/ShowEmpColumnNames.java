/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
public class ShowEmpColumnNames {
  private BindingContainer bindings;
  public ShowEmpColumnNames() {
  }
  public BindingContainer getBindings() {
    return this.bindings;
  }
  public void setBindings(BindingContainer bindings) {
    this.bindings = bindings;
  }
  public String onRetrieveMapOfSelectedAttributeNames() {
    BindingContainer bindings = getBindings();
    OperationBinding operationBinding = 
    bindings.getOperationBinding("retrieveMapOfSelectedAttributeNames");
    Map result = (Map)operationBinding.execute();
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedAttributes",result);
    return "showdata";
  }
}
