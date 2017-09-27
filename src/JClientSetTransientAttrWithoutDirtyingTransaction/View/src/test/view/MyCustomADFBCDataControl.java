/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import oracle.jbo.ApplicationModule;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;
public class MyCustomADFBCDataControl extends JUApplication {
  public MyCustomADFBCDataControl() {
  }
  /*
   * This is required for the default data control factory 
   * to correctly instantiate this class.
   */
   /**
   * Constructor used internally by the framework to associate an application module
   * with a JClient application object.
   */
   public MyCustomADFBCDataControl(ApplicationModule am)
   {
      super(null, am, null);
   }   
  public void setTransactionModified() {
    if (((ApplicationModule)getDataProvider()).getTransaction().isDirty()) {
      super.setTransactionModified();      
    }
  }
}
