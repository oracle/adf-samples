/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.beans;
import java.util.Date;

public class Order  {
  int id;
  float total;
  Date datePlaced;
  public Order() {
  }


  public void setId(int id) {
    this.id = id;
  }


  public int getId() {
    return id;
  }


  public void setTotal(float total) {
    this.total = total;
  }


  public float getTotal() {
    return total;
  }


  public void setDatePlaced(Date datePlaced) {
    this.datePlaced = datePlaced;
  }


  public Date getDatePlaced() {
    return datePlaced;
  }
}
