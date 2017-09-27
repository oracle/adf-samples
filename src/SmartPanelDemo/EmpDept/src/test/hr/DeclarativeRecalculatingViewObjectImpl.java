/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.hr;
import com.sun.java.util.collections.ArrayList;
import com.sun.java.util.collections.HashMap;
import com.sun.java.util.collections.Iterator;
import java.util.StringTokenizer;
import oracle.jbo.AttributeDef;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowSetImpl;
import oracle.jbo.Row;

public class DeclarativeRecalculatingViewObjectImpl extends ViewObjectImpl  {
  private static final String RECALC_PREFIX = "Recalc_";
  private static final int PREFIX_LENGTH = RECALC_PREFIX.length();
  /**
   * Overridden framework method to allow declarative specification of
   * attribute recalculation based on dependent attributes.
   * 
   * In order to have one or more attribute X, Y, Z recalculated when
   * attribute A is changed, add a custom VO level property to the view
   * object named Recalc_A whose value is the comma-separate list X,Y,Z
   * 
   * @param attrIndices
   * @param viewRows
   * @param vrs
   */
  protected void notifyRowUpdated(ViewRowSetImpl vrs, Row[] viewRows, int[] attrIndices) {
    int attrIndexCount = attrIndices.length;
    HashMap voProps = getPropertiesMap();
    if (voProps != null && voProps.size() > 0) {
      Iterator iter = voProps.keySet().iterator();
      ArrayList otherAttrIndices = null;
      while (iter.hasNext()) {
        String curPropName = (String)iter.next();
        if (curPropName.startsWith(RECALC_PREFIX)) {
          String changingAttrNameToCheck = curPropName.substring(PREFIX_LENGTH);
          int changingAttrIndexToCheck = findAttributeDef(changingAttrNameToCheck).getIndex();
          if (isAttrIndexInList(changingAttrIndexToCheck,attrIndices)) {
            String curPropValue = (String)voProps.get(curPropName);
            StringTokenizer st = new StringTokenizer(curPropValue,",");
            if (otherAttrIndices == null) {
              otherAttrIndices = new ArrayList();
            }
            int extraAttrIndexes = 0;
            while (st.hasMoreTokens()) {
              String attrName = st.nextToken();
              int attrIndex = findAttributeDef(attrName).getIndex();
              if (!isAttrIndexInList(attrIndex,attrIndices)) {
                Integer intAttr = new Integer(attrIndex);
                if (!otherAttrIndices.contains(intAttr)) {
                  otherAttrIndices.add(intAttr);
                }
              }
            }
          }
        }
      }
      if (otherAttrIndices != null && otherAttrIndices.size() > 0) {
        int extraAttrsToAdd = otherAttrIndices.size();
        int[] newAttrIndices = new int[attrIndexCount + extraAttrsToAdd];
        System.arraycopy(attrIndices,0,newAttrIndices,0,attrIndexCount);
        for (int z = 0; z < extraAttrsToAdd; z++) {
          newAttrIndices[attrIndexCount+z] = ((Integer)otherAttrIndices.get(z)).intValue();
        }
        attrIndices = newAttrIndices;
      }
    }
    super.notifyRowUpdated(vrs, viewRows, attrIndices);
  }
  private boolean isAttrIndexInList(int index, int[] attrIndexList) {
    for (int z = 0, attrListCount = attrIndexList.length; z < attrListCount; z++) {
      if (attrIndexList[z] == index) {
        return true;
      }
    }
    return false;
  }
}
