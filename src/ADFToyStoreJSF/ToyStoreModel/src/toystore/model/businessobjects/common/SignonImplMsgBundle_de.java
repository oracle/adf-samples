/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/SignonImplMsgBundle_de.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import oracle.jbo.common.JboResourceBundle;
import toystore.fwk.exceptions.ErrorMessages;
/**
 * German translations of Signon entity object control hints
 * Deutsche Übersetzung der Kontroll Hinweise der Signon Entität
 */
public class SignonImplMsgBundle_de extends SignonImplMsgBundle {
  static final Object[][] sMessageStrings = {
    {
      ErrorMessages.ENTITY_ALREADY_EXISTS,
      "Ein anderer Benutzer hat diesen Namen bereits gewählt. Bitte Versuchen Sie einen anderen."
    },
    { "Username_LABEL", "Anwendernamen" },
    { "Username_TOOLTIP", "Bitte geben Sie Ihren Anwendernamen ein" },
    { "Password_LABEL", "Passwort" },
    { "Password_TOOLTIP", "Bitte geben Sie Ihr Passwort ein." },
  };

  public SignonImplMsgBundle_de() {
  }

  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }
}
