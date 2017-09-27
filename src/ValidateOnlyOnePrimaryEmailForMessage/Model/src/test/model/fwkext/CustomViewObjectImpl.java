/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model.fwkext;

import oracle.jbo.Row;
import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewObjectImpl;

public class CustomViewObjectImpl extends ViewObjectImpl {
    private static final String INSERT_NEW_ROWS_AT_END = "InsertNewRowsAtEnd";

    /**
     * Force new rows to go at the end if the "InsertNewRowsAtEnd"
     * VO properties is present.
     * @param row row to insert
     */
    public void insertRow(Row row) {
      if (getProperty(INSERT_NEW_ROWS_AT_END) != null) {
        insertRowAtRangeIndex(getFetchedRowCount(),row);
        setCurrentRow(row);
      }
      else {
        super.insertRow(row);
      }
    }
}
