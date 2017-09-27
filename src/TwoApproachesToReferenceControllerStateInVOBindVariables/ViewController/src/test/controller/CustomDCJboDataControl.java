/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;

import test.controller.util.EL;

import test.model.common.ExampleModule;

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
      String favoriteColor = EL.getAsString("#{UserInfo.favoriteColor}");
      super.beginRequest(requestCtx);
      if (favoriteColor != null) {
        /*
         * ExampleModule is the custom AM interface
         * that the ADF design time generates for
         * the application module named "ExampleModule"
         * once at least one method is exposed on
         * the client interface. In this case, our
         * AM named "ExampleModule" has exposed a custom
         * method named "setSessionFavoriteColor()" on its client
         * interface, and we're calling it here.
         */
        ExampleModule am = (ExampleModule)getDataProvider();
        am.setSessionFavoriteColor(favoriteColor);
      }
    }
}
