/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.managed;

import java.util.List;

public class AppInfo {
  private List expectedApplicationExceptionPackageNames;
  public AppInfo() {
  }
  public void setExpectedApplicationExceptionPackageNames(List setExpectedApplicationExceptionPackageNames) {
    this.expectedApplicationExceptionPackageNames = setExpectedApplicationExceptionPackageNames;
  }
  public List getExpectedApplicationExceptionPackageNames() {
    return expectedApplicationExceptionPackageNames;
  }
}
