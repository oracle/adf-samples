/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.JboWarning;

/*
 * Represents a subclass of Warnings that are only informational.
 */
public class InformationalMessage extends JboWarning {
  public InformationalMessage(String message)
  {
     super(message);
  }

}
