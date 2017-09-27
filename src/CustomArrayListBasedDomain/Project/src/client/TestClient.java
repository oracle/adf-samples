/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package client;

import model.types.Account;
import model.types.common.ListOfAccounts;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

public class TestClient {
  public static void main(String[] args) {
    String amDef = "model.TestModule";
    String config = "TestModuleLocal";
    ApplicationModule am = 
      Configuration.createRootApplicationModule(amDef, config);
    ViewObject vo = am.findViewObject("ExampleTransientView");
    // Insert a first row in the transient view object.
    Row r = vo.createRow();
    vo.insertRow(r);
    r.setAttribute("Id", "SomeValueForKey1");
    ListOfAccounts loa = 
      new ListOfAccounts(new Account[] { new Account("123", "Oracle Corporation"), 
                                         new Account("456", 
                                                     "Microsoft Corporation"), 
                                         new Account("789", 
                                                     "IBM Corporation") });
    r.setAttribute("ListOfAccounts", loa);
    // Insert a second row in the transient view object at the end.
    r = vo.createRow();
    vo.last();
    vo.next();
    vo.insertRow(r);
    r.setAttribute("Id", "SomeValueForKey2");
    loa = 
        new ListOfAccounts(new Account[] { new Account("987", "Adobe Corporation"), 
                                           new Account("654", 
                                                       "Salesforce.com"), 
                                           new Account("321", "Quicken") });
    r.setAttribute("ListOfAccounts", loa);
    showAccountListContents(vo, "Before passivation");
    // Take a state passivation snapshot and remember thd id
    int passivationSnapshotId = am.passivateState(null, 0);
    // Release the application module
    Configuration.releaseRootApplicationModule(am, true);
    // Acquire a new application module instance
    ApplicationModule anotherAm = 
      Configuration.createRootApplicationModule(amDef, config);
    // Activate the passivate state snapshot
    anotherAm.activateState(passivationSnapshotId, null, 0);
    ViewObject anotherVO = anotherAm.findViewObject("ExampleTransientView");
    showAccountListContents(anotherVO, "After activation");
  }
  private static void showAccountListContents(ViewObject vo, String title) {
    System.out.println("--- " + title + " ---");
    vo.reset();
    while (vo.hasNext()) {
      Row accountRow = vo.next();
      System.out.println("In transient row with Id=" + 
                         accountRow.getAttribute("Id") + " we have...");
      ListOfAccounts accounts = 
        (ListOfAccounts)accountRow.getAttribute("ListOfAccounts");
      if (accounts != null) {
        for (Account acc: accounts.toList()) {
          System.out.println("  Account(id=" + acc.getAccountid() + ",name=" + 
                             acc.getAccountname() + ")");
        }
      }
    }
  }
}
