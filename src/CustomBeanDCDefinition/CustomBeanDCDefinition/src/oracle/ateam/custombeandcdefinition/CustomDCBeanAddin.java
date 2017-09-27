/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.custombeandcdefinition;

import oracle.adfdt.model.datacontrols.AdapterSettings;
import oracle.ide.Addin;

public class CustomDCBeanAddin implements Addin
{
  public CustomDCBeanAddin()
  {
    super();
  }

  @Override
  public void initialize()
  {
    CustomBeanDataControlFactory factory = new CustomBeanDataControlFactory();
    AdapterSettings.addFactory(factory);    
  }
}
