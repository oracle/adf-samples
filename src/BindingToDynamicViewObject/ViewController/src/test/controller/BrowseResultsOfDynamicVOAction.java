/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;
import java.util.List;
import oracle.adf.controller.struts.actions.DataActionContext;
import oracle.jbo.SQLStmtException;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

public class BrowseResultsOfDynamicVOAction extends DynamicViewObjectSupportForwardAction  {
  private static final String QUERY_CHANGED        = "QueryChanged";
  
  /**
   * If we didn't detect a query change, then perform the default action.
   * If we did detect a query change, we'll be changing/re-executing the 
   * query so we don't want to *also* perform a "Next" operation or we'll
   * end up on the 2nd page of the new query's results.
   */
  public void onNextSet(DataActionContext ctx) {
    if (!isQueryTextChanged(ctx)) {
      if (ctx.getEventActionBinding() != null) ctx.getEventActionBinding().doIt();
    }
  }
  /**
   * If we didn't detect a query change, then perform the default action.
   * If we did detect a query change, we'll be changing/re-executing the 
   * query so we don't want to *also* perform a "Next" operation or we'll
   * end up on the 2nd page of the new query's results.
   */
  public void onPreviousSet(DataActionContext ctx) {
    if (!isQueryTextChanged(ctx)) {
      if (ctx.getEventActionBinding() != null) ctx.getEventActionBinding().doIt();
    }
  }  
  
  /**
   * Query setup needs to be done before calling super.prepareModel()
   * so we cannot perform this in an onQuery() event handler method.
   */  
  protected void prepareModel(DataActionContext ctx) throws Exception {

    if (isQueryEventFiringOrQueryChanged(ctx)) {
      ctx.getBindingContainer().setEnableTokenValidation(false);
      String sql = ctx.getHttpServletRequest().getParameter("sql");
      if (sql != null) {
        setupDynamicQueryAndDynamicBindings(ctx,sql);
      }
    }
    super.prepareModel(ctx);
    ctx.getBindingContainer().setEnableTokenValidation(true);
  }
  /**
   * Returns true if "Query" event is firing or if the user changed 
   * the value of the SQL text in the form.
   */
  private boolean isQueryEventFiringOrQueryChanged(DataActionContext ctx) {
    return isQueryEventFiring(ctx) || isQueryTextChanged(ctx);
  }
  /**
   * Returns true if the value of the "_sql" parameter (the old query)
   * is different from the value of the "sql" parameter (the new query)
   */
  private boolean isQueryTextChanged(DataActionContext ctx) {
    String queryChanged = (String)ctx.getHttpServletRequest().getAttribute(QUERY_CHANGED);
    boolean changed = false;
    if (queryChanged != null) {
      changed = queryChanged.equals("Y");
    }
    else {
      String oldSQL = ctx.getHttpServletRequest().getParameter("_sql");
      boolean oldSQLBlank = isNullOrEmpty(oldSQL);
      String newSQL = ctx.getHttpServletRequest().getParameter("sql");
      boolean newSQLBlank = isNullOrEmpty(newSQL);
      changed = (oldSQLBlank && !newSQLBlank)||
                (!oldSQLBlank && newSQLBlank)||
                (newSQL != null && !newSQL.equals(oldSQL));
      ctx.getHttpServletRequest().setAttribute(QUERY_CHANGED,changed?"Y":"N");
    }
    return changed;
  }
  /**
   * Returns true if string is null or empty;
   */
  private boolean isNullOrEmpty(String s) {
    return s == null || s.equals("");
  }
  /**
   * Returns true if a named event "Query" has been triggered by the client.
   */
  private boolean isQueryEventFiring(DataActionContext ctx) {
    List eventList = ctx.getEvents();
    return eventList != null ? eventList.contains("Query") : false;
  }
}
