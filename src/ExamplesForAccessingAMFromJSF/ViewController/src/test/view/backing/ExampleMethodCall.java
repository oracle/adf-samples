/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
import test.model.common.AppModule;

public class ExampleMethodCall {
 
    public void callAMMethodAndThenDoSomethingElse() {
        // "#{bindings}"
        DCBindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
        // #{bindings.customApplicationModuleMethodWithResult}"
        JUCtrlActionBinding ob = (JUCtrlActionBinding)bc.getOperationBinding("customApplicationModuleMethodWithResult");
        // #{bindings.customApplicationModuleMethodWithResult.execute}"
        Object result = ob.execute();
        // Here you can do something with the result.
        
        // Access the AM that's been checked out for you by ADF during this request
        AppModule am = (AppModule)ob.getDataControl().getDataProvider();
        
        // If you call the method directly, then exceptions are not handled
        // for you automatically by the ADF Model exception handler...
        // am.customApplicationModuleMethodWithResult("foo",1);
        
        // DO NOT USE THIS CODE
        // ApplicationModule am = Configuration.createRootApplicationModule(amDef,config);
        // --------------------
        
        // Then you can find the view object and rows if needed...
        ViewObject vo = am.findViewObject("...");
        Row r = vo.getCurrentRow();

        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        // If you do a forward here...
        // req.getRequestDispatcher("...").forward();
        FacesContext.getCurrentInstance().responseComplete();
    }
}
