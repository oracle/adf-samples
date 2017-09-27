/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.emg.adf.sample.view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlValueDef;


/**
 * Generic map class that returns a list of JSF select items in get method based on an ADF model-based LOV binding passed in as the key,
 * so it can be used for table filtering.
 *
 * @author Steven Davelaar
 */
public class TableFilterChoiceList
  extends HashMap
{

  /** 
   * Cache the list of selectItems for each model LOV binding to increase performance. Caching scope depends on scope of this
   * bean which is viewScope in this sample, but can be changed to pageFlowScope if desired.
   **/
  private Map<String,List<SelectItem>> cachedFilterLists = new HashMap<String,List<SelectItem>>();

  public TableFilterChoiceList(java.util.Map map)
  {
    super(map);
  }

  public TableFilterChoiceList()
  {
    super();
  }

  public TableFilterChoiceList(int i)
  {
    super(i);
  }

  public TableFilterChoiceList(int i, float f)
  {
    super(i, f);
  }

  /**
   * When a list binding is passed in as key, this method returns the list of JSF select items
   * as defined inside the list binding.
   * 
   * @param key
   * @return
   */
public Object get(Object key)
{
  if (key instanceof FacesCtrlListBinding)
  {
    // we need to cast to internal class FacesCtrlListBinding rather than JUCtrlListBinding to
    // be able to call getItems method. To prevent this import, we could evaluate an EL expression
    // to get the list of items
    FacesCtrlListBinding lb = (FacesCtrlListBinding) key;
    if (cachedFilterLists.containsKey(lb.getName()))
    {
      return cachedFilterLists.get(lb.getName());
    }
    List<SelectItem> items = (List<SelectItem>)lb.getItems();
    if (items==null || items.size()==0)
    {
      return items;
    }
    List<SelectItem> newItems = new ArrayList<SelectItem>();
    JUCtrlValueDef def = ((JUCtrlValueDef)lb.getDef());
    String valueAttr = def.getFirstAttrName();
    // the items list has an index number as value, we need to replace this with the actual
    // value of the attribute that is copied back by the choice list
    for (int i = 0; i < items.size(); i++)
    {
      SelectItem si = (SelectItem) items.get(i);
      Object value = lb.getValueFromList(i);
      if (value instanceof Row)
      {
        Row row = (Row) value;
        si.setValue(row.getAttribute(valueAttr));          
      }
      else
      {
        // this is the "empty" row, set value to empty string so all rows will be returned
        // as user no longer wants to filter on this attribute
        si.setValue("");
      }
      newItems.add(si);
    }
    cachedFilterLists.put(lb.getName(), newItems);
    return newItems;
  }
  return null;
}
}

