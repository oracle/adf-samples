/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model.common;

public class DeptImplMsgBundle_it extends DeptImplMsgBundle  {
  public DeptImplMsgBundle_it() {
  }
  /**
   * 
   * @return an array of key-value pairs.
   */
  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }

  static final Object[][] sMessageStrings = {
    {"Deptno_LABEL", "ID"},
    {"Dname_LABEL", "Nome"},
    {"Loc_LABEL", "Località"},
    {TWO_XX_NAMES_ERROR,"Non si può avere due dipartimenti con 'XX' nel loro nome"}
  };  
}
