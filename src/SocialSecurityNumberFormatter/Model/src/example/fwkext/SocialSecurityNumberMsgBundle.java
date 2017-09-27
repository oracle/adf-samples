/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.fwkext;

import java.util.ListResourceBundle;

public class SocialSecurityNumberMsgBundle extends ListResourceBundle {
  public static final String INVALID_SSN = "20001";
  private static final Object[][] sMessageStrings = new String[][] {
      { INVALID_SSN, "Social Security number must have the format ###-##-####" },
    };

  /**
   * Return String Identifiers and corresponding Messages in a two-dimensional array.
   */
  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
