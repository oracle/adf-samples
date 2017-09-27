/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
public class Operation {
  public static final Operation READXML = new Operation("ReadXML");
  public static final Operation WRITEXML = new Operation("WriteXML"); 
  private final String operation;
  private Operation(String operation) { this.operation = operation; }
  public String toString() { return operation; };
}
