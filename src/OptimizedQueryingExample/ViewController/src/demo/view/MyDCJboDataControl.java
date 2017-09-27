/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;

public class MyDCJboDataControl extends JUApplication {
  public MyDCJboDataControl() {
  }
  /*
   * This is required for the default data control factory 
   * to correctly instantiate this class.
   */
  public MyDCJboDataControl(SessionCookie sessionCookie)
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
