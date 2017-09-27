/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.util.Locale;
import oracle.jbo.server.SessionImpl;
import oracle.jbo.common.PropertyMetadata;

public class MyVariantHandlingSessionImpl extends SessionImpl  {
  public MyVariantHandlingSessionImpl() {
  }

   public Locale getLocale() {
     Locale loc = Locale.getDefault();
     setLocale(loc);
     return loc;
   }  
}
