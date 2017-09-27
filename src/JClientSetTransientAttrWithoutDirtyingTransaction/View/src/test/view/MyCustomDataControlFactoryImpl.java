/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import oracle.adf.model.bc4j.DataControlFactoryImpl;
public class MyCustomDataControlFactoryImpl extends DataControlFactoryImpl {
  public MyCustomDataControlFactoryImpl() {
  }
  protected String getDataControlClassName() {
    return MyCustomADFBCDataControl.class.getName();
  }
}
