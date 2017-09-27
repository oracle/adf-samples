/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.adf;
import java.io.IOException;

import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.controller.v2.lifecycle.Lifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;

import toystore.controller.Utils;
public class LoginRequiredPagePhaseListener implements PagePhaseListener {
  public void beforePhase(PagePhaseEvent event) {
    if (event.getPhaseId()== Lifecycle.PREPARE_MODEL_ID) {
      if (!Utils.getAppUserManager().isSignedOn()) {
        Utils.redirectToSignin();
      }    
    }
  }
  public void afterPhase(PagePhaseEvent event) {
  }
}
