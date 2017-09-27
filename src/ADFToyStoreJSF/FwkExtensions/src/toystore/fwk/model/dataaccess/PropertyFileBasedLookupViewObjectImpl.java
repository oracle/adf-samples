/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/dataaccess/PropertyFileBasedLookupViewObjectImpl.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.model.dataaccess;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
/**
 * Customization of the ADF framework base class for view object components
 * to read data from a Java *.properties file instead of the database.
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
 */
public class PropertyFileBasedLookupViewObjectImpl extends ViewObjectImpl {
  int rows = -1;
  private Object[] codes = null;
  private Object[] descriptions = null;

  /**
   * Overridden framework method
   *
   * This is the framework "pinch point" for all query execution.
   * Since our data is static once we read it in a component instantiation
   * time, we don't really need to perform any query, but we need to
   * call the super to allow other framework setup for the rowset to be
   * done correctly. The fact that we have nulled out all traces of a query
   * in the create() method will mean that the framework doesn't actually
   * perform any query during this setup.
   *
   * @param rowset  the query collection about to execute the query.
   * @param params  the bind parameters that will be applied to the query.
   * @param noUserParams  the number of user bind parameters supplied
   *                      through the setWhereClauseParam calls.
   */
  protected void executeQueryForCollection(Object rowset, Object[] params,
    int noUserParams) {
    // Initialize our fetch position for the query collection
    setFetchPos(rowset, 0);
    super.executeQueryForCollection(rowset, params, noUserParams);
  }
  /**
   * Overridden framework method
   * 
   * The framework calls this method to support the hasNext() method on
   * the rowset iterator for a rowset created from this view object.
   * We return true if our fetchPosition is still less than the rows in
   * our in-memory data arrays
   * 
   * @param rowset The current query collection based on this view object
   * @return  true if there are more rows still to fetch.
   */
  protected boolean hasNextForCollection(Object rowset) {
    return getFetchPos(rowset) < rows;
  }
  /**
   * Overridden framework method.
   * 
   * Populates the "fetched" data for one row when the framework asks us to.
   * We get the data from the "codes" array and the "descriptions" array
   * to populate the first (position 0) and second attribute slots in the
   * view object row. We ignore the resultSet passed in which will be null
   * since there is no real query going on here to get the data.
   * 
   * @param rowset The current query collection based on this view object
   * @param resultSet The JDBC result set being used to fetch data from
   * @return Next view row fetched from this rowset 
   */
  protected ViewRowImpl createRowFromResultSet(Object rowset,
    ResultSet resultSet) {
    // Create and populate a new row 
    ViewRowImpl r = createNewRowForCollection(rowset);
    int pos = getFetchPos(rowset);
    populateAttributeForRow(r, 0, codes[pos]);
    populateAttributeForRow(r, 1, descriptions[pos]);
    setFetchPos(rowset, pos + 1);
    return r;
  }
  /**
   * Overridden framework method.
   *
   * When this VO component is created, we load the data from properties file.
   *
   * This method is called once when the VO is first instantiated.
   */
  protected void create() {
    super.create();
    // Setup string arrays of codes and values from VO custom properties
    loadDataFromPropertiesFile();
    rows = (codes != null) ? codes.length : 0;
    // Wipe out all traces of a query for this VO
    getViewDef().setQuery(null);
    getViewDef().setSelectClause(null);
    setQuery(null);
  }
  /**
   * Set the current fetch position for the query collection
   *
   * Since one view object can be used to create multiple rowsets, we
   * need to keep track our current position in the rowset in the
   * per-rowset "user data" that the framework allows us to store as
   * context information.
   */
  private void setFetchPos(Object rowset, int pos) {
    if (pos == rows) {
      setFetchCompleteForCollection(rowset, true);
    }
    setUserDataForCollection(rowset, new Integer(pos));
  }
  /**
   * Get the current fetch position for the query collection
   *
   * This returns the fetch position for the current rowset in question
   * by retrieve the user data we stored in the context.
   */
  private int getFetchPos(Object rowset) {
    return ((Integer) getUserDataForCollection(rowset)).intValue();
  }
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
  private synchronized void loadDataFromPropertiesFile() {
    String propertyFile = getViewDef().getFullName().replace('.', '/') +
      ".properties";
    ArrayList codesList = new ArrayList(20);
    ArrayList descriptionList = new ArrayList(20);
    try {
      InputStream is = Thread.currentThread().getContextClassLoader()
                             .getResourceAsStream(propertyFile);
      LineNumberReader lnr = new LineNumberReader(new InputStreamReader(is));
      String line = null;
      while ((line = lnr.readLine()) != null) {
        line.trim();
        int eqPos = line.indexOf('=');
        if ((eqPos >= 1) && (line.charAt(0) != '#')) {
          codesList.add(line.substring(0, eqPos));
          descriptionList.add(line.substring(eqPos + 1));
        }
      }
      lnr.close();
      is.close();
    }
    catch (IOException iox) {
      iox.printStackTrace();
      return;
    }
    codes = codesList.toArray();
    descriptions = descriptionList.toArray();
  }
  /**
   * Overridden framework method.
   * 
   * If anyone asks for a getEstimatedRowCount() on this VO, we'll return
   * the number of rows we read from the property file.
   * 
   * @param viewRowSet The current set of view rows
   * @return Count of rows read from the properties file
   */
  public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
    return rows;
  }
}
