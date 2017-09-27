/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.srdemo.view;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;

import oracle.srdemo.model.common.AppModule;
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
    HttpServletRequest request = (HttpServletRequest)requestCtx.get(BindingContext.HTTP_REQUEST);
    HttpSession session = request.getSession(false);
    String filterValue = null;    
    if (session != null) {
      filterValue = (String)session.getAttribute("Filter");
    }
    filterValue = "x";
    super.beginRequest(requestCtx);
    if (filterValue != null) {
      /*
       * AppModule is the custom AM interface
       * that the ADF design time generates for
       * the application module named "AppModule"
       * once at least one method is exposed on
       * the client interface. In this case, our
       * AM named "AppModule" has exposed a custom
       * method named "setFilter()" on its client
       * interface, and we're calling it here.
       */
      AppModule am = (AppModule)getDataProvider();
      am.setFilter(filterValue);
    }
  }
}
