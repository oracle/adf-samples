/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.jbo.NoDefException;
import oracle.jbo.Variable;
import oracle.jbo.VariableValueManager;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;

public class CustomViewObjectImpl extends ViewObjectImpl {
  protected void bindParametersForCollection(QueryCollection qc, 
                                             Object[] params, 
                                             PreparedStatement stmt) throws SQLException {
    params = uppercaseAnyParametersThatNeedUppercasing(params);
    super.bindParametersForCollection(qc, params, stmt);
  }
  /*
   * Scan through any bind parameters for the collection. If the view object
   * is using Oracle Named binding style, and a given parameter is an 
   * Object[], then it will be an Object[2] with {"VarName",varValue}. Since
   * uppercasing only makes sense for Strings, we only perform the uppercasing
   * of the parameter value if the bind variable exists for a the parameter
   * name and is instanceof String (and is non-null).
   */
  private Object[] uppercaseAnyParametersThatNeedUppercasing(Object[] params) {
    if (params != null && params.length > 0 && SQLBuilder.BINDING_STYLE_ORACLE_NAME == getBindingStyle()) {
      for (int z = 0; z < params.length; z++) {
        if (params[z] instanceof Object[]) {
          String paramName = (String)((Object[])params[z])[0];
          try {
            Variable v = ensureVariableManager().findVariable(paramName);
            if (v.getProperty("Uppercase") != null && String.class.isAssignableFrom(v.getJavaType())) {
              String value = (String)((Object[])params[z])[1];
              if (value != null) {
                ((Object[])params[z])[1] = value.toUpperCase(getRootApplicationModule().getSession().getLocale());
              }
            }
          }
          catch (NoDefException noDef) {
            // ignore. No bind variable by this name.
          }
        }
      }      
    }
    return params;
  }
}
