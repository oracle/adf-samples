/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/AccountImplMsgBundle_de.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import oracle.jbo.common.JboResourceBundle;
import toystore.fwk.exceptions.ErrorMessages;
/**
 * German translations of Account entity object control hints
 * Deutsche Übersetzung der Kontrollhilfen des Account Entität Objektes
 */
public class AccountImplMsgBundle_de extends AccountImplMsgBundle {
  static final Object[][] sMessageStrings = {
    { "Country_Rule_0", "Ungültige Länderkennung" },
    {
      ErrorMessages.ENTITY_ALREADY_EXISTS,
      "Ein anderer Benutzer hat diesen Namen bereits gewählt. Bitte versuchen Sie es nochmal."
    },
    { "Addr1_LABEL", "Straße" },
    { "Addr1_TOOLTIP", "Bitte geben Sie den Straßennamen an" },
    { "City_LABEL", "Stadt" },
    { "City_TOOLTIP", "Bitte geben Sie den Namen der Stadt an in der Sie wohnen" },
    { "Country_LABEL", "Land" },
    {
      "Country_TOOLTIP",
      "Bitte geben Sie den Namen des Landes an in dem Sie wohnen."
    },
    { "Email_LABEL", "Email" },
    { "Email_TOOLTIP", "Bitte geben Sie Ihre email Adresse an." },
    { "Firstname_LABEL", "Vorname" },
    { "Firstname_TOOLTIP", "Bitte geben Sie Ihren Vornamen ein" },
    { "Lastname_LABEL", "Nachname" },
    { "Lastname_TOOLTIP", "Bitte geben Sie Ihren Nachnamen ein." },
    { "Phone_LABEL", "Telefonnummer" },
    { "Phone_TOOLTIP", "Bitte geben Sie Ihre Telefonnummer ein" },
    { "State_LABEL", "Bundesland" },
    {
      "State_TOOLTIP",
      "Bitte geben Sie das Bundesland ein in dem Sie wohnhaft sind"
    },
    { "Userid_LABEL", "Anwendername" },
    { "Userid_TOOLTIP", "Bitte geben Sie Ihren Anwendernamen ein." },
    { "Zip_LABEL", "PLZ" },
    { "Zip_TOOLTIP", "Bitte geben Sie Ihre Postleitzahl ein." }
  };

  public AccountImplMsgBundle_de() {
  }

  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }
}
