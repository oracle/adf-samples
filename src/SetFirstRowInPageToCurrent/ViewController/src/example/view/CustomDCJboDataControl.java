/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;
import example.model.common.ExampleModule;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.share.ADFContext;

import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;
public class CustomDCJboDataControl extends JUApplication {
  public CustomDCJboDataControl() {
  }
  /*
   * This is required for the default data control factory 
   * to correctly instantiate this class.
   */
  public CustomDCJboDataControl(SessionCookie sessionCookie)
  {
     super(sessionCookie);
  }  
  public void beginRequest(HashMap requestCtx) {
    System.out.println("#### --------vvvvv----BeginRequest----vvvvv--------");
    super.beginRequest(requestCtx);
    HttpServletRequest request = (HttpServletRequest)ADFContext.getCurrent().getEnvironment().getRequest();
    ExampleModule em = (ExampleModule)getDataProvider();
    /*
     * Reference request attributes here
     * and pass any necessary values into
     * the AM by passing them as parameters
     * to a custom AM method.
     */
    em.exampleMethod();

  }
  public void endRequest(HashMap requestCtx) {
    super.endRequest(requestCtx);
    System.out.println("#### --------^^^^^----EndRequest----^^^^^--------");
  }  
}
