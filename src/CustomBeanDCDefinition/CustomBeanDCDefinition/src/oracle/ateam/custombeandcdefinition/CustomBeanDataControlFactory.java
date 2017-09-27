/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.custombeandcdefinition;

import oracle.adfdt.model.datacontrols.AdapterDataControlObjectFactory;
import oracle.adfdt.model.datacontrols.JUDTAdapterDataControl;


public class CustomBeanDataControlFactory
  implements AdapterDataControlObjectFactory
{
  public CustomBeanDataControlFactory()
  {
    super();
  }

  @Override
  public Object create(String className)
  {
    try
    {
      Class dc = JUDTAdapterDataControl.class.getClassLoader().loadClass("oracle.adf.model.adapter.bean.BeanDCDefinition");
      return dc.newInstance();
    }
    catch (ClassNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}

