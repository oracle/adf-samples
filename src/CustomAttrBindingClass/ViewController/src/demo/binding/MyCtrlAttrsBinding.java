/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.binding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.jbo.uicli.binding.JUCtrlAttrsBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;
public class MyCtrlAttrsBinding extends JUCtrlAttrsBinding {
  public MyCtrlAttrsBinding(Object control, DCIteratorBinding iterBinding,
    String[] attrNames) {
    super(control, iterBinding, attrNames);
  }

  public void setInputValue(JUCtrlValueBinding binding, int index, Object value) {
    System.out.println("### Custom setInputValue Called ###");
    super.setInputValue(binding, index, value);
  }
}
