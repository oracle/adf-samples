/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.backing;
import demo.view.util.EL;
import demo.view.util.OnPageLoadBackingBeanBase;

import oracle.adf.view.faces.component.core.layout.CorePanelBox;
public class SearchPage extends OnPageLoadBackingBeanBase {
  private CorePanelBox panelBoxComponent;

  /**
   * Before rendering the page, set the value of the
   * field bindings to the value of the processScope
   * property that holds its "sticky" value.
   *
   * Since these field bindings are bound to page def
   * variables, setting their value does not
   */
  public void onPagePreRender() {
    System.out.println("bindings.EmpName="+EL.get("#{bindings.EmpName.inputValue}"));
    System.out.println("bindings.HighSal"+EL.get("#{bindings.HighSal.inputValue}"));
    System.out.println("bindings.LowSal"+EL.get("#{bindings.LowSal.inputValue}"));
    EL.set("#{bindings.EmpName.inputValue}",
           "#{processScope.EmpName}");
    EL.set("#{bindings.HighSal.inputValue}",
           "#{processScope.HighSal}");
    EL.set("#{bindings.LowSal.inputValue}",
           "#{processScope.LowSal}");
    System.out.println("bindings.EmpName="+EL.get("#{bindings.EmpName.inputValue}"));
    System.out.println("bindings.HighSal"+EL.get("#{bindings.HighSal.inputValue}"));
    System.out.println("bindings.LowSal"+EL.get("#{bindings.LowSal.inputValue}"));           
  }
  public void setPanelBoxComponent(CorePanelBox panelBoxComponent) {
    this.panelBoxComponent = panelBoxComponent;
  }
  public CorePanelBox getPanelBoxComponent() {
    return panelBoxComponent;
  }
}
