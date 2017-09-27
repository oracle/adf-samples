/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.client;
import java.util.HashMap;
import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFactory;
import oracle.adf.model.binding.DCErrorHandlerImpl;
import oracle.jbo.common.DefLocaleContext;
import oracle.jbo.uicli.mom.JUMetaObjectManager;
public class BindingContextFactory  {
  public static BindingContext create(String bindingFileName) {
    BindingContext ctx = new BindingContext();
    ctx.setErrorHandler(new DCErrorHandlerImpl(true));
    ctx.setLocaleContext(new DefLocaleContext());
    HashMap map = new HashMap(1);
    map.put(DataControlFactory.APP_PARAMS_BINDING_CONTEXT, ctx);
    JUMetaObjectManager.loadCpx(bindingFileName, map);
    return ctx;
  }
}
