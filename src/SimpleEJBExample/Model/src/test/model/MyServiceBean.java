/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Stateless;

import test.model.dataaccess.SimpleAddressBookDataManager;
import test.model.exceptions.ContactAlreadyExistsException;

@Stateless(name = "MyService")
public class MyServiceBean implements MyServiceLocal {
  public MyServiceBean() {
  }
  public List<Contact> findAllContacts() {
    System.out.println("### findAddContacts ###");
    return SimpleAddressBookDataManager.getInstance().findAllContacts();
  }
  public List<Contact> findContactsByName(String name) {
    System.out.println("### findAddContacts(name=" + 
                       (name != null ? name : "<null>") + ") ###");
    return SimpleAddressBookDataManager.getInstance().findContactsByName(name);
  }
  public Contact updateContact(Contact c) {
    System.out.println("### updateContact(c[email=" + c.getEmail() + "]) ###");
    return SimpleAddressBookDataManager.getInstance().updateContact(c);
  }
  public Contact findOrMergeContact(Contact c) throws ContactAlreadyExistsException {
    return SimpleAddressBookDataManager.getInstance().findOrMergeContact(c);
  }
  public Contact findOrMergeContact(String email, String name) throws ContactAlreadyExistsException {
    Contact c = new Contact(name,email);
    System.out.println("### findOrMergeContact(c[email=" + c.getEmail() +","+c.getName()+ "]) ###");
    return SimpleAddressBookDataManager.getInstance().findOrMergeContact(c);
  }
  public Contact addContact(Contact c) throws ContactAlreadyExistsException {
    System.out.println("### addContact(c[email=" + c.getEmail() + "]) ###");
    return SimpleAddressBookDataManager.getInstance().addContact(c);
  }
  public void removeContact(Contact c) {
    System.out.println("### removeContact(c[email=" + c.getEmail() + "]) ###");
    SimpleAddressBookDataManager.getInstance().removeContact(c);
  }
}
