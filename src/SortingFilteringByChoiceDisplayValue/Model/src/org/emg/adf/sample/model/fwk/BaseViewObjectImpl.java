/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.emg.adf.sample.model.fwk;

import java.util.Comparator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

import java.util.TreeMap;

import oracle.jbo.AttributeDef;
import oracle.jbo.SortCriteria;
import oracle.jbo.SortCriteriaImpl;
import oracle.jbo.server.ViewDefImpl;
import oracle.jbo.server.ViewObjectImpl;

/**
 * Base class to implement sorting and filtering by choice display value
 * @author Steven Davelaar
 */
public class BaseViewObjectImpl extends ViewObjectImpl {
 
    public BaseViewObjectImpl(String string, ViewDefImpl viewDefImpl) {
        super(string, viewDefImpl);
    }

    public BaseViewObjectImpl() {
        super();
    }

    /**
     * Standard sorting when clicking an ADF Faces table column header results in setting the order by clause for the DB query.
     * In this method we check the order by clause and when set, we loop over the column names, and map the column name to the 
     * corresponding attribute definition.
     * We then check whether this attribute has an LOV choice list defined with the first display attribute not being mapped to the
     * order by attribute. If that is the case we return a sort criteria based on this order by attribute, and sort criteria for any
     * other column that must be sorted as well. So, if the Order By clause contains at least one reference to choice display value, this
     * method will return sort criteria for all columns in the order by.
     * By returning a sort criteria, the framework will call the ViewObjectImpl.sort method, which in turn calls the getRowComparator method.
     * We have also overridden the getRowComparator method to compare based on the matching LOV display attribute value, rather than the value 
     * of the order-by attribute itself. 
     * @return
     */
public SortCriteria[] getSortCriteria()
{
  String orderBy = getOrderByClause();       
  if (orderBy!=null )
  {
    // use linkedHashMap to maintain sort order
    LinkedHashMap<String,Boolean> sortAttrs = new LinkedHashMap<String, Boolean>();
    StringTokenizer tokenizer = new StringTokenizer(orderBy,",");
    boolean sortByChoiceDisplayValue = false;
    while (tokenizer.hasMoreTokens())
    {
      String orderByPart = tokenizer.nextToken();
      boolean descending = false;
      if (orderByPart.endsWith(" DESC")) 
      {
        descending = true;
        orderByPart = orderByPart.substring(0,orderByPart.length()-5);
      }
      // extract column name, is part after the dot
      int dotpos = orderByPart.lastIndexOf(".");
      String columnName = orderByPart.substring(dotpos+1);
      // loop over attributes and find matching attribute
      AttributeDef orderByAttrDef = null;
      for (AttributeDef attrDef : getAttributeDefs())
      {
        if (columnName.equals(attrDef.getColumnName()))
        {
          orderByAttrDef = attrDef;
          break;
        }
      }
      if (orderByAttrDef!=null)
      {
        String orderbyAttr = orderByAttrDef.getName();
        sortAttrs.put(orderbyAttr, descending);
        if ("choice".equals(orderByAttrDef.getProperty("CONTROLTYPE")) 
            && orderByAttrDef.getListBindingDef()!=null)
        {
          String[] displayAttrs = orderByAttrDef.getListBindingDef().getListDisplayAttrNames();
          String[] listAttrs = orderByAttrDef.getListBindingDef().getListAttrNames();
          // if first list display attributes is not the same as first list attribute, than the value
          // displayed is different fron the value copied back to the order by attribute, in which case we need to
          // use our custom comparator
          if (displayAttrs!=null && listAttrs!=null && displayAttrs.length>0 && !displayAttrs[0].equals(listAttrs[0]))
          {
            sortByChoiceDisplayValue = true; 
          }
        }  
      }
    }
    if (sortByChoiceDisplayValue)
    {
      // yes, we need to return sortCriteria
      SortCriteria[] sc = new SortCriteriaImpl[sortAttrs.size()];
      Iterator<String> attrs = sortAttrs.keySet().iterator();
      int index = 0;
      while (attrs.hasNext())
      {
        String attrName = attrs.next();
        sc[index] = new SortCriteriaImpl(attrName, sortAttrs.get(attrName));
        index++;
      }
      return sc;                    
    }
  }
  return super.getSortCriteria();
}

    /**
     * Return custom LovDisplayAttributeRowComparator class which will compare the two attributes based on choice-list display value.
     * See getSortCriteria()
     * @return
     */
public Comparator getRowComparator()
{
   return new LovDisplayAttributeRowComparator(getSortCriteria());
}

}
