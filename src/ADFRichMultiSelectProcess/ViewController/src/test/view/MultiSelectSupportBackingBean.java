/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTree;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.jbo.Key;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;
import org.apache.myfaces.trinidad.component.UIXTree;
public class MultiSelectSupportBackingBean {

    private Map<Object,Set<Key>> selectedAdfRowKeys = new HashMap(){
      public Object get(Object key) {
          if (key instanceof RichTable) {
              return getSelectedAdfRowKeys((RichTable)key);
          }
          if (key instanceof UIXTree) {
              return getSelectedAdfRowKeys((UIXTree)key);
          }
          else if (key instanceof String) {
              UIComponent uiComp = FacesContext.getCurrentInstance().getViewRoot().findComponent((String)key);
              if (uiComp instanceof RichTable) {
                  return getSelectedAdfRowKeys((RichTable)uiComp);
              }
              else if (uiComp instanceof UIXTree) {
                  return getSelectedAdfRowKeys((UIXTree)uiComp);
              }
          }
          throw new RuntimeException("ERR: selectedAdfRowKeys map key must be a RichTable, RichTree, o RichTreeTable or a string id to one of these");
      }
    };
    public Map<Object,Set<Key>> getSelectedAdfRowKeys() {
      return selectedAdfRowKeys;      
    }    
    /*
     * Both RichTree and RichTreeTable components extend UIXTree
     * so this one method can handle both.
     */
    public Set<Key> getSelectedAdfRowKeys(UIXTree table) {
        Set<Key> retVal = new HashSet<Key>();
        for (Object opaqueFacesRowKey : table.getSelectedRowKeys()) {
            table.setRowKey(opaqueFacesRowKey);
            Object o = table.getRowData();
            JUCtrlHierNodeBinding rowData = (JUCtrlHierNodeBinding)o;
            retVal.add(rowData.getRow().getKey());
        }
        return retVal;
    }
    public Set<Key> getSelectedAdfRowKeys(RichTable table) {
        Set<Key> retVal = new HashSet<Key>();
        for (Object opaqueFacesRowKey : table.getSelectedRowKeys()) {
            table.setRowKey(opaqueFacesRowKey);
            Object o = table.getRowData();
            JUCtrlHierNodeBinding rowData = (JUCtrlHierNodeBinding)o;
            retVal.add(rowData.getRow().getKey());
        }
        return retVal;
    }
    // ----------- ADD THE LINES ABOVE ----------
}
