/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.example;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;
import test.example.common.MenRow;
import test.example.common.PeopleRow;
import test.example.common.TestModule;
import test.example.common.WomenRow;
public class TestClient  {
  public static void main(String[] args) {
    String        amDef = "test.example.TestModule";
    String        config = "TestModuleLocal";
    TestModule tm = (TestModule)Configuration.createRootApplicationModule(amDef,config);
    ViewObject vo = tm.findViewObject("People");
    vo.executeQuery();
    while (vo.hasNext()) {
      PeopleRow p = (PeopleRow)vo.next();
      System.out.println(p.getName());
      p.doPersonThing();
      if (p instanceof MenRow) {
        MenRow m = (MenRow)p;
        System.out.println(m.getManAttr());
      }
      else if (p instanceof WomenRow) {
        WomenRow w = (WomenRow)p;
        System.out.println(w.getWomanAttr());
      }
      System.out.println("Trying EO cache lookup via AM Custom Method, name = "+
      tm.findPersonName(p.getKey()));
      System.out.println("---");
    }
    Configuration.releaseRootApplicationModule(tm,true);
  }
}
