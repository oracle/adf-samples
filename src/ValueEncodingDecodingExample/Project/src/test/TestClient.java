/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

import test.common.UserInfoModule;

public class TestClient {
  public static void main(String[] args) {
    String        amDef = "test.UserInfoModule";
    String        config = "UserInfoModuleLocal";
    UserInfoModule am = (UserInfoModule) Configuration.createRootApplicationModule(amDef,config);

    // =============

    System.out.println("---> Testing iterating over view object");
    ViewObject vo = am.findViewObject("UserInfoView");
    vo.executeQuery();
    while (vo.hasNext()) {
      Row r = vo.next();
      System.out.println(r.getAttribute("Username"));
    }

    // =============

    System.out.println("---> Testing entity findByPrimaryKey()");
    am.testFindingUserInfoByPrimaryKey("smuench");

    // =============
    
    System.out.println("---> Testing view object findByKey()");
    Row r = vo.findByKey(new Key(new Object[]{"smuench"}),1)[0];
    System.out.println(r.getAttribute("Username"));
    
    // =============

    am.getTransaction().rollback();
    System.out.println("---> Testing entity findByPrimaryKey() after rollback");
    am.testFindingUserInfoByPrimaryKey("smuench");

    // =============
    
    am.getTransaction().rollback();
    System.out.println("---> Testing view object findByKey() after rollback");
    r = vo.findByKey(new Key(new Object[]{"smuench"}),1)[0];
    System.out.println(r.getAttribute("Username"));
    
    Configuration.releaseRootApplicationModule(am,true);
  }
  
}
