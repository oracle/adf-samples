/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.binding;
import oracle.jbo.uicli.binding.JUCtrlAttrsDef;
import oracle.adf.model.binding.DCControlBinding;
import oracle.adf.model.binding.DCBindingContainer;

public class MyCtrlAttrsDef extends JUCtrlAttrsDef  {
  protected DCControlBinding createControlBindingInstance(Object control, DCBindingContainer formBnd) {
      return new MyCtrlAttrsBinding(control, 
                                    (getIterBindingName() != null)
                                       ? this.getIterBinding(formBnd)
                                       : null, 
                                    getAttrNames());  }
}
