/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.srdemo.model.frameworkExt;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import java.util.ArrayList;
import java.util.List;
/**
 * Customization of the SRStaticDataViewObjectImpl class for view
 * object components to read data from a Java *.properties file instead
 * of the database.
 *
 * This can be useful for static lookup lists which you would still
 * like to drive out of a file in case you add or fix an entry every
 * once in a while without having to recompile code to do so. Similar
 * techniques could be used to read data from XML or other formats as well.
 *
 * To create a property-file based lookup VO:
 *
 * 1. Create a new view object, and define two transient attributes
 *    (e.g. attributes "Code", and "Description")
 *
 * 2. On the Java tab, indicate this class as the ViewObject base class
 *    (there's no need to actually generate a custom VOImpl subclass)
 *
 * 3. Create a text file named YourViewObject.properties in the same
 *    directory as your YourViewObject.xml with the following format
 *    _______________________________________________________________
 *    |# This is the property file format. Comments like this are ok
 *    |CA=California
 *    |NV=Nevada
 *    |______________________________________________________________
 *
 * @author Steve Muench
 * $Id: SRPropertiesFileViewObjectImpl.java,v 1.3 2006/05/11 00:32:03 steve Exp $
 */
public class SRPropertiesFileViewObjectImpl extends SRStaticDataViewObjectImpl {
  /**
   * Read code=Description lines from property file related to this view object
   *
   * Skip over lines beginning with a hash/pound-sign to allow comments
   *
   * We implement our own file reading logic here so that we can
   * preserve the ordering of the lines in the file. Reading the properties
   * using the java.util.Properties object loads them into a HashTable and
   * you lose the ordering.
   */
  private synchronized String[][] loadDataFromPropertiesFile() {
    String propertyFile = 
      getViewDef().getFullName().replace('.', '/') + ".properties";
    List codesAndDescriptionsList = new ArrayList(20);
    try {
      InputStream is = 
        Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFile);
      LineNumberReader lnr = 
        new LineNumberReader(new InputStreamReader(is));
      String line = null;
      while ((line = lnr.readLine()) != null) {
        line.trim();
        int eqPos = line.indexOf('=');
        if ((eqPos >= 1) && (line.charAt(0) != '#')) {
          codesAndDescriptionsList.add(new String[]{
                     line.substring(0, eqPos),
                     line.substring(eqPos + 1)});
        }
      }
      lnr.close();
      is.close();
    } catch (IOException iox) {
      iox.printStackTrace();
      return new String[0][0];
    }
    String[][] ret = new String[codesAndDescriptionsList.size()][2];
    return (String[][])codesAndDescriptionsList.toArray(ret);
  }

  /**
   * Initialize static data by loading data from a properties file.
   */
  protected void initializeStaticData() {
    setCodesAndDescriptions(loadDataFromPropertiesFile());
  }
}
