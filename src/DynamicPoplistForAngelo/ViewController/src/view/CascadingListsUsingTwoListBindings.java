/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

public class CascadingListsUsingTwoListBindings {
  public CascadingListsUsingTwoListBindings() {
  }
     public void onRegionListChanged(ValueChangeEvent valueChangeEvent) {
       FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("RegionChanged","x");
     }
     public void onCountryChanged(ValueChangeEvent valueChangeEvent) {
       FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("CountryChanged","x");
     }  
}
