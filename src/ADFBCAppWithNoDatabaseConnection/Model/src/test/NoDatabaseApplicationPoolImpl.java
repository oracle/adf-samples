/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.common.ampool.ApplicationPoolImpl;

public class NoDatabaseApplicationPoolImpl extends ApplicationPoolImpl {
    public NoDatabaseApplicationPoolImpl() {
        super();
    }

    @Override
    public boolean isSupportsPassivation() {
        return false;
    }
}
