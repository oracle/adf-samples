/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.backing;
import demo.view.util.EL;
/**
 * Generally we advise putting business logic like attribute default values
 * into on overridden create() method of your Entity Object as described
 * in section 9.4.1 "Defaulting Values for New Rows at Create Time"
 * of the ADF Developer's Guide for Forms/4GL Developers. Section 9.10,
 * "How to Store Information About the Current User Session" explains how
 * a custom application module method can save data into the per-user 
 * session "user data" hashtable, which entity object business logic like
 * create()-time defaulting can then reference. However this backing bean
 * illustrates a case of controller-layer logic that conditionally
 * returns a default value for the "Loc" binding based on controller-layer
 * flags like the presence of a #{requestScope.creating} attribute, and on
 * the value of a #{sessionScope.locDefaultValue} attribute.
 */
public class Create {
  /**
   * Backing bean property getter method that returns the inputValue of the
   * "Loc" binding, unless requestScope.creating is set.
   */
  public String getLoc() {
    return EL.test("#{requestScope.creating}") ?
           EL.getAsString("#{sessionScope.locDefaultValue}") :
           EL.getAsString("#{bindings.Loc.inputValue}");
  }
  /**
   * Backing bean property setter delegates the set to the "Loc" binding
   */
  public void setLoc(String val) {
    EL.set("#{bindings.Loc.inputValue}",val);
  }
}
