/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import oracle.jbo.Transaction;
import oracle.jbo.domain.Array;

import oracle.jbo.domain.DomainContext;

import oracle.sql.ArrayDescriptor;

public class ArrayWithWorkaroundForBug7452095 extends Array {
    /**
    * <b>Internal:</b> <em>Applications should not use this constructor.</em>
    */
    public ArrayWithWorkaroundForBug7452095(ArrayDescriptor type, Connection conn, Object elements, Transaction txn)
       throws SQLException
    {
       super(type,conn,elements);
        Map m = new HashMap();
        m.put(DomainContext.ELEMENT_SQL_NAME,
        type.getTypeName());
        setContext(null, txn, m); 
    }
}
