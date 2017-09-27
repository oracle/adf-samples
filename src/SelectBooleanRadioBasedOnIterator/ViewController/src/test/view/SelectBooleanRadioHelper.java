/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import oracle.adf.model.BindingContext;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

public class SelectBooleanRadioHelper
{
   Integer selectedIndex = -1;
   Map<Integer, Boolean> m = new HashMap<Integer, Boolean>() {
      public Boolean put(Integer index, Boolean flag) 
      {
         Boolean ret = super.put(index,flag);
         if (Boolean.TRUE.equals(flag)) {
           selectedIndex = index;
           setAttrValueInBindingUsingRowWithIndex(selectedIndex);
         }
        return ret;
      }
   };
   String valueAttributeName;
   String valueBindingNameToSet;
   String valueBindingNameForRows;

   private SelectBooleanRadioHelper() 
   {
     
   }
   public SelectBooleanRadioHelper(String valueAttributeName, String valueBindingNameToSet, String valueBindingNameForRows) 
   {
     if (valueAttributeName == null) 
     {
       throw new IllegalArgumentException("valueAttributeName must be non-null");
     }
     if (valueBindingNameToSet == null) 
     {
       throw new IllegalArgumentException("valueBindingNameToSet must be non-null");
     }
     if (valueBindingNameForRows == null) 
     {
       throw new IllegalArgumentException("valueBindingNameForRows must be non-null");
     }
     this.valueAttributeName = valueAttributeName;
     this.valueBindingNameToSet = valueBindingNameToSet;
     this.valueBindingNameForRows = valueBindingNameForRows;
   }
   public Map<Integer, Boolean> getMap()
   {
      return m;
   }

   public int getSelectedIndex()
   {
     return selectedIndex;
   }

   private void setAttrValueInBindingUsingRowWithIndex(Integer selectedIndex)
   {
      if (selectedIndex >= 0)
      {
         JUCtrlValueBinding binding =
            (JUCtrlValueBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get(valueBindingNameForRows);
         Row[] rows = binding.getRowIterator().getAllRowsInRange();
         if (rows != null && rows.length >= selectedIndex)
         {
           JUCtrlValueBinding bindingToSet =
              (JUCtrlValueBinding)BindingContext.getCurrent().getCurrentBindingsEntry().get(valueBindingNameToSet);
           bindingToSet.setInputValue(rows[selectedIndex].getAttribute(valueAttributeName));
            
         }
      }
   }
}
