/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;
import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.DatabaseTransactionFactory;
/**
 * This class exists for the sole purpose of returning an instance
 * of our custom CustomTransactionImpl class instead of the default
 * transaction implementation.
 * 
 * NOTE: To use the CustomTransactionImpl class, you must set
 * ====  the TransactionFactory property in your confirguration to
 *       the fully-qualified class name of the custom TransactionFactory
 *       that will return instances of the CustomTransactionImpl class
 *       instead of the default.
 */
public class CustomTransactionFactory extends DatabaseTransactionFactory {
  public DBTransactionImpl2 create() {
    return new CustomTransactionImpl();
  }
}
