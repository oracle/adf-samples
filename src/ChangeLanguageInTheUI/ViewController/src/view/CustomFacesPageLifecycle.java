/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import java.util.List;
import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import oracle.adf.controller.faces.lifecycle.FacesPageLifecycle;
import oracle.adf.controller.v2.context.LifecycleContext;

import oracle.binding.ControlBinding;

import oracle.jbo.uicli.binding.JUCtrlListBinding;

import view.utils.ELHelper;

public class CustomFacesPageLifecycle extends FacesPageLifecycle {
  /*
   * Adjust the user's preferred locale before the JSF render phase
   * and before the ADFM/ADFC prepareRender() phase.
   */
  public void prepareRender(LifecycleContext lfContext) {
    App app = (App)ELHelper.get("#{App}");
    Locale preferredLocale = app.getPreferredLocale();
    UIViewRoot uiViewRoot = FacesContext.getCurrentInstance().getViewRoot();
    boolean changedLocale = false;
    if (preferredLocale == null) {
        app.setPreferredLocale(uiViewRoot.getLocale());
    } else {
        if (preferredLocale != uiViewRoot.getLocale()) {
            uiViewRoot.setLocale(preferredLocale);
            changedLocale = true;
        }
    }
    if (changedLocale) {
      for (ControlBinding cb : (List<ControlBinding>)lfContext.getBindingContainer().getControlBindings()) {
        if (cb instanceof JUCtrlListBinding) {
          JUCtrlListBinding lb = (JUCtrlListBinding)cb;
          // If the list has a translatable null value
          if (lb.hasNullValue()) {
            // Force the list of values for the list binding to be recalculated
            // so that the null value text will be in the new locale.
            ((JUCtrlListBinding)cb).setValueList(null);
          }
        }
      }
    }
    super.prepareRender(lfContext);
  }
}
