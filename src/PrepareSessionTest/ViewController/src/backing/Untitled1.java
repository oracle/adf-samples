/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package backing;
import devguide.util.ADFJSFUtils;
import devguide.util.JSFUtils;

import java.io.IOException;
public class Untitled1 {
  public Untitled1() {
  }
  public String onClickInvalidateSessionAndRedirectButton() throws IOException {
    System.out.println("#### Invalidate Session And Redirect Button Clicked ####");
    JSFUtils.invalidateSessionAndRedirect("untitled1.jsp");
    return null;
  }
  public String onClickReleaseStateless() throws IOException {
     System.out.println("#### Release Stateless Button Clicked ####");
     ADFJSFUtils.getDataControl("AppModuleDataControl").resetState();
    return null;
  }  
}
