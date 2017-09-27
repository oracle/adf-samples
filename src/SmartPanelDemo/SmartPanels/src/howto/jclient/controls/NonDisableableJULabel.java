/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package howto.jclient.controls;
import oracle.jbo.uicli.controls.JULabel;
import java.awt.*;

public class NonDisableableJULabel extends JULabel  {

  private int columns = 0;
  private int columnWidth = 0;
  
  public NonDisableableJULabel() {
     setColumns(8);
  }

  public Dimension getPreferredSize() {
      synchronized (getTreeLock()) {
          Dimension size = super.getPreferredSize();
          if (columns != 0) {
              size.width = columns * getColumnWidth();
          }
          return size;
      }
  }

  public void setFont(Font f) {
      super.setFont(f);
      columnWidth = 0;
  }

  public void setColumns(int columns) {
      int oldVal = this.columns;
      if (columns < 0) {
          throw new IllegalArgumentException("columns less than zero.");
      }
      if (columns != oldVal) {
          this.columns = columns;
          invalidate();
      }
  }

  protected int getColumnWidth() {
      if (columnWidth == 0) {
          FontMetrics metrics = getFontMetrics(getFont());
          columnWidth = metrics.charWidth('m');
      }
      return columnWidth;
  }
    
  /**
   * By default, the JULabel calls disable() when the bound data
   * is not updateable. This seems like a bug to me.
   */
  public void setEnabled(boolean enabled) {
    super.setEnabled(true);
  }
  
}
