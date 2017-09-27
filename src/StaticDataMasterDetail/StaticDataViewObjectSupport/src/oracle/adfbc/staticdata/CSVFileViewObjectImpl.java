/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfbc.staticdata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oracle.adfbc.staticdata.dataproviders.CSVFileDataProvider;

import oracle.jbo.Variable;

/**
 * Customization of the ListOfMapsDataProviderViewObjectImpl class for view
 * object components to read data from a Java *.csv file instead
 * of the database.
 *
 * This can be useful for static lookup lists which you would still
 * like to drive out of a file in case you add or fix an entry every
 * once in a while without having to recompile code to do so. Similar
 * techniques could be used to read data from XML or other formats as well.
 *
 * To create a property-file based lookup VO:
 *
 * 1. Create a new view object, and define your transient attributes
 *
 * 2. On the Java tab, indicate this class as the ViewObject base class
 *    (there's no need to actually generate a custom VOImpl subclass)
 *
 * 3. Create a text file named YourViewObject.csv in the same
 *    directory as your YourViewObject.xml with the following format
 *    _______________________________________________________________
 *    |# This is the property file format. Comments like this are ok
 *    |CA,California
 *    |NV,Nevada
 *    |______________________________________________________________
 *
 * @author Steve Muench
 * $Id: SRPropertiesFileViewObjectImpl.java,v 1.3 2006/05/11 00:32:03 steve Exp $
 */
public class CSVFileViewObjectImpl extends ListOfMapsDataProviderViewObjectImpl {
  ListOfMapsDataProvider dp = null;

  protected ListOfMapsDataProvider getDataProvider() {
    if (dp == null) {
      String resource = getViewDef().getFullName().replace('.', '/')+".csv";
       dp = new CSVFileDataProvider(resource);
    }
    return dp;
  }
}
