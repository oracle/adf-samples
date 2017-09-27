/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.model;

import oracle.jbo.server.DBTransactionImpl2;

public class NoDatabaseConnectionDBTransactionImpl2 extends DBTransactionImpl2 {

    /*
     * Do nothing for rollback.
     */
    protected void doRollback() {
       // super.doRollback();
    }
}
