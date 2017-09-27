/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package devguide.advanced.restorecurrency;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.server.TransactionEvent;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
public class CustomViewObjectImpl extends ViewObjectImpl {
  Key currentRowKey;
  int firstRowInRange;
  int currentRowIndexInRange;
  public void beforeRollback(TransactionEvent TransactionEvent) {
    if (isExecuted()) {
      ViewRowImpl currentRow = (ViewRowImpl)getCurrentRow();
      if (currentRow != null) {
        byte newRowState = currentRow.getNewRowState();
        if (newRowState != Row.STATUS_INITIALIZED && newRowState != Row.STATUS_NEW) {
          currentRowKey = currentRow.getKey();
          firstRowInRange = getRangeStart();
          int rangeIndexOfCurrentRow = getRangeIndexOf(currentRow);
          currentRowIndexInRange = rangeIndexOfCurrentRow;
        }
      }
    }
    super.beforeRollback(TransactionEvent);
  }
  public void afterRollback(TransactionEvent TransactionEvent) {
    super.afterRollback(TransactionEvent);
    if (currentRowKey != null) {
      Key k = new Key(currentRowKey.getAttributeValues());
      Row[] found = findByKey(k, 1);
      executeQuery();
      if (found != null && found.length == 1) {
        Row r = getRow(k);
        setCurrentRow(r);
        if (currentRowIndexInRange >= 0) {
          scrollRangeTo(r, currentRowIndexInRange);
        }
      }
    }
    currentRowKey = null;
  }
}
