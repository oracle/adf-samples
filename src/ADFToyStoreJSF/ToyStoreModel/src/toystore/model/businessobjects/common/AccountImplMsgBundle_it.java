/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/AccountImplMsgBundle_it.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import oracle.jbo.common.JboResourceBundle;
import toystore.fwk.exceptions.ErrorMessages;
/**
 * Italian translations of Account entity object control hints
 * Traduzioni italiane dei control hint dell'entity object Account
 */
public class AccountImplMsgBundle_it extends AccountImplMsgBundle {
  static final Object[][] sMessageStrings = {
    { "Country_Rule_0", "Codice di paese inesistente" },
    {
      ErrorMessages.ENTITY_ALREADY_EXISTS,
      "Un altro utente ha già scelto questo nome. Prova un altro."
    },
    { "Addr1_LABEL", "Indirizzo" },
    { "Addr1_TOOLTIP", "Inserisci il tuo indirizzo" },
    { "City_LABEL", "Città" },
    { "City_TOOLTIP", "Inserisci il nome della città in cui abiti" },
    { "Country_LABEL", "Paese" },
    { "Country_TOOLTIP", "Inserisci il nome del paese in cui abiti" },
    { "Email_LABEL", "Email" },
    { "Email_TOOLTIP", "Inserisci il tuo indirizzo email" },
    { "Firstname_LABEL", "Nome" },
    { "Firstname_TOOLTIP", "Inserisci il tuo nome" },
    { "Lastname_LABEL", "Cognome" },
    { "Lastname_TOOLTIP", "Inserisci il tuo cognome" },
    { "Phone_LABEL", "Numero Telefonico" },
    { "Phone_TOOLTIP", "Inserisci il tuo numero telefonico" },
    { "State_LABEL", "Stato/Provincia" },
    { "State_TOOLTIP", "Inserisci il nome dello stato o provincia in cui abiti" },
    { "Userid_LABEL", "Username" },
    { "Userid_TOOLTIP", "Inserisci il tuo username" },
    { "Zip_LABEL", "CAP" },
    { "Zip_TOOLTIP", "Inserisci il tuo codice di avviamento postale" }
  };

  public AccountImplMsgBundle_it() {
  }

  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }
}
