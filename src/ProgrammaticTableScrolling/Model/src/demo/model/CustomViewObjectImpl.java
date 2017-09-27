/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;

public class CustomViewObjectImpl extends ViewObjectImpl {
  public CustomViewObjectImpl() {
  }
  public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
    if (getProperty("PlainPaging") != null) {
      return getRangeSize();
    }    
    return super.getQueryHitCount(viewRowSet);
  }
}
