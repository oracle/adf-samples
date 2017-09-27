/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import test.view.util.BackingBeanBase;

public class SearchPage extends BackingBeanBase {

    public void onPagePreRender() {
        if (isPostback()) {
            long rows = 
                getIteratorBinding("EmployeesResultsIterator").getEstimatedRowCount();
            String message = null;
            if (rows == 0) {
                message = 
                        "Query returns no rows. Please try a less specific search.";
            } else if (rows > 10) {
                message = 
                        "Query returns more than 10 rows. Please try a more specific search.";
            }
            if (message != null) {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(message));
            }
        }
    }
}
