/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.fwkext;
import com.sun.java.util.collections.HashMap;
import com.sun.java.util.collections.Iterator;
import java.util.ArrayList;
import java.util.StringTokenizer;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
public class CustomEntityImpl extends EntityImpl {
  public CustomEntityImpl() {
  }
  private static final String RECALC_PREFIX = "Recalc_";
  private static final int PREFIX_LENGTH = 7;
  protected void notifyAttributesChanged(int[] attrIndices, Object[] values) {
    int attrIndexCount = attrIndices.length;
    EntityDefImpl def = getEntityDef();
    HashMap eoProps = def.getPropertiesMap();
    if (eoProps != null && eoProps.size() > 0) {
      Iterator iter = eoProps.keySet().iterator();
      ArrayList otherAttrIndices = null;
      // 1. Iterate over the set of custom entity properties
      while (iter.hasNext()) {
        String curPropName = (String)iter.next();
        if (curPropName.startsWith(RECALC_PREFIX)) {
          // 2. If property name starts with "Recalc_" get follow attr name
          String changingAttrNameToCheck = curPropName.substring(PREFIX_LENGTH);
          // 3. Get the index of the recalc-triggering attribute
          int changingAttrIndexToCheck =
              def.findAttributeDef(changingAttrNameToCheck).getIndex();
          if (isAttrIndexInList(changingAttrIndexToCheck,attrIndices)) {
            // 4. If list of changed attrs includes recalc-triggering attr,
            //    then tokenize the comma-separated value of the property
            //    to find the names of the attributes to recalculate
            String curPropValue = (String)eoProps.get(curPropName);
            StringTokenizer st = new StringTokenizer(curPropValue,",");
            if (otherAttrIndices == null) {
              otherAttrIndices = new ArrayList();
            }
            while (st.hasMoreTokens()) {
              String attrName = st.nextToken();
              int attrIndex = def.findAttributeDef(attrName).getIndex();
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
        // 5. If there were any attributes to recalculate, add their attribute
        //    indexes to the int[] of attributes whose values have changed
        int extraAttrsToAdd = otherAttrIndices.size();
        int[] newAttrIndices = new int[attrIndexCount + extraAttrsToAdd];
        Object[] newValues = new Object[attrIndexCount + extraAttrsToAdd];
        System.arraycopy(attrIndices,0,newAttrIndices,0,attrIndexCount);
        System.arraycopy(values,0,newValues,0,attrIndexCount);
        for (int z = 0; z < extraAttrsToAdd; z++) {
          newAttrIndices[attrIndexCount+z] =
           ((Integer)otherAttrIndices.get(z)).intValue();
          newValues[attrIndexCount+z] =
           getAttribute((Integer)otherAttrIndices.get(z));
        }
        attrIndices = newAttrIndices;
        values = newValues;
      }
    }
    // 6. Call the super with the possibly updated array of changed attributes    
    super.notifyAttributesChanged(attrIndices, values);
  } 
  /**
   * Returns true if the attribute with index number 'index' is a member
   * of the int[] attrIndexList.
   */
  private boolean isAttrIndexInList(int index, int[] attrIndexList) {
    for (int z = 0, attrListCount = attrIndexList.length; z < attrListCount; z++) {
      if (attrIndexList[z] == index) {
        return true;
      }
    }
    return false;
  }  
}
