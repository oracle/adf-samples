/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model.common;
import model.common.DeptImplMsgBundle;
import oracle.jbo.ValidationException;
import oracle.jbo.common.StringManager;
/**
 * Example of a subclass of ValidationException that customizes the
 * exception message based on the DeptImplMsgBundle.
 */
public class TwoDepartmentsBothHaveXXInNameException extends ValidationException  {
  public TwoDepartmentsBothHaveXXInNameException() {
    super(StringManager.getString(DeptImplMsgBundle.class.getName(),
                                    DeptImplMsgBundle.TWO_XX_NAMES_ERROR,null));
  }
}
