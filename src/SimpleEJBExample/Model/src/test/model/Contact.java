/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.util.Date;

public class Contact {
  private String name;
  private String email;
  private Date updated;
  public Contact() {
  }
  public Contact(Contact c) {
    this.name = c.getName();
    this.email = c.getEmail();
    this.updated = c.getUpdated();
  }
  public Contact(String name, String email) {
    this.name = name;
    this.email = email;
    updated = new Date();
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return name;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getEmail() {
    return email;
  }
  public void setUpdated(Date updated) {
    this.updated = updated;
  }
  public Date getUpdated() {
    return updated;
  }
}
