/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.fwkext;
import oracle.jbo.server.ViewObjectImpl;
public class MyCustomViewObjectImpl extends ViewObjectImpl {
  private static final String NO = "N";
  private static final String FIRSTROWS = "/*+ FIRST_ROWS */";
  private static final String RANGEPAGING_FIRSTROWS = "RangePagingFirstRows";

  /**
   * Overridden framework method.
   *
   * If the ViewObject-level custom property named "RangePagingFirstRows"
   * is set to the value "N", then we strip out the FIRST_ROWS query
   * optimizer hint before returning the query.
   * @param qry Base SQL query to "wrap" with the range paging query
   * @param noUserParams Number of user-defined query parameters
   * @return Query string to return to the framework to use for range paging
   */
  protected String buildRangePagingQuery(String qry, int noUserParams) {
    String retval = super.buildRangePagingQuery(qry, noUserParams);
    if (NO.equalsIgnoreCase((String) getProperty(RANGEPAGING_FIRSTROWS))) {
      int idx = retval.indexOf(FIRSTROWS);
      if (idx >= 0) {
        retval = retval.substring(0, idx) + retval.substring(idx + 17);
      }
    }
    return retval;
  }
}
