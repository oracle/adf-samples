/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.fwkext;
import oracle.jbo.server.ViewObjectImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {
  private static final String VALID_ATTR_NAME = "Valid";
  private int VALID; 
  int getValidAttrIndex() {
    return VALID;
  }
  protected void create() {
    super.create();
    addDynamicAttribute(VALID_ATTR_NAME);
    VALID = findAttributeDef(VALID_ATTR_NAME).getIndex();
  }
}
