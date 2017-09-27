/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.emg.adf.sample.model.fwk;

import oracle.jbo.RowSetIterator;
import oracle.jbo.server.ViewRowImpl;

/**
 * Base class to implement sorting and filtering by choice display value
 * @author Steven Davelaar
 */
public class BaseViewRowImpl
  extends ViewRowImpl
{
  public BaseViewRowImpl()
  {
    super();
  }
  
  /**
   * Public method to get list binding rowset iterator for an attribute.
   * Standard framework-provided method findListBindingRSI is protected
   * @param attrName
   * @param lovName
   * @return
   */
  public RowSetIterator getListBindingRSI(String attrName, String lovName)
  {
    return findListBindingRSI(attrName, lovName, null);
  }
}
