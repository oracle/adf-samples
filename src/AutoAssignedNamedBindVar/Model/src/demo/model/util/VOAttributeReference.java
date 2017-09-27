/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.util;
import oracle.jbo.JboException;
import oracle.jbo.NoDefException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
public class VOAttributeReference {
  String expr;
  String voInstanceName;
  String voAttrName;
  public VOAttributeReference(DBTransaction txn, String expr) {
    this.expr = expr;
    initAndValidateMembers(txn);
  }
  private static final String ERR = "Error resolving expression '";
  private static final String WARNING = "Warning resolving expression '";

  private void initAndValidateMembers(DBTransaction txn) {
    Object retVal = null;
    int lastDot = expr.lastIndexOf('.');
    // Expr must be at least of the form "V.A"
    if (lastDot < 1 || lastDot == expr.length() - 1) {
      throw new JboException("Malformed expression '" + expr + "'");
    }
    voInstanceName = expr.substring(0, lastDot);
    voAttrName = expr.substring(lastDot + 1);
    ViewObject vo = txn.getRootApplicationModule().findViewObject(voInstanceName);
    if (vo == null) {
      throw new JboException(ERR + expr + "': view instance '" + 
                             voInstanceName + "' does not exist!");
    }
  }
  public Object getValue(DBTransaction txn) {
    Object retVal = null;
    ViewObject vo = txn.getRootApplicationModule().findViewObject(voInstanceName);
    if (vo == null) {
      throw new JboException(ERR + expr + "': view instance '" + 
                             voInstanceName + "' does not exist!");
    }
    Row currentRow = vo.getCurrentRow();
    if (currentRow == null) {
//      System.err.println(WARNING+ expr + "': view instance '" + 
//                             voInstanceName + "' has no current row, returning null");
    }
    else {
      try {
        retVal = currentRow.getAttribute(voAttrName);
      } catch (NoDefException nde) {
        throw new JboException(ERR + expr + "': view instance '" + 
                               voInstanceName + " has no attribute named '" + 
                               voAttrName + "'");
      }
    }
    return retVal;    
  }
  public String toString() {
    return "VOAttributeReference("+expr+")";
  }
}
