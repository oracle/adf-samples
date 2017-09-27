/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jbo.ViewCriteria;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
public class TracingViewObjectImpl extends ViewObjectImpl {
  // Log Query Statement and Bind Parameters
  @Override
  protected void bindParametersForCollection(QueryCollection qc,
                                             Object[] params,
                                             PreparedStatement stmt)
  throws SQLException {
    logQueryStatementAndBindParameters(qc, params);
    super.bindParametersForCollection(qc, params, stmt);
  }
  private void logQueryStatementAndBindParameters(QueryCollection qc,
                                                  Object[] params) {
    
    String vrsiName = null;
    if (qc != null) {
      ViewRowSetImpl vrsi = qc.getRowSetImpl();
      vrsiName = vrsi.isDefaultRS() ? "<Default>" : vrsi.getName();
    }
    String voName = getName();
    String voDefName = getDefFullName();
    if (qc != null) {
      System.out.println("----[Exec query for VO=" + voName + ", RS=" +
                         vrsiName + "]----");
    } else {
      System.out.println("----[Exec COUNT query for VO=" + voName + "]----");
    }
    System.out.println("VODef =" + voDefName);
    ViewCriteria[] appliedCriterias = getApplyViewCriterias(ViewCriteria.CRITERIA_MODE_QUERY);
    if (appliedCriterias != null && appliedCriterias.length > 0) {
      StringBuilder sb = new StringBuilder();
      int criteriaCount = 0;
      for (ViewCriteria vc : appliedCriterias) {
        if (criteriaCount++ > 0) {
          sb.append(",");
        }
        sb.append(vc.getName());
      }
      System.out.println("Applied Database VCs = (" + sb + ")");
    }
    appliedCriterias = getApplyViewCriterias(ViewCriteria.CRITERIA_MODE_CACHE);
    if (appliedCriterias != null && appliedCriterias.length > 0) {
      StringBuilder sb = new StringBuilder();
      int criteriaCount = 0;
      for (ViewCriteria vc : appliedCriterias) {
        if (criteriaCount++ > 0) {
          sb.append(",");
        }
        sb.append(vc.getName());
      }
      System.out.println("Applied In-Memory VCs = (" + sb + ")");
    }
    appliedCriterias = getApplyViewCriterias(ViewCriteria.CRITERIA_MODE_QUERY|ViewCriteria.CRITERIA_MODE_CACHE);
    if (appliedCriterias != null && appliedCriterias.length > 0) {
      StringBuilder sb = new StringBuilder();
      int criteriaCount = 0;
      for (ViewCriteria vc : appliedCriterias) {
        if (criteriaCount++ > 0) {
          sb.append(",");
        }
        sb.append(vc.getName());
      }
      System.out.println("Applied 'Both' VCs = (" + sb + ")");
    }
    System.out.println(getQuery());
    if (params != null) {
      if (getBindingStyle() == SQLBuilder.BINDING_STYLE_ORACLE_NAME) {
        StringBuilder binds = new StringBuilder("BindVars:(");
        int paramNum = 0;
        for (Object param : params) {
          paramNum++;
          Object[] nameValue = (Object[])param;
          String name = (String)nameValue[0];
          Object value = nameValue[1];
          if (paramNum > 1) {
            binds.append(",");
          }
          binds.append(name).append("=").append(value);
        }
        binds.append(")");
        System.out.println(binds);
      }
    }
  }
  // Log Row Fetched
  @Override
  protected ViewRowImpl createRowFromResultSet(Object object,
                                               ResultSet resultSet) {
    ViewRowImpl ret = super.createRowFromResultSet(object, resultSet);
    if (ret != null) {
      System.out.println("----[VO "+getName()+" fetched " + ret.getKey() + "]");
    }
    return ret;
  }
}
