/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.struts.actions.DataForwardAction;

import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;


public class DataPage1Action extends DataForwardAction {
    protected void invokeCustomMethod(DataActionContext ctx) {
        ApplicationModule metaModule = (ApplicationModule) ctx.getBindingContext()
                                                              .findDataControl("SystemDataControl")
                                                              .getDataProvider();
        ViewObject vo = metaModule.findViewObject("CurrentDBDate");
        vo.executeQuery();
        System.out.println(vo.first().getAttribute("CurrentDate"));
        super.invokeCustomMethod(ctx);
    }
}
