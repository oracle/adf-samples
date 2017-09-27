/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/service/ToyStoreDBTransactionImpl.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.model.service;
import oracle.jbo.DMLConstraintException;
import oracle.jbo.JboException;
import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.TransactionEvent;
import toystore.fwk.exceptions.ErrorMessages;
/**
 * Custom ADF DBTransaction implementation.
 * 
 * Works in tandem with a custom DBTransactionFactory implementation
 * which returns instances of this subclass of DBTransactionImpl2
 * instead of the default one.
 * 
 * @author Steve Muench
 */
public class ToyStoreDBTransactionImpl extends DBTransactionImpl2 {
  /**
   * Overridden framework method that traps any ADF exceptions thrown
   * during the post/commit cycle, and if they happen to be wrapping
   * a database exception representing a constraint violation, it
   * rethrows a new exception with a custom error message from the
   * toystore.fwk.util.ErrorMessages bundle, keyed by the name of the database
   * constraint that has been raised.
   * 
   * @param te The transaction event.
   */
  public void postChanges(TransactionEvent te) {
    try {
      super.postChanges(te);
    }
    catch (DMLConstraintException ex) {
      throw new JboException(ErrorMessages.class, ex.getConstraintName(), null);
    }
  }
}
