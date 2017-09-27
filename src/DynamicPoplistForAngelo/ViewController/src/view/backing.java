/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
public class backing {
  public backing() {
  }
  public void onRegionListChanged(ValueChangeEvent valueChangeEvent) {
    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("RegionChanged","x");
  }
}
