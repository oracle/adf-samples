/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
public class Test {
  public static void main(String[] args)  {
    String _am = "test.hr.HRModule", _cf = "HRModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(_am,_cf);
    ViewObject vo = am.findViewObject("Departments");
    System.out.println(vo.getDefFullName());
    Configuration.releaseRootApplicationModule(am,true);
  }
}
