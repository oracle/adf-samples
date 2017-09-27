/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package davelaar.demo.model.base;

import oracle.jbo.domain.Number;

public interface BaseApplicationModule
{
  void queryByKeyValue(String viewObjectUsage, String keyValue);

  void queryByKeyValue(String viewObjectUsage, Number keyValue);
}
