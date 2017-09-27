/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.fwk.view;
import java.util.List;
import java.util.Map;
import oracle.jbo.Row;
import oracle.jbo.html.BindingContainerDataSource;
import oracle.jbo.uicli.binding.JUControlBinding;
import oracle.jbo.uicli.binding.JUCtrlListBinding;
import oracle.jdeveloper.html.StaticPickList;
/**
 * Extends the oracle.jdeveloper.html.StaticPickList renderer to drive
 * off of a list binding.
 */
public class ListBindingPoplistRenderer extends StaticPickList {
  /**
   * Overrides renderToString() in StaticPickList.
   * 
   * @param row Current row of data values.
   * @return String representing HTML for this control.
   */
  public String renderToString(Row row) {
    BindingContainerDataSource ds = (BindingContainerDataSource)getDatasource();
    JUControlBinding b = ds.getControlBinding();
    String[] labels = null;
    String[] values = null;
    if (b instanceof JUCtrlListBinding) {
      JUCtrlListBinding listBinding = (JUCtrlListBinding)b;
      List valueList = listBinding.getDisplayData();
      int size = valueList.size();
      values = new String[size];
      labels = new String[size];
      for (int z = 0; z < size; z++) {
        labels[z] = (String)((Map)valueList.get(z)).get("prompt");
        values[z] = Integer.toString(z);
      }
      setValue(Integer.toString(listBinding.getSelectedIndex()));
    }
    setDataSource(labels,values);
    return super.renderToString(row);
  }  
}
