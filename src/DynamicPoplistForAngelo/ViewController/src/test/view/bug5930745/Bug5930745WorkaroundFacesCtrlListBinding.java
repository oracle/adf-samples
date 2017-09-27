/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.bug5930745;

import java.util.AbstractList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adfinternal.view.faces.util.IntegerUtils;

import oracle.jbo.uicli.binding.JUCtrlListBinding;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

public class Bug5930745WorkaroundFacesCtrlListBinding extends JUCtrlListBinding 
{
  /**
   * *** For internal framework use only ***
   */
  protected Bug5930745WorkaroundFacesCtrlListBinding()
  {
  }

  /**
   * Uses the given static list of value objects to display data in the control.
   */
  public Bug5930745WorkaroundFacesCtrlListBinding(Object control, DCIteratorBinding iterBinding, String[] attrNames,
                              Object[] valueList)
  {
    super(control, iterBinding, attrNames, valueList);
  }
  
  /**
  * **For Testing purposes only***
  * Uses the same Iterator Binding to update as well as display values.
  */
  public Bug5930745WorkaroundFacesCtrlListBinding(Object control, DCIteratorBinding iterBinding,
                             String[] attrNames, int listOperMode)
  {
    super(control, iterBinding, attrNames, listOperMode);
  }

  /**
  * Uses the listIterBinding object to get the iterator and attribute names from
  * listDisplayAttrNames to display attributes from the BC4J Rows in the iterator.
  * Also maps values from listAttrNames attributes to the set of attributes (attrNames)
  * in the current row of the target iterator referred by iterBinding object 
  */
  protected Bug5930745WorkaroundFacesCtrlListBinding(Object control, DCIteratorBinding iterBinding,
                                 String[] attrNames, DCIteratorBinding listIterBinding,
                                 String[] listAttrNames, String[] listDisplayAttrNames)
  {
    super(control, iterBinding, attrNames, listIterBinding, 
          listAttrNames, listDisplayAttrNames);
  }
  
  /**
   * Gets a list of all the legal values for this SelectOne
   * @return each element in this list is a {@link SelectItem}
   */
  public List getItems()
  {
    return _items;
  }

  /**
   * Primarily for spel support. Could be removed prior to 905Prod and merged into a get() 
   * method for spel-access.
   * <p>
   * Returns the last input value for the first attribute on this binding if this value 
   * raised an exception. Otherwise returns the value from the model object calling
   * getAttribute(0);
   * @javabean.property
   */
  protected Object getInputValueFixed()
  {
     if (getError() != null) 
     {
        // if it was an exception, return the index corresponding to the null value 
        // if the list binding accepts nulls, otherwise return the last input value
        // (could be null)
        if (hasNullValue())
           Integer.valueOf(getNullValueIndex());
        else
           return mInputVal;
     }
     int sel = getSelectedIndex();
     if (mInputVal != null && (sel == -1 || sel == (getNullValueIndex())))
     {
        return mInputVal;
     }
     return new Integer(sel);
  }


  public Object getInputValue()
  {
    Object value = getInputValueFixed();
    // if no value is set then super class returns -1.
    // in that case we should return null here:
    if ((value != null) &&
        (value.getClass() == Integer.class) &&
        (((Integer) value).intValue() < 0))
    {
      return null; // bug 4902453
    }
    return value;
  }
  
  protected Object internalGet(String key)
  {
    if ("items".equals(key)) // bug 4588228
      return getItems();
    return super.internalGet(key);
  }
  
  private final List _items = new AbstractList()
  {
    public int size()
    {
      return getDisplayData().size();
    }
    
    public Object get(int index)
    {
      Map map = (Map) getDisplayData().get(index);
      // do not use the following: it returns a String. However,
      // getInputValue returns an Integer:
      //Object value = map.get(LISTITEM_Index);
      Object value = IntegerUtils.getInteger(index);
      String label = map.get(LISTITEM_Prompt).toString();
      SelectItem item = new SelectItem(value, label);
      return item;
    }
  };
}
