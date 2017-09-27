/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.AttributeDef;
import oracle.jbo.Row;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.Entity;
import oracle.jbo.server.ViewObjectImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {
  public CustomViewObjectImpl() {
  }
  private static final String ORIG = "_orig";
  protected void create() {
    super.create();
    for (AttributeDef attr : getAttributeDefs()) {
      String origAttrName = attr.getName()+ORIG;      
      addDynamicAttribute(origAttrName);
      ((AttributeDefImpl)findAttributeDef(origAttrName)).setUpdateableFlag(AttributeDef.READONLY);      
    }
  }
}
