/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.faces.component.core.data.CoreTable;
import oracle.adf.view.faces.component.core.layout.CorePanelGroup;
import oracle.adf.view.faces.component.core.nav.CoreCommandButton;
import oracle.adf.view.faces.context.AdfFacesContext;
public class PageWithDataAndGraph {
  private CorePanelGroup graphPanelGroup;
  private CoreCommandButton rollbackButton;
  private CoreTable table;
  public PageWithDataAndGraph() {
  }
  /*
   * Cause the panelGroup containing the Graph and the rollback button
   * to to partial page update whenever the Sal or Comm values change. 
   * This method is "wired up" as the ValueChangeListener for the
   * Sal and Comm inputText controls in the table, and both of those fields
   * has autoPost="true" on. We have to do this programmatically because
   * when components appear multiple times in a table, their "id" for
   * the "PartialTriggers" is something that is automatically name-mangled
   * to be unique in the page, so we can't really say which id should trigger
   * the Partial Page Redraw.
   */
  public void onChangeSalOrCommValues(ValueChangeEvent valueChangeEvent) {
    AdfFacesContext.getCurrentInstance().addPartialTarget(getGraphPanelGroup());
    AdfFacesContext.getCurrentInstance().addPartialTarget(getRollbackButton());
    AdfFacesContext.getCurrentInstance().addPartialTarget(getTable());    
  }
  public void setGraphPanelGroup(CorePanelGroup graphPanelGroup) {
    this.graphPanelGroup = graphPanelGroup;
  }
  public CorePanelGroup getGraphPanelGroup() {
    return graphPanelGroup;
  }
  public void setRollbackButton(CoreCommandButton rollbackButton) {
    this.rollbackButton = rollbackButton;
  }
  public CoreCommandButton getRollbackButton() {
    return rollbackButton;
  }
  public void setTable(CoreTable table) {
    this.table = table;
  }
  public CoreTable getTable() {
    return table;
  }
}
