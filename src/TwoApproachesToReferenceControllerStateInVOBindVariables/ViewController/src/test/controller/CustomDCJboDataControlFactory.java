/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.controller;

import oracle.adf.model.bc4j.DataControlFactoryImpl;

public class CustomDCJboDataControlFactory extends DataControlFactoryImpl {
  protected String getDataControlClassName() {
    return CustomDCJboDataControl.class.getName();
  }
}
