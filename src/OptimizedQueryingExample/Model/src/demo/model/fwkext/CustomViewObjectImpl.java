/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.fwkext;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.NullValue;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {

  private static final String FWK_BIND_PREFIX = "Bind_";

  protected void bindParametersForCollection(QueryCollection qc, 
                                             Object[] params, 
                                             PreparedStatement stmt) throws SQLException {
    log(params, qc == null);
    super.bindParametersForCollection(qc, params, stmt);
  }
  /*
   * This overridden framework method suppresses the execution of a
   * database query on this view object if framework-supplied bind variables
   * of the form "Bind_*" have null values.
   */
  protected void executeQueryForCollection(Object qc, Object[] params, 
                                           int noUserParams) {
    /*
     * For this example, allow the custom application module environment
     * property named "enable.optimizations" to control whether the
     * optimizations are enabled or not for comparison.
     */
    if (!"true".equals(getRootApplicationModule().getSession().getEnvironment().get("enable.optimizations"))) {
      super.executeQueryForCollection(qc, params, noUserParams);
    }
    else {
      boolean performNullSkip = false;
      if (params != null && getBindingStyle() == SQLBuilder.BINDING_STYLE_ORACLE_NAME) {
        boolean allFwkBindsNull = true;
        boolean hasSomeFwkBinds = false;
        for (Object param : params) {
          Object[] nameValue = (Object[])param;
          String varName = (String)nameValue[0];
          Object varValue = nameValue[1];
          if (varName.startsWith(FWK_BIND_PREFIX)) {
            hasSomeFwkBinds = true;
            if (varValue != null && !(varValue instanceof NullValue)) {
              allFwkBindsNull = false;
              break;
            }
          }
        }
        if (hasSomeFwkBinds && allFwkBindsNull) {
          performNullSkip = true;
        }
      }
      int previousMaxFetchSize = -1;
      if (performNullSkip) {
        previousMaxFetchSize = getMaxFetchSize();
        setMaxFetchSize(0);
      }
      try {
        super.executeQueryForCollection(qc, params, noUserParams);
      } finally {
        if (performNullSkip) {
          setMaxFetchSize(previousMaxFetchSize);
        }
      }
    }
  }  
  private void log(Object[] params, boolean countQuery) {
    StringBuilder sb = new StringBuilder("[");
    if (params != null) {
      int pos = 0;
      for (Object o: params) {
        String varName = null;
        Object varValue = null;
        if (getBindingStyle() == SQLBuilder.BINDING_STYLE_ORACLE_NAME) {
          Object[] nameValue = (Object[])o;
          varName = (String)nameValue[0];
          varValue = nameValue[1];
        } else {
          varName = "" + pos;
          varValue = o;
        }
        if (pos > 0)
          sb.append(",");
        sb.append(varName).append("=").append(varValue);
        pos++;
      }
    }
    sb.append("]");
    System.out.println("###> bindParamsForCollection, '" + 
                       getName() + "' "+
                       (countQuery?"[COUNT] ":"")
                       + sb.toString());
  }
}
