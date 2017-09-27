/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.model.binding.DCIteratorBinding;

public class MyDataForwardAction extends DataForwardAction  {
  protected void executeQueryForIter(DataActionContext ctx, String iterName) {
    DCIteratorBinding iter = ctx.getBindingContainer().findIteratorBinding(iterName);
    if (iter != null) {
      /*
       * Calling getRowSetIterator reactivates the iterator that
       * might have been released in immediate mode. This is a
       * workaround for Bug# 4443916 where invoking executeQuery()
       * in prepareModel() for an iterator in immediate mode
       * gets ignored if called before super.prepareModel()
       */
      iter.getRowSetIterator(); 
      iter.executeQuery();    
    }
  }
}
