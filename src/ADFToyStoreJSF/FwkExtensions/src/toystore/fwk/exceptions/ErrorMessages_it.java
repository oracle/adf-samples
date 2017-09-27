/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/exceptions/ErrorMessages_it.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.exceptions;
import java.util.ListResourceBundle;
/**
 * Italian translations of message bundle for custom error messages used
 * by the framework extension functionality for the Toy Store demo.
 *
 * Traduzioni italiane dei messaggi d'errore nel message bundle usato
 * dalla funzionalità della Toy Store Demo che estende il framework ADF.
 * 
 * @author Steve Muench
 */
public class ErrorMessages_it extends ErrorMessages {
  private static final Object[][] sMessageStrings = new String[][] {
      {
        ENTITY_ALREADY_EXISTS,
        "Un entity {1} con {2} con valore ''{3}'' esiste già"
      },
      { ACCT_PK_CONSTRAINT, "This username is already taken. Try another." },
      { SIGNON_PK_CONSTRAINT, "This username is already taken. Try another." },
      {
        NO_SUCH_STATE_FOR_COUNTRY,
        "Il codice di provincia/stato ''{0}'' non è valido per questo paese"
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
