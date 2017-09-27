/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package fwkext;

import oracle.jbo.server.SQLBuilder;
import java.sql.PreparedStatement;

import java.sql.SQLException;

import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.SQLBuilder;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;
public class TracingViewObjectImpl extends ViewObjectImpl 
{
  public TracingViewObjectImpl()
  {
  }

        @Override
        protected void bindParametersForCollection(QueryCollection qc,
                                                   Object[] params,
                                                   PreparedStatement stmt) throws SQLException {
            String vrsiName = null;
            if (qc != null) {
                ViewRowSetImpl vrsi = qc.getRowSetImpl();
                vrsiName = vrsi.isDefaultRS() ? "<Default>" : vrsi.getName();
            }
            String voName = getName();
            String voDefName = getDefFullName();
            if (qc != null) {
                System.out.println("----[Exec query for VO="+voName+", RS="+vrsiName+"]----");
            }
            else {
                System.out.println("----[Exec COUNT query for VO="+voName+"]----");                
            }
            System.out.println("VODef ="+voDefName);
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
                        binds.append(name)
                             .append("=")
                            .append(value);
                    }
                    binds.append(")");
                    System.out.println(binds);
                }
            }
            super.bindParametersForCollection(qc, params, stmt);
        }
}
