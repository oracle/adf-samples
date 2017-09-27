/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.fwkext;
import oracle.jbo.Row;
import oracle.jbo.server.Entity;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ExtendedViewObjectImpl;

public class ExtendedViewRowImpl extends ViewRowImpl  {
  public ExtendedViewRowImpl() {
  }
  private String translateStatusToString(byte b) {
    String ret = null;
    switch (b) {
      case Entity.STATUS_INITIALIZED: {
        ret = "Initialized";
        break;
      }
      case Entity.STATUS_MODIFIED: {
        ret = "Modified";
        break;
      }
      case Entity.STATUS_UNMODIFIED: {
        ret = "Unmodified";
        break;
      }
      case Entity.STATUS_NEW: {
        ret = "New";
        break;
      }
    }
    return ret;
  }
  public Object getAttribute(int i) {
    if (getStructureDef().getAttributeDef(i).getName().equals(ExtendedViewObjectImpl.ROWSTATE_ATTR)) {
        Entity primaryEntity = getEntity(0);
        if (primaryEntity != null) {
          return translateStatusToString(primaryEntity.getEntityState());
        }
    }
    return super.getAttribute(i);
  }
}
