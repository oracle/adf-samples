/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class TestClient {
  public static void main(String[] args) {
      String        amDef = "example.model.TestModule";
      String        config = "TestModuleLocal";
      ApplicationModule am =
      Configuration.createRootApplicationModule(amDef,config);
      ViewObject vo = am.findViewObject("CountryList");
      vo.executeQuery();
      while (vo.hasNext()) {
          Row r = vo.next();
          System.out.println(r.getAttribute("CountryCode"));
      }
      Configuration.releaseRootApplicationModule(am,true);
  }
  
}
