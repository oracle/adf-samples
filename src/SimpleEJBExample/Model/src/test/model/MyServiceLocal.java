/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import test.model.exceptions.ContactAlreadyExistsException;

@Local
public interface MyServiceLocal {
  public List<Contact> findAllContacts();
  public List<Contact> findContactsByName(String name);
  public Contact findOrMergeContact(Contact c) throws ContactAlreadyExistsException;
  public Contact findOrMergeContact(String email, String name) throws ContactAlreadyExistsException;  
  public Contact updateContact(Contact c);
  public Contact addContact(Contact c) throws ContactAlreadyExistsException;
  public void removeContact(Contact c);
}
