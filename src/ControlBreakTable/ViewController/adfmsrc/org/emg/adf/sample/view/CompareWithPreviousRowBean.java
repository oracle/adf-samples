/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.emg.adf.sample.view;

import java.util.HashMap;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

public class CompareWithPreviousRowBean
  extends HashMap
{
  
  private String rowExpression = "#{row}";

  public CompareWithPreviousRowBean(java.util.Map map)
  {
    super(map);
  }

  public CompareWithPreviousRowBean()
  {
    super();
  }

  public CompareWithPreviousRowBean(int i)
  {
    super(i);
  }

  public CompareWithPreviousRowBean(int i, float f)
  {
    super(i, f);
  }

  /**
   * Pass in attributeName, returns true when value of this attribute in current row is same as in previous row.
   * @param key
   * @return
   */
  public Object get(Object key)
  {
    String attrName = (String) key;
    boolean isSame = false;
    // get the currently processed row, using row expression #{row}
    JUCtrlHierNodeBinding row = (JUCtrlHierNodeBinding) resolveExpression(getRowExpression());
    JUCtrlHierBinding tableBinding = row.getHierBinding();
    int rowRangeIndex = row.getViewObject().getRangeIndexOf(row.getRow());
    Object currentAttrValue = row.getRow().getAttribute(attrName);
    if (rowRangeIndex > 0)
    {
      Object previousAttrValue = tableBinding.getAttributeFromRow(rowRangeIndex - 1, attrName);
      isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
    }
    else if (tableBinding.getRangeStart() > 0)
    {
      // previous row is in previous range, we create separate rowset iterator,
      // so we can change the range start without messing up the table rendering which uses
      // the default rowset iterator
      int absoluteIndexPreviousRow = tableBinding.getRangeStart() - 1;
      RowSetIterator rsi = null;
      try
      {
        rsi = tableBinding.getViewObject().getRowSet().createRowSetIterator(null);
        rsi.setRangeStart(absoluteIndexPreviousRow);
        Row previousRow = rsi.getRowAtRangeIndex(0);
        Object previousAttrValue = previousRow.getAttribute(attrName);
        isSame = currentAttrValue != null && currentAttrValue.equals(previousAttrValue);
      }
      finally
      {
        rsi.closeRowSetIterator();
      }
    }
    return isSame;
  }

  private Object resolveExpression(String expression)
  {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
    return valueExp.getValue(elContext);
  }

  public void setRowExpression(String rowExpression)
  {
    this.rowExpression = rowExpression;
  }

  public String getRowExpression()
  {
    return rowExpression;
  }
}
