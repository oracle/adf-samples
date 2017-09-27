/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.emg.adf.sample.model.fwk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import oracle.jbo.AttributeDef;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowComparator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.SortCriteria;

/**
 * Class to implement sorting by choice display value
 * @author Steven Davelaar
 */
public class LovDisplayAttributeRowComparator
  extends RowComparator
{
  SortCriteria[] mSortCriteria;

  private Map keyDisplayValueMap = new HashMap();

  public LovDisplayAttributeRowComparator(SortCriteria[] sortCriterias)
  {
    super(sortCriterias);
    mSortCriteria = sortCriterias;
  }

  public int compareRows(Row row1, Row row2)
  {
    if (mSortCriteria == null)
    {
      return 0;
    }

    for (int j = 0; j < mSortCriteria.length; j++)
    {
      Object lValue;
      Object rValue;
      SortCriteria sortCrit = mSortCriteria[j];
      int attrId = sortCrit.getAttributeIndex();

      if (attrId < 0)
      {
        attrId =
            row1.getStructureDef().getAttributeIndexOf(sortCrit.getAttributeName());
        sortCrit.setAttributeIndex(attrId);
      }

      lValue = row1.getAttribute(attrId);
      rValue = row2.getAttribute(attrId);

      lValue = getLovDisplayValue(row1, lValue, attrId);
      rValue = getLovDisplayValue(row2, rValue, attrId);

      if (!sortCrit.isCaseSensitive())
      {
        lValue = (lValue != null)? lValue.toString().toUpperCase(): lValue;
        rValue = (rValue != null)? rValue.toString().toUpperCase(): rValue;
      }
      int comp;
      if ((comp = compareValues(lValue, rValue)) != 0)
      {
        return sortCrit.isDescending()? -comp: comp;
      }
    }

    return 0;
  }

  private Object getLovDisplayValue(Row row, Object lValue, int attrIndex)
  {
    // check whether display value has been looked up before for this key value
    if (keyDisplayValueMap.containsKey(lValue))
    {
      return keyDisplayValueMap.get(lValue);
    }
    Object displayValue = lValue;
    AttributeDef attrDef =
      row.getStructureDef().getAttributeDef(attrIndex);
    if ("choice".equals(attrDef.getProperty("CONTROLTYPE")) 
        && attrDef.getListBindingDef()!=null)
    {


      String lovName = attrDef.getListBindingDef().getName();
      String attrName = attrDef.getName();
      String[] displayAttrs =
        attrDef.getListBindingDef().getListDisplayAttrNames();
      if (displayAttrs != null && displayAttrs.length > 0)
      {
        RowSetIterator lovRsi =
          ((BaseViewRowImpl) row).getListBindingRSI(attrName, lovName);
        // check whether list attr name is key attribute, so we can use findByKey, otherwise
        // loop over all records
        String[] listAttrNames = attrDef.getListBindingDef().getListAttrNames();
        if (listAttrNames==null || listAttrNames.length==0)
        {
          // invalid LOV definition, return value itself
          return lValue;
        }
        String listKeyAttr = listAttrNames[0];
        AttributeDef[] keyAttrs = lovRsi.getRowSet().getViewObject().getKeyAttributeDefs();
        // check whether the list attribute copied back is the key attr so we can use findByKey
        if (keyAttrs!=null && keyAttrs.length==1 && keyAttrs[0].getName().endsWith(listKeyAttr))
        {
          Key lovKey = new Key(new Object[]
              { lValue });
          Row[] lovRows = lovRsi.findByKey(lovKey, 1);
          if (lovRows != null && lovRows.length > 0)
          {
            displayValue = lovRows[0].getAttribute(displayAttrs[0]);
          }
        }
        else
        {
          // it is not the key attr, loop over all rows to find matching row    
          lovRsi.reset();
          while (lovRsi.hasNext())
          {
            Row lovRow = lovRsi.next();
            if (lValue.equals(lovRow.getAttribute(listKeyAttr)))
            {
              // we found the corresponding lov row
              displayValue = lovRow.getAttribute(displayAttrs[0]);
              break;
            }
          }
        }
      }
    }
    // cache key value and its display value
    keyDisplayValueMap.put(lValue, displayValue);
    return displayValue;
  }
}
