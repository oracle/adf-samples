/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/datatypes/common/ErrorMessages_de.java,v 1.1.1.1 2006/01/26 21:47:22 steve Exp $
package toystore.model.datatypes.common;
import java.util.ListResourceBundle;
/**
 * German translations of datatype/domain error messages
 * Deutsche Übersetzung der datatype/domain Fehler meldungen
 */
public class ErrorMessages_de extends ListResourceBundle {
  public static final String INVALID_EMAIL = "20002";
  public static final String INVALID_CREDITCARD = "20003";
  public static final String INVALID_EXPRDATE = "20004";
  private static final Object[][] sMessageStrings = new String[][] {
      { INVALID_EMAIL, "Bitte geben Sie eine gültige Email Adresse ein" },
      {
        INVALID_CREDITCARD,
        "Eine gültige Kreditkartenformat hat 16 Zahlen und keine Leerzeichen"
      },
      { INVALID_EXPRDATE, "Das Format des Verfallsdatum muß MM/YYYY sein" },
    };

  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
