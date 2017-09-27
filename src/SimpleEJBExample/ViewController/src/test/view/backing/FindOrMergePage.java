/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import java.util.Date;

import test.view.backing.util.ExampleAppBackingBeanBase;
import test.view.util.EL;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import test.model.Contact;

public class FindOrMergePage extends ExampleAppBackingBeanBase {
  // ------------------------- Custom Logic  ----------------------
   /**
    * If we're not performing a postback, and the
    * clearPageDefinitionVariablesOnPageLoad property in the UserInfo
    * session-scoped managed bean is true, then programmatically clear
    * the page definition variables.
    */
   public void onPageLoad() {
     super.onPageLoad();
     if (!isPostback() && shouldClearVariablesOnPageLoad()) {
       clearVariable("findOrMergeContact_email");
       clearVariable("findOrMergeContact_name");
     }
   }
   /**
    * If we're performing a postback and the
    * clearPageDefinitionVariablesOnSubmit property in the UserInfo
    * session-scoped managed bean is true, then programmatically clear
    * the page definition variables before rendering the page. It's important
    * to do it after the prepareModel phase so that the values supplied by
    * the user in the fields on the page get applied and used in the
    * call to the method action binding when the Execute operation on the
    * method iterator is triggered by the user's button press.
    */
   public void onPagePreRender() {
     super.onPagePreRender();
     if (isPostback() && shouldClearVariablesOnSubmit()) {
       clearVariable("findOrMergeContact_email");
       clearVariable("findOrMergeContact_name");
     }
   }
  /**
   * Action Method for (OK) button to add a contact
   */   
  public String onClickOK() {
    Date currentDate = new Date();
    Contact foundOrMergedContact = (Contact)executeQueryForMethodIterator("findOrMergeContactIter");
    /*
     * If Contact returned has an updated date greater than the current date
     * before the method was invoked, then we know it was added or updated.
     */
    if (foundOrMergedContact.getUpdated().compareTo(currentDate) >= 0) {
      handleContactModified(foundOrMergedContact);
    }
    return null;
  }
  private boolean shouldClearVariablesOnPageLoad() {
    return EL.test("#{UserInfo.clearPageDefinitionVariablesOnPageLoad}");
  }
  private boolean shouldClearVariablesOnSubmit() {
    return EL.test("#{UserInfo.clearPageDefinitionVariablesOnSubmit}");
  }
  
}
