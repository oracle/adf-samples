/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.model;

import oracle.jbo.common.ampool.ApplicationPoolImpl;

public class NoDatabaseConnectionApplicationPoolImpl extends ApplicationPoolImpl {
    public NoDatabaseConnectionApplicationPoolImpl() {
    }

    public String getConnectString() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getUserName() {
        return null;
    }
}
