/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view;
import oracle.adf.model.bc4j.DataControlFactoryImpl;
public class MyDataControlFactoryImpl extends DataControlFactoryImpl {
  protected String getDataControlClassName() {
    return MyDCJboDataControl.class.getName();
  }
}
