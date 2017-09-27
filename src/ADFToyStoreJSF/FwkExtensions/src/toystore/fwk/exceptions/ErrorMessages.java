/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/exceptions/ErrorMessages.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.exceptions;
import java.util.ListResourceBundle;
/**
 * Message Bundle for custom error messages used by the framework
 * extension functionality for the Toy Store demo.
 * 
 * @author Steve Muench
 */
public class ErrorMessages extends ListResourceBundle {
  public static final String ACCT_PK_CONSTRAINT = "PK_ACCOUNT";
  public static final String SIGNON_PK_CONSTRAINT = "PK_SIGNON";
  public static final String ENTITY_ALREADY_EXISTS = "20001";
  public static final String NO_SUCH_STATE_FOR_COUNTRY = "20002";
  private static final Object[][] sMessageStrings = new String[][] {
      { ENTITY_ALREADY_EXISTS, "An {1} entity with {2} of ''{3}'' already exists" },
      { ACCT_PK_CONSTRAINT, "This username is already taken. Try another." },
      { SIGNON_PK_CONSTRAINT, "This username is already taken. Try another." },
      {
        NO_SUCH_STATE_FOR_COUNTRY,
        "The state/province code of ''{0}'' is not valid for this country"
      }
    };

  /**
   * Return String Identifiers and corresponding Messages in a two-dimensional array.
   *
   * @return Array of {key,Message} String arrays.
   */
  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
