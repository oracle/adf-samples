/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.binding;
import oracle.jbo.uicli.binding.JUBindingDefFactoryImpl;
import oracle.adf.model.binding.DCDefBase;

public class MyCustomBindingDefFactoryImpl extends JUBindingDefFactoryImpl  {
  public DCDefBase createControlDef(String subType) {
    if (DCDefBase.PNAME_TextField.equals(subType)) {
      return new MyCtrlAttrsDef();
    }
    return super.createControlDef(subType);
  }
}
