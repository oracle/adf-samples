/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

import test.common.AppModule;

public class TestClient {
  public static void main(String[] args) {
      String        amDef = "test.AppModule";
      String        config = "AppModuleLocal";
      AppModule am = (AppModule) Configuration.createRootApplicationModule(amDef,config);
      am.createViewObjectAndViewLinks();
      ViewObject deptVO = am.findViewObject("Departments");
      ViewObject empVO = am.findViewObject("Employees");
      while (deptVO.hasNext()) {
          Row deptRow = deptVO.next();
          System.out.println(deptRow.getAttribute("Dname"));
          while (empVO.hasNext()) {
              Row empRow = empVO.next();
              System.out.println("--| "+empRow.getAttribute("Ename"));
          }
      }
      Configuration.releaseRootApplicationModule(am,true);
  }
  
}
