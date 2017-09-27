/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfbc.staticdata;

import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;

import oracle.adfbc.staticdata.dataproviders.ListOfMapsDataProviderImplBase;

import oracle.jbo.AttributeDef;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowMatch;
import oracle.jbo.Variable;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewLink;
import oracle.jbo.ViewObject;
import oracle.jbo.common.JboTypeMap;
import oracle.jbo.domain.TypeFactory;
import oracle.jbo.server.AssociationDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.RowFilter;
import oracle.jbo.server.RowFilterKey;
import oracle.jbo.server.ViewAccessorDef;
import oracle.jbo.server.ViewAttributeDefImpl;
import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewLinkDefImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;

/**
 * Customization of the ADF framework base class for view object components
 * to read data from a static source like an List&lt;List&lt;String>>.
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
 *    initializeStaticData() method and call setRows(List&lt;List&lt;String>>)
 *    with a List containing Lists.
 *    _______________________________________________________________
 *    | protected void initializeStaticData() {
 *    |   setRows(myListOfListsOfString);
 *    |  }
 *    |______________________________________________________________
 * 
 * @author Steve Muench
 * $Id: SRStaticDataViewObjectImpl.java,v 1.4 2006/05/11 00:32:03 steve Exp $
 */
public abstract class ListOfMapsDataProviderViewObjectImpl extends ViewObjectImpl {
    private static final String EQUALS_OPER = "=";
    private static final String LIKE_OPER = "LIKE";

    protected abstract ListOfMapsDataProvider getDataProvider();

    Map<String, String> bindVariableToAttributeNameMapping = null;

    protected Map<String, String> getBindVariableToAttributeNameMapping() {
        if (bindVariableToAttributeNameMapping == null) {
            bindVariableToAttributeNameMapping = new HashMap<String, String>();
            Variable[] variables = ensureVariableManager().getVariables();
            if (variables != null && variables.length > 0) {
                for (Variable v : variables) {
                    Object propVal = v.getProperty("MapToAttribute");
                    if (propVal != null) {
                        bindVariableToAttributeNameMapping.put(v.getName(), 
                                                               (String)propVal);
                    }
                }
            }
        }
        return bindVariableToAttributeNameMapping;
    }


    /*
   * Used by overridden createViewLinkAccessorRS() method to help the
   * destination view object know what view link definition connects
   * it to the view link accessor rowsets parent.
   */
    private ViewLinkDefImpl viewLinkDefImpl;

    private boolean inExecuteEmptyRowSet = false;

    @Override
    public void executeEmptyRowSet() {
        try {
            inExecuteEmptyRowSet = true;
            super.executeEmptyRowSet();
        }
        finally {
            inExecuteEmptyRowSet = false;
        }
        
    }

    @Override
    protected void create() {
        super.create();
        // Manage rows using a key. This is required since the
        // view object is not entity-based.
        setManageRowsByKey(true);
        setIterMode(ITER_MODE_LAST_PAGE_PARTIAL);
    }

    enum AttributePopulationMode {
        POPULATE_BY_NAME,
        POPULATE_BY_POSITION;
    }
    private AttributePopulationMode attrMode = 
        AttributePopulationMode.POPULATE_BY_NAME;

    protected void setAttributePopulationMode(AttributePopulationMode value) {
        attrMode = value;
    }

    protected AttributePopulationMode getAttributePopulationMode() {
        return attrMode;
    }

    void setViewLinkDefImpl(ViewLinkDefImpl vldef) {
        viewLinkDefImpl = vldef;
    }
    private boolean initialized = false;
    //int rows = -1;

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
        super.executeQueryForCollection(rowset, params, noUserParams);
        // When executeEmptyRowSet() is called executeQueryForCollection()
        // will be invoked. In that case, skip the custom processing here.
        if (!performingExecuteEmptyRowSet((QueryCollection)rowset)) {
            setUserDataForCollection(rowset, 
                                     new UserData(getFilteredDataList((QueryCollection)rowset, 
                                                                      params, 
                                                                      noUserParams)));
        }
    }
    private boolean performingExecuteEmptyRowSet(QueryCollection qc) {
        // return qc.isSkipQuery(); (new in 11.1.1.3.0)
        return inExecuteEmptyRowSet;
    }
    private Map<String, Object> getFilterCriteria(QueryCollection qc, 
                                                  Object[] params, 
                                                  int noUserParams) {
        Map<String, Object> filter = null;
        if (params != null && params.length > 0) {
            filter = new HashMap<String, Object>(params.length);
            /*
       * First hand any user-defined parameters
       */
            if (noUserParams > 0) {
                for (int z = 0; z < noUserParams; z++) {
                    String paramName = null;
                    Object paramValue = null;
                    if (params[z] instanceof Object[]) {
                        Object[] paramObjArr = (Object[])params[z];
                        if (paramObjArr.length == 2 && 
                            paramObjArr[0] instanceof String) {
                            paramName = (String)paramObjArr[0];
                            paramValue = paramObjArr[1];
                        }
                    }
                    if (paramName != null && paramValue != null) {
                        filter.put(getFilterAttributeNameToUse(paramName), 
                                   paramValue);
                    }
                }
            }
            /*
       * Then, handle any viewlink related parameters the system has added
       */
            RowFilter rowFilter = ((QueryCollection)qc).getRowFilter();
            if (rowFilter instanceof RowFilterKey) {
                Key key = ((RowFilterKey)rowFilter).getKey();
                if (key != null && key.getAttributeCount() > 0) {
                    AttributeDefImpl[] attrDefImpls = null;
                    if (viewLinkDefImpl != null) {
                        attrDefImpls = 
                                viewLinkDefImpl.getDestinationEnd().getAttributeDefImpls();
                    } else {
                        ViewLink[] viewlinks = getViewLinks();
                        for (ViewLink vl : viewlinks) {
                            if (vl.getDestination() == this) {
                                ViewLinkImpl vli = (ViewLinkImpl)vl;
                                ViewLinkDefImpl vlDef = vli.getViewLinkDef();
                                attrDefImpls = 
                                        vlDef.getDestinationEnd().getAttributeDefImpls();
                                break;
                            }
                        }
                    }
                    if (attrDefImpls != null) {
                        filter = 
                                new HashMap<String, Object>(attrDefImpls.length);
                        int attrSlot = 0;
                        for (AttributeDefImpl attrDef : attrDefImpls) {
                            Object keyValue = key.getAttribute(attrSlot++);
                            filter.put(getFilterAttributeNameToUse(attrDef.getName()), 
                                       keyValue);
                        }
                    }
                }
            }
        }
        /*
   * Then handle any view criteria
   */
        ViewCriteriaManager vcm = 
            qc.getRowSetImpl().getViewObject().getViewCriteriaManager();
        String[] vcNames = vcm.getApplyViewCriteriaNames();
        if (vcNames != null && vcNames.length > 0) {
            for (String vcName : vcNames) {
                ViewCriteria vc = vcm.getViewCriteria(vcName);
                vc.reset();
                while (vc.hasNext()) {
                    ViewCriteriaRow vcr = (ViewCriteriaRow)vc.next();
                    List<ViewCriteriaItem> vcis = vcr.getCriteriaItems();
                    for (ViewCriteriaItem vci : vcis) {
                        String oper = vci.getOperator();
                        if ((EQUALS_OPER.equals(oper)||LIKE_OPER.equals(oper)) && 
                            vci.getValueCount() == 1) {
                            String paramName = vci.getAttributeDef().getName();
                            Object paramValue = vci.getValue(0);
                            if (filter == null) {
                                filter = new HashMap<String, Object>();
                            }
                            filter.put(paramName, paramValue);
                        }

                    }

                }
            }
        }

        return filter;
    }

    private String getFilterAttributeNameToUse(String attrName) {
        String remappedName = null;
        if (bindVariableToAttributeNameMapping != null) {
            remappedName = bindVariableToAttributeNameMapping.get(attrName);
        }
        return remappedName != null ? remappedName : attrName;
    }

    private List<Map<String, Object>> getFilteredDataList(QueryCollection qc, 
                                                          Object[] params, 
                                                          int noUserParams) {
        Map<String, Object> filter = 
            getFilterCriteria(qc, params, noUserParams);
        return getDataProvider().getData(filter);
    }

    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        return getQueryHitCount(viewRowSet, null);
    }

    public long getQueryHitCount(ViewRowSetImpl viewRowSet, Row[] masterRows) {
        getQuery(); // make sure the row filter is built
        return getRowCount();
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
        Iterator<Map<String, Object>> iter = 
            ((UserData)getUserDataForCollection(rowset)).getIterator();
        boolean retVal = iter.hasNext();
        if (retVal == false) {
            setFetchCompleteForCollection(rowset, true);
        }
        return retVal;
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
    protected ViewRowImpl createRowFromResultSet(Object rowset, ResultSet rs) {
        Iterator<Map<String, Object>> iter = getIteratorForCollection(rowset);
        ViewRowImpl r = null;
        if (iter.hasNext()) {
            Map<String, Object> row = iter.next();
            r = createNewRowForCollection(rowset);
            List<String> attrNamesFromDataProvider = 
                getDataProvider().getAttributeNames();
            for (AttributeDef attrDef : getAttributeDefs()) {
                byte kind = attrDef.getAttributeKind();
                if (kind == AttributeDef.ATTR_ENTITY_DERIVED || 
                    kind == AttributeDef.ATTR_TRANSIENT || 
                    kind == AttributeDef.ATTR_PERSISTENT || 
                    kind == AttributeDef.ATTR_SQL_DERIVED) {
                    int attrSlot = attrDef.getIndex();
                    Object value = null;
                    if (getAttributePopulationMode() == 
                        AttributePopulationMode.POPULATE_BY_NAME) {
                        String caseInsensitiveMapAttrNameMatch = 
                            getCaseInsensitiveDataProviderAttrName(attrDef.getName(), 
                                                                   attrNamesFromDataProvider);
                        if (caseInsensitiveMapAttrNameMatch != null) {
                            value = row.get(caseInsensitiveMapAttrNameMatch);
                        }
                    } else {
                        value = 
                                row.get(attrNamesFromDataProvider.get(attrSlot));
                    }
                    Object correctlyTypedValue = 
                        value != null ? TypeFactory.getInstance(getAttributeDef(attrSlot).getJavaType(), 
                                                                value) : null;
                    populateAttributeForRow(r, attrSlot, correctlyTypedValue);
                }
            }
        } else {
            setFetchCompleteForCollection(rowset, true);
        }
        return r;
    }

    protected String getCaseInsensitiveDataProviderAttrName(String name, 
                                                            List<String> attrNames) {
        for (String attrName : attrNames) {
            if (name.equalsIgnoreCase(attrName)) {
                return attrName;
            }
        }
        return null;
    }

    protected Row[] retrieveByKey(ViewRowSetImpl rs, Key key, int maxNumOfRows, 
                                  boolean skipWhere) {
        rs.getRowCount(); // fill up the cache
        return rs.findByKey(key, maxNumOfRows, skipWhere);
    }

    private void initialize() {
        setManageRowsByKey(true);
        bindVariableToAttributeNameMapping = 
                getBindVariableToAttributeNameMapping();
    }

    public void setOrderByClause(String string) {
        super.setOrderByClause(string);
    }

    public void setSortBy(String string) {
        super.setSortBy(string);
    }

    private Iterator<Map<String, Object>> getIteratorForCollection(Object rowset) {
        UserData ud = ((UserData)getUserDataForCollection(rowset));
        if (ud == null) {
          throw new RuntimeException("UserData for collection was null unexpectedly.");
        }
        Iterator<Map<String,Object>> iter = ud.getIterator();
        return iter;
    }

    @Override
    protected ViewRowSetImpl createViewLinkAccessorRS(AssociationDefImpl assocDef, 
                                                      ViewObjectImpl accessorVO, 
                                                      Row masterRow, 
                                                      Object[] values) {
        ViewRowSetImpl ret = 
            super.createViewLinkAccessorRS(assocDef, accessorVO, masterRow, 
                                           values);
        ViewLinkDefImpl vldef = (ViewLinkDefImpl)assocDef.getAssociation();
        ViewObject vo = ret.getViewObject();
        if (vo instanceof ListOfMapsDataProviderViewObjectImpl) {
            ((ListOfMapsDataProviderViewObjectImpl)vo).setViewLinkDefImpl(vldef);
        }
        return ret;
    }
    /*
   * Might want to be making a copy of the data here.
   */

    class UserData {
        Iterator<Map<String, Object>> iter = null;

        UserData(List<Map<String, Object>> data) {
            if (data == null) {
             data = new ArrayList<Map<String,Object>>(0);   
            }
            iter = data.iterator();
        }

        Iterator<Map<String, Object>> getIterator() {
            return iter;
        }
    }
}
