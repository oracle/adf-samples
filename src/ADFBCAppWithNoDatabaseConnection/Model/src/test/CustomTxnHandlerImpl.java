/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.sql.Connection;

import oracle.jbo.server.DefaultTxnHandlerImpl;

public class CustomTxnHandlerImpl extends DefaultTxnHandlerImpl {

    @Override
    public void handleCommit(Connection conn, boolean autoStart) {
        System.out.println("###> ignoring handleCommit() <###");
        // super.handleCommit(conn, autoStart);
    }

    @Override
    public void handleRollback(Connection conn, boolean autoStart) {
        System.out.println("###> ignoring handleRollback() <###");
    }
}
