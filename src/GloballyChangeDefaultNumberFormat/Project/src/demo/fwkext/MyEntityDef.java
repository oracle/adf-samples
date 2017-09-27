/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.fwkext;
import oracle.jbo.AttributeDef;
import oracle.jbo.AttributeHints;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;

public class MyEntityDef extends EntityDefImpl 
{
  public MyEntityDef()
  {
  }

  public void resolveDefObject()
  {
    // TODO:  Override this oracle.jbo.server.EntityDefImpl method
    super.resolveDefObject();
    AttributeDefImpl[] attrs = getAttributeDefImpls();
    if (attrs != null) 
    {
      for (int z = 0; z < attrs.length; z++) 
      {
        AttributeDefImpl curAttr = attrs[z];
        if (curAttr.getJavaType() == Number.class) 
        {
          curAttr.setProperty(AttributeHints.FMT_FORMAT,"000000");
          curAttr.setProperty(AttributeHints.FMT_FORMATTER,"oracle.jbo.format.DefaultNumberFormatter");
        }
      }
    }
  }
}
