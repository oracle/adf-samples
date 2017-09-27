/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.dataaccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import test.model.Contact;
import test.model.exceptions.ContactAlreadyExistsException;

/**
 * Simple Address Book Data Manager
 * ================================
 * 
 * This class is used as a substitute for a real persistence store
 * for demonstration purposes.
 * 
 */
public class SimpleAddressBookDataManager {
  private static SimpleAddressBookDataManager singleton;
  private List<Contact> contacts = new ArrayList<Contact>();
  private SimpleAddressBookDataManager() {
    contacts.add(new Contact("Steve", "steve@yahoo.com"));
    contacts.add(new Contact("Charles", "cyoung@global.tt.net"));
    contacts.add(new Contact("Karl", "kheinz@acme.org"));
    contacts.add(new Contact("Mike", "mike_meier@yahoo.com"));
    contacts.add(new Contact("Yvonne", "yvonne_yvonne@gmail.com"));
    contacts.add(new Contact("Sung", "superstar001@yahoo.com"));
    contacts.add(new Contact("Shailesh", "spatel@acme.org"));
    contacts.add(new Contact("John", "jjb@cablenet.net"));
    contacts.add(new Contact("Ricky", "rmartin@acme.org"));
    contacts.add(new Contact("Shaolin", "shaolins@gmail.com"));
    contacts.add(new Contact("Olga", "olga077@yahoo.com"));
    contacts.add(new Contact("Ron", "reggerts@acme.org"));
    contacts.add(new Contact("Juan", "jperez@acme.org"));
    contacts.add(new Contact("Uday", "udaykumar@adcglobal.net"));
    contacts.add(new Contact("Aminur", "aminur@aaaminur.com"));
    contacts.add(new Contact("Sathish", "sparekh@acme.org"));
    contacts.add(new Contact("Kal", "kalyan.krishnan@worldwide.net"));
    contacts.add(new Contact("Prakash", "prakash01@gmail.com"));
  }
  public static SimpleAddressBookDataManager getInstance() {
    if (singleton == null) {
      singleton = new SimpleAddressBookDataManager();
    }
    return singleton;
  }
  public List<Contact> findAllContacts() {
    return findContactsByName(null);
  }
  public List<Contact> findContactsByName(String name) {
    String namePattern = 
      ".*" + (name != null ? name.toUpperCase() : "") + ".*";
    List<Contact> matches = new ArrayList();
    for (Contact c: contacts) {
      String contactName = c.getName();
      if (contactName == null) {
        contactName = "";
      }
      if (Pattern.matches(namePattern, contactName.toUpperCase())) {
        Contact copyOfContactBean = new Contact(c);
        matches.add(copyOfContactBean);
      }
    }
    return matches;
  }
  public Contact updateContact(Contact c) {
    Contact curContact = findContactByEmail(c.getEmail());
    curContact.setName(c.getName());
    curContact.setUpdated(new Date());
    return curContact;
  }
  public Contact addContact(Contact c) throws ContactAlreadyExistsException {
    Contact existingContact = findContactByEmail(c.getEmail());
    if (existingContact != null) {
      throw new ContactAlreadyExistsException("User with email " + 
                                              c.getEmail() + 
                                              " already exists.");
    }
    Contact newContact = new Contact(c.getName(), c.getEmail());
    contacts.add(newContact);
    return newContact;
  }
  public void removeContact(Contact c) {
    Contact contactToRemove = findContactByEmail(c.getEmail());
    if (contactToRemove != null) {
      contacts.remove(contactToRemove);
    }
  }
  public Contact findOrMergeContact(Contact c) throws ContactAlreadyExistsException {
    Contact existingContact = findContactByEmail(c.getEmail());
    existingContact = existingContact != null ?
                      updateContactIfNameChangedToNonNullValue(c)        :
                      addContact(c);
    return existingContact;
  }
  private Contact findContactByEmail(String email) {
    for (Contact c: contacts) {
      if (c.getEmail().equals(email)) {
        return c;
      }
    }
    return null;
  }
  private Contact updateContactIfNameChangedToNonNullValue(Contact c) {
    Contact curContact = findContactByEmail(c.getEmail());
    String newName = c.getName();
    if (!isNullOrEmpty(newName) && valuesDiffer(curContact.getName(),newName)) {
      curContact.setName(c.getName());
      curContact.setUpdated(new Date());
    }
    return curContact;
  }  
  private boolean valuesDiffer(Comparable c1, Comparable c2) {
    return (c1 == null && c2 != null) ||
           (c1 != null && c2 == null) ||
           (c1.compareTo(c2) != 0);
  }
  private boolean isNullOrEmpty(String s) {
    return s == null || s.length() == 0;
  }
}
