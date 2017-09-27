/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.AttributeDef;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.Entity;
import oracle.jbo.server.ViewRowImpl;
public class CustomViewRowImpl extends ViewRowImpl {
  public CustomViewRowImpl() {
  }
  private static final String ORIG = "_orig";
  protected Object getAttributeInternal(int index) {
    AttributeDef attr = getViewObject().getAttributeDef(index);
    String name = attr.getName();
    int pos = name.indexOf(ORIG);
    if (pos >= 0 && name.endsWith(ORIG)) {
      String baseName = name.substring(0,pos);
      int entAttrSlot = getEntityAttrForAttribute(baseName).getIndex();
      Entity e = getEntityForAttribute(baseName);
      if (e instanceof CustomEntityImpl) {
        return ((CustomEntityImpl)e).getPostedAttribute(entAttrSlot);
      }
    }
    return super.getAttributeInternal(index);
  }
}
