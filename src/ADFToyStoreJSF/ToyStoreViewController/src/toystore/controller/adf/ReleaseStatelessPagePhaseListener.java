/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.adf;
import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.model.binding.DCDataControl;
public class ReleaseStatelessPagePhaseListener implements PagePhaseListener {
  public void afterPhase(PagePhaseEvent event) {
    if (event.getPhaseId() == Lifecycle.PREPARE_RENDER_ID) {
      getDataControl("ToyStoreService", event).resetState();
    }
  }
  public void beforePhase(PagePhaseEvent event) {
  }
  private DCDataControl getDataControl(String name, PagePhaseEvent event) {
    return event.getLifecycleContext().getBindingContext().findDataControl(name);
  }
}
