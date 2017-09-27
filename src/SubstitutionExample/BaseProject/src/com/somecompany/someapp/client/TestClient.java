/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package com.somecompany.someapp.client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;

public class TestClient 
{
  public static void main(String[] args)
  {
    String _am = "com.somecompany.someapp.SomeModule", _cf = "SomeModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(_am,_cf);
    // Add code here to work with application module
    ViewObject vo = am.findViewObject("Master");
    vo.executeQuery();
    Row r = vo.first();
    System.out.println(r.getAttribute("MyNumber"));
    Configuration.releaseRootApplicationModule(am,true);    
  }
}
