/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfbc.staticdata.dataproviders;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.adfbc.staticdata.dataproviders.util.CSV;

public class CSVFileDataProvider extends ListOfMapsDataProviderImplBase {
  String resourceName;
  Map<String,String> filterNameToAttributeMap = null;
  public CSVFileDataProvider(String resourceName) {
    this.resourceName = resourceName;
  }
  private synchronized List<Map<String,Object>> loadDataFromCsvFile() {
    List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>(20);
    try {
      CSV csv = new CSV();
      boolean readFirstNonCommentLine = false;
      InputStream is = 
        Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
      LineNumberReader lnr = 
        new LineNumberReader(new InputStreamReader(is));
      String line = null;
      while ((line = lnr.readLine()) != null) {
        line.trim();
        if ((line.length() > 0 && line.charAt(0) != '#')) {
          List<String> attributesInLine = csv.parse(line);
          if (!readFirstNonCommentLine) {
            readFirstNonCommentLine = true;
            attributeNames = new ArrayList<String>(attributesInLine.size());
            attributeNames.addAll(attributesInLine);
          }
          else {
            Map<String,Object> row = new HashMap<String,Object>(attributeNames.size());
            int index=0;
            for (String attrName : attributeNames) {
              row.put(attrName,attributesInLine.get(index++));              
            }
            rows.add(row);
          }
        }
      }
      lnr.close();
      is.close();
    } catch (IOException iox) {
      iox.printStackTrace();
      return new ArrayList<Map<String,Object>>();
    }
    return rows;
  }
  
  List<Map<String,Object>> allData = null;

  @Override
  protected List<Map<String,Object>> getAllData() {
    if (allData == null) {
      allData = loadDataFromCsvFile();
    }
    return allData;
  }
  List<String> attributeNames;
  public List<String> getAttributeNames() {
    if (attributeNames == null) {
      getAllData();
    }
    return attributeNames;
  }
}

