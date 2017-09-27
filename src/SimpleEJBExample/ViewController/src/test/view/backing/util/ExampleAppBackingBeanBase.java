/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing.util;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.RowSetIterator;

import test.model.Contact;

import test.view.util.EL;
import test.view.util.OnPageLoadBackingBeanBase;

/**
 * Application-specific base class for backing beans
 */
public class ExampleAppBackingBeanBase extends OnPageLoadBackingBeanBase {
  /**
   * When a Contact gets modified, show a confirmation message and put
   * the Contact on the request to serve the dual purpose of flagging
   * (by its presence) the fact that a contact was modified, and allowing
   * the page handling the request to reference any of its attributes if 
   * desired.
   * 
   * @param c Contact that was modified
   */
  protected void handleContactModified(Contact c) {
    addJSFMessage("Changes saved for contact " + c.getEmail());
    setModifiedContact(c);
  }  
  /**
   * When a Contact gets deleted, show a confirmation message
   * 
   * @param c Contact that was modified
   */
  protected void handleContactDeleted(Contact c) {
    String emailOfDeletedContact = null;
    if (c != null) {
      emailOfDeletedContact = c.getEmail();
    }
    addJSFMessage("Contact "+emailOfDeletedContact+" deleted");
  }
  /**
   * Helper method to set a modified contact as the current row in the
   * iterator whose iterator binding name is passed in. 
   * 
   * @param iterName Name of iterator binding 
   */
  protected void setModifiedContactAsCurrentIfNeededInIterator(String iterName) {
    Contact c = getModifiedContact();
    if (c != null) {
      setCurrentRowWithKeyValueIfItExistsInIterator(iterName,c.getEmail());
    }
  }
  /**
   * Return the Contact stored on the requestScope, if any
   * 
   * @return Contact that was set on the request scope
   */
  protected Contact getModifiedContact() {
    return (Contact)EL.get("#{requestScope.modifiedContact}");
  }
  private void setModifiedContact(Contact c) {
    EL.set("#{requestScope.modifiedContact}",c);
  }
  
}
