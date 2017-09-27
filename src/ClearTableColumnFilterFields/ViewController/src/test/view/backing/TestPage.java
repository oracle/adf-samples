/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import java.util.Map;

import javax.faces.event.PhaseId;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.event.QueryEvent;

import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import test.view.util.EL;

public class TestPage {
    private RichTable table;
    public void setTable(RichTable table) {
        this.table = table;
    }
    public RichTable getTable() {
        return table;
    }

    /*
     * Helper Methods
     */
    private FilterableQueryDescriptor getTableQueryDescriptor() {
        return (FilterableQueryDescriptor)getTable().getFilterModel();
    }
    private Map getTableFilterCriteria() {
      return getTableQueryDescriptor().getFilterCriteria();
    }

    /*
     * Table Query Listener event handler
     * 
     * NOTE: Overriding the default query listener expression of
     * ----  "#{bindings.DeptView1Query.processQuery}" is not required
     *       to implement the search field clearing, but this illustrates
     *       how a custom query listener method can be used to add code
     *       before and/or after invoking the default query listener.
     */
    public void onTableQueryExecuted(QueryEvent queryEvent) {
        // If needed, do something before the table's query is processed
        
        // Invoke the method expression that was the default expression that
        // the JDeveloper design time had bound to the "QueryListener" on
        // the af:table component
        EL.invokeMethod("#{bindings.DeptView1Query.processQuery}",QueryEvent.class,queryEvent);
        
        // If needed, do something after the table's query is processed
    }

    private void queueTableQueryEvent() {
        QueryEvent queryEvent = new QueryEvent(getTable(),getTableQueryDescriptor() );
        // Query event should be delivered after the updates have been processed. This
        // will ensure that filter facets with input date etc are updated.
        queryEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
        getTable().queueEvent(queryEvent);        
    }
    public String onClearTableSearchFields() {
        getTableFilterCriteria().clear();
        return null;
    }

    public String onJustClearTheDnameSearchField() {
        getTableFilterCriteria().remove("Dname");
        return null;
    }

    public String onClearTableSearchFieldsAndReexecuteQuery() {
        getTableFilterCriteria().clear();
        queueTableQueryEvent();
        return null;
    }

    public String onProgrammaticallySetFilterCriteriaAndReexecuteQuery() {
        getTableFilterCriteria().clear();
        getTableFilterCriteria().put("Dname","%N%");
        getTableFilterCriteria().put("Loc","%O%");
        queueTableQueryEvent();
        return null;
    }    
}
