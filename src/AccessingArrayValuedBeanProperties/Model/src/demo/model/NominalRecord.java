/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model;

public class NominalRecord  {
String surname;
String firstname;
String[] descriptionCode;
String[] descriptionText;  
  public NominalRecord() {
  }


  public void setSurname(String surname) {
    this.surname = surname;
  }


  public String getSurname() {
    return surname;
  }


  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }


  public String getFirstname() {
    return firstname;
  }


  public void setDescriptionCode(String[] descriptionCode) {
    this.descriptionCode = descriptionCode;
  }


  public String[] getDescriptionCode() {
    return descriptionCode;
  }


  public void setDescriptionText(String[] descriptionText) {
    this.descriptionText = descriptionText;
  }


  public String[] getDescriptionText() {
    return descriptionText;
  }
}
