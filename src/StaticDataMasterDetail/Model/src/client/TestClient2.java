/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package client;

import oracle.jbo.*;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.*;
import oracle.jbo.domain.Number;

public class TestClient2 {
  public static void main(String[] args) {
    String amDef = "model.TestModule";
    String config = "TestModuleLocal";
    ApplicationModule am = 
      Configuration.createRootApplicationModule(amDef, config);
    ViewObject vo = am.findViewObject("States");
    vo.executeQuery();
    System.out.println("Number of States: " + vo.getEstimatedRowCount());
    while (vo.hasNext()) {
      Row state = vo.next();
      String stateName = (String)state.getAttribute("Name");
      RowSet cities = (RowSet)state.getAttribute("Cities");
      System.out.println("Number of Cities in State " + stateName + ": " + 
                         cities.getEstimatedRowCount());
      while (cities.hasNext()) {
        Row city = cities.next();
        String cityName = (String)city.getAttribute("Name");
        System.out.println(cityName + ", " + stateName);
      }
    }
    Configuration.releaseRootApplicationModule(am, true);
  }
}
