/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import javax.faces.event.PhaseId;

import oracle.adf.controller.ControllerContext;
import oracle.adf.controller.v2.lifecycle.PageLifecycle;
import oracle.adf.controller.v2.lifecycle.PagePhaseEvent;
import oracle.adf.controller.v2.lifecycle.PagePhaseListener;
import oracle.adf.controller.v2.lifecycle.Phases;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

public class CalledTaskflowErrorReporterPagePhaseListener implements PagePhaseListener
{
   public void afterPhase(PagePhaseEvent event)
   {
      //System.out.println("In afterPhase " + event.getPhaseId());
   }
 
 
   public void beforePhase(PagePhaseEvent event)
   {
       if (PhaseId.RENDER_RESPONSE.equals(event.getPhaseId())) {
           ControllerContext context = ControllerContext.getInstance();
           Exception ex = context.getCurrentViewPort().getExceptionData();
           if (ex != null)
           {
               ((DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry()).reportException(ex);
           }
       }
   }
}
