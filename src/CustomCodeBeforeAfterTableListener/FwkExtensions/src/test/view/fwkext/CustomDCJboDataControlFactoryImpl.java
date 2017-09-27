/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.fwkext;

import oracle.adf.model.bc4j.DataControlFactoryImpl;
public class CustomDCJboDataControlFactoryImpl extends DataControlFactoryImpl {
  public CustomDCJboDataControlFactoryImpl() {
  }
  protected String getDataControlClassName() {
    return CustomDCJboDataControl.class.getName();
  }  
}
