/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.exceptions;

import javax.ejb.ApplicationException;
import javax.ejb.EJBException;
public class ContactAlreadyExistsException extends Exception {
  public ContactAlreadyExistsException() {
  }
  public ContactAlreadyExistsException(String msg) {
    super(msg);
  }
}
