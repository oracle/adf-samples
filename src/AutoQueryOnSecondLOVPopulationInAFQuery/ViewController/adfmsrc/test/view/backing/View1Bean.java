/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.component.UIComponent;

import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.event.QueryOperationEvent;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlSearchBinding;

import oracle.jbo.ViewCriteria;

import oracle.jbo.ViewCriteriaItem;
import oracle.jbo.ViewCriteriaRow;

import oracle.jbo.uicli.binding.JUSearchBindingCustomizer;

import test.view.util.EL;

public class View1Bean {
    private RichTable tableComponent;

    public View1Bean() {
        super();
    }

    private ViewCriteria getViewCriteriaForSearchBinding() {
        FacesCtrlSearchBinding fcsb =
            (FacesCtrlSearchBinding)EL.get("#{bindings.DeptViewCriteriaQuery}");
        return JUSearchBindingCustomizer.getViewCriteria(fcsb,
                                                         "DeptViewCriteria");
    }

    // The <af:query> component engages this method via the component
    // property queryOperationListener="#{View1.onQueryOperationPerformed}"

    public void onQueryOperationPerformed(QueryOperationEvent queryOperationEvent) {
        // If the query operation being performed is a criterion update...
        if (queryOperationEvent.getOperation() ==
            QueryOperationEvent.Operation.CRITERION_UPDATE) {
            // Get the first view criteria row in the view criteria related
            // to the search binding related to the <af:query> component
            // in question.
            ViewCriteriaRow vcr =
                (ViewCriteriaRow)getViewCriteriaForSearchBinding().first();
            // Access the named view criteria items in the view criteria row
            // corresponding to the two attributes that our search form
            // is collecting search criteria for...
            ViewCriteriaItem dnameCriteriaItem =
                vcr.ensureCriteriaItem("Dname");
            ViewCriteriaItem locCriteriaItem = vcr.ensureCriteriaItem("Loc");
            // Get the values of these two view critera items to see what
            // the user has entered so far into the search form.
            Object dnameCriteriaValue = dnameCriteriaItem.getValue();
            Object locCriteriaValue = locCriteriaItem.getValue();
            // If both the dname criteria item and the loc criteria item
            // are non-null, then queue up a QueryEvent for the <af:query>
            // component. It's the same component that is the source of
            // the queryOperationEvent we're currently processing
            if (dnameCriteriaValue != null && locCriteriaValue != null) {
                QueryEvent qe =
                    new QueryEvent((UIComponent)queryOperationEvent.getSource(),
                                   queryOperationEvent.getDescriptor());
                ((UIComponent)queryOperationEvent.getSource()).queueEvent(qe);
                // Force the table to repaint, too
                AdfFacesContext.getCurrentInstance().addPartialTarget(getTableComponent());
            }
        }
        // Invoke the default handler for the query operation listener on the query binding
        EL.invokeMethod("#{bindings.DeptViewCriteriaQuery.processQueryOperation}",
                        QueryOperationEvent.class, queryOperationEvent);
    }

    // The <af:query> component engages this method via the component
    // property queryListener="#{View1.onQueryPerformed}"

    public void onQueryPerformed(QueryEvent queryEvent) {
        // Invoke the default handler for the query listener on the query binding
        EL.invokeMethod("#{bindings.DeptViewCriteriaQuery.processQuery}",
                        QueryEvent.class, queryEvent);
    }

    public void setTableComponent(RichTable tableComponent) {
        this.tableComponent = tableComponent;
    }

    public RichTable getTableComponent() {
        return tableComponent;
    }
}
