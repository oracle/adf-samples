/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.pageDefs;

import oracle.jbo.common.JboResourceBundle;

public class TestPagePageDefMsgBundle_it extends JboResourceBundle {
  static final Object[][] sMessageStrings = 
  { { "DeptViewTransientNumberAttribute_null", "<Nessun Dipartimento>" } };

  /**This is the default constructor (do not remove)
   */
  public TestPagePageDefMsgBundle_it() {
  }

  /**@return an array of key-value pairs.
   */
  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }
}
