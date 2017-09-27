/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.Entity;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;

public class CustomEntityImpl extends EntityImpl {
    public CustomEntityImpl() {
    }

    public void afterCommit(TransactionEvent e) {
        byte stateBeforeItGetsReset = getEntityState();
        super.afterCommit(e);
        DBTransaction dbTxn = getDBTransaction();
        if (dbTxn instanceof CustomDBTransactionImpl) {
            CustomDBTransactionImpl customDbTxn =
                (CustomDBTransactionImpl)dbTxn;
            if (stateBeforeItGetsReset == Entity.STATUS_NEW) {
                customDbTxn.incrementInsertCounter();
            } else if (stateBeforeItGetsReset == Entity.STATUS_MODIFIED) {
                customDbTxn.incrementUpdateCounter();
            } else if (stateBeforeItGetsReset == Entity.STATUS_DELETED) {
                customDbTxn.incrementDeleteCounter();
            }
        }
    }
}
