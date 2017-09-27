/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;

import java.util.Map;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControl;
import oracle.adf.model.bc4j.DataControlFactoryImpl;

import org.w3c.dom.Node;

public class MyCustomDataControlFactory extends DataControlFactoryImpl {
    public MyCustomDataControlFactory() {
    }
    protected String getDataControlClassName() {
      return MyCustomADFBCDataControl.class.getName();
    }

    public DataControl createSession(BindingContext ctx, String sName, 
                                     Map appParams, Map parameters) {
        return super.createSession(ctx, sName, appParams, parameters);
    }

    public DataControl createSession(BindingContext ctx, Node node, 
                                     Map appParams) {
        return super.createSession(ctx, node, appParams);
    }
}
