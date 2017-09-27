/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller;
import demo.model.common.ExampleModule;

import oracle.adf.controller.faces.lifecycle.FacesPageLifecycle;
import oracle.adf.controller.v2.context.LifecycleContext;
import demo.view.util.EL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.ViewObject;
/**
 * smuench 01-AUG-2006 Added checks for null iter and VO name
 * 
 */
public class CustomJSFPageLifecycle extends FacesPageLifecycle {
  public CustomJSFPageLifecycle() {
  }
  public void prepareModel(LifecycleContext ctx) {
    ExampleModule em = (ExampleModule)ctx.getBindingContext().findDataControl("ExampleModuleDataControl").getDataProvider();
    /*
     * Call the Application Module method that enables/disables detail
     * coordination based on the view instance names referenced by the
     * iterator bindings in the current binding container.
     */
    em.enableDetailsForViewInstances(viewInstancesForCurrentBindingContainer(ctx));
    /*
     * This code adds a Map named "implicitlyExecuted" to the request containing
     * the names of each iterator binding and a boolean flag indicating whether
     * it was in the "executed" state before the prepareModel phase. View objects
     * that are not executed will get implicitly executed the first time
     * during the subsequent prepareModel phase. An EL expression in a page
     * definition can leverage this information to avoid forcing an unnecessary
     * re-ExecuteQuery in the case that the executeQuery will have already been
     * done implicitly.
     */
    DCBindingContainer bc = (DCBindingContainer)ctx.getBindingContainer();
    List iterBindings = bc.getIterBindingList();
    Map<String,Boolean> implicitlyExecuted = null;
    for (DCIteratorBinding ib : (List<DCIteratorBinding>)bc.getIterBindingList()) {
      if (implicitlyExecuted == null) {
        implicitlyExecuted = new HashMap<String,Boolean>(iterBindings.size());
      }
      String name = ib.getName();
      ViewObject iterVO = ib.getViewObject();
      if (name != null && iterVO != null) {
        implicitlyExecuted.put(name,!iterVO.isExecuted());
      }
    }
    if (implicitlyExecuted != null) {
      FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("implicitlyExecuted",implicitlyExecuted);
    }
    super.prepareModel(ctx);
  }
  private String[] viewInstancesForCurrentBindingContainer(LifecycleContext ctx) {
    DCBindingContainer bc = (DCBindingContainer)ctx.getBindingContainer();
    List<String> vos = new ArrayList<String>();
    for (DCIteratorBinding iter: (List<DCIteratorBinding>)bc.getIterBindingList()) {
      String voName = iter.getVOName();
      if (voName != null) {
        vos.add(voName);
      }
    }
    String[] ret = new String[vos.size()];
    return (String[])vos.toArray(ret);
  }
}
