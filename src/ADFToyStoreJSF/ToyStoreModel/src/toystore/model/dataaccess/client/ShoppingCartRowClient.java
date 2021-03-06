/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.model.dataaccess.client;
import oracle.jbo.client.remote.RowImpl;
//  ---------------------------------------------------------------
//  ---    File generated by Oracle Business Components for Java.
//  ---------------------------------------------------------------

public class ShoppingCartRowClient extends RowImpl  {
  /**
   * 
   *  This is the default constructor (do not remove)
   */
  public ShoppingCartRowClient() {
  }

  public String getItemid() {
    return (String)getAttribute("Itemid");
  }

  public void setItemid(String value) {
    setAttribute("Itemid", value);
  }

  public Long getQuantity() {
    return (Long)getAttribute("Quantity");
  }

  public void setQuantity(Long value) {
    setAttribute("Quantity", value);
  }

  public oracle.jbo.domain.Number getListprice() {
    return (oracle.jbo.domain.Number)getAttribute("Listprice");
  }

  public void setListprice(oracle.jbo.domain.Number value) {
    setAttribute("Listprice", value);
  }

  public String getName() {
    return (String)getAttribute("Name");
  }

  public void setName(String value) {
    setAttribute("Name", value);
  }

  public Double getExtendedTotal() {
    return (Double)getAttribute("ExtendedTotal");
  }

  public void setExtendedTotal(Double value) {
    setAttribute("ExtendedTotal", value);
  }

  public String getInStock() {
    return (String)getAttribute("InStock");
  }

  public void setInStock(String value) {
    setAttribute("InStock", value);
  }













}
