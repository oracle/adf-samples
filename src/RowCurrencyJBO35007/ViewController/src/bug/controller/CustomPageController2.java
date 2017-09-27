/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package bug.controller;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.controller.v2.context.PageLifecycleContext;
import oracle.adf.controller.v2.lifecycle.PageController;
import oracle.adf.model.ADFmMessageBundle;

import oracle.jbo.JboException;

public class CustomPageController2 extends PageController {

  

    public void handleError(PageLifecycleContext context, Exception ex) {
        super.handleError(context, ex);
        if (ex instanceof JboException) {
            JboException j = (JboException)ex;
            if (ADFmMessageBundle.EXC_UNEXPECTED_CURRENT_ROW.equals(j.getErrorCode())) {
                ((HttpServletRequest)context.getEnvironment().getRequest()).setAttribute("TriedToEditWrongCurrentRow",Boolean.TRUE);
                FacesContext.getCurrentInstance().renderResponse();
            }
        }
    }
}
