/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.ViewObjectImpl;

public class QueryPrintingViewObjectImpl extends ViewObjectImpl {
    @Override
    protected void bindParametersForCollection(QueryCollection qc,
                                               Object[] params,
                                               PreparedStatement stmt) throws SQLException {
        super.bindParametersForCollection(qc, params, stmt);
        if (qc != null) {
            System.out.println("#==[Executing VO "+getName()+"]==#");
            System.out.println(getQuery());
        }
    }
}
