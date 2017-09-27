/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.server.DefaultTxnHandlerFactoryImpl;
import oracle.jbo.server.TransactionHandler;

public class CustomTxnHandlerFactoryImpl extends DefaultTxnHandlerFactoryImpl {
    public CustomTxnHandlerFactoryImpl() {
        super();
    }

    @Override
    public TransactionHandler createTransactionHandler() {
        return new CustomTxnHandlerImpl();
    }
}
