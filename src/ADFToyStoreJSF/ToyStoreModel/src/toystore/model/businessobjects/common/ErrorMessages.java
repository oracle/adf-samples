/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/ErrorMessages.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import java.util.ListResourceBundle;
public class ErrorMessages extends ListResourceBundle {
  public static final String INVALID_EMAIL = "20002";
  private static final Object[][] sMessageStrings = new String[][] {
      { INVALID_EMAIL, "Please enter a valid email address" },
    };

  /**
   * Return String Identifiers and corresponding Messages in a two-dimensional array.
   */
  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
