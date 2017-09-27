/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.server.EntityImpl;
public class CustomEntityImpl extends EntityImpl {
  public CustomEntityImpl() {
  }
  public Object getPostedAttribute(int index) {
    return super.getPostedAttribute(index);
  }
}
