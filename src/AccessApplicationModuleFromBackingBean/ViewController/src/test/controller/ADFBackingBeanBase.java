/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import java.util.Map;

import javax.faces.context.FacesContext;
import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.DataControl;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
public class ADFBackingBeanBase {
  private static final String BINDINGSATTR   = "bindings";
  private static final String BINDINGCTXATTR = "data";
  protected BindingContainer getBindings() {
    return (BindingContainer)getRequestAttr(BINDINGSATTR);
  }
  protected BindingContext getBindingContext() {
    return (BindingContext)getRequestAttr(BINDINGCTXATTR);
  }
  protected OperationBinding getOperationBinding(String name) {
    return getBindings().getOperationBinding(name);
  }
  protected Object executeOperationBinding(String name, Map params) {
    OperationBinding b = getOperationBinding(name);
    if (b != null) {
      b.getParamsMap().putAll(params);
      return b.execute();
    }
    return null;
  }  
  protected boolean operationBindingHasErrors(String name) {
    return !getOperationBinding(name).getErrors().isEmpty();
  }
  protected Object executeOperationBinding(String name) {
    OperationBinding b = getOperationBinding(name);
    if (b != null) {
      return b.execute();
    }
    return null;
  }
  protected ControlBinding getControlBinding(String name) {
    return getBindings().getControlBinding(name);
  }
  protected AttributeBinding getAttributeBinding(String name) {
    return (AttributeBinding)getControlBinding(name);
  }
  protected ApplicationModule getApplicationModule(String dataControlName) {
    DataControl dc = getDataControl(dataControlName);
    if (dc != null && dc instanceof DCJboDataControl) {
      return (ApplicationModule)dc.getDataProvider();
    }
    return null;
  }
  protected DataControl getDataControl(String name) {
    return getBindingContext().findDataControl(name);
  }
  private Object getRequestAttr(String name) {
    return getFacesContext().getExternalContext().getRequestMap().get(name);
  }
  private FacesContext getFacesContext() {
    return FacesContext.getCurrentInstance();
  }
}
