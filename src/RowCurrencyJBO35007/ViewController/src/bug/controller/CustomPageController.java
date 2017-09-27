/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package bug.controller;

import javax.faces.context.FacesContext;

import oracle.adf.controller.v2.context.LifecycleContext;
import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;

import oracle.adf.model.ADFmMessageBundle;

import oracle.jbo.JboException;

public class CustomPageController extends PageController {

//    public void prepareModel(LifecycleContext context) {
//        super.prepareModel(context);
//    }
//
//    public void prepareRender(LifecycleContext context) {
//        super.prepareRender(context);
//    }
//
//    public void processUpdateModel(LifecycleContext context) {
//        super.processUpdateModel(context);
//    }
//
//    public boolean shouldAllowModelUpdate(PageLifecycleContext context) {
//        return super.shouldAllowModelUpdate(context);
//    }
//
//    public void validateModelUpdates(LifecycleContext context) {
//        super.validateModelUpdates(context);
//    }

    public void handleError(PageLifecycleContext context, Exception ex) {
        super.handleError(context, ex);
        if (ex instanceof JboException) {
            JboException j = (JboException)ex;
            if (ADFmMessageBundle.EXC_UNEXPECTED_CURRENT_ROW.equals(j.getErrorCode())) {
                context.setForwardPath("problem.jspx");
                context.setRedirect(true);
                FacesContext.getCurrentInstance().renderResponse();
            }
        }
    }
}
