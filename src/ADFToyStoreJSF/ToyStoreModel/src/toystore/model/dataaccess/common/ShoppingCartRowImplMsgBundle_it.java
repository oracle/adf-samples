/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/dataaccess/common/ShoppingCartRowImplMsgBundle_it.java,v 1.1.1.1 2006/01/26 21:47:22 steve Exp $
package toystore.model.dataaccess.common;
public class ShoppingCartRowImplMsgBundle_it
  extends ShoppingCartRowImplMsgBundle {
  static final Object[][] sMessageStrings = {
    { "InStock_LABEL", "Disponibilità" },
    { "Name_LABEL", "Articolo" },
    { "Listprice_LABEL", "Prezzo" },
    { "Description_LABEL", "Descrizone" },
    { "Itemid_LABEL", "Codice" },
    { "ExtendedTotal_LABEL", "Totale" },
    { "Quantity_LABEL", "Quantità" }
  };

  /**
   *
   * This is the default constructor (do not remove)
   */
  public ShoppingCartRowImplMsgBundle_it() {
  }

  /**
   *
   * @return an array of key-value pairs.
   */
  public Object[][] getContents() {
    return super.getMergedArray(sMessageStrings, super.getContents());
  }
}
