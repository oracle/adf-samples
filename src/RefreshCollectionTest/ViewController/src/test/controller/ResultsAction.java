/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.generic.DCRowSetIteratorImpl;

public class ResultsAction extends DataForwardAction  {
  protected void invokeCustomMethod(DataActionContext ctx) {
    super.invokeCustomMethod(ctx);
    DCIteratorBinding b = ctx.getBindingContainer().findIteratorBinding("peopleIterator");
    ((DCRowSetIteratorImpl)b.getRowSetIterator()).rebuildIteratorUpto(-1);
    b.executeQuery();
  }
}
