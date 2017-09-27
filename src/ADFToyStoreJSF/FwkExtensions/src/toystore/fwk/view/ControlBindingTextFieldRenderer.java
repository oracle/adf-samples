/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/view/ControlBindingTextFieldRenderer.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.view;
import oracle.jbo.Row;
import oracle.jbo.html.BindingContainerDataSource;
import oracle.jbo.uicli.binding.JUControlBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;
import oracle.jdeveloper.html.TextField;
/**
 * Workaround for Bug 3703925
 *   adf:inputrender TextField Renderer Doesn't See Pending Bad Value
 *   
 * This class extends the default TextField renderer to pull the value
 * for the field from the ADF control value binding instead of directly
 * from the underlying ADF Row. It's the control binding that caches the
 * currently invalid value that the user needs to see in order to correct.
 */
public class ControlBindingTextFieldRenderer extends TextField  {
  /**
   * Overridden framework method to change the way the HTML value of
   * the control is determined.
   * @param row Current Row of data values.
   * @return String value to use as value of the control.
   */
  protected String getHTMLValue(Row row) {
    if (getDatasource().isBindingContainerDataSource()) {
      BindingContainerDataSource ds = (BindingContainerDataSource)getDatasource();
      JUControlBinding b = ds.getControlBinding();
      if (b instanceof JUCtrlValueBinding) {
        Object value = ((JUCtrlValueBinding)b).getInputValue();
        return value != null ? value.toString() : null;
      }
    }
    return super.getHTMLValue(row);
  }   
}
