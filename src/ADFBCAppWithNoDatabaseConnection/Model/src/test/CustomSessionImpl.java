/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.server.DefaultTxnHandlerFactoryImpl;
import oracle.jbo.server.SessionImpl;
import oracle.jbo.server.TransactionHandlerFactory;

public class CustomSessionImpl extends SessionImpl {
    public CustomSessionImpl() {
        super();
    }

    private TransactionHandlerFactory mTxnHandlerFactory = new CustomTxnHandlerFactoryImpl();

    @Override
    public TransactionHandlerFactory getTransactionHandlerFactory() {
        return mTxnHandlerFactory;
    }
}
