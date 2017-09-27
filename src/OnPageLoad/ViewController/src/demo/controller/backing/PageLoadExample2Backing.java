/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.controller.backing;
import demo.controller.TestModuleBackingBeanBase;
public class PageLoadExample2Backing extends TestModuleBackingBeanBase {
  public PageLoadExample2Backing() {
  }
  public String commandButton_action() {
    // Add event code here...
    return null;
  }
  /*
   * This method is called by some code in the base TestModuleBackingBeanBase
   * class before the prepareModel phase of the ADF controller lifecycle.
   */
  public void onPageLoad() {
    if (!isPostback()) {
      /*
       * This is an alternative to using an ADF action binding
       * to invoke the method. We get the custom interface for
       * the AppModule Business Service, then call a method on it.
       */    
      getTestModule().doSomething();
    }
  }
}
