/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;
import java.util.HashMap;

import oracle.adf.model.bc4j.DCJboDataControl;
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
  }
  public void endRequest(HashMap requestCtx) {
    super.endRequest(requestCtx);
    System.out.println("#### --------^^^^^----EndRequest----^^^^^--------");
  }  
}
