/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.fwkext;
import com.sun.java.util.collections.Iterator;
import com.sun.java.util.collections.List;
import oracle.jbo.Key;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;

public class CustomEntityDefImpl extends EntityDefImpl  {
  /**
   * Workaround for Bug 3267441:
   * FindByPrimaryKey On Parent Entity Doesn't Find Extended Child EO Instance
   * -------------------
   * Overridden framework method to search in the entity caches
   * of subtyped entity defs if the findByPrimaryKey() returns
   * null for the current entity def.
   * @param txn DBTransaction to use
   * @param key Key of the entity instance being looked-up
   * @return Entity instance if found, or null of not found.
   */
  public EntityImpl findByPrimaryKey(DBTransaction txn, Key key) {
    EntityImpl e = super.findByPrimaryKey(txn, key);
    if (e == null) {
      List extendedDefs = getExtendedDefObjects();
      if (extendedDefs != null) {
        Iterator iter = extendedDefs.iterator();
        while (iter.hasNext()) {
          e = ((EntityDefImpl)iter.next()).findByPrimaryKey(txn,key);
          if (e != null) {
            break;
          }
        }
      }
    }
    return e;
  }
}
