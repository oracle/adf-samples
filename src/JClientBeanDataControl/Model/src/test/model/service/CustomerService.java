/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.service;
import test.model.beans.Customer;
import test.model.beans.Order;

public class CustomerService  {
  public CustomerService() {
  }
  public Order[] findAllOrdersByCustomer(Customer cust) {
    if (cust == null) return null;
    Order[] orders = null;
    if (cust.getId() == 1) {
      orders = new Order[3];
      orders[0] = new Order();
      orders[0].setId(12345);
      orders[1] = new Order();
      orders[1].setId(45353);
      orders[2] = new Order();
      orders[2].setId(33342);      
    }
    else if (cust.getId() == 2) {
      orders = new Order[2];
      orders[0] = new Order();
      orders[0].setId(6665);
      orders[1] = new Order();
      orders[1].setId(34334);
    }
    return orders;
  }
}
