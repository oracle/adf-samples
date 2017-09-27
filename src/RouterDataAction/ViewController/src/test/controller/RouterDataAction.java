/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import oracle.adf.controller.struts.actions.DataAction;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.uicli.binding.JUCtrlActionBinding;
/**
 * This class illustrates a generic extension of DataAction
 * which automatically sets the name of the page forward to
 * "follow" to the next step in the page flow based on the
 * (Assumed String!) result of invoking the declarative
 * method associated with this data action.
 */
public class RouterDataAction extends DataAction  {
  /**
   * Overidden framework method to set the forward name
   * to be the String result of the declaratively invoked
   * method for this data action.
   * @param ctx Data Action Context
   */
  protected void invokeCustomMethod(DataActionContext ctx) {
    super.invokeCustomMethod(ctx);
    /*
     * After invoking the default, set the action forward name
     * to be the result of the above method invocation.
     */
    ctx.setActionForward((String)methodResult(ctx));
  }
  /**
   * Return the result of invoking the method binding whose
   * name matches the name of the method that's been declaratively
   * associated to this data action.

   * @param ctx Data Action Context
   * @return Result of declaratively invoked method for this data action
   */
  private Object methodResult(DataActionContext ctx) {
    String methodBindingName = methodNameForThisDataAction(ctx);
    return ((JUCtrlActionBinding)ctx.getBindingContainer().findCtrlBinding(methodBindingName)).getResult();
  }
  /**
   * Return the name of the method that is declaratively associated
   * with this data action through its "methodName" property on 
   * the Struts action mapping.

   * @param ctx DataActionContext
   * @return method name with data control name stripped off
   */
  private String methodNameForThisDataAction(DataActionContext ctx) {
    /* This will have the format datacontrolname.methodname */
    String methodName = ctx.getActionMapping().getMethodName();
    int indexOfDot = methodName.indexOf('.');
    return indexOfDot >= 0 ? methodName.substring(indexOfDot+1) : methodName;
  }
}
