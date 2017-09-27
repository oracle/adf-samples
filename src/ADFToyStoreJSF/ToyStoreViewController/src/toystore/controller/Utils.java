/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller;
import java.io.IOException;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
public class Utils {
  private static final String PERFORMED_REDIRECT = "toystore.performed.redirect";
  private static String navigationRuleToPage(String url) {
    if (url != null) {
      int idxLeadSlash = url.indexOf('/');
      if (idxLeadSlash >= 0) {
        url = url.substring(idxLeadSlash + 1);
      }
      int idxTrailingDot = url.lastIndexOf('.');
      if (idxTrailingDot >= 0) {
        url = url.substring(0, idxTrailingDot);
      }
    }
    return url;
  }
  public static boolean performedRedirect() {
    return JSFUtils.getRequestAttribute(PERFORMED_REDIRECT) != null;
  }
  public static void redirectToSignin() {
    try {
      JSFUtils.redirect("signin.jsp?returnTo=" + getOutcomeForView());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    JSFUtils.setRequestAttribute(PERFORMED_REDIRECT);
    JSFUtils.getFacesContext().renderResponse();
  }

  private static String getOutcomeForView() {
    return navigationRuleToPage(JSFUtils.getRootViewId());
  }
  public static AppUserManager getAppUserManager() {
    return (AppUserManager)JSFUtils.getManagedBean("AppUserManager");
  }  
  public static boolean isSignedOn(HttpServletRequest request) {
    return request.getSession(true).getAttribute("UserLoggedIn") != null;
  }  
  
}
