/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import oracle.adf.view.rich.component.rich.layout.RichPanelSplitter;
import oracle.adf.view.rich.render.ClientEvent;


public class SplitterBean
{
  boolean collapsed = false;

  public void setCollapsed(boolean collapsed)
  {
    this.collapsed = collapsed;
  }

  public boolean isCollapsed()
  {
    return collapsed;
  }

  public void handleSplitterEvent(ClientEvent clientEvent)
  {
    RichPanelSplitter splitter = (RichPanelSplitter) clientEvent.getComponent();
    boolean serverSideCollapsedState = splitter.isCollapsed();
    // server side state is not yet in synch with client state
    // so we set the reverse value for collapsed.
    splitter.setCollapsed(!serverSideCollapsedState);
    setCollapsed(!serverSideCollapsedState);
  }
}
