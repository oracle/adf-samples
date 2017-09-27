/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/service/ToyStoreDBTransactionFactory.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.model.service;
import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.DatabaseTransactionFactory;
import toystore.fwk.model.service.ToyStoreDBTransactionImpl;
/**
 * Custom ADF DBTransactionFactory implementation.
 * 
 * It's only role in life is returning instances of the custom subclass
 * of DBTransactionImpl2 instead of the default framework base class for
 * a transaction implementation.
 * 
 * In order for this custom factory to get used, set the ADF business
 * components configuration property 'TransactionFactory' to the
 * fully-qualified name of this class.
 * @author Steve Muench
 */
public class ToyStoreDBTransactionFactory extends DatabaseTransactionFactory {
  /**
   * Return an instance of our custom ToyStoreDBTransactionImpl class
   * instead of the default implementation.
   * 
   * @return An instance of our custom DBTransactionImpl implementation.
   */
  public DBTransactionImpl2 create() {
    return new ToyStoreDBTransactionImpl();
  }
}
