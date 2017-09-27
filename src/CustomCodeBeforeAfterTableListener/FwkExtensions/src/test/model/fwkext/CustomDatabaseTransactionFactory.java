/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.fwkext;

import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.DatabaseTransactionFactory;

public class CustomDatabaseTransactionFactory extends DatabaseTransactionFactory {
  public DBTransactionImpl2 create() {
    return new CustomDBTransactionImpl();
  }
}
