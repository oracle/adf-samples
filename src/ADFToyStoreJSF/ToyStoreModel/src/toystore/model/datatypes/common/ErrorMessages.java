/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/datatypes/common/ErrorMessages.java,v 1.1.1.1 2006/01/26 21:47:22 steve Exp $
package toystore.model.datatypes.common;
import java.util.ListResourceBundle;
public class ErrorMessages extends ListResourceBundle {
  public static final String INVALID_EMAIL = "20002";
  public static final String INVALID_CREDITCARD = "20003";
  public static final String INVALID_EXPRDATE = "20004";
  private static final Object[][] sMessageStrings = new String[][] {
      { INVALID_EMAIL, "Please enter a valid email address" },
      { INVALID_CREDITCARD, "Credit card must be sixteen digits with no spaces" },
      { INVALID_EXPRDATE, "Expiration date must be in MM/YYYY format" },
    };

  /**
   * Return String Identifiers and corresponding Messages in a two-dimensional array.
   */
  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
