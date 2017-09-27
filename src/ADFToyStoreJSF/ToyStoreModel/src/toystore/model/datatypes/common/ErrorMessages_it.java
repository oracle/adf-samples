/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/datatypes/common/ErrorMessages_it.java,v 1.1.1.1 2006/01/26 21:47:22 steve Exp $
package toystore.model.datatypes.common;
import java.util.ListResourceBundle;
/**
 * Italian translations of datatype/domain error messages
 * Traduzioni italiane dei messaggi di errore di datatype/domain custom
 */
public class ErrorMessages_it extends ListResourceBundle {
  public static final String INVALID_EMAIL = "20002";
  public static final String INVALID_CREDITCARD = "20003";
  public static final String INVALID_EXPRDATE = "20004";
  private static final Object[][] sMessageStrings = new String[][] {
      { INVALID_EMAIL, "Inserisci un indirizzo email valido" },
      {
        INVALID_CREDITCARD,
        "Numero di carta di credito deve essere 16 cifre senza spazi"
      },
      { INVALID_EXPRDATE, "Data di scadenza deve avere il formatto MM/YYYY" },
    };

  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
