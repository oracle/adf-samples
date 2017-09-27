/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.view.backing;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.faces.component.core.data.CoreTable;
import oracle.adf.view.faces.event.RangeChangeEvent;

import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlRangeBinding;
public class ProgrammaticScroll {
  private Integer rowsToScroll = null;
  private static final int TABLEUICOMP = 1;
  private static final int ITERBINDING = 2;
  private static final int VIEWOBJECT  = 3;
  private int scollUsing = 1;
  private CoreTable table;
  public ProgrammaticScroll() {
  }
  public void setTable(CoreTable table) {
    this.table = table;
  }
  public CoreTable getTable() {
    return table;
  }
  public void setRowsToScroll(Integer rowsToScroll) {
    this.rowsToScroll = rowsToScroll;
  }
  public Integer getRowsToScroll() {
    return rowsToScroll == null ? getTable().getRows() : rowsToScroll;
  }
  public String onScrollBackward() {
    scrollRangeIfNeeded(true);
    return null;
  }
  public String onScrollForward() {
    scrollRangeIfNeeded(false);
    return null;
  }
  public void setScollUsing(int scollUsing) {
    this.scollUsing = scollUsing;
  }
  public int getScollUsing() {
    return scollUsing;
  }  
  public void onRangeChanged(RangeChangeEvent rangeChangeEvent) {
    int oldStart = rangeChangeEvent.getOldStart();
    int oldEnd   = rangeChangeEvent.getOldEnd();
    int newStart = rangeChangeEvent.getNewStart();
    int newEnd   = rangeChangeEvent.getNewEnd();
    System.out.println("Table Range Changed: old("+oldStart+","+oldEnd+
                       ") new("+newStart+","+newEnd+")");
  }
  
  /* -------- PRIVATE IMPLEMENTATION CODE ------- */
  
  private ViewObject getCountriesViewObject() {
    return getCountriesIter().getViewObject();
  }
  private DCIteratorBinding getIteratorBinding(String name) {
    return getBindingContainer().findIteratorBinding(name);
  }
  private DCBindingContainer getBindingContainer() {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding("#{bindings}");
    return (DCBindingContainer)vb.getValue(fc);
  }
  private void scrollRangeIfNeededUsingViewObject(boolean backwards) {
    if (getRowsToScroll() != null) {
      int rowsToScroll = getRowsToScroll().intValue();
      if (backwards) {
        rowsToScroll = -rowsToScroll;
      }
      int oldStart = getTable().getFirst();
      int oldEnd   = getCountryTableEnd();
      ViewObject vo = getCountriesViewObject();
      vo.scrollRange(rowsToScroll);
      int newStart = vo.getRangeStart();
      if (newStart != oldStart) {
        queueRangeChangeEventForTable(oldStart,oldEnd,newStart);
      }
    }
  }  
  private void scrollRangeIfNeededUsingIteratorBinding(boolean backwards) {
    if (getRowsToScroll() != null) {
      int rowsToScroll = getRowsToScroll().intValue();
      if (backwards) {
        rowsToScroll = -rowsToScroll;
      }
      DCIteratorBinding ib = getCountriesIter();
      int oldStart = ib.getRangeStart();
      int newFirstRow = newFirstRow(rowsToScroll);
      if (oldStart != newFirstRow) {
        int oldEnd = getCountryTableEnd();
        ib.setRangeStart(newFirstRow);
        queueRangeChangeEventForTable(oldStart,oldEnd,newFirstRow);
      }
    }
  }
  private int getCountryCount() {
    return (int)getCountriesIter().getEstimatedRowCount();
  }
  private int getCountryTableEnd() {
    int tableFirst = getTable().getFirst();
    int tableRows  = getTable().getRows();
    int lastRow  = getCountryCount();
    return Math.min(tableFirst+tableRows-1,lastRow);
  }
  private  DCIteratorBinding getCountriesIter() {
    return getIteratorBinding("CountriesViewIterator");
  }
  private void queueRangeChangeEventForTable(int oldStart,int oldEnd, int newStart) {
    int newEnd = Math.min(newStart+getTable().getRows()-1,getCountryCount());
    RangeChangeEvent rce = new RangeChangeEvent(getTable(),oldStart,oldEnd,newStart,newEnd);
    getTable().queueEvent(rce);
  }
  private void scrollRangeIfNeededUsingTable(boolean backwards) {
    if (getRowsToScroll() != null) {
      int rowsToScroll = getRowsToScroll().intValue();
      if (backwards) {
        rowsToScroll = -rowsToScroll;
      }
      int newFirst = newFirstRow(rowsToScroll);
      if (newFirst != getTable().getFirst()) {
        getTable().setFirst(newFirst);
      }
    }
  }  
  private void scrollRangeIfNeeded(boolean backwards) {
    switch (getScollUsing())  {
      case TABLEUICOMP: {
          scrollRangeIfNeededUsingTable(backwards);
        }
        break;
      case ITERBINDING: {
          scrollRangeIfNeededUsingIteratorBinding(backwards);
        }
        break;
      case VIEWOBJECT: {
          scrollRangeIfNeededUsingViewObject(backwards);
        }
        break;
    }
  }
  private int newFirstRow(int rowsToScroll) {
    int maxRows = getCountryCount();
    int currentFirst = getTable().getFirst();
    int newFirst = currentFirst + rowsToScroll;
    if (newFirst < 0) return 0;
    else if (newFirst > maxRows) return maxRows;
    else return newFirst;
  }

}
