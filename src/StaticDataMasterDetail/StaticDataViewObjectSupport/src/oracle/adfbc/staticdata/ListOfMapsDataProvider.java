/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfbc.staticdata;

import java.util.List;
import java.util.Map;

public interface ListOfMapsDataProvider {
  List<String> getAttributeNames();
  List<Map<String,Object>> getData();
  List<Map<String,Object>> getData(Map<String,Object> filterCriteria);
}
