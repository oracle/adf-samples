/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreViewController/src/toystore/controller/AppUserManager.java,v 1.2 2006/01/27 22:23:19 steve Exp $
package toystore.controller;

import javax.faces.context.FacesContext;

/**
 * Class encapsulating access to information about currently logged-in user.
 * 
 * @author Steve Muench
 */
public class AppUserManager {
  public AppUserManager() {
  }
  /*
   * Session-scope attribute name that keeps track of a user's being logged in
   */
  private static final String LOGIN = "UserLoggedIn";

  /**
   * Sign out the current user by removing the session-scope
   * attribute we use to keep track of his/her being signed in.
   */
  public void signOut() {
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(LOGIN);
  }
  /**
   * Return the name of the signed-in user.
   * 
   * @return The currently logged-on user name, or null
   */
  public String getSignedInUser() {
    if (isSignedOn()) {
      return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN);
    } else {
      return null;
    }
  }
  /**
   * Sets a session-scope attribute to keep track of the
   * user's being signed in.
   * 
   * @param username The username to sign in
   */
  public void signIn(String username) {
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(LOGIN,username);
  }
  /**
   * Returns true if the current user is signed in.
   * @return True, if a user is currently logged in
   */
  public boolean isSignedOn() {
    return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(LOGIN) != null;
  }
}
