/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class TestClient
{
  public static void main(String[] args)
  {
    String amDef = "model.TestModule";
    String config = "TestModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(amDef, config);
    ViewObject vo = am.findViewObject("States");
    vo.executeQuery();
    System.out.println("Number of States: "+vo.getEstimatedRowCount());
    while (vo.hasNext()) 
    {
      Row state = vo.next();
      String stateName = (String)state.getAttribute("Name");
      RowSet cities = (RowSet)state.getAttribute("Cities");
      
      while (cities.hasNext()) 
      {
        Row city = cities.next();
        String cityName = (String)city.getAttribute("Name");
        System.out.println(cityName+", "+stateName);
      }
      System.out.println("Number of Cities in State "+stateName+": "+cities.getEstimatedRowCount());
    }
    vo = am.findViewObject("CitiesByStateId");
    vo.setNamedWhereClauseParam("TheStateId",2);
    vo.executeQuery();
    while (vo.hasNext()) {
      Row city = vo.next();
      String cityName = (String)city.getAttribute("Name");
      System.out.println(cityName);
    }
    System.out.println("---");
    vo.setNamedWhereClauseParam("TheStateId",1);
    vo.executeQuery();
    while (vo.hasNext()) {
      Row city = vo.next();
      String cityName = (String)city.getAttribute("Name");
      System.out.println(cityName);
    }
    Configuration.releaseRootApplicationModule(am, true);
  }

}
