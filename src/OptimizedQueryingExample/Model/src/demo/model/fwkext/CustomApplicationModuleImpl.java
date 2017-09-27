/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.fwkext;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.jbo.AttributeDef;
import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewLink;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewLinkDefImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
public class CustomApplicationModuleImpl extends ApplicationModuleImpl {
  Map<String,RowSetIterator> disabledMasterRSIs = new HashMap<String,RowSetIterator>();
  /**
   * This method accepts a list of view object instance names, and enables
   * master/detail coordination for the view instances in the data model 
   * whose names appear in that list. It disables other master/detail 
   * coordination for the views mentioned since we assume the user doesn't
   * need to see the data for those view instances that are not mentioned
   * in the list of view instance names passed in.
   * 
   * @param voInstanceNames Array of view instance names for which to enable master/detail coordination
   */
  public void enableDetailsForViewInstances(String[] voInstanceNames) {
    /*
     * For this example, allow the custom application module environment
     * property named "enable.optimizations" to control whether the
     * optimizations are enabled or not for comparison.
     */
    if (!"true".equals(getSession().getEnvironment().get("enable.optimizations"))) {
      return;
    }
    // Since we'll be testing containership, it's handier to work with a list
    List<String> voInstList = (List<String>)Arrays.asList(voInstanceNames);
    /*
     * Loop over each view instance name in the list
     */
    for (String voName : voInstList) {
      ViewObject vo = findViewObject(voName);
      /*
       * If the view instance name is a valid one...
       */
      if (vo != null) {
        String[] viewLinkNames = vo.getViewLinkNames();
        /*
         * process the list of view links involving this view object
         */
        for (String vlName2 : viewLinkNames) {
          ViewLink vl = findViewLink(vlName2);
          String source = vl.getSource().getName();
          /*
           * If the current view instance we're processing plays the
           * role of "source" in the view link that its involved in
           * (e.g. it is the "master" in the master/detail)...
           */
          if (source.equals(voName)) {
            /*
             * Get the name of the destination view link instance name
             */
            String dest   = vl.getDestination().getName();
            boolean enable = true;
            /*
             * If the destination view link instance name is not in the
             * list of view instances names passed in by the caller, then
             * it does not need to be enabled for master/detail coordination.
             */
            if (!voInstList.contains(dest)) {
              enable = false;
            }
            /*
             * Get the detail/destination view object instance, and
             * retrieve its array of master row set iterators.
             */
            ViewObject destVO = findViewObject(dest);
            RowSetIterator[] masterRSIs = destVO.getMasterRowSetIterators();
            /*
             * If we need to enable master/detail coordination...
             */
            if (enable) { // Enable it
              boolean foundEnabledMasterRSI = false;
              /*
               * Loop over the master RSIs to see if the master RSI
               * corresponding to the source view object we're processing
               * is already enabled (i.e. already in the list of master RSIs
               * that the detail is listening on for automatic detail 
               * coordination).
               */
              for (RowSetIterator rsi : masterRSIs) {
                String rsiForVO = rsi.getRowSet().getViewObject().getName();
                if (rsiForVO.equals(source)) {
                  foundEnabledMasterRSI = true;
                  break;
                }
              }
              /*
               * If we didn't find the source view object's master RSI in
               * the detail view object instance's master RSI list, then 
               * we need to add it back.
               */
              if (!foundEnabledMasterRSI) {
                /*
                 * Retrieve the master RSI from the map of disabledMasterRSIs
                 */
                RowSetIterator rsi = disabledMasterRSIs.get(dest);
                if (rsi != null) {
                  /*
                   * And set that master RSI back into the detail view object
                   * instance's list of master RSI's.
                   */
                  destVO.setMasterRowSetIterator(rsi);
                  /*
                   * Then remove it from the list of disabled master RSI's
                   */
                  disabledMasterRSIs.remove(dest);
                  /*
                   * Finally, if upon enabling the master RSI, we detect
                   * that the detail row set's filter keys are for a different
                   * "current master row" than the *actual* current row in
                   * the master view object instance, then we need to mark
                   * the detail view instance as needing re-querying by
                   * calling clearCache() on it.
                   */
                  if (masterRowDiffersFromDestFilterKeys(rsi,vl,destVO)) {
                    destVO.clearCache();
                  }                  
                }
              }
            }
            /*
             * If instead we should be disabling the master/detail 
             * coordination for the destination view instance...
             */
            else { 
              /*
               * See if the master RSI we're about to disable is already
               * in our disabled map.
               */
              RowSetIterator disabledRSI = disabledMasterRSIs.get(dest);
              /*
               * If it's not, then remove the masterRSI and put it into
               * our disabledMasterRSI's map.
               */
              if (disabledRSI == null) {
                for (RowSetIterator rsi : masterRSIs) {
                  String rsiForVO = rsi.getRowSet().getViewObject().getName();
                  if (rsiForVO.equals(source)) {
                    destVO.removeMasterRowSetIterator(rsi);
                    disabledMasterRSIs.put(dest,rsi);
                    break;
                  }
                }
              }
            }
          }
        }
      }
      else {
        System.err.println("WARNING: View instance '"+voName+"' does not exist.");
      }      
    }
  }
  /**
   * Helper method to determine if the row filter keys of the detail rowset
   * differ from the matching source view link attributes in the current row
   * of the master view object.
   * 
   * @param masterRSI Master RowSetIterator
   * @param vl ViewLink involving the destination view object instance
   * @param destVO destination view object instance
   * @return true if the master row differs from destination row filter key
   */
  private boolean masterRowDiffersFromDestFilterKeys(RowSetIterator masterRSI,
                                                     ViewLink vl,
                                                     ViewObject destVO) {
    ViewObjectImpl destVOImpl = (ViewObjectImpl)destVO;
    Object[] destVOFilterValues = destVOImpl.getRowFilterValues();
    if (destVOFilterValues != null) {
      Row r = masterRSI.getCurrentRow();
      if (r != null) {
        Object[] masterViewLinkAttrValues = getMasterViewLinkAttrValues(r,vl);
        int numValues = masterViewLinkAttrValues.length;
        for (int z = 0; z < numValues; z++) {
          if (!masterViewLinkAttrValues[z].equals(destVOFilterValues[z])) {
            return true;
          }
        }
        return false;
      }
    }
    return true;
  }
  /**
   * Helper method to get the source view link attribute values from
   * a master row passed in, based on a particular view link.
   * 
   * @param r Master row
   * @param vl View link instance to use
   * @return array of attributes in master row involved in view link
   */
  private Object[] getMasterViewLinkAttrValues(Row r, ViewLink vl) {
    ViewLinkDefImpl vlDef = (ViewLinkDefImpl)((ViewLinkImpl)vl).getDef();
    AttributeDefImpl[] sourceAttrs = vlDef.getSourceEnd().getAttributeDefImpls();
    Object[] attrValues = new Object[sourceAttrs.length];
    for (int z = 0; z < sourceAttrs.length; z++) {
      attrValues[z] = r.getAttribute(sourceAttrs[z].getName());
    }
    return attrValues;
  }
}

