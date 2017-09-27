/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.ui.controller;

import davelaar.demo.ui.util.JsfUtils;

public class EditEmployeeTabRegionController
  extends TabRegionController
{


  @Override
  protected String getTabLabel()
  {
    return "Edit Employee "+JsfUtils.getExpressionValue("#{bindings.LastName.inputValue}");
  }
}
