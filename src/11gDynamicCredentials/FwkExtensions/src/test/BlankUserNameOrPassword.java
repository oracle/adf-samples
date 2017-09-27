/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import oracle.jbo.JboException;

public class BlankUserNameOrPassword extends JboException  {
  public BlankUserNameOrPassword() {
    super("Blank username or password");
  }
}
