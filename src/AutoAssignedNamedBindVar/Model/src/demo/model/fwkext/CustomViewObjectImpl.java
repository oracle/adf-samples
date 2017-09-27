/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.fwkext;
import demo.model.util.VOAttributeReference;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import oracle.jbo.Variable;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {
  private static final String VOATTREXPR_PROPNAME = "VOAttributeExpression";  
  protected void bindParametersForCollection(QueryCollection qc, 
                                             Object[] params, 
                                             PreparedStatement stmt) throws SQLException {
    if (params != null && 
        getBindingStyle() == SQLBuilder.BINDING_STYLE_ORACLE_NAME) {
      Map<String, String> dynamicBindVariables = getDynamicBindVariables();
      if (dynamicBindVariables != null) {
        for (int z = 0; z < params.length; z++) {
          Object[] nameValue = (Object[])params[z];
          String curVarName = (String)nameValue[0];
          if (dynamicBindVariables.containsKey(curVarName)) {
            String voAttrExpr = dynamicBindVariables.get(curVarName);
            nameValue[1] = dynamicVariableValue(voAttrExpr);
          }
        }
      }
    }
    super.bindParametersForCollection(qc, params, stmt);
  }
  private Map<String, String> dynamicBindVariables = null;
  private boolean resolvedDynamicBindVariables = false;
  private Map<String, String> getDynamicBindVariables() {
    if (!resolvedDynamicBindVariables && dynamicBindVariables == null) {
      ensureVariableManager();
      Variable[] namedBindVariables = 
        getVariableManager().getVariablesOfKind(Variable.VAR_KIND_WHERE_CLAUSE_PARAM);
      if (namedBindVariables != null && namedBindVariables.length > 0) {
        for (Variable v: namedBindVariables) {
          String varPropVal = (String)v.getProperty(VOATTREXPR_PROPNAME);
          /*
           * If the property is not set on the bind variable itself, 
           * look to see if the appmodule has a property named
           * <VOInstanceName>_<BindParamName>_VOAttributeExpression
           * and use that as the VO attribute expression as a fallback.
           */
          if (varPropVal == null) {
            String voInstanceName = getName();
            String amPropertyName = voInstanceName+"_"+v.getName()+"_"+VOATTREXPR_PROPNAME;
            varPropVal = (String)getApplicationModule().getProperty(amPropertyName);
          }
          if (varPropVal != null) {
            if (dynamicBindVariables == null) {
              dynamicBindVariables = new HashMap<String, String>();
            }
            dynamicBindVariables.put(v.getName(), varPropVal);
          }
        }
      }
      resolvedDynamicBindVariables = true;
    }
    return dynamicBindVariables;
  }
  private Object dynamicVariableValue(String expr) {
    VOAttributeReference v = 
      new VOAttributeReference(getDBTransaction(), expr);
    return v.getValue(getDBTransaction());
  }  
}
