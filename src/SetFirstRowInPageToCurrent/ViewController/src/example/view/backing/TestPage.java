/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view.backing;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.faces.event.RangeChangeEvent;
import example.view.EL;

import oracle.adf.view.faces.event.SelectionEvent;

public class TestPage {
  private static final String EMPVIEW_ITER_EL = "#{bindings.EmpViewIterator}";
  public void onRangeChanged(RangeChangeEvent rangeChangeEvent) {
    DCIteratorBinding empViewIter = (DCIteratorBinding)EL.get(EMPVIEW_ITER_EL);
    // NOTE: EmpView view object has its "Fill last page of rows when paging
    // ====  through rowset" property *UNCHECKED* on the "Tuning" panel of
    //       the view object editor.
    empViewIter.setRangeStart(rangeChangeEvent.getNewStart());
    empViewIter.setCurrentRowIndexInRange(0);
    EL.set("#{requestScope.ignoreCurrentRowChange}",true);
  }

    public void onSelectionChanged(SelectionEvent selectionEvent) {
        if (EL.test("#{requestScope.ignoreCurrentRowChange}")) {
          return;
        }
        EL.invokeMethod("#{bindings.EmpView.collectionModel.makeCurrent}",SelectionEvent.class,selectionEvent);
    }
}
