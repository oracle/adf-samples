/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.SortCriteria;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.uicli.binding.JUApplication;

public class MyCustomADFBCDataControl extends JUApplication {
    public MyCustomADFBCDataControl() {
    }
    /*
     * This is required for the default data control factory
     * to correctly instantiate this class.
     */

    public MyCustomADFBCDataControl(SessionCookie sessionCookie) {
        super(sessionCookie);
    }

    protected void applySortCriteria(DCIteratorBinding iter, 
                                     SortCriteria[] sortBy) {
        // super.applySortCriteria(iter, sortBy);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        if (sortBy != null) {
            for (SortCriteria s: sortBy) {
                if (!first) {
                    sb.append(",");
                } else {
                    first = false;
                }
                sb.append(s.getAttributeName());
                if (s.isDescending()) {
                    sb.append(" desc");
                }
            }
            iter.getViewObject().setSortBy(sb.toString());
        }
    }
}
