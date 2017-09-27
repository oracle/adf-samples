/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.jsf;
import toystore.controller.Utils;
public class GlobalActions {
  public String onLogin() {
    Utils.redirectToSignin();
    return null;
  }
  public String onLogout() {
    Utils.getAppUserManager().signOut();
    return "home";
  }
}
