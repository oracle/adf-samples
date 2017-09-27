/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.adf.controller.struts.actions.DataForwardAction;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCDataControl;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;
import test.model.common.TestModule;

public class DynamicViewObjectSupportForwardAction extends DataForwardAction  {
  private static final String DYNAMIC_VO_ITER_NAME = "DynamicViewObjectIterator";
  private static final String DYNAMIC_VO_RANGE     = "DynamicViewObject";
  private static final String DATACONTROL          = "TestModuleDataControl";
  /**
   * Setup the new query for the dynamic view object, then 
   * recreate the range binding based on the iterator binding
   * bound to that view object so the attribute definitions will
   * be retrieved correctly for the new query.
   */
  protected void setupDynamicQueryAndDynamicBindings(DataActionContext ctx,
                                                   String sql) {
    unbindRowsetIteratorFromDynamicQueryIteratorBinding(ctx);
    changeDynamicViewObjectQuery(ctx,sql);
    recreateRangeBindingForDynamicQuery(ctx);
  }
  private void unbindRowsetIteratorFromDynamicQueryIteratorBinding(DataActionContext ctx) {
    ctx.getBindingContainer().findIteratorBinding(DYNAMIC_VO_ITER_NAME)
       .bindRowSetIterator(null,false);
  }
  /**
   * Call a custom application module method to recreate the view object
   * with a new query.
   */
  private void changeDynamicViewObjectQuery(DataActionContext ctx,
                                            String newSQL) {
    DCDataControl dc = ctx.getBindingContext().findDataControl(DATACONTROL);
    TestModule tm = (TestModule)dc.getDataProvider();
    tm.recreateDynamicViewObjectWithNewQuery(newSQL); 
  }
  /**
   * Recreate the "DynamicViewObject" range binding.
   */
  private void recreateRangeBindingForDynamicQuery(DataActionContext ctx) {
    removeControlBinding(ctx,DYNAMIC_VO_RANGE);
    addDynamicRangeBinding(ctx,DYNAMIC_VO_RANGE, DYNAMIC_VO_ITER_NAME);
  }
  /**
   * Remove a control binding by name.
   */
  private void removeControlBinding(DataActionContext ctx, String name) {
    DCBindingContainer bc = ctx.getBindingContainer();
    DCControlBinding binding = bc.findCtrlBinding(name);
    binding.getDCIteratorBinding().removeValueBinding(binding);
    bc.removeControlBinding(binding);
  }
  /**
   * Add a dynamic range binding (one with null for its attribute list)
   */
  private void addDynamicRangeBinding(DataActionContext ctx,
                                      String bindingName,
                                      String iterName) {
    DCBindingContainer bc = ctx.getBindingContainer();
    JUCtrlRangeBinding dynamicRangeBinding =
        new JUCtrlRangeBinding(null,bc.findIteratorBinding(iterName),null);
    bc.addControlBinding(bindingName,dynamicRangeBinding);
  }
}
