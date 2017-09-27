/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.fwkext;
import oracle.jbo.server.ViewRowImpl;
public class CustomViewRowImpl extends ViewRowImpl {
  protected Object getAttributeInternal(int i) {
    if (i == ((CustomViewObjectImpl)getViewObject()).getValidAttrIndex()) {
      return getEntity(0).isValid();
    }
    else {
      
    }
    return super.getAttributeInternal(i);
  }
}
