/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.client;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class TestClient  {
  public static void main(String[] args) {
    String        amDef = "demo.model.HRModule";
    String        config = "HRModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(amDef,config);
    ViewObject vo = am.findViewObject("Employees");
    vo.setAccessMode(RowSet.RANGE_PAGING);
    vo.setRangeSize(10);
    Row r = vo.first();
    Configuration.releaseRootApplicationModule(am,true);
  }
}
