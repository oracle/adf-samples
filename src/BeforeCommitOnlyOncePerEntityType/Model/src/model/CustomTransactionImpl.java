/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;
import java.util.HashMap;
import java.util.Hashtable;
import oracle.jbo.server.DBTransactionImpl2;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
/**
 * Custom DB Transaction Impl class that enables keeping track of
 * whether a beforeCommit() method has fired for any entities
 * of a given entity type.
 *
 * NOTE: If your beforeCommit() logic throws validation errors, you
 * ====  must set the jbo.txn.handleafterpostexc property to "true"
 *       to have the framework automatically handle rolling back
 *       the in-memory state of your already-posted entity objects.
 */
public class CustomTransactionImpl extends DBTransactionImpl2 {
  public boolean alreadyDidBeforeCommitForEntityType(EntityImpl eo) {
    return getBeforeCommitTracker().containsKey(defNameForEntity(eo));
  }
  public void markAlreadyDidBeforeCommitForEntityType(EntityImpl eo) {
    getBeforeCommitTracker().put(defNameForEntity(eo), null);
  }
  /**
   * Commit the pending changes in the transaction. When done, clean up
   * the entries in our beforeCommit-tracker HashMap.
   */
  public void commit() {
    try {
      super.commit();
    }
    finally {
      getBeforeCommitTracker().clear();
    }
  }
  /**
   * Use a HashMap in the session to keep track of the entity defs
   * on which we've already fired a beforeCommit() for at least one instance.
   * @return HashMap of entity def names that we've already processed.
   */
  private HashMap getBeforeCommitTracker() {
    HashMap h = (HashMap) getSession().getUserData().get(BEFORE_COMMIT_TRACKER);
    if (h == null) {
      h = new HashMap();
      getSession().getUserData().put(BEFORE_COMMIT_TRACKER, h);
    }
    return h;
  }
  /**
   * Returns the fully-qualified entity def name for any entity instance.
   * 
   * @param eo An entity instance.
   * @return Fully-qualified entity def name
   */
  private static String defNameForEntity(EntityImpl eo) {
    return ((EntityDefImpl)eo.getStructureDef()).getFullName();
  }
  private static final String BEFORE_COMMIT_TRACKER = "BeforeCommitTracker";
}
