/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import oracle.adf.view.rich.component.rich.fragment.RichRegion;
/*
 * NOTE: This class has no private member fields so it's fine to
 *       register the class as an applicationScope helper class
 */
public class RegionHelper {
  private static final String REFRESH_REGION_PREFIX = "refreshRegion_";
  private static final int PREFIX_LENGTH = REFRESH_REGION_PREFIX.length();
  /* This action event method looks at the id of the command component
   * that invokes it, and if the component's id starts with "refreshRegion_"
   * (e.g. "refreshRegion_r1") then it tries to find a region in the page
   * with an id matching the string that follows this prefix (e.g. "r1")
   * and refreshes that region.
   */
  public void refreshRegionUsingButtonId(ActionEvent event) {
      // Get the id of the component whose action event handler was triggered
      String componentId = event.getComponent().getId();
      // If the component's id starts with the "refreshRegion_" prefix
      if (componentId.startsWith(REFRESH_REGION_PREFIX) 
          && componentId.length() > PREFIX_LENGTH) {
        // Use the string that follows this prefix as the region id to lookup
        String regionIdToRefresh = componentId.substring(PREFIX_LENGTH);
        FacesContext fc = FacesContext.getCurrentInstance();
        // Find the candidate region by id
        UIComponent uic = fc.getViewRoot().findComponent(regionIdToRefresh);
        if (uic instanceof RichRegion) {
          // If the component exists and is a region, then refresh it
          ((RichRegion)uic).refresh(fc);
        }
      }
  }  
}
