/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/beans/LoginBean.java,v 1.1.1.1 2006/01/26 21:47:20 steve Exp $
package toystore.model.beans;
public class LoginBean {
  String _username;
  String _password;

  public LoginBean() {}

  public void setUsername(String username) {
    _username = username;
  }
  public String getUsername() {
    return _username;
  }
  public void setPassword(String password) {
    _password = password;
  }
  public String getPassword() {
    return _password;
  }
}
