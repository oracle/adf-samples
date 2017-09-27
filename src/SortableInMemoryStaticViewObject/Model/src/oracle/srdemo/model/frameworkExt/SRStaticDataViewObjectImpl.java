/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.srdemo.model.frameworkExt;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;

/**
 * Customization of the ADF framework base class for view object components
 * to read data from a static source like an in-memory array.
 *
 * This can be useful for static lookup lists.
 *
 * To create a static lookup VO:
 *
 * 1. Create a new view object, and define two transient attributes
 *    (e.g. attributes "Code", and "Description")
 *
 * 2. On the Java tab, indicate this class as the ViewObject base class
 *
 * 3. In your custom ViewObjectImpl subclass, override the 
 *    initializeStaticData() method and call setCodesAndDescriptions()
 *    with a 2-dimensional String array like this:
 *    _______________________________________________________________
 *    | protected void initializeStaticData() {
 *    |   setCodesAndDescriptions(new String[][]{
 *    |     {"Code1","Description1"},
 *    |     {"Code2","Description2"}
 *    |   });
 *    |  }
 *    |______________________________________________________________
 * 
 * @author Steve Muench
 * $Id: SRStaticDataViewObjectImpl.java,v 1.4 2006/05/11 00:32:03 steve Exp $
 */
public class SRStaticDataViewObjectImpl extends ViewObjectImpl {
  private boolean initialized = false;
  private static final int CODE = 0;
  private static final int DESCRIPTION = 1;
  int rows = -1;
  private String[][] codesAndDescriptions = null;

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
   if (!initialized) {
       initialize();
       initialized = true;
   }
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
   * @param rs The JDBC result set being used to fetch data from
   * @return Next view row fetched from this rowset
   */
  protected ViewRowImpl createRowFromResultSet(Object rowset, 
                                               ResultSet rs) {
    // Create and populate a new row 
    ViewRowImpl r = createNewRowForCollection(rowset);
    int pos = getFetchPos(rowset);
    populateAttributeForRow(r, 0, codesAndDescriptions[pos][CODE]);
    populateAttributeForRow(r, 1, codesAndDescriptions[pos][DESCRIPTION]);
    setFetchPos(rowset, pos + 1);
    return r;
  }

  /**
   * Set the static code and description data for this view object.
   * This is expected to be called from initializeStaticData() as part
   * of the view object creation.
   * @param codesAndDescriptions array of {value,description} pairs
   */
  protected void setCodesAndDescriptions(String[][] codesAndDescriptions) {
    this.codesAndDescriptions = codesAndDescriptions;
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
  }
  
 /*
  * Moved from create() method since create() is not correctly invoked
  * when there is no transaction.
  */
  private void initialize() {
      initializeStaticData();
      rows = (codesAndDescriptions != null) ? codesAndDescriptions.length : 0;
      // Wipe out all traces of a query for this VO
      getViewDef().setQuery(null);
      getViewDef().setSelectClause(null);
      setQuery(null);
      setManageRowsByKey(true);
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

  /**
   * Override to initialize the static data for this view object.
   */
  protected void initializeStaticData() {
    setCodesAndDescriptions(new String[][]{
      {"Code1","Description1"},
      {"Code2","Description2"}
    });
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
    return ((Integer)getUserDataForCollection(rowset)).intValue();
  }

    public void setOrderByClause(String string) {
        super.setOrderByClause(string);
    }

    public void setSortBy(String string) {
        super.setSortBy(string);
    }
}
