/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/exceptions/ErrorMessages_de.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.exceptions;
import java.util.ListResourceBundle;
/**
 * German translations of message bundle for custom error messages used
 * by the framework extension functionality for the Toy Store demo.
 *
 * Deutsche Übersetzung der Message Bundle für individuelle Fehlermeldungen
 * die vom Frameworks für die erweiterten Funktionalität der ToyStore Demo
 * benötigt werden
 * 
 * @author Steve Muench
 */
public class ErrorMessages_de extends ErrorMessages {
  private static final Object[][] sMessageStrings = new String[][] {
      {
        ENTITY_ALREADY_EXISTS,
        "Eine Entität {1} mit dem Attribut {2} und dem Wert ''{3}'' existiert bereits"
      },
      {
        ACCT_PK_CONSTRAINT,
        "Dieser Anwendername existiert bereits. Bitte versuchen Sie es nochmal."
      },
      {
        SIGNON_PK_CONSTRAINT,
        "Dieser Anwendername existiert bereits. Bitte versuchen Sie es nochmal."
      },
      { NO_SUCH_STATE_FOR_COUNTRY, "Die Länderkennung ''{0}'' ist nicht zulässig" }
    };

  /**
   * Return String Identifiers and corresponding Messages in a two-dimensional array.
   * @return Array of {key,Message} String arrays.
   */
  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
