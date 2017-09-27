/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.exceptions;

public class EmailRequiredException extends Exception {
  public EmailRequiredException() {
  }
  public EmailRequiredException(String msg) {
    super(msg);
  }
}
