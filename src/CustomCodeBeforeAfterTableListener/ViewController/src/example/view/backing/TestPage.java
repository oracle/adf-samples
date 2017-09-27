/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view.backing;

import oracle.adf.view.faces.event.SelectionEvent;

import test.view.util.EL;

public class TestPage {
/*
 * Normally when you drop an ADF Table, it gets configured to have a
 * selectionListener property whose EL expression references the
 * makeCurrent() method of the collectionModule property of the
 * ADF table binding object. For example, for a table binding named
 * "Employees" that EL expression looks like:
 * 
 *    #{bindings.Employees.collectionModel.makeCurrent}
 *    
 * Here, in the TestPage.jspx we've changed the selectionListener property
 * to be bound to this method in the backing bean using the EL expression:
 * 
 *    #{TestPage.onTableSelectionChanged}
 *    
 * and then we have custom code before and after the call to the default
 * makeCurrent() method, which we invoke using a helper method in the 'EL'
 * utility class (in the FwkExtensions project in this sample).
 */
  public void onTableSelectionChanged(SelectionEvent selectionEvent) {
    System.out.println("=======> Custom backing bean code before default selection event listener");
    EL.invokeMethod("#{bindings.Employees.collectionModel.makeCurrent}",
                    SelectionEvent.class,selectionEvent);
    System.out.println("=======> Custom backing bean code after default selection event listener");
  }
}
