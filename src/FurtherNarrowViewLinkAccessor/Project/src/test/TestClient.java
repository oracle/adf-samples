/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

import test.common.TestModule;

public class TestClient {
  public static void main(String[] args) {
    String        amDef = "test.TestModule";
    String        config = "TestModuleLocal";
    TestModule am = (TestModule)
    Configuration.createRootApplicationModule(amDef,config);
    am.test();
    Configuration.releaseRootApplicationModule(am,true);
  }
  
}
