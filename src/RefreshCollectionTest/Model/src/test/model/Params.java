/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

public class Params  {
  public Params() {
  }
  String stringParam = "TestValue";
  int intParam = 3;


  public void setStringParam(String stringParam) {
    this.stringParam = stringParam;
  }


  public String getStringParam() {
    return stringParam;
  }


  public void setIntParam(int intParam) {
    this.intParam = intParam;
  }


  public int getIntParam() {
    return intParam;
  }
}
