/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package paramform.view.backing;
import oracle.jbo.Row;
import paramform.view.util.EL;
public class DynamicParameterForm {
  public DynamicParameterForm() {
  }
  public String onParameterFormSubmitButtonPressed() {
    /*
     * Illustrate accessing the user-entered values from the
     * transient view object "UserValue" attributes in each row.
     */
    Row[] paramFormItemRows = (Row[])EL.get("#{bindings.ParameterFormItemsIterator.allRowsInRange}");
    for (Row paramFormItemRow : paramFormItemRows) {
      String parameterName  = (String)paramFormItemRow.getAttribute("ParameterName");
      String parameterValue = (String)paramFormItemRow.getAttribute("UserValue");
      System.out.println(parameterName+"="+parameterValue);
    }
    return "Submit";
  }
}
