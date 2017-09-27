/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.model;

import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.DatabaseTransactionFactory;

public class NoDatabaseConnectionDatabaseTransactionFactory extends DatabaseTransactionFactory {
    public DBTransactionImpl2 create() {
        return new NoDatabaseConnectionDBTransactionImpl2();
    }
}
