/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package testclient;
import example.common.TestModule;
import oracle.jbo.client.Configuration;
import oracle.jbo.*;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.*;

public class SampleClient  {
  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    String        amDef = "example.TestModule";
    String        config = "TestModuleLocal";
    TestModule am = (TestModule)
    Configuration.createRootApplicationModule(amDef,config);
    am.demonstrateAccessingViewlinkAttributes();
    // Work with your appmodule and view object here
    Configuration.releaseRootApplicationModule(am,true);
  }
}
